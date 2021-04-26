package com.ashield.RobiCustomerCarePortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoAdminOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import com.ashield.helper.CommonHelper;
import com.ashield.logs.ErrorLogger;
import com.ashield.logs.Logging;
import com.ashield.pojoClasses.LoginDetails;
import com.ashield.pojoClasses.cdr_logs;
import com.ashield.repository.SubscriptionRepository;
import com.ashield.repository.loginRepository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;


@RestController
public class PurchaseController {

	@Autowired
	private loginRepository repository;
	@Autowired
	private SubscriptionRepository subrepository;

	@Autowired
	MongoOperations mongoOperations;

	


	//   --- user login page , user name and password authentication ---

	@RequestMapping(value = "/adminLoginForm")
	public @ResponseBody void getCCGuiLoginDetails(@RequestParam(value="username") String usr,
			@RequestParam(value="password") String pwd,   
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		LoginDetails loginValues = null;

		try {
			int random = (int)(Math.random() * 9999 + 1);
			String RobiId=usr+random;
			MDC.put("Robi-UNIQUE-ID", RobiId);

				Logging.getLogger().info("CC GUI Login request for User --> "+usr+", pwd : "+pwd);
			RequestDispatcher rd;
			
				loginValues = repository.findByUsernameAndPassword(usr, pwd);
			if(loginValues!=null && !loginValues.getStatus().contentEquals("Deactive")){
				if(!loginValues.getStatus().contentEquals("Inactive")) {
					
			
			// removing the invalid attempt session val		
				session.removeAttribute(usr);
				
			//generating random id for login values to store in cookies
				String randomNumber = UUID.randomUUID().toString();
				
				Cookie cookie = new Cookie("Ashield",randomNumber);
				cookie.setMaxAge(3000); 
				cookie.setSecure(true);
				cookie.setPath("/RobiCC"); 
				cookie.setHttpOnly(true);
				//cookie.setSameSite("strict");
				response.addCookie(cookie);
				//getting the cookie values
		
				String cookieval=cookie.getValue();
				
				//generating the new crypthographic value using sha-512
				String generatedPassword = null;
				String val="AshieldRobiContessa@2020**";
				byte[] salt=val.getBytes();
				
				
				 MessageDigest md = MessageDigest.getInstance("SHA-512");
	                md.update(salt);
	                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
	               
	                StringBuilder sb = new StringBuilder();
	                for (int i = 0; i < bytes.length; i++) {
	                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	                }
	                generatedPassword = sb.toString();
	             session.setAttribute(usr,generatedPassword);
	              Logging.getLogger().info("CC GUI Loginuser name or password is correct : "+usr+", pwd : "+pwd);

				session.setAttribute("uname", usr);
                session.setMaxInactiveInterval(900);

				rd = request.getRequestDispatcher("/WEB-INF/jsp/cc.jsp");
				
				}else {
					 Logging.getLogger().info("CC GUI Login username or password is invalid exceeded the attempts--> username: "+usr+", pwd : "+pwd);
						request.setAttribute("errorMessage","invalid details contact admin");
						rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		}
			
			}else {
					
				 int loginAttempt;
				 if (session.getAttribute(usr) == null)
		            {
					 loginAttempt = 0;
		                session.setAttribute(usr,loginAttempt);
		               
		                }
		            else
		            {
		                loginAttempt=(Integer)session.getAttribute(usr);
		                 loginAttempt++;
							session.setAttribute(usr,loginAttempt);
		                 
		            }
				
		            if(loginAttempt>=2 ) {
		            	 loginValues = repository.findByUsername(usr);
		            	if(loginValues!=null && !loginValues.getStatus().contentEquals("Inactive")) {
		            			mongoOperations.updateFirst(query(where("username").is(usr)), update("status", "Inactive"), LoginDetails.class);  
		            			
		            			}else {
		   		            	 
		    						Logging.getLogger().info("CC GUI username not exist and incorrect attempt:"+loginAttempt+" username: "+usr+", pwd : "+pwd);
		            				
		            			}
		        	 }
		            else {
		            	
						Logging.getLogger().info("CC GUI Login username or password  incorrect attempt:"+loginAttempt+" username: "+usr+", pwd : "+pwd);
				 }
		          // loginValues = repository.findByUsername(usr);
		            if(loginAttempt>=3) {
		            	Logging.getLogger().info("CC GUI Login username or password is invalid exceeded the attempts--> username: "+usr+", pwd : "+pwd);
						request.setAttribute("errorMessage","invalid details contact admin");
						rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		           
				
				
				}else {
					
					 Logging.getLogger().info("CC GUI Login username or password is incorrect--> username: "+usr+", pwd : "+pwd);
						request.setAttribute("errorMessage","Username or Password is wrong");
						rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");

				}
					 
		} 
		 
			
			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminLoginForm : ",e);
		}	finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}

	
	// --   Admin page  username and password authentication functionality

	@RequestMapping(value = "/adminDetailsForm")
	public @ResponseBody void getCCGuiAdminDetails(@RequestParam(value="username") String usr,
			@RequestParam(value="password") String pwd,   
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		LoginDetails loginValues = null;
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		
		try {
			int random = (int)(Math.random() * 9999 + 1);
			String RobiId=usr+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);

			Logging.getLogger().info("CC GUI Admin Login request Details--> user : "+usr+", pwd : "+pwd);
			
			RequestDispatcher rd;
			
			loginValues = repository.findByUsernameAndPassword(usr, pwd);
			Logging.getLogger().info("CC GUI Admin: user LoginDetails colection is exist : "+usr+", pwd : "+pwd);


			
			if(loginValues!=null && loginValues.getRole().contentEquals("AU")){
				
				if(!loginValues.getStatus().contentEquals("Inactive")) {

				Logging.getLogger().info("CC GUI Admin request for User is correct: "+usr+", pwd : "+pwd);
				session.removeAttribute(usr);
				
				//generating random id for login values to store in cookies
					String randomNumber = UUID.randomUUID().toString();
					
					Cookie cookie = new Cookie("Ashield",randomNumber);
					cookie.setMaxAge(3000); 
					cookie.setSecure(true);
					cookie.setPath("/RobiCC"); 
					response.addCookie(cookie);
					cookie.setHttpOnly(true);

					//getting the cookie values
					
					String cookieval=cookie.getValue();
					
					//generating the new crypthographic value using sha-512
					String generatedPassword = null;
					String val="AshieldRobiContessa@2020**";
					byte[] salt=val.getBytes();
					
					
					 MessageDigest md = MessageDigest.getInstance("SHA-512");
		                md.update(salt);
		                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
		               
		                StringBuilder sb = new StringBuilder();
		                for (int i = 0; i < bytes.length; i++) {
		                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		                }
		                generatedPassword = sb.toString();
		                
		                session.setAttribute(usr,generatedPassword);
		         	
	                
				session.setAttribute("uname", usr); 
                session.setMaxInactiveInterval(900);

				List<LoginDetails> loginList=repository.findByStatus("Active");
				Logging.getLogger().info("CC GUI Active Users List: "+loginList);

				request.setAttribute("tableData", loginList);
				rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");
				}else {
					Logging.getLogger().info("CC GUI Login username or password is invalid exceeded the attempts--> username: "+usr+", pwd : "+pwd);
					request.setAttribute("errorMessage","invalid details contact admin");
					rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");

				}

			}else{
				
				
				 int loginAttempt;
				 if (session.getAttribute(usr) == null)
		            {
		            	 loginAttempt = 0;
		                session.setAttribute(usr,loginAttempt);
		               
		                }
		            else
		            {
		                 loginAttempt = (Integer) session.getAttribute(usr);
		                 loginAttempt++;
							session.setAttribute(usr,loginAttempt);
		                 
		            }
				
		            if(loginAttempt>=2 ) {
		            	 loginValues = repository.findByUsername(usr);
		            	if(loginValues!=null && !loginValues.getStatus().contentEquals("Inactive")) {
		            			mongoOperations.updateFirst(query(where("username").is(usr)), update("status", "Inactive"), LoginDetails.class);  
		            	}else {
		    						Logging.getLogger().info("CC GUI username not exist and incorrect attempt:"+loginAttempt+" username: "+usr+", pwd : "+pwd);

		            			}
		        	 }
		            else {
		            	
						Logging.getLogger().info("CC GUI Login username or password  incorrect attempt:"+loginAttempt+" username: "+usr+", pwd : "+pwd);
				 }
		            if(loginAttempt>=3 ) {
		          
		            	Logging.getLogger().info("CC GUI Login username or password is invalid exceeded the attempts--> username: "+usr+", pwd : "+pwd);
						request.setAttribute("errorMessage","invalid details contact admin");
						rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		            	
			}else {
				Logging.getLogger().info("CC GUI Admin Login request incorrect Details : "+usr+", pwd : "+pwd);
				request.setAttribute("errorMessage","Please Enter correct username  password!");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");

			}
			}
			
			

			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminDetailsForm : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}


	
	
	//-- Admin page: add new user to customer care portal service functionality
	@RequestMapping(value = "/adminNewUser")
	public @ResponseBody void getAdminDetailValidations(@RequestParam(value="username") String usr,
			@RequestParam(value="password") String pwd,  @RequestParam(value="conform_password") String cpwd,@CookieValue(name="Ashield", defaultValue = "unknown")String cookieval
,			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		LoginDetails loginValues = null;

		try {
			int random = (int)(Math.random() * 9999 + 1);
            String usr1=(String)session.getAttribute("uname");

			String RobiId=usr1+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);
			
			
			Logging.getLogger().info("CC GUI admin new User request Details --->  username: "+usr+", password : "+pwd+",conformpassword:"+cpwd);

			String generatedPassword = null;
			String val="AshieldRobiContessa@2020**";
			byte[] salt=val.getBytes();
			
			
			 MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
               
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
                RequestDispatcher rd;
                String usr2=(String)session.getAttribute(usr1);
                session.setMaxInactiveInterval(900);


                if(usr2!=null&&usr1!=null&&usr!=null&&generatedPassword!=null&&generatedPassword.contentEquals(usr2)) {
		
			if( usr!=null && !usr.contentEquals("") && !pwd.contentEquals("") && !cpwd.contentEquals("")&& pwd.equalsIgnoreCase(cpwd)  && pwd!=null & cpwd!=null){

				LoginDetails exists = repository.findByUsername(usr);
				if(exists==null) {
					LoginDetails login=new LoginDetails();
					login.setUsername(usr);
					login.setPassword(pwd);
					login.setCreatedBy("robiashield");
					login.setRole("user");
					login.setStatus("Active");
					login.setCreatedDate(LocalDateTime.now());
					repository.save(login);
					Logging.getLogger().info("CC GUI Admin new user created time:"+LocalDateTime.now());

					Logging.getLogger().info("CC GUI Admin new user details-->  username: "+usr+", password : "+pwd+",conform password:"+cpwd+
							", Created By: admin , status: Active, Role: user, created Date:"+LocalDateTime.now());

					List<LoginDetails> loginList=repository.findByStatus("Active");
					request.setAttribute("tableData", loginList);
					rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");

				}
				else{
					Logging.getLogger().info("CC GUI Username is already exist: "+usr);

					request.setAttribute("errorMessage","Username is already exist!");
					rd = request.getRequestDispatcher("/WEB-INF/jsp/newuser.jsp");
				}
			}
			else {
				Logging.getLogger().info("CC GUI New user incomplete Fields");

				request.setAttribute("errorMessage","Please compete all fields");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/newuser.jsp");
			}
			}
			else {
				Logging.getLogger().info("CC GUI New user session timeout");

				request.setAttribute("errorMessage","Session Timeout please re-login");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				
			}
			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminNewUser : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}

	// User disable and Edit password

	@RequestMapping(value = "/adminEditForm")
	public @ResponseBody void getadminEditValidations(@CookieValue(name="Ashield", defaultValue = "unknown")String cookieval,@RequestParam(value="username") String usr,@RequestParam(value="Edit") String epass,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		LoginDetails loginData = null;

		try {
			int random = (int)(Math.random() * 9999 + 1);
			String usr1=(String)session.getAttribute("uname");
			String RobiId=usr1+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);

			Logging.getLogger().info("CC GUI Admin request for User deactivation and Edit Passowrd Details  usr-->: "+usr+", Edit param : "+epass);

			loginData=repository.findByUsername(usr);
			String generatedPassword = null;
			String val="AshieldRobiContessa@2020**";
			byte[] salt=val.getBytes();
			
			
			 MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
               
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
                RequestDispatcher rd;
                String usr2=(String)session.getAttribute(usr1);
                session.setMaxInactiveInterval(900);

                
                if(usr2!=null&&usr1!=null&&usr!=null&&generatedPassword!=null&&generatedPassword.contentEquals(usr2)) {
			if(usr!=null && !usr.contentEquals("") && epass.contentEquals("Disable User")) {

				Logging.getLogger().info("CC GUI Admin request for User deactivation   usr--> : "+usr+", status : Deactivate");

				if(loginData!=null && loginData.getStatus().contentEquals("Active") && !loginData.getStatus().contentEquals("AU") ){
					Logging.getLogger().info("CC GUI Admin request for updating details in LoginDetails collection   usr--> : "+usr+", status : Deactivate");

					mongoOperations.updateFirst(query(where("username").is(usr)), update("status", "Deactive"), LoginDetails.class);  
					Logging.getLogger().info("CC GUI Admin response for updating details in LoginDetails collection   usr--> : "+usr+", status : Deactivate");

					List<LoginDetails> loginList=repository.findByStatus("Active");
					request.setAttribute("tableData", loginList);
					rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");

				}else{

					Logging.getLogger().info("CC GUI User already deactivated");

					List<LoginDetails> loginList=repository.findByStatus("Active");
					request.setAttribute("tableData", loginList);
					request.setAttribute("errorMessage","User already Deactivated");
					rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");
				}
			}
			else {
				Logging.getLogger().info("CC GUI Admin request for User Pasword Details -->   usr : "+usr+", Edit Param :"+epass);
				request.setAttribute("username", loginData.getUsername());
				rd = request.getRequestDispatcher("/WEB-INF/jsp/adminhome.jsp");

			}
			}
			else {
				Logging.getLogger().info("CC GUI User already deactivated");
				List<LoginDetails> loginList=repository.findByStatus("Active");
				request.setAttribute("tableData", loginList);
				request.setAttribute("errorMessage","Session Timeout please re-login");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
		
			}
			
			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminLoginForm : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
		
	}


	//----Admin: user password Edit functionality

	@RequestMapping(value = "/adminPassForm")
	public @ResponseBody void getadminEditDetails(@CookieValue(name="Ashield", defaultValue = "unknown")String cookieval
			,@RequestParam(value="username") String usr,
			@RequestParam(value="password") String pwd,  @RequestParam(value="conform_password") String cpwd,

			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		LoginDetails loginData = null;

		try {
			int random = (int)(Math.random() * 9999 + 1);
			String usr1=(String)session.getAttribute("uname");
			String RobiId=usr1+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);
			
			String generatedPassword = null;
			String val="AshieldRobiContessa@2020**";
			byte[] salt=val.getBytes();
			
			
			 MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
               
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
                RequestDispatcher rd;
                String usr2=(String)session.getAttribute(usr1);
                session.setMaxInactiveInterval(900);

                
                if(usr2!=null&&usr!=null&&usr1!=null&&generatedPassword!=null&&generatedPassword.contentEquals(usr2)) {
                	Logging.getLogger().info("CC GUI Admin request for Edit password Details-->  username: "+usr+", password : "+pwd+",conformpassword:"+cpwd);

			loginData=repository.findByUsername(usr);
				
				if(loginData!=null && !usr.contentEquals("") && !pwd.contentEquals("") && !cpwd.contentEquals("") && usr!=null  && pwd!=null & cpwd!=null && pwd.equalsIgnoreCase(cpwd) ){
						Logging.getLogger().info("CC GUI Admin request for Update new password LoginDetails Collection--> usr"+usr+", password :" +pwd);

						mongoOperations.updateFirst(query(where("username").is(usr)), update("password", pwd), LoginDetails.class);  
						Logging.getLogger().info("CC GUI LoginDetails Collection Response Succesfully updated password for username: "+usr);

						List<LoginDetails> loginList=repository.findByStatus("Active");
						request.setAttribute("tableData", loginList);
						rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");
				}
				else {
					Logging.getLogger().info("CC GUI User Incorrect Password or incomplete fields");

					request.setAttribute("errorMessage","Please enetr the correct password!");
					rd = request.getRequestDispatcher("/WEB-INF/jsp/adminhome.jsp");
			}
			}
			else {
				Logging.getLogger().info("CC GUI User Incorrect Password or incomplete fields");

				request.setAttribute("errorMessage","session Timeout please re-login!");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
			}
			

			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminPassForm : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}


	// admin form back button service functionality

	@RequestMapping(value = "/adminBackBtn")
	public @ResponseBody void getadminBackbtn(@RequestParam(value="btnname") String usr,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		LoginDetails loginData = null;

		response.setHeader("X-FRAME-OPTIONS", "DENY");
		try {
			int random = (int)(Math.random() * 9999 + 1);
			String usr1=(String)session.getAttribute("uname");
			String RobiId=usr1+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);
				
			RequestDispatcher rd;
			String usr2=(String)session.getAttribute(usr1);
			if(usr2!=null &&usr1!=null) {
            session.setMaxInactiveInterval(900);
			if(usr!=null && usr.contentEquals("Back")){
				
				Logging.getLogger().info("CC GUI In complete form Details User Back Button : "+usr);
				List<LoginDetails> loginList=repository.findByStatus("Active");
				request.setAttribute("tableData", loginList);
				rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");	

			}
			else {
				Logging.getLogger().info("CC GUI invalid username Back Button : "+usr);
				request.setAttribute("errorMessage","Error");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");
			}
			}
			else
			{
				Logging.getLogger().info("CC GUI session timedout back Button : "+usr);
				request.setAttribute("errorMessage","Session Timeout please re-login");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				
			}
			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminBackBtn : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}

	
	
	
	
	
	// admin newuser functionality

	@RequestMapping(value = "/adminUsrAdd")
	public @ResponseBody void getadminnewuser(@CookieValue(name="Ashield", defaultValue = "unknown")String cookieval,@RequestParam(value="adduser") String btn,
				
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException { 
		LoginDetails loginData = null;
		//String loginvalues = null;
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		try {

			int random = (int)(Math.random() * 9999 + 1);
			String usr=(String)session.getAttribute("uname");
			
			String RobiId=usr+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);

			String generatedPassword = null;
			String val="AshieldRobiContessa@2020**";
			byte[] salt=val.getBytes();
			
			
			 MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
               
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
                RequestDispatcher rd;
               
                String usr2=(String)session.getAttribute(usr);
                
                session.setMaxInactiveInterval(900);

                if(usr2!=null&&generatedPassword!=null&&generatedPassword.contentEquals(usr2)) {
                	if( btn!=null && !btn.contentEquals("")){
				rd = request.getRequestDispatcher("/WEB-INF/jsp/newuser.jsp");	
			}
			else {
				Logging.getLogger().info("CC GUI Login request for new  User---> invalid username: "+usr);
				List<LoginDetails> loginList=repository.findByStatus("Active");
				request.setAttribute("tableData", loginList);
				request.setAttribute("errorMessage","Error");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/EditAdmin.jsp");
			}
			}
			else {
				
				Logging.getLogger().info("CC GUI User already deactivated");
				List<LoginDetails> loginList=repository.findByStatus("Active");
				request.setAttribute("tableData", loginList);
				request.setAttribute("errorMessage","session Timeout please re-login");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
				
			}
			rd.forward(request, response);
		} catch (Exception e) {
			ErrorLogger.getLogger().error("Exception in adminUsrAdd : ",e);
		}finally{
			MDC.remove("Robi-UNIQUE-ID");
		}
	}

	// Msisdn Validation Details with CDR Data In MongoDB Subscription table

	
/*	@RequestMapping(value = "/MSISDNValidate")
	public @ResponseBody void getCCGuiMsisdnValidationDetails(@RequestParam(value="msisdn") String mobileNumber,
			HttpServletRequest request, HttpServletResponse response,HttpSession session ) throws IOException { 
		response.setHeader("X-FRAME-OPTIONS", "DENY");
		PrintWriter out = response.getWriter();
		try{
		

			int random = (int)(Math.random() * 9999 + 1);
			String userid = (String) session.getAttribute("uname");
			String RobiId=userid+random;
			MDC.put("Robi-UNIQUE-ID",RobiId);
			String number="88"+mobileNumber;
			Logging.getLogger().info(" User Request MSISDN  - "+number);
			List<cdr_logs> dbResp = subrepository.findByMsisdn(number);
			//dbResp.
		
			if(dbResp!=null && dbResp.size()>0){
			Logging.getLogger().info("CC-Requested RobiMsisdnDataReports response "
				+ "from SubscriptionDetails : "+dbResp.get(0).getAshieldid()+","+dbResp.get(0).getMsisdn());

				Logging.getLogger().info("MSISDN Available - "+number);

				response.getWriter().print(true);
			}else{
				Logging.getLogger().info("MSISDN Not Available - "+number);
				response.getWriter().print(false);
			}
			
		}catch(Exception e){

			ErrorLogger.getLogger().error("Exception in MSISDNValidate : ",e);
		}finally{
			out.close();
			MDC.remove("Robi-UNIQUE-ID");
		}
	}
	*/

	//CCGUI Data Extraction API
		@RequestMapping(value = "/RobiMsisdnDataReports")
		public @ResponseBody void getDeactivationDataToDashboard(@CookieValue(name="Ashield", defaultValue = "unknown")String cookieval,@RequestParam(value="msisdn") String mobileNumber, 
				HttpServletRequest request, HttpServletResponse response,HttpSession session  ) throws IOException { 
			PrintWriter out = response.getWriter();
			response.setHeader("X-FRAME-OPTIONS", "DENY");
			RequestDispatcher rd;

			try{
				int random = (int)(Math.random() * 9999 + 1);
				String usr=(String)session.getAttribute("uname");
				String RobiId=usr+random;
				MDC.put("Robi-UNIQUE-ID",RobiId);
				String number="88"+mobileNumber;
			if(number.length()==13) {
				
				String generatedPassword = null;
				String val="AshieldRobiContessa@2020**";
				byte[] salt=val.getBytes();
				
				
				 MessageDigest md = MessageDigest.getInstance("SHA-512");
	                md.update(salt);
	                byte[] bytes = md.digest(cookieval.getBytes(StandardCharsets.UTF_8));
	               
	                StringBuilder sb = new StringBuilder();
	                for (int i = 0; i < bytes.length; i++) {
	                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	                }
	                generatedPassword = sb.toString();
	               
	                String usr2=(String)session.getAttribute(usr);
	                session.setMaxInactiveInterval(900);

	                
	                if(usr2!=null&&usr!=null&&generatedPassword!=null&&generatedPassword.contentEquals(usr2)) {

	                	Logging.getLogger().info("CC-Requested RobiMsisdnDataReports, Msisdn : "+number);

	                	Logging.getLogger().info("CC-Requested RobiMsisdnDataReports from, Msisdn, SubscriptionDetails : "+number);

	                 	List<cdr_logs> tableDetailsList = subrepository.findByMsisdn(number,Sort.by(Sort.Direction.DESC,"Request Date time"));
	                 	
	                 	if(tableDetailsList!=null&& tableDetailsList.size()>0) {
	                	Logging.getLogger().info("Response MSISDN Data List Size --> "+tableDetailsList.size());

	                	Logging.getLogger().info("Response MSISDN Data --> "+tableDetailsList);
	                	String msisdn1=tableDetailsList.get(0).getMsisdn();
	                	
	                	
	                	request.setAttribute("msisdn",msisdn1 );
	                	
	                	request.setAttribute("tableData", tableDetailsList);


	                	rd = request.getRequestDispatcher("/WEB-INF/jsp/responseMsisdnDetails.jsp");
	                 	}
	                 	else {
	        				request.setAttribute("errorMessage","No information available for this msisdn");

	                    	rd = request.getRequestDispatcher("/WEB-INF/jsp/cc.jsp");

	                 		
	                 	}
	                	
	                }
	                else {
	                	Logging.getLogger().info("User Session Timeout");
	    				request.setAttribute("errorMessage","Session Timeout Please re-login");
	    				rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	        		
	                }
			}else {
				Logging.getLogger().info("inivalid number");
				request.setAttribute("errorMessage","please enter 11 digit numbers");
				rd = request.getRequestDispatcher("/WEB-INF/jsp/cc.jsp");
			
				
			}
			
				
				rd.forward(request, response);
			}catch (Exception e){  

				ErrorLogger.getLogger().error("Exception in RobiMsisdnDataReports : ", e);			
			}finally{
				out.close();
				MDC.remove("Robi-UNIQUE-ID");
			}
		}

		
		
		
		
		
		
		
		
		
	}