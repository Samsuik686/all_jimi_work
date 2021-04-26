/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2017.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording, 
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 * 
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2017年4月11日    ChengXuWei         Create the class
 * http://www.jimilab.com/
 */

package cn.concox.comm.sms.other;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.concox.comm.util.http.Result;
import cn.concox.comm.util.http.SendRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * @FileName SmsUtils.java
 * @Description:
 * 
 * @Date 2017年4月11日 下午5:31:24
 * @author ChengXuWei
 * @version 1.0
 */
public class UCPaasUtils {

    private static final Logger log = LoggerFactory.getLogger(UCPaasUtils.class);

    // private static final String ver="2014-06-30";
    // private static final String sid = "86f8371fd947e24182da356586c7bdea";
    private static final String token = "36548AAA21ED5335BA632DB209CC7172";
    // private static final String authToken = "c56858085fbd16d0f2cf78279cf13976";
    // https://api.ucpaas.com/{SoftVersion}/Accounts/{accountSid}/{function}/{operation}?sig={SigParameter}
//    private static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * @Title: sendSms
     * @Description:
     * @param tplId
     * @param params
     * @return
     * @author ChengXuWei
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @date 2017年4月11日 下午5:39:59
     */
    public static String sendSms(String appId, String templateId, String param, String to) {
        String url = "http://sms.jimicloud.com/v1/commons/sendSms";

        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        formBodyBuilder.add("ver", "1");
        formBodyBuilder.add("method", "jimi.tms.commons.services.sendSms");
        formBodyBuilder.add("token", token); // DES加密项目名 
        formBodyBuilder.add("templateId", templateId);
        formBodyBuilder.add("toPhone", to);
//        formBodyBuilder.add("params", param);
        formBodyBuilder.addEncoded("params", param);
        // 创建请求
        Request request = new Request.Builder().url(url).post(formBodyBuilder.build())
                .build();
        // JSONObject jsonObj = sendApiRequest(url, params);
        Response response;
        try {
            response = client.newCall(request).execute();
            String result = response.body().string();
            String code = JSONObject.parseObject(result).getString("resultCode");
            if (response.code() == 200 && "0".equals(code)) {
                log.info("短信发送成功");
            } else {
                throw new RuntimeException("短信发送失败" + result);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";
    }

    /**
     * @Title: sendApiRequest
     * @Description:发送外部api请求
     * @param url
     * @param params
     * @return
     * @author TangYuping
     * @date 2017年11月2日 下午7:52:45
     */
    private static JSONObject sendApiRequest(String url, Map<String, String> params) {
        try {
            cn.concox.comm.util.http.RequestParam param = new cn.concox.comm.util.http.RequestParam();
            param.setUrl(url);
            param.setParams(params);
            Result rst = SendRequest.runSendPost(param);
            // Result rst = SendRequest.sendPostUrl(url, null, params, "utf-8");
            String json = rst.getHtml(rst, "utf-8");
            log.info("调用track-api成功。返回结果：{}", json);
            return JSONObject.parseObject(json);
        } catch (Exception e) {
            log.error("调用track-api异常。url:" + url + ",params:" + JSONObject.toJSONString(params), e);
        }
        return null;
    }

    /**
     * @Title: testSms
     * @Description:
     * @author ChengXuWei
     * @date 2017年4月18日 下午1:52:34
     */
    private static void testSms() {
        String appId = "c3e6e1da63784b8cb08bfdf0f3161768";// 售后app应用Id
        String templateId = "10759";
        String to = "13163799689";
        // 你的Track平台验证码为{1},有效时间{2}分钟，感谢你的使用
        String result = sendSms(appId, templateId, "123456,60", to);
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        testSms();

    }
}
