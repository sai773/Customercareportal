package com.ashield.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ashield.pojoClasses.cdr_logs;
import java.lang.String;
import java.util.List;

public interface SubscriptionRepository  extends MongoRepository<cdr_logs,String>{
	
	List<cdr_logs> findByMsisdn(String msisdn,Sort sort);
	List<cdr_logs> findByMsisdn(String msisdn);
public  cdr_logs findBySpid(String spid);
}
