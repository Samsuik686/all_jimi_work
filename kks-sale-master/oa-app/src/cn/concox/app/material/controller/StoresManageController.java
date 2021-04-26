package cn.concox.app.material.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.material.service.StoresManageService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.comm.vo.TreeNode;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.material.StoresManage;

/**
 * <pre>
 * StoresManageController 仓库管理
 * </pre>
 * 
 * @author Liao.bifeng
 * @version v1.0
 */
@Controller
@Scope("prototype")
public class StoresManageController extends BaseController {

	Logger log = Logger.getLogger(StoresManageController.class);
	@Resource(name = "storesManageService")
	private StoresManageService storesManageService;

	/*
	 * 增加或修改方法
	 * 
	 * @RequestMapping("storesManage/addOrUpdateStoresManage")
	 * 
	 * @ResponseBody public APIContent addOrUpdateCjgzmanage(HttpServletRequest
	 * req,HttpServletResponse resp,@ModelAttribute StoresManage storesManage){
	 * try { if(!storesManage.getId().equals("0")&&storesManage.getId()!=null){
	 * log.info("更新仓库信息信息开始"); storesManageService.update(storesManage); return
	 * new APIContent(Globals.OPERA_SUCCESS_CODE,"更新仓库信成功"+Globals.
	 * OPERA_SUCCESS_CODE_DESC); }else{ log.info("新增仓库信息开始");
	 * if(!StringUtils.isBlank(req.getParameter("outPutTypeNum"))){
	 * if(Integer.parseInt(req.getParameter("outPutTypeNum"))==1){
	 * storesManage.setOutPutType("出库"); }else{
	 * storesManage.setOutPutType("入库"); } } List<StoresManage>
	 * listStoresManage=storesManageService.queryList(storesManage);
	 * StoresManage storesManage1=new StoresManage();
	 * storesManage1.setId(UUID.randomUUID().toString());
	 * if(listStoresManage.size()>0){
	 * storesManage1.setDid(listStoresManage.get(0).getDid());
	 * storesManage1.setDepotName(listStoresManage.get(0).getDepotName());
	 * storesManage1.setOid(listStoresManage.get(0).getOid());
	 * storesManage1.setOutPutType(listStoresManage.get(0).getOutPutType());
	 * storesManage1.setTid(UUID.randomUUID().toString());
	 * storesManage1.setType(storesManage.getType());
	 * storesManage1.setEnableFalg(1L); Timestamp timestamp =new Timestamp
	 * (System.currentTimeMillis()); storesManage1.setCreateTime(timestamp);
	 * }else{ storesManage1.setDid(UUID.randomUUID().toString());
	 * storesManage1.setDepotName(storesManage.getDepotName());
	 * storesManage1.setOid(UUID.randomUUID().toString());
	 * storesManage1.setOutPutType(storesManage.getOutPutType());
	 * storesManage1.setTid(UUID.randomUUID().toString());
	 * storesManage1.setType(storesManage.getType());
	 * storesManage1.setEnableFalg(1L); Timestamp timestamp =new Timestamp
	 * (System.currentTimeMillis()); storesManage1.setCreateTime(timestamp); }
	 * storesManageService.insert(storesManage1); return new
	 * APIContent(Globals.OPERA_SUCCESS_CODE
	 * ,"新增仓库信成功"+Globals.OPERA_SUCCESS_CODE_DESC); } } catch (Exception e) {
	 * log.info("更新或新增仓库信息失败！"); return
	 * super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC);
	 * } }
	 */

	@RequestMapping("storesManage/addOrUpdateStoresManage")
	@ResponseBody
	public APIContent addOrUpdateCjgzmanage(HttpServletRequest req,
			HttpServletResponse resp, @ModelAttribute StoresManage storesManage) {
		try {

			log.info("新增仓库信息信息开始");
			if (!StringUtils.isBlank(req.getParameter("outPutTypeNum"))) {
				if (Integer.parseInt(req.getParameter("outPutTypeNum")) == 1) {
					storesManage.setOutPutType("出库");
				} else {
					storesManage.setOutPutType("入库");
				}
			}
			List<StoresManage> listStoresManage = storesManageService
					.queryList(storesManage);
			StoresManage storesManage1 = new StoresManage();
			storesManage1.setId(UUID.randomUUID().toString());
			if (listStoresManage.size() > 0) {
				storesManage1.setDid(listStoresManage.get(0).getDid());
				storesManage1.setDepotName(listStoresManage.get(0)
						.getDepotName());
				storesManage1.setOid(listStoresManage.get(0).getOid());
				storesManage1.setOutPutType(listStoresManage.get(0)
						.getOutPutType());
				storesManage1.setTid(UUID.randomUUID().toString());
				storesManage1.setType(storesManage.getType());
				storesManage1.setEnableFalg(1L);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				storesManage1.setCreateTime(timestamp);
			} else {
				storesManage1.setDid(UUID.randomUUID().toString());
				storesManage1.setDepotName(storesManage.getDepotName());
				storesManage1.setOid(UUID.randomUUID().toString());
				storesManage1.setOutPutType(storesManage.getOutPutType());
				storesManage1.setTid(UUID.randomUUID().toString());
				storesManage1.setType(storesManage.getType());
				storesManage1.setEnableFalg(1L);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				storesManage1.setCreateTime(timestamp);
			}
			storesManageService.insert(storesManage1);
			return new APIContent(Globals.OPERA_SUCCESS_CODE, "新增仓库信成功"
					+ Globals.OPERA_SUCCESS_CODE_DESC);

		} catch (Exception e) {
			log.error("更新或新增仓库信息失败！", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,
					Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/*
	 * 查询仓库类型
	 */
	@RequestMapping("storesManage/selectStoresManage")
	@ResponseBody
	public APIContent selectCjgzmanage(HttpServletRequest req,
			HttpServletResponse resp, @ModelAttribute StoresManage storesManage) {
		log.info("仓库信息查询开始");
		try {
			if (storesManage.getId() != null) {
				StoresManage storesManage1 = storesManageService
						.select(storesManage);
				return super.putAPIData(new APIContent(
						Globals.OPERA_SUCCESS_CODE, "查询成功!", storesManage1));
			} else {
				log.error("仓库信息查询失败！");
				return super.operaStatus(Globals.OPERA_FAIL_CODE,
						Globals.OPERA_FAIL_CODE_DESC);
			}

		} catch (Exception e) {
			log.error("仓库信息查询失败！", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,
					Globals.OPERA_FAIL_CODE_DESC);

		}
	}

	/*
	 * 删除仓库类型
	 */
	@RequestMapping("storesManage/deleteStoresManage")
	@ResponseBody
	public APIContent deleteCjgzmanage(HttpServletRequest req,
			HttpServletResponse resp, @ModelAttribute StoresManage storesManage) {
		log.info("删除仓库信息开始");
		try {
			List<StoresManage> listStoresManage = storesManageService
					.queryList(storesManage);
			for (int i = 0; i < listStoresManage.size(); i++) {
				listStoresManage.get(i).setEnableFalg(0L);
				storesManageService.update(listStoresManage.get(i));
			}
			return super.operaStatus(Globals.OPERA_SUCCESS_CODE, "仓库信息删除"
					+ Globals.OPERA_SUCCESS_CODE_DESC);
		} catch (Exception e) {
			log.error("删除仓库信息失败！", e);
			return super.operaStatus(Globals.OPERA_FAIL_CODE,
					Globals.OPERA_FAIL_CODE_DESC);

		}
	}

	/**
	 * 列表查询不分页树
	 */
	@RequestMapping("storesManage/queryListTrees")
	@ResponseBody
	public APIContent queryListTrees(HttpServletRequest req,
			HttpServletResponse resp, @ModelAttribute StoresManage storesManage) {
		log.info("列表查询开始");
		try {
			storesManage.setEnableFalg(1L);
			List<StoresManage> listStoresManage = storesManageService
					.queryList(storesManage);
			for (int i = 0; i < listStoresManage.size(); i++) {
				for (int j = (listStoresManage.size() - 1); j > i; j--) {
					if (listStoresManage.get(i).getDepotName()
							.equals(listStoresManage.get(j).getDepotName())) {
						listStoresManage.remove(i);
					}
				}
				if (i > 0) {
					for (int n = 0; n < i; n++) {
						if (i < listStoresManage.size()) {
							if (listStoresManage
									.get(n)
									.getDepotName()
									.equals(listStoresManage.get(i)
											.getDepotName())) {
								listStoresManage.remove(n);
							}
						}
					}
				}
			}
			TreeNode treeNode = new TreeNode("0", "所有仓库");
			List<TreeNode> children1 = new ArrayList<TreeNode>();
			for (StoresManage storesManages : listStoresManage) {
				TreeNode treeNode1 = new TreeNode(storesManages.getDid(),
						storesManages.getDepotName());
				StoresManage storesManage1 = new StoresManage();
				storesManage1.setEnableFalg(1L);
				storesManage1.setDid(storesManages.getDid());
				List<StoresManage> listStoresManage2 = storesManageService
						.queryList(storesManage1);
				for (int i = 0; i < listStoresManage2.size(); i++) {
					if (listStoresManage2.get(i).getOutPutType() != null
							&& !listStoresManage2.get(i).getOutPutType()
									.equals("")) {
						for (int j = (listStoresManage2.size() - 1); j > i; j--) {
							if (listStoresManage2.get(j).getOutPutType() != null
									&& !listStoresManage2.get(j)
											.getOutPutType().equals("")) {
								if (listStoresManage2
										.get(i)
										.getOutPutType()
										.equals(listStoresManage2.get(j)
												.getOutPutType())) {
									listStoresManage2.remove(i);
								}
							}
						}
					}
				}
				List<TreeNode> children2 = new ArrayList<TreeNode>();
				for (StoresManage storesManage2 : listStoresManage2) {
					TreeNode treeNode2 = new TreeNode(storesManage2.getOid(),
							storesManage2.getOutPutType());
					if (storesManage2.getOid() != null
							&& !storesManage2.getOid().equals("")) {
						storesManage1.setOid(storesManage2.getOid());
						List<StoresManage> listStoresManage3 = storesManageService
								.queryList(storesManage1);

						List<TreeNode> children3 = new ArrayList<TreeNode>();
						for (StoresManage storesManage3 : listStoresManage3) {
							if (storesManage3.getTid() != null
									&& !storesManage3.getTid().equals("")) {
								TreeNode treeNode3 = new TreeNode(
										storesManage3.getTid(),
										storesManage3.getType());
								children3.add(treeNode3);
							}
						}
						treeNode2.setChildren(children3);
						children2.add(treeNode2);
					}

				}
				treeNode1.setChildren(children2);
				children1.add(treeNode1);
			}
			treeNode.setChildren(children1);
			return super.putAPIData(treeNode);
		} catch (Exception e) {
			log.error("获取故障列表失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,
					Globals.OPERA_FAIL_CODE_DESC);
		}
	}

	/**
	 * 查询列表
	 * 
	 */
	@RequestMapping("storesManage/queryList")
	@ResponseBody
	public APIContent queryList(@ModelAttribute StoresManage storesManage) {
		log.info("列表查询开始");
		try {
			storesManage.setEnableFalg(1L);
			List<StoresManage> listStoresManage = storesManageService
					.queryList(storesManage);
			return super.putAPIData(listStoresManage);
		} catch (Exception e) {
			log.error("获取仓库信息列表失败:" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,
					Globals.OPERA_FAIL_CODE_DESC);
		}

	}

}
