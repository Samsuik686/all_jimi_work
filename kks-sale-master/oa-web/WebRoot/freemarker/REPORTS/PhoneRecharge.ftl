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
 <Worksheet ss:Name="手机卡充值报表">
   <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
	    <Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="120.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="250.5"/>
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="12">
				<Data ss:Type="String">手机卡充值报表</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	   <#list peList as entity>
		  <Row ss:AutoFitHeight="0" ss:Height="24"> 
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.rechargeDate)!''}</Data></Cell>	  	<#-- === 充值日期   ==== -->
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.cusName)!''}</Data></Cell>	  	    <#-- === 客户名称==== -->
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.imei)!''}</Data></Cell>	  			<#-- === IMEI号   ==== -->
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.phone)!''}</Data></Cell>	  		<#-- === 手机号   ==== -->
		   	<#if entity.faceValue??>
			    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.faceValue)?string("#.##")}</Data></Cell> <#-- === 充值面值(￥)  ==== -->
		    <#else>  
		  		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			</#if>
		   	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.monthNumber)!''}</Data></Cell>   		<#-- === 月数 ==== -->		 
		  	<#if entity.payable??>
		   		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.payable)?string("#.##")}</Data></Cell>	<#-- === 收客户费用(￥)   ==== -->
		    <#else>  
		  		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			</#if>
			<#if entity.payOther??>
			    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.payOther)?string("#.##")}</Data></Cell> <#-- === 需付给卡商(￥)  ==== -->
		    <#else>  
		  		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			</#if>
			<#if entity.ratePrice??>
			    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.ratePrice)?string("#.##")}</Data></Cell> <#-- === 税费(￥)  ==== -->
		    <#else>  
		  		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			</#if>
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.orderStatus)!''}</Data></Cell>		<#-- === 状态==== -->
	   	 	<#if entity.remittanceMethod??>														<#-- === 汇款方式  ==== -->
			   	<#if entity.remittanceMethod=='personPay'>										  							
				    <Cell ss:StyleID="s54"><Data ss:Type="String">人工付款</Data></Cell> 
				<#elseif entity.remittanceMethod=='aliPay'>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">支付宝付款</Data></Cell>     
			    <#elseif entity.remittanceMethod=='weChatPay'>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">微信付款</Data></Cell>
				<#else> 
				    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remittanceMethod)}</Data></Cell>    
			    </#if>
			<#else>
				 <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		    </#if>
		   	
	    	<#if entity.isMobileCharge ? exists>														<#-- === 移动系统充值==== -->
			   	<#if entity.isMobileCharge==0>										  							
				    <Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>     
			    <#elseif entity.isMobileCharge==1>										  						
				    <Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>     
			    </#if>
			<#else>
				<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
		    </#if>
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remark)!''}</Data></Cell>			<#-- === 备注 ==== -->
		   </Row>
	    </#list>
 </Table>
 
 <Table ss:ExpandedColumnCount="${cosLenth1}" ss:ExpandedRowCount="${size1}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
	    <Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="1">
				<Data ss:Type="String">费用账单</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames1 as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	   <#list peList1 as entity>
		  <Row ss:AutoFitHeight="0" ss:Height="24"> 
		    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.projects)!''}</Data></Cell>	  	<#-- === 项目   ==== -->
		    <#if entity.costs??>
		   		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.costs)?string("#.##")}</Data></Cell>	<#-- === 费用(￥)   ==== -->
		    <#else>  
		  		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			</#if>
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