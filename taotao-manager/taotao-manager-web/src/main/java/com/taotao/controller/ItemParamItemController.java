package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.service.ItemParamItemService;
/**
 * 展示商品规格参数
 */

@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;
	/*在item.jsp页面展示商品规格参数*/
	@RequestMapping("/showitem/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String string = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", string);
		return "item";
	}
	
	/*在item-param-add.jsp页面展示商品规格*/
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		EUDataGridResult result = itemParamItemService.getItemList(page, rows);
		return result;
	}
	
	
}
