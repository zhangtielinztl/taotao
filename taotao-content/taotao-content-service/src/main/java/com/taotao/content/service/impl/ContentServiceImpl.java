package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;

import redis.clients.jedis.JedisCluster;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired JedisClient JedisClient;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
@Autowired
private TbContentMapper tbcontentMapper;
@Autowired
private TbItemCatMapper tbItemCatMapper;
	@Override
	public EasyUIDataGridResult findContentAll(long categoryId) {
		List<TbContent> tbContentAll = tbcontentMapper.findTbContentAll(categoryId);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setTotal(tbContentAll.size());
		result.setRows(tbContentAll);
		return result;
	}
	@Override
	public TaotaoResult deleteContentAll(long id) {
		tbcontentMapper.deletefindTbcontentById(id);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult addContent(TbContent tbContent) {
		Date date=new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		tbcontentMapper.addTbContent(tbContent);
		JedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
		
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> geTbContent(long categoryId) {
		//查询缓存
	try {
		String json = JedisClient.hget(CONTENT_KEY, categoryId+"");
		//判断json是否为空
		if(StringUtils.isNotBlank(json)) {
			//把json转换成result
			List<TbContent> result = JsonUtils.jsonToList(json, TbContent.class);
			System.out.println("从缓存中取redis");
			return result;
		};
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		/**
		 * 第一次请求的时候 由于没有缓存 所以直接查询数据库 查询完成数据库以后，
		 * 在return之前把数据库里面的数据加入到redis缓存中
		 * 在第二次访问的时候 由于先走上面的代码 先从缓存里面去取 ，取到直接return，没有取代代码继续执行下去
		 */
		List<TbContent> result = tbcontentMapper.findTbContentAll(categoryId);
		System.out.println("查询数据库");
		//这里加入缓存
		try {
			JedisClient.hset(CONTENT_KEY, categoryId+"",JsonUtils.objectToJson(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override

	public List<TbItemCat> getItemCatAll(long parentId) {

		List<TbItemCat> result = tbItemCatMapper.getItemCatByParentId(parentId);

		return result;

	}



}
