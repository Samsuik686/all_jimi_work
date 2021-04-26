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
 <Worksheet ss:Name="未寄出设备报表">
  <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
    <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
   <Column ss:AutoFitWidth="0" ss:Width="125"/>
	<Column ss:AutoFitWidth="0" ss:Width="125"/>
	  <Column ss:AutoFitWidth="0" ss:Width="125"/>
	  <Column ss:AutoFitWidth="0" ss:Width="100"/>
   <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  <Column ss:AutoFitWidth="0" ss:Width="150"/>

   <Row ss:StyleID="s50" ss:Height="44">
		<Cell ss:StyleID="s56" ss:MergeAcross="16">
			<Data ss:Type="String">未寄出设备报表</Data>
		</Cell>
   </Row>
   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
	   <#list fieldNames as entity>
	    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
	   </#list>
   </Row>
   <#list peList as entity>
	   <Row ss:AutoFitHeight="0" ss:Height="24">
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity).acceptanceTime!''}</Data></Cell>   		<#-- === 送修单位  ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.repairnNmber)!''}</Data></Cell>	  	<#-- === 批次号  ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_cusName)!''}</Data></Cell>	  			<#-- === 客户名称    ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.imei)!''}</Data></Cell>	  	    <#-- === IMEI ==== -->
		   <#if entity.state ? exists>																			<#-- === 工站==== -->
			   <#if entity.state==0 >
				   <Cell ss:StyleID="s54"><Data ss:Type="String">受理</Data></Cell>
			   <#elseif entity.state==1>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">分拣</Data></Cell>
			   <#elseif entity.state==2 || entity.state==5 || entity.state==10 || entity.state==11 || entity.state==12 || entity.state==16>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">维修</Data></Cell>
			   <#elseif entity.state==3 || entity.state==9 || entity.state==14>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">报价</Data></Cell>
			   <#elseif entity.state==13>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">终检</Data></Cell>
			   <#elseif entity.state==-1>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">已发货</Data></Cell>
			   <#elseif entity.state==15>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">测试</Data></Cell>
			   <#elseif entity.state==4>
					<#if entity.w_priceState==-1 >
				   		<Cell ss:StyleID="s54"><Data ss:Type="String">维修</Data></Cell>
				   	<#elseif entity.w_repairState==-1 >
				   		<Cell ss:StyleID="s54"><Data ss:Type="String">报价</Data></Cell>
					</#if>
			   <#elseif entity.state==6>
				   <#if entity.w_FinispassFalg==-1 >
					   <Cell ss:StyleID="s54"><Data ss:Type="String">维修</Data></Cell>
				   <#elseif entity.w_repairState==-1 >
					   <Cell ss:StyleID="s54"><Data ss:Type="String">终检</Data></Cell>
				   </#if>
			   <#elseif entity.state==7>
				   <#if entity.w_FinispassFalg==-1 >
					   <Cell ss:StyleID="s54"><Data ss:Type="String">装箱</Data></Cell>
				   <#else>
					   <Cell ss:StyleID="s54"><Data ss:Type="String">终检</Data></Cell>
				   </#if>
			   <#elseif entity.state==8>
				   <#if entity.w_repairState==-1 >
					   <Cell ss:StyleID="s54"><Data ss:Type="String">装箱</Data></Cell>
				   <#else>
					   <Cell ss:StyleID="s54"><Data ss:Type="String">维修</Data></Cell>
				   </#if>
			   <#elseif entity.state==17>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">受理</Data></Cell>
			   <#elseif entity.state==18>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">分拣</Data></Cell>
			   <#elseif entity.state==19>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">测试</Data></Cell>
			   </#if>
		   <#else>
			   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		   </#if>

		   <#if entity.state??>																							<#-- === 进度  ==== -->
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
			   <#elseif entity.state  == 17>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">已受理，已测试</Data></Cell>
			   <#elseif entity.state  == 18>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">已测试，待分拣</Data></Cell>
			   <#elseif entity.state  == 18>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">不报价，测试中</Data></Cell>
			   <#else>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">不报价，测试中</Data></Cell>

			   </#if>
		   <#else>
			   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		   </#if>
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_model)!''}</Data></Cell>	  		<#-- === 主板型号   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_marketModel)!''}</Data></Cell>	  		<#-- === 市场型号   ==== -->
		  	 <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_engineer)!''}</Data></Cell>	  		<#-- === 维修员   ==== -->
		   <#if entity.state ? exists>																			<#-- === 已至装箱==== -->
			   <#if entity.state==-1 >
				   <Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>
			   <#else >
				   <Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>
			   </#if>
		   <#else>
			   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		   </#if>
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_sendFinTime)!''}</Data></Cell>	  		<#-- === 发送终检时间   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_payTime)!''}</Data></Cell>	  		<#-- === 客户付款日期   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.price_createTime)!''}</Data></Cell>	  		<#-- === 发送报价日期   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_sjfjDesc)!''}</Data></Cell>	  		<#-- === 随机附件    ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.returnTimes)!''}</Data></Cell>	  		<#-- === 返修次数   ==== -->
		   <#if entity.isWarranty ? exists>																			<#-- === 保内|保外==== -->
			   <#if entity.isWarranty==0>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">保内</Data></Cell>
			   <#else >
				   <Cell ss:StyleID="s54"><Data ss:Type="String">保外</Data></Cell>
			   </#if>
		   <#else>
			   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		   </#if>
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_cjgzDesc)!''}</Data></Cell>	  		<#-- === 初检故障    ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_zzgzDesc)!''}</Data></Cell>	  		<#-- === 最终故障   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_solution)!''}</Data></Cell>	  		<#-- === 处理方法   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.lockId)!''}</Data></Cell>	  		<#-- === 智能锁ID   ==== -->
		   <#if entity.w_isRW ? exists>																			<#-- === 是否人为==== -->
			   <#if entity.w_isRW==0>
				   <Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>
			   <#else >
				   <Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>
			   </#if>
		   <#else>
			   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		   </#if>	  		  		<#-- === 是否人为   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_repairPrice)!''}</Data></Cell>	  		<#-- === 维修报价(￥)    ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.w_wxbjDesc)!''}</Data></Cell>	  		<#-- === 报价描述    ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testPerson)!''}</Data></Cell>	  		<#-- === 测试人员   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testResult)!''}</Data></Cell>	  		<#-- === 测试结果   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remark)!''}</Data></Cell>	  		<#-- === 送修备注   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.acceptRemark)!''}</Data></Cell>	  		<#-- === 受理备注   ==== -->
		   <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.testResult)!''}</Data></Cell>	  		<#-- === 批次备注   ==== -->



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
