package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;



public class SolrJTest {
	
	
	public void addDocument() throws Exception{
		
		//创建一个连接（单机版）
		SolrServer solrServer = new HttpSolrServer("http://192.168.192.130:8080/solr");
		//创建一个文档对象
		SolrInputDocument inputDocument = new SolrInputDocument();
		inputDocument.addField("id", "test002");
		inputDocument.addField("item_title", "");
		inputDocument.addField("item_price", 0);
		//把文档对象写进索引库
		solrServer.add(inputDocument);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.192.130:8080/solr");
		//solrServer.deleteById("test002");//根据id删除，也可创建一个list，存储id号，进行批量删除
		solrServer.deleteByQuery("*:*");//删除所有
		solrServer.commit();
	}

}
