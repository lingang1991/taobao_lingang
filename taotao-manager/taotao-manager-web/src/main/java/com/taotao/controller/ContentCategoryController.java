package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
		
		
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {
		TaotaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}
	

	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name) {
		TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	
	

}
