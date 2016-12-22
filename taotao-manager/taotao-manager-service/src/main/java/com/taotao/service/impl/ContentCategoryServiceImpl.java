package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		
		//根据parentid查询节点列表
	    TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setParentId(tbContentCategory.getParentId());
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}

		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		System.out.println("insert之前，id的值是："+contentCategory.getId());
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		System.out.println("insert之后，id的值是："+contentCategory.getId());
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		
		return TaotaoResult.ok(contentCategory);

		
	}

	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		
		//删除节点的子节点（如果有的话）。查询所有父节点为id数据、因为如果删除的是父节点，则子节点也要被删除
	    TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list.size()>0){
		for (TbContentCategory tbContentCategory : list) {
		contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
		 }
		}
		//删除节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		//根据parentId查询节点列表
	    TbContentCategoryExample example1 = new TbContentCategoryExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list1 = contentCategoryMapper.selectByExample(example1);
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(list1.size() <= 0) {
			contentCategory.setIsParent(false);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		System.out.println(name+" 设置成功！");
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}

}
