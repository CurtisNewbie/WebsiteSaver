package com.curtisnewbie.api;

/**
 * @author zhuangyongj
 */
public interface UrlUtil {

    /**
     * Parse the given http url (with protocols), and extract the base url.
     *
     * @param url http url with protocols
     * @return base url or empty string if failed
     */
    String parseBaseUrl(String url);

}
