package com.story.utils;



/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/10/18
 */
public class ExcelUtil {
//    public static String NO_DEFINE = "no_define";//未定义的字段
//    public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式
//    public static int DEFAULT_COLOUMN_WIDTH = 17;
//    public static void exportExcel(){
//        //1.创建一个webbook，对应一个Excel文件
//        HSSFWorkbook wb = new HSSFWorkbook();
//        //2.在建立的工作簿中添加一个sheet，对应Excell文件中的工作簿，并设置工作簿名称
//        HSSFSheet sheet = wb.createSheet("我是工作簿左下角的名字（第一个）");
//        //2.1如果需要多个，则继续创建即可
//        HSSFSheet sheet1 = wb.createSheet("我是工作簿左下角的名字（第二个）");
//
//
//        //3.第一行2,3,4列合并（注意：从0开始开始，参数分别表示起始第2行，结束第4行，起始第2列，结束第4列，大家可以
//        //在脑海中想象画出对应的矩形框）
//        CellRangeAddress region1 = new CellRangeAddress(1, 3, (short) 1, (short) 3);
//        HSSFRow row1 = sheet.createRow(1);
//        //第一个合并单元区域：
//        //3.1为合并单元格赋值，实际就是为坐标（1,1）这个单元格赋值（工作簿中坐标是（2，2)）
//        //经本人测试，只有合并区域左上角那个单元格赋值能成功，左下角、右下角、右上角都不行。
//        //注意：这儿colum必须是 1 ：因为这是代码，是从0开始数的，Excel中对应的是2，以下同理
//        row1.createCell(1).setCellValue("第二至四行2-4列合并");
//        //4.把合并区域添加到工作簿中
//        sheet.addMergedRegion(region1);
//
//        //5.设置合并单元格的边框样式，用RegionUtil这个工具类
//
//        //5.1设置合并单元格的边框是------or.........0r__________等等这样的样式
//        RegionUtil.setBorderBottom(BorderStyle.THIN,region1,sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN,region1,sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN,region1,sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN,region1,sheet);
//
//        //5.2设置合并单元格边框的颜色
//        RegionUtil.setBottomBorderColor(12,region1,sheet);
//        RegionUtil.setLeftBorderColor(12,region1,sheet);
//        RegionUtil.setRightBorderColor(12,region1,sheet);
//        RegionUtil.setTopBorderColor(12,region1,sheet);
//
//        //6.创建单元格（单个cell）样式（为合并的单元格设置样式，设计上就是合并单元格的最左上角
//        //那个单元格设置样式，这儿坐标为（1.1），excel中为（2,2））这同时也是单个单元格的样式设置的代码
//        HSSFCellStyle cellStyle = wb.createCellStyle();
//        // 6.1创建字体样式对象
//        Font fontStyle = wb.createFont();
//        //6.1.1字体加粗
//        fontStyle.setBold(true);
//        //6.1.2字体大小
//        fontStyle.setFontHeightInPoints((short) 12);
//        // 7.将字体样式添加到单元格样式中
//        cellStyle.setFont(fontStyle);
//        // 6.2 单元格样式-->水平居中
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        //6.3 单元格样式-->垂直居中
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        //6.4 单个cell边框样式
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        //6.4 单个cell框y、颜色
//        cellStyle.setLeftBorderColor((short)12);
//        cellStyle.setBottomBorderColor((short)12);
//        cellStyle.setRightBorderColor((short)12);
//        cellStyle.setTopBorderColor((short)12);
//
//        //7.得到坐标（1,1）Excel中（2,2）这个单元格cell对象，设置样式
//        CellUtil.getCell(row1,1).setCellStyle(cellStyle);
//
//
//        //第二个合并单元：
//        //第2--4行6--10列合并
//        CellRangeAddress region2 = new CellRangeAddress(1, 3, (short) 5, (short) 9);
//        sheet.addMergedRegion(region2);
//        row1.createCell(5).setCellValue("第二至四行6--10列合并");
//
//        RegionUtil.setBorderBottom(BorderStyle.DASHED,region2,sheet);
//        RegionUtil.setBorderLeft(BorderStyle.DASHED,region2,sheet);
//        RegionUtil.setBorderRight(BorderStyle.DASHED,region2,sheet);
//        RegionUtil.setBorderTop(BorderStyle.DASHED,region2,sheet);
//
//        RegionUtil.setBottomBorderColor(50,region2,sheet);
//        RegionUtil.setLeftBorderColor(50,region2,sheet);
//        RegionUtil.setRightBorderColor(50,region2,sheet);
//        RegionUtil.setTopBorderColor(50,region2,sheet);
//
//        HSSFCellStyle cellStyle1 = wb.createCellStyle();
//        // 创建字体样式对象
//        Font fontStyle1 = wb.createFont();
//        //字体加粗
//        fontStyle1.setBold(true);
//        //字体大小
//        fontStyle1.setFontHeightInPoints((short) 12);
//        // 将字体样式添加到单元格样式中
//        cellStyle1.setFont(fontStyle1);
//        // 单元格样式-->水平居中
//        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
//        // 单元格样式-->垂直居中
//        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
//        //单元格样式边框
//        cellStyle1.setBorderBottom(BorderStyle.DASHED);
//        cellStyle1.setBorderLeft(BorderStyle.DASHED);
//        cellStyle1.setBorderRight(BorderStyle.DASHED);
//        cellStyle1.setBorderTop(BorderStyle.DASHED);
//        //单元格样式边框颜色
//        cellStyle1.setLeftBorderColor((short)50);
//        cellStyle1.setBottomBorderColor((short)50);
//        cellStyle1.setRightBorderColor((short)50);
//        cellStyle1.setTopBorderColor((short)50);
//
//        //得到坐标（1,5）Excel中（2,6）这个单元格cell对象，设置样式
//        //注意：这儿两个合并区域的单元起始在同一行，都是第2行开始，故不能再创建Row 这个
//        //对象，否则前一个单元区域的值会失效。
//        CellUtil.getCell(row1,5).setCellStyle(cellStyle1);
//
//        //第三个合并单元
//        //第6--9行2--4列合并
//        CellRangeAddress region3 = new CellRangeAddress(5, 8, (short) 1, (short) 3);
//        sheet.addMergedRegion(region3);
//        HSSFRow row2 = sheet.createRow(5);
//        row2.createCell(1).setCellValue("第六至九行2--4列合并");
//
//        RegionUtil.setBorderBottom(BorderStyle.THIN,region3,sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN,region3,sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN,region3,sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN,region3,sheet);
//
//        RegionUtil.setBottomBorderColor(12,region3,sheet);
//        RegionUtil.setLeftBorderColor(12,region3,sheet);
//        RegionUtil.setRightBorderColor(12,region3,sheet);
//        RegionUtil.setTopBorderColor(12,region3,sheet);
//
//        //单个单元格的样式我不想再写，可以服用第一次创建的单元格样式cellStyle,，可抽取成工具类
//        //注意：该合并的单元区设置的边框样式必须和第一次设置单元格的样式保持一致
//        CellUtil.getCell(row2,1).setCellStyle(cellStyle);
//
//
//        //第四个单元区域
//        //第7--12行7--9列合并
//        CellRangeAddress region4 = new CellRangeAddress(6, 11, (short) 6, (short) 8);
//        sheet.addMergedRegion(region4);
//        HSSFRow row3 = sheet.createRow(6);
//        row3.createCell(6).setCellValue("第7--12行7--9列合并");
//
//        RegionUtil.setBorderBottom(BorderStyle.THIN,region4,sheet);
//        RegionUtil.setBorderLeft(BorderStyle.THIN,region4,sheet);
//        RegionUtil.setBorderRight(BorderStyle.THIN,region4,sheet);
//        RegionUtil.setBorderTop(BorderStyle.THIN,region4,sheet);
//
//        RegionUtil.setBottomBorderColor(12,region4,sheet);
//        RegionUtil.setLeftBorderColor(12,region4,sheet);
//        RegionUtil.setRightBorderColor(12,region4,sheet);
//        RegionUtil.setTopBorderColor(12,region4,sheet);
//
//        //第三个  第四个合并的单元区域起始行不在同一列，所以必须创建新的Row
//        CellUtil.getCell(row3,6).setCellStyle(cellStyle);
//
//        //第一步 ：暂时注释一下两行代码，我们看看效果
//        //第二步：放开下面两行代码，再看看效果
//        //造成原因：第四个区域单元格合并造，设置的样式是（7,7）这个坐标的单元格样式。这就造成第三个合并单元格7的行样式失效
//        //为了解决这个问题，还要单独的为这两个（7,2）与（7,4）单元格设置和第三个合并单元区域相同的样式
//
//        /*<p>那么题又来了-->我用RegionUtil设置整个合并单元格的样式就可以了，为什么还要去单独设置
//         *一个cell的样式呢？ 因为你要设置合并单元格的内容、字体、水平居中、垂直居中==，这就只能依靠
//         * 单个cell去设置，单个cell去设置又会覆盖整个RegionUtil设置的合并单元格样式，这就需要单个
//         * cell的边框样式和整个单元格设置的边框保持一致</P>
//         * 你get到了吗？？？？？
//         */
//        CellUtil.getCell(row3,1).setCellStyle(cellStyle);
//        CellUtil.getCell(row3,3).setCellStyle(cellStyle);
//
//        FileOutputStream fileOut;
//        try {
//            fileOut = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/workbook.xls");
//            wb.write(fileOut);
//            fileOut.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("写出成功！");
//        Log.e("hyd", "exportExcel: " );
//    }
}
