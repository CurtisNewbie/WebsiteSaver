package com.curtisnewbie.dto;

/**
 * @author zhuangyongj
 */
public class QueryDto {
    private String url;
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "QueryEntity{" +
                "url='" + url + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
