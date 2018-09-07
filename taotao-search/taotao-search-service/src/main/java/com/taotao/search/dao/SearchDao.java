package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;


@Repository
public class SearchDao {
	/**
	 * 根据业务层传入的条件查询文档库
	 * 
	 * @param query
	 *            查询条件
	 * @return 商品结果集，总记录条数，总页数
	 */
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private  SearchItemMapper searchItemMapper;

	public SearchResult search(SolrQuery query) {
		
		try {
			QueryResponse queryResponse = solrServer.query(query);

			SolrDocumentList documentList = queryResponse.getResults();
			
			List<SearchItem> list = new ArrayList<SearchItem>();
			for (SolrDocument solrDocument : documentList) {
				SearchItem item = new SearchItem();
				item.setId((String) solrDocument.get("id"));
				item.setCategory_name((String) solrDocument.get("item_category_name"));
				item.setImage((String) solrDocument.get("item_image"));
				item.setPrice((long) solrDocument.get("item_price"));
				item.setSellPoint((String) solrDocument.get("item_sell_point"));
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				List<String> list2= highlighting.get(solrDocument.get("id")).get("item_title");
				String itemTitle = "";
				//有高亮显示的内容时。
				if (list2 != null && list2.size() > 0) {
					itemTitle = list2.get(0);
				} else {
					itemTitle = (String) solrDocument.get("item_title");
				}
				item.setTitle(itemTitle);
				//添加到商品列表
				list.add(item);
			}
			SearchResult result = new SearchResult();
			result.setRecordCount(documentList.getNumFound());
		
			result.setItemList(list);
			//总记录数
		
			
			return result;
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}