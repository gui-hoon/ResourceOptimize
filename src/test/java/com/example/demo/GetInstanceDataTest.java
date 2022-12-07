//
//package com.example.demo;
// 
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.ec2.AmazonEC2;
//import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
//import com.amazonaws.services.ec2.model.AvailabilityZone;
//import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
//import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
//import com.amazonaws.services.ec2.model.DescribeInstancesResult;
//import com.amazonaws.services.ec2.model.Instance;
//import com.amazonaws.services.ec2.model.Reservation;
//import com.example.demo.dao.AwsMapper;
//import com.example.demo.dto.AwsDto;
// 
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class GetInstanceDataTest {
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
//			
//			BasicAWSCredentials  awsBasicCredentials = new BasicAWSCredentials(accessKey, secretKey);
//		    AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsBasicCredentials)).build();
//		    
//		    getAwsData(ec2, accessKey);
//    	}
//    }
//    
//    public void getAwsData(AmazonEC2 ec2, String accessKey) {
//    	DescribeAvailabilityZonesResult zones_response = ec2.describeAvailabilityZones();
//		String region = "";
//		
//		for(AvailabilityZone zone : zones_response.getAvailabilityZones()) {
//			region = zone.getRegionName();
//		}
//		
//    	boolean done = false;
//		
//		DescribeInstancesRequest request = new DescribeInstancesRequest();
//		
//		while(!done) {
//		    DescribeInstancesResult response = ec2.describeInstances(request);
//
//		    for(Reservation reservation : response.getReservations()) {
//		        for(Instance instance : reservation.getInstances()) {
//		        	AwsDto aws = new AwsDto();
//		        	aws.setAccessKey(accessKey);
//		        	aws.setInstanceID(instance.getInstanceId());
//		        	aws.setImageID(instance.getImageId());
//		            aws.setPublicDns(instance.getPublicDnsName());
//		            aws.setPrivateDns(instance.getPrivateDnsName());
//		            aws.setInstanceType(instance.getInstanceType());
//		            aws.setArchitecture(instance.getArchitecture());
//		            aws.setPlatform(instance.getPlatformDetails());
//		            aws.setRegion(region);
//		            
//		            aMapper.insertAwsConfigID(aws);
//		        }
//		    }
//
//		    request.setNextToken(response.getNextToken());
//
//		    if(response.getNextToken() == null) {
//		        done = true;
//		    }
//		}
//    }
//}