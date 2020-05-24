package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}

	public PurchaseDaoImpl() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);

	}

	@Override
	public Purchase findPurchase(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		Purchase purchase = sqlSession.selectOne("PurchaseMapper.getPurchase", prodNo);
		return purchase;
	}
	
	public Purchase findPurchase2(int tranNo) throws Exception{
		
		Purchase purchase = sqlSession.selectOne("PurchaseMapper.getPurchase2", tranNo);
		return purchase;
		
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("search", search);
		temp.put("buyerId", buyerId);
		
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", temp);
		
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);

	}

	@Override
	public int getTotalCount(String buyerId) throws Exception {
		// TODO Auto-generated method stub
		int totalCount = sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
		return totalCount;
	}

}
