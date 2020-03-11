package www.mwj;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author MWJ 2020/3/8
 */
public class WriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    List<AliInfo> aliInfos;
    List<HuaweiInfo> huaweiInfos;

    public WriteExcel(List<AliInfo> aliInfos, List<HuaweiInfo> huaweiInfos) {
        this.aliInfos = aliInfos;
        this.huaweiInfos = huaweiInfos;
    }

    public void writeAliExcel(String aliPath) {
        OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(aliPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(aliPath);
            workBook.write(out);
            Field[] fields = AliInfo.class.getDeclaredFields();
            // 获取总列数
            int columnNumCount = fields.length - 1;
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < aliInfos.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录

                AliInfo aliInfo = aliInfos.get(j);
                for (int k = 0; k <= columnNumCount; k++) {
                    Field f = fields[k];
                    f.setAccessible(true);
                    Cell cell = row.createCell(k);
                    cell.setCellValue(String.valueOf(f.get(aliInfo)));
                    if (aliInfo.getChange()) {
                        //设置样式-颜色
                        CellStyle style = workBook.createCellStyle();
                        //设置填充方案
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        //设置自定义填充颜色
                        style.setFillForegroundColor(IndexedColors.RED.getIndex());
                        cell.setCellStyle(style);
                    }
                }

            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(aliPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource(out);
        }
        System.out.println("阿里反馈表导出成功");
    }

    private void releaseResource(OutputStream out) {
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHuaweiExcel(String huaweiPath) {
        OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(huaweiPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(huaweiPath);
            workBook.write(out);
            Field[] fields = HuaweiInfo.class.getDeclaredFields();
            // 获取总列数
            int columnNumCount = fields.length - 1;
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < huaweiInfos.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录

                HuaweiInfo huaweiInfo = huaweiInfos.get(j);
                for (int k = 0; k <= columnNumCount; k++) {
                    Field f = fields[k];
                    f.setAccessible(true);
                    Cell cell = row.createCell(k);
                    cell.setCellValue(String.valueOf(f.get(huaweiInfo)));
                    if (huaweiInfo.getChange()) {
                        //设置样式-颜色
                        CellStyle style = workBook.createCellStyle();
                        //设置填充方案
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        //设置自定义填充颜色
                        style.setFillForegroundColor(IndexedColors.RED.getIndex());
                        cell.setCellStyle(style);
                    }
                }

            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(huaweiPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResource(out);
        }
        System.out.println("华为更新表数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
