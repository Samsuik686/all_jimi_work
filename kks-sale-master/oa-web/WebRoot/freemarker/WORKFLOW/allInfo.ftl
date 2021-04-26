<?xml version="1.0"  encoding="UTF-8"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
		  xmlns:o="urn:schemas-microsoft-com:office:office"
		  xmlns:x="urn:schemas-microsoft-com:office:excel"
		  xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
		  xmlns:html="http://www.w3.org/TR/REC-html40">
	<DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
		<Created>2016-07-18T11:21:51Z</Created>
		<LastSaved>2016-07-18T11:21:55Z</LastSaved>
		<Version>1.00</Version>
	</DocumentProperties>

	<OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
	 <RemovePersonalInformation/>
	</OfficeDocumentSettings>
 
  <#-- ============= 基本属性   =============== -->
	<ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
		 <WindowHeight>11640</WindowHeight>
		 <WindowWidth>19200</WindowWidth>
		 <WindowTopX>0</WindowTopX>
		 <WindowTopY>90</WindowTopY>
		 <ProtectStructure>False</ProtectStructure>
		 <ProtectWindows>False</ProtectWindows>
	</ExcelWorkbook>
	<Styles>
		<Style ss:ID="s50">
			<Alignment ss:Horizontal="Center" />
		</Style>
		<Style ss:ID="s51">
			<Alignment />
			<Borders />
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000" />
			<Interior />
			<NumberFormat />
			<Protection />
		</Style>
		<Style ss:ID="s53">
			<Alignment ss:Horizontal="Center" />
			<Borders>
				<Border ss:Position="Bottom" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Left" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Right" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Top" ss:LineStyle="Continuous"
					ss:Weight="1" />
			</Borders>
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"
				ss:Bold="1" />
			<Interior ss:Color="#E7E6E6" ss:Pattern="Solid" />
			<NumberFormat />
			<Protection />
		</Style>
		<Style ss:ID="s54">
			<Alignment ss:Horizontal="Center" />
			<Borders>
				<Border ss:Position="Bottom" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Left" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Right" ss:LineStyle="Continuous"
					ss:Weight="1" />
			</Borders>
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000" />
			<Interior />
			<NumberFormat />
			<Protection />
		</Style>
		<Style ss:ID="s56">
			<Alignment ss:Horizontal="Center" />
			<Borders>
				<Border ss:Position="Left" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Right" ss:LineStyle="Continuous"
					ss:Weight="1" />
				<Border ss:Position="Top" ss:LineStyle="Continuous"
					ss:Weight="1" />
			</Borders>
			<Font ss:FontName="宋体" x:CharSet="134" ss:Size="16" ss:Color="#000000"
				ss:Bold="1" />
			<Interior ss:Color="#E7E6E6" ss:Pattern="Solid" />
			<NumberFormat />
		</Style>
	</Styles>
 <Worksheet ss:Name="设备维修单">
   <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="150" ss:DefaultRowHeight="13.5">
	    <Column ss:Index="14" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="15" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="16" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="17" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="18" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="22" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="28" AutoFitWidth="0" ss:Width="280"/>
	    <Column ss:Index="29" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="30" AutoFitWidth="0" ss:Width="280"/>
	    <Column ss:Index="31" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="33" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="40" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="42" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="43" AutoFitWidth="0" ss:Width="280"/>
	    <Column ss:Index="44" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="46" AutoFitWidth="0" ss:Width="280"/>
	    <Column ss:Index="48" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="52" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="54" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="61" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="62" AutoFitWidth="0" ss:Width="200"/>
	    <Column ss:Index="69" AutoFitWidth="0" ss:Width="200"/>
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="73">
				<Data ss:Type="String">设备维修单</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	   <#list peList as entity>
		   <Row ss:AutoFitHeight="0" ss:Height="24"> 
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_cusName)!''}</Data></Cell>							<#-- === 客户名称   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_phone)!''}</Data></Cell>   							<#-- === 客户手机号 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.repairnNmber)!''}</Data></Cell>     					<#-- === 送修批号 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.imei)!''}</Data></Cell>									<#-- === IMEI   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.lockId)!''}</Data></Cell>								<#-- === 智能锁ID  ==== -->
    		<#if entity.state??>																							<#-- === 状态  ==== -->
			   	<#if entity.state==-1>										  							
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已发货</Data></Cell> 
				<#elseif entity.state == 0>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已受理</Data></Cell>     
			    <#elseif entity.state == 1>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已受理，待分拣</Data></Cell>     
			    <#elseif entity.state == 2>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已分拣，待维修</Data></Cell>     
			    <#elseif entity.state == 3>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">待报价</Data></Cell>  
				<#elseif entity.state == 4>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已付款，待维修</Data></Cell>     
			    <#elseif entity.state == 5>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已维修，待终检</Data></Cell>     
			    <#elseif entity.state == 6>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已终检，待维修</Data></Cell>     
			    <#elseif entity.state == 7>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已终检，待装箱</Data></Cell> 
				<#elseif entity.state == 8>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">放弃报价，待装箱</Data></Cell>     
			    <#elseif entity.state == 9>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">已报价，待付款</Data></Cell>     
			    <#elseif entity.state == 10>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">不报价，待维修</Data></Cell>     
			    <#elseif entity.state == 11>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">放弃报价，待维修</Data></Cell> 
    			<#elseif entity.state  == 12>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">已维修，待报价</Data></Cell> 
    			<#elseif entity.state  == 13>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">待终检</Data></Cell>
				<#elseif entity.state  == 14>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">放弃维修</Data></Cell>  
			    <#elseif entity.state  == 15>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">测试中</Data></Cell>  
			    <#elseif entity.state  == 16>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">已测试，待维修</Data></Cell>  
			    </#if>
			<#else>
				 <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		    </#if>
	    	
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_marketModel)!''}</Data></Cell>    	 				<#-- === 市场型号 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_model)!''}</Data></Cell>     							<#-- === 主板型号 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.insideSoftModel)!''}</Data></Cell>     							<#-- === 内部机型 ==== -->
			<#if entity.isWarranty ? exists>																				<#-- === 保内|保外 ==== -->
	    		<#if entity.isWarranty == 0>
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">保内</Data></Cell>  
	    		<#elseif entity.isWarranty ==1 >
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">保外</Data></Cell> 
	    		</#if>
	    	<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
    		<#if entity.w_isRW ? exists>
				<#if entity.w_isRW == 0>
					<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>   										<#-- === 是否人为 ==== -->
		    	<#else>
		    		<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>  
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.acceptanceTime)!''}</Data></Cell>						<#-- === 受理时间   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.salesTime)!''}</Data></Cell>							<#-- === 出货日期   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sfVersion)!''}</Data></Cell>							<#-- === 软件版本号   ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remark)!''}</Data></Cell>   							<#-- === 送修备注 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_cjgzDesc)!''}</Data></Cell>    						<#-- === 初检故障 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_zzgzDesc)!''}</Data></Cell>     						<#-- === 最终故障==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_solution)!''}</Data></Cell>    	 				<#-- === 处理措施 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_expressNO)!''}</Data></Cell>    	 					<#-- === 快递单号==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_expressCompany)!''}</Data></Cell>    	 				<#-- === 快递公司==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.returnTimes)!''}</Data></Cell>							<#-- === 返修次数   ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.acceptRemark)!''}</Data></Cell>   						<#-- === 受理备注 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_engineer)!''}</Data></Cell>    	 					<#-- === 维修员 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.accepter)!''}</Data></Cell>   							<#-- === 受理人 ==== -->
	    	<#if entity.payDelivery ? exists>																				<#-- === 客户寄货方式==== -->
		    	<#if entity.payDelivery == 'Y'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">到付</Data></Cell>     
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String">顺付</Data></Cell>    
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
    		<#if entity.customerStatus ? exists>																			<#-- === 无名件==== -->
		    	<#if entity.customerStatus == 'normal'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">正常</Data></Cell>     
				<#elseif entity.customerStatus == 'un_normal'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">无名件</Data></Cell>    
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_linkman)!''}</Data></Cell>   							<#-- === 联系人 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_address)!''}</Data></Cell>   							<#-- === 通信地址 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_email)!''}</Data></Cell>   							<#-- === Email ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_wxbjDesc)!''}</Data></Cell>     						<#-- === 维修报价==== -->
			 <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_repairRemark)!''}</Data></Cell>    					<#-- === 维修备注 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_modelType)!''}</Data></Cell>    	 					<#-- === 型号类别 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.repairNumberRemark)!''}</Data></Cell>    				<#-- === 批次备注 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.lastEngineer)!''}</Data></Cell>							<#-- === 上次维修人员 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.lastAccTime)!''}</Data></Cell>     						<#-- === 上次受理时间 ==== -->
			<#if entity.payedLogCost ? exists>																				<#-- === 是否已支付物流费 ==== -->
		    	<#if entity.payedLogCost == '0'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>     
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>    
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_unNormal_expressCompany)!''}</Data></Cell>    	 	<#-- === 寄来无名件的快递公司 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_unNormal_expressNO)!''}</Data></Cell>     			<#-- === 寄来无名件的快递单号==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.dutyOfficer)!''}</Data></Cell>    	 					<#-- === 超三天责任人 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.timeoutRemark)!''}</Data></Cell>    	 				<#-- === 超三天备注 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sortTime)!''}</Data></Cell>     						<#-- === 分拣日期==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_sjfjDesc)!''}</Data></Cell>    	 					<#-- === 随机附件 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_matDesc)!''}</Data></Cell>    						<#-- === 组件料 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_dzlDesc)!''}</Data></Cell>    	 					<#-- === 电子料 ==== -->
			 <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_repairAgainCount)!''}</Data></Cell>    				<#-- === 内部二次返修次数 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_priceRemark)!''}</Data></Cell>    	 				<#-- === 维修报价说明 ==== -->
			<#if entity.w_isPrice ? exists>																					<#-- === 放弃报价 ==== -->
				<#if entity.w_isPrice == '1'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>   		
		    	<#else>
		    		<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell> 
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(w_solutionTwo.w_solutionTwo)!''}</Data></Cell>    	 			<#-- === 处理措施2==== -->
			<#if entity.w_giveUpRepairStatus ? exists>																		<#-- === 放弃维修状态 ==== -->
				<#if entity.w_giveUpRepairStatus == '0'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">正常</Data></Cell>   		
		    	<#else>
		    		<Cell ss:StyleID="s54"><Data ss:Type="String">放弃维修</Data></Cell>  
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.price_createTime)!''}</Data></Cell>     				<#-- === 发送报价时间==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_offer)!''}</Data></Cell>     							<#-- === 报价人==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_onePriceRemark)!''}</Data></Cell>    					<#-- === 单台报价备注 ==== -->
			<#if entity.w_customerReceipt ? exists>																			<#-- === 客户收货方式==== -->
		    	<#if entity.w_customerReceipt == 'N'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">到付</Data></Cell>     
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String">顺付</Data></Cell>    
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_price_Remark)!''}</Data></Cell>     						<#-- === 批次报价备注==== -->
	    	<#if entity.w_repairMoney ? exists>												
	    		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_repairMoney)?string("#.##")}</Data></Cell>    	<#--  === 维修费 ==== -->
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_payReason)!''}</Data></Cell>     						<#-- === 付款原因==== -->
			<#if entity.w_remAccount??>																						<#-- === 付款方式  ==== -->
			   	<#if entity.w_remAccount=='personPay'>										  							
				    <Cell ss:StyleID="s54"><Data ss:Type="String">人工付款</Data></Cell> 
				<#elseif entity.w_remAccount=='aliPay'>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">支付宝付款</Data></Cell>     
			    <#elseif entity.w_remAccount=='weChatPay'>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">微信付款</Data></Cell>
				<#else> 
				    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_remAccount)}</Data></Cell>    
			    </#if>
			<#else>
				 <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		    </#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_payTime)!''}</Data></Cell>     						<#-- === 付款时间==== -->
			<#if entity.w_isPay ? exists>																					<#-- === 是否付款 ==== -->
				<#if entity.w_isPay == 0>
					<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>   		
		    	<#else>
		    		<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>  
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<#if entity.w_ispass ? exists>																					<#-- === 是否合格==== -->
				<#if entity.w_ispass == '0'>
					<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>   		
		    	<#else>
		    		<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>  
				</#if>
			<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_cusRemark)!''}</Data></Cell>    	 					<#-- === 送修单位备注==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_FinDesc)!''}</Data></Cell>     						<#-- === 终检备注==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_FinChecker)!''}</Data></Cell>    						<#-- === 终检人==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_packTime)!''}</Data></Cell>    	 					<#-- === 装箱时间==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sendPackTime)!''}</Data></Cell>    	 						<#-- === 发送装箱时间==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_packingNO)!''}</Data></Cell>    	 					<#-- === 装箱单号==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_packer)!''}</Data></Cell>     						<#-- === 装箱人==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_shipper)!''}</Data></Cell>    	 					<#-- === 发货方==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_packRemark)!''}</Data></Cell>     					<#-- === 装箱备注==== -->
			
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testPerson)!''}</Data></Cell>     						<#-- === 测试员==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.dataSource)!''}</Data></Cell>     						<#-- === 测试数据来源站==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sendTime)!''}</Data></Cell>    	 						<#-- === 发送测试时间==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testResult)!''}</Data></Cell>    	 					<#-- === 测试结果==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testTime)!''}</Data></Cell>    	 						<#-- === 测试时间==== -->
		  </Row>
	   </#list>
 </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
	   <PageSetup>
		    <Header x:Margin="0.3"/>
		    <Footer x:Margin="0.3"/>
		    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
	   </PageSetup>
	   
	   <Print>
		    <ValidPrinterInfo/>
		    <PaperSizeIndex>9</PaperSizeIndex>
		    <HorizontalResolution>200</HorizontalResolution>
		    <VerticalResolution>200</VerticalResolution>
	   </Print>
	   
	   <Selected/>
		   <Panes>
			    <Pane>
				     <Number>3</Number>
				     <ActiveRow>8</ActiveRow>
				     <ActiveCol>3</ActiveCol>
			    </Pane>
		   </Panes>
	   <ProtectObjects>False</ProtectObjects>
	   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>