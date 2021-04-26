///////////////////////////// Web 浏览器集成打印JS
/**
 * Web 浏览器集成打印JS 
 * @author Li.Shangzhi
 * @date   2016-09-09 16:01:24
 */
////////////////////////// 售后部门配置基本数据信息   ////////////////////

var commUnitname ="深圳市康凯斯信息技术有限公司";
var commContacts ="罗朝花";
var commAddress  ="广东省深圳市宝安区67区留仙一路高新奇工业园C栋4楼";
var commContactnm="13480175400"; 
var serviceTel = "4006165932";
var date = new Date();
var year = date.getFullYear();
var month = date.getMonth() + 1;
var strDate = date.getDate();
var currentDate = year+'-'+month+'-'+strDate;

//////////////////////////////////////////////////////////////
/**
 * 打印
 */
function InfoPrint(){
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length==1){
		var expressInfo   = updated[0];
		var expressCompany=updated[0].w_expressCompany;       //快递公司
		if(!expressCompany){
			$.messager.alert("提示信息","未选择快递公司，不能打印","info");
		}
		if(expressCompany=='顺丰速运'){
			CreateOnePageShunFeng(expressInfo);		   	
		}
		if(expressCompany=='百世汇通'){
			CreateOnePageBaiShi(expressInfo);   
		}
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要打印的行！","info");
	}else if(updated.length>1){
		$.messager.alert("提示信息","只能选择一行打印！","info");
	}
}

	var LODOP;  //声明为全局变量 
	/**
	* 顺丰速运
	**/
	function CreateOnePageShunFeng(expressInfo){
		LODOP=getLodop(); 
		if(LODOP){
			LODOP.SET_PREVIEW_WINDOW(1,2,0,980,580,"预览查看.开始打印");
	 		LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",1);	
			LODOP.SET_PRINT_MODE("PROGRAM_CONTENT_BYVAR",1);
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
			LODOP.SET_PRINT_STYLE("FontSize",12);
			//寄件人信息 LODOP.ADD_PRINT_TEXT(距上边距,左边距,文本宽度,文本高度)
			{
				LODOP.ADD_PRINT_TEXT(110,130,110,0,commUnitname);
				LODOP.ADD_PRINT_TEXT(110,340,110,28,commContacts);
				LODOP.ADD_PRINT_TEXT(145,100,330,28,commAddress);
				LODOP.ADD_PRINT_TEXT(165,170,150,28,commContactnm);
				LODOP.ADD_PRINT_TEXT(165,320,150,28,serviceTel);
				LODOP.ADD_PRINT_TEXT(400,120,300,28,currentDate);

			}
			//收件人信息
			{
				LODOP.ADD_PRINT_TEXT(195,150,110,0,expressInfo.w_cusName);	    //联系人
	            LODOP.ADD_PRINT_TEXT(195,345,110,28,expressInfo.w_linkman);     //单位
	            LODOP.ADD_PRINT_TEXT(245,100,330,28,expressInfo.w_address);   //地址
	            LODOP.ADD_PRINT_TEXT(270,250,150,28,expressInfo.w_phone);       //联系方式
	                    
			}
			//LODOP.SET_PRINT_STYLEA(1,"AngleOfPageInside",-90);
			LODOP.SET_PRINT_STYLEA(0,"AngleOfPageInside",0);
			//LODOP.PRINTA();
			LODOP.PREVIEW();
		}
 		
  
};
	
	/**
	* 百世汇通
	**/
	function CreateOnePageBaiShi(expressInfo){
		LODOP=getLodop();  
		if(LODOP){
			LODOP.SET_PREVIEW_WINDOW(1,2,0,980,580,"预览查看.开始打印");
	 		LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",1);	
			LODOP.SET_PRINT_MODE("PROGRAM_CONTENT_BYVAR",1);
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
			LODOP.SET_PRINT_STYLE("FontSize",12);
			//寄件人信息
			{
				LODOP.ADD_PRINT_TEXT(85,130,110,0,commContacts);
				LODOP.ADD_PRINT_TEXT(115,120,250,28,commUnitname);
				LODOP.ADD_PRINT_TEXT(170,85,330,28,commAddress);
				LODOP.ADD_PRINT_TEXT(223,120,150,28,commContactnm);
				LODOP.ADD_PRINT_TEXT(223,270,150,28,serviceTel);
				LODOP.ADD_PRINT_TEXT(390,65,300,28,currentDate);
				
			}
			//收件人信息
			{
	            LODOP.ADD_PRINT_TEXT(85,500,110,0,expressInfo.w_linkman);       //联系人
	            LODOP.ADD_PRINT_TEXT(115,500,220,28,expressInfo.w_cusName);      //单位
	            LODOP.ADD_PRINT_TEXT(170,440,330,28,expressInfo.w_address);    //地址
	            LODOP.ADD_PRINT_TEXT(223,500,150,28,expressInfo.w_phone);        //联系方式
			}
			//LODOP.SET_PRINT_STYLEA(1,"AngleOfPageInside",-90);
			LODOP.SET_PRINT_STYLEA(0,"AngleOfPageInside",0);
			//LODOP.PRINTA();
			LODOP.PREVIEW();
		}
 		
  
		
};	