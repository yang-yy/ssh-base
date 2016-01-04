package com.microsoft.msdn.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 导出Excel方法
 * param
 * array  列名数组
 * resultArray   数据集
 * xslName 文件名称
 *
 * @author Administrator
 */
public class ExportExcel {
    public static void export(HttpServletResponse response, HttpServletRequest request, String[] array, List<List<String>> resultArray, String xslName) throws IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");


        String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "excels";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = System.currentTimeMillis() + "_" + StringUtils.trimToEmpty(xslName) + ".xls";

        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow row = sheet.createRow((short) 0);
        for (int i = 0; i < array.length; i++) {
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(array[i]);
        }

        int index = 0;
        int cellIndex = 0;
        for (List<String> rowsData : resultArray) {
            HSSFRow rowTmp = sheet.createRow(++index);
            cellIndex = 0;
            for (String singleRow : rowsData)
                rowTmp.createCell(cellIndex++).setCellValue(singleRow);
        }
        FileOutputStream out = null;
        OutputStream outS = null;
        try {
            out = new FileOutputStream(path + File.separator + fileName);
            outS = response.getOutputStream();
            workbook.write(out);
            workbook.write(outS);
            outS.flush();
        } finally {
            if (outS != null) outS.close();
            if (out != null) out.close();
        }
        System.out.println("文件生成...");
    }
}

