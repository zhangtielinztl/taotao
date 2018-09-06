package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	/**
	 * 根据ID查询分类信息
	 * @param
	 * @return查询分类类目返回json集合数据 里面包含了id，text，state
	 */
List<EasyUITreeNode> getCatList(long parentId);
}
