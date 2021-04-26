/*
 Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
CKEDITOR.dialog.add("textfield",
function(b) {
	texFieldGoble="";
	function e(a) {
		var a = a.element,
		c = this.getValue();
		c ? a.setAttribute(this.id, c) : a.removeAttribute(this.id)
	}
	function f(a) {
		this.setValue(a.hasAttribute(this.id) && a.getAttribute(this.id) || "")
	}
	var g = {
		email: 1,
		password: 1,
		search: 1,
		tel: 1,
		text: 1,
		url: 1
	};
	return {
		title: b.lang.forms.textfield.title,
		minWidth: 350,
		minHeight: 150,
		onShow: function() {
			delete this.textField;
			var a = this.getParentEditor().getSelection().getSelectedElement();
			if (a && "input" == a.getName() && (g[a.getAttribute("type")] || !a.getAttribute("type"))) this.textField = a,
			this.setupContent(a)
		},
		onOk: function() {
			var a = this.getParentEditor(),
			c = this.textField,
			b = !c;
			b && (c = a.document.createElement("input"), c.setAttribute("type", "text"));
			c = {
				element: c
			};
			b && a.insertElement(c.element);
			this.commitContent(c);
			b || a.getSelection().selectElement(c.element)
		},
		onLoad: function() {
			this.foreach(function(a) {
				if (a.getValue && (a.setup || (a.setup = f), !a.commit)) a.commit = e
			})
		},
		contents: [{
			id: "info",
			label: b.lang.forms.textfield.title,
			title: b.lang.forms.textfield.title,
			elements: [{
				type: "hbox",
				widths: ["50%", "50%"],
				children: [{
					id: "_cke_saved_name",
					type: "text",
					label: b.lang.forms.textfield.name,
					"default": "",
					accessKey: "N",
					setup: function(a) {
						this.setValue(a.data("cke-saved-name") || a.getAttribute("name") || "")
					},
					commit: function(a) {
						a = a.element;
						this.getValue() ? a.data("cke-saved-name", this.getValue()) : (a.data("cke-saved-name", !1), a.removeAttribute("name"))
					}
				},
				{
					id: "value",
					type: "text",
					label: b.lang.forms.textfield.value,
					"default": "",
					accessKey: "V",
					commit: function(a) {
						if (CKEDITOR.env.ie && !this.getValue()) {
							var c = a.element,
							d = new CKEDITOR.dom.element("input", b.document);
							c.copyAttributes(d, {
								value: 1
							});
							d.replace(c);
							a.element = d
						} else e.call(this, a)
					}
				}]
			},
			{
				type: "hbox",
				widths: ["50%", "50%"],
				children: [{
					id: "size",
					type: "text",
					label: b.lang.forms.textfield.charWidth,
					"default": "",
					accessKey: "C",
					style: "width:50px",
					validate: CKEDITOR.dialog.validate.integer(b.lang.common.validateNumberFailed)
				},
				{
					id: "maxLength",
					type: "text",
					label: b.lang.forms.textfield.maxChars,
					"default": "",
					accessKey: "M",
					style: "width:50px",
					validate: CKEDITOR.dialog.validate.integer(b.lang.common.validateNumberFailed)
				}],
				onLoad: function() {
					CKEDITOR.env.ie7Compat && this.getElement().setStyle("zoom", "100%")
				}
			},
			{
				id: "type",
				type: "select",
				label: b.lang.forms.textfield.type,
				"default": "text",
				accessKey: "M",
				items: [[b.lang.forms.textfield.typeEmail, "email"], [b.lang.forms.textfield.typePass, "password"], [b.lang.forms.textfield.typeSearch, "search"], [b.lang.forms.textfield.typeTel, "tel"], [b.lang.forms.textfield.typeText, "text"], [b.lang.forms.textfield.typeUrl, "url"]],
				setup: function(a) {
					this.setValue(a.getAttribute("type"))
				},
				commit: function(a) {
					var c = a.element;  
					if (CKEDITOR.env.ie) {
						var d = c.getAttribute("type"),
						e = this.getValue();
						d != e && (d = CKEDITOR.dom.element.createFromHtml('<input type="' + e + '"></input>', b.document), c.copyAttributes(d, {
							type: 1
						}), d.replace(c), a.element = d)
					} else c.setAttribute("type", this.getValue())
				}
			} 
		 
			 //############################################## 支持onclick事件
			/*,{  
				id: "onclick",  
				type: "text",
				label: b.lang.forms.textfield.onclick,
				"default": "",
				accessKey: "N",
				setup: function(a) {
					this.setValue(a.data("cke-saved-onclick") || a.getAttribute("onclick") || "")
				}, 
				commit: function(a) {
					a = a.element;
					this.getValue() ? a.data("cke-saved-onclick", this.getValue()) : (a.data("cke-saved-onclick", !1), a.removeAttribute("onclick"))
				}
			}*/
			// add by xiehongtao 2013.10.11
		 //############################################## 支持各种JS事件
			,{
				id: "jscode",  
				type: "textarea",
				label:  b.lang.forms.textfield.eventType,
		 
				"default": "",  
				setup: function(a) { 
			 	    if(texFieldGoble!=null&&texFieldGoble!='')
					this.setValue(a.hasAttribute(texFieldGoble) && texFieldGoble+"="+a.getAttribute(texFieldGoble) || "")
				},  
				commit: function(a) {
		     	a = a.element;
		        var part1=null;
					if(this.getValue()!=null&&this.getValue()!=""){
						var content=	this.getValue();
							//alert("content"+content);     
			       var pos=content.indexOf('='); 
			      if(content!=null&&pos==-1){  
			          alert(b.lang.forms.textfield.ckeckJSCode);
			      	return;    
			      	}
			      // alert("pos"+pos);  
				    part1=this.getValue().substring(0,pos);  
				    part2=this.getValue().substring(pos+1,this.getValue().length);
				    texFieldGoble=part1;		         
				    this.getValue() ? a.setAttribute(part1,part2) : a.removeAttribute(part1)
 	  
						}else{
							if(part1!=null)  {   
								a.removeAttribute(part1);
							}
							 else   
								a.removeAttribute(texFieldGoble);
					  }   
			          
				}
			}   

		 //##############################################
		 ]
		}]
	}
});