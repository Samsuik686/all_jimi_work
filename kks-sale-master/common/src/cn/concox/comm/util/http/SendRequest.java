/*
 * Created: 2016-08-30 09:39:45
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.comm.util.http;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class SendRequest {

    private static final Logger logger = LoggerFactory.getLogger(SendRequest.class);

    private static DefaultHttpClient client = new DefaultHttpClient();

    private static OkHttpClient client1 = new OkHttpClient();

    /**
     * 发送Get请求
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result sendGet(String url, Map<String, String> headers, Map<String, String> params, String encoding,
            boolean duan) throws ClientProtocolException, IOException {
        url = url + (null == params ? "" : assemblyParameter(params));
        HttpGet hp = new HttpGet(url);
        if (null != headers)
            hp.setHeaders(assemblyHeader(headers));
        HttpResponse response = client.execute(hp);
        if (duan)
            hp.abort();
        HttpEntity entity = response.getEntity();
        Result result = new Result();
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setHeaders(response.getAllHeaders());
        result.setHttpEntity(entity);
        return result;
    }

    public static Result sendGet(String url, Map<String, String> headers, Map<String, String> params, String encoding)
            throws ClientProtocolException, IOException {
        return sendGet(url, headers, params, encoding, false);
    }

    /**
     * 发送Post请求
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result sendPost(String url, Map<String, String> headers, Map<String, String> params, String encoding)
            throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : params.keySet()) {
            list.add(new BasicNameValuePair(temp, params.get(temp)));
        }
        post.setEntity(new UrlEncodedFormEntity(list, encoding));

        if (null != headers)
            post.setHeaders(assemblyHeader(headers));
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        Result result = new Result();
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setHeaders(response.getAllHeaders());
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        result.setHttpEntity(entity);
        return result;
    }

    /**
     * 发送Post请求
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Result sendPostUrl(String url, Map<String, String> headers, Map<String, String> params,
            String encoding) throws ClientProtocolException, IOException, InterruptedException, ExecutionException {

        RequestParam requestParam = new RequestParam();
        requestParam.setUrl(url);
        requestParam.setParams(params);
        headers = addCloseHeader(headers);
        requestParam.setHeaders(headers);
        requestParam.setEncoding(encoding);
        String method = params.get("_method_");
        if (null == method || "".equals(method)) {
            method = params.get("method");
        }
        // 并非所有URL请求都有method 或是_method_
        if (StringUtil.isEmpty(method)) {
            method = "sendPost";
        }
        requestParam.setMethod(method);

        HttpCmd cmd = new HttpCmd(url, method, requestParam, SendRequest.class.getName(), "runSendPost");
        Object obj = cmd.queue().get();
        if (null != obj && obj instanceof Result) {
            Result result = (Result) obj;
            return result;
        } else {
            throw new RuntimeException("服务器熔断了,请联系管理人员");
        }
    }

    /**
     * 
     * @param headers
     * @return
     */
    private static Map<String, String> addCloseHeader(Map<String, String> headers) {
        if (null == headers) {
            headers = new HashMap<String, String>();
        }
        headers.put("Connection", "close");
        return headers;
    }

    /**
     * 组装头部信息
     * 
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, String> headers) {
        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }

    public static int sendPostBody(String urls, String bodys, Map<String, String> params, String encodings)
            throws ClientProtocolException, IOException {
        HttpClient client = new HttpClient();
        urls = urls + (null == params ? "" : assemblyParameter(params));
        PostMethod post = new PostMethod(urls);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encodings);
        post.setRequestBody(bodys);
        return client.executeMethod(post);
    }

    /**
     * 组装Cookie
     * 
     * @param cookies
     * @return
     */
    public static String assemblyCookie(List<Cookie> cookies) {
        StringBuffer sbu = new StringBuffer();
        for (Cookie cookie : cookies) {
            sbu.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        if (sbu.length() > 0)
            sbu.deleteCharAt(sbu.length() - 1);
        return sbu.toString();
    }

    /**
     * 组装请求参数
     * 
     * @param parameters
     * @return
     */
    public static String assemblyParameter(Map<String, String> parameters) {
        String para = "?";
        for (String str : parameters.keySet()) {
            para += str + "=" + parameters.get(str) + "&";
        }
        return para.substring(0, para.length() - 1);
    }

    /**
     * 文件上传
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result uploadFile(String url, Map<String, String> headers, Map<String, String> params, String filepath)
            throws ClientProtocolException, IOException {
        File targetFile = new File(filepath);// TODO 指定上传文件
        boolean flag = true;
        for (String temp : params.keySet()) {
            if (flag) {
                url = url + "?" + temp + "=" + params.get(temp);
                flag = false;
            } else {
                url = url + "&" + temp + "=" + params.get(temp);
            }
        }
        PostMethod filePost = new PostMethod(url);
        Result result = new Result();
        try {
            Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            if (null != headers && headers.size() > 0) {
                for (String str : headers.keySet()) {
                    filePost.setRequestHeader(str, headers.get(str));
                }
            }
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(100);
            int status = client.executeMethod(filePost);
            HttpClientParams resultParams = client.getParams();
            result.setStatusCode(status);
            result.setCookie(resultParams.getCookiePolicy());
            result.setOtherContent(filePost.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            filePost.releaseConnection();
        }
        return result;
    }

    // TODO demo
    public static void main(String[] args) {
        Map<String, String> param = new HashMap<String, String>();

        try {
            Result result = SendRequest.sendGet("http://www.baidu.com", param, param, "utf-8");
            // SendRequest.u
            String str = result.getHtml(result, "utf-8");
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Result runSendGet(RequestParam requestParam) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            String url = requestParam.getUrl(); // 请求的url
            Map<String, String> headers = requestParam.getHeaders(); // 请求的头信息
            // String encoding = requestParam.getEncoding(); //编码格式
            Map<String, String> params = requestParam.getParams(); // 请求参数
            logger.debug("调用url：" + url + " 方法名称：" + requestParam.getMethod() + " imei: "
                    + requestParam.getParams().get("imei") + " 调用Get请求");
            boolean duan = requestParam.isDuan();
            url = url + (null == params ? "" : assemblyParameter(params));
            HttpGet httpGet = new HttpGet(url);
            if (null != headers) {
                httpGet.setHeaders(assemblyHeader(headers));
            }
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpGet);
            if (duan) {
                httpGet.abort();
            }
            HttpEntity entity = response.getEntity();
            Result result = new Result();
            // result.setCookie(assemblyCookie(defaultHttpClient.getCookieStore().getCookies()));
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            result.setHttpEntity(entity);
            return result;
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpclient) {
                httpclient.close();
            }
        }
    }

    /**
     * 执行Post请求 ，由HttpCmd内部进行反射调用，来监控http请求的异常问题
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result runSendPost(RequestParam requestParam) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            String url = requestParam.getUrl(); // 请求的url
            Map<String, String> headers = requestParam.getHeaders(); // 请求的头信息
            String encoding = requestParam.getEncoding(); // 编码格式
            Map<String, String> params = requestParam.getParams(); // 请求参数
            logger.debug("调用url：" + url + " 方法名称：" + requestParam.getMethod() + " imei: "
                    + requestParam.getParams().get("imei") + " 调用SENDPOST请求");
            HttpPost post = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String temp : params.keySet()) {
                list.add(new BasicNameValuePair(temp, params.get(temp)));
            }
            post.setEntity(new UrlEncodedFormEntity(list, encoding));
            if (null != headers) {
                post.setHeaders(assemblyHeader(headers));
            }
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            Result result = new Result();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            // result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
            result.setHttpEntity(entity);
            return result;
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != httpclient) {
                httpclient.close();
            }
            // TODO: handle finally clause
        }
    }

    /**
     * 执行sendOK请求 ，由HttpCmd内部进行反射调用，来监控http请求的异常熔断问题
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String runSendOKHttpRequest(RequestParam requestParam) throws IOException {
        String result = "";
        Response response = null;
        Request request = null;
        try {
            String url = requestParam.getUrl(); // 请求的url
            Map<String, String> params = requestParam.getParams(); // 请求参数
            logger.debug("调用url：" + url + " 方法名称：" + requestParam.getMethod() + " imei: "
                    + requestParam.getParams().get("imei") + " 调用SendOK请求");
            url = url + (null == params ? "" : assemblyParameter(params));
            request = new Request.Builder().header("Content-Type", "application/json")
                    .addHeader("Accept", "application/json").addHeader("Connection", "close").url(url).build();
            response = client1.newCall(request).execute();
            result = response.body().string();
            return result;
        } finally {
            if (null != response) {
                response.body().close();
            }
            // TODO: handle finally clause
        }
    }


    /**
     * 执行 run PostBody请求 由HttpCmd内部进行反射调用，来监控http请求的异常熔断问题
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static int runPostBody(RequestParam requestParam) throws ClientProtocolException, IOException {
        String url = requestParam.getUrl(); // 请求的url
        String encoding = requestParam.getEncoding(); // 编码格式
        Map<String, String> params = requestParam.getParams(); // 请求参数
        String bodys = requestParam.getBodys(); // body内容
        logger.debug("调用url：" + url + " 方法名称：" + requestParam.getMethod() + " imei: "
                + requestParam.getParams().get("imei") + " 调用PostBody请求");
        HttpClient client = new HttpClient();
        url = url + (null == params ? "" : assemblyParameter(params));
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        post.setRequestBody(bodys);
        return client.executeMethod(post);
    }

    /**
     * 执行文件上传uploadFile请求 由HttpCmd内部进行反射调用，来监控http请求的异常熔断问题
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result runUploadFile(RequestParam requestParam) throws ClientProtocolException, IOException {
        String url = requestParam.getUrl();
        Map<String, String> headers = requestParam.getHeaders();
        Map<String, String> params = requestParam.getParams();
        logger.debug("调用url：" + url + " 方法名称：" + requestParam.getMethod() + " imei: "
                + requestParam.getParams().get("imei") + " 调用runUploadFile请求");
        String filepath = requestParam.getFilepath();
        File targetFile = new File(filepath);// TODO 指定上传文件
        boolean flag = true;
        for (String temp : params.keySet()) {
            if (flag) {
                url = url + "?" + temp + "=" + params.get(temp);
                flag = false;
            } else {
                url = url + "&" + temp + "=" + params.get(temp);
            }
        }
        PostMethod filePost = new PostMethod(url);
        Result result = new Result();
        try {
            Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            if (null != headers && headers.size() > 0) {
                for (String str : headers.keySet()) {
                    filePost.setRequestHeader(str, headers.get(str));
                }
            }
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(100);
            int status = client.executeMethod(filePost);
            HttpClientParams resultParams = client.getParams();
            result.setStatusCode(status);
            result.setCookie(resultParams.getCookiePolicy());
            result.setOtherContent(filePost.getResponseBodyAsString());
        } finally {
            filePost.releaseConnection();
        }
        return result;
    }

}