(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ddc73884"],{"21c1":function(t,A,a){},"2dda":function(t,A){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAJKADAAQAAAABAAAAJAAAAAAqDuP8AAAB2ElEQVRYCe1Wy0rDQBQ1k0KXutQ/EEJXbroRXbtMCbjPdxQrfodfUBp3rlwognTjSkoEf6BbwU2gaT1HMlLaeSRjX2AKQ5p7z9x7ejtzz/X2/vDpdDrBdDq9QYh2EWYohOgOBoORa1jPdSPIXOR5nmB/cyFG5vt+CFL3C/ZSr06Eoijan0wm77PZ7FCVxfO8caPROO73+58qv8kmTE6dD2ROdWS4hz5idPtNdidCSNgyBaWvDEYVw4kQDu7iuVmKXQaztAkGJ0KqQKuy1YRslawrVFfIVgGb/1c6CjlgB27Zeggw51hnpuCQjyesRxMGwpwB8waZeZYy80OIQgnnLZIotckUdBU+kBqjCDEF2eMIAdV+RWBr911FckMMTgknophntk2GPJvkwj4khysD+Y252jvZGIcb+/32RByBRRe4zI5dOyIjF8GBnDMwr97aU2oSMHcxh492szFqiGvNYRj2cEWvtAA4UP7rJEl6JozKt5O3TEV0a7a6QrbS1xX6HxVCD7JKTRmMqlpOZ4hjpyrYvK0MZh4vvzsR4gxs0j76iJFJqjydCHEghzTESKT666jasRzaq5Ah1q+6QeLTNP0IguAO70eoyAHWF9YDVPsSE8SLxFV9fgOIXK7ygH1YxgAAAABJRU5ErkJggg=="},6712:function(t,A,a){"use strict";a.r(A);var i=function(){var t=this,A=t.$createElement,i=t._self._c||A;return i("div",{staticClass:"user follow"},[i("div",{staticClass:"top-bg"},[i("div",{staticClass:"top-bg"},[i("van-nav-bar",{attrs:{"left-arrow":"",border:!1},on:{"click-left":t.onClickLeft}})],1)]),i("div",{staticClass:"user-code"},[i("div",{staticClass:"code-user-info"},[i("div",{staticClass:"code-user-img"},[i("div",{staticClass:"image"},[this.codeUrl?i("van-image",{attrs:{round:"",width:"2rem",height:"2rem",fit:"cover",src:this.codeUrl}}):i("van-image",{attrs:{round:"",width:"2rem",height:"2rem",fit:"cover",src:a("bd9f")}})],1)])]),i("div",{staticClass:"info"},[i("p",{staticClass:"name"},[t._v(t._s(this.info.nickname))]),i("div",{staticClass:"sex"},[i("p",{staticClass:"sex-img"},[i("van-image",{attrs:{round:"",width:"0.5rem",fit:"cover",src:a("c777")}})],1),i("p",{staticClass:"sex-word"},[i("span",[t._v(t._s(this.info.id))])])]),t.correctInfo?i("div",{staticClass:"code"},[t.iffollow?t._e():i("div",{staticClass:"say-hello"},[i("van-row",{attrs:{type:"flex",justify:"center"}},[i("van-col",{attrs:{span:"20"}},[i("input",{directives:[{name:"model",rawName:"v-model",value:t.remark,expression:"remark"}],staticClass:"other",attrs:{rows:"3",cols:"20",placeholder:"Say Hello !",name:"Other"},domProps:{value:t.remark},on:{input:function(A){A.target.composing||(t.remark=A.target.value)}}})]),i("van-col",{attrs:{span:"4"}},[i("div",{staticClass:"images"},[i("van-image",{attrs:{width:"15px",height:"15px",src:a("2dda")}})],1)])],1)],1),i("div",{staticClass:"follow-btn"},[t.iffollow?i("van-button",{staticClass:"btn",attrs:{type:"default"},on:{click:t.unfollow}},[t._v("Unfollow")]):i("van-button",{staticClass:"btn",attrs:{type:"default"},on:{click:t.follow}},[t._v("Follow")])],1)]):t._e()])]),t._m(0)])},e=[function(){var t=this,A=t.$createElement,a=t._self._c||A;return a("div",{staticClass:"code-bottom"},[a("p",[t._v(" Send this QR code to others. After scanning this QR, they can access to your data. ")])])}],n=(a("d3b7"),a("ac1f"),a("1276"),a("5530")),s=a("2b0e"),o=a("d399"),c=a("09e4"),l=a("7cb4"),r=a("995e"),d=a("2f62");a("94d5");s["a"].use(o["a"]);var g={computed:Object(n["a"])({},Object(d["c"])(["userEmail"])),data:function(){return{isPending:!1,remark:"",info:{},codeUrl:"",inviteKey:"",correctInfo:!0,iffollow:!1,uuid:""}},created:function(){this.$route.params.email&&this.setUserEmail(this.$route.params.email),this.inviteKey=this.$route.params.inviteKey,console.log("sxmmm get_inviteKey== "+this.inviteKey),this.inviteKey&&""!==this.inviteKey&&this.scanQRCode(),console.log("userEmail = "+this.userEmail),console.log("remark = "+this.remark),this.userEmail&&""!==this.userEmail&&this.getUserInfo()},methods:Object(n["a"])(Object(n["a"])({},Object(d["b"])(["setUserEmail"])),{},{onClickLeft:function(){this.$router.push({name:"user"})},follow:function(){var t=this;if(!this.isPending){this.isPending=!0;var A={url:r["G"],data:{email:this.userEmail,remark:this.remark}},a=this;Object(l["a"])(A).then((function(A){200===A.data.code?(console.log("发起关注成功..."),t.$confirm({iconShow:!0,type:"success",msg:"Sent successfully."}),t.isPending=!1,t.$router.push("/user")):Object(c["a"])(A.data,a)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}},unfollow:function(){var t=this;if(!this.isPending){this.isPending=!0;var A={url:r["K"],data:{uid:this.uuid}},a=this;Object(l["a"])(A).then((function(A){200===A.data.code?t.ifwefollow():Object(c["a"])(A.data.data,a)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}},ifwefollow:function(){var t=this,A={url:r["v"],data:{email:this.userEmail}},a=this;Object(l["a"])(A).then((function(A){console.log(A),200===A.data.code?(console.log("判断是否关注接口调用成功..."),t.iffollow=A.data.data.isFollow,t.uuid=A.data.data.userId):Object(c["a"])(A.data,a)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))},getUserInfo:function(){var t=this;if(!this.isPending){this.isPending=!0;var A={url:r["t"],data:{email:this.userEmail}},a=this;Object(l["a"])(A).then((function(A){if(200===A.data.code){t.correctInfo=!0,t.info=A.data.data,t.isPending=!1,t.ifwefollow();var i=t.info.headImageUrl;if(i){var e=i.split("/");t.getResource(e[e.length-1])}}else console.log("getUserInfo eslse",A.data),t.correctInfo=!1,Object(c["a"])(A.data,a)})).catch((function(A){t.correctInfo=!1,console.log(A)})).finally((function(){t.isPending=!1}))}},scanQRCode:function(){var t=this;if(!this.isPending){this.isPending=!0;var A={url:r["H"],data:{inviteKey:this.inviteKey}},a=this;Object(l["a"])(A).then((function(A){if(200===A.data.code){t.correctInfo=!0,t.info=A.data.data,console.log("sxmmm info== "+A.data.data),t.setUserEmail(t.info.email),t.isPending=!1,t.ifwefollow();var i=t.info.headImageUrl;if(i){var e=i.split("/");t.getResource(e[e.length-1])}}else t.correctInfo=!0,Object(c["a"])(A.data,a)})).catch((function(t){console.log(t)})).finally((function(){t.isPending=!1}))}},getResource:function(t){var A=this;if(!this.isPending){this.isPending=!0;var a={url:r["r"],data:{name:t,type:1}};Object(l["a"])(a).then((function(t){A.codeUrl="data:image/jpeg;base64,"+t.data})).catch((function(t){console.log(t)})).finally((function(){A.isPending=!1}))}}})},u=g,f=(a("d622"),a("f2a0"),a("0c7c")),h=Object(f["a"])(u,i,e,!1,null,"748232ca",null);A["default"]=h.exports},bd9f:function(t,A){t.exports="data:image/png;base64,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"},c777:function(t,A){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAAXNSR0IArs4c6QAAAERlWElmTU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAE6ADAAQAAAABAAAAEwAAAAAgb1CmAAABkUlEQVQ4Ea1UvS8EQRx9u0ckCCcnuYRORHO9gloloaOgUGgo1IjEX6ARUV+h8FHplVrlhnC5VlZw4uNE3O14v53Zm82d45b7JZvdnXnvze9zgDaa852W8lZ7gaclBJiFgxygMoDzAAUPLk6BdN7J7b/WcxvElLc4h6C6S2C2Hhz79+Gm1pzcwXFsjefETHkLGxQ64tJPQsLICi7Ex/g1z4xHIpTM3NR85GEoFuYoKBWokoXbDYztAFcrwOAMn2mg+swtBqEqQPkG8A+5VksZQx4YlRx2aDeY7GahPZ4BdycaxmogPQmMbAOFdYpXZZ0pCfl7OmdStZZMkXdO74pA/4RlGL4WC8tv9379eqdY17CFGb6ppvRRAgvKQKonRtB8I8aGTGKdPPvzPsbQfC0mnZ3E+saZt2vLMHwtFo6I3Wv6JaENLQMVtsrbpYUZvmmNdB4obXG3sfMzU7pyDs8NPoCXC+CWcGu+zKr8tnUCdJiiKkPruptyQstGfDRKwql5Fgn859ZoEBPRv95nkUNteX8Bz+SKg7RBJKAAAAAASUVORK5CYII="},d622:function(t,A,a){"use strict";var i=a("21c1"),e=a.n(i);e.a},f2a0:function(t,A,a){"use strict";var i=a("f4b1"),e=a.n(i);e.a},f4b1:function(t,A,a){}}]);