(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-006e4540"],{"0b84":function(t,i,a){"use strict";var n=a("c50f"),s=a.n(n);s.a},2159:function(t,i,a){},"362f":function(t,i,a){},"3a5e":function(t,i,a){"use strict";var n=function(){var t=this,i=t.$createElement,a=t._self._c||i;return a("div",{staticClass:"loading"},[a("van-loading",{attrs:{color:"#1989fa",size:"80px"}})],1)},s=[],e={name:"Loading"},c=e,o=(a("497f"),a("0c7c")),A=Object(o["a"])(c,n,s,!1,null,"802598ba",null);i["a"]=A.exports},"497f":function(t,i,a){"use strict";var n=a("2159"),s=a.n(n);s.a},"7bd6":function(t,i){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACEAAAAhCAYAAABX5MJvAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAIaADAAQAAAABAAAAIQAAAAAWQIAAAAABdUlEQVRYCe1YMU7DQBDMoUggQU9qWiSQqFLxhFAQXPEM+ARlPsEr0kICAmQrLsI7ACUK6Jgxu1KIFiUFd3ZxK432NNrcjNe+nM+tFsJ73wdegBnwZaAAt8/aTQP1HWACWLEAmQNZNR8GF1aVwfU2NcA6/L5nzGFRmQOb4zdHIvCA/CFjTR6DErh2zs2UXJcx7zZqboBDo3YXXFf4go7ZGsadURyMgt6oUoX+FlTaovQWTNGe+F3oNk3UHsmE3oLGdGIsjjSrwdB5JAL3XKJ7wCkQtSvUE13+Z6RIHfjVAW5gB2AuAW44sYMb4i1NPGFwElt9Sa/kspwuEXUMXx1V0Y1jpDpuxxy6BT2kaE4H8DzwrfgM2Inpinqi2+FDWQKMQWQTgx9ZX9KExjCyiaEKR92+/7rIZEI705hOfIqj2K9ZqrdYPYs+wpCejLRbzDyLXgU6i+ZcopkulTU51Km8X12tGOH3Aj0cr/r57+8Tcwg8A+c08A3Eid7ba2YAxgAAAABJRU5ErkJggg=="},"9f8a":function(t,i,a){"use strict";a.r(i);var n=function(){var t=this,i=t.$createElement,n=t._self._c||i;return n("div",{staticClass:"binding"},[t.isLoading?n("Loading"):t._e(),n("div",{staticClass:"binding-title1 clearfix"},[n("span",{staticClass:"icon",on:{click:t.back}},[n("van-icon",{attrs:{name:"arrow-left",color:"#fff",size:"20"}})],1),n("span",{staticClass:"help",on:{click:function(i){return t.goRoute("help")}}},[t._v("Help")])]),n("div",{staticClass:"binding-box clearfix"},[t._m(0),n("div",{staticClass:"form"},[n("div",{staticClass:"group"},[n("div",{staticClass:"binding-text"},[t._v(" Now, you shoud bind a Youthope. Please scan the QR code at the bottom of the Youthope or enter the S/N ")]),n("div",{staticClass:"field-code"},[n("div",{staticClass:"scan clearfix"},[n("span",{staticClass:"sacn-code"},[t._v("Scan the QR code")]),n("div",{staticClass:"f-r",on:{click:t.scanClick}},[n("img",{staticClass:"img-sise",attrs:{src:a("7bd6"),alt:""}})])])]),n("div",{staticClass:"field"},[n("van-field",{staticClass:"input",attrs:{"input-align":"right","label-class":"font-style","label-width":"120px",label:"S/N",border:!1,clearable:"",placeholder:"Enter the S/N"},model:{value:t.sn,callback:function(i){t.sn=i},expression:"sn"}})],1)]),n("div",{staticClass:"bind-btn-box"},[n("van-button",{staticClass:"bind-btn",on:{click:t.bind}},[t._v("Bind")])],1)])])],1)},s=[function(){var t=this,i=t.$createElement,a=t._self._c||i;return a("div",{staticClass:"binding-title"},[a("span",[t._v("Bind device")])])}],e=(a("d3b7"),a("ac1f"),a("5319"),a("5530")),c=a("2b0e"),o=a("f564"),A=a("2241"),l=a("d399"),r=a("995e"),d=a("7cb4"),u=(a("09e4"),a("2f62")),f=a("3a5e");c["a"].use(l["a"]),c["a"].use(o["a"]),c["a"].use(A["a"]);var g={computed:Object(e["a"])({},Object(u["c"])(["isLoading"])),components:{Loading:f["a"]},data:function(){return{sn:"",myTimeOut:""}},created:function(){this.sn=this.$route.params.result,console.log("sn = "+this.sn)},destroyed:function(){this.clearMyTimeOut()},watch:{active:function(){this.bindingShow=!0}},methods:{bind:function(){var t=this;if(this.sn&&""!==this.sn){if(!this.isPending){this.isPending=!0;var i={url:r["b"],data:{deviceNo:this.sn}};Object(d["a"])(i).then((function(i){if(200===i.data.code){var a=t;t.myTimeOut=setInterval((function(){a.$router.replace("/deviceInformation")}),2e3)}else t.$alertWarning(i.data.data)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}}else this.$alertWarning("Please enter the S/N.")},clearMyTimeOut:function(){clearTimeout(this.myTimeOut),this.myTimeOut=null},back:function(){this.$router.push({name:"report"})},goRoute:function(t){this.$router.push({name:t})},scanClick:function(){this.$router.replace({name:"scanCodeBinding"})}}},h=g,b=(a("a432"),a("0b84"),a("0c7c")),v=Object(b["a"])(h,n,s,!1,null,"f140cbea",null);i["default"]=v.exports},a432:function(t,i,a){"use strict";var n=a("362f"),s=a.n(n);s.a},c50f:function(t,i,a){}}]);