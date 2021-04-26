package com.ashield.RobiCustomerCarePortal;

import java.time.LocalDateTime;

import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ashield.logs.Logging;

@Controller
public class MVCController {
	
	@RequestMapping("/login")
	public String loginpage() {
		int random = (int)(Math.random() * 9999 + 1);
		int RobiId=random;
		MDC.put("Robi-UNIQUE-ID", RobiId);
		Logging.getLogger().info("CC GUI Request Recived for User Login :"+RobiId+ ",Date and Time : "+LocalDateTime.now() );

		return "login";
	}
	
	@RequestMapping("/admin")
	public String adminpage() {
		int random = (int)(Math.random() * 9999 + 1);
		int RobiId=random;
		MDC.put("Robi-UNIQUE-ID", RobiId);
		Logging.getLogger().info("CC GUI Request Recived for Admin Login :"+RobiId+",Date and Time : "+LocalDateTime.now());

		return "admin";
	}
	
	

}
