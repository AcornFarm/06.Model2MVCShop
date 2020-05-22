package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;



//==> ȸ������ Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method ���� ����
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception {

		System.out.println("/addPurchaseView.do");
		
		Product product = productService.getProduct(prodNo);
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("uvo", user);
		modelAndView.addObject("pvo", product);
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	
	  @RequestMapping("/addPurchase.do") 
	  public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase, @RequestParam("buyerId") String buyerId,
																					@RequestParam("prodNo") int prodNo) throws Exception{
	  
	  System.out.println("/addPurchase.do"); //Business Logic
	  
	  User user = new User();
	  user.setUserId(buyerId);
	  
	  Product product = new Product();
	  product.setProdNo(prodNo);
	  
	  purchase.setBuyer(user);
	  purchase.setPurchaseProd(product);
	  purchaseService.addPurchase(purchase);
	  
	  System.out.println("::"+purchase);
	  
	  Purchase puvo = purchaseService.getPurchase(purchase.getTranNo());
	  
	  return new ModelAndView("forward:/product/addProduct.jsp", "puvo", puvo); 
	  
	  }
	 
	
	
	  @RequestMapping(value="/getPurchase.do", method=RequestMethod.GET) 
	  public ModelAndView getPurchase( @RequestParam("tranNo") int tranNo ) throws Exception {
	  
	  System.out.println("/getPurchase.do"); //Business Logic Product product =
	  
	  Purchase purchase = purchaseService.getPurchase(tranNo);
	  String viewName="forward:/purchase/getPurchase.jsp";
	  
	  return new ModelAndView(viewName, "puvo", purchase); 
	  
	  }
	  
	  
	
	  @RequestMapping("/updatePurchaseView.do") 
	  public ModelAndView updatePurchaseView( @RequestParam("tranNo") int tranNo ) throws Exception{
	  
	  System.out.println("/updatePurchaseView.do"); //Business Logic Product product
	  Purchase purchase = purchaseService.getPurchase(tranNo); // Model �� View ����
	  
	  return  new ModelAndView("forward:/purchase/updatePurchaseView.jsp", "puvo", purchase);
	  
	  }
	  
	  
	  @RequestMapping("/updatePurchase.do") 
	  public ModelAndView updatePurchase( @ModelAttribute("purchase") Purchase purchase , @RequestParam("buyerId") String buyerId
			  													) throws Exception{
	  
	  System.out.println("/updatePurchase.do"); //Business Logic
	  User user = new User();
	  user.setUserId(buyerId);
	  
	  purchase.setBuyer(user);
	  System.out.println(":::::"+purchase);
	 
	  purchaseService.updatePurcahse(purchase);
	  Purchase puvo = purchaseService.getPurchase(purchase.getTranNo());
	  
	  return new ModelAndView("forward:/purchase/getPurchase.jsp", "puvo", puvo); 
	  
	  }
	 
	  
	  
	
	  @RequestMapping("/listPurchase.do") 
	  public ModelAndView listPurchase( @ModelAttribute("search") Search search, 
	  																	HttpSession session) throws Exception{
	  
	  System.out.println("/listPurchase.do");
	  
	  if(search.getCurrentPage() ==0 ){ search.setCurrentPage(1); }
	  search.setPageSize(pageSize);
	  
	  // Business logic ���� Map<String , Object>
	  String buyerId = ((User)session.getAttribute("user")).getUserId();
	  Map<String, Object> map = new HashMap<String, Object>();
	  map=purchaseService.getPurchaseList(search, buyerId);
	  
	  Page resultPage = new Page( search.getCurrentPage(),
	  ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	  System.out.println(resultPage);
	  
	  ModelAndView modelAndView = new ModelAndView();
	  modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
	  modelAndView.addObject("resultPage", resultPage); 
	  modelAndView.addObject("list", map.get("list"));
	  modelAndView.addObject("search",search);
	  
	  return modelAndView; 
	  }
	 
	  
	 
	 
}