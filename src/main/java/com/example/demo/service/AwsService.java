package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.AwsDto;

public interface AwsService {
	// AwsResourceController.java
	// make Group instances with the same accountID
	public Map<String, List<AwsDto>> MappingAccountID(long startDay, long endDay);
	
	// 설정 한 기간 동안 instance의 cpu, net, mem의 평균 값
	public long getInstanceAvgVal(String instanceID, String resource, String statistic, long startDay, long endDay);
	
	// 설정 한 기간 동안 instance의 disk의 평균 값
	public List<AwsDto> getInstanceDiskAvgVal(String instanceID, String statistic, long startDay, long endDay);
	
	// accountID로 Mapping 된 map으로 부터 cpu, disk, net, mem에 이상이 있는 instance들로 다시 mapping
	public Map<String, Map<String, List<AwsDto>>> createProblemMap (Map<String, List<AwsDto>> accountIDMap, long startDay, long endDay);
	// 한 account의 전체 problem 개수 count
	public Map<String, Map<Integer, Integer>> totalAndProblemCountMap (Map<String, List<AwsDto>> MappingAccountID, Map<String, Map<String, List<AwsDto>>> problemMap);

	// instance detail date cpu, net, mem
	public Map<String, List<AwsDto>> createDetailMap (String instanceID, String resource, String statistic, long startDay, long endDay);
	// instance detail date disk
	public Object[] createDiskDetailMap (String instanceID, String resource, String statistic, long startDay, long endDay);

	// 현재 account의 instance type 현황 맵
	public Object[] asIsTobeInstanceTypeMap (List<AwsDto> sameAccountIDList, String state);

	
	// AdminController.java
	// AWS IAM 계정
	public List<AwsDto> getAwsConfigKeyList();
	public AwsDto getAwsConfigKey(AwsDto aws);
	public void putAwsConfigKey(AwsDto aws);
	public void upAwsConfigKey(AwsDto aws);
	public void delAwsConfigKey(AwsDto aws);
	
	// AWS IAM 계정
	public List<AwsDto> getDynaConfigKeyList();
	public AwsDto getDynaConfigKey(AwsDto aws);
	public void putDynaConfigKey(AwsDto aws);
	public void upDynaConfigKey(AwsDto aws);
	public void delDynaConfigKey(AwsDto aws);
	
	// admin 수정 로그
	public void putModifiedLog(String userId, String modifiedTable, String modifiedLog, String updateDate);
	public List<AwsDto> getAdminLogList();
}
