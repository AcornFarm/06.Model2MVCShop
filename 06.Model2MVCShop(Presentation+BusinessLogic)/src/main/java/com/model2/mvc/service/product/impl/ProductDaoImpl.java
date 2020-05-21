package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		//metadata에서 받아온 sqlSession injection
		this.sqlSession = sqlSession;
	}
	

	public ProductDaoImpl() {
		// TODO Auto-generated constructor stub
		System.out.println(this.getClass());
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		String manuDay = "";
		String[] srr = (product.getManuDate()).split("-");
		for(int i=0; i<srr.length; i++) {
			manuDay += srr[i];
		}
		System.out.println("ManuDay:"+manuDay);
		product.setManuDate(manuDay);
		
		
		sqlSession.insert("ProductMapper.addProduct", product);
		

	}

	@Override
	public Product findProduct(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		Product product = sqlSession.selectOne("ProductMapper.getProduct", prodNo);
		return product;
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		String manuDay = "";
		String[] srr = (product.getManuDate()).split("-");
		for(int i=0; i<srr.length; i++) {
			manuDay += srr[i];
		}
		System.out.println("ManuDay:"+manuDay);
		product.setManuDate(manuDay);
		
		sqlSession.update("ProductMapper.updateProduct", product);

	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
