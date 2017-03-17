package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	/**
	 * 购物车添加商品的控制层。为了防止刷新url导致添加数量刷新增长，这里对添加业务完成后的url
	 * 进行了重定向，即添加进入购物车后，重定向到添加成功的页面。
	 * */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, 
			@RequestParam(defaultValue="1")Integer num, 
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}
	
	/**
	 * 展示购物车商品的业务层
	 * */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}

	@RequestMapping("/update/{itemId}")
	public String updateCartNum(@PathVariable Long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response, Model model) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}


}
