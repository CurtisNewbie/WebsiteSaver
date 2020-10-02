package com.curtisnewbie.dto;

/**
 * @author zhuangyongj
 */
public class QueryEntity {
    private String url;
    private String path;
    private String authKey;

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

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public String toString() {
        return "QueryEntity{" + "url='" + url + '\'' + ", path='" + path + '\'' + ", authKey='" + authKey + '\'' + '}';
    }
}
