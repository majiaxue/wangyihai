package com.xingshi.bean;

import java.util.List;

public class XSBlockExamineImg {
    List<ImgBean> img;

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public static class  ImgBean{
        public String imgUrl;

        public ImgBean(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "ImgBean{" +
                    "imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }
}

