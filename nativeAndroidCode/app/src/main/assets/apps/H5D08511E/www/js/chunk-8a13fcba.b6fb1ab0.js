(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8a13fcba"],{"09d3":function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACEAAAAhCAYAAABX5MJvAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAIaADAAQAAAABAAAAIQAAAAAWQIAAAAADnUlEQVRYCe2XTUwTQRSA501buhA4GBMhQS968eKB+EMrB0OIMV5VvHvzookK9AdIALU/SjT+HLx5MF6oB+MVjSfoEgI3Dx5I/IkJ6sEgmrZI5/netkOXWmR3WxIPTtOdn5333rfvvZnZBVEu4XSuTym4AohHUIh2Pb5dDQCrNGfIjBmPtpobTq51KVEk3dBFc9ZI/zwApsxY8zuWAb50J3LjAmBUIFp9HnNTAMQ8KTxWSyaULAyT3jEU6LffBwE/pA8uzEaCz6DsgWkNQBSfEGDZLvC3Ns1fRSkScxFjunpeKJFPk/Ghyjh8pzbBYAuPsRdlIHjIjwquagApYDAbNyYrQt5b1QASZPxUtCn9JiP8H5cK9xDxIv3b1FrhGoQSuWXOAfaAGW/e691sRfJPALiUjRkP9YzT9zH47WdhhSCClBBZqZPQTQi0slr1dgAs09ohKEr0o0IP75fcaFRxAsC2KBwTxGBwm9bCYsMgQsl8yp6EIOGyPQRskAuvFgpDhNu0QvJ+n7i7adnwDS/F8gBWVoEEyoFoJQe0zhKAuqH7QoqhmYjxtm4IywM2AMsDjgBkzIwGHzBQXeEoA1iuZWUMYEYNSzH3dan2AC/XuWgwpe97hqgHIBsLJjUA154gGgngCSKczE3o7GYFW4YgVRhBtCehjFV7gOW5uPKEdc6gGC2JbgOg1HU9rzoH9LiuXa0OOmeitL1YsiDlsM5urYzrEHugCqCWB7rT+ZOg8CadYo8dQxy/jXvUeqGXDdFW+37f/qa0yR1bcQrAIqBEHFEcJV0HHYeDAM5QLvhYAZ0zU5nzUOS2Lm4AWIb82WbVdJI69gRJndMGfUJmdLt/Cpuss0Cpjf2Cc6BWCLRMde0cgl7ALGEQy50HAovhW79OqPVi/4el/FkC7NCK3QKwnGMIevH7QsZaKZrt9ORfKTS7LMOlPKUmKIrvIHngjjXu4uI4J+gJx0tGkd6FywBsGqBA1+d+n+wx44ZrANbp2BOzkcCTcAqL9AIwQmm1m6ybEmWmBQIvXsZgxQL0eHEMQU/Mjn9a/ns0V1vMcThqizdm9D+E9uO/4Qla25+ZiNbdxoajCXey1vbYPn10wSIbo9TvDCfyAztpWOtmO2zP6gMsQE8617uu4JX+FCQyV9+iWrHTmj1gA0C/xD6yWf9XuVOATfNo3wGBE/Q1P2ZB8E32SFHBAHnkMJG2bxJoYIdzgFJgwSdxcibS/JpV/wZb2Lu18SsEgQAAAABJRU5ErkJggg=="},2159:function(t,e,n){},"3a5e":function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"loading"},[n("van-loading",{attrs:{color:"#1989fa",size:"80px"}})],1)},a=[],s={name:"Loading"},o=s,c=(n("497f"),n("0c7c")),l=Object(c["a"])(o,i,a,!1,null,"802598ba",null);e["a"]=l.exports},"497f":function(t,e,n){"use strict";var i=n("2159"),a=n.n(i);a.a},5285:function(t,e,n){},5448:function(t,e,n){},5964:function(t,e,n){"use strict";var i=n("5285"),a=n.n(i);a.a},8870:function(t,e,n){"use strict";var i=n("5448"),a=n.n(i);a.a},f634:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"binding"},[t.isLoading?i("Loading"):t._e(),i("div",{staticClass:"binding-title1 clearfix"},[i("span",{staticClass:"icon",on:{click:t.back}},[i("van-icon",{attrs:{name:"arrow-left",color:"#fff",size:"17px"}})],1),i("span",{staticClass:"help",on:{click:function(e){return t.goRoute("help")}}},[t._v("Help")])]),i("div",{staticClass:"binding-box clearfix"},[t._m(0),i("div",{staticClass:"form"},[i("div",{staticClass:"group"},[i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"120px",label:"Account",border:!1,readonly:"",placeholder:"Enter the Account"},model:{value:t.info.account,callback:function(e){t.$set(t.info,"account",e)},expression:"info.account"}})],1),i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right",clearable:"","label-class":"font-style",autofocus:t.autofocus,"label-width":"120px",label:"Device name",border:!1,readonly:t.readonly,placeholder:"Enter the name"},model:{value:t.info.deviceName,callback:function(e){t.$set(t.info,"deviceName",e)},expression:"info.deviceName"}}),i("div",{staticClass:"fill-box",on:{click:t.modifyName}},[i("img",{staticClass:"img-sise",attrs:{src:n("09d3"),alt:""}})])],1),i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"120px",label:"Network",border:!1,readonly:"",placeholder:"Enter the Network"},model:{value:t.info.networkState,callback:function(e){t.$set(t.info,"networkState",e)},expression:"info.networkState"}})],1),i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"155px",label:"Signal line status",border:!1,readonly:"",placeholder:"Enter the status"},model:{value:t.info.signalLineState,callback:function(e){t.$set(t.info,"signalLineState",e)},expression:"info.signalLineState"}})],1),i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"120px",label:"S/N",border:!1,readonly:"",placeholder:"Enter the S/N"},model:{value:t.info.deviceNo,callback:function(e){t.$set(t.info,"deviceNo",e)},expression:"info.deviceNo"}})],1),i("div",{staticClass:"field"},[i("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"155px",label:"Firmware Version",border:!1,readonly:"",placeholder:"Enter the Version"},model:{value:t.info.version,callback:function(e){t.$set(t.info,"version",e)},expression:"info.version"}})],1)]),i("div",{staticClass:"bind-btn-box"},[i("van-button",{staticClass:"bind-btn",on:{click:t.unBindDevice}},[t._v("Unbind")])],1)])])],1)},a=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"binding-title"},[n("span",[t._v("Device information")])])}],s=(n("d3b7"),n("ac1f"),n("5319"),n("5530")),o=n("2b0e"),c=n("d399"),l=n("f564"),d=n("2241"),r=n("7cb4"),u=n("09e4"),A=n("995e"),f=n("3a5e"),g=n("2f62");o["a"].use(c["a"]),o["a"].use(l["a"]),o["a"].use(d["a"]);var b={computed:Object(s["a"])({},Object(g["c"])(["isLoading"])),components:{Loading:f["a"]},data:function(){return{info:{signalLineState:"DisConnected",network:"DisConnected"},isPending:!1,readonly:!1,autofocus:!0,account:"Bob123@gmail.com",name:"youthope008",network:"Connected",status:"Connected",sn:"YH20200410",version:"20200410.08"}},mounted:function(){this.getInfo()},watch:{active:function(){this.bindingShow=!0}},methods:{modifyName:function(){this.editDeviceName()},getInfo:function(){var t=this;if(!this.isPending){this.isPending=!0;var e={url:A["m"],data:{}};Object(r["a"])(e).then((function(e){200===e.data.code?t.info=e.data.data:"请先绑定设备"===e.data.data?t.$router.replace("/binding"):Object(u["a"])(e.data,t)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}},unBindDevice:function(){var t=this;d["a"].confirm({message:"Do you want to unbind the YoutHope？If you unbind the YoutHope, you will not receive your report"}).then((function(){t.$confirm({iconShow:!0,type:"success",msg:"Device Unbind succeeded !"});var e={url:A["J"],data:{}};Object(r["a"])(e).then((function(e){200===e.data.code?(t.$alertSuccess(e.data.data),t.$router.replace("/setting")):Object(u["a"])(e.data,t)})).catch((function(t){console.log(t)}))})).catch((function(){t.$confirm({iconShow:!0,type:"cross",msg:"Device Unbind failed !"})}))},back:function(){this.$router.go(-1)},goRoute:function(t){this.$router.push({name:t})},scan:function(){this.$confirm({iconShow:!0,type:"success",msg:"click to scan code"})},editDeviceName:function(){var t=this;if(!this.isPending){this.isPending=!0;var e={url:A["g"],data:{deviceName:this.info.deviceName}};Object(r["a"])(e).then((function(e){200===e.data.code?t.$alertSuccess("edit success"):Object(u["a"])(e.data,t)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}}}},v=b,h=(n("5964"),n("8870"),n("0c7c")),p=Object(h["a"])(v,i,a,!1,null,"3ea08538",null);e["default"]=p.exports}}]);