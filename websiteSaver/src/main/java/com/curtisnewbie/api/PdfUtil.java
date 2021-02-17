package com.curtisnewbie.api;

/**
 * Class that provides util methods related to pdf
 *
 * @author zhuangyongj
 */
public interface PdfUtil {

    /**
     * <p>
     * Convert the html content to a pdf file located at {@code path}.
     * </p>
     * <p>
     * Exceptions are caught within the method, such that the caller only takes care of the returned value that
     * indicates whether such operation is success or not.
     * </p>
     * <p>
     * If the parent directory of the given {@code path} doesn't exist, this method will create the parent directory.
     * </p>
     * <p>
     * For example, for path "../dir/not/exists/abc.pdf". If parent directories ".../dir/not/exists/..." doesn't exist,
     * this method will create all these directories before converting the pdf file.
     * </p>
     *
     * @param htmlContent actual content of the website
     * @param path        absolute or relative path to the pdf file (that will be created)
     * @return TRUE if operation success else FALSE
     */
    boolean toPdfFile(String htmlContent, String path);

    /**
     * <p>
     * Convert the html content to a pdf file located at {@code path}.
     * </p>
     * <p>
     * Exceptions are caught within the method, such that the caller only takes care of the returned value that
     * indicates whether such operation is success or not.
     * </p>
     * <p>
     * If the parent directory of the given {@code path} doesn't exist, this method will create the parent directory.
     * </p>
     * <p>
     * For example, for path "../dir/not/exists/abc.pdf". If parent directories ".../dir/not/exists/..." doesn't exist,
     * this method will create all these directories before converting the pdf file.
     * </p>
     * <p>
     * Base url is used for handing the css or images resources. A base url can be specified using a {@code base} html
     * tag. Some websites may prefer to use relative location for their resources, so that the base tag isn't used.
     * </p>
     *
     * @param htmlContent actual content of the website
     * @param baseUrl     of the htmlContent
     * @param path        absolute or relative path to the pdf file (that will be created)
     * @return TRUE if operation success else FALSE
     * @see HtmlUtil#extractBaseUrl(org.jsoup.nodes.Document)
     */
    boolean toPdfFile(String htmlContent, String baseUrl, String path);

}
