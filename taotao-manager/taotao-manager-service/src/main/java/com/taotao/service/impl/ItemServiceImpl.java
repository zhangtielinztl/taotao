package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.commom.utils.IDUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
/**
 * @Service替代 <bean> 标签
 *
 *
 */

@Service
public class ItemServiceImpl implements ItemService {
@Autowired
private TbItemMapper tbItemMapper;
	@Override
	public TbItem geTbItemById(long itemId) {
		TbItem tbItem = tbItemMapper.geTbItemById(itemId);
		return tbItem;
	}
	@Override
	public EasyUIDataGridResult getItems(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//得到所有 商品信息
		List<TbItem> list = tbItemMapper.getTbItem();
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		//创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
}
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		//补全商品信息
		long itemId=IDUtils.genItemId();
	item.setId(itemId);
	//补全状态
	item.setStatus((byte) 1);
	Date date = new Date();
	//补全创建时间
	item.setCreated(date);
	//补全更新时间
	item.setUpdated(date);
	//添加商品基本信息
	tbItemMapper.addTbItem(item);
	
	TbItemDesc itemDesc=new  TbItemDesc();
	//补全商品描述表中的商品ID
	itemDesc.setItemId(itemId);
	//补全商品描述表中创建时间
	itemDesc.setCreated(date);
	//补全商品描述表中更新时间
	itemDesc.setUpdated(date);
	tbItemMapper.addTbItemDesc(itemDesc);
	
		return TaotaoResult.ok();
	}
}
