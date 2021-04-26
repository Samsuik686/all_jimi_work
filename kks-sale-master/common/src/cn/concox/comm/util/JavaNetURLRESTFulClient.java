package cn.concox.comm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.concox.comm.Globals;

public class JavaNetURLRESTFulClient {
	

    public static void main(String[] args) {
//    	restGoods("GT700","","");
//    	System.out.println(restGoods("GT230_0BD",Globals.AMS_Goodsurl));
    	
       	String json = JavaNetURLRESTFulClient.restSale("867597012475536","", Globals.AMS_Salesurl);  
      	System.out.println(json); 
    }
    
    public static int restGoods(String mcType ,String amsUrl){
        try {
            URL targetUrl = new URL(amsUrl);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"mcType\":\""+mcType+"\"}";

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream())));

            String output;
            int goods=0;
            while ((output = responseBuffer.readLine()) != null) {
                  // System.out.println(output);
                   goods = Integer.parseInt(output);
            }

            httpConnection.disconnect();
            return goods;
       } catch (MalformedURLException e) {
            e.printStackTrace();
            return 0;
       } catch (IOException e) {
            e.printStackTrace();
            return 0;
      }
    }
    
    
    public static String restSale(String imei, String lockId, String amsUrl){
    	String workflow = "";
        try {
            URL targetUrl = new URL(amsUrl);

            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"imei\":\""+imei+"\",\"lockId\":\""+lockId+"\"}";

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                          + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                          (httpConnection.getInputStream()),"utf-8"));

            String output;
            while ((output = responseBuffer.readLine()) != null) {
                 //  System.out.println(output);
                   workflow = output;
            }

            httpConnection.disconnect();
   
       } catch (MalformedURLException e) {
            e.printStackTrace();
  
       } catch (IOException e) {
            e.printStackTrace();
           
      }
        if(workflow.indexOf("lockId()") > -1){
        	workflow = workflow.replace("lockId()", "lockId");
        }
        return workflow;
    }
	

}
