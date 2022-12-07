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
//import com.example.demo.dto.AwsDto;
//import com.example.demo.service.AwsService;
//import com.example.demo.service.UserService;
// 
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class DbWebApplicationTests {
//
//	@Autowired
//    private UserService uService;
//    @Autowired
//   	private AwsService aService;
//    
////    @Test
////    public void userTest() 
////    {
////        UserDto user = new UserDto();
////        user.setUserId("test2");
////        user.setUserPw("test2");
////        user.setUserName("테스트2");
////        user.setUserGender("남");
////        user.setUserEmail("test2@test2.test");
////        uService.join(user);
////        System.out.println(uService.getUser("test2"));
////        System.out.println("login result:"+ uService.login("test2", "test2"));
////        
////    }
////    @Test
////    public void boardTest() {
////        BoardDto board = new BoardDto();
////        board.setNum(1972);
////        board.setPassword("9596");
////        board.setContent("board test");
////        board.setTitle("test");
////        bService.write(board);
////        
////        for(BoardDto b : bService.getBoardList())
////        	System.out.println(b);
////    }
////    
//    @Test
//    public void awsTest() {
//		List<AwsDto> awsResourceList=aService.getAwsResourceList();
//		for(AwsDto a : awsResourceList)
//        	System.out.println(a);
//    }
//}