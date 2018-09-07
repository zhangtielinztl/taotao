package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.taotao.common.pojo.SearchResult;

import javax.annotation.Resource;


@Controller
public class SearchController {

	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@Resource
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			@RequestParam(defaultValue="1")Integer page, Model model) {
		try {
			byte[] bytes = queryString.getBytes("iso-8859-1");
			queryString = new String(bytes,"utf-8");
			//商品集合，总记录条数 总页数 
			SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
			model.addAttribute("query", queryString);
			model.addAttribute("totalPages", result.getPageCount());
			System.out.println(result.getPageCount());
			model.addAttribute("itemList", result.getItemList());
			model.addAttribute("page", page);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
	}

}
