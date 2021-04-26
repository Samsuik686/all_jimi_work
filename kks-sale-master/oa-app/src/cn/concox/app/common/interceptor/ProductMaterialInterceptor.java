package cn.concox.app.common.interceptor;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.concox.app.ipallow.service.IpWhiteListService;
import cn.concox.app.materialManage.controller.ProductMaterialController;
import cn.concox.app.materialManage.service.ProductMaterialService;
import cn.concox.comm.Globals;
import cn.concox.comm.util.CodeMsgPackage;

public class ProductMaterialInterceptor extends HandlerInterceptorAdapter {
    Logger logger = Logger.getLogger(ProductMaterialController.class);

    @Resource(name = "productMaterialService")
    private ProductMaterialService productMaterialService;
    
    @Resource(name = "ipWhiteListService")
    private IpWhiteListService ipAllowService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Set<String> ipSet = new HashSet<String>();
        //
        // // 公司内网网关
        // ipSet.add("10.0.8.1");
        //
        // // 公司外网IP
        // ipSet.add("10.0.9.245");
        // ipSet.add("120.237.87.194");
        // ipSet.add("183.237.64.58");
        // ipSet.add("183.39.232.170");
        // ipSet.add("10.0.10.253");
        //
        // // 产品一部内部人员内网固定ip
        // ipSet.add("10.0.11.241");// 刘佳灵
        // ipSet.add("10.0.9.145");// 刘成生
        // ipSet.add("10.0.8.156");// 王志华
        // ipSet.add("10.0.8.86");// 张蒋明
        // ipSet.add("10.0.8.154");// 曾小鹏
        // ipSet.add("10.0.20.213");// 沈裕全
        // ipSet.add("10.0.8.132");// 陈梦娜
        // ipSet.add("10.0.20.2");// 曾佳文
        // ipSet.add("10.0.9.213");// 魏文俊
        // ipSet.add("10.0.12.60");// 邢银弟
        //
        // ipSet.add("10.0.8.73");// 徐明星：
        // ipSet.add("10.0.9.87");// 罗朝花
        // ipSet.add("10.0.8.175");// 江萌
        // ipSet.add("10.0.8.29");// 王花
        // ipSet.add("10.0.8.31");// 刘晓欢：
        // ipSet.add("10.0.12.14");// 杨云龙：
        // ipSet.add("10.0.8.90");// 彭官厚：
        // ipSet.add("10.0.8.45");// 黄家亮：
        // ipSet.add("10.0.8.66");// 陈永旺：
        // ipSet.add("10.0.9.184");// 郑志明;
        // ipSet.add("10.0.9.32");// 蓝荣川：
        // ipSet.add("10.0.8.188");// 廖广平：
        // ipSet.add("10.0.8.146");// 方卫群：
        // ipSet.add("10.0.9.182");// 维修电脑：
        // ipSet.add("10.0.8.84");// 谭志刚：
        // ipSet.add("10.0.8.67");// 彭强：
        // ipSet.add("10.0.8.64");// 钱永光：
        // ipSet.add("10.0.9.160");// 符文聪：
        // ipSet.add("10.0.8.142");// 杨丹丹
        // ipSet.add("10.0.9.43");// 张欣怡：
        // ipSet.add("10.0.8.18");// 李春梅：
        // ipSet.add("10.0.9.216");// 何孟青：
        //
        // ipSet.add("172.16.32.42");// 吴宣
        //
        // // 开发人员本地测试ip
        // ipSet.add("10.0.17.208");

        String ip = getIpAddress(request);
        String[] ips = ip.split("\\.");

        // 除特定IP段外的白名单
        boolean isWhiteList =  ipAllowService.getAccessIp(ip);
        
        if (checkIP(ips) || isWhiteList) {
            logger.error("本次访问ip为：" + ip + "，是否通过：true");
            return true;
        } else {
            PrintWriter writ = response.getWriter();
            writ.write(CodeMsgPackage.getJSONByCodeAndMsg(Globals.USER_MENUNOPOWER_CODE, Globals.USER_MENUNOPOWER_URL));
            writ.flush();
            logger.error("本次访问ip为：" + ip + "，是否通过：false");
            return false;
        }
    }

    /**
     * @Title: checkIP 
     * @Description:验证ip白名单，ip与子网掩码做与运算，然后和网关做比较
     * @param ips
     * @return 
     * @author Wang Xirui
     * @date 2019年4月23日 下午4:30:35
     */
    private static boolean checkIP(String[] ips) {
        // 网关10.0.16.0
        // 子网掩码 255.255.248.0
        int ip1 = Integer.valueOf(ips[0]) & 255;
        int ip2 = Integer.valueOf(ips[1]) & 255;
        int ip3 = Integer.valueOf(ips[2]) & 248;

        if (ip1 == 10 && ip2 == 0 && ip3 == 16) {
            return true;
        }
        return false;
    }
    
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("0:0:0:0:0:0:0:1")) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();// localhost对应的真实id
                    // System.out.println(ip);
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
