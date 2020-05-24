package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao=purchaseDao;
	}

	public PurchaseServiceImpl() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDao.insertPurchase(purchase);

	}

	@Override
	public Purchase getPurchase(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		Purchase purchase = purchaseDao.findPurchase(prodNo);
		return purchase;
	}
	
	public Purchase getPurchase2(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		Purchase purchase = purchaseDao.findPurchase2(tranNo);
		return purchase;
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub

		List<Purchase> list= purchaseDao.getPurchaseList(search, buyerId);
		int totalCount = purchaseDao.getTotalCount(buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		purchaseDao.updateTranCode(purchase);

	}

}
