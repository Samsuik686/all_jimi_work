let url;
let url2;
if (process.env.NODE_ENV === 'production') {
  url = window.g.API_URL
  url2 = window.g.API_URL2
} else {
  url = window.g.LOCAL_URL
  url2 = window.g.LOCAL_URL2
}

/*line*/
export const lineListUrl = url+'/line/selectAll';

/*user*/
let user = url + '/user';
export const loginUrl = user + '/login';
export const addUserUrl = user + '/add';
export const updateUserUrl = user + '/update';
export const userListUrl = user + '/list';

/*program*/
let program = url + '/program';
export const programListUrl = program + '/list';
export const programFileUploadUrl = program +'/upload';
export const programStartUrl = program +'/start';
export const programFinishUrl = program + '/finish';
export const programCancelUrl = program + '/cancel';
export const programDownloadUrl = program + '/download';
/*programItem*/
export const programItemListUrl = program + '/listItem';
export const updateProgramItemUrl = program +'/updateItem';

/*config*/
let config = url + '/config';
export const configListUrl = config +'/list';
export const setConfigInfoUrl = config + '/set';

/*material*/
let material = url + '/material';
export const addMaterialUrl = material + '/add';
export const updateMaterialUrl = material + '/update';
export const deleteMaterialUrl = material + '/delete';
export const materialListUrl = material + '/list';

/*operation*/
let operation = url + '/operation';
/*client*/
export const clientReportListUrl = operation + '/listClientReport';
export const downloadClientReportUrl = operation + '/downloadClientReport';
/*operation*/
export const operationReportListUrl = operation + '/listOperationReport';
export const operationReportSummaryListUrl = operation + '/listOperationReportSummary';
export const downloadOperationReportUrl = operation + '/downloadOperationReport';
/*stock*/
export const stockLogsListUrl = operation + '/listStockLogs';

/*display*/
export const getAllStatusDetails = url + '/getStatusDetailsByDay';
//export const getLineData = url + '/getStatusDetails';
export const getStatusDetails = url + '/getStatusDetails';
//smt_kanban
export const smtepsuploadExcel = url2 + '/config/epsuploadExcel'
export const smtepsupdateState = url2 + '/config/epsupdateState';

