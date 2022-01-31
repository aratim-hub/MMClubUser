package com.appmart.mmcuser.models;

public class SliderImageListData {
    private String SliderImageURL;
    private String image_title;

    public String getSliderImageURL() {
        return SliderImageURL;
    }

    public String getImage_title() {
        return image_title;
    }

    public SliderImageListData(String image_id, String image_title, String image, String image_url, String created_at) {
        this.SliderImageURL=image_url;
        this.image_title=image_title;
    }
}
