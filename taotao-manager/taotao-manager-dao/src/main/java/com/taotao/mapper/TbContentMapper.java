package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface TbContentMapper {
/**
 * 查询指定内容分类下的所有内容
 * @param categoryId 内容分类id
 * @return 当前指定分类下的所有内容
 */
 List<TbContent> findTbContentAll(long categoryId);
 /**
  * 根据指定内容id删除指定内容
  *
  */
 void deletefindTbcontentById(Long id);
 /**
  * 添加一个内容
  * @param tbContent
  */
 void addTbContent(TbContent tbContent);
 
}