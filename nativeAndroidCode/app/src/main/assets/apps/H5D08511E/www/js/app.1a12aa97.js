(function(e){function t(t){for(var o,r,i=t[0],u=t[1],s=t[2],l=0,m=[];l<i.length;l++)r=i[l],Object.prototype.hasOwnProperty.call(c,r)&&c[r]&&m.push(c[r][0]),c[r]=0;for(o in u)Object.prototype.hasOwnProperty.call(u,o)&&(e[o]=u[o]);d&&d(t);while(m.length)m.shift()();return a.push.apply(a,s||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],o=!0,r=1;r<n.length;r++){var i=n[r];0!==c[i]&&(o=!1)}o&&(a.splice(t--,1),e=u(u.s=n[0]))}return e}var o={},r={app:0},c={app:0},a=[];function i(e){return u.p+"js/"+({}[e]||e)+"."+{"chunk-080f9df9":"6cdaf075","chunk-0c40879e":"33fb9e11","chunk-0ce56026":"fe5106ba","chunk-17d99d86":"12ea0868","chunk-1beafda9":"1eb0beee","chunk-1ca133e8":"a0b236e5","chunk-261ccaf6":"e529efbd","chunk-27c2ddb8":"0048616c","chunk-2c5ad1b6":"d09b9c65","chunk-2fe37ba1":"81fbe79d","chunk-40fdc8e0":"ba6cc7f4","chunk-4900818a":"7ec00dc4","chunk-490e41db":"8e838ee7","chunk-4cef3252":"376048ae","chunk-5003a44f":"e2749932","chunk-553c85ec":"690c487d","chunk-56b8875c":"c9ad4f3f","chunk-57cbf732":"abe1fcf5","chunk-61ec6db1":"59e3b308","chunk-7adccdc4":"765cd6fc","chunk-7e2097d8":"e4281748","chunk-ce8379e4":"84c9756a","chunk-ddc73884":"46295f17","chunk-ea2eb0ba":"045f28ac","chunk-ea6af63a":"610aaadd"}[e]+".js"}function u(t){if(o[t])return o[t].exports;var n=o[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.e=function(e){var t=[],n={"chunk-080f9df9":1,"chunk-0c40879e":1,"chunk-0ce56026":1,"chunk-17d99d86":1,"chunk-1beafda9":1,"chunk-1ca133e8":1,"chunk-261ccaf6":1,"chunk-27c2ddb8":1,"chunk-2c5ad1b6":1,"chunk-2fe37ba1":1,"chunk-40fdc8e0":1,"chunk-4900818a":1,"chunk-490e41db":1,"chunk-4cef3252":1,"chunk-5003a44f":1,"chunk-553c85ec":1,"chunk-56b8875c":1,"chunk-57cbf732":1,"chunk-61ec6db1":1,"chunk-7adccdc4":1,"chunk-7e2097d8":1,"chunk-ce8379e4":1,"chunk-ddc73884":1,"chunk-ea2eb0ba":1,"chunk-ea6af63a":1};r[e]?t.push(r[e]):0!==r[e]&&n[e]&&t.push(r[e]=new Promise((function(t,n){for(var o="css/"+({}[e]||e)+"."+{"chunk-080f9df9":"faa541d8","chunk-0c40879e":"766be55b","chunk-0ce56026":"edc6e843","chunk-17d99d86":"3981b293","chunk-1beafda9":"7e7f92a0","chunk-1ca133e8":"6f6e125b","chunk-261ccaf6":"b3c9410a","chunk-27c2ddb8":"2146ee2f","chunk-2c5ad1b6":"d1235c7c","chunk-2fe37ba1":"57c3916e","chunk-40fdc8e0":"49c55b9f","chunk-4900818a":"3268b12b","chunk-490e41db":"8db447b2","chunk-4cef3252":"e9fca74c","chunk-5003a44f":"55a37c44","chunk-553c85ec":"bf8656ff","chunk-56b8875c":"9f9ecd44","chunk-57cbf732":"532097e3","chunk-61ec6db1":"25db2836","chunk-7adccdc4":"81fa7ee4","chunk-7e2097d8":"2db2874b","chunk-ce8379e4":"f692b361","chunk-ddc73884":"a79fdb69","chunk-ea2eb0ba":"6e83bed6","chunk-ea6af63a":"0e0db231"}[e]+".css",c=u.p+o,a=document.getElementsByTagName("link"),i=0;i<a.length;i++){var s=a[i],l=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(l===o||l===c))return t()}var m=document.getElementsByTagName("style");for(i=0;i<m.length;i++){s=m[i],l=s.getAttribute("data-href");if(l===o||l===c)return t()}var d=document.createElement("link");d.rel="stylesheet",d.type="text/css",d.onload=t,d.onerror=function(t){var o=t&&t.target&&t.target.src||c,a=new Error("Loading CSS chunk "+e+" failed.\n("+o+")");a.code="CSS_CHUNK_LOAD_FAILED",a.request=o,delete r[e],d.parentNode.removeChild(d),n(a)},d.href=c;var f=document.getElementsByTagName("head")[0];f.appendChild(d)})).then((function(){r[e]=0})));var o=c[e];if(0!==o)if(o)t.push(o[2]);else{var a=new Promise((function(t,n){o=c[e]=[t,n]}));t.push(o[2]=a);var s,l=document.createElement("script");l.charset="utf-8",l.timeout=120,u.nc&&l.setAttribute("nonce",u.nc),l.src=i(e);var m=new Error;s=function(t){l.onerror=l.onload=null,clearTimeout(d);var n=c[e];if(0!==n){if(n){var o=t&&("load"===t.type?"missing":t.type),r=t&&t.target&&t.target.src;m.message="Loading chunk "+e+" failed.\n("+o+": "+r+")",m.name="ChunkLoadError",m.type=o,m.request=r,n[1](m)}c[e]=void 0}};var d=setTimeout((function(){s({type:"timeout",target:l})}),12e4);l.onerror=l.onload=s,document.head.appendChild(l)}return Promise.all(t)},u.m=e,u.c=o,u.d=function(e,t,n){u.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},u.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},u.t=function(e,t){if(1&t&&(e=u(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)u.d(n,o,function(t){return e[t]}.bind(null,o));return n},u.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return u.d(t,"a",t),t},u.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},u.p="",u.oe=function(e){throw console.error(e),e};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var m=0;m<s.length;m++)t(s[m]);var d=l;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"09e4":function(e,t,n){"use strict";n.d(t,"a",(function(){return c}));n("ac1f"),n("5319"),n("21ac");var o=n("4360"),r=n("ec6d"),c=function(e,t){if(void 0!==t)switch(e.data){case"对方暂未绑定设备":t.$router.replace("/device/status/notOtherBind");break;case"您暂未绑定设备":t.$router.replace("/device/status/notBind");break;case"对方的设备不在线":t.$router.replace("/device/status/notOtherOnline");break;case"您的设备不在线":t.$router.replace("/device/status/notOnline");break;case"对方还没有开始使用心晓设备尚无报告可看":t.$router.replace("/device/status/notOtherUsed");break;case"您还没有开始使用心晓设备尚无报告可看":t.$router.replace("/device/status/notUsed");break;case"请先注册":t.$router.replace("/user/person/register");break;default:break}switch(e.code){case 210:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 211:console.log("errHandler 211"),o["a"].commit("setToken",""),window.localStorage.removeItem("token"),window.location.reload(),Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 401:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 400:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 412:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 500:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 501:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 510:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 300:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;case 310:Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break;default:null==t?(console.log("null this point"),null==e.data||0===e.data.length?Object(r["a"])({iconShow:!0,type:"cross",msg:e}):Object(r["a"])({iconShow:!0,type:"cross",msg:e.data})):null==e.data||0===e.data.length?Object(r["a"])({iconShow:!0,type:"cross",msg:e}):Object(r["a"])({iconShow:!0,type:"cross",msg:e.data});break}}},"21ac":function(e,t,n){"use strict";n.d(t,"a",(function(){return o})),n.d(t,"c",(function(){return r})),n.d(t,"b",(function(){return c})),n.d(t,"d",(function(){return a}));n("2b0e");var o=function(e){this.$confirm({iconShow:!0,type:"cross",msg:e})},r=function(e){this.$confirm({iconShow:!0,type:"success",msg:e})},c=function(e){this.$confirm({iconShow:!1,type:"cross",msg:e})},a=function(e){this.$confirm({iconShow:!0,type:"cross",msg:e})}},4360:function(e,t,n){"use strict";var o={};n.r(o),n.d(o,"setToken",(function(){return i})),n.d(o,"setContent",(function(){return u})),n.d(o,"setInfo",(function(){return s})),n.d(o,"setuserhistury",(function(){return l})),n.d(o,"setReportId",(function(){return m})),n.d(o,"setDateDetails",(function(){return d})),n.d(o,"setDateDetails2",(function(){return f})),n.d(o,"setUid",(function(){return h})),n.d(o,"setIsConcern",(function(){return p})),n.d(o,"setIsLoading",(function(){return b})),n.d(o,"setShareData",(function(){return x})),n.d(o,"setCurrentDay",(function(){return k})),n.d(o,"setCurrentDayTime",(function(){return g})),n.d(o,"setUserEmail",(function(){return F})),n.d(o,"setCurrentScore",(function(){return v})),n.d(o,"setIsCheck",(function(){return y})),n.d(o,"setIsMonitoring",(function(){return w}));var r={};n.r(r),n.d(r,"setToken",(function(){return C})),n.d(r,"setContent",(function(){return D})),n.d(r,"setInfo",(function(){return S})),n.d(r,"setuserhistury",(function(){return E})),n.d(r,"setReportId",(function(){return B})),n.d(r,"setDateDetails",(function(){return I})),n.d(r,"setDateDetails2",(function(){return O})),n.d(r,"setUid",(function(){return j})),n.d(r,"setIsConcern",(function(){return T})),n.d(r,"setIsLoading",(function(){return A})),n.d(r,"setShareData",(function(){return R})),n.d(r,"setCurrentDay",(function(){return N})),n.d(r,"setCurrentDayTime",(function(){return L})),n.d(r,"setUserEmail",(function(){return P})),n.d(r,"setCurrentScore",(function(){return _})),n.d(r,"setIsCheck",(function(){return $})),n.d(r,"setIsMonitoring",(function(){return M}));var c=n("2b0e"),a=n("2f62"),i=function(e,t){e.token=t},u=function(e,t){e.content=t},s=function(e,t){e.info=t},l=function(e,t){e.userhistury=t},m=function(e,t){e.reportId=t},d=function(e,t){e.dateDetails=t},f=function(e,t){e.dateDetails2=t},h=function(e,t){e.uid=t},p=function(e,t){e.isConcern=t},b=function(e,t){e.isLoading=t},x=function(e,t){e.shareData.id=t.id,e.shareData.shareKey=t.shareKey},k=function(e,t){e.currentDay=t},g=function(e,t){e.currentDayTime=t},F=function(e,t){e.userEmail=t},v=function(e,t){e.currentScore=t},y=function(e,t){e.isCheck=t},w=function(e,t){e.isMonitoring=t},C=function(e,t){var n=e.commit;n("setToken",t)},D=function(e,t){var n=e.commit;n("setContent",t)},S=function(e,t){var n=e.commit;n("setInfo",t)},E=function(e,t){var n=e.commit;n("setuserhistury",t)},B=function(e,t){var n=e.commit;n("setReportId",t)},I=function(e,t){var n=e.commit;n("setDateDetails",t)},O=function(e,t){var n=e.commit;n("setDateDetails2",t)},j=function(e,t){var n=e.commit;n("setUid",t)},T=function(e,t){var n=e.commit;n("setIsConcern",t)},A=function(e,t){var n=e.commit;n("setIsLoading",t)},R=function(e,t){var n=e.commit;n("setShareData",t)},N=function(e,t){var n=e.commit;n("setCurrentDay",t)},L=function(e,t){var n=e.commit;n("setCurrentDayTime",t)},P=function(e,t){var n=e.commit;n("setUserEmail",t)},_=function(e,t){var n=e.commit;n("setCurrentScore",t)},$=function(e,t){var n=e.commit;n("setIsCheck",t)},M=function(e,t){var n=e.commit;n("setIsMonitoring",t)},q=n("94d5");c["a"].use(a["a"]);var H={organizationId:1,appId:"wx4fff3cfe5325d612",token:"",content:"",info:{},userhistury:"",reportId:"",isConcern:!1,uid:"",trendColor:{totalCardiacEnergy:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],sympatheticNerveTension:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],vagusNerveTension:[{min:0,max:300,color:"#FE552E"},{min:300,max:600,color:"#FFC10E"},{min:600,max:1800,color:"#4A7DD8"},{min:1800,max:3e3,color:"#FFC10E"},{min:3e3,max:999999999,color:"#FE552E"}],autonomicNerveAbility:[{min:0,max:.4,color:"#FE552E"},{min:.4,max:.7,color:"#FFC10E"},{min:.7,max:1.5,color:"#4A7DD8"},{min:1.5,max:2.5,color:"#FFC10E"},{min:2.5,max:999999999,color:"#FE552E"}],heartRateDecelerationForce:[{min:0,max:2.5,color:"#FE552E"},{min:2.5,max:4.6,color:"#FFC10E"},{min:4.6,max:15,color:"#4A7DD8"},{min:15,max:20,color:"#FFC10E"},{min:20,max:999999999,color:"#FE552E"}],baseHeartRate:[{min:0,max:43,color:"#FE552E"},{min:43,max:50,color:"#FFC10E"},{min:50,max:74,color:"#4A7DD8"},{min:74,max:83,color:"#FFC10E"},{min:83,max:999999999,color:"#FE552E"}],baseBreatheRate:[{min:0,max:9,color:"#FE552E"},{min:9,max:12,color:"#FFC10E"},{min:12,max:18,color:"#4A7DD8"},{min:18,max:21,color:"#FFC10E"},{min:21,max:999999999,color:"#FE552E"}],breathePause:[{min:0,max:5,color:"#4A7DD8"},{min:5,max:15,color:"#FFC10E"},{min:15,max:30,color:"#FE552E"},{min:30,max:999999999,color:"#FE552E"}],arrhythmiaRisk:[{min:0,max:9,color:"#FE552E"},{min:9,max:12,color:"#FFC10E"},{min:12,max:18,color:"#4A7DD8"},{min:18,max:21,color:"#FFC10E"},{min:21,max:999999999,color:"#FE552E"}]},colors:{hypertension:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"需关注",color:"#FFC066"},{min:0,max:30,text:"密切关注",color:"#F68989"}],autonomicNerveAbility:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],sympatheticNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]},hyperglycemia:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"需关注",color:"#FFC066"},{min:0,max:30,text:"密切关注",color:"#F68989"}],autonomicNerveDamage:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],vagusNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]},risk:{arrhythmiaRisk:[{min:0,max:30,text:"高风险",color:"#F68989"},{min:30,max:50,text:"中风险",color:"#FFC066"},{min:50,max:180,text:"低风险",color:"#9BD0FF"}],asystoleRisk:[{min:0,max:180,text:"低风险",color:"#9BD0FF"},{min:180,max:220,text:"中风险",color:"#FFC066"},{min:220,max:270,text:"高风险",color:"#F68989"}],coronaryHeartRisk:[{min:0,max:25,text:"高风险",color:"#F68989"},{min:25,max:50,text:"中风险",color:"#FFC066"},{min:50,max:100,text:"低风险",color:"#9BD0FF"}],infarctionRisk:[{min:0,max:15,text:"高风险",color:"#F68989"},{min:15,max:25,text:"中风险",color:"#FFC066"},{min:25,max:100,text:"低风险",color:"#9BD0FF"}],heartRateDecelerationForce:[{min:0,max:2.5,text:"过低",color:"#F68989"},{min:2.5,max:4.6,text:"偏低",color:"#FFC066"},{min:4.6,max:15,text:"正常",color:"#9BD0FF"},{min:15,max:20,text:"偏高",color:"#FFC066"},{min:20,max:30,text:"过高",color:"#F68989"}]},heart:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],currentBaseHeartRate:[{min:0,max:43,text:"过低",color:"#F68989"},{min:43,max:55,text:"偏低",color:"#FFC066"},{min:55,max:74,text:"正常",color:"#9BD0FF"},{min:74,max:83,text:"偏高",color:"#FFC066"},{min:83,max:120,text:"过高",color:"#F68989"}],averageBaseHeartRate:[{min:0,max:43,text:"过低",color:"#F68989"},{min:43,max:55,text:"偏低",color:"#FFC066"},{min:55,max:74,text:"正常",color:"#9BD0FF"},{min:74,max:83,text:"偏高",color:"#FFC066"},{min:83,max:120,text:"过高",color:"#F68989"}],tooFastDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}],tooSlowDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}]},breathe:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],breathePause:[{min:0,max:5,text:"正常",color:"#B8A4FF"},{min:5,max:15,text:"低风险",color:"#9BD0FF"},{min:15,max:30,text:"中风险",color:"#FFC066"},{min:30,max:60,text:"高风险",color:"#F68989"}],currentBaseBreatheRate:[{min:0,max:9,text:"过低",color:"#F68989"},{min:9,max:12,text:"偏低",color:"#FFC066"},{min:12,max:18,text:"正常",color:"#9BD0FF"},{min:18,max:21,text:"偏高",color:"#FFC066"},{min:21,max:25,text:"过高",color:"#F68989"}],averageBaseBreatheRate:[{min:0,max:9,text:"过低",color:"#F68989"},{min:9,max:12,text:"偏低",color:"#FFC066"},{min:12,max:18,text:"正常",color:"#9BD0FF"},{min:18,max:21,text:"偏高",color:"#FFC066"},{min:21,max:25,text:"过高",color:"#F68989"}],tooFastDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}],tooSlowDuration:[{min:0,max:10,text:"尚可",color:"#B8A4FF"},{min:10,max:30,text:"低风险",color:"#9BD0FF"},{min:30,max:60,text:"中风险",color:"#FFC066"},{min:60,max:90,text:"高风险",color:"#F68989"}]},sleep:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}]},heartRateVariability:{severity:[{min:60,max:100,text:"正常",color:"#9BD0FF"},{min:30,max:60,text:"异常",color:"#FFC066"},{min:0,max:30,text:"严重异常",color:"#F68989"}],autonomicNerveAbility:[{min:0,max:.4,text:"过低",color:"#F68989"},{min:.4,max:.7,text:"偏低",color:"#FFC066"},{min:.7,max:1.5,text:"正常",color:"#9BD0FF"},{min:1.5,max:2.5,text:"偏高",color:"#FFC066"},{min:2.5,max:4,text:"过高",color:"#F68989"}],sympatheticNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}],vagusNerveTension:[{min:0,max:300,text:"过低",color:"#F68989"},{min:300,max:600,text:"偏低",color:"#FFC066"},{min:600,max:1800,text:"正常",color:"#9BD0FF"},{min:1800,max:3e3,text:"偏高",color:"#FFC066"},{min:3e3,max:6e3,text:"过高",color:"#F68989"}]}},isLoading:!1,shareData:{shareKey:"",id:""},currentDay:"",currentDayTime:"",userEmail:"",currentScore:"",dateDetails:[],dateDetails2:[],isCheck:!1,isMonitoring:!1},U=new a["a"].Store({state:H,getters:q,actions:r,mutations:o});t["a"]=U},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("2b0e"),r=(n("5cfb"),n("b970")),c=(n("157a"),n("8ac2"),n("d940"),n("8b06")),a=n.n(c),i=(n("1157"),n("5530")),u=n("a925"),s=n("3c69"),l=n("91f4"),m=n.n(l),d=n("b459"),f=n.n(d),h={app:{user:"User",Record:"Record",Report:"Report"},user:{weight:"Weight",height:"Height",age:"Age",peopleInBed:"People in bed"}},p={app:{user:"用户"}};o["a"].use(u["a"]);var b={en:Object(i["a"])(Object(i["a"])({},m.a),h),zh:Object(i["a"])(Object(i["a"])({},f.a),p)},x=new u["a"]({locale:"en",messages:b});function k(e){"en"===e?s["a"].use(e,m.a):"zh"===e&&s["a"].use(e,f.a)}var g=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("keep-alive",[e.$route.meta.keepAlive?n("router-view"):e._e()],1),e.$route.meta.keepAlive?e._e():n("router-view"),n("van-dialog",{attrs:{title:"Tips","show-cancel-button":!1,"show-confirm-button":!1,width:"90%"},model:{value:e.tipDialog,callback:function(t){e.tipDialog=t},expression:"tipDialog"}},[n("div",{staticClass:"tip-top"},[n("p",{staticClass:"tip-content"},[e._v("Your friend "),n("span",{staticClass:"name"},[e._v(e._s(e.nickname))]),e._v(" want to follow you, after that he will recieve your report.")]),n("div",{staticClass:"tip-info"},[n("p",{staticClass:"info"},[e._v(e._s(e.remark))])])]),n("div",{staticClass:"tip-btn"},[n("van-row",{attrs:{type:"flex",justify:"space-around"}},[n("van-col",{attrs:{span:"8"}},[n("div",{staticClass:"agree-btn",on:{click:function(t){return e.agreeClick(!1)}}},[e._v(" Disagree ")])]),n("van-col",{attrs:{span:"8"}},[n("div",{staticClass:"disagree-btn",on:{click:function(t){return e.agreeClick(!0)}}},[e._v(" Agree")])])],1)],1)])],1)},F=[],v=(n("caad"),n("d3b7"),n("ac1f"),n("2532"),n("1276"),n("4328")),y=n.n(v),w=n("4360"),C=n("2f62"),D=n("995e"),S=n("7cb4"),E=n("09e4"),B=n("d399");o["a"].use(B["a"]);n("18a0");var I={name:"App",data:function(){return{tipDialog:!1,websock:null,nickname:"",requestCode:"",remark:"",isPending:!1,tip:"Michelle \n rejected your invitation",icon:""}},watch:{token:function(e){if(e){var t={id:1,body:{token:e},direction:"Request",type:"login"};this.sendSock(t)}}},mounted:function(){var e=this;this.initWebSocket(),window.addEventListener("pagehide",(function(t){e.isMonitoring&&e.asyncClose()}));var t=this.$route.path;window.g.PROPAGATE_URL;t.includes("dictionary")&&window.location.href,t.includes("about")&&window.location.href},computed:Object(i["a"])({},Object(C["c"])(["organizationId","appId","uid","token","isMonitoring"])),methods:{createXHR:function(){if("undefined"!=typeof XMLHttpRequest)return new XMLHttpRequest;if("undefined"!=typeof ActiveXObject){if("string"!=typeof arguments.callee.activeXString){var e,t,n=["MSXML2.XMLHttp.6.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp"];for(e=0,t=n.length;e<t;e++)try{new ActiveXObject(n[e]),arguments.callee.activeXString=n[e];break}catch(o){}}return new ActiveXObject(arguments.callee.activeXString)}throw new Error("No XHR object available.")},asyncClose:function(){var e=this.createXHR();e.onreadystatechange=function(){4==e.readyState&&(e.status>=200&&e.status<300||304==e.status?console.log(e.responseText):console.log("Request was unsuccessful: "+e.status))},e.open("post",D["e"],!1),e.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");var t={"#TOKEN#":this.token};this.uid&&(t["uid"]=this.uid),e.send(y.a.stringify(t))},agreeClick:function(e){var t=this;if(this.tipDialog=!1,!this.isPending){this.isPending=!0;var n={url:D["u"],data:{requestCode:this.requestCode,isAccept:e}};Object(S["a"])(n).then((function(n){200===n.data.code?e&&t.$router.push("/user"):Object(E["a"])(n.data)})).catch((function(e){console.log(e)})).finally((function(){t.isPending=!1}))}},initWebSocket:function(){var e=this,t="ws://39.108.84.118:8097/heart_wechat/heartServer.ws";this.websock=new WebSocket(t),this.websock.onmessage=function(t){e.websocketonmessage(t)},this.websock.onclose=function(t){e.websocketclose(t)},this.websock.onopen=function(){e.websocketOpen()},this.websock.onerror=function(){console.log("WebSocket connect error.")}},sendSock:function(e){this.websock.readyState===this.websock.OPEN?this.websocketsend(e):this.websock.readyState===this.websock.CONNECTING?setTimeout((function(){this.sendSock(e),console.log("WebSocket connecting")}),1e3):console.log("WebSocket not connected")},websocketonmessage:function(e){console.log("数据接收");var t=JSON.parse(e.data);if(console.log("res="+JSON.stringify(t)),"invitation"===t.type)this.nickname=t.body.nickname,t.body.remark?this.remark=t.body.remark:this.remark="Hello my friend.I want to follow you to know your body information.",this.requestCode=t.body.requestCode,this.tipDialog=!0;else if("follow"===t.type){t.body.accept?this.tip=t.body.nickname+" \n accepted your invitation":this.tip=t.body.nickname+" \n rejected your invitation";var n=t.body.headImageUrl;if(null!==n){var o=n.split("/");this.getResource(o[o.length-1])}else Object(B["a"])({message:this.tip,icon:"",className:"user-tip",position:"bottom"});this.$router.push("/user")}},websocketsend:function(e){this.websock.send(JSON.stringify(e))},websocketclose:function(e){var t=this;console.log("connection closed ("+e.code+")");var n=w["a"].state.token;n&&setTimeout((function(){t.initWebSocket()}),5e3)},websocketOpen:function(){console.log("连接成功");var e=w["a"].state.token;if(e){var t={id:1,body:{token:e},direction:"Request",type:"login"};this.sendSock(t)}},getResource:function(e){var t=this;if(console.log("getResource ",this.isPending),!this.isPending){this.isPending=!0;var n={url:D["r"],data:{name:e,type:1}};Object(S["a"])(n).then((function(e){t.isPending=!1,t.icon="data:image/jpeg;base64,"+e.data,Object(B["a"])({message:t.tip,icon:t.icon,className:"user-tip",position:"bottom"})})).catch((function(e){console.log(e)})).finally((function(){t.isPending=!1}))}}}},O=I,j=(n("7c55"),n("0c7c")),T=Object(j["a"])(O,g,F,!1,null,null,null),A=T.exports,R=(n("99af"),n("c975"),n("25f0"),n("5319"),n("841c"),n("8c4f")),N=n("21ac"),L=function(){return n.e("chunk-2c5ad1b6").then(n.bind(null,"37fe"))},P=function(){return n.e("chunk-553c85ec").then(n.bind(null,"f456"))},_=function(){return n.e("chunk-ea2eb0ba").then(n.bind(null,"1297"))},$=function(){return n.e("chunk-57cbf732").then(n.bind(null,"9075"))},M=function(){return n.e("chunk-7adccdc4").then(n.bind(null,"5f0f"))},q=function(){return n.e("chunk-1ca133e8").then(n.bind(null,"943f"))},H=function(){return n.e("chunk-2fe37ba1").then(n.bind(null,"ebce"))},U=function(){return n.e("chunk-ea6af63a").then(n.bind(null,"ea3c"))},X=function(){return n.e("chunk-56b8875c").then(n.bind(null,"7720"))},J=function(){return n.e("chunk-ce8379e4").then(n.bind(null,"f34b"))},K=function(){return n.e("chunk-5003a44f").then(n.bind(null,"7545"))},W=function(){return n.e("chunk-40fdc8e0").then(n.bind(null,"8b2b"))},z=function(){return n.e("chunk-17d99d86").then(n.bind(null,"451a"))},G=function(){return n.e("chunk-27c2ddb8").then(n.bind(null,"f429"))},V=function(){return n.e("chunk-080f9df9").then(n.bind(null,"4300"))},Q=function(){return n.e("chunk-490e41db").then(n.bind(null,"849d"))},Y=function(){return n.e("chunk-261ccaf6").then(n.bind(null,"9f8a"))},Z=function(){return n.e("chunk-0c40879e").then(n.bind(null,"c8da"))},ee=function(){return n.e("chunk-0ce56026").then(n.bind(null,"f634"))},te=function(){return n.e("chunk-7e2097d8").then(n.bind(null,"0e4b"))},ne=function(){return n.e("chunk-4900818a").then(n.bind(null,"8cdd"))},oe=function(){return n.e("chunk-1beafda9").then(n.bind(null,"363f"))},re=function(){return n.e("chunk-4cef3252").then(n.bind(null,"86a8"))},ce=function(){return n.e("chunk-61ec6db1").then(n.bind(null,"2da4"))},ae=function(){return n.e("chunk-ddc73884").then(n.bind(null,"6712"))},ie=R["a"].prototype.push;R["a"].prototype.push=function(e){return ie.call(this,e).catch((function(e){return e}))},o["a"].use(R["a"]);var ue=new R["a"]({mode:"hash",base:"",routes:[{path:"",redirect:"/jimihome"},{path:"/login",name:"login",meta:{back:"exit"},component:M},{path:"/personalInformation",name:"personalInformation",meta:{back:"goBack"},component:K},{path:"/binding",name:"binding",meta:{back:"goBack"},component:Y},{path:"/scanCodeBinding",name:"scanCodeBinding",meta:{back:"goBack"},component:Z},{path:"/setting",name:"setting",meta:{back:"goBack"},component:Q},{path:"/deviceInformation",name:"deviceInformation",meta:{back:"goBack"},component:ee},{path:"/help",name:"help",meta:{back:"goBack"},component:te},{path:"/about",name:"about",meta:{back:"goBack"},component:ne},{path:"/update",name:"update",meta:{back:"goBack"},component:oe},{path:"/installation",name:"installation",meta:{back:"goBack"},component:re},{path:"/tutorial",name:"tutorial",meta:{back:"goBack"},component:ce},{path:"/jimihome",name:"jimihome",component:L,redirect:"/report",children:[{path:"/record",name:"record",component:P,meta:{tabflag:"/record",back:"exit"}},{path:"/trend",name:"trend",component:J,meta:{tabflag:"/record",back:"goBack"}},{path:"/report",name:"report",component:_,meta:{keepAlive:!1,isBack:!1,tabflag:"/report",back:"exit"}},{path:"/user",name:"user",component:$,meta:{tabflag:"/user",back:"exit"}},{path:"/myCode",name:"myCode",component:q,meta:{tabflag:"/user",back:"goBack"}},{path:"/scanToFollow",name:"scanToFollow",component:ae,meta:{tabflag:"/user",back:"goBack"}},{path:"/scanCode",name:"scanCode",component:H,meta:{tabflag:"/user",back:"goBack"}},{path:"/userInfo",name:"userInfo",component:U,meta:{tabflag:"/user",back:"goBack"}},{path:"/heartRateVariability",name:"heartRateVariability",component:W,meta:{tabflag:"/report",back:"goBack"}},{path:"/healthBriefing",name:"healthBriefing",component:z,meta:{tabflag:"/report",back:"goBack"}},{path:"/monitor",name:"monitor",component:G,meta:{tabflag:"/report",back:"goBack"}},{path:"/healthEvaluation",name:"healthEvaluation",component:V,meta:{tabflag:"/report",back:"goBack"}}]},{path:"/attention",name:"attention",component:X,meta:{keepAlive:!0,isBack:!1,back:"goBack"}}]});function se(e,t){w["a"].commit("setIsLoading",!0),console.log("getLevel");var n={url:D["o"],data:{}},o=this;Object(S["a"])(n).then((function(n){if(200===n.data.code){var r=n.data.data;w["a"].commit("setIsLoading",!1),le(r,e,t)}else Object(E["a"])(n.data,o),w["a"].commit("setIsLoading",!1)})).catch((function(e){console.log("abc",e),w["a"].commit("setIsLoading",!1),null!=e.response||"Network Error"!==e.toString().substr(7,13)?e.response&&500===e.response.status&&(console.log("abcd",e),Object(E["a"])("network error!",o)):Object(E["a"])("network error!",o)}))}function le(e,t,n){var o="";switch(0!==e&&w["a"].commit("setIsConcern",!1),e){case 0:break;case 1:"/login"!==t&&(o="/login");break;case 2:"/personalInformation"!==t&&(o="/personalInformation");break;case 3:"/monitor"===t&&""==w["a"].getters.uid&&(Object(E["a"])("Please bind the device first."),o="/report");break;case 4:break;default:break}""===o?n():n({path:o})}window.localStorage.getItem("token")&&w["a"].commit("setToken",window.localStorage.getItem("token")),window.localStorage.getItem("reportId")&&w["a"].commit("setReportId",JSON.parse(window.localStorage.getItem("reportId"))),window.localStorage.getItem("userhistury")&&w["a"].commit("setuserhistury",window.localStorage.getItem("userhistury")),ue.beforeEach((function(e,t,n){var o=window.location.href;if(o.includes("code")){var r=o.split("code")[1];if(r.includes("#")){var c=r.split("#");o=o.split("?")[0]+"#"+c[1]}}var a=w["a"].state.token;if(e.fullPath.includes("shareKey")?n():a?e.path.includes("dictionary")||e.path.includes("about")||e.path.includes("concernTip")||e.path.includes("passive")||e.path.includes("propagate")||e.path.includes("home")&&t.path.includes("home")?n():se(e.path,n):"/login"!==e.path?n({path:"/login"}):n(),t.query.reportId&&!e.query.reportId){var i=JSON.parse(JSON.stringify(e.query));i["reportId"]=t.query.reportId,n({path:e.path,query:i})}})),ue.afterEach((function(e,t,n){window.scrollTo(0,0)}));var me=ue,de=n("ec6d");k(x.locale),o["a"].use(r["a"]),o["a"].use(a.a),o["a"].prototype.$alertError=N["a"],o["a"].prototype.$alertInfo=N["b"],o["a"].prototype.$alertSuccess=N["c"],o["a"].prototype.$alertWarning=N["d"],o["a"].prototype.$confirm=de["a"],o["a"].config.productionTip=!1,new o["a"]({router:me,store:w["a"],i18n:x,render:function(e){return e(A)},mounted:function(){var e=this;document.addEventListener("plusready",(function(t){var n=null;plus.key.addEventListener("backbutton",(function(){window.location.hash;console.log(e.$route);var t=e.$route.meta.back;console.log(t),"exit"===t?n?(new Date).getTime()-n<1e3&&plus.runtime.quit():(n=(new Date).getTime(),plus.nativeUI.toast("Press again to exit the app",{duration:"short"}),setTimeout((function(){n=null}),1e3)):history.go(-1)}),!1)}))}}).$mount("#app")},"7c55":function(e,t,n){"use strict";var o=n("88c8"),r=n.n(o);r.a},"7cb4":function(e,t,n){"use strict";n.d(t,"a",(function(){return s})),n.d(t,"b",(function(){return l})),n.d(t,"c",(function(){return m}));n("d3b7");var o=n("4328"),r=n.n(o),c=n("bc3a"),a=n.n(c),i=n("4360");a.a.interceptors.request.use((function(e){return""!==i["a"].state.token&&(""===e.data?e.data+="#TOKEN#="+i["a"].state.token:e.data+="&#TOKEN#="+i["a"].state.token),e}),(function(e){return Promise.reject(e)}));var u=a.a,s=function(e){return u({method:"post",url:e.url,timeout:12e4,responseType:e.responseType?e.responseType:"",headers:{"Content-type":"application/x-www-form-urlencoded; charset=UTF-8","Accept-Language":"en_US"},data:r.a.stringify(e.data)})},l=function(e){for(var t=[],n=0;n<e.length;n++)t.push(e[n].id+"="+e[n].have);return t.toString()},m=function(e,t){return e.sort((function(e,n){var o=e[t],r=n[t];return"NA"===o||"NA"===r?"NA"===r?-1:"NA"===o?1:0:o>r?-1:o<r?1:0}))}},"88c8":function(e,t,n){},"8ac2":function(e,t,n){},"94d5":function(e,t,n){"use strict";n.r(t),n.d(t,"token",(function(){return o})),n.d(t,"content",(function(){return r})),n.d(t,"info",(function(){return c})),n.d(t,"userhistury",(function(){return a})),n.d(t,"trendColor",(function(){return i})),n.d(t,"colors",(function(){return u})),n.d(t,"reportId",(function(){return s})),n.d(t,"dateDetails",(function(){return l})),n.d(t,"dateDetails2",(function(){return m})),n.d(t,"organizationId",(function(){return d})),n.d(t,"isConcern",(function(){return f})),n.d(t,"appId",(function(){return h})),n.d(t,"uid",(function(){return p})),n.d(t,"isLoading",(function(){return b})),n.d(t,"shareData",(function(){return x})),n.d(t,"currentDay",(function(){return k})),n.d(t,"currentDayTime",(function(){return g})),n.d(t,"userEmail",(function(){return F})),n.d(t,"currentScore",(function(){return v})),n.d(t,"isCheck",(function(){return y})),n.d(t,"isMonitoring",(function(){return w}));var o=function(e){return e.token},r=function(e){return e.content},c=function(e){return e.info},a=function(e){return e.userhistury},i=function(e){return e.trendColor},u=function(e){return e.colors},s=function(e){return e.reportId},l=function(e){return e.dateDetails},m=function(e){return e.dateDetails2},d=function(e){return e.organizationId},f=function(e){return e.isConcern},h=function(e){return e.appId},p=function(e){return e.uid},b=function(e){return e.isLoading},x=function(e){return e.shareData},k=function(e){return e.currentDay},g=function(e){return e.currentDayTime},F=function(e){return e.userEmail},v=function(e){return e.currentScore},y=function(e){return e.isCheck},w=function(e){return e.isMonitoring}},"990b":function(e,t,n){"use strict";var o=n("fb8b"),r=n.n(o);r.a},"995e":function(e,t,n){"use strict";var o;n.d(t,"z",(function(){return r})),n.d(t,"o",(function(){return c})),n.d(t,"F",(function(){return a})),n.d(t,"x",(function(){return i})),n.d(t,"a",(function(){return u})),n.d(t,"B",(function(){return s})),n.d(t,"C",(function(){return l})),n.d(t,"e",(function(){return m})),n.d(t,"m",(function(){return d})),n.d(t,"b",(function(){return f})),n.d(t,"J",(function(){return h})),n.d(t,"g",(function(){return p})),n.d(t,"G",(function(){return b})),n.d(t,"K",(function(){return x})),n.d(t,"u",(function(){return k})),n.d(t,"v",(function(){return g})),n.d(t,"I",(function(){return F})),n.d(t,"k",(function(){return v})),n.d(t,"L",(function(){return y})),n.d(t,"j",(function(){return w})),n.d(t,"t",(function(){return C})),n.d(t,"p",(function(){return D})),n.d(t,"H",(function(){return S})),n.d(t,"l",(function(){return E})),n.d(t,"s",(function(){return B})),n.d(t,"y",(function(){return I})),n.d(t,"w",(function(){return O})),n.d(t,"f",(function(){return j})),n.d(t,"r",(function(){return T})),n.d(t,"d",(function(){return A})),n.d(t,"i",(function(){return R})),n.d(t,"h",(function(){return N})),n.d(t,"A",(function(){return L})),n.d(t,"q",(function(){return P})),n.d(t,"n",(function(){return _})),n.d(t,"E",(function(){return $})),n.d(t,"D",(function(){return M})),n.d(t,"c",(function(){return q})),o="http://api.ddnurse.senviv.cn/heart_wechat";var r=o+"/wechat/login",c=o+"/access/getLevel",a=o+"/emergency/remove",i=o+"/emergency/list",u=o+"/emergency/add",s=o+"/device/monitor",l=o+"/device/open",m=o+"/device/close",d=o+"/device/getInfo",f=o+"/device/bind",h=o+"/device/unbind",p=o+"/device/editDeviceName",b=o+"/account/requestFollow",x=o+"/account/unfollow2",k=o+"/account/isAcceptFollow",g=o+"/account/isFollow",F=o+"/account/submitInfo",v=o+"/account/getConcerns2",y=o+"/account/updateRelationship",w=o+"/account/getInfo2",C=o+"/account/getUserInfo",D=o+"/account/getQRCode",S=o+"/account/scanQRCode",E=o+"/report/getContent",B=o+"/report/getTrend",I=o+"/report/listIds2",O=o+"/report/listDays",j=o+"/report/current",T=o+"/resource/get",A=o+"/report/isMyReport",R=o+"/email/register",N=o+"/email/login",L=o+"/email/modifyPassword",P=o+"/consult/getHealthDiaryTrend",_=o+"/consult/getHealthDiary",$=o+"/consult/addHealthDiary",M=o+"/consult/listProblems2",q=o+"/upgrade/getLatestAppUpgradePack"},d940:function(e,t,n){},ec6d:function(e,t,n){"use strict";n("d3b7");var o=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{directives:[{name:"show",rawName:"v-show",value:e.isShow,expression:"isShow"}],staticClass:"tip"},[n("div",{staticClass:"tip-one"},[n("div",{staticClass:"left-out"},[n("div",{directives:[{name:"show",rawName:"v-show",value:e.iconShow,expression:"iconShow"}],staticClass:"left"},[n("van-icon",{attrs:{name:e.text.type,size:24}})],1)]),n("div",{staticClass:"right"},[n("p",{staticClass:"content"},[e._v(e._s(e.text.msg))])])])])},c=[],a={data:function(){return{iconShow:!0,isShow:!0,text:{iconShow:!0,type:"提示",msg:""},btn:{ok:"确定",no:"取消"},timer:""}},mounted:function(){console.log("dialog mounted")},watch:{text:function(e){this.isShow=!0,this.iconShow=e.iconShow,this.timer=setTimeout(this.close,2e3)}},methods:{close:function(){this.isShow=!1},ok:function(){this.isShow=!1}}},i=a,u=(n("990b"),n("0c7c")),s=Object(u["a"])(i,r,c,!1,null,"b4f8f40a",null),l=s.exports,m=o["a"].extend(l),d=function(e){return new Promise((function(t,n){var o=new m({el:document.createElement("div")});document.body.appendChild(o.$el),o.text=e,console.log("confirmDom",o.text),o.ok=function(){t(),o.isShow=!1},o.close=function(){n(),o.isShow=!1}})).catch((function(e){}))};t["a"]=d},fb8b:function(e,t,n){}});