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
 <Worksheet ss:Name="主板型号管理列表">
   <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
	    <Column ss:AutoFitWidth="0" ss:Width="150"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150"/>
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="5">
				<Data ss:Type="String">主板型号管理列表</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	   <#list peList as entity>
		   <Row ss:AutoFitHeight="0" ss:Height="24"> 
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.marketModel)!''}</Data></Cell>	<#-- === 市场型号   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.model)!''}</Data></Cell>   		<#-- === 主板型号 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.modelType)!''}</Data></Cell> 	<#-- === 型号类别  ==== -->
			<#if entity.createType??>
		    	<#if entity.createType==0>										  	  				  <#-- === 创建类型   ==== -->
				    <Cell ss:StyleID="s54"><Data ss:Type="String">默认</Data></Cell>      
				<#else>
				    <Cell ss:StyleID="s54"><Data ss:Type="String">产品</Data></Cell>     
			    </#if>
		    <#else>
		   	 	<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
		    </#if>
			<#if entity.enabledFlag??>
		    	<#if entity.enabledFlag==0>										  	  				  <#-- === 禁用  ==== -->
				    <Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell>      
				<#else>
				    <Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>     
			    </#if>
		    <#else>
		   	 	<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 
		    </#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remark)!''}</Data></Cell>     <#-- === 备注==== -->
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