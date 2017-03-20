package br.com.heiderlopes.recyclerviewactionmode.model;

/**
 * Created by heider on 10/03/17.
 */

public class ItemModel {

    private String title, subTitle;

    public ItemModel(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
