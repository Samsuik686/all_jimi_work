import Vue from 'vue'

function alertMsg(title, message, type) {
  new Vue().$notify({
    title: title,
    message: message,
    duration: 3000,
    dangerouslyUseHTMLString: true,
    position: 'bottom-right',
    showClose: false,
    type: type
  });
}


export const alertError = function (string) {
  alertMsg("错误", string, 'error');
};

export const alertSuccess = function (string) {
  alertMsg("成功", string, "success");
};

export const alertInfo = function (string) {
  alertMsg("提示", string, "info");
};

export const alertWarning = function (string) {
  let showArr = string.split('\n').filter(val => val !== '');
  let str = '';
  if (showArr.length > 1) {
    str = '<p style="max-height:500px;overflow-y:auto;padding-right:20px;">'+ showArr[0] + '<br/>';
    for (let i = 1; i < showArr.length; i++) {
      str = str + i + '、 ' + showArr[i] + '<br/><br/>';
    }
    str = str + '</p>';
  } else {
    str = string
  }
  alertMsg("警告", str, "warning");
};
