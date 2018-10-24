package com.story.utils;


import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/10/18
 */
public class Excel1Util {

    /**
     * 将实体类的信息写入Excel文件
     *  
     *
     * @param fileName                excel文件名称 如：文件1.excel
     * @param list                    实体类集合
     * @param titles                  excel标题名称
     * @param columnLength            标题名称宽度
     * @param fileds                  对应标题所填充的实体类信息（属性名）
     * @throws IOException
     * @throws WriteException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> void writeExcel(String fileName, List<T> list,
                                      String[] titles, int[] columnLength, String[] fileds) {
        try {
// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
            File file = createFile(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            if (wwb != null) {
    // 创建一个可写入的工作表
    // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
                WritableSheet ws = wwb.createSheet("sheet1", 0);
                /*
                 * 表头单元格样式的设定 WritableFont.createFont("宋体")：设置字体为宋体 12：设置字体大小
                 * WritableFont.BOLD:设置字体加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
                 * UnderlineStyle.NO_UNDERLINE：没有下划线 Colour.BLACK 字体颜色 黑色
                 */
                WritableFont titleFont = new WritableFont(
                        WritableFont.createFont("宋体"), 12, WritableFont.BOLD,
                        false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat titleCellFormat = new WritableCellFormat(
                        titleFont);
    // 字休居中
                titleCellFormat.setAlignment(Alignment.CENTRE);
    // 设置单元格背景色：表体为白色
                titleCellFormat.setBackground(Colour.WHITE);
    // 整个表格线为细线、黑色
                titleCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
                        Colour.BLACK);
                WritableFont contentFont = new WritableFont(
                        WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD,
                        false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat contentCellFormat = new WritableCellFormat(
                        contentFont);
    // 字休居中
                contentCellFormat.setAlignment(Alignment.CENTRE);
    // 设置单元格背景色：表体为白色
                contentCellFormat.setBackground(Colour.WHITE);
    // 整个表格线为细线、黑色
                contentCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
                        Colour.BLACK);
                for (int i = 0; i < titles.length; i++) {
                    ws.setColumnView(i, columnLength[i]); // 设置列的宽度
                    Label label = new Label(i, 0, titles[i], titleCellFormat);
                    ws.addCell(label);
                }
    // 填充实体类的基本信息
                for (int j = 0; list != null && !list.isEmpty() && j < list.size(); j++) {
                    T t = list.get(j);
                    Class clazz = t.getClass();
                    String[] contents = new String[fileds.length];
                    for (int i = 0; fileds != null && i < fileds.length; i++) {
                        String filedName = toUpperCaseFirstOne(fileds[i]);
                        Method method = clazz.getMethod(filedName);
                        method.setAccessible(true);
                        Object obj = method.invoke(t);
                        String str = String.valueOf(obj);
                        if (str == null || str.equals("null"))
                            str = "";
                        contents[i] = str;
                    }
                    for (int n = 0; n < contents.length; n++) {
    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                        Label labelC = new Label(n, j + 1, contents[n],
                                contentCellFormat);
    // 将生成的单元格添加到工作表中
                        ws.addCell(labelC);
                    }
                }
    // 从内存中写入文件中
                wwb.write();
    // 关闭资源，释放内存
                wwb.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static File createFile(String path, String filename) {
        File file = new File(path + "/" + filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 将第一个字母转换为大写字母并和get拼合成方法
     *  
     *
     * @param origin
     * @return
     */
    private static String toUpperCaseFirstOne(String origin) {
        StringBuffer sb = new StringBuffer(origin);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");
        return sb.toString();
    }


//    public static void main(String[] args) throws WriteException,
//            NoSuchMethodException, SecurityException, IllegalAccessException,
//            IllegalArgumentException, InvocationTargetException, IOException {
//        List<User> list = new ArrayList<User>();// 获取数据列表
//        User oneUser = new User();
//        oneUser.setName("测试");
//
//
//        list.add(oneUser);
//
//
//        String[] titles = new String[] { "姓名" };// 设置列中文名
//        int columnLength[] = { 10, 10, 10 };// 设置列宽
//        String fileds[] = new String[] { "name" };// 设置列英文名
//        ExcelUtils.writeExcel("D:\\ceshi.xls", list, titles, columnLength,
//                fileds);
//
//
//    }

}
