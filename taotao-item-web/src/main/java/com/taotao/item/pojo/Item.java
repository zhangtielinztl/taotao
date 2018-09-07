package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;


    public class Item extends TbItem {
//外界查询数据库把数据库中的数据封装到TbItem对象里面。我们要把TbItem对象的属性赋值到Item属性里面
        public Item(TbItem tbItem) {
            this.setBarcode(tbItem.getBarcode());
            this.setCid(tbItem.getCid());
            this.setCreated(tbItem.getCreated());
            this.setId(tbItem.getId());
            this.setImage(tbItem.getImage());
            this.setNum(tbItem.getNum());
            this.setPrice(tbItem.getPrice());
            this.setSellPoint(tbItem.getSellPoint());
            this.setStatus(tbItem.getStatus());
            this.setTitle(tbItem.getTitle());
            this.setUpdated(tbItem.getUpdated());
        }
        public String[] getImages() {

            if (super.getImage()!= null && !"".equals(super.getImage())) {
                String[] strings = super.getImage().split(",");
                return strings;
            }
            return null;
        }

    }

