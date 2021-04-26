/**
 * bpmn-js-seed
 * 
 * This is an example script that loads an embedded diagram file <diagramXML>
 * and opens it using the bpmn-js modeler.
 */
(function(BpmnModeler, $) {

	var xml = document.getElementById('xml').value;
	var forward = document.getElementById('forward').value;

	function asyncCallService(url, type, asyncType, dataType, param, success,
			error) {
		var getAsyncType;
		if (asyncType == null || '' == asyncType) {
			getAsyncType = true;
		} else {
			getAsyncType = asyncType;
		}

		$.ajax({
			url : url,
			type : type,
			async : getAsyncType,
			dataType : dataType,
			data : param,
			cache : false,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(result) {
				success.call(this, result);
			},
			error : function(result) {
				if (error != undefined && error != null)
					error.call(this, result);
			}
		});
	}

	// create modeler
	var bpmnModeler = new BpmnModeler({
		container : '#canvas'
	});

	// import function
	function importXML(xml) {

		// import diagram
		bpmnModeler.importXML(xml, function(err) {

			if (err) {
				return console.error('could not import BPMN 2.0 diagram', err);
			}

			var canvas = bpmnModeler.get('canvas');

			// zoom to fit full viewport
			canvas.zoom('fit-viewport');
		});

		// save diagram on button click
		var saveButton = document.querySelector('#save-button');

		saveButton.addEventListener('click', function() {

			// get the diagram contents
			bpmnModeler.saveXML({
				format : true
			}, function(err, xml) {

				if (err) {
					console.error('diagram save failed', err);
				} else {
					var selParams = {
						"xml" : xml,
						"remark" : document.getElementById('remark').value
					};
					var url = ctx + "/customFlow/insertCustomFlow";
					asyncCallService(url, 'post', false, 'json', selParams,
							function(returnData) {
								if (returnData.code == '0') {
									if (window.confirm('流程图创建成功，是否关闭当前页面？')) {
										window.close();
										return;
									}
								} else {
									alert(returnData.msg);
								}
								$("#DataGrid1").datagrid('loaded');
								getPageSize();
								resetCurrentPageShow(currentPageNum);
							}, function() {
								$("#DataGrid1").datagrid('loaded');
								$.messager.alert("操作提示", "网络错误", "info");
							});
				}
			});
		});
	}

	// a diagram to display
	//
	// see index-async.js on how to load the diagram asynchronously from a url.
	// (requires a running webserver)
	
	if ('2' == forward) {
		var diagramXML = xml;
	} else if ('1' == forward) {
		var diagramXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" targetNamespace=\"\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd\"><collaboration id=\"sid-c0e745ff-361e-4afb-8c8d-2a1fc32b1424\"><participant id=\"sid-87F4C1D6-25E1-4A45-9DA7-AD945993D06F\" name=\"点此输入流程图名称\" processRef=\"sid-C3803939-0872-457F-8336-EAE484DC4A04\" /></collaboration><process id=\"sid-C3803939-0872-457F-8336-EAE484DC4A04\" name=\"Customer\" processType=\"None\" isClosed=\"false\" isExecutable=\"false\"><extensionElements /><laneSet id=\"sid-b167d0d7-e761-4636-9200-76b7f0e8e83a\"><lane id=\"sid-57E4FE0D-18E4-478D-BC5D-B15164E93254\"><flowNodeRef>sid-D7F237E8-56D0-4283-A3CE-4F0EFE446138</flowNodeRef><flowNodeRef>sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26</flowNodeRef><flowNodeRef>sid-E433566C-2289-4BEB-A19C-1697048900D2</flowNodeRef><flowNodeRef>sid-E49425CF-8287-4798-B622-D2A7D78EF00B</flowNodeRef></lane></laneSet><startEvent id=\"sid-D7F237E8-56D0-4283-A3CE-4F0EFE446138\" name=\"开始\"><outgoing>SequenceFlow_1bpeqj6</outgoing></startEvent><task id=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26\" name=\"任务1\"><incoming>SequenceFlow_1bpeqj6</incoming><incoming>SequenceFlow_0gy7w7x</incoming><outgoing>sid-EE8A7BA0-5D66-4F8B-80E3-CC2751B3856A</outgoing></task><endEvent id=\"sid-E433566C-2289-4BEB-A19C-1697048900D2\" name=\"结束\"><incoming>sid-57EB1F24-BD94-479A-BF1F-57F1EAA19C6C</incoming></endEvent><sequenceFlow id=\"sid-EE8A7BA0-5D66-4F8B-80E3-CC2751B3856A\" name=\"前往任务2\" sourceRef=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26\" targetRef=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B\" /><sequenceFlow id=\"sid-57EB1F24-BD94-479A-BF1F-57F1EAA19C6C\" sourceRef=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B\" targetRef=\"sid-E433566C-2289-4BEB-A19C-1697048900D2\" /><sequenceFlow id=\"SequenceFlow_1bpeqj6\" sourceRef=\"sid-D7F237E8-56D0-4283-A3CE-4F0EFE446138\" targetRef=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26\" /><task id=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B\" name=\"任务2\"><incoming>sid-EE8A7BA0-5D66-4F8B-80E3-CC2751B3856A</incoming><outgoing>sid-57EB1F24-BD94-479A-BF1F-57F1EAA19C6C</outgoing><outgoing>SequenceFlow_0gy7w7x</outgoing></task><sequenceFlow id=\"SequenceFlow_0gy7w7x\" name=\"退回任务1\" sourceRef=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B\" targetRef=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26\" /></process><bpmndi:BPMNDiagram id=\"sid-74620812-92c4-44e5-949c-aa47393d3830\"><bpmndi:BPMNPlane id=\"sid-cdcae759-2af7-4a6d-bd02-53f3352a731d\" bpmnElement=\"sid-c0e745ff-361e-4afb-8c8d-2a1fc32b1424\"><bpmndi:BPMNShape id=\"sid-87F4C1D6-25E1-4A45-9DA7-AD945993D06F_gui\" bpmnElement=\"sid-87F4C1D6-25E1-4A45-9DA7-AD945993D06F\" isHorizontal=\"true\"><omgdc:Bounds x=\"43\" y=\"75\" width=\"933\" height=\"250\" /><bpmndi:BPMNLabel labelStyle=\"sid-84cb49fd-2f7c-44fb-8950-83c3fa153d3b\"><omgdc:Bounds x=\"47.49999999999999\" y=\"170.42857360839844\" width=\"12.000000000000014\" height=\"59.142852783203125\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"sid-57E4FE0D-18E4-478D-BC5D-B15164E93254_gui\" bpmnElement=\"sid-57E4FE0D-18E4-478D-BC5D-B15164E93254\" isHorizontal=\"true\"><omgdc:Bounds x=\"72.5\" y=\"75\" width=\"903\" height=\"250\" /></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"sid-D7F237E8-56D0-4283-A3CE-4F0EFE446138_gui\" bpmnElement=\"sid-D7F237E8-56D0-4283-A3CE-4F0EFE446138\"><omgdc:Bounds x=\"150\" y=\"165\" width=\"30\" height=\"30\" /><bpmndi:BPMNLabel labelStyle=\"sid-e0502d32-f8d1-41cf-9c4a-cbb49fecf581\"><omgdc:Bounds x=\"153\" y=\"197\" width=\"24\" height=\"12\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26_gui\" bpmnElement=\"sid-52EB1772-F36E-433E-8F5B-D5DFD26E6F26\"><omgdc:Bounds x=\"352.5\" y=\"140\" width=\"100\" height=\"80\" /><bpmndi:BPMNLabel labelStyle=\"sid-84cb49fd-2f7c-44fb-8950-83c3fa153d3b\"><omgdc:Bounds x=\"360.5\" y=\"172\" width=\"84\" height=\"12\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B_gui\" bpmnElement=\"sid-E49425CF-8287-4798-B622-D2A7D78EF00B\"><omgdc:Bounds x=\"615\" y=\"140\" width=\"100\" height=\"80\" /><bpmndi:BPMNLabel labelStyle=\"sid-84cb49fd-2f7c-44fb-8950-83c3fa153d3b\"><omgdc:Bounds x=\"695.9285736083984\" y=\"162\" width=\"83.14285278320312\" height=\"36\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"sid-E433566C-2289-4BEB-A19C-1697048900D2_gui\" bpmnElement=\"sid-E433566C-2289-4BEB-A19C-1697048900D2\"><omgdc:Bounds x=\"865\" y=\"166\" width=\"28\" height=\"28\" /><bpmndi:BPMNLabel labelStyle=\"sid-e0502d32-f8d1-41cf-9c4a-cbb49fecf581\"><omgdc:Bounds x=\"868\" y=\"196\" width=\"24\" height=\"12\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNEdge id=\"sid-EE8A7BA0-5D66-4F8B-80E3-CC2751B3856A_gui\" bpmnElement=\"sid-EE8A7BA0-5D66-4F8B-80E3-CC2751B3856A\"><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"453\" y=\"180\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"615\" y=\"180\" /><bpmndi:BPMNLabel><omgdc:Bounds x=\"509\" y=\"165\" width=\"51\" height=\"12\" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"sid-57EB1F24-BD94-479A-BF1F-57F1EAA19C6C_gui\" bpmnElement=\"sid-57EB1F24-BD94-479A-BF1F-57F1EAA19C6C\"><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"715\" y=\"180\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"866\" y=\"180\" /><bpmndi:BPMNLabel><omgdc:Bounds x=\"791\" y=\"165\" width=\"0\" height=\"0\" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"SequenceFlow_1bpeqj6_di\" bpmnElement=\"SequenceFlow_1bpeqj6\"><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"179\" y=\"180\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"353\" y=\"180\" /><bpmndi:BPMNLabel><omgdc:Bounds x=\"266\" y=\"155\" width=\"0\" height=\"0\" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"SequenceFlow_0gy7w7x_di\" bpmnElement=\"SequenceFlow_0gy7w7x\"><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"665\" y=\"220\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"665\" y=\"280\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"403\" y=\"280\" /><omgdi:waypoint xsi:type=\"omgdc:Point\" x=\"403\" y=\"220\" /><bpmndi:BPMNLabel><omgdc:Bounds x=\"508\" y=\"265\" width=\"52\" height=\"12\" /></bpmndi:BPMNLabel></bpmndi:BPMNEdge></bpmndi:BPMNPlane><bpmndi:BPMNLabelStyle id=\"sid-e0502d32-f8d1-41cf-9c4a-cbb49fecf581\"><omgdc:Font name=\"Arial\" size=\"11\" isBold=\"false\" isItalic=\"false\" isUnderline=\"false\" isStrikeThrough=\"false\" /></bpmndi:BPMNLabelStyle><bpmndi:BPMNLabelStyle id=\"sid-84cb49fd-2f7c-44fb-8950-83c3fa153d3b\"><omgdc:Font name=\"Arial\" size=\"12\" isBold=\"false\" isItalic=\"false\" isUnderline=\"false\" isStrikeThrough=\"false\" /></bpmndi:BPMNLabelStyle></bpmndi:BPMNDiagram></definitions>";
	}
	// import xml
	importXML(diagramXML);
	

})(window.BpmnJS, window.jQuery);
