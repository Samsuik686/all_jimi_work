(function(t){function e(e){for(var o,r,i=e[0],u=e[1],s=e[2],l=0,m=[];l<i.length;l++)r=i[l],Object.prototype.hasOwnProperty.call(a,r)&&a[r]&&m.push(a[r][0]),a[r]=0;for(o in u)Object.prototype.hasOwnProperty.call(u,o)&&(t[o]=u[o]);d&&d(e);while(m.length)m.shift()();return c.push.apply(c,s||[]),n()}function n(){for(var t,e=0;e<c.length;e++){for(var n=c[e],o=!0,r=1;r<n.length;r++){var i=n[r];0!==a[i]&&(o=!1)}o&&(c.splice(e--,1),t=u(u.s=n[0]))}return t}var o={},r={app:0},a={app:0},c=[];function i(t){return u.p+"js/"+({}[t]||t)+"."+{"chunk-07a136cc":"68dc62d6","chunk-080f9df9":"6cdaf075","chunk-0c40879e":"33fb9e11","chunk-15b82ac7":"6da31794","chunk-1ca133e8":"a0b236e5","chunk-25e9f132":"b9144a3d","chunk-261ccaf6":"e529efbd","chunk-27516ec5":"93abf10c","chunk-276f544e":"3c1460fc","chunk-3e11f0f8":"ea05e086","chunk-3e9fadd1":"ea195949","chunk-40fdc8e0":"ba6cc7f4","chunk-422b93df":"6193a50b","chunk-4900818a":"7ec00dc4","chunk-522c1088":"01266cb3","chunk-53062cdf":"3757ed20","chunk-5454ba98":"955ce35d","chunk-2ded6e59":"c45307d7","chunk-72e0f319":"d68ab182","chunk-5a892a63":"06c24cec","chunk-72d999b6":"709bd9bb","chunk-7adccdc4":"765cd6fc","chunk-7e2097d8":"e4281748","chunk-83e2ee20":"2c696ec7","chunk-9a1a7310":"8c854db8","chunk-a5a8e75e":"59ba5d0a","chunk-fb2f2ed8":"45d72a3a"}[t]+".js"}function u(e){if(o[e])return o[e].exports;var n=o[e]={i:e,l:!1,exports:{}};return t[e].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.e=function(t){var e=[],n={"chunk-07a136cc":1,"chunk-080f9df9":1,"chunk-0c40879e":1,"chunk-15b82ac7":1,"chunk-1ca133e8":1,"chunk-25e9f132":1,"chunk-261ccaf6":1,"chunk-27516ec5":1,"chunk-276f544e":1,"chunk-3e11f0f8":1,"chunk-3e9fadd1":1,"chunk-40fdc8e0":1,"chunk-422b93df":1,"chunk-4900818a":1,"chunk-522c1088":1,"chunk-53062cdf":1,"chunk-5454ba98":1,"chunk-2ded6e59":1,"chunk-72e0f319":1,"chunk-5a892a63":1,"chunk-72d999b6":1,"chunk-7adccdc4":1,"chunk-7e2097d8":1,"chunk-83e2ee20":1,"chunk-9a1a7310":1,"chunk-a5a8e75e":1,"chunk-fb2f2ed8":1};r[t]?e.push(r[t]):0!==r[t]&&n[t]&&e.push(r[t]=new Promise((function(e,n){for(var o="css/"+({}[t]||t)+"."+{"chunk-07a136cc":"a063101a","chunk-080f9df9":"faa541d8","chunk-0c40879e":"766be55b","chunk-15b82ac7":"d1235c7c","chunk-1ca133e8":"6f6e125b","chunk-25e9f132":"0b968e4a","chunk-261ccaf6":"b3c9410a","chunk-27516ec5":"1b7314d6","chunk-276f544e":"f1e8f2a4","chunk-3e11f0f8":"aa07e6ff","chunk-3e9fadd1":"a7f2710f","chunk-40fdc8e0":"49c55b9f","chunk-422b93df":"cc85df85","chunk-4900818a":"3268b12b","chunk-522c1088":"32d85604","chunk-53062cdf":"0a37b9f9","chunk-5454ba98":"e2b80ac0","chunk-2ded6e59":"7fd0ec53","chunk-72e0f319":"5465f138","chunk-5a892a63":"1d71ccf1","chunk-72d999b6":"85d5bfcf","chunk-7adccdc4":"81fa7ee4","chunk-7e2097d8":"2db2874b","chunk-83e2ee20":"23e32f10","chunk-9a1a7310":"47acfc0f","chunk-a5a8e75e":"f128cd43","chunk-fb2f2ed8":"9c38788a"}[t]+".css",a=u.p+o,c=document.getElementsByTagName("link"),i=0;i<c.length;i++){var s=c[i],l=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(l===o||l===a))return e()}var m=document.getElementsByTagName("style");for(i=0;i<m.length;i++){s=m[i],l=s.getAttribute("data-href");if(l===o||l===a)return e()}var d=document.createElement("link");d.rel="stylesheet",d.type="text/css",d.onload=e,d.onerror=function(e){var o=e&&e.target&&e.target.src||a,c=new Error("Loading CSS chunk "+t+" failed.\n("+o+")");c.code="CSS_CHUNK_LOAD_FAILED",c.request=o,delete r[t],d.parentNode.removeChild(d),n(c)},d.href=a;var f=document.getElementsByTagName("head")[0];f.appendChild(d)})).then((function(){r[t]=0})));var o=a[t];if(0!==o)if(o)e.push(o[2]);else{var c=new Promise((function(e,n){o=a[t]=[e,n]}));e.push(o[2]=c);var s,l=document.createElement("script");l.charset="utf-8",l.timeout=120,u.nc&&l.setAttribute("nonce",u.nc),l.src=i(t);var m=new Error;s=function(e){l.onerror=l.onload=null,clearTimeout(d);var n=a[t];if(0!==n){if(n){var o=e&&("load"===e.type?"missing":e.type),r=e&&e.target&&e.target.src;m.message="Loading chunk "+t+" failed.\n("+o+": "+r+")",m.name="ChunkLoadError",m.type=o,m.request=r,n[1](m)}a[t]=void 0}};var d=setTimeout((function(){s({type:"timeout",target:l})}),12e4);l.onerror=l.onload=s,document.head.appendChild(l)}return Promise.all(e)},u.m=t,u.c=o,u.d=function(t,e,n){u.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},u.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},u.t=function(t,e){if(1&e&&(t=u(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)u.d(n,o,function(e){return t[e]}.bind(null,o));return n},u.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return u.d(e,"a",e),e},u.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},u.p="",u.oe=function(t){throw console.error(t),t};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=e,s=s.slice();for(var m=0;m<s.length;m++)e(s[m]);var d=l;c.push([0,"chunk-vendors"]),n()})({0:function(t,e,n){t.exports=n("56d7")},"09e4":function(t,e,n){"use strict";n.d(e,"a",(function(){return a}));n("ac1f"),n("5319"),n("21ac");var o=n("4360"),r=n("ec6d"),a=function(t,e){if(void 0!==e)switch(t.data){case"对方暂未绑定设备":e.$router.replace("/device/status/notOtherBind");break;case"您暂未绑定设备":e.$router.replace("/device/status/notBind");break;case"对方的设备不在线":e.$router.replace("/device/status/notOtherOnline");break;case"您的设备不在线":e.$router.replace("/device/status/notOnline");break;case"对方还没有开始使用心晓设备尚无报告可看":e.$router.replace("/device/status/notOtherUsed");break;case"您还没有开始使用心晓设备尚无报告可看":e.$router.replace("/device/status/notUsed");break;case"请先注册":e.$router.replace("/user/person/register");break;default:break}switch(t.code){case 210:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 211:console.log("errHandler 211"),o["a"].commit("setToken",""),window.localStorage.removeItem("token"),window.location.reload(),Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 401:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 400:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 412:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 500:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 501:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 510:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 300:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;case 310:Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break;default:null==e?(console.log("null this point"),null==t.data||0===t.data.length?Object(r["a"])({iconShow:!0,type:"cross",msg:t}):Object(r["a"])({iconShow:!0,type:"cross",msg:t.data})):null==t.data||0===t.data.length?Object(r["a"])({iconShow:!0,type:"cross",msg:t}):Object(r["a"])({iconShow:!0,type:"cross",msg:t.data});break}}},"21ac":function(t,e,n){"use strict";n.d(e,"a",(function(){return o})),n.d(e,"c",(function(){return r})),n.d(e,"b",(function(){return a})),n.d(e,"d",(function(){return c}));n("2b0e");var o=function(t){this.$confirm({iconShow:!0,type:"cross",msg:t})},r=function(t){this.$confirm({iconShow:!0,type:"success",msg:t})},a=function(t){this.$confirm({iconShow:!1,type:"cross",msg:t})},c=function(t){this.$confirm({iconShow:!0,type:"cross",msg:t})}},4360:function(t,e,n){"use strict";var o={};n.r(o),n.d(o,"setToken",(function(){return i})),n.d(o,"setContent",(function(){return u})),n.d(o,"setInfo",(function(){return s})),n.d(o,"setuserhistury",(function(){return l})),n.d(o,"setmonitorData",(function(){return m})),n.d(o,"setReportId",(function(){return d})),n.d(o,"setDateDetails",(function(){return f})),n.d(o,"setDateDetails2",(function(){return h})),n.d(o,"setUid",(function(){return p})),n.d(o,"setIsConcern",(function(){return b})),n.d(o,"setIsLoading",(function(){return k})),n.d(o,"setShareData",(function(){return x})),n.d(o,"setCurrentDay",(function(){return g})),n.d(o,"setCurrentDayTime",(function(){return F})),n.d(o,"setUserEmail",(function(){return v})),n.d(o,"setCurrentScore",(function(){return y})),n.d(o,"setIsCheck",(function(){return w})),n.d(o,"setIsMonitoring",(function(){return C}));var r={};n.r(r),n.d(r,"setToken",(function(){return D})),n.d(r,"setContent",(function(){return S})),n.d(r,"setInfo",(function(){return E})),n.d(r,"setuserhistury",(function(){return B})),n.d(r,"setmonitorData",(function(){return O})),n.d(r,"setReportId",(function(){return I})),n.d(r,"setDateDetails",(function(){return j})),n.d(r,"setDateDetails2",(function(){return T})),n.d(r,"setUid",(function(){return A})),n.d(r,"setIsConcern",(function(){return R})),n.d(r,"setIsLoading",(function(){return N})),n.d(r,"setShareData",(function(){return P})),n.d(r,"setCurrentDay",(function(){return L})),n.d(r,"setCurrentDayTime",(function(){return _})),n.d(r,"setUserEmail",(function(){return $})),n.d(r,"setCurrentScore",(function(){return M})),n.d(r,"setIsCheck",(function(){return q})),n.d(r,"setIsMonitoring",(function(){return H}));var a=n("2b0e"),c=n("2f62"),i=function(t,e){t.token=e},u=function(t,e){t.content=e},s=function(t,e){t.info=e},l=function(t,e){t.userhistury=e},m=function(t,e){t.monitorData=e},d=function(t,e){t.reportId=e},f=function(t,e){t.dateDetails=e},h=function(t,e){t.dateDetails2=e},p=function(t,e){t.uid=e},b=function(t,e){t.isConcern=e},k=function(t,e){t.isLoading=e},x=function(t,e){t.shareData.id=e.id,t.shareData.shareKey=e.shareKey},g=function(t,e){t.currentDay=e},F=function(t,e){t.currentDayTime=e},v=function(t,e){t.userEmail=e},y=function(t,e){t.currentScore=e},w=function(t,e){t.isCheck=e},C=function(t,e){t.isMonitoring=e},D=function(t,e){var n=t.commit;n("setToken",e)},S=function(t,e){var n=t.commit;n("setContent",e)},E=function(t,e){var n=t.commit;n("setInfo",e)},B=function(t,e){var n=t.commit;n("setuserhistury",e)},O=function(t,e){var n=t.commit;n("setmonitorData",e)},I=function(t,e){var n=t.commit;n("setReportId",e)},j=function(t,e){var n=t.commit;n("setDateDetails",e)},T=function(t,e){var n=t.commit;n("setDateDetails2",e)},A=function(t,e){var n=t.commit;n("setUid",e)},R=function(t,e){var n=t.commit;n("setIsConcern",e)},N=function(t,e){var n=t.commit;n("setIsLoading",e)},P=function(t,e){var n=t.commit;n("setShareData",e)},L=function(t,e){var n=t.commit;n("setCurrentDay",e)},_=function(t,e){var n=t.commit;n("setCurrentDayTime",e)},$=function(t,e){var n=t.commit;n("setUserEmail",e)},M=function(t,e){var n=t.commit;n("setCurrentScore",e)},q=function(t,e){var n=t.commit;n("setIsCheck",e)},H=function(t,e){var n=t.commit;n("setIsMonitoring",e)},U=n("94d5");a["a"].use(c["a"]);var X={organizationId:1,appId:"wx4fff3cfe5325d612",token:"",content:"",info:{},userhistury:"",monitorData:"",reportId:"",isConcern:!1,uid:"",trendColor:{totalCardiacEnergy:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],sympatheticNerveTension:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],vagusNerveTension:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],autonomicNerveAbility:[{min:0,max:.4,color:"#FE552E"},{min:.4,max:.7,color:"#FFC10E"},{min:.7,max:1.5,color:"#4A7DD8"},{min:1.5,max:2.5,color:"#FFC10E"},{min:2.5,max:999999999,color:"#FE552E"}],heartRateDecelerationForce:[{min:0,max:2.5,color:"#FE552E"},{min:2.5,max:4.6,color:"#FFC10E"},{min:4.6,max:15,color:"#4A7DD8"},{min:15,max:20,color:"#FFC10E"},{min:20,max:999999999,color:"#FE552E"}],baseHeartRate:[{min:0,max:43,color:"#FE552E"},{min:43,max:50,color:"#FFC10E"},{min:50,max:74,color:"#4A7DD8"},{min:74,max:83,color:"#FFC10E"},{min:83,max:999999999,color:"#FE552E"}],baseBreatheRate:[{min:0,max:9,color:"#FE552E"},{min:9,max:12,color:"#FFC10E"},{min:12,max:18,color:"#4A7DD8"},{min:18,max:21,color:"#FFC10E"},{min:21,max:999999999,color:"#FE552E"}],breathePause:[{min:0,max:5,color:"#4A7DD8"},{min:5,max:15,color:"#FFC10E"},{min:15,max:30,color:"#FE552E"},{min:30,max:999999999,color:"#FE552E"}],arrhythmiaRisk:[{min:0,max:9,color:"#FE552E"},{min:9,max:12,color:"#FFC10E"},{min:12,max:18,color:"#4A7DD8"},{min:18,max:21,color:"#FFC10E"},{min:21,max:999999999,color:"#FE552E"}]},colors:{hypertension:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"需关注",color:"#FFC066"},{min:0,max:30,text:"密切关注",color:"#F68989"}],autonomicNerveAbility:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],sympatheticNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]},hyperglycemia:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"需关注",color:"#FFC066"},{min:0,max:30,text:"密切关注",color:"#F68989"}],autonomicNerveDamage:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],vagusNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]},risk:{arrhythmiaRisk:[{min:0,max:30,text:"高风险",color:"#F68989"},{min:30,max:50,text:"中风险",color:"#FFC066"},{min:50,max:180,text:"低风险",color:"#9BD0FF"}],asystoleRisk:[{min:0,max:180,text:"低风险",color:"#9BD0FF"},{min:180,max:220,text:"中风险",color:"#FFC066"},{min:220,max:270,text:"高风险",color:"#F68989"}],coronaryHeartRisk:[{min:0,max:25,text:"高风险",color:"#F68989"},{min:25,max:50,text:"中风险",color:"#FFC066"},{min:50,max:100,text:"低风险",color:"#9BD0FF"}],infarctionRisk:[{min:0,max:15,text:"高风险",color:"#F68989"},{min:15,max:25,text:"中风险",color:"#FFC066"},{min:25,max:100,text:"低风险",color:"#9BD0FF"}],heartRateDecelerationForce:[{min:0,max:2.5,text:"过低",color:"#F68989"},{min:2.5,max:4.6,text:"偏低",color:"#FFC066"},{min:4.6,max:15,text:"正常",color:"#9BD0FF"},{min:15,max:20,text:"偏高",color:"#FFC066"},{min:20,max:30,text:"过高",color:"#F68989"}]},heart:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],currentBaseHeartRate:[{min:0,max:43,text:"过低",color:"#F68989"},{min:43,max:55,text:"偏低",color:"#FFC066"},{min:55,max:74,text:"正常",color:"#9BD0FF"},{min:74,max:83,text:"偏高",color:"#FFC066"},{min:83,max:120,text:"过高",color:"#F68989"}],averageBaseHeartRate:[{min:0,max:43,text:"过低",color:"#F68989"},{min:43,max:55,text:"偏低",color:"#FFC066"},{min:55,max:74,text:"正常",color:"#9BD0FF"},{min:74,max:83,text:"偏高",color:"#FFC066"},{min:83,max:120,text:"过高",color:"#F68989"}],tooFastDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}],tooSlowDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}]},breathe:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],breathePause:[{min:0,max:5,text:"正常",color:"#B8A4FF"},{min:5,max:15,text:"低风险",color:"#9BD0FF"},{min:15,max:30,text:"中风险",color:"#FFC066"},{min:30,max:60,text:"高风险",color:"#F68989"}],currentBaseBreatheRate:[{min:0,max:9,text:"过低",color:"#F68989"},{min:9,max:12,text:"偏低",color:"#FFC066"},{min:12,max:18,text:"正常",color:"#9BD0FF"},{min:18,max:21,text:"偏高",color:"#FFC066"},{min:21,max:25,text:"过高",color:"#F68989"}],averageBaseBreatheRate:[{min:0,max:9,text:"过低",color:"#F68989"},{min:9,max:12,text:"偏低",color:"#FFC066"},{min:12,max:18,text:"正常",color:"#9BD0FF"},{min:18,max:21,text:"偏高",color:"#FFC066"},{min:21,max:25,text:"过高",color:"#F68989"}],tooFastDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}],tooSlowDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}]},sleep:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}]},heartRateVariability:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],autonomicNerveAbility:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],sympatheticNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}],vagusNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]}},isLoading:!1,shareData:{shareKey:"",id:""},currentDay:"",currentDayTime:"",userEmail:"",currentScore:"",dateDetails:[],dateDetails2:[],isCheck:!1,isMonitoring:!1},J=new c["a"].Store({state:X,getters:U,actions:r,mutations:o});e["a"]=J},"56d7":function(t,e,n){"use strict";n.r(e);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("2b0e"),r=(n("5cfb"),n("b970")),a=(n("157a"),n("8ac2"),n("d940"),n("8b06")),c=n.n(a),i=(n("1157"),n("5530")),u=n("a925"),s=n("3c69"),l=n("91f4"),m=n.n(l),d=n("b459"),f=n.n(d),h={app:{user:"User",Record:"Record",Report:"Report"},user:{weight:"Weight",height:"Height",age:"Age",peopleInBed:"People in bed"}},p={app:{user:"用户"}};o["a"].use(u["a"]);var b={en:Object(i["a"])(Object(i["a"])({},m.a),h),zh:Object(i["a"])(Object(i["a"])({},f.a),p)},k=new u["a"]({locale:"en",messages:b});function x(t){"en"===t?s["a"].use(t,m.a):"zh"===t&&s["a"].use(t,f.a)}var g=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"app"}},[n("keep-alive",[t.$route.meta.keepAlive?n("router-view"):t._e()],1),t.$route.meta.keepAlive?t._e():n("router-view"),n("van-dialog",{attrs:{title:"Tips","show-cancel-button":!1,"show-confirm-button":!1,width:"90%"},model:{value:t.tipDialog,callback:function(e){t.tipDialog=e},expression:"tipDialog"}},[n("div",{staticClass:"tip-top"},[n("p",{staticClass:"tip-content"},[t._v("Your friend "),n("span",{staticClass:"name"},[t._v(t._s(t.nickname))]),t._v(" want to follow you, after that he will recieve your report.")]),n("div",{staticClass:"tip-info"},[n("p",{staticClass:"info"},[t._v(t._s(t.remark))])])]),n("div",{staticClass:"tip-btn"},[n("van-row",{attrs:{type:"flex",justify:"space-around"}},[n("van-col",{attrs:{span:"8"}},[n("div",{staticClass:"agree-btn",on:{click:function(e){return t.agreeClick(!1)}}},[t._v(" Disagree ")])]),n("van-col",{attrs:{span:"8"}},[n("div",{staticClass:"disagree-btn",on:{click:function(e){return t.agreeClick(!0)}}},[t._v(" Agree")])])],1)],1)])],1)},F=[],v=(n("caad"),n("d3b7"),n("ac1f"),n("2532"),n("1276"),n("4328")),y=n.n(v),w=n("4360"),C=n("2f62"),D=n("995e"),S=n("7cb4"),E=n("09e4"),B=n("d399");o["a"].use(B["a"]);n("18a0");var O={name:"App",data:function(){return{tipDialog:!1,websock:null,nickname:"",requestCode:"",remark:"",isPending:!1,tip:"Michelle \n rejected your invitation",icon:""}},watch:{token:function(t){if(t){var e={id:1,body:{token:t},direction:"Request",type:"login"};this.sendSock(e)}}},mounted:function(){var t=this;this.initWebSocket(),window.addEventListener("pagehide",(function(e){t.isMonitoring&&t.asyncClose()}));var e=this.$route.path;window.g.PROPAGATE_URL;e.includes("dictionary")&&window.location.href,e.includes("about")&&window.location.href},computed:Object(i["a"])({},Object(C["c"])(["organizationId","appId","uid","token","isMonitoring","monitorData"])),methods:Object(i["a"])(Object(i["a"])({},Object(C["b"])(["setmonitorData"])),{},{createXHR:function(){if("undefined"!=typeof XMLHttpRequest)return new XMLHttpRequest;if("undefined"!=typeof ActiveXObject){if("string"!=typeof arguments.callee.activeXString){var t,e,n=["MSXML2.XMLHttp.6.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp"];for(t=0,e=n.length;t<e;t++)try{new ActiveXObject(n[t]),arguments.callee.activeXString=n[t];break}catch(o){}}return new ActiveXObject(arguments.callee.activeXString)}throw new Error("No XHR object available.")},asyncClose:function(){var t=this.createXHR();t.onreadystatechange=function(){4==t.readyState&&(t.status>=200&&t.status<300||304==t.status?console.log(t.responseText):console.log("Request was unsuccessful: "+t.status))},t.open("post",D["e"],!1),t.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");var e={"#TOKEN#":this.token};this.uid&&(e["uid"]=this.uid),t.send(y.a.stringify(e))},agreeClick:function(t){var e=this;if(this.tipDialog=!1,!this.isPending){this.isPending=!0;var n={url:D["u"],data:{requestCode:this.requestCode,isAccept:t}};Object(S["a"])(n).then((function(n){200===n.data.code?t&&e.$router.push("/user"):Object(E["a"])(n.data)})).catch((function(t){console.log(t)})).finally((function(){e.isPending=!1}))}},initWebSocket:function(){var t=this,e="ws://39.108.84.118:8097/heart_wechat/heartServer.ws";this.websock=new WebSocket(e),this.websock.onmessage=function(e){t.websocketonmessage(e)},this.websock.onclose=function(e){t.websocketclose(e)},this.websock.onopen=function(){t.websocketOpen()},this.websock.onerror=function(){console.log("server connect error."),Object(E["a"])("server connect error.",t)}},sendSock:function(t){this.websock.readyState===this.websock.OPEN?this.websocketsend(t):this.websock.readyState===this.websock.CONNECTING?setTimeout((function(){this.sendSock(t),console.log("WebSocket connecting")}),1e3):console.log("WebSocket not connected")},websocketonmessage:function(t){console.log("数据接收");var e=JSON.parse(t.data);if(console.log("res="+JSON.stringify(e)),"invitation"===e.type)console.log("socketbody,invitation==="+e),this.nickname=e.body.nickname,e.body.remark?this.remark=e.body.remark:this.remark="Hello my friend.I want to follow you to know your body information.",this.requestCode=e.body.requestCode,this.tipDialog=!0;else if("follow"===e.type){e.body.accept?this.tip=e.body.nickname+" \n accepted your invitation":this.tip=e.body.nickname+" \n rejected your invitation";var n=e.body.headImageUrl;if(null!==n){var o=n.split("/");this.getResource(o[o.length-1])}else Object(B["a"])({message:this.tip,icon:"",className:"user-tip",position:"bottom"});this.$router.push("/user")}else"monitorDataEvent"===e.type&&(console.log("socketbody,monitorDataEvent==="+JSON.stringify(e)),w["a"].commit("setmonitorData",JSON.stringify(e)))},websocketsend:function(t){this.websock.send(JSON.stringify(t))},websocketclose:function(t){var e=this;console.log("connection closed ("+t.code+")");var n=w["a"].state.token;n&&setTimeout((function(){e.initWebSocket()}),5e3)},websocketOpen:function(){console.log("连接成功");var t=w["a"].state.token;if(t){var e={id:1,body:{token:t},direction:"Request",type:"login"};this.sendSock(e)}},getResource:function(t){var e=this;if(console.log("getResource ",this.isPending),!this.isPending){this.isPending=!0;var n={url:D["r"],data:{name:t,type:1}};Object(S["a"])(n).then((function(t){e.isPending=!1,e.icon="data:image/jpeg;base64,"+t.data,Object(B["a"])({message:e.tip,icon:e.icon,className:"user-tip",position:"bottom"})})).catch((function(t){console.log(t)})).finally((function(){e.isPending=!1}))}}})},I=O,j=(n("7c55"),n("0c7c")),T=Object(j["a"])(I,g,F,!1,null,null,null),A=T.exports,R=(n("99af"),n("c975"),n("25f0"),n("5319"),n("841c"),n("8c4f")),N=n("21ac"),P=function(){return n.e("chunk-15b82ac7").then(n.bind(null,"37fe"))},L=function(){return n.e("chunk-276f544e").then(n.bind(null,"f456"))},_=function(){return Promise.all([n.e("chunk-5454ba98"),n.e("chunk-72e0f319")]).then(n.bind(null,"1297"))},$=function(){return n.e("chunk-83e2ee20").then(n.bind(null,"9075"))},M=function(){return n.e("chunk-7adccdc4").then(n.bind(null,"5f0f"))},q=function(){return n.e("chunk-1ca133e8").then(n.bind(null,"943f"))},H=function(){return n.e("chunk-27516ec5").then(n.bind(null,"ebce"))},U=function(){return n.e("chunk-5a892a63").then(n.bind(null,"ea3c"))},X=function(){return n.e("chunk-9a1a7310").then(n.bind(null,"7720"))},J=function(){return n.e("chunk-72d999b6").then(n.bind(null,"f34b"))},K=function(){return n.e("chunk-522c1088").then(n.bind(null,"7545"))},z=function(){return n.e("chunk-40fdc8e0").then(n.bind(null,"8b2b"))},W=function(){return n.e("chunk-3e11f0f8").then(n.bind(null,"451a"))},G=function(){return n.e("chunk-fb2f2ed8").then(n.bind(null,"f429"))},V=function(){return n.e("chunk-080f9df9").then(n.bind(null,"4300"))},Q=function(){return n.e("chunk-3e9fadd1").then(n.bind(null,"849d"))},Y=function(){return n.e("chunk-261ccaf6").then(n.bind(null,"9f8a"))},Z=function(){return n.e("chunk-0c40879e").then(n.bind(null,"c8da"))},tt=function(){return n.e("chunk-a5a8e75e").then(n.bind(null,"f634"))},et=function(){return n.e("chunk-7e2097d8").then(n.bind(null,"0e4b"))},nt=function(){return n.e("chunk-4900818a").then(n.bind(null,"8cdd"))},ot=function(){return n.e("chunk-53062cdf").then(n.bind(null,"363f"))},rt=function(){return n.e("chunk-422b93df").then(n.bind(null,"86a8"))},at=function(){return n.e("chunk-07a136cc").then(n.bind(null,"2da4"))},ct=function(){return n.e("chunk-25e9f132").then(n.bind(null,"6712"))},it=function(){return Promise.all([n.e("chunk-5454ba98"),n.e("chunk-2ded6e59")]).then(n.bind(null,"be50"))},ut=R["a"].prototype.push;R["a"].prototype.push=function(t){return ut.call(this,t).catch((function(t){return t}))},o["a"].use(R["a"]);var st=new R["a"]({mode:"hash",base:"",routes:[{path:"",redirect:"/jimihome"},{path:"/login",name:"login",meta:{back:"exit"},component:M},{path:"/personalInformation",name:"personalInformation",meta:{back:"goBack"},component:K},{path:"/binding",name:"binding",meta:{back:"goBack"},component:Y},{path:"/scanCodeBinding",name:"scanCodeBinding",meta:{back:"goBack"},component:Z},{path:"/setting",name:"setting",meta:{back:"goBack"},component:Q},{path:"/deviceInformation",name:"deviceInformation",meta:{back:"goBack"},component:tt},{path:"/help",name:"help",meta:{back:"goBack"},component:et},{path:"/about",name:"about",meta:{back:"goBack"},component:nt},{path:"/update",name:"update",meta:{back:"goBack"},component:ot},{path:"/installation",name:"installation",meta:{back:"goBack"},component:rt},{path:"/tutorial",name:"tutorial",meta:{back:"goBack"},component:at},{path:"/jimihome",name:"jimihome",component:P,redirect:"/report",children:[{path:"/record",name:"record",component:L,meta:{tabflag:"/record",back:"exit"}},{path:"/trend",name:"trend",component:J,meta:{tabflag:"/record",back:"goBack"}},{path:"/report",name:"report",component:_,meta:{keepAlive:!1,isBack:!1,tabflag:"/report",back:"exit"}},{path:"/user",name:"user",component:$,meta:{tabflag:"/user",back:"exit"}},{path:"/myCode",name:"myCode",component:q,meta:{tabflag:"/user",back:"goBack"}},{path:"/scanToFollow",name:"scanToFollow",component:ct,meta:{tabflag:"/user",back:"goBack"}},{path:"/scanCode",name:"scanCode",component:H,meta:{tabflag:"/user",back:"goBack"}},{path:"/FollowPeople",name:"FollowPeople",component:it,meta:{tabflag:"/user",back:"goBack"}},{path:"/userInfo",name:"userInfo",component:U,meta:{tabflag:"/user",back:"goBack"}},{path:"/heartRateVariability",name:"heartRateVariability",component:z,meta:{tabflag:"/report",back:"goBack"}},{path:"/healthBriefing",name:"healthBriefing",component:W,meta:{tabflag:"/report",back:"goBack"}},{path:"/monitor",name:"monitor",component:G,meta:{tabflag:"/report",back:"goBack"}},{path:"/healthEvaluation",name:"healthEvaluation",component:V,meta:{tabflag:"/report",back:"goBack"}}]},{path:"/attention",name:"attention",component:X,meta:{keepAlive:!0,isBack:!1,back:"goBack"}}]});function lt(t,e){w["a"].commit("setIsLoading",!0),console.log("getLevel");var n={url:D["o"],data:{}},o=this;Object(S["a"])(n).then((function(n){if(200===n.data.code){var r=n.data.data;w["a"].commit("setIsLoading",!1),mt(r,t,e)}else Object(E["a"])(n.data,o),w["a"].commit("setIsLoading",!1)})).catch((function(t){if(console.log("abc",t),w["a"].commit("setIsLoading",!1),null==t.response&&"Network Error"===t.toString().substr(7,13))return Object(E["a"])("network error!",o),void console.log("network error!network error!network error!");t.response&&500===t.response.status&&(console.log("abcd",t),console.log(t),Object(E["a"])("network error!",o))}))}function mt(t,e,n){var o="";switch(0!==t&&w["a"].commit("setIsConcern",!1),t){case 0:break;case 1:"/login"!==e&&(o="/login");break;case 2:"/personalInformation"!==e&&(o="/personalInformation");break;case 3:"/monitor"===e&&""==w["a"].getters.uid&&(Object(E["a"])("Please bind the device first."),o="/report");break;case 4:break;default:break}""===o?n():n({path:o})}window.localStorage.getItem("token")&&w["a"].commit("setToken",window.localStorage.getItem("token")),window.localStorage.getItem("reportId")&&w["a"].commit("setReportId",JSON.parse(window.localStorage.getItem("reportId"))),window.localStorage.getItem("userhistury")&&w["a"].commit("setuserhistury",window.localStorage.getItem("userhistury")),st.beforeEach((function(t,e,n){var o=window.location.href;if(o.includes("code")){var r=o.split("code")[1];if(r.includes("#")){var a=r.split("#");o=o.split("?")[0]+"#"+a[1]}}var c=w["a"].state.token;if(t.fullPath.includes("shareKey")?n():c?t.path.includes("dictionary")||t.path.includes("about")||t.path.includes("concernTip")||t.path.includes("passive")||t.path.includes("propagate")||t.path.includes("home")&&e.path.includes("home")?n():lt(t.path,n):"/login"!==t.path?n({path:"/login"}):n(),e.query.reportId&&!t.query.reportId){var i=JSON.parse(JSON.stringify(t.query));i["reportId"]=e.query.reportId,n({path:t.path,query:i})}})),st.afterEach((function(t,e,n){window.scrollTo(0,0)}));var dt=st,ft=n("ec6d");x(k.locale),o["a"].use(r["a"]),o["a"].use(c.a),o["a"].prototype.$alertError=N["a"],o["a"].prototype.$alertInfo=N["b"],o["a"].prototype.$alertSuccess=N["c"],o["a"].prototype.$alertWarning=N["d"],o["a"].prototype.$confirm=ft["a"],o["a"].config.productionTip=!1,new o["a"]({router:dt,store:w["a"],i18n:k,render:function(t){return t(A)},mounted:function(){var t=this;document.addEventListener("plusready",(function(e){var n=null;plus.key.addEventListener("backbutton",(function(){window.location.hash;console.log(t.$route);var e=t.$route.meta.back;console.log(e),"exit"===e?n?(new Date).getTime()-n<1e3&&plus.runtime.quit():(n=(new Date).getTime(),plus.nativeUI.toast("Press again to exit the app",{duration:"short"}),setTimeout((function(){n=null}),1e3)):history.go(-1)}),!1)}))}}).$mount("#app")},"7c55":function(t,e,n){"use strict";var o=n("88c8"),r=n.n(o);r.a},"7cb4":function(t,e,n){"use strict";n.d(e,"a",(function(){return s})),n.d(e,"b",(function(){return l})),n.d(e,"c",(function(){return m}));n("d3b7");var o=n("4328"),r=n.n(o),a=n("bc3a"),c=n.n(a),i=n("4360");c.a.interceptors.request.use((function(t){return""!==i["a"].state.token&&(""===t.data?t.data+="#TOKEN#="+i["a"].state.token:t.data+="&#TOKEN#="+i["a"].state.token),t}),(function(t){return Promise.reject(t)}));var u=c.a,s=function(t){return u({method:"post",url:t.url,timeout:12e4,responseType:t.responseType?t.responseType:"",headers:{"Content-type":"application/x-www-form-urlencoded; charset=UTF-8","Accept-Language":"en_US"},data:r.a.stringify(t.data)})},l=function(t){for(var e=[],n=0;n<t.length;n++)e.push(t[n].id+"="+t[n].have);return e.toString()},m=function(t,e){return t.sort((function(t,n){var o=t[e],r=n[e];return"NA"===o||"NA"===r?"NA"===r?-1:"NA"===o?1:0:o>r?-1:o<r?1:0}))}},"88c8":function(t,e,n){},"8ac2":function(t,e,n){},"94d5":function(t,e,n){"use strict";n.r(e),n.d(e,"token",(function(){return o})),n.d(e,"content",(function(){return r})),n.d(e,"info",(function(){return a})),n.d(e,"userhistury",(function(){return c})),n.d(e,"monitorData",(function(){return i})),n.d(e,"trendColor",(function(){return u})),n.d(e,"colors",(function(){return s})),n.d(e,"reportId",(function(){return l})),n.d(e,"dateDetails",(function(){return m})),n.d(e,"dateDetails2",(function(){return d})),n.d(e,"organizationId",(function(){return f})),n.d(e,"isConcern",(function(){return h})),n.d(e,"appId",(function(){return p})),n.d(e,"uid",(function(){return b})),n.d(e,"isLoading",(function(){return k})),n.d(e,"shareData",(function(){return x})),n.d(e,"currentDay",(function(){return g})),n.d(e,"currentDayTime",(function(){return F})),n.d(e,"userEmail",(function(){return v})),n.d(e,"currentScore",(function(){return y})),n.d(e,"isCheck",(function(){return w})),n.d(e,"isMonitoring",(function(){return C}));var o=function(t){return t.token},r=function(t){return t.content},a=function(t){return t.info},c=function(t){return t.userhistury},i=function(t){return t.monitorData},u=function(t){return t.trendColor},s=function(t){return t.colors},l=function(t){return t.reportId},m=function(t){return t.dateDetails},d=function(t){return t.dateDetails2},f=function(t){return t.organizationId},h=function(t){return t.isConcern},p=function(t){return t.appId},b=function(t){return t.uid},k=function(t){return t.isLoading},x=function(t){return t.shareData},g=function(t){return t.currentDay},F=function(t){return t.currentDayTime},v=function(t){return t.userEmail},y=function(t){return t.currentScore},w=function(t){return t.isCheck},C=function(t){return t.isMonitoring}},"990b":function(t,e,n){"use strict";var o=n("fb8b"),r=n.n(o);r.a},"995e":function(t,e,n){"use strict";var o;n.d(e,"z",(function(){return r})),n.d(e,"o",(function(){return a})),n.d(e,"F",(function(){return c})),n.d(e,"x",(function(){return i})),n.d(e,"a",(function(){return u})),n.d(e,"B",(function(){return s})),n.d(e,"C",(function(){return l})),n.d(e,"e",(function(){return m})),n.d(e,"m",(function(){return d})),n.d(e,"b",(function(){return f})),n.d(e,"J",(function(){return h})),n.d(e,"g",(function(){return p})),n.d(e,"G",(function(){return b})),n.d(e,"K",(function(){return k})),n.d(e,"u",(function(){return x})),n.d(e,"v",(function(){return g})),n.d(e,"I",(function(){return F})),n.d(e,"k",(function(){return v})),n.d(e,"L",(function(){return y})),n.d(e,"j",(function(){return w})),n.d(e,"t",(function(){return C})),n.d(e,"p",(function(){return D})),n.d(e,"H",(function(){return S})),n.d(e,"l",(function(){return E})),n.d(e,"s",(function(){return B})),n.d(e,"y",(function(){return O})),n.d(e,"w",(function(){return I})),n.d(e,"f",(function(){return j})),n.d(e,"r",(function(){return T})),n.d(e,"d",(function(){return A})),n.d(e,"i",(function(){return R})),n.d(e,"h",(function(){return N})),n.d(e,"A",(function(){return P})),n.d(e,"q",(function(){return L})),n.d(e,"n",(function(){return _})),n.d(e,"E",(function(){return $})),n.d(e,"D",(function(){return M})),n.d(e,"c",(function(){return q})),o="http://api.ddnurse.senviv.cn/heart_wechat";var r=o+"/wechat/login",a=o+"/access/getLevel",c=o+"/emergency/remove",i=o+"/emergency/list",u=o+"/emergency/add",s=o+"/device/monitor",l=o+"/device/open",m=o+"/device/close",d=o+"/device/getInfo",f=o+"/device/bind",h=o+"/device/unbind",p=o+"/device/editDeviceName",b=o+"/account/requestFollow",k=o+"/account/unfollow2",x=o+"/account/isAcceptFollow",g=o+"/account/isFollow",F=o+"/account/submitInfo",v=o+"/account/getConcerns2",y=o+"/account/updateRelationship",w=o+"/account/getInfo2",C=o+"/account/getUserInfo",D=o+"/account/getQRCode",S=o+"/account/scanQRCode",E=o+"/report/getContent",B=o+"/report/getTrend",O=o+"/report/listIds2",I=o+"/report/listDays",j=o+"/report/current",T=o+"/resource/get",A=o+"/report/isMyReport",R=o+"/email/register",N=o+"/email/login",P=o+"/email/modifyPassword",L=o+"/consult/getHealthDiaryTrend",_=o+"/consult/getHealthDiary",$=o+"/consult/addHealthDiary",M=o+"/consult/listProblems2",q=o+"/upgrade/getLatestAppUpgradePack"},d940:function(t,e,n){},ec6d:function(t,e,n){"use strict";n("d3b7");var o=n("2b0e"),r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{directives:[{name:"show",rawName:"v-show",value:t.isShow,expression:"isShow"}],staticClass:"tip"},[n("div",{staticClass:"tip-one"},[n("div",{staticClass:"left-out"},[n("div",{directives:[{name:"show",rawName:"v-show",value:t.iconShow,expression:"iconShow"}],staticClass:"left"},[n("van-icon",{attrs:{name:t.text.type,size:24}})],1)]),n("div",{staticClass:"right"},[n("p",{staticClass:"content"},[t._v(t._s(t.text.msg))])])])])},a=[],c={data:function(){return{iconShow:!0,isShow:!0,text:{iconShow:!0,type:"提示",msg:""},btn:{ok:"确定",no:"取消"},timer:""}},mounted:function(){console.log("dialog mounted")},watch:{text:function(t){this.isShow=!0,this.iconShow=t.iconShow,this.timer=setTimeout(this.close,2e3)}},methods:{close:function(){this.isShow=!1},ok:function(){this.isShow=!1}}},i=c,u=(n("990b"),n("0c7c")),s=Object(u["a"])(i,r,a,!1,null,"b4f8f40a",null),l=s.exports,m=o["a"].extend(l),d=function(t){return new Promise((function(e,n){var o=new m({el:document.createElement("div")});document.body.appendChild(o.$el),o.text=t,console.log("confirmDom",o.text),o.ok=function(){e(),o.isShow=!1},o.close=function(){n(),o.isShow=!1}})).catch((function(t){}))};e["a"]=d},fb8b:function(t,e,n){}});