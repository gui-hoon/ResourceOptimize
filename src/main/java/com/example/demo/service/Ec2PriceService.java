package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Ec2PriceDto;

public interface Ec2PriceService {
	
	// table - ec2_pricing_comparison
	public Ec2PriceDto getEc2Price (String regioncode, String operatingSys, String instanceType);
	public String getEc2RegionName (String regioncode);
	public List<Ec2PriceDto> getEc2PriceList (String regioncode, String operatingSys, String instanceType);
	
	public List<Ec2PriceDto> getOnDemandList (String regionname, String operatingSys);
	public List<String> getRegionnameList();
	public Ec2PriceDto getInstanceType(Ec2PriceDto ec2);
	public void upCustomPrice(Ec2PriceDto ec2);
}
