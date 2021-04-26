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
  .order-number { font-size:1.6rem; color:#fff; padding:1rem 1.5rem;}
  .menu { background-color: #fff; text-align: center; box-shadow: 0 1px 2px rgba(0,0,0,.1) }
  .menu ul{display:-webkit-flex; display:flex;} 
  .menu ul li{-webkit-flex:1; flex:1; padding:1.2rem 0; font-size: 1.6rem;}
  .menu ul li.active { color:#f17100;font-weight: 500; }
  .tab-con .con { display: none; }
  .order-list li { position: relative; padding: 1.5rem; background-color: #fff; font-size: 1.4rem; }
  .order-list li h3 { margin-right: 5rem; color: #333; font-weight: 500; }
  .order-list li p { color: #666; }
  .order-list li p b {font-weight: 500;}
  .order-list li .battery { position: absolute; top:1.5rem; right: 1.5rem; padding: .2rem .5rem; border-radius: .2rem; font-size: 1rem;  background-color: #00c556;  color:#fff; }
  .order-list li.repair .battery {background-color:#6495ED;}
  .order-list li.offer .battery {background-color:#ef9802;}
  .price-text {font-size: 12px;}
</style>

</head>
<body class="bc-eee fixed-header">
<header class="header">
  <div class="order-number bc-primary">送修批次：<span>${BATCHNUMBER}</span></div>
  <menu class="menu">
    <ul class="tab-nav">
      <li class="active">全部</li>
      <li>维修中</li>
      <li>待付款</li>
      <li>付款</li>
    </ul>
  </menu>
</header>
<main >
<div class="tab-con">
  <!-- 全部  index =0-->
  <div class="con" id="allOrder"></div>
  
  <!-- 维修中 index =1-->
  <div class="con" id="repOrder"></div>
  
  <!-- 待报价 index =2-->
  <div class="con" id="picOrder"></div>
  
  <!-- 待报价金额 index =3-->
    <div class="con">
	    <div class="payment ta-c">
	    	<div>
			   	<span class="price-text">批次号：${BATCHNUMBER}</span>
		  	</div>
	    	<div style="display: inline-block;">
			   	<span class="price-text" id="sumLogs">物流费：￥0.00</span>
			   	<span class="price-text" id="sumRepair">维修费：￥0.00</span>			   	  
			   	<span class="price-text" id="batchPjCosts">配件费：￥0.00</span>  	
			   	<span class="price-text" id="ratePrice">税费：￥0.00</span> 
		   </div>
		   		 <p> <span class="price-text" id="payPrice">总费用：￥0.00</span> </p>	    	     
	     <br><br>
		      <p class="payment-text" id="title-pay" style="color: blue;">待付款总金额</p>
		      <p class="payment-number" id="totalprice">￥0.00</p> 
	      <br>
	     	 <div id="code" align="center" ></div>  
	   		 </div>
   		 <br> <br> <br> 
	   	 <div class="payment-text" id="reminStr" align="center">长按二维码识别付款</div>  
  </div>

</div>

<!-- <div class="no-data" data-content="暂无数据"></div> -->

<script type="text/html" id="allOrder-info">
	<ul class="order-list list-bb b-t_5">
		{{each reslut as client index}}
			<li class="repair">
			{{if client.state=='已报价，待付款'}}
      			<li class="offer">
			{{/if}}
			{{if client.state=='已受理' || client.state=='已受理，待分拣' || client.state=='已分拣，待维修'
 				|| client.state=='待报价' || client.state=='已付款，待维修' || client.state=='已维修，待终检'
				|| client.state=='已终检，待维修' || client.state=='已终检，待装箱' || client.state=='放弃报价，待装箱'
				|| client.state=='不报价，待维修' || client.state=='放弃报价，待维修' || client.state=='已维修，待报价' 
				|| client.state=='待终检' || client.state=='放弃维修' || client.state=='测试中'
				|| client.state=='已测试,待维修' }}
			{{/if}}
			{{if client.state=='已发货'}}
      			<li>
			{{/if}}
			<h3>IMEI：{{client.imei}}</h3>
        	<p><b>机型：</b>{{client.marketModel}}</p>
        	<p><b>终检故障：</b>{{client.zzgzDesc}}</p>
			<p><b>维修费：</b>{{client.sumPrice == null ? 0 : client.sumPrice}}元</p>
			<p><b>维修报价描述 ：</b>{{client.wxbjDesc}}</p>
        	<span class="battery">{{client.state}}</span>
      		</li>
		{{/each}}
    </ul>
</script>
<script type="text/html" id="repOrder-info">    
	<ul class="order-list list-bb b-t_5">
		{{each reslut as client index}}
			{{if client.state=='已受理' || client.state=='已受理，待分拣' || client.state=='已分拣，待维修'
				|| client.state=='已维修，待终检' || client.state=='已终检，待维修' || client.state=='不报价，待维修' 
				|| client.state=='待报价' || client.state=='已付款，待维修' || client.state=='已终检，待装箱' 
				|| client.state=='放弃报价，待装箱' || client.state=='放弃报价，待维修' || client.state=='已维修，待报价' 
				|| client.state=='待终检' || client.state=='放弃维修' || client.state=='已测试,待维修' }}
      			<li class="repair">
        			<h3>IMEI：{{client.imei}}</h3>
        			<p><b>机型：</b>{{client.marketModel}}</p>
        			<p><b>终检故障：</b>{{client.zzgzDesc}}</p>
					<p><b>维修费：</b>{{client.sumPrice == null ? 0 : client.sumPrice}}元</p>
					<p><b>维修报价描述 ：</b>{{client.wxbjDesc}}</p>
        			<span class="battery">{{client.state}}</span>
      			</li>
			{{/if}}
		{{/each}}
  </ul>
</script>
<script type="text/html" id="picOrder-info">
	<ul class="order-list list-bb b-t_5">
		{{each reslut as client index}}
			{{if client.state=='已报价，待付款' }}
      			<li class="offer">
        			<h3>IMEI：{{client.imei}}</h3>
        			<p><b>机型：</b>{{client.marketModel}}</p>
        			<p><b>终检故障：</b>{{client.zzgzDesc}}</p>
					<p><b>维修费：</b>{{client.sumPrice == null ? 0 : client.sumPrice}}元</p>
					<p><b>维修报价描述 ：</b>{{client.wxbjDesc}}</p>
        			<span class="battery">{{client.state}}</span>
      			</li>
 			{{/if}}
		{{/each}}
    </ul>
</script>

<script type="text/javascript">
initOrders('ALL');

tabs(".tab-nav","active",".tab-con",function(index){
	if(index==0){
		initOrders('ALL');
	}else if(index==1){
		initOrders('REP');
	}else if(index==2){
		initOrders('PIC');
	}else if(index==3){
		initreallyPrc();
	}
});

//加载全部数据
function initOrders(stateFlag){
	$.ajax({
		url : ctx+"/wechatdev/doGetOrders",
		type : 'post', 
		async:false,
		dataType : 'json',
		data : null,
		cache : false,
		success : function(returnData) {
			if(returnData.code=="0"){				
				var resultData = returnData.data;
				$.each(resultData,function(i, item){
				  if(item.state==-1){
						item.state="已发货";
					}else if(item.state==0){
						item.state="已受理";
					}else if(item.state==1){
						item.state="已受理，待分拣";
					}else if(item.state==2){
						item.state="已分拣，待维修";
					}else if(item.state==3){
						item.state="待报价";
					}else if(item.state==4){
						item.state="已付款，待维修";
					}else if(item.state==5){
						item.state="已维修，待终检";
					}else if(item.state==6){
						item.state="已终检，待维修";
					}else if(item.state==7){
						item.state="已终检，待装箱";
					}else if(item.state==8){
						item.state="放弃报价，待装箱";
					}else if(item.state==9){
						item.state="已报价，待付款";
					}else if(item.state==10){
						item.state="不报价，待维修";
					}else if(item.state==11){
						item.state="放弃报价，待维修";
					}else if(item.state==12){
						item.state="已维修，待报价";
					}else if(item.state==13){
						item.state="待终检";
					}else if(item.state==14){
						item.state="放弃维修";
					}else{
						item.state="维修中";
					}
					if(item.zzgzDesc==undefined){
						item.zzgzDesc="";
					}
				});
				var data = {"reslut":returnData.data}
				if(stateFlag =='ALL'){
					$("#allOrder").html(template("allOrder-info",data));
				}else if(stateFlag =='REP'){
					$("#repOrder").html(template("repOrder-info",data));
				}else if(stateFlag =='PIC'){
					$("#picOrder").html(template("picOrder-info",data));
				}
			}
		}
	});
}
//加载待付款实际费用
function initreallyPrc(){
	initPrice();
	$.ajax({
		url : ctx+"/wechatdev/getTotalPrice",
		type : 'post', 
		async:false,
		dataType : 'json',
		data : null,
		cache : false,
		success : function(returnData) {
			if(returnData.code=="0"){
				if(returnData.data.totalPrice){
					$("#payPrice").html("总费用："+returnData.data.totalPrice+"元");
				}else{
					$("#payPrice").html("总费用：0元");
				}
				if(returnData.data.sumRepair){
					$("#sumRepair").html("维修费："+returnData.data.sumRepair+"元 | ");
				}else{
					$("#sumRepair").html("维修费：0元 | ");
				}
				if(returnData.data.logCost){
					$("#sumLogs").html("物流费："+returnData.data.logCost+"元 | ");
				}else{
					$("#sumLogs").html("物流费：0元 | ");
				}
				if(returnData.data.ratePrice){
					$("#ratePrice").html("税费："+returnData.data.ratePrice+"元");
				}else{
					$("#ratePrice").html("税费：0元");
				}
				if(returnData.data.batchPjCosts){
					$("#batchPjCosts").html("配件费："+returnData.data.batchPjCosts+"元 |");
				}else{
					$("#batchPjCosts").html("配件费：0元 | ");
				}
				
				$("#totalprice").html("￥"+returnData.data.totalPrice);
				var qrcode = new QRCode('code', {
					  text: returnData.msg,
					  width: 180,
					  height: 180,
					  colorDark : '#000000',
					  colorLight : '#ffffff',
					  correctLevel : QRCode.CorrectLevel.H
				});
				// 使用 API
				qrcode.clear();
				qrcode.makeCode(returnData.msg); 
				$("#reminStr").show();
				$("#totalprice").show();
				$("#title-pay").html("待付款总金额");
			}else{
				$("#title-pay").html(returnData.msg);
				$("#reminStr").hide();
				$("#totalprice").hide();
			}
		}
	});
}

function initPrice(){
	$("#code").html("");
	$("#totalprice").html("￥0.00");
	$("#sumRepair").html("维修费：￥0.00");
	$("#sumLogs").html("物流费：￥0.00");
	$("#ratePrice").html("税费：￥0.00");
	$("#batchPjCosts").html("配件费：￥0.00");
	$("#reminStr").hide();
}
</script>
</main>
</body>