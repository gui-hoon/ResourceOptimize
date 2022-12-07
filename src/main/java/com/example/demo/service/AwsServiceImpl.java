package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AwsMapper;
import com.example.demo.dao.Ec2PriceMapper;
import com.example.demo.dto.AwsDto;
import com.example.demo.dto.Ec2PriceDto;

@Service
public class AwsServiceImpl implements AwsService{
	
	@Autowired
    private AwsMapper aMapper;
	
	@Autowired
    private Ec2PriceMapper ec2Mapper;
	
	// AwsResourceController.java
	@Override
	public long getInstanceAvgVal(String instanceID, String resource, String statistic, long startDay, long endDay) {
		long val = 0;
		AwsDto aws = new AwsDto();
		aws = aMapper.selectAvgVal(instanceID, resource, statistic, startDay, endDay);
		
		if(aws != null) {
			val = aws.getVal();
		}
		
		return val;
	}

	@Override
	public List<AwsDto> getInstanceDiskAvgVal(String instanceID, String statistic, long startDay, long endDay) {
		List<AwsDto> instanceDiskList = new ArrayList<>();
		instanceDiskList = aMapper.selectDiskAvgVal(instanceID, statistic, startDay, endDay);
		
		return instanceDiskList;
	}	
	
	@Override
	public Map<String, List<AwsDto>> MappingAccountID(long startDay, long endDay) {
		Map<String, List<AwsDto>> accountIDMap = new HashMap<>();
		List<AwsDto> awsInstanceList = new ArrayList<AwsDto>();
		List<AwsDto> sameAccountIDList = new ArrayList<AwsDto>();
		
		String accountID = "";
		int count = 0;
		awsInstanceList = aMapper.selectAllInstance();
		
		for (AwsDto aws : awsInstanceList) {
			count += 1;
			// set cpu, net, mem
			aws.setCpuUVal(getInstanceAvgVal(aws.getInstanceID(), "CPUUtilization", "Average", startDay, endDay));
			aws.setNetIVal(getInstanceAvgVal(aws.getInstanceID(), "NetworkIn", "Average", startDay, endDay));
			aws.setNetOVal(getInstanceAvgVal(aws.getInstanceID(), "NetworkOut", "Average", startDay, endDay));
			aws.setMemUVal(getInstanceAvgVal(aws.getInstanceID(), "MemoryUsed", "Average", startDay, endDay));
			
			// set disk
			aws.setDiskList(getInstanceDiskAvgVal(aws.getInstanceID(), "Average", startDay, endDay));
			aws.setDiskCount(aMapper.selectInstanceDiskList(aws.getInstanceID(), startDay, endDay).size());
			
			// instance 사용 시간 계산 및 사용 비용 계산
			int operatingTime = 0;
			double hourlyCost = 0;
			String operatingSys = aws.getPlatform();
			if (operatingSys.equals("Linux/UNIX")) {	operatingSys = "Linux";		}
			
			operatingTime = aMapper.calOperatingTime(aws.getInstanceID(), startDay, endDay);
			hourlyCost = ec2Mapper.selectEc2Price(aws.getRegion(), operatingSys, aws.getInstanceType()).getPrice();
			aws.setHourlyCost(hourlyCost);
			
			double usageCost = Math.round((hourlyCost * operatingTime)*100.0)/100.0;
			
			aws.setUsageCost(usageCost);
			aws.setOperatingTime(operatingTime);
			
			if (accountID.isEmpty()) {
				accountID = aws.getAccountID();
				sameAccountIDList.add(aws);
				if (count == awsInstanceList.size()) {
					accountIDMap.put(accountID, sameAccountIDList);
				}
			} else {
				if (accountID.equals(aws.getAccountID())) {
					sameAccountIDList.add(aws);
					if (count == awsInstanceList.size()) {
						accountIDMap.put(accountID, sameAccountIDList);
					}
				} else {
					accountIDMap.put(accountID, sameAccountIDList);
					sameAccountIDList = new ArrayList<AwsDto>();
					sameAccountIDList.add(aws);
					accountID = aws.getAccountID();
					if (count == awsInstanceList.size()) {
						accountIDMap.put(accountID, sameAccountIDList);
					}
				}
			}
		}
		
		return accountIDMap;
	}

	@Override
	public Map<String, Map<String, List<AwsDto>>> createProblemMap(Map<String, List<AwsDto>> accountIDMap, long startDay, long endDay) {
		Map<String, Map<String, List<AwsDto>>> accountIDProblemMap = new HashMap<>();
		Map<String, List<AwsDto>> instanceAccountIDMap = accountIDMap;
		List<AwsDto> sameAccountIDList = new ArrayList<>();

		for (String accountID : instanceAccountIDMap.keySet()) {
			sameAccountIDList = instanceAccountIDMap.get(accountID);
			Map<String, List<AwsDto>> resourceProblemMap = new HashMap<>();
			
			List<AwsDto> cpuUProblemList = new ArrayList<>();
			List<AwsDto> diskRProblemList = new ArrayList<>();
			List<AwsDto> diskWProblemList = new ArrayList<>();
			List<AwsDto> diskFProblemList = new ArrayList<>();
			List<AwsDto> netIProblemList = new ArrayList<>();
			List<AwsDto> netOProblemList = new ArrayList<>();
			List<AwsDto> memUProblemList = new ArrayList<>();
			List<AwsDto> stopInstanceList = new ArrayList<>();
			
			for (AwsDto aws : sameAccountIDList) {	
				// cpu, net, mem
				long cpuUVal = aws.getCpuUVal();
				long netIVal = aws.getNetIVal();
				long netOVal = aws.getNetOVal();
				long memUVal = aws.getMemUVal();
				
				if ((cpuUVal + netIVal + netOVal + memUVal) != 0) {
					if (cpuUVal < 10 || cpuUVal > 80) {
						cpuUProblemList.add(aws);
					}
					if (netIVal < 1000 || netIVal > 100000) {
						netIProblemList.add(aws);
					}
					if (netOVal < 1000 || netOVal > 100000) {
						netOProblemList.add(aws);
					}
					if (memUVal < 10 || memUVal > 80) {
						memUProblemList.add(aws);
					}
					
					// disk
					List<AwsDto> oneInstanceDiskIDList = aws.getDiskList();
					
					for (AwsDto oneInstanceDiskID : oneInstanceDiskIDList) {
						oneInstanceDiskID.setCpuUVal(cpuUVal);
						oneInstanceDiskID.setNetIVal(netIVal);
						oneInstanceDiskID.setNetOVal(netOVal);
						oneInstanceDiskID.setMemUVal(memUVal);
						
						if (oneInstanceDiskID.getDiskRVal() < 1000 || oneInstanceDiskID.getDiskRVal() > 100000) {
							diskRProblemList.add(oneInstanceDiskID);
						}
						if (oneInstanceDiskID.getDiskWVal() < 1000 || oneInstanceDiskID.getDiskWVal() > 100000) {
							diskWProblemList.add(oneInstanceDiskID);
						}
						if (oneInstanceDiskID.getDiskFVal() < 10) {
							diskFProblemList.add(oneInstanceDiskID);
						}
					}
				} else {
					stopInstanceList.add(aws);
				}
				
			}
			resourceProblemMap.put("cpuUProblem", cpuUProblemList);
			resourceProblemMap.put("netIProblem", netIProblemList);
			resourceProblemMap.put("netOProblem", netOProblemList);
			resourceProblemMap.put("memUProblem", memUProblemList);
			
			resourceProblemMap.put("diskRProblem", diskRProblemList);
			resourceProblemMap.put("diskWProblem", diskWProblemList);
			resourceProblemMap.put("diskFProblem", diskFProblemList);
			
			resourceProblemMap.put("stopInstance", stopInstanceList);
			
			accountIDProblemMap.put(accountID, resourceProblemMap);
		}
		return accountIDProblemMap;
	}

	@Override
	public Map<String, Map<Integer, Integer>> totalAndProblemCountMap(Map<String, List<AwsDto>> MappingAccountID,
			Map<String, Map<String, List<AwsDto>>> problemMap) {

		//problem map -> total problem count
		Map<String, Map<Integer, Integer>> accountCountMap = new HashMap<String, Map<Integer, Integer>>();
		
		for (String accountID : problemMap.keySet()) {
			int totalHostCount = 0;
			int problemCount = 0;
			Map<String, List<AwsDto>> resourceProblemMap = new HashMap<String, List<AwsDto>>();
			Map<Integer, Integer> totalAndProblemCountMap = new HashMap<Integer, Integer>();
			
			totalHostCount = MappingAccountID.get(accountID).size();
			resourceProblemMap = problemMap.get(accountID);
			for (String problem : resourceProblemMap.keySet()) {
				problemCount = problemCount + resourceProblemMap.get(problem).size();
			}
			
			totalAndProblemCountMap.put(totalHostCount, problemCount);
			accountCountMap.put(accountID, totalAndProblemCountMap);
		}
		
		return accountCountMap;
	}

	@Override
	public Map<String, List<AwsDto>> createDetailMap(String instanceID, String resource, String statistic, long startDay,
			long endDay) {
		Map<String, List<AwsDto>> instanceDetailMap = new HashMap<>();
		
		if (resource.equals("Cpu")) {
			List<AwsDto> awsCpu = aMapper.selectDailyDetail(instanceID, "CPUUtilization", statistic, startDay, endDay);
			instanceDetailMap.put("CPUUtilization", awsCpu);
		} else if (resource.equals("Network")) {
			List<AwsDto> awsNetI = aMapper.selectDailyDetail(instanceID, "NetworkIn", statistic, startDay, endDay);
			List<AwsDto> awsNetO = aMapper.selectDailyDetail(instanceID, "NetworkOut", statistic, startDay, endDay);
			instanceDetailMap.put("NetworkIn", awsNetI);
			instanceDetailMap.put("NetworkOut", awsNetO);
		} else if (resource.equals("Memory")) {
			List<AwsDto> awsMemU = aMapper.selectDailyDetail(instanceID, "MemoryUsed", statistic, startDay, endDay);
			instanceDetailMap.put("MemoryUsed", awsMemU);
		} else {
			instanceDetailMap = new HashMap<>();
		}
		
		return instanceDetailMap;
	}

	@Override
	public Object[] createDiskDetailMap(String instanceID, String resource, String statistic, long startDay,
			long endDay) {
		
		Map<String, Map<String, List<AwsDto>>> diskIDResourceMap = new HashMap<>();
		List<String> instanceDiskList = aMapper.selectInstanceDiskList(instanceID, startDay, endDay);
		 
		if (resource.equals("Disk")) {
			for(String diskID : instanceDiskList) {
				Map<String, List<AwsDto>> instanceDiskDetailMap = new HashMap<>();
				
				List<AwsDto> awsDiskR = aMapper.selectDailyDiskDetail(instanceID, diskID, "DiskReadBytes", statistic, startDay, endDay);
				List<AwsDto> awsDiskW = aMapper.selectDailyDiskDetail(instanceID, diskID, "DiskWriteBytes", statistic, startDay, endDay);
				List<AwsDto> awsDiskF = aMapper.selectDailyDiskDetail(instanceID, diskID, "DiskFree", statistic, startDay, endDay);
				instanceDiskDetailMap.put("DiskReadBytes", awsDiskR);
				instanceDiskDetailMap.put("DiskWriteBytes", awsDiskW);
				instanceDiskDetailMap.put("DiskFree", awsDiskF);
				diskIDResourceMap.put(diskID, instanceDiskDetailMap);
			}
		}
		
		return new Object[] {instanceDiskList, diskIDResourceMap};
	}

	@Override
	public Object[] asIsTobeInstanceTypeMap(List<AwsDto> sameAccountIDList, String state) {
		List<String> linuxList = new ArrayList<>();
		List<String> windowList = new ArrayList<>();
		
		for (AwsDto aws : sameAccountIDList) {
			String platform = aws.getPlatform();
			String instanceType = "";
			String recommend = aws.getRecommend();
			
			if (state.equals("asis")) {
				instanceType = aws.getInstanceType();
			} else if (state.equals("tobe")) {
				if (recommend == null) {
					instanceType = aws.getInstanceType();
				} else {
					instanceType = recommend;
				}
			}
			
			if (platform.equals("Linux/UNIX")) {
				linuxList.add(instanceType);
			} else {
				windowList.add(instanceType);
			}
			
		}
		
		Map<String, Integer> linuxInstanceTypeCount = new HashMap<>();
		Map<String, Integer> windowInstanceTypeCount = new HashMap<>();
		
		Set<String> linuxset = new HashSet<String>(linuxList);
		Set<String> windowset = new HashSet<String>(windowList);
		
        for (String str : linuxset) {
        	linuxInstanceTypeCount.put(str, Collections.frequency(linuxList, str));
        }
        
        for (String str : windowset) {
        	windowInstanceTypeCount.put(str, Collections.frequency(windowset, str));
        }
        
        
		return new Object[] {linuxInstanceTypeCount, windowInstanceTypeCount};
	}
		
	// AdminController.java
	// AWS IAM 계정
	@Override
	public List<AwsDto> getAwsConfigKeyList() {
		return aMapper.selectAwsConfigKeyList();
	}

	@Override
	public AwsDto getAwsConfigKey(AwsDto aws) {
		return aMapper.selectAwsConfigKey(aws);
	}

	@Override
	public void putAwsConfigKey(AwsDto aws) {
		if (!aws.getAccountID().isEmpty()) {
			aMapper.insertAwsConfigKey(aws);
		}
	}

	@Override
	public void upAwsConfigKey(AwsDto aws) {
		aMapper.updateAwsConfigKey(aws);
	}

	@Override
	public void delAwsConfigKey(AwsDto aws) {
		aMapper.deleteAwsConfigKey(aws);
		
	}
	
	// Dynatrace 계정
	@Override
	public List<AwsDto> getDynaConfigKeyList() {
		return aMapper.selectDynaConfigKeyList();
	}

	@Override
	public AwsDto getDynaConfigKey(AwsDto aws) {
		return aMapper.selectDynaConfigKey(aws);
	}

	@Override
	public void putDynaConfigKey(AwsDto aws) {
		if (!aws.getEnvironmentID().isEmpty()) {
			aMapper.insertDynaConfigKey(aws);
		}
	}

	@Override
	public void upDynaConfigKey(AwsDto aws) {
		if (aws.getEnvironment() != "" || aws.getEnvironmentID() != "" || aws.getToken() != "") {
			aMapper.updateDynaConfigKey(aws);
		}
	}

	@Override
	public void delDynaConfigKey(AwsDto aws) {
		aMapper.deleteDynaConfigKey(aws);
		
	}

	// admin 수정 log
	@Override
	public void putModifiedLog(String userId, String modifiedTable, String modifiedLog, String updateDate) {
		aMapper.insertModifiedLog(userId, modifiedTable, modifiedLog, updateDate);
	}
	
	@Override
	public List<AwsDto> getAdminLogList() {
		return aMapper.selectAdminLogList();
	}

}
