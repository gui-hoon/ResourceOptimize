//package com.example.demo;
// 
//import java.time.Instant;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.demo.dao.AwsMapper;
//import com.example.demo.dto.AwsDto;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
//import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
//import software.amazon.awssdk.services.cloudwatch.model.Dimension;
//import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataRequest;
//import software.amazon.awssdk.services.cloudwatch.model.GetMetricDataResponse;
//import software.amazon.awssdk.services.cloudwatch.model.Metric;
//import software.amazon.awssdk.services.cloudwatch.model.MetricDataQuery;
//import software.amazon.awssdk.services.cloudwatch.model.MetricDataResult;
//import software.amazon.awssdk.services.cloudwatch.model.MetricStat;
// 
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class GetMetricsTest {
//
//	@Autowired
//    private AwsMapper aMapper;
//    
//    @Test
//    public void awsTest() {
//    	List<AwsDto> awsKeyList = aMapper.selectAllAccessKey();
//    	
//    	for(AwsDto awsKey : awsKeyList) {
//			String accessKey = awsKey.getAccessKey();
//			String secretKey = awsKey.getSecretKey();
//			AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
//		    AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
//		    CloudWatchClient cloudWatchClient  = CloudWatchClient.builder().credentialsProvider(credentialsProvider).build();
//	
//	        String identifier = "InstanceId";
//	        List<String> instanceIds = new ArrayList<>(Arrays.asList("i-061a45508883aca13", "i-00858915f8a7f626e"));
//	        int period = 3600;
//	        
//		    getMetDataAvg(cloudWatchClient, identifier, instanceIds, period);
//		    cloudWatchClient .close();
//    	}
//    }
//    
//    public void insertAws(String instanceID, String times, String resource, String statistic, double val) {
//    	AwsDto awsresource = new AwsDto();
//
//        awsresource.setInstanceID(instanceID);
//        awsresource.setTimes(times);
//        awsresource.setResource(resource);
//        awsresource.setStatistic(statistic);
//        awsresource.setVal(val);
//        
//        aMapper.insertAwsResource(awsresource);
//    }
//    
//    public void getMetDataAvg(CloudWatchClient cloudWatchClient, String identifier, List<String> instanceIds, int period) {
//    	
//    	boolean done = false;
//        String nToken = null;
//        
//        List<String> metricNames = new ArrayList<>(Arrays.asList("CPUUtilization", "DiskReadBytes", "DiskWriteBytes"
//        		, "NetworkIn", "NetworkOut"));
//        List<String> statistics = new ArrayList<>(Arrays.asList("Average", "Minimum", "Maximum"));
//        
//        ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");
//        Instant startTime = Instant.parse("2022-09-27T00:00:00Z");
//        Instant endTime = Instant.parse("2022-09-28T00:00:00Z");
//        
//	    try {
//	    	while(!done) {
//	            List<MetricDataQuery> metricDataQueryList = new ArrayList<>();
//	            
//	            int numbering = 0;
//	            for(String instanceId : instanceIds) {
//	                Dimension dimension = Dimension.builder().name(identifier).value(instanceId).build();
//	                for (String metricName : metricNames) {
//	                	Metric metric = Metric.builder().namespace("AWS/EC2").dimensions(dimension).metricName(metricName).build();
//	                	for (String stat : statistics) {
//			                MetricStat metricStat = MetricStat.builder().metric(metric).period(period).stat(stat).build();
//			                MetricDataQuery metricDataQuery = MetricDataQuery.builder().metricStat(metricStat).id("m"+(numbering++)).build();
//			                metricDataQueryList.add(metricDataQuery);
//	                	}
//	                }
//	            }
//	            GetMetricDataRequest request = GetMetricDataRequest.builder().metricDataQueries(metricDataQueryList).startTime(startTime).endTime(endTime).build();
//	            GetMetricDataResponse response = cloudWatchClient.getMetricData(request);
//	            
//	            List<Instant> timestamps = null;
//	            List<Double> values = null;
//	            
//	            for(MetricDataResult result : response.metricDataResults()) {
//	                timestamps = result.timestamps();
//	                values = result.values();
//	                
//	                System.out.println(String.format("\n id : %s label : %s", result.id(), result.label()));
//		            
//	                for (int i=values.size()-1; i>=0; i--) {
//	                	System.out.println(String.format("timestamp : %s, value : %s", 
//	                    		timestamps.get(i).atZone(ZONE_ID), values.get(i)));
//	                	insertAws(result.label().split(" ")[0], String.valueOf(timestamps.get(i).getEpochSecond()), result.label().split(" ")[1], 
//	                			result.label().split(" ")[2], values.get(i));
//	                }
//	            }
//	            
//		        if(response.nextToken() == null) {
//	                done = true;
//	            } else {
//	                nToken = response.nextToken();
//	            }
//	    	}
//	    	
//	    	} catch (CloudWatchException e) {
//	    		System.err.println(e.awsErrorDetails().errorMessage());
//	    		System.exit(1);
//	    	}
//    }
//}