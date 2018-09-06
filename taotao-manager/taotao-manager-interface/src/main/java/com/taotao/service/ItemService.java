package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	/**
	 * 根据商品ID指定商品信息
	 * @param itemId 商品id
	 * @return返回指定商品id的信息
	 */
 public  TbItem geTbItemById(long itemId);
 
/**
 * 根据页面传递过来的参数分页显示商品信息
 * @param page 当前页面
 * @param rows 每一页的显示记录条数
 * @return 总记录条数+每一个商品信息
 */
 EasyUIDataGridResult getItems(int page, int rows);
/**
 * 添加商品基本信息和描述信息，商品的规格参数以后来参加
 * @param item 商品基本信息
 * @param itemDesc 商品描述信息
 * @return 包含了状态吗 是否成功的message 和json数据
 */
 TaotaoResult addItem(TbItem item, String desc);
}
