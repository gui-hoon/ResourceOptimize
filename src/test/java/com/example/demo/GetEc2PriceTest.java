//package com.example.demo;
// 
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.demo.dao.Ec2PriceMapper;
//import com.example.demo.dto.Ec2PriceDto;
// 
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class GetEc2PriceTest {
//
//	@Autowired
//    private Ec2PriceMapper ec2Mapper;
//	
//	public void insertEc2Price(String instanceType, double price, String vCPU, String ec2_memory, String operatingSys,
//			String ec2_storage, String networkPerformance) {
//		Ec2PriceDto ec2Price = new Ec2PriceDto();
//		ec2Price.setInstanceType(instanceType);
//		ec2Price.setPrice(price);
//		ec2Price.setVCPU(vCPU);
//		ec2Price.setEc2_memory(ec2_memory);
//		ec2Price.setOperatingSys(operatingSys);
//		ec2Price.setEc2_storage(ec2_storage);
//		ec2Price.setNetworkPerformance(networkPerformance);
//		
//		ec2Mapper.insertEc2Price(ec2Price);
//	}
//    
//    @Test
//    public void ec2PriceTest() throws ParseException {
//    	List<String> urlList = new ArrayList<>();
//		urlList.add("https://api.allorigins.win/get?url=https://b0.p.awsstatic.com/pricing/2.0/meteredUnitMaps/ec2/USD/current/ec2-ondemand-without-sec-sel/Asia%20Pacific%20(Seoul)/Windows/index.json?timestamp=1639230933739");
//		urlList.add("https://api.allorigins.win/get?url=https://b0.p.awsstatic.com/pricing/2.0/meteredUnitMaps/ec2/USD/current/ec2-ondemand-without-sec-sel/Asia%20Pacific%20(Seoul)/Linux/index.json?timestamp=1639230933739");
//		String response = "";
//		for (String targetUrl : urlList) {
//			try {
//				URL url = new URL(targetUrl);
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET"); // 전송 방식
//				conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//				conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초) 
//				conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
//				conn.setDoOutput(true);
//				
//		        System.out.println("getContentType():" + conn.getContentType()); // 응답 콘텐츠 유형 구하기
//		        System.out.println("getResponseCode():"    + conn.getResponseCode()); // 응답 코드 구하기
//		        System.out.println("getResponseMessage():" + conn.getResponseMessage()); // 응답 메시지 구하기
//	
//				String inputLine;			
//				StringBuffer sb = new StringBuffer();
//				
//				Charset charset = Charset.forName("UTF-8");
//				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
//				
//				while ((inputLine = br.readLine()) != null) {
//					sb.append(inputLine);
//				}
//				br.close();
//				
//				response = sb.toString();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			JSONObject result = (JSONObject) new JSONParser().parse(response);
//			
//			String contents = (String) result.get("contents");
//			JSONObject content = (JSONObject) new JSONParser().parse(contents);
//			
//			JSONObject regions = (JSONObject) content.get("regions");
//			JSONObject ec2List = (JSONObject) regions.get("Asia Pacific (Seoul)");
//			
//			Iterator iter =  ec2List.keySet().iterator();
//	
//			while( iter.hasNext() )
//			{
//				String key = (String)iter.next();
//				JSONObject ec2 = (JSONObject) ec2List.get(key);
//				
//				String instanceType = (String) ec2.get("Instance Type");
//				double price = (double) ec2.get("price");
//				String vCPU = (String) ec2.get("vCPU");
//				String ec2_memory = (String) ec2.get("Memory");
//				String operatingSystem = (String) ec2.get("Operating System");
//				String ec2_storage = (String) ec2.get("Storage");
//				String networkPerformance = (String) ec2.get("Network Performance");
//				
//				insertEc2Price(instanceType, price, vCPU, ec2_memory, operatingSystem, ec2_storage, networkPerformance);
//				
//				System.out.printf("instance type: %s, price: %s, vCPU: %s, memory: %s, operating system: %s, storage: %s, network performance: %s\n",
//						instanceType, price, vCPU, ec2_memory, operatingSystem, ec2_storage, networkPerformance);
//			}
//		}
//    }
//    
//    
//}
