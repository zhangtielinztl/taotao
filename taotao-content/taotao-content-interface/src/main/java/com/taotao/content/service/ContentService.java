package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCat;

public interface ContentService {
	/**
	 * 根据内容分类ID查询得到指指定内容信息
	 * @param categoryId 分类id
	 * @return json数据 包含总记录条数 和每条记录的json数据
	 */
 EasyUIDataGridResult findContentAll(long categoryId);
 /**
  * 根据id删除指定内容信息
  * @param id
  */

TaotaoResult deleteContentAll(long id);
/**
 * 添加一个cms内容信息
 * @param tbContent
 * @return 200则表示成功
 */
TaotaoResult addContent(TbContent tbContent);
/**
 * 根据分类ID查询指定内容
 * @param categoryId 分类id
 * @return 返回指定分类下面的所有内容
 */
List<TbContent> geTbContent(long categoryId);
	/**

	 * 根据id查询所有分类

	 * @param parentId 分类id

	 * @return 当前id的所有分类

	 */

	List<TbItemCat> getItemCatAll(long parentId);
}
