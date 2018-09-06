package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		List<TbContentCategory> tbContentCategorys = tbContentCategoryMapper.gteTbContentCategoryByParentId(parentId);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : tbContentCategorys) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

			result.add(node);
		}
		return result;
	}

	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		// 我们第一次添加的一定是子节点，还得判断他的父节点是子节点还是父节点
		// 补全TbContentCategory对象的属性
		tbContentCategory.setIsParent(false);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		// 排列序号，表示同类级目录的展现次序，如数值相等则按名称次序排列。取值范围：大于0的则整数
		tbContentCategory.setSortOrder(1);
		// 状态 ：可选值：1正常 2删除
		tbContentCategory.setStatus(1);
		Date date = new Date();
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		// 插入数据到数据库
		tbContentCategoryMapper.addTbContentcategory(tbContentCategory);
		// 修改父级目录，页面传递过来的数据 有两个 parentId 和name 其中parentId为当前内容分类的id，name为当前内容分类的名称
		TbContentCategory category = tbContentCategoryMapper.geTbContentCategoryById(parentId);
		// 则表示要设置当前节点为父节点,
		if (!category.getIsParent()) {
			category.setId(parentId);
			category.setIsParent(true);
			tbContentCategoryMapper.updateTbContentCategory(tbContentCategory);
		}
		//回传给页面显示
		return TaotaoResult.ok(tbContentCategory);
	}

}
