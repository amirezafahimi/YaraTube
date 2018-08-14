
package com.yaratech.yaratube.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("parent_categories")
    @Expose
    private List<ParentCategory> parentCategories = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("tabStrip")
    @Expose
    private List<Object> tabStrip = null;
    @SerializedName("headeritem")
    @Expose
    private List<HeaderItem> headeritem = null;
    @SerializedName("homeitem")
    @Expose
    private List<HomeItem> homeitem = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ParentCategory> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(List<ParentCategory> parentCategories) {
        this.parentCategories = parentCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Object> getTabStrip() {
        return tabStrip;
    }

    public void setTabStrip(List<Object> tabStrip) {
        this.tabStrip = tabStrip;
    }

    public List<HeaderItem> getHeaderitem() {
        return headeritem;
    }

    public void setHeaderitem(List<HeaderItem> headeritem) {
        this.headeritem = headeritem;
    }

    public List<HomeItem> getHomeitem() {
        return homeitem;
    }

    public void setHomeitem(List<HomeItem> homeitem) {
        this.homeitem = homeitem;
    }

}
