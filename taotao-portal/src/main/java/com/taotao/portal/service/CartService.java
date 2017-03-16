package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;

public interface CartService {
	
	TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

}
