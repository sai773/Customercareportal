package com.ashield.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ashield.pojoClasses.LoginDetails;

import java.lang.String;



public interface loginRepository extends  MongoRepository<LoginDetails, String>{

	public LoginDetails findByUsernameAndPassword(String username,String Password);
	public LoginDetails findByUsername(String username);
	List<LoginDetails> findByStatus(String status);
	
}
