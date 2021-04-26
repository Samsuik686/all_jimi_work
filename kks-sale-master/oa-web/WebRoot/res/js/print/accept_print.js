///////////////////////////// Web 浏览器集成打印JS
/**
 * Web 浏览器集成打印JS 
 */
////////////////////////// 售后部门配置基本数据信息   ////////////////////

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
	if (updated.length>=1){
		CreateOnePage(updated);
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要打印的行！","info");
	}
}

	var LODOP;  //声明为全局变量 
	function CreateOnePage(expressInfo){
		LODOP=getLodop(); 
		if(expressInfo.length >=1){
			if(LODOP){
				for (var int = 0; int < expressInfo.length; int++) {
					LODOP.SET_PREVIEW_WINDOW(1,2,0,500,320,"预览查看.开始打印");
					LODOP.SET_PRINT_MODE("PROGRAM_CONTENT_BYVAR",1);
					LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
					LODOP.SET_PRINT_STYLE("FontSize",7);
					
					// LODOP.ADD_PRINT_TEXT(距上边距,左边距,文本宽度,文本高度)
					{
						LODOP.ADD_PRINT_TEXT(10,70,250,0,"维修登记标签");
					}
					{
						LODOP.ADD_PRINT_TEXT(22,10,35,0,"客户:");
						LODOP.ADD_PRINT_TEXT(22,35,120,28,expressInfo[int].w_cusName);
						LODOP.ADD_PRINT_TEXT(22,112,50,0,"保修:");
						LODOP.ADD_PRINT_TEXT(22,135,170,28,expressInfo[int].isWarranty);
					}
					{
						LODOP.ADD_PRINT_TEXT(34,10,35,0,"机型:");
						LODOP.ADD_PRINT_TEXT(34,35,120,28,expressInfo[int].w_model.substring(0,expressInfo[int].w_model.indexOf("|")).replace(/\ +/g,""));
						LODOP.ADD_PRINT_TEXT(34,103,60,0,"批备注:");
						LODOP.ADD_PRINT_TEXT(34,135,170,28,expressInfo[int].repairNumberRemark);
					}
					{
						LODOP.ADD_PRINT_TEXT(46,10,35,0,"IMEI:");
						LODOP.ADD_PRINT_TEXT(46,35,350,28,expressInfo[int].imei);
					}
					{
						LODOP.ADD_PRINT_TEXT(58,10,35,0,"故障:");
						LODOP.ADD_PRINT_TEXT(58,35,120,28,expressInfo[int].w_cjgzDesc);
					}
					{
						LODOP.ADD_PRINT_TEXT(70,10,35,0,"次数:");
						LODOP.ADD_PRINT_TEXT(70,35,120,28,expressInfo[int].returnTimes);
						LODOP.ADD_PRINT_TEXT(70,103,60,0,"返修人:");
						LODOP.ADD_PRINT_TEXT(70,135,170,28,expressInfo[int].lastEngineer);
					}
					{
						LODOP.ADD_PRINT_TEXT(82,10,60,0,"送修日期:");
						if(expressInfo[int].acceptanceTime.indexOf("</label>") != -1){
							var acceptanceTime = expressInfo[int].acceptanceTime;
							LODOP.ADD_PRINT_TEXT(82,50,160,28,acceptanceTime.substring(acceptanceTime.indexOf(">")+1, acceptanceTime.indexOf("</label>")).trim());
						}else{
							LODOP.ADD_PRINT_TEXT(82,50,160,28,expressInfo[int].acceptanceTime);
						}
					}
					{
						LODOP.ADD_PRINT_TEXT(94,10,60,0,"送修备注:");
						LODOP.ADD_PRINT_TEXT(94,50,350,28,expressInfo[int].remark);
					}
					{
						
						LODOP.ADD_PRINT_TEXT(106,10,60,0,"随机附件:");
						LODOP.ADD_PRINT_TEXT(106,50,120,28,expressInfo[int].w_sjfjDesc.replace(/\ +/g,""));
						
					}
					LODOP.SET_PRINT_STYLEA(0,"AngleOfPageInside",0);
					//LODOP.PRINTA();
					 LODOP.NewPageA();
				}
			}
			LODOP.PREVIEW();
		}
 		
  
};
