package com.hackathon.prophet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML标签过滤工具
 *
 * @author jim
 * @date 2017/11/27
 */
public final class HtmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);

    private static Pattern p_script;
    private static Pattern p_style;
    private static Pattern p_html;
    private static Pattern p_space;
    private static Pattern p_escape;

    static
    {
        // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

        // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

        // 定义HTML标签的正则表达式
        String regEx_html = "<[^>]+>";

        // 定义空格回车换行符
        String regEx_space = "\\s*|\t|\r|\n";

        // 定义转义字符
        String regEx_escape = "&.{2,6}?;";

        // 过滤script标签
        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        p_escape = Pattern.compile(regEx_escape, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 禁止实例化
     */
    private HtmlUtils() {
        throw new IllegalStateException("禁止实例化");
    }

    /**
     * 过滤HTML标签输出文本
     *
     * @param inputString 原字符串
     * @return 过滤后字符串
     */
    public static String Html2Text(String inputString) {
        if (StringUtils.isEmpty(inputString)) {
            return "";
        }

        // 含html标签的字符串
        String htmlStr = inputString.trim();
        String textStr = "";
        Matcher m_script;
        Matcher m_style;
        Matcher m_html;
        Matcher m_space;
        Matcher m_escape;

        try {
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");

            // 过滤style标签

            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");

            // 过滤html标签
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");

            // 过滤空格回车标签
            m_space = p_space.matcher(htmlStr);
            htmlStr = m_space.replaceAll("");

            // 过滤转义字符
            m_escape = p_escape.matcher(htmlStr);
            htmlStr = m_escape.replaceAll("");

            textStr = htmlStr;

        } catch (Exception e) {
            logger.info("Html2Text:{}", e.getMessage());
        }

        // 返回文本字符串
        return textStr;
    }

    /**
     * 删除所有的HTML标签
     *
     * @param source 需要进行除HTML的文本
     * @return
     */
    public static String deleteAllHTMLTag(String source) {

        if(source == null) {
            return "";
        }

        String s = source;
        /** 删除普通标签  */
        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
        s = s.replaceAll("&.{2,6}?;", "");
        return s;
    }
}
