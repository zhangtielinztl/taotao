package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	/**
	 * 查询内容分类
	 * @param parentId 父级目录Id
	 * @return 当前父级目录的tree数据 包换了id，text，status
	 */
	 List<EasyUITreeNode> getContentCategoryList(long parentId);
/**
 * 添加一个内容分类信息
 * @param parentId 父级目录id
 * @param name 分类名称
 * @return 200表示成功 msg响应给页面的提示 data传递给页面的json'书记
 */
	 TaotaoResult addContentCategory(long parentId, String name);
}
