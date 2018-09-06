package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {
	/**
	 * 查询数据库并且导入到索引库中
	 * @return
	 */
	public TaotaoResult importAllItems();
}
