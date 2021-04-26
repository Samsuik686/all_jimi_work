package cn.concox.app.materialManage.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.service.DzlmanageService;
import cn.concox.app.basicdata.service.PjlmanageService;
import cn.concox.app.common.page.Page;
import cn.concox.app.materialManage.mapper.ProductMaterialMapper;
import cn.concox.comm.Globals;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.FileUtil;
import cn.concox.vo.basicdata.Dzlmanage;
import cn.concox.vo.basicdata.Pjlmanage;
import cn.concox.vo.materialManage.ProductMaterial;
import cn.concox.vo.materialManage.ProductMaterialFile;
import cn.concox.vo.materialManage.ProductMaterialVo;

/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:31:35
 */
@Service("productMaterialService")
@Scope("prototype")
public class ProductMaterialService {

    @Autowired
    private ProductMaterialMapper<ProductMaterial> productMaterialMapper;

    @Autowired
    private PjlmanageService pjlmanageService;

    @Autowired
    private DzlmanageService dzlmanageService;

    @Value("${file_upload_url}")
    private String FILE_UPLOAD_URL;

    /**
     * 查询产品资料数据列表
     * 
     * @author TangYuping
     * @version 2017年3月28日 下午2:05:52
     * @param pm
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<ProductMaterial> productMaterialList(ProductMaterial pm, Integer currentPage, Integer pageSize) {
        Page<ProductMaterial> pages = new Page<ProductMaterial>();
        pages.setCurrentPage(currentPage);
        pages.setSize(pageSize);
        pages = productMaterialMapper.productMaterialPageList(pages, pm);
        for (ProductMaterial p : pages.getResult()) {
            List<ProductMaterialFile> pmfList = productMaterialMapper
                    .getProductFileCountListByPid(p.getId().toString());
            for (ProductMaterialFile pmf : pmfList) {
                if (null != pmf.getFileType() && !"".equals(pmf.getFileType())) {
                    if ("bomName".equals(pmf.getFileType())) {
                        p.setBomCount(pmf.getFileTypeCout());
                    } else if ("dianzibomName".equals(pmf.getFileType())) {
                        p.setDianzibomCount(pmf.getFileTypeCout());
                    } else if ("repairName".equals(pmf.getFileType())) {
                        p.setRepairCount(pmf.getFileTypeCout());
                    } else if ("differenceName".equals(pmf.getFileType())) {
                        p.setDifferenceCount(pmf.getFileTypeCout());
                    } else if ("projectRemarkName".equals(pmf.getFileType())) {
                        p.setProjectRemarkCount(pmf.getFileTypeCout());
                    } else if ("softwareName".equals(pmf.getFileType())) {
                        p.setSoftwareCount(pmf.getFileTypeCout());
                    } else if ("explainName".equals(pmf.getFileType())) {
                        p.setExplainCount(pmf.getFileTypeCout());
                    } else if ("kfName".equals(pmf.getFileType())) {
                        p.setKfCount(pmf.getFileTypeCout());
                    } else if ("softDifName".equals(pmf.getFileType())) {
                        p.setSoftDifCount(pmf.getFileTypeCout());
                    } else if ("datumName".equals(pmf.getFileType())) {
                        p.setDatumCount(pmf.getFileTypeCout());
                    } else if ("huaceName".equals(pmf.getFileType())) {
                        p.setHuaceCount(pmf.getFileTypeCout());
                    } else if ("functionName".equals(pmf.getFileType())) {
                        p.setFunctionCount(pmf.getFileTypeCout());
                    } else if ("zhilingName".equals(pmf.getFileType())) {
                        p.setZhilingCount(pmf.getFileTypeCout());
                    } else if ("productRemarkName".equals(pmf.getFileType())) {
                        p.setProductRemarkCount(pmf.getFileTypeCout());
                    } else if ("zuzhuangName".equals(pmf.getFileType())) {
                        p.setZuzhuangCount(pmf.getFileTypeCout());
                    } else if ("testName".equals(pmf.getFileType())) {
                        p.setTestCount(pmf.getFileTypeCout());
                    } else if ("testToolName".equals(pmf.getFileType())) {
                        p.setTestToolCount(pmf.getFileTypeCout());
                    } else if ("xiehaoTooName".equals(pmf.getFileType())) {
                        p.setXiehaoTooCount(pmf.getFileTypeCout());
                    } else if ("qualityRemarkName".equals(pmf.getFileType())) {
                        p.setQualityRemarkCount(pmf.getFileTypeCout());
                    }
                }
            }

        }
        return pages;
    }

    /**
     * 保存，修改产品资料的基本信息
     * 
     * @author TangYuping
     * @version 2017年3月29日 上午10:44:33
     * @param pm
     */
    public Integer saveOrUpdateProductMaterial(ProductMaterial pm, UserInfo user) {
        pm.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        pm.setUpdateUser(user.getuName());
        if (null != pm.getId() && pm.getId() > 0) {
            productMaterialMapper.update(pm);
        } else {
            pm.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pm.setCreateUser(user.getuName());
            productMaterialMapper.insert(pm);
        }
        return pm.getId();

    }

    /**
     * 产品资料文件上传
     * 
     * @author TangYuping
     * @version 2017年3月29日 下午2:04:38
     * @param pmVo
     * @param file
     * @param user
     * @throws Exception
     */
    public boolean uploadProductMaterialFile(ProductMaterialVo pmVo, MultipartFile file, UserInfo user)
            throws Exception {
        if (null != pmVo.getFileType()) {
            if (null != file && file.getSize() > 0) {
                String fileName = file.getOriginalFilename();// 上传的文件名
                if (null != fileName && !"".equals(fileName)) {
                    String suffix = fileName.substring(fileName.lastIndexOf("."));
                    String linuxFileName = "PRODUCT_" + UUID.randomUUID().toString();// 服务器上面的文件名
                    // 本地测试路径，
                    // String rootPath=getClass().getResource("/").getFile().toString();
                    // String
                    // uploadFilePath=rootPath.substring(0,rootPath.lastIndexOf("WEB-INF"))+"uploadfile/"+linuxFileName+suffix;
                    // uploadFilePath = new File(uploadFilePath).getPath().replaceAll("%20", "");

                    // 本地测试start -h
                    // File f = new File("/uploadFile/");
                    // if(!f.exists()){
                    // f.mkdirs();
                    // }
                    // String uploadFilePath=f.getAbsolutePath()+"/"+fileName;
                    // 本地测试end -h

                    // 线上路径
//                     String uploadFilePath = FILE_UPLOAD_URL + "project/" + linuxFileName + suffix;
//                     InputStream ins = file.getInputStream(); // 读取文件的输入流
//                     boolean b = FileUtil.readInputStreamToFile(ins, uploadFilePath);
//                     if (b) {
//                     // 文件上传成功
//                     pmVo.setFileName(fileName);
//                     pmVo.setFileUrl(uploadFilePath);
//                     pmVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
//                     pmVo.setCreateUser(user.getuName());
//                     this.getFileObj(pmVo);

                    if ("bomName".equals(pmVo.getFileType())) {
                        importPjl(pmVo, file);
                    }
                    if ("dianzibomName".equals(pmVo.getFileType())) {
                        importDzl(pmVo, file);
                    }
                    return true;
//                     } else {
//                     throw new FileNotFoundException("文件上传失败！");
//                     }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private void importPjl(ProductMaterialVo pmVo, MultipartFile file) throws Exception {
        Object[] result = ReadExcel.readXlsForPom(file, 5);
        List<Pjlmanage> list = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        for (Object obj : result) {
            if (obj == null) {
                continue;
            }
            Object[] row = (Object[]) obj;
            boolean flag = false;
            for (int i = 0; i < row.length; i++) {
                String str = (String) row[i];
                if (str.indexOf("包装物料") > -1 || str.indexOf("制表人") > -1) {
                    flag = true;
                    break;
                }
                map.put(i, str);
            }
            Pjlmanage pjl = new Pjlmanage();

            if (StringUtils.isBlank(map.get(1)) || flag) {
                continue;
            }
            pjl.setProNO(map.get(1));

            if (StringUtils.isNotBlank(map.get(2))) {
                pjl.setProName(map.get(2));
            }
            if (StringUtils.isNotBlank(map.get(3))) {
                pjl.setProSpeci(map.get(3));
            }
            if (StringUtils.isNotBlank(map.get(4))) {
                pjl.setConsumption(Integer.valueOf(map.get(4)));
            }
            if (StringUtils.isNotBlank(map.get(5))) {
                pjl.setMasterOrSlave(map.get(5));
            }
            pjl.setModel(pmVo.getModel());
            pjl.setMarketModel(pmVo.getMarketModel());
            pjl.setHourFee(new BigDecimal(0));
            pjl.setRetailPrice(new BigDecimal(0));

            list.add(pjl);
            map.clear();
        }

        if ("bomName".equals(pmVo.getFileType())) {
            for (Pjlmanage pjlmanage : list) {
                int isExist = pjlmanageService.isExists(pjlmanage);
                if (isExist == 0) {
                    pjlmanageService.insertPjlInfo(pjlmanage);
                }
            }
        }
    }

    private void importDzl(ProductMaterialVo pmVo, MultipartFile file) throws Exception {
        Object[] result = ReadExcel.readXlsForPom(file, 4);
        List<Dzlmanage> list = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();

        for (Object obj : result) {
            if (obj == null) {
                continue;
            }
            Object[] row = (Object[]) obj;
            boolean flag = false;
            for (int i = 0; i < row.length; i++) {
                String str = (String) row[i];
                if (str.indexOf("包装物料") > -1 || str.indexOf("制表人") > -1) {
                    flag = true;
                    break;
                }
                map.put(i, str);
            }
            Dzlmanage dzl = new Dzlmanage();

            if (StringUtils.isNotBlank(map.get(1)) || flag) {
                continue;
            }
            dzl.setProNO(map.get(1));

            if (StringUtils.isNotBlank(map.get(2))) {
                dzl.setProName(map.get(2));
            }
            if (StringUtils.isNotBlank(map.get(3))) {
                dzl.setProSpeci(map.get(3));
            }
            if (StringUtils.isNotBlank(map.get(4))) {
                dzl.setConsumption(Integer.valueOf(map.get(4)));
            }
            if (StringUtils.isNotBlank(map.get(5))) {
                dzl.setMasterOrSlave(map.get(5));
            }

            list.add(dzl);
            map.clear();
        }

        if ("dianzibomName".equals(pmVo.getFileType())) {
            for (Dzlmanage dzlmanage : list) {
                int isExist = dzlmanageService.isExists(dzlmanage);
                if (isExist > 0) {
                    dzlmanageService.insertDzlInfo(dzlmanage);
                }
            }
        }
    }

    /**
     * 添加附件信息到数据库
     * 
     * @author TangYuping
     * @version 2017年3月29日 下午2:23:25
     * @param fileType
     */
    public void getFileObj(ProductMaterialVo pmVo) {
        ProductMaterialFile proFile = new ProductMaterialFile(pmVo);
        productMaterialMapper.insertProductFile(proFile);
    }

    /**
     * 查询单个产品信息及产品附件
     * 
     * @author TangYuping
     * @version 2017年3月30日 上午11:30:02
     * @param vo
     * @return
     */
    public ProductMaterialVo getProductMaterialByPid(ProductMaterial pm) {
        if (null != pm.getId() && pm.getId() > 0) {
            ProductMaterial product = productMaterialMapper.productMaterialPageList(pm);
            ProductMaterialVo vo = new ProductMaterialVo(product);
            List<ProductMaterialFile> fileList = productMaterialMapper.getProductFileListByPid(pm.getId().toString());
            vo.setFileList(fileList);
            return vo;
        }
        return new ProductMaterialVo();
    }

    /**
     * 下载产品资料里面的附件
     * 
     * @author TangYuping
     * @version 2017年3月31日 上午11:13:58
     * @param file
     */
    public void downLoadProductFile(ProductMaterialFile file, HttpServletRequest req, HttpServletResponse response)
            throws Exception {
        String fileUrl = URLDecoder.decode(file.getFileUrl(), "UTF-8");
        String fileName = file.getFileName();
        boolean b = FileUtil.downLoadFile(req, response, fileUrl, fileName);
        if (!b) {
            throw new IOException();
        }
    }

    /**
     * 删除产品资料及相关附件
     * 
     * @author TangYuping
     * @version 2017年3月31日 下午3:11:34
     * @param pm
     */
    public void deleteProMaterialById(ProductMaterial pm) throws Exception {
        if (null != pm.getId() && pm.getId() > 0) {
            ProductMaterialFile file = new ProductMaterialFile();
            file.setDeleteType("proId");
            file.setPid(pm.getId());
            productMaterialMapper.deleteProductFileById(file);
            productMaterialMapper.delete(pm);

        } else {
            throw new Exception("无法获取ID");
        }

    }

    /**
     * 删除产品资料对应的附件
     * 
     * @author TangYuping
     * @version 2017年3月31日 下午3:12:15
     * @param file
     */
    public void deleteFileByFid(ProductMaterialFile file) throws Exception {
        if (null != file.getFid() && file.getFid() > 0) {
            productMaterialMapper.deleteProductFileById(file);
        } else {
            throw new Exception("无法获取ID");
        }
    }

    /**
     * 根据项目和附件类型查询已上传的附件列表
     * 
     * @param model
     * @param fileType
     * @return
     */
    public List<ProductMaterialFile> getProductFileListByProjectNameAndFileType(ProductMaterialFile proFile) {
        return productMaterialMapper.getProductFileListByProjectNameAndFileType(proFile);
    }

    public List<ProductMaterialFile> getProductFileListByFid(String... fid) {
        return productMaterialMapper.getProductFileListByFid(fid);
    }

    /**
     * @Title: getProNumCount
     * @Description:新增时查询编码数量,提示是否已存在
     * @param proNum
     * @return
     * @author HuangGangQiang
     * @date 2017年6月22日 下午6:28:27
     */
    public int getProNumCount(String proNum) {
        return productMaterialMapper.getProNumCount(proNum);
    }
}
