//扩展easyui表单的验证    
$.extend($.fn.validatebox.defaults.rules, {  
    //验证汉字  
    CHS: {  
        validator: function (value) {  
            return /^[\u0391-\uFFE5]+$/.test(value);  
        },  
        message: 'The input Chinese characters only.'  
    },  
    //移动手机号码验证  
    Mobile: {//value值为文本框中的值  
        validator: function (value) {  
            var reg = /^1\d{10}$/;  
            return reg.test(value);  
        },  
        message: '手机号码格式不正确,以1开头的11位数字'  
    },  
    //国内邮编验证  
    ZipCode: {  
        validator: function (value) {  
            var reg = /^[0-9]\d{5}$/;  
            return reg.test(value);  
        },  
        message: 'The zip code must be 6 digits and 0 began.'  
    },  
  //数字  
    Number: {  
        validator: function (value) {  
            var reg =/^[0-9]*$/;  
            return reg.test(value);  
        },  
        message: '只能填写数字.'  
    },  
    //传真
    Fax: {  
        validator: function (value) {  
            var reg =/^((0\d{2,3}-)\d{7,8})$/;  
            return reg.test(value);  
        },  
        message: '0开头的3到4位 数字，中间横线（-），最后7或8位数字.'  
    },
    //邮箱
    Email: {  
        validator: function (value) {  
            var reg =/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;  
            return reg.test(value);  
        },  
        message: '邮箱格式不正确'  
    },
    
    //金额
    Amount: {  
        validator: function (value) {  
            var reg =/^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;  
            return reg.test(value);  
        },  
        message: '金额格式不正确'  
    }
})  