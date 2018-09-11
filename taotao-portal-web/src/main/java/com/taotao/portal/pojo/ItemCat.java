package com.taotao.portal.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ItemCat {
    //@JsonProperty("u")在java里面 他的属性名称还是叫做url 但是吧这个属性变成json格式的数据的时候他的key叫做u

    @JsonProperty("u")

    private String url;

    @JsonProperty("n")

    private String name;

    @JsonProperty("i")

    private List<?> item;



    public String getUrl() {

        return url;

    }



    public void setUrl(String url) {

        this.url = url;

    }



    public String getName() {

        return name;

    }



    public void setName(String name) {

        this.name = name;

    }



    public List<?> getItem() {

        return item;

    }



    public void setItem(List<?> item) {

        this.item = item;

    }



    @Override

    public String toString() {

        return "ItemCat{" +

                "url='" + url + '\'' +

                ", name='" + name + '\'' +

                ", item=" + item +

                '}';

    }
}
