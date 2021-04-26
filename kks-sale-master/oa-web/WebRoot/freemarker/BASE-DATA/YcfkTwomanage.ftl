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
 <Worksheet ss:Name="客诉问题反馈跟进表--${exportDate}">
   <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
   		<Column ss:AutoFitWidth="0" ss:Width="50"/>
	    <Column ss:AutoFitWidth="0" ss:Width="70"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="70"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	    <Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="80"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="80"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="300"/>
	    <Column ss:AutoFitWidth="0" ss:Width="300"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="80"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="450"/>
	    <Column ss:AutoFitWidth="0" ss:Width="450"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="60"/>
	    <Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="70"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="25">
				<Data ss:Type="String">客诉问题反馈跟进表--${exportDate}</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	  
	   <#list peList as entity>
		   <Row ss:AutoFitHeight="0" ss:Height="24"> 
		  		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sortId)!''}</Data></Cell>            <#-- === 序号  ==== -->
		  		<#if entity.feedBackDate??>
					<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.feedBackDate)?string("yyyy-MM-dd")}</Data></Cell>          <#-- === 反馈日期  ==== -->
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>
		   		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.followupPeople)!''}</Data></Cell>       	<#-- === 跟进人 ==== -->
		   		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.currentSite)!''}</Data></Cell>       	<#-- === 设备所在工站 ==== -->
   		   		<#if entity.levelFlag??>
			     	<#if entity.levelFlag==0>									  	                       <#-- ===紧急度==== -->
					    <Cell ss:StyleID="s54"><Data ss:Type="String">一般</Data></Cell>      
					<#elseif entity.levelFlag==1>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">较紧急</Data></Cell> 
					<#elseif entity.levelFlag==2>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">紧急</Data></Cell>     
				    </#if>
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>
			   <#if entity.severityFlag??>
				   <#if entity.severityFlag==0>									  	                       <#-- ===问题严重程度==== -->
					   <Cell ss:StyleID="s54"><Data ss:Type="String">一般问题</Data></Cell>
				   <#elseif entity.severityFlag==1>
					   <Cell ss:StyleID="s54"><Data ss:Type="String">重要问题</Data></Cell>
				   <#elseif entity.severityFlag==2>
					   <Cell ss:StyleID="s54"><Data ss:Type="String">严重问题</Data></Cell>
				   <#elseif entity.severityFlag==3>
				   		<Cell ss:StyleID="s54"><Data ss:Type="String">重大问题</Data></Cell>
				   </#if>
			   <#else>
				   <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>
			   </#if>

		   		<#if entity.completionState??>
			     	<#if entity.completionState==0>									  	                       <#-- ===设备状态 待解决/已完成==== -->
					    <Cell ss:StyleID="s54"><Data ss:Type="String">待解决</Data></Cell>      
					<#elseif entity.completionState==1>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">已完成</Data></Cell> 
					<#elseif entity.completionState==2>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">处理中</Data></Cell>     
				    </#if>
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>
		    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.model)!''}</Data></Cell>            <#-- === 销售机型  ==== -->
		    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.imei)!''}</Data></Cell>	  			   <#-- === 设备imei   ==== -->
		    	
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.customerName)!''}</Data></Cell>           <#-- === 客户名称 ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.phone)!''}</Data></Cell>       	        <#-- === 联系电话 ==== -->
		    	<#if entity.warranty??>
			     	<#if entity.warranty==0>									  	                       <#-- ===保内==== -->
					    <Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>      
					<#else>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>     
				    </#if>
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>
		        <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.number)!''}</Data></Cell>       	       <#-- ===配件数量 ==== -->				
				
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.feedBackPerson)!''}</Data></Cell>        <#-- === 反馈人   ==== -->
		    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.description)!''}</Data></Cell>       	   <#-- ===问题描述 ==== -->
		    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.analysis)!''}</Data></Cell>		<#-- ===原因分析 ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.responsibilityUnit)!''}</Data></Cell>    <#-- === 责任单位 ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.recipient)!''}</Data></Cell>    <#-- === 责任人 ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.measures)?replace('<span>','')?replace('</span>','')?replace('<br>','')?html}</Data></Cell>       	    <#-- === 处理措施 ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.strategy)!''}</Data></Cell>  <#-- ===库存品处理策略==== -->
				<#if entity.checkResult??>
			     	<#if entity.checkResult==0>									  	                       <#-- ===验收结果 待解决/已完成==== -->
					    <Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>      
					<#elseif entity.checkResult==1>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">不合格</Data></Cell> 
					<#elseif entity.checkResult==2>
					    <Cell ss:StyleID="s54"><Data ss:Type="String">合格</Data></Cell>
					<#elseif entity.checkResult==3>
						<Cell ss:StyleID="s54"><Data ss:Type="String">有条件通过</Data></Cell>
				    </#if>
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.checker)!''}</Data></Cell>        <#-- === 验收人   ==== -->
			    <Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.copyPerson)!''}</Data></Cell>        <#-- === 抄送人   ==== -->
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.timeoutDate)!''}</Data></Cell>       	  <#-- === 未完成时间（天） ==== -->
				<#if entity.completionDate??>
					<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.completionDate)?string("yyyy-MM-dd")}</Data></Cell>       	<#-- === 完成时间 ==== -->								
				<#else>
					<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
				</#if>				
				<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remak)!''}</Data></Cell>       	        <#-- === 备注 ==== -->
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