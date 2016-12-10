package com.taotao.rest.controller;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/*SpringMVC的@ResponseBody注解可以将请求方法返回的对象直接转
	 * 换成JSON对象，但是当返回值是String的时候，中文会乱码，原因是因为
	 * 其中字符串转换和对象转换用的是两个转换器，而String的转换器中固定了转
	 * 换编码为"ISO-8859-1",添加@RequestMapping注解，配置produces的值
       @RequestMapping(value = " ", produces = {"application/json;charset=UTF-8"})
       * */
	@RequestMapping(value="/itemcat/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemCatList();
		//把pojo转换成json字符串
		String json = JsonUtils.objectToJson(catResult);
		//拼装返回值
		String result = callback+ "("+json+");";
		return result;
		
		
	}
}
