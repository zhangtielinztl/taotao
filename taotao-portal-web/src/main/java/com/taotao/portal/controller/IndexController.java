package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import com.taotao.pojo.TbItemCat;
import com.taotao.portal.pojo.ItemCat;
import com.taotao.portal.pojo.ItemCatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.Node;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@RequestMapping("/index")
	public String showIndex(Model model) {
		List<TbContent> tbContents = contentService.geTbContent(AD1_CID);
		List<Ad1Node> ad1=new ArrayList<Ad1Node>();
		for (TbContent tbContent : tbContents) {
			Ad1Node node=new Ad1Node();
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			//添加到列表
			ad1.add(node);
		
		}
		model.addAttribute("ad1",JsonUtils.objectToJson(ad1));
		return "index";
	}
	// http://localhost:8082/item/cat/itemcat/all.html

	@RequestMapping("/item/cat/itemcat/all")

	@ResponseBody

	public ItemCatResult queryAll(String callback) {

		ItemCatResult result = new ItemCatResult();

		result.setData(getItemCatList(0));

		return result;

	}



	/**

	 * 查询数据库得到想要的分类的

	 *

	 * @return

	 */

	private List<?> getItemCatList(long parentId) {

		List<TbItemCat> list = contentService.getItemCatAll(parentId);

		List data = new ArrayList();

		int count = 0;

		for (TbItemCat item : list) {

			ItemCat itemCat = new ItemCat();

			//判断是否是父节点

			if (item.getIsParent()) {



				itemCat.setUrl("/products/" + item.getId() + ".html");

				if (parentId == 0) {

					//注意 只有第一级目录才是这样 第二级是字符串

					itemCat.setName("<a href='products/" + item.getId() + ".html'>" + item.getName() + "</a>");

				} else {

					//第二级目录

					itemCat.setName(item.getName());

				}

				count ++;

				//递归

				itemCat.setItem(getItemCatList(item.getId()));

				//查询数据库以后得到的一二级目录存放到一个list集合里面去

				data.add(itemCat);





				if (parentId == 0 && count >= 14) {

					break;

				}





			} else {

				//最底层的子目录 存放的最下级目录

				data.add("/products/" + item.getId() + ".html|" + item.getName());



			}

		}

		return data;

	}
}
