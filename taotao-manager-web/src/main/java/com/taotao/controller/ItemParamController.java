package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {


    @Autowired
    private ItemParamService itemParamService;
    @RequestMapping("/query/itemcatid/{itemCatIid}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatIid){
        TaotaoResult result= itemParamService.getItemParamByCid(itemCatIid);
        return  result;
    }

    @RequestMapping("/save/{itemCatIid}")
    @ResponseBody
    public  TaotaoResult insertItemParam(@PathVariable long itemCatIid,String paramData){
        TbItemParam tbItemParam =new TbItemParam();
        tbItemParam.setItemcatId(itemCatIid);
        tbItemParam.setParamData(paramData);

        TaotaoResult result = itemParamService.addItemParam(tbItemParam);
        return  result;
    }
}
