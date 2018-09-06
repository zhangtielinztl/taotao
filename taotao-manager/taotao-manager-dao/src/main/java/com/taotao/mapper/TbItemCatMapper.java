package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItemCat;

public interface TbItemCatMapper {
	/**
	 * 根据ID查询分类信息
	 * @param parentId 当前id
	 * @return 当前分类下的子目录
	 */
 List<TbItemCat> getItemCatByParentId(long parentId);
}