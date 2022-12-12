package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.dto.AwsDto;

@Mapper
public interface AwsMapper {
	// AwsResourceController.java
	// select all instance
	public List<AwsDto> selectAllInstance();
	// select average value resource: cpu, net, mem & statistic: avg, min, max
	public AwsDto selectAvgVal(String instanceID, String resource, String statistic, long startDay, long endDay);
	// select average value resource: disk & statistic: avg, min, max
	public List<AwsDto> selectDiskAvgVal(String instanceID, String statistic, long startDay, long endDay);
	
	// select instance disk list
	public List<String> selectInstanceDiskList(String instanceID, long startDay, long endDay);
	
	// select instance daily detail data
	public List<AwsDto> selectDailyDetail(String instanceID, String resource, String statistic, long startDay, long endDay);
	// select instance daily detail data
	public List<AwsDto> selectDailyDiskDetail(String instanceID, String diskID, String resource, String statistic, long startDay, long endDay);

	// 조회 기간동안 사용 시간 계산
	public int calOperatingTime(String instanceID, long startDay, long endDay);
	
	
	
	// AdminController.java
	// AWS IAM 계정
	public List<AwsDto> selectAwsConfigKeyList();
	public AwsDto selectAwsConfigKey(AwsDto aws);
	public int awsIdDuplicateCheck (AwsDto aws);
	public void insertAwsConfigKey(AwsDto aws);
	public void updateAwsConfigKey(AwsDto aws);
	public void deleteAwsConfigKey(AwsDto aws);
	
	// AWS dynatrace 계정
	public List<AwsDto> selectDynaConfigKeyList();
	public AwsDto selectDynaConfigKey(AwsDto aws);
	public int dynaIdDuplicateCheck (AwsDto aws);
	public void insertDynaConfigKey(AwsDto aws);
	public void updateDynaConfigKey(AwsDto aws);
	public void deleteDynaConfigKey(AwsDto aws);
	
	// admin 수정 로그
	public void insertModifiedLog(String userId, String modifiedTable, String modifiedLog, String updateDate);
	public List<AwsDto> selectAdminLogList();
	
	// setting
	public AwsDto selectProblemThreshold();
	public void updateProblemThreshold(String thName, String val);
}





