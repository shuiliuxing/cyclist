package com.huabing.cyclist.gson.musicmenugson;

import java.util.List;

/**
 * Created by 30781 on 2017/7/9.
 */

public class MusicMenuBean {
    private String name;
    private List<Menu> menu;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
    public List<Menu> getMenu() {
        return menu;
    }
}
