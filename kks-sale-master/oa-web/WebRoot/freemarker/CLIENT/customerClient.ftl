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
 <Worksheet ss:Name="送修设备维修状态">
   <Table ss:ExpandedColumnCount="${cosLenth}" ss:ExpandedRowCount="${size}" x:FullColumns="1" x:FullRows="1" ss:DefaultColumnWidth="80" ss:DefaultRowHeight="13.5">
	    <Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	    <Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="100.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="280.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="200"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="250.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="250.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
	  	<Column ss:AutoFitWidth="0" ss:Width="150.5"/>
		<Row ss:StyleID="s50" ss:Height="44">
			<Cell ss:StyleID="s56" ss:MergeAcross="14">
				<Data ss:Type="String">送修设备维修状态</Data>
			</Cell>
		</Row>
	   <Row ss:AutoFitHeight="0" ss:StyleID="s51" ss:Height="28">
		   <#list fieldNames as entity>
		    	<Cell ss:StyleID="s53"><Data ss:Type="String">${entity}</Data></Cell>
			</#list>
	   </Row>
	   <#list peList as entity>
		   <Row ss:AutoFitHeight="0" ss:Height="24"> 
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.cusName)!''}</Data></Cell>	<#-- === 客户名称   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.repairnNmber)!''}</Data></Cell>	<#-- === 批次号  ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.imei)!''}</Data></Cell> 	<#-- === IMEI ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.marketModel)!''}</Data></Cell>     <#-- === 机型 ==== -->
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.acceptTime)!''}</Data></Cell>     <#-- === 受理时间 ==== -->
			<#if entity.isWarranty ? exists>	
	    		<#if entity.isWarranty == 0>
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>  <#-- === 保内|保外  是否保修 ==== -->
	    		<#elseif entity.isWarranty == 1 >
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell> 
	    		</#if>
	    	<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
	    	<#if entity.isRW ? exists>	
	    		<#if entity.isRW == "0">
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">是</Data></Cell>  <#-- === 是否人为 ==== -->
	    		<#elseif entity.isRW == "1" >
	    			<Cell ss:StyleID="s54"><Data ss:Type="String">否</Data></Cell> 
	    		</#if>
	    	<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell>   		
	    	</#if>
			<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.wxbjDesc)!''}</Data></Cell>	<#-- === 维修报价描述   ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.remark)!''}</Data></Cell>   		<#-- === 送修备注 ==== -->   		
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.zzgzDesc)!''}</Data></Cell>   		<#-- === 终检故障 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sjfjDesc)!''}</Data></Cell>   		<#-- === 随机附件 ==== -->
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.solutionTwo)!''}</Data></Cell>   		<#-- === 处理措施 ==== -->
	    	<#if entity.sumPrice??>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.sumPrice)!''}</Data></Cell>   		<#-- === 维修费 ==== -->			
	    		<#else>
	    		<Cell ss:StyleID="s54"><Data ss:Type="String">0.00</Data></Cell>   				
	    	</#if>
	    	<#if entity.state ? exists>
	    		<#if entity.state == 0>										  							
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
				<Cell ss:StyleID="s54"><Data ss:Type="String">已测试,待维修</Data></Cell> 
    			<#else>
    				<Cell ss:StyleID="s54"><Data ss:Type="String">已发货</Data></Cell>	
			    </#if>	
    		<#else>
    			<Cell ss:StyleID="s54"><Data ss:Type="String"></Data></Cell> 			 		
	    	</#if>
	    	<Cell ss:StyleID="s54"><Data ss:Type="String">${(entity.payTime)!''}</Data></Cell>   		<#-- === 支付时间 ==== -->  
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