//package com.example.demo;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.demo.dto.AwsDto;
//import com.example.demo.service.AwsDetailServiceImpl;
//import com.example.demo.service.AwsServiceImpl;
//import com.example.demo.service.Ec2PriceService;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class javaTest {
//	
//	@Autowired
//    private AwsServiceImpl aService;
//	@Autowired
//	private AwsDetailServiceImpl adService;
//	@Autowired
//	private Ec2PriceService ec2Service;
//	
//    @Test
//    public void javaTest() {
//    	List<AwsDto> intanceIdList = aService.getAwsInstanceID("");
//    	for (AwsDto instance : intanceIdList) {
//    		
//    		String instanceType = instance.getInstanceType();
//    		System.out.println(instanceType);
//    		
//    		String[] instanceName = instanceType.split("\\.");
//    		System.out.println(Arrays.toString(instanceName));
//    	}
//
//    }
//
//}
