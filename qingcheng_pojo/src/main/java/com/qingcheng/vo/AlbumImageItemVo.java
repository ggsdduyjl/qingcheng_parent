package com.qingcheng.vo;

import java.io.Serializable;

/**
 * 相册图片列表对象
 */
public class AlbumImageItemVo implements Serializable {

    private String url;

    private Long uid;

    private String status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
