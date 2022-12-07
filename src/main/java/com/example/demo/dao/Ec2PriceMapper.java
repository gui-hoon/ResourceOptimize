package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.Ec2PriceDto;

@Mapper
public interface Ec2PriceMapper {
	// table - ec2_pricing_comparison
	public Ec2PriceDto selectEc2Price (String regioncode, String operatingSys, String instanceType);
	public String selectEc2RegionName (String regioncode);
	public List<Ec2PriceDto> selectEc2PriceList(String regioncode, String operatingSys, String instanceType);
	
	public List<Ec2PriceDto> seletOnDemandList(String regionname, String operatingSys);
	public List<String> selectRegionnameList();
	public Ec2PriceDto selectInstanceType(Ec2PriceDto ec2);
	public void updateCustomPrice(Ec2PriceDto ec2);
}
