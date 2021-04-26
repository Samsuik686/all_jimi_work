<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/wechat/comm/comm.jsp" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<meta name="format-detection" content="telephone=no"/>  
<meta name="format-detection" content="email=no" />
<title>几米终端客户服务</title> 
<link rel="stylesheet" type="text/css" href="${ctx}/wechat/css/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/wechat/css/site.css">
<script type="text/javascript" src="${ctx}/wechat/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/wechat/js/zepto.js"></script>
<script type="text/javascript" src="${ctx}/wechat/js/tab.js"></script>
<script type="text/javascript" src="${ctx}/wechat/js/zepto.js"></script>
<script type="text/javascript" src="${ctx}/wechat/js/qrcode.js"></script>
<script src="${ctx}/wechat/js/template/template.js"></script> 
<script src="${ctx}/wechat/js/template/template-plugin.js"></script>
<script src="${ctx}/wechat/js/template/template-plugin.js"></script>
<style type="text/css">
  .fixed-header {  padding-top:5.0rem;}
  .order-number { font-size:1.6rem; color:#fff; padding:1rem 1.5rem;}
  .order-list li { position: relative; padding: 1.5rem; background-color: #fff; font-size: 1.4rem; }
  .order-list li h3 { margin-right: 5rem; color: #333; font-weight: 500; }
  .order-list li p { color: #666; }
  .order-list li p b {font-weight: 500;}
  .order-list li .showInfo { position: absolute; top:1.5rem; right: 1.5rem; padding: .2rem .5rem; border-radius: .2rem; font-size: 1rem;  background-color: #00c556;  color:#fff; }
</style>
</head>
<body class="bc-eee fixed-header">
<header class="header">
  <div class="order-number bc-primary">手机号：<span >${PHONENUMBER}</span></div>
</header>
<main >
<div id="repairNumber">
	<script type="text/html" id="repairNumber-info">
		<ul class="order-list list-bb b-t_5">
			{{each reslut as workflow index}}
				<li>
					<h3>序号：{{index + 1}}</h3>
					<p><b>客户名称：</b>{{workflow.w_cusName}}</p>
					<p><b>批次号：</b>{{workflow.repairnNmber}}</p>
					<p><b>受理日期：</b>{{workflow.acceptanceTime | dateFormat:'yyyy-MM-dd'}}</p>
					<p><b>装箱日期：</b>{{workflow.w_packTime | dateFormat:'yyyy-MM-dd'}}</p>
					<p><b>状态：</b>{{workflow.stateStr}}</p>
					<p style="display:none;" id="repairNumber_{{index}}">{{workflow.repairnNmber}}</p>
					<span class="showInfo" onclick="clickRepairNumber({{index}})">查看</span>
				</li>
			{{/each}}
		</ul>
	</script>
</div>

<script type="text/javascript">
$(function(){
	initOrders();
});

//加载全部数据
function initOrders(){
	$.ajax({
		url : ctx+"/wechatdev/doGetRepairNumber",
		type : 'post', 
		async:false,
		dataType : 'json',
		data : null,
		cache : false,
		success : function(returnData) {
			if(returnData.code=="0"){				
				var resultData = returnData.data;
				var data = {"reslut":returnData.data}
				$("#repairNumber").html(template("repairNumber-info",data));
			}
		}
	});
}

// 查看批次号详情
function clickRepairNumber(index){
	$.ajax({
		url : ctx+"/wechatdev/showRepairNumberInfo",
		type : 'post', 
		async:false,
		dataType : 'json',
		data : {"repairNumber":$("#repairNumber_" + index).text()},
		cache : false,
		success : function(returnData) {
			if(returnData.code=="0"){				
				window.location.href=ctx+"/wechat/orderList.jsp";
			}
		}
	});
}

</script>
</main>
</body>