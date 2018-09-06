package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.Node;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
@Autowired
private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		List<TbItemCat> tbItemCats= itemCatMapper.getItemCatByParentId(parentId);
		List<EasyUITreeNode> result=new ArrayList<EasyUITreeNode>();
		for (TbItemCat tbItemCat : tbItemCats) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			result.add(node);
		}
		
		return result;
	}

}
