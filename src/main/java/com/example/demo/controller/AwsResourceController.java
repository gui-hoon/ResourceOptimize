package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AwsDto;
import com.example.demo.dto.Ec2PriceDto;
import com.example.demo.dto.UserVo;
import com.example.demo.service.AwsService;
import com.example.demo.service.Ec2PriceService;

@Controller
@RequestMapping(value="/home")
public class AwsResourceController {
	private AwsService aService;
	private Ec2PriceService ec2Service;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private String localStartDay = null;
	private String localEndDay = null;
	private long l_startDay = Instant.now().minus(1, ChronoUnit.DAYS).getEpochSecond();
	private long l_endDay = Instant.now().getEpochSecond() + 86400;
	
	private Map<String, Double> accountUsageCostMap = new HashMap<>();
	private Map<String, List<AwsDto>> accountIDMap = new HashMap<>(); 
	private Map<String, Map<String, List<AwsDto>>> accountIDProblemMap = new HashMap<>();
	private Map<String, Map<Integer, Integer>> accountCountMap = new HashMap<>();
	
	List<Ec2PriceDto> asIsLinuxEc2List = new ArrayList<>();
	List<Ec2PriceDto> asIsWindowEc2List = new ArrayList<>();
	List<Ec2PriceDto> toBeLinuxEc2List = new ArrayList<>();
	List<Ec2PriceDto> toBeWindowEc2List = new ArrayList<>();
	
	double asIsLinuxTotalCost = 0;
	double asIsWindowTotalCost = 0;
	double toBeLinuxTotalCost = 0;
	double toBeWindowTotalCost = 0;
	int toBeSetFlag = 0;
	
	private String selectedResource = "";
	private String selectedStatistic = "";
	
	public AwsResourceController (AwsService a, Ec2PriceService ec2) {
		this.aService=a;
		this.ec2Service=ec2;
	}
	
	// page 1
	// 전체 계정 overview
	@PostMapping(value="/accountID")
	public String selectAccountID (Model model, Authentication authentication, String startDay, String endDay) throws ParseException {
        localStartDay = startDay;
        localEndDay = endDay;
        
        // day set
        if (startDay != null && endDay != null) {
	        if (!startDay.isEmpty() && !endDay.isEmpty()) {
		        Date d_startDay = formatter.parse(startDay);
		        Date d_endDay = formatter.parse(endDay);
		        
		        l_startDay = d_startDay.getTime()/1000;
		        l_endDay = d_endDay.getTime()/1000 + 86400;
	        }
        }
        
        accountIDMap = aService.MappingAccountID(l_startDay, l_endDay);
        accountIDProblemMap = aService.createProblemMap(accountIDMap, l_startDay, l_endDay);
        accountCountMap = aService.totalAndProblemCountMap(accountIDMap, accountIDProblemMap);
        
		return "redirect:/home/accountID";
	}
	
	@GetMapping(value="/accountID")
	public String selectAccountID (Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		toBeSetFlag = 0;
		double userTotalCost = 0;
		
		for(String accountID : accountIDMap.keySet()) {
			double totalUsageCost = 0;
			
			for (AwsDto aws : accountIDMap.get(accountID)) {
				totalUsageCost = totalUsageCost + aws.getUsageCost();
			}
			userTotalCost = userTotalCost + totalUsageCost;
			accountUsageCostMap.put(accountID, Math.round(totalUsageCost*100.0)/100.0);
		}
		
		model.addAttribute("accountIDMap", accountIDMap);
		model.addAttribute("accountIDProblemMap", accountIDProblemMap);
		model.addAttribute("accountCountMap", accountCountMap);
		model.addAttribute("accountUsageCostMap", accountUsageCostMap);
		model.addAttribute("userTotalCost", Math.round(userTotalCost*100.0)/100.0);
		
		toBeLinuxEc2List = new ArrayList<>();
		toBeWindowEc2List = new ArrayList<>();
		
		model.addAttribute("member", userVo);
		
		return "pageOne/accountList";
	}
	
	// page 2
	// Monthly Instance Usage Cost
	@GetMapping(value="/accountID/monthlyRate")
	public String selectmonthlyRate (Model model, Authentication authentication) throws ParseException {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		
		// 오늘 날짜
		Calendar cal = Calendar.getInstance();
		String format = "yyyy-MM-01";
		
		// 이번 달 1일 날짜
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String thisMonth = sdf.format(cal.getTime());
		// 지난 달 1일 날짜
		cal.add(cal.MONTH, -1);
		String lastMonth = sdf.format(cal.getTime());
		// 지지난 달 1일 날짜
		cal.add(cal.MONTH, -1);
		String twoMonthsAgo = sdf.format(cal.getTime());
		
		// day set
		Date d_thisMonth = formatter.parse(thisMonth);
        Date d_lastMonth = formatter.parse(lastMonth);
        Date d_twoMonthsAgo = formatter.parse(twoMonthsAgo);
        
        long l_thisMonth = d_thisMonth.getTime()/1000;
        long l_lastMonth = d_lastMonth.getTime()/1000;
        long l_twoMonthsAgo = d_twoMonthsAgo.getTime()/1000;
        
		// 지지난 달 요금 맵
		Map<String, Double> twoMonthsAgoCostMap = new HashMap<>();
		Map<String, List<AwsDto>> twoMonthsAgoMap = aService.MappingAccountID(l_twoMonthsAgo, l_lastMonth);
		
		double twoMTotalCost = 0;
		for(String accountID : twoMonthsAgoMap.keySet()) {
			double totalUsageCost = 0;
			
			for (AwsDto aws : twoMonthsAgoMap.get(accountID)) {
				totalUsageCost = totalUsageCost + aws.getUsageCost();
			}
			twoMonthsAgoCostMap.put(accountID, Math.round(totalUsageCost*100.0)/100.0);
			twoMTotalCost = twoMTotalCost + totalUsageCost;
		}
		
		// 지난 달 요금 맵
		Map<String, Double> lastMonthCostMap = new HashMap<>();
		Map<String, List<AwsDto>> lastMonthMap = aService.MappingAccountID(l_lastMonth, l_thisMonth);
		
		double lastMTotalCost = 0;
		for(String accountID : lastMonthMap.keySet()) {
			double totalUsageCost = 0;
			
			for (AwsDto aws : lastMonthMap.get(accountID)) {
				totalUsageCost = totalUsageCost + aws.getUsageCost();
			}
			lastMonthCostMap.put(accountID, Math.round(totalUsageCost*100.0)/100.0);
			lastMTotalCost = lastMTotalCost + totalUsageCost;
		}
		
		model.addAttribute("member", userVo);
		
		model.addAttribute("twoMonthsAgoCostMap", twoMonthsAgoCostMap);
		model.addAttribute("lastMonthCostMap", lastMonthCostMap);
		
		model.addAttribute("twoMonthsAgo", twoMonthsAgo.substring(5, twoMonthsAgo.length()-3)+"월");
		model.addAttribute("lastMonth", lastMonth.substring(5, lastMonth.length()-3)+"월");
		
		model.addAttribute("twoMTotalCost", Math.round(twoMTotalCost*100.0)/100.0);
		model.addAttribute("lastMTotalCost", Math.round(lastMTotalCost*100.0)/100.0);
		
		return "pageTwo/monthlyRate";
	}
	
	// one account의 모든 host overview
	@PostMapping(value="/accountID/{accountID}")
	public String selectInstance (Model model, Authentication authentication,  @PathVariable("accountID") String accountID, String startDay, String endDay) throws ParseException {
        localStartDay = startDay;
        localEndDay = endDay;
        
        // day set
        if (startDay != null && endDay != null) {
	        if (!startDay.isEmpty() && !endDay.isEmpty()) {
		        Date d_startDay = formatter.parse(startDay);
		        Date d_endDay = formatter.parse(endDay);
		        
		        l_startDay = d_startDay.getTime()/1000;
		        l_endDay = d_endDay.getTime()/1000 + 86400;
	        }
        }
        
        accountIDMap = aService.MappingAccountID(l_startDay, l_endDay);
        accountIDProblemMap = aService.createProblemMap(accountIDMap, l_startDay, l_endDay);
        accountCountMap = aService.totalAndProblemCountMap(accountIDMap, accountIDProblemMap);
        
		return "redirect:/home/accountID/{accountID}";
	}
	
	@GetMapping(value="/accountID/{accountID}")
	public String selectInstance (Model model, Authentication authentication, @PathVariable("accountID") String accountID) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		model.addAttribute("accountID", accountID);
		
		List<AwsDto> sameAccountIDList = accountIDMap.get(accountID);
		
		model.addAttribute("sameAccountIDList", sameAccountIDList);
		model.addAttribute("accountUsageCostMap", accountUsageCostMap);
		model.addAttribute("member", userVo);
		
		return "pageTwo/instanceList";
	}
	
	// one account의 한 host 의 resource problem 
	@PostMapping(value="/accountID/{accountID}/problem")
	public String selectProblem (Model model, Authentication authentication,  @PathVariable("accountID") String accountID, String startDay, String endDay) throws ParseException {
        localStartDay = startDay;
        localEndDay = endDay;
        
        // day set
        if (startDay != null && endDay != null) {
	        if (!startDay.isEmpty() && !endDay.isEmpty()) {
		        Date d_startDay = formatter.parse(startDay);
		        Date d_endDay = formatter.parse(endDay);
		        
		        l_startDay = d_startDay.getTime()/1000;
		        l_endDay = d_endDay.getTime()/1000 + 86400;
	        }
        }
        
        accountIDMap = aService.MappingAccountID(l_startDay, l_endDay);
        accountIDProblemMap = aService.createProblemMap(accountIDMap, l_startDay, l_endDay);
        accountCountMap = aService.totalAndProblemCountMap(accountIDMap, accountIDProblemMap);
        
		return "redirect:/home/accountID/{accountID}/problem";
	}
	
	@GetMapping(value="/accountID/{accountID}/problem")
	public String selectProblem (Model model, Authentication authentication, @PathVariable("accountID") String accountID) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		model.addAttribute("accountID", accountID);
		
		Map<String, List<AwsDto>> resourceProblemMap = accountIDProblemMap.get(accountID);
		
		List<AwsDto> cpuUProblemList = resourceProblemMap.get("cpuUProblem");
		List<AwsDto> diskRProblemList = resourceProblemMap.get("diskRProblem");
		List<AwsDto> diskWProblemList = resourceProblemMap.get("diskWProblem");
		List<AwsDto> diskFProblemList = resourceProblemMap.get("diskFProblem");
		List<AwsDto> netIProblemList = resourceProblemMap.get("netIProblem");
		List<AwsDto> netOProblemList = resourceProblemMap.get("netOProblem");
		List<AwsDto> memUProblemList = resourceProblemMap.get("memUProblem");
		List<AwsDto> stopProblemList = resourceProblemMap.get("stopInstance");
		
		// cpuUProblem instance type recommend
		List<AwsDto> sameAccountIDList = accountIDMap.get(accountID);
		String regioncode = sameAccountIDList.get(0).getRegion();
		String regionname = ec2Service.getEc2RegionName(regioncode);
		
		for (AwsDto aws : cpuUProblemList) {
			String instanceType = aws.getInstanceType();
			String operatingSys = aws.getPlatform();
			if (operatingSys.equals("Linux/UNIX")) {	operatingSys = "Linux";		}
			String str = instanceType.split("\\.")[0];
			List <Ec2PriceDto> ec2List = ec2Service.getEc2PriceList(regioncode, operatingSys, str+".");
			int index = 0;
			
			for (int i = 0; i < ec2List.size(); i++) {
				if (instanceType.equals(ec2List.get(i).getInstanceType())) {
					index = i;
				}
			}
			
			if (aws.getCpuUVal() < 10) {
				if (index != 0) {
					int recommendIndex = index - 1;
					String recommendType = ec2List.get(recommendIndex).getInstanceType();
					double recommendPrice = ec2List.get(recommendIndex).getPrice();
					
					aws.setRecommend(recommendType);
					aws.setRecommendPrice(recommendPrice);
				} else {
					aws.setRecommend(str + "보다 낮은 instanceType 추천");
				}
			} else if (aws.getCpuUVal() > 80) {
				if (index != (ec2List.size()-1)) {
					int recommendIndex = index + 1;
					String recommendType = ec2List.get(recommendIndex).getInstanceType();
					double recommendPrice = ec2List.get(recommendIndex).getPrice();
					
					aws.setRecommend(recommendType);
					aws.setRecommendPrice(recommendPrice);
				} else {
					aws.setRecommend(str + "보다 높은 instanceType 추천");
				}
			}
		}
		
		// TO-BE instanceType List
		for (AwsDto cpuP : cpuUProblemList) {
			String recommend = cpuP.getRecommend();
			double recommendPrice = cpuP.getRecommendPrice();
			
			for (AwsDto aws : sameAccountIDList) {
				if (aws.getInstanceID().equals(cpuP.getInstanceID())) {
					aws.setRecommend(recommend);
					aws.setRecommendPrice(recommendPrice);
				}
			}
		}
	
		List<Ec2PriceDto> linuxEc2List = new ArrayList<>();
		List<Ec2PriceDto> windowEc2List = new ArrayList<>();
		
		Object[] instanceTypeMapObj = aService.asIsTobeInstanceTypeMap(sameAccountIDList, "tobe");
		Map<String, Integer> linuxInstanceTypeCount = (Map<String, Integer>) instanceTypeMapObj[0];
		Map<String, Integer> windowInstanceTypeCount = (Map<String, Integer>) instanceTypeMapObj[1];
		
		double linuxTotalCost = 0;
		double windowTotalCost = 0;
		
		for (String instanceType : linuxInstanceTypeCount.keySet()) {
			Ec2PriceDto ec2 = new Ec2PriceDto();
			if (instanceType.length() < 20) {
				ec2 = ec2Service.getEc2Price(regioncode, "Linux", instanceType);
			} else {
				ec2.setInstanceType(instanceType);
				ec2.setPrice(0);
				ec2.setOperatingSys("Linux");
				ec2.setRegion(regionname);
			}
			ec2.setCount(linuxInstanceTypeCount.get(instanceType));
			
			linuxEc2List.add(ec2);
			linuxTotalCost = linuxTotalCost + (ec2.getPrice() * linuxInstanceTypeCount.get(instanceType));
		}
		
		for (String instanceType : windowInstanceTypeCount.keySet()) {
			Ec2PriceDto ec2 = new Ec2PriceDto();
			if (instanceType.length() < 20) {
				ec2 = ec2Service.getEc2Price(regioncode, "Windows", instanceType);
			} else {
				ec2.setInstanceType(instanceType);
				ec2.setPrice(0);
				ec2.setOperatingSys("Windows");
				ec2.setRegion(regionname);
			}
			ec2.setCount(windowInstanceTypeCount.get(instanceType));
			
			windowEc2List.add(ec2);
			windowTotalCost = windowTotalCost + (ec2.getPrice() * windowInstanceTypeCount.get(instanceType));
		}
		toBeLinuxEc2List = linuxEc2List;
		toBeWindowEc2List = windowEc2List;
		toBeLinuxTotalCost = Math.round(linuxTotalCost*1000)/1000.0;
		toBeWindowTotalCost = Math.round(windowTotalCost*1000)/1000.0;
		toBeSetFlag = 1;
		
		model.addAttribute("cpuUProblemList", cpuUProblemList);
		model.addAttribute("diskRProblemList", diskRProblemList);
		model.addAttribute("diskWProblemList", diskWProblemList);
		model.addAttribute("diskFProblemList", diskFProblemList);
		model.addAttribute("netIProblemList", netIProblemList);
		model.addAttribute("netOProblemList", netOProblemList);
		model.addAttribute("memUProblemList", memUProblemList);
		model.addAttribute("stopProblemList", stopProblemList);
		
		model.addAttribute("resourceProblemMap", resourceProblemMap);
		model.addAttribute("member", userVo);
		
		return "pageTwo/problemList";
	}
	
	// one account의 한 host 의 instance type 추천 및 cost 변동 확인
	@GetMapping(value="/accountID/{accountID}/problem/recommend")
	public String selectRecommend (Model model, Authentication authentication, @PathVariable("accountID") String accountID) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		model.addAttribute("accountID", accountID);
		
		List<AwsDto> sameAccountIDList = accountIDMap.get(accountID);
		String regioncode = sameAccountIDList.get(0).getRegion();
		
		List<Ec2PriceDto> linuxEc2List = new ArrayList<>();
		List<Ec2PriceDto> windowEc2List = new ArrayList<>();
		
		Object[] instanceTypeMapObj = aService.asIsTobeInstanceTypeMap(sameAccountIDList, "asis");
		Map<String, Integer> linuxInstanceTypeCount = (Map<String, Integer>) instanceTypeMapObj[0];
		Map<String, Integer> windowInstanceTypeCount = (Map<String, Integer>) instanceTypeMapObj[1];
		
		double linuxTotalCost = 0;
		double windowTotalCost = 0;
		
		for (String instanceType : linuxInstanceTypeCount.keySet()) {
			Ec2PriceDto ec2 = new Ec2PriceDto();
			ec2 = ec2Service.getEc2Price(regioncode, "Linux", instanceType);
			ec2.setCount(linuxInstanceTypeCount.get(instanceType));
			
			linuxEc2List.add(ec2);
			linuxTotalCost = linuxTotalCost + (ec2.getPrice() * linuxInstanceTypeCount.get(instanceType));
		}
		
		for (String instanceType : windowInstanceTypeCount.keySet()) {
			Ec2PriceDto ec2 = new Ec2PriceDto();
			ec2 = ec2Service.getEc2Price(regioncode, "Windows", instanceType);
			ec2.setCount(windowInstanceTypeCount.get(instanceType));
			
			windowEc2List.add(ec2);
			windowTotalCost = windowTotalCost + (ec2.getPrice() * windowInstanceTypeCount.get(instanceType));
		}
		asIsLinuxEc2List = linuxEc2List;
		asIsWindowEc2List = windowEc2List;
		asIsLinuxTotalCost = Math.round(linuxTotalCost*1000)/1000.0;
		asIsWindowTotalCost = Math.round(windowTotalCost*1000)/1000.0;
		
		double linuxBenefit = Math.round((asIsLinuxTotalCost - toBeLinuxTotalCost)*1000)/1000.0;
		double windowBenefit = Math.round((asIsWindowTotalCost - toBeWindowTotalCost)*1000)/1000.0;
		double totalBenefit = Math.round((linuxBenefit + windowBenefit)*1000)/1000.0;
		
		model.addAttribute("asIsLinuxEc2List", asIsLinuxEc2List);
		model.addAttribute("asIsWindowEc2List", asIsWindowEc2List);
		model.addAttribute("asIsLinuxTotalCost", asIsLinuxTotalCost);
		model.addAttribute("asIsWindowTotalCost", asIsWindowTotalCost);
		
		model.addAttribute("toBeLinuxEc2List", toBeLinuxEc2List);
		model.addAttribute("toBeWindowEc2List", toBeWindowEc2List);
		model.addAttribute("toBeLinuxTotalCost", toBeLinuxTotalCost);
		model.addAttribute("toBeWindowTotalCost", toBeWindowTotalCost);
		
		model.addAttribute("linuxBenefit", linuxBenefit);
		model.addAttribute("windowBenefit", windowBenefit);
		model.addAttribute("totalBenefit", totalBenefit);
		model.addAttribute("toBeSetFlag", toBeSetFlag);
		
		model.addAttribute("member", userVo);
		
		return "pageTwo/recommend";
	}
	
	// page 3
	// 한 host의 property 및 data chart 확인
	@PostMapping(value="/accountID/{accountID}/{instanceID}")
	public String selectInstanceDetailChart (Model model, Authentication authentication,  @PathVariable("accountID") String accountID, @PathVariable("instanceID") String instanceID, String startDay, String endDay
			, String resource, String statistic) throws ParseException {
        localStartDay = startDay;
        localEndDay = endDay;
        selectedResource = resource;
        selectedStatistic = statistic;
        
        // day set
        if (startDay != null && endDay != null) {
	        if (!startDay.isEmpty() && !endDay.isEmpty()) {
		        Date d_startDay = formatter.parse(startDay);
		        Date d_endDay = formatter.parse(endDay);
		        
		        l_startDay = d_startDay.getTime()/1000;
		        l_endDay = d_endDay.getTime()/1000 + 86400;
	        }
        }
        
        
        accountIDMap = aService.MappingAccountID(l_startDay, l_endDay);
        accountIDProblemMap = aService.createProblemMap(accountIDMap, l_startDay, l_endDay);
        accountCountMap = aService.totalAndProblemCountMap(accountIDMap, accountIDProblemMap);
        
		return "redirect:/home/accountID/{accountID}/{instanceID}";
	}
	
	@GetMapping(value="/accountID/{accountID}/{instanceID}")
	public String selectInstanceDetailChart (Model model, Authentication authentication, @PathVariable("accountID") String accountID, @PathVariable("instanceID") String instanceID,
			String diskID) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		model.addAttribute("accountID", accountID);
		model.addAttribute("instanceID", instanceID);
		
		Map<String, List<AwsDto>> awsDailyDataMap = new HashMap<>();
		Map<String, Map<String, List<AwsDto>>> awsDailyDiskMap = new HashMap<>();
		
		for(AwsDto aws : accountIDMap.get(accountID)) {
			if (aws.getInstanceID().equals(instanceID)) {
				model.addAttribute("aws", aws);
			}
		}
		
		List<String> instanceDiskList = new ArrayList<>();
		
		if (selectedResource != null) {
        	if (selectedResource.contains("Cpu")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else if (selectedResource.contains("Disk")) {
            	instanceDiskList = (List<String>) aService.createDiskDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay)[0];
            	awsDailyDiskMap = (Map<String, Map<String, List<AwsDto>>>) aService.createDiskDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay)[1];
            } else if (selectedResource.contains("Network")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else if (selectedResource.contains("Memory")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else {
            	awsDailyDataMap = new HashMap<>();
            }
        }
		
		String selectedDiskID = diskID;
		Map<String, List<AwsDto>> diskIDResourceMap = awsDailyDiskMap.get(selectedDiskID);
		
		model.addAttribute("awsDailyDataMap", awsDailyDataMap);
		model.addAttribute("instanceDiskList", instanceDiskList);
		model.addAttribute("awsDailyDiskMap", awsDailyDiskMap);
		
		model.addAttribute("selectedDiskID", selectedDiskID);
		model.addAttribute("diskIDResourceMap", diskIDResourceMap);
		
        model.addAttribute("selectedResource", selectedResource);
        model.addAttribute("selectedStatistic", selectedStatistic);
		model.addAttribute("member", userVo);
		
		
		return "pageThree/instanceDetailChart";
	}
	
	// 한 host의 property 및 data detail list 확인
	@PostMapping(value="/accountID/{accountID}/{instanceID}/detail")
	public String selectInstanceDetailData (Model model, Authentication authentication,  @PathVariable("accountID") String accountID, @PathVariable("instanceID") String instanceID, String startDay, String endDay
			, String resource, String statistic) throws ParseException {
        localStartDay = startDay;
        localEndDay = endDay;
        selectedResource = resource;
        selectedStatistic = statistic;
        
        // day set
        if (startDay != null && endDay != null) {
	        if (!startDay.isEmpty() && !endDay.isEmpty()) {
		        Date d_startDay = formatter.parse(startDay);
		        Date d_endDay = formatter.parse(endDay);
		        
		        l_startDay = d_startDay.getTime()/1000;
		        l_endDay = d_endDay.getTime()/1000 + 86400;
	        }
        }
        
        
        accountIDMap = aService.MappingAccountID(l_startDay, l_endDay);
        accountIDProblemMap = aService.createProblemMap(accountIDMap, l_startDay, l_endDay);
        accountCountMap = aService.totalAndProblemCountMap(accountIDMap, accountIDProblemMap);
        
		return "redirect:/home/accountID/{accountID}/{instanceID}/detail";
	}
	
	@GetMapping(value="/accountID/{accountID}/{instanceID}/detail")
	public String selectInstanceDetailData (Model model, Authentication authentication, @PathVariable("accountID") String accountID, @PathVariable("instanceID") String instanceID) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
		model.addAttribute("startDay", localStartDay);
		model.addAttribute("endDay", localEndDay);
		model.addAttribute("accountID", accountID);
		model.addAttribute("instanceID", instanceID);
		
		Map<String, List<AwsDto>> awsDailyDataMap = new HashMap<>();
		Map<String, Map<String, List<AwsDto>>> awsDailyDiskMap = new HashMap<>();
		
		for(AwsDto aws : accountIDMap.get(accountID)) {
			if (aws.getInstanceID().equals(instanceID)) {
				model.addAttribute("aws", aws);
			}
		}
		
		
		if (selectedResource != null) {
        	if (selectedResource.contains("Cpu")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else if (selectedResource.contains("Disk")) {
            	awsDailyDiskMap = (Map<String, Map<String, List<AwsDto>>>) aService.createDiskDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay)[1];
            } else if (selectedResource.contains("Network")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else if (selectedResource.contains("Memory")) {
            	awsDailyDataMap = aService.createDetailMap(instanceID, selectedResource, selectedStatistic, l_startDay, l_endDay);
            } else {
            	awsDailyDataMap = new HashMap<>();
            }
        }
		
		model.addAttribute("awsDailyDataMap", awsDailyDataMap);
		model.addAttribute("awsDailyDiskMap", awsDailyDiskMap);
		
        model.addAttribute("selectedResource", selectedResource);
        model.addAttribute("selectedStatistic", selectedStatistic);
		model.addAttribute("member", userVo);
		
		
		return "pageThree/instanceDetailData";
	}
	
}
