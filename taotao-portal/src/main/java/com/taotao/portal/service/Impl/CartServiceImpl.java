package com.taotao.portal.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车service
 * 
 * */
@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	
/**
 * 添加购物车商品的功能：接收一个商品id，数量（默认为1），根据商品id查询商品信息。调用taotao-rest的服务。
 * 把商品添加到购物车，先把购物车商品列表取出来，判断列表中是否有此商品，如果有就增加数量就可以了。
 * 如果没有把此商品添加到购物车商品列表。返回添加成功Taotaoresult。
 * */
	@Override
	public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		//取商品信息
	    CartItem cartItem = null;
		
		//先取购物车商品列表，如果里面有需要添加的商品，则直接数量加一
		List<CartItem> CartItemList = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for (CartItem cItem : CartItemList) {
			//如果存在此商品
			if (cItem.getId() == itemId) {
				//增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		
		if (cartItem == null) {
	    cartItem = new CartItem();
		//根据商品id查询商品的基本信息
		String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
		//把json转换成pojo对象
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
		if (result.getStatus() == 200) {
			TbItem item = (TbItem) result.getData();
			cartItem.setId(item.getId());
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			//如果存在图片，取第一张
			cartItem.setImage(item.getImage() == null?"":item.getImage().split(",")[0]);
			cartItem.setNum(num);
		}
		
		//添加到购物车列表
		CartItemList.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(CartItemList), true);
		return TaotaoResult.ok();
	}

	/*
	 * 从cookie中取购物车里面的商品列表
	 * **/
	private List<CartItem> getCartItemList(HttpServletRequest request){
		//从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);//获取编码的cookie值
		if (cartJson == null) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}


}
