package cn.concox.app.materialManage.controller;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.service.DzlmanageService;
import cn.concox.app.basicdata.service.PjlmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.service.MaterialService;
import cn.concox.app.materialManage.service.ProductMaterialFileLogService;
import cn.concox.app.materialManage.service.ProductMaterialService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.commvo.CommonParam;
import cn.concox.vo.material.Material;
import cn.concox.vo.materialManage.ProductMaterial;
import cn.concox.vo.materialManage.ProductMaterialFile;
import cn.concox.vo.materialManage.ProductMaterialFileLog;
import cn.concox.vo.materialManage.ProductMaterialVo;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:23:13
 */

@Controller
@RequestMapping("/productMaterial")
@Scope("prototype")
public class ProductMaterialController extends BaseController {
    Logger logger = Logger.getLogger(ProductMaterialController.class);

    @Autowired
    private ProductMaterialService productMaterialService;

    @Autowired
    private ProductMaterialFileLogService productMaterialFileLogService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private PjlmanageService pjlmanageService;
    @Autowired
    private DzlmanageService dzlmanageService;

    /**
     * 获取产品资料数据列表
     * 
     * @author TangYuping
     * @version 2017年3月28日 下午2:13:03
     * @param pm
     * @param com
     * @param req
     * @return
     */
    @RequestMapping("/productMaterialList")
    @ResponseBody
    public APIContent getProductMaterialList(@ModelAttribute ProductMaterial pm, @ModelAttribute CommonParam com,
            HttpServletRequest req) {
        try {
            Page<ProductMaterial> page = productMaterialService.productMaterialList(pm, com.getCurrentpage(),
                    com.getPageSize());
            req.getSession().setAttribute("totalValue", page.getTotal());
            List<ProductMaterial> list = page.getResult();
            return super.putAPIData(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取产品资料数据失败:" + e.toString());
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }

    }

    /**
     * 保存产品资料基本信息
     * 
     * @author TangYuping
     * @version 2017年3月29日 上午10:38:39
     * @param pm
     * @param req
     * @return
     */
    @RequestMapping("/saveOrUpdateInfo")
    @ResponseBody
    public APIContent saveOrUpdateProductMaterial(@ModelAttribute ProductMaterial pm, HttpServletRequest req) {
        try {
            UserInfo user = super.getSessionUser(req);
            Integer id = productMaterialService.saveOrUpdateProductMaterial(pm, user);
            if (null != id && id > 0) {
                return super.putAPIData(id);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "未获取到项目ID");
            }
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }

    }

    /**
     * 上传产品资料对应的附件信息
     * 
     * @author TangYuping
     * @version 2017年3月29日 上午10:39:07
     * @param pmVo
     * @param mutipartFile
     * @param req
     * @return
     */
    @RequestMapping("/uploadProductFile")
    @ResponseBody
    public APIContent saveProductMaterialFile(@ModelAttribute ProductMaterialVo pmVo, 
            @RequestParam(value="files", required=false) MultipartFile multipartFile, 
            HttpServletRequest req){
        try {
            UserInfo user = super.getSessionUser(req);
            boolean  b = productMaterialService.uploadProductMaterialFile(pmVo, multipartFile, user);
            if (!b){
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "文件上传失败！");
            } 
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "文件上传失败！");
        } 
        
    }
    
//    @RequestMapping("/uploadProductFile")
//    @ResponseBody
//    public APIContent saveProductMaterialFile(@ModelAttribute ProductMaterialVo pmVo,
//            @RequestParam(value = "files", required = false) MultipartFile multipartFile, HttpServletRequest req) {
//        try {
//            UserInfo user = super.getSessionUser(req);
//            // boolean b = productMaterialService.uploadProductMaterialFile(pmVo, multipartFile, user);
//            String fileName = multipartFile.getName();
//            InputStream stream = multipartFile.getInputStream();
//            HSSFWorkbook hwb = new HSSFWorkbook(stream);
//            HSSFSheet sheet = hwb.getSheetAt(0);
//            HSSFRow row = null;
//            sheet = hwb.getSheetAt(0);
//            if (null != fileName) {
//                if ("dianzibomName".equals(pmVo.getFileType())) {
//                    for (int j = 4; j <= sheet.getLastRowNum(); j++) {
//                        Dzlmanage dzlmanage = new Dzlmanage();
//                        Material material = new Material();
//                        row = sheet.getRow(j);
//                        row.getCell(0).setCellType(1);
//                        row.getCell(1).setCellType(1);
//                        row.getCell(2).setCellType(1);
//                        row.getCell(3).setCellType(1);
//                        row.getCell(4).setCellType(1);
//                        row.getCell(5).setCellType(1);
//                        row.getCell(6).setCellType(1);
//                        row.getCell(7).setCellType(1);
//                        row.getCell(8).setCellType(1);
//                        row.getCell(9).setCellType(1);
//                        // 编码
//                        String proNO = row.getCell(1).getStringCellValue().trim();
//                        dzlmanage.setProNO(proNO);
//                        material.setMaterialType(0);
//                        material.setProNO(proNO);
//                        // 名称
//                        String proName = row.getCell(2).getStringCellValue().trim();
//                        dzlmanage.setProName(proName);
//                        material.setProName(proName);
//                        // 规格
//                        String proSpeci = row.getCell(3).getStringCellValue().trim();
//                        dzlmanage.setProSpeci(proSpeci);
//                        material.setProSpeci(proSpeci);
//                        // 位号
//                        String placesNO = row.getCell(4).getStringCellValue().trim();
//                        dzlmanage.setPlacesNO(placesNO);
//                        // 数量
//                        String consumption = row.getCell(5).getStringCellValue().trim();
//                        material.setConNum(Integer.valueOf(consumption));
//                        // 备注
//                        String remark = row.getCell(6).getStringCellValue().trim();
//                        dzlmanage.setRemark(remark);
//                        dzlmanage.setMasterOrSlave(remark);
//                        material.setRemark(remark);
//                        material.setMasterOrSlave(remark);
//                        // 厂商代码
//                        String factoryCode = row.getCell(6).getStringCellValue().trim();
//                        // 损耗
//                        String lossType = row.getCell(7).getStringCellValue().trim();
//                        material.setLossType(lossType);
//                        // 厂商
//                        String factory = row.getCell(8).getStringCellValue().trim();
//                        dzlmanage.setEnabledFlag(1);;
//                        materialService.saveMaterial(material);
//                        dzlmanageService.insertDzlInfo(dzlmanage);
//                    }
//                }
//                if ("bomName".equals(pmVo.getFileType())) {
//                    for (int j = 5; j <= sheet.getLastRowNum(); j++) {
//                        Pjlmanage pjlmanage = new Pjlmanage();
//                        Material material = new Material();
//                        row = sheet.getRow(j);
//                        row.getCell(0).setCellType(1);
//                        row.getCell(1).setCellType(1);
//                        row.getCell(2).setCellType(1);
//                        row.getCell(3).setCellType(1);
//                        row.getCell(4).setCellType(1);
//                        row.getCell(5).setCellType(1);
//                        row.getCell(6).setCellType(1);
//                        row.getCell(7).setCellType(1);
//                        row.getCell(8).setCellType(1);
//                        row.getCell(9).setCellType(1);
//                        // 编码
//                        String proNO = row.getCell(1).getStringCellValue().trim();
//                        pjlmanage.setProNO(proNO);
//                        material.setMaterialType(0);
//                        material.setProNO(proNO);
//                        // 名称
//                        String proName = row.getCell(2).getStringCellValue().trim();
//                        pjlmanage.setProName(proName);
//                        material.setProName(proName);
//                        // 规格
//                        String proSpeci = row.getCell(3).getStringCellValue().trim();
//                        pjlmanage.setProSpeci(proSpeci);
//                        material.setProSpeci(proSpeci);
//                        // 用量
//                        String consumption = row.getCell(4).getStringCellValue().trim();
//                        pjlmanage.setConsumption(Integer.valueOf(consumption));
//                        material.setConNum(Integer.valueOf(consumption));
//                        // M\T
//                        String masterOrSlave = row.getCell(5).getStringCellValue().trim();
//                        pjlmanage.setMasterOrSlave(masterOrSlave);
//                        material.setMasterOrSlave(masterOrSlave);
//                        material.setTotalNum(0);
//                        // // 厂商代码
//                        // String factoryCode = row.getCell(6).getStringCellValue().trim();
//                        // // 厂商
//                        // String factory = row.getCell(7).getStringCellValue().trim();
//                        // 损耗
//                        String lossType = row.getCell(8).getStringCellValue().trim();
//                        material.setLossType(lossType);
//                        // 备注
//                        String remark = row.getCell(9).getStringCellValue().trim();
//                        pjlmanage.setRemark(remark);
//                        material.setRemark(remark);
//                        materialService.saveMaterial(material);
//                        pjlmanageService.insertPjlInfo(pjlmanage);
//                    }
//                }
//            }
//
//            // if (!b){
//            // return super.operaStatus(Globals.OPERA_FAIL_CODE, "文件上传失败！");
//            // }
//            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
//        } catch (Exception e) {
//            return super.operaStatus(Globals.OPERA_FAIL_CODE, "文件上传失败！");
//        }
//
//    }

    /**
     * 获取单个产品资料信息及相关附件
     * 
     * @author TangYuping
     * @version 2017年3月30日 下午3:31:48
     * @param vo
     * @return
     */
    @RequestMapping("/getProductMaterialById")
    @ResponseBody
    public APIContent getProductMaterialById(@ModelAttribute ProductMaterial pm) {
        try {
            ProductMaterialVo productMaterialVo = productMaterialService.getProductMaterialByPid(pm);
            return super.putAPIData(productMaterialVo);
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取文件信息失败");
        }

    }

    /**
     * 下载产品资料里面的附件
     * 
     * @author TangYuping
     * @version 2017年3月31日 上午11:11:05
     * @param file
     * @param req
     * @param resp
     */
    @RequestMapping("/downLoadProductFile")
    @ResponseBody
    public void downProductFile(@ModelAttribute ProductMaterialFile file, HttpServletRequest req,
            HttpServletResponse response) {
        try {
            ProductMaterialFileLog pmfl = new ProductMaterialFileLog();// 下载日志记录
            pmfl.setIp(getIpAddress(req));
            pmfl.setUserName(super.getSessionUserName(req));
            pmfl.setFileName(file.getFileName());
            pmfl.setFileType(file.getFileType());
            pmfl.setDownloadTime(new Timestamp(System.currentTimeMillis()));
            productMaterialService.downLoadProductFile(file, req, response);
            productMaterialFileLogService.insert(pmfl);
        } catch (Exception e) {
            logger.error("下载文件失败:", e);
        }
    }

    /**
     * 删除产品资料，连带删除对应附件
     * 
     * @author TangYuping
     * @version 2017年3月31日 下午3:01:01
     * @param pm
     * @return
     */
    @RequestMapping("/deleteProdectMaterialById")
    @ResponseBody
    public APIContent deleteProdectMaterial(@ModelAttribute ProductMaterial pm) {
        try {
            productMaterialService.deleteProMaterialById(pm);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "删除文件信息失败！");
        }
    }

    /**
     * 根据文件ID删除文件
     * 
     * @author TangYuping
     * @version 2017年3月31日 下午2:59:26
     * @param file
     * @return
     */
    @RequestMapping("/deleteFileByFid")
    @ResponseBody
    public APIContent deleteFileByFid(@ModelAttribute ProductMaterialFile file) {
        try {
            productMaterialService.deleteFileByFid(file);
            return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "删除附件信息失败！");
        }
    }

    /**
     * 根据项目和附件类型查询已上传的附件列表
     * 
     * @param model
     * @param fileType
     * @return
     */
    @RequestMapping("/getProductFileListByProjectNameAndFileType")
    @ResponseBody
    public APIContent getProductFileListByProjectNameAndFileType(@ModelAttribute ProductMaterialFile file) {
        try {
            if (null != file && !StringUtil.isRealEmpty(file.getProjectName())
                    && !StringUtil.isRealEmpty(file.getFileType())) {
                List<ProductMaterialFile> fileList = productMaterialService
                        .getProductFileListByProjectNameAndFileType(file);
                return super.putAPIData(fileList);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取已上传附件列表失败,请先录入项目基本信息保存后,再选择附件");
            }
        } catch (Exception e) {
            return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取已上传附件列表失败");
        }
    }

    /**
     * 选择附件列表
     * 
     * @param req
     * @return
     */
    @RequestMapping("/chooseProductFileListByFid")
    @ResponseBody
    public APIContent chooseProductFileListByFid(HttpServletRequest req) {
        try {
            String fidTemp = req.getParameter("fids");
            String pid = req.getParameter("pid");
            if (null != fidTemp && !StringUtil.isEmpty(fidTemp) && null != pid && !StringUtil.isEmpty(pid)) {
                String fid[] = fidTemp.split(",");
                List<ProductMaterialFile> fileList = productMaterialService.getProductFileListByFid(fid);
                if (fileList.size() > 0) {
                    for (ProductMaterialFile f : fileList) {
                        ProductMaterialVo pmVo = new ProductMaterialVo();
                        pmVo.setId(Integer.valueOf(pid));
                        pmVo.setFileName(f.getFileName());
                        pmVo.setFileUrl(f.getFileUrl());
                        pmVo.setFileType(f.getFileType());
                        pmVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        pmVo.setCreateUser(super.getSessionUserName(req));
                        productMaterialService.getFileObj(pmVo);
                    }
                }
                return super.operaStatus(Globals.OPERA_SUCCESS_CODE, Globals.OPERA_SUCCESS_CODE_DESC);
            } else {
                return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
            }
        } catch (Exception e) {
            logger.error("选择附件列表失败");
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
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

    /**
     * 新增时查询编码数量,提示是否已存在
     */
    @RequestMapping("/getProNumCount")
    @ResponseBody
    public APIContent getProNumCount(@RequestParam String proNum, HttpServletRequest req) {
        try {
            int count = productMaterialService.getProNumCount(proNum);
            return super.putAPIData(count);
        } catch (Exception e) {
            logger.error("新增时查询编码数量失败");
            return super.operaStatus(Globals.OPERA_FAIL_CODE, Globals.OPERA_FAIL_CODE_DESC);
        }
    }
}
