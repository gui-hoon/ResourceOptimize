package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Ec2PriceMapper;
import com.example.demo.dto.Ec2PriceDto;

@Service
public class Ec2PriceServiceImpl implements Ec2PriceService{
	
	@Autowired
	private Ec2PriceMapper ec2Mapper;

	@Override
	public Ec2PriceDto getEc2Price(String regioncode, String operatingSys, String instanceType) {
		return ec2Mapper.selectEc2Price(regioncode, operatingSys, instanceType);
	}
	
	@Override
	public String getEc2RegionName(String regioncode) {
		return ec2Mapper.selectEc2RegionName(regioncode);
	}
	
	@Override
	public List<Ec2PriceDto> getEc2PriceList(String regioncode, String operatingSys, String instanceType) {
		return ec2Mapper.selectEc2PriceList(regioncode, operatingSys, instanceType);
	}

	@Override
	public List<Ec2PriceDto> getOnDemandList(String regionname, String operatingSys) {
		return ec2Mapper.seletOnDemandList(regionname, operatingSys);
	}

	@Override
	public List<String> getRegionnameList() {
		return ec2Mapper.selectRegionnameList();
	}

	@Override
	public Ec2PriceDto getInstanceType(Ec2PriceDto ec2) {
		return ec2Mapper.selectInstanceType(ec2);
	}

	@Override
	public void upCustomPrice(Ec2PriceDto ec2) {
		ec2Mapper.updateCustomPrice(ec2);
	}


}
