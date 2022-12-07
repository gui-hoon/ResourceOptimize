package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.AwsDto;
import com.example.demo.dto.Ec2PriceDto;
import com.example.demo.dto.UserVo;
import com.example.demo.service.AwsService;
import com.example.demo.service.Ec2PriceService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	private UserService uService;
	private AwsService aService;
	private Ec2PriceService ec2Service;
	
	private String selectedOs = "";
    private String selectedRegionname = "";
    
	public AdminController(UserService u, AwsService a, Ec2PriceService ec2) {
		this.uService=u;
		this.aService=a;
		this.ec2Service=ec2;
	}
	
	// admin home
	@RequestMapping(value="/admin_home", method = RequestMethod.GET)
    public String adminHome(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
		
        return "adminHome/admin_home";
    }
	
	// 회원관리
	@RequestMapping(value="/admin_member", method = RequestMethod.GET)
    public String memberList(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        List<UserVo> userList = uService.getAllUserList();
		model.addAttribute("userList", userList);
		
        return "adminHome/admin_member";
    }
	
	// AWS IAM 계정 관리
	/**
     * AWS 계정 목록
     */
	@RequestMapping(value="/admin_aws", method = RequestMethod.GET)
    public String awsConfigList(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        List<AwsDto> awsConfigKeyList = aService.getAwsConfigKeyList();
        model.addAttribute("awsConfigKeyList", awsConfigKeyList);
        
        return "adminHome/admin_aws";
    }
	
	/**
     * AWS 계정 등록 화면
     */
	@RequestMapping("/insertAwsKeyView")
    public String insertAwsKeyView(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "adminHome/insertAwsKey";
    }
	
	/**
     * AWS 계정 등록 
     */
	@PostMapping("/insertAwsKey")
    public String insertAwsKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Insert AWS account. accountID: " + aws.getAccountID() + ", accessKey: " + aws.getAccessKey() +
	    		", secretKey: " + aws.getSecretKey() + ", region: " + aws.getRegion();
	    
        aService.putModifiedLog(userVo.getUserId(), "awsconfig_key", modifiedLog, localTime);
        
        aService.putAwsConfigKey(aws);
        
        return "redirect:/admin/insertAwsKey";
    }
	
	@RequestMapping("/insertAwsKey")
    public String insertAwsKey(Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "redirect:/admin/admin_aws";
    }
	/**
     * AWS 상세 계정 조회
     */
	@RequestMapping("/getAwsKey")
    public String getAwsKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
		
        model.addAttribute("awsKey", aService.getAwsConfigKey(aws));
        return "adminHome/getAwsKey";
    }
	
	/**
     * AWS 계정 수정 후 목록으로 이동
     */
	@PostMapping("/updateAwsKey")
    public String updateAwsKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Modify AWS account. num: " +aws.getNum() + ", accountID: " + aws.getAccountID() + ", accessKey: " + aws.getAccessKey() +
	    		", secretKey: " + aws.getSecretKey() + ", region: " + aws.getRegion();
	    
        aService.putModifiedLog(userVo.getUserId(), "awsconfig_key", modifiedLog, localTime);
        aService.upAwsConfigKey(aws);
        
        return "redirect:/admin/updateAwsKey";
    }
	
	@RequestMapping("/updateAwsKey")
    public String updateAwsKey(Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "redirect:/admin/admin_aws";
    }
	
	/**
     * AWS 계정 삭제  후 목록으로 이동
     */
	@RequestMapping("/deleteAwsKey")
    public String deleteAwsKey(Model model, Authentication authentication, AwsDto aws) {
    	UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Delete AWS account. num: " +aws.getNum() + ", accountID: " + aws.getAccountID();
	    
        aService.putModifiedLog(userVo.getUserId(), "awsconfig_key", modifiedLog, localTime);
        
        aService.delAwsConfigKey(aws);
        
        return "redirect:/admin/admin_aws";
    }
    
	// Dynatrace url, access token 관리
    /**
     * Dynatrace 계정 목록
     */
	@RequestMapping(value="/admin_dyna", method = RequestMethod.GET)
    public String dynaConfigList(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        List<AwsDto> dynaConfigKeyList = aService.getDynaConfigKeyList();
        model.addAttribute("dynaConfigKeyList", dynaConfigKeyList);
        
        return "adminHome/admin_dyna";
    }
	
	/**
     * Dynatrace 계정 등록 화면
     */
	@RequestMapping("/insertDynaKeyView")
    public String insertDynaKeyView(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "adminHome/insertDynaKey";
    }
	
	/**
     * Dynatrace 계정 등록  
     */
	@PostMapping("/insertDynaKey")
    public String insertDynaKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
	 	aService.putDynaConfigKey(aws);
	 	
	 	SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Insert Dynatrace env. environment: " + aws.getEnvironmentID() + ", url: " + aws.getEnvironment() +
	    		", access token: " + aws.getToken();
	    
        aService.putModifiedLog(userVo.getUserId(), "dynatrace_key", modifiedLog, localTime);
        
        return "redirect:/admin/insertDynaKey";
    }
	
	@RequestMapping("/insertDynaKey")
    public String insertDynaKey(Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "redirect:/admin/admin_dyna";
    }
	
	/**
     * Dynatrace 상세 계정 조회
     */
	@RequestMapping("/getDynaKey")
    public String getDynaKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
		
        model.addAttribute("dynaKey", aService.getDynaConfigKey(aws));
        return "adminHome/getDynaKey";
    }
	
	/**
     * Dynatrace 계정 수정 후 목록으로 이동
     */
	@PostMapping("/updateDynaKey")
    public String updateDynaKey(Model model, Authentication authentication, AwsDto aws) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        aService.upDynaConfigKey(aws);
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Modify Dynatrace env. num: "+aws.getNum() + ", environment: " + aws.getEnvironmentID() + ", url: " + aws.getEnvironment() +
	    		", access token: " + aws.getToken();
	    
        aService.putModifiedLog(userVo.getUserId(), "dynatrace_key", modifiedLog, localTime);
        
        return "redirect:/admin/updateDynaKey";
    }
	
	@RequestMapping("/updateDynaKey")
    public String updateDynaKey(Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "redirect:/admin/admin_dyna";
    }
	
	/**
     * Dynatrace 계정 삭제 후 목록으로 이동
     */
	@RequestMapping("/deleteDynaKey")
    public String deleteDynaKey(Model model, Authentication authentication, AwsDto aws) {
    	UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        aService.delDynaConfigKey(aws);
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "Delete Dynatrace env. num: "+aws.getNum() + ", environment: " + aws.getEnvironmentID();
	    
        aService.putModifiedLog(userVo.getUserId(), "dynatrace_key", modifiedLog, localTime);
        
        return "redirect:/admin/admin_dyna";
    }
	
	
	// AWS List Price custom
    /**
     * On-Demand Pricing Table 목록
     */
	@RequestMapping(value="/admin_OnDemand")
    public String onDmandList(Model model, Authentication authentication, String operatingSys, String regionname){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        selectedOs = operatingSys;
        selectedRegionname = regionname;
		
        return "redirect:admin_OnDemand";
    }
    
	@RequestMapping(value="/admin_OnDemand", method = RequestMethod.GET)
    public String onDmandList(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        List<Ec2PriceDto> onDemandList = ec2Service.getOnDemandList(selectedRegionname, selectedOs);
		List<String> regionnameList = ec2Service.getRegionnameList();
		
		model.addAttribute("onDemandList", onDemandList);
		model.addAttribute("regionnameList", regionnameList);
		model.addAttribute("selectedOs", selectedOs);
		model.addAttribute("selectedRegionname", selectedRegionname);
		
        return "adminHome/admin_OnDemand";
    }
	
	/**
     * instance type 상세
     */
	@RequestMapping("/getInstanceType")
    public String getInstanceType(Model model, Authentication authentication, Ec2PriceDto ec2) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);

        
        model.addAttribute("instanceType", ec2Service.getInstanceType(ec2));
        return "adminHome/getInstanceType";
    }
	
	/**
     * customer set price 수정 후 목록으로 이동
     */
	@PostMapping("/updateCustomPrice")
    public String updateCustomPrice(Model model, Authentication authentication, Ec2PriceDto ec2) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        if (ec2.getCustomPrice().isEmpty()) {
        	ec2.setCustomPrice(String.valueOf(ec2.getPrice()));
        	ec2Service.upCustomPrice(ec2);
        } else {
        	ec2Service.upCustomPrice(ec2);
        }
        
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:sss");
        Date time = new Date();
	    String localTime = format.format(time);
	    
	    String modifiedLog = "customer set price. instance type: " + ec2.getInstanceType() + 
	    		", operating system: " + ec2.getOperatingSys() + ", region: " + ec2.getRegion() +
	    		"custom price: " +ec2.getCustomPrice();
	    
        aService.putModifiedLog(userVo.getUserId(), "ec2_pricing_comparison", modifiedLog, localTime);
        
        return "redirect:/admin/updateCustomPrice";
    }
	
	@RequestMapping("/updateCustomPrice")
    public String updateCustomPrice(Model model, Authentication authentication) {
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        return "redirect:/admin/admin_OnDemand";
    }
	
	// admin log
	/**
     * admin log list
     */
	@RequestMapping(value="/admin_log", method = RequestMethod.GET)
    public String adminLogList(Model model, Authentication authentication){
		UserVo userVo = (UserVo) authentication.getPrincipal();
        model.addAttribute("member", userVo);
        
        List<AwsDto> adminLogList = aService.getAdminLogList();
        model.addAttribute("adminLogList", adminLogList);
        
        return "adminHome/admin_log";
    }
}
