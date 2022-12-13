package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsDto {
	// awsconfig_id
	private int FK_key_num;
	private String instanceID;
	private String imageID;
	private String publicDns;
	private String privateDns;
	private String instanceType;
	private String architecture;
	private String platform;
	private String region;
	
	// awsconfig_key
	private int num;
	private String accountID;
	private String accessKey;
	private String secretKey;
	
	// dynatrace key
	private String environmentID;
	private String environment;
	private String token;
	
	// awsresource
	// awsresource_disk
	private String diskID;
	private String times;
	private String resource;
	private String statistic;
	private long val;
	
	private long cpuUVal;
	private List<AwsDto> diskList;
	private long diskRVal;
	private long diskWVal;
	private long diskFVal = -1;
	private long netIVal;
	private long netOVal;
	private long memUVal = -1;
	
	private int diskCount;
	
	private String dateTimes;
	private String recommend;
	private double recommendPrice;
	
	private double benefit;
	
	// 조회 기간동안 사용  시간 및 비용 계산
	private double hourlyCost;
	private int operatingTime;
	private double usageCost;
	
	// ID 중복 확인
	private int success;
	
	// log
	private String userId;
	private String modifiedTable;
	private String admin_log;
	private String updateDate;
	
	// setting
	private int thLowCpu;
	private int thHighCpu;
	private long thLowNetI;
	private long thHighNetI;
	private long thLowNetO;
	private long thHighNetO;
	private int thLowMemU;
	private int thHighMemU;
	private long thLowDiskR;
	private long thHighDiskR;
	private long thLowDiskW;
	private long thHighDiskW;
	private int thLowDiskF;
	private int thHighDiskF;
}