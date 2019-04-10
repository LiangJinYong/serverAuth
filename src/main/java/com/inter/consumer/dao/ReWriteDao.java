package com.inter.consumer.dao;

public interface ReWriteDao {

	Integer getOrderNumberBySequence(Long sequence);

	String queryHomepageAddr(Integer orderNumber);

}
