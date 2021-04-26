package com.ashield.pojoClasses;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class LoginDetails {
	
	public LoginDetails( String username, String password,String createdBy,String role,LocalDateTime createdDate,String status) {
		super();
		
		this.username = username;
		this.password = password;
		this.role=role;
		this.createdBy=createdBy;
		this.createdDate=createdDate;
		this.status=status;
		
		
	}

	public LoginDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id	
	private String username;
	private String password;
	private LocalDateTime createdDate;
	private String role;
	private String createdBy;
	private String status;

}
