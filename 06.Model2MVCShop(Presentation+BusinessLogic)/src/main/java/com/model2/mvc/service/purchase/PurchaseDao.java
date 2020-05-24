package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
	
	public void insertPurchase(Purchase purchase) throws Exception;
	
	public Purchase findPurchase(int prodNo) throws Exception;
	
	public Purchase findPurchase2(int tranNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public int getTotalCount(String buyerId) throws Exception;

}
