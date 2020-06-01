package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



//==> 회원관리 Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "forward:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product, Model model, MultipartHttpServletRequest request) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		
		
		Map<String, MultipartFile> files = request.getFileMap();
		CommonsMultipartFile cmf  = (CommonsMultipartFile) files.get("uploadFile");
		
		String path = "C:\\Users\\user\\git\\06.Model2MVCShop\\06.Model2MVCShop(Presentation+BusinessLogic)\\WebContent\\images\\uploadFiles\\"
		+cmf.getOriginalFilename();
		
		File f = new File(path);
		
		cmf.transferTo(f);
		
		product.setFileName(cmf.getOriginalFilename());
		
		System.out.println("실제로 들어가는 product:::"+product);
		productService.addProduct(product);
		model.addAttribute("pvo", product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value="/getProduct.do", method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") int prodNo , @RequestParam(value="menu", required = false) String menu, 
														Model model, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("pvo", product);
		
		cookieSave(request, response);
		
		String forward = "forward:/product/getProduct.jsp";
		
		if(menu.equals("manage")) {
			forward = "forward:/product/updateProductView.jsp";
		}
		
		return forward;
	}
	
	@RequestMapping(value="/getProduct.do", method=RequestMethod.POST)
	public String getProduct( @RequestParam("prodNo") int prodNo, Model model, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("pvo", product);
		
		cookieSave(request, response);
		
		return  "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("pvo", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateUser( @ModelAttribute("pvo") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	
	@RequestMapping("/listProduct.do")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	private void cookieSave(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		String history = "";
		
			
			for(Cookie cookie : cookies) {
				
				
				if(cookie.getName().equals("history")) {
					
					if(cookie.getValue().contains(request.getParameter("prodNo"))){
					System.out.println("여기 실행");
					 history = cookie.getValue();
					 
					}else if(cookie.getValue().contains("")) {
						
						history += cookie.getValue();	
					}
					
					
				}else {
					System.out.println("여기 실행~~~");
					history += request.getParameter("prodNo")+",";
				}
				
				cookie= new Cookie("history", history);
				response.addCookie(cookie);
			}
			
			System.out.println(history);
	}
}