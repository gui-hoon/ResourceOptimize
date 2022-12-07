package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ec2PriceDto {
	
	private String instanceType;
	private double price;
	private String ec2_vCPU;
	private String ec2_memory;
	private String operatingSys;
	private String ec2_storage;
	private String networkPerformance;
	private String region;
	private String customPrice;
	
	private int count;
	
	private String regioncode;
	private String regionname;
}