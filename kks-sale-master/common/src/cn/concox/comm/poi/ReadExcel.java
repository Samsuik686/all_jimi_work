/*
 * Created: 2016-7-18
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.comm.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * ReadExcel
 * 
 * @author Li.Shangzhi
 * @date 2016年7月18日
 */
public class ReadExcel {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcel.class);

    /**
     * 详述: 读取2010及以上excel文档 <br/>
     * 创建时间：2016年7月18日
     * 
     * @param path
     *            文件路径
     * @return Object[] 三维数组
     * @throws IOException
     */
    public static Object[] readXlsx(String path) throws IOException {
        return readXlsx(path, null);
    }

    /**
     * 详述: 读取2010及以上excel文档 <br/>
     * 创建时间：2016年7月18日
     * 
     * @param path
     *            文件路径
     * @param startLine
     *            从第几行开始读取，null表示从第0行开始读取
     * @return Object[] 三维数组
     * @throws IOException
     */
    public static Object[] readXlsx(String path, Integer startLine) throws IOException {
        if (startLine == null) {
            startLine = 0;
        }
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Object[] sheel = new Object[xssfWorkbook.getNumberOfSheets()];

        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null || xssfSheet.getLastRowNum() == 0) {
                continue;
            }
            // xssfSheet.getLastRowNum() 获取最后一行的索引
            Object[] rows = new Object[xssfSheet.getLastRowNum() + 1 - startLine];
            // Read the Row
            for (int rowNum = startLine; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    Object[] cols = new Object[xssfRow.getLastCellNum()];
                    for (int i = 0; i <= xssfRow.getLastCellNum() - 1; i++) {
                        cols[i] = getValue(xssfRow.getCell(i));
                        if (cols[i] == null) {// 列值为空
                            cols[i] = "";
                        }
                    }
                    rows[rowNum - startLine] = cols;
                }
            }
            sheel[numSheet] = rows;
        }
        return sheel;
    }

    /**
     * 详述: 读取2010及以上excel文档 <br/>
     * 创建时间：2016年7月18日
     * 
     * @param is
     *            输入流
     * @return Object[] 三维数组
     * @throws IOException
     */
    public static Object[] readXlsx(InputStream is) throws IOException {
        return readXlsx(is, null);
    }

    /**
     * 详述: 读取2010及以上excel文档 <br/>
     * 创建时间：2016年7月18日
     * 
     * @param is
     *            输入流
     * @param startLine
     *            从第几行开始读取，null表示从第0行开始读取
     * @return Object[] 三维数组
     * @throws IOException
     */
    public static Object[] readXlsx(InputStream is, Integer startLine) throws IOException {
        if (startLine == null) {
            startLine = 0;
        }
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Object[] sheel = new Object[xssfWorkbook.getNumberOfSheets()];

        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null || xssfSheet.getLastRowNum() == 0) {
                continue;
            }
            // xssfSheet.getLastRowNum() 获取最后一行的索引
            Object[] rows = new Object[xssfSheet.getLastRowNum() + 1 - startLine];
            // Read the Row
            for (int rowNum = startLine; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                // System.out.println(rowNum);//判断出错行
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null && xssfRow.getLastCellNum() >= 0) {
                    Object[] cols = new Object[xssfRow.getLastCellNum()];
                    for (int i = 0; i <= xssfRow.getLastCellNum() - 1; i++) {
                        cols[i] = getValue(xssfRow.getCell(i));
                        if (cols[i] == null) {// 列值为空
                            cols[i] = "";
                        }
                    }
                    if (validateText(cols)) {
                        rows[rowNum - startLine] = cols;
                    }
                }
            }
            sheel[numSheet] = rows;
        }
        return sheel;
    }

    /**
     * 验证表格当前行所有列为空的情况下，返回false,不是返回true
     * 
     * @param cols
     * @return
     */
    private static boolean validateText(Object[] cols) {
        for (int i = 0; i < cols.length; i++) {
            if (null != cols[i] && !"".equals(cols[i].toString().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 详述: 2003-2007格式xls，也兼容2010，调用2010不建议采用此方法<br/>
     * 创建时间：2016年7月18日
     * 
     * @param path
     *            文件路径
     * @return Object[] 三维数组
     * @throws Exception
     */
    public static Object[] readXls(String path) throws Exception {
        return readXls(path, null);
    }

    public static Object[] readXls(String path, Integer startLine) throws Exception {
        if (startLine == null) {
            startLine = 0;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            Object[] sheel = new Object[hssfWorkbook.getNumberOfSheets()];
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null || hssfSheet.getLastRowNum() == 0) {
                    continue;
                }
                Object[] rows = new Object[hssfSheet.getLastRowNum() + 1 - startLine];
                for (int rowNum = startLine; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Object[] cols = new Object[hssfRow.getPhysicalNumberOfCells()];
                        for (int i = 0; i < hssfRow.getPhysicalNumberOfCells(); i++) {
                            cols[i] = getCellFormatValue(hssfRow.getCell(i));
                            if (cols[i] == null) {// 列值为空
                                cols[i] = "";
                            }
                        }
                        rows[rowNum - startLine] = cols;
                    }
                }
                sheel[numSheet] = rows;
            }
            return sheel;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            return readXlsx(is, startLine);
        }
        return null;
    }

    public static Object[] readXls(MultipartFile file, Integer startLine) throws Exception {
        if (startLine == null) {
            startLine = 0;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            Object[] sheel = new Object[hssfWorkbook.getNumberOfSheets()];
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null || hssfSheet.getLastRowNum() == 0) {
                    continue;
                }
                Object[] rows = new Object[hssfSheet.getLastRowNum() + 1 - startLine];
                for (int rowNum = startLine; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    int lastNum = hssfSheet.getLastRowNum();
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Object[] cols = new Object[hssfRow.getLastCellNum()];
                        for (int i = 0; i <= hssfRow.getLastCellNum() - 1; i++) {
                            cols[i] = getCellFormatValue(hssfRow.getCell(i));
                            if (cols[i] == null) {// 列值为空
                                cols[i] = "";
                            }
                        }
                        rows[rowNum - startLine] = cols;
                    }
                }
                sheel[numSheet] = rows;
            }
            return sheel;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            return readXlsx(file.getInputStream(), startLine);
        }
        return null;
    }

    public static Object[] readXlsForPom(MultipartFile file, Integer startLine) throws Exception {
        if (startLine == null) {
            startLine = 0;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

            Object[] rows = new Object[hssfSheet.getLastRowNum() + 1 - startLine];
            for (int rowNum = startLine; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    if (hssfRow.getLastCellNum() == -1) {
                        break;
                    }
                    Object[] cols = new Object[hssfRow.getLastCellNum()];
                    for (int i = 0; i <= hssfRow.getLastCellNum() - 1; i++) {
                        cols[i] = getCellFormatValue(hssfRow.getCell(i));
                        if (cols[i] == null) {// 列值为空
                            cols[i] = "";
                        }
                    }
                    rows[rowNum - startLine] = cols;
                }
            }

            return rows;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据HSSFCell类型设置数据
     * 
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellvalue = sdf.format(date);
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    // cellvalue = String.valueOf(cell.getNumericCellValue());
                    /*
                     * DecimalFormat df = new DecimalFormat("0"); String whatYourWant =
                     * df.format(cell.getNumericCellValue());
                     */
                    cellvalue = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
                break;
            }
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    private static String getValue(XSSFCell xssfRow) {
        if (null != xssfRow) {
            if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(xssfRow)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = xssfRow.getDateCellValue();
                    return sdf.format(date);
                } else if (xssfRow.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    double value = xssfRow.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    return sdf.format(date);
                } else {
                    double value = xssfRow.getNumericCellValue();
                    XSSFCellStyle style = xssfRow.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#.##");
                    }
                    return format.format(value);
                }

            } else {
                return String.valueOf(xssfRow.getStringCellValue());
            }
        }
        return "";
    }

    public static void main(String[] args) throws Exception {

        Object[] result = ReadExcel.readXlsx("C:\\Users\\Administrator\\Desktop\\测试数据导入模板.xlsx");
        for (int i = 0; i < result.length; i++) {
            Object[] m = (Object[]) result[i];
            if (m != null && m.length > 0) {
                for (int j = 0; j < m.length; j++) { // TODO 行
                    Object[] n = (Object[]) m[j];
                    if (n != null && n.length > 0) {
                        for (int k = 0; k < n.length; k++) { // TODO 列
                            // TODO Mapper.insert();
                            System.out.print(n[k] + " ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}