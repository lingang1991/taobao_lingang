package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;


//搜索service
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		//设置默认搜素域
		query.set("df", "item_keywords");
		//设置高亮显示
		query.setHighlight(true);//先开启高亮显示true，然后根据设置需要高亮显示的字段名
		query.addHighlightField("item_title");// 高亮字段  
		query.setHighlightSimplePre("<em style=\"color:red\">");//标记，高亮关键字前缀 
		query.setHighlightSimplePost("</em>");//后缀  
		//执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算查询结果总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
				pageCount++;
			}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		return searchResult;

	}

}
