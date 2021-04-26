package cn.concox.app.materialManage.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sso.comm.model.UserInfo;
import cn.concox.app.common.page.Page;
import cn.concox.app.materialManage.mapper.InteriorMaterialMapper;
import cn.concox.comm.util.FileUtil;
import cn.concox.vo.materialManage.InteriorMaterial;



/**
 * 
 * @author TangYuping
 * @version 2017年3月28日 上午11:31:35
 */
@Service("interiorMaterialService")
@Scope("prototype")
public class InteriorMaterialService {
	
	@Autowired
	private InteriorMaterialMapper<InteriorMaterial> interiorMaterialMapper;
	
	@Value("${file_upload_url}")
	private String FILE_UPLOAD_URL;

	/**
	 * 查询内部材料数据
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:32:55
	 * @param interiorMaterial
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<InteriorMaterial> interiorMaterialList(InteriorMaterial interiorMaterial, Integer currentPage, Integer pageSize){
		Page<InteriorMaterial> pages = new Page<InteriorMaterial>();
		pages.setCurrentPage(currentPage);
		pages.setSize(pageSize);
		pages = interiorMaterialMapper.interiorMaterialPageList(pages, interiorMaterial);
		return pages;
	}

	/**
	 * 保存修改内部材料
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:39:03
	 * @param interiorMaterial
	 * @param user
	 */
	public void saveOrUpdateInteriorMaterial(InteriorMaterial interiorMaterial, UserInfo user,
			MultipartFile multipartFile) throws Exception {	
		
			interiorMaterial.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			interiorMaterial.setUpdateUser(user.getuName());
		
			if ( null != multipartFile && multipartFile.getSize()>0) {
				boolean b = this.uploadInteriorMaterialFile(multipartFile, interiorMaterial);
				if (b){
					if (null != interiorMaterial.getId() && interiorMaterial.getId() >0 ) {
						// 修改就是删除文件，然后重新上传
						interiorMaterialMapper.update(interiorMaterial);
					} else {										
						interiorMaterial.setCreateTime(new Timestamp(System.currentTimeMillis()));
						interiorMaterial.setCreateUser(user.getuName());
						interiorMaterialMapper.insert(interiorMaterial);
					} 
					
				}else {
					throw new Exception("文件上传失败！");
				}
			} else {
				if (null != interiorMaterial.getId() && interiorMaterial.getId() >0 ) {
					// 修改就是删除文件，然后重新上传
					interiorMaterialMapper.update(interiorMaterial);
				}
			}						
	}
	
	/**
	 * 内部材料文件上传
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:42:26
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public boolean uploadInteriorMaterialFile(MultipartFile file, InteriorMaterial interiorMaterial) throws Exception {
			if (null != file && file.getSize()>0){
				String fileName=file.getOriginalFilename();//上传的文件名	
				if (null != fileName && !"".equals(fileName)) {
					String linuxFileName = "INTERIOR_"+UUID.randomUUID().toString();//服务器上面的文件名
					String suffix = fileName.substring(fileName.lastIndexOf("."));
					//本地测试路径，
//					String rootPath=getClass().getResource("/").getFile().toString();  
//					String uploadFilePath=rootPath.substring(0,rootPath.lastIndexOf("WEB-INF"))+"uploadfile/"+linuxFileName+suffix;
//					uploadFilePath = new File(uploadFilePath).getPath().replaceAll("%20", "");
					//线上路径
					String uploadFilePath = FILE_UPLOAD_URL+"interior/"+linuxFileName+suffix;	
					InputStream ins = file.getInputStream();	 //读取文件的输入流
					boolean b = FileUtil.readInputStreamToFile(ins, uploadFilePath);
					if (b) {
						interiorMaterial.setFileName(fileName);
						interiorMaterial.setFileUrl(uploadFilePath);
						return true;
					} else {
						throw new FileNotFoundException("文件上传失败！");
					}
				}
								
			} 
			return false;
		
	}
	
	/**
	 * 下载内部材料附件
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:48:21
	 * @param interiorMaterial
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	public void downLoadInteriorFile (InteriorMaterial interiorMaterial, HttpServletRequest req,
			HttpServletResponse response) throws Exception {		
		String fileUrl = URLDecoder.decode(interiorMaterial.getFileUrl(),"UTF-8");
		String fileName = interiorMaterial.getFileName();
		boolean b = FileUtil.downLoadFile(req, response, fileUrl, fileName);
		if (!b) {
			throw new IOException();
		}
	}

	/**
	 * 删除内部材料
	 * @author TangYuping
	 * @version 2017年3月31日 下午5:51:23
	 * @param interiorMaterial
	 * @throws Exception
	 */
	public void deleteInteriorMaterialById(InteriorMaterial interiorMaterial) throws Exception{
		if (null != interiorMaterial.getId() && interiorMaterial.getId() > 0) {
			interiorMaterialMapper.delete(interiorMaterial);
		} else {
			throw new Exception("无法获取ID");
		}
	}
}
