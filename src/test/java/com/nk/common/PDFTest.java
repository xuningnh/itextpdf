package com.nk.common;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PDFTest {
    private static final String FILE_URL = "C:\\Users\\xn\\Pictures\\";
    private static final int alarmNum = 123;

    ChartUtil chartUtil;

    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_URL + "园区报告.pdf"));
            document.open();
            //设置中文样式（不设置，中文将不会显示）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font fontChinese_title = new Font(bfChinese, 20, Font.BOLD, BaseColor.BLACK);
            Font fontChinese_content = new Font(bfChinese, 10, Font.NORMAL, BaseColor.BLACK);
            Paragraph paragraph_title = new Paragraph("这是一个标题", fontChinese_title);
            paragraph_title.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph paragraph_title_1 = new Paragraph("01总概括", fontChinese_content);
            paragraph_title_1.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            Paragraph paragraph_content = new Paragraph("本期发生告警数量一共" + alarmNum + "次", fontChinese_content);
            paragraph_content.setFirstLineIndent(20);
            document.add(paragraph_title);
            document.add(paragraph_title_1);
            document.add(paragraph_content);
//            document.newPage();
//            Image img = Image.getInstance(FILE_URL+"N03S{0KBD3NLW8(M}G0{UMR.png");
//            img.setAlignment(Image.ALIGN_CENTER);
//            img.scaleAbsolute(328, 370);
//            document.add(img);
            /**
             * 生成饼状统计图
             */
            PieDataset dataset = pieDataSet();
            JFreeChart chart = ChartFactory.createPieChart(
                    " 项目进度分布", // chart title
                    dataset,// data
                    true,// include legend
                    true,
                    false
            );
            PiePlot plot = (PiePlot) chart.getPlot();
            //设置扇区颜色
            plot.setSectionPaint( " 非高新企业",new Color(222, 235, 247));
            plot.setSectionPaint( " 高新企业",new Color(91, 155, 213));
            // 设置扇区的线条颜色
            plot.setDefaultSectionOutlinePaint(new Color(255, 255, 255));
            // 设置扇区的线条大小
            plot.setDefaultSectionOutlineStroke(new BasicStroke(2));
            //设置Label字体
            plot.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
            //设置legend字体
            chart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
            // 图片中显示百分比:默认方式
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
            // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
            // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
            // 设置背景色为白色
            chart.setBackgroundPaint(Color.white);
            // 指定图片的透明度(0.0-1.0)
            plot.setForegroundAlpha(1.0f);
            // 指定显示的饼图上圆形(false)还是椭圆形(true)
            plot.setCircular(true);
            // 设置图标题的字体
            java.awt.Font font = new java.awt.Font(" 黑体", java.awt.Font.CENTER_BASELINE, 20);
            TextTitle title = new TextTitle("饼状图");
            title.setFont(font);
            chart.setTitle(title);
            FileOutputStream fos_jpg = null;
            try {
                fos_jpg = new FileOutputStream(FILE_URL + "饼状图.jpg");
                ChartUtils.writeChartAsJPEG(fos_jpg, 0.7f, chart, 800, 1000, null);
                fos_jpg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.newPage();
            Paragraph pieParagraph = new Paragraph("02、饼状图测试", fontChinese_content);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);
            Image pieImage = Image.getInstance(FILE_URL + "饼状图.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);


            /**
             * 柱状图
             */
            JFreeChart jFreeChart = CreateJfreeBarChart.iCreateBarChart();
            CreateJfreeBarChart.iSetBarChart(jFreeChart);
            jFreeChart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));
            //获取title
            jFreeChart.getTitle().setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));

            // 设置图标题的字体
            java.awt.Font font2 = new java.awt.Font(" 黑体", java.awt.Font.CENTER_BASELINE, 20);
            TextTitle title2 = new TextTitle("柱状图");
            title2.setFont(font2);
            jFreeChart.setTitle(title2);
            FileOutputStream fos_jpg2 = null;
            try {
                fos_jpg2 = new FileOutputStream(FILE_URL + "柱状图.jpg");
                ChartUtils.writeChartAsJPEG(fos_jpg2, 0.7f, jFreeChart, 800, 1000, null);
                fos_jpg2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.newPage();
            Paragraph barParagraph = new Paragraph("02、柱状图测试", fontChinese_content);
            barParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(barParagraph);
            Image barImage = Image.getInstance(FILE_URL + "柱状图.jpg");
            barImage.setAlignment(Image.ALIGN_CENTER);
            barImage.scaleAbsolute(400, 300);
            document.add(barImage);


            /**
             * 折线图
             */
            CategoryDataset lineDataset = lineDataset();
            JFreeChart lineChart = ChartFactory.createLineChart("java图书销量",
                    "java图书",
                    "销量",
                    lineDataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    true
            );
            lineChart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));
            //获取title
            lineChart.getTitle().setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));

            //获取绘图区对象
            CategoryPlot linePlot = lineChart.getCategoryPlot();
            linePlot.setBackgroundAlpha(0.1f);

            //获取坐标轴对象
            CategoryAxis lineAxis = linePlot.getDomainAxis();
            //设置坐标轴字体
            lineAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
            //设置坐标轴标尺值字体（x轴）
            lineAxis.setTickLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
            //获取数据轴对象（y轴）
            ValueAxis rangeAxis = linePlot.getRangeAxis();
            rangeAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));


            /*
             * 生成图片
             */
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(FILE_URL + "折线图.jpg");
                ChartUtils.writeChartAsJPEG(fos, 0.7f, lineChart, 600, 300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Paragraph lineParagraph = new Paragraph("03、折线图测试", fontChinese_content);
            lineParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(lineParagraph);
            Image image = Image.getInstance(FILE_URL + "折线图.jpg");
            image.setAlignment(Image.ALIGN_CENTER);
            image.scaleAbsolute(600, 300);
            document.add(image);

            System.out.println("over");
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CategoryDataset lineDataset() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        //添加第一季度数据
        dataSet.addValue(6000, "第一季度", "J2SE类");
        dataSet.addValue(3000, "第一季度", "J2ME类");
        dataSet.addValue(12000, "第一季度", "J2EE类");
        //添加第二季度数据
        dataSet.addValue(8000, "第二季度", "J2SE类");
        dataSet.addValue(4000, "第二季度", "J2ME类");
        dataSet.addValue(6000, "第二季度", "J2EE类");
        //添加第三季度数据
        dataSet.addValue(5000, "第三季度", "J2SE类");
        dataSet.addValue(4000, "第三季度", "J2ME类");
        dataSet.addValue(8000, "第三季度", "J2EE类");
        //添加第四季度数据
        dataSet.addValue(8000, "第四季度", "J2SE类");
        dataSet.addValue(2000, "第四季度", "J2ME类");
        dataSet.addValue(9000, "第四季度", "J2EE类");
        return dataSet;
    }

    private static PieDataset pieDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue(" 市场前期", new Double(10));
//        dataset.setValue(" 立项", new Double(15));
//        dataset.setValue(" 计划", new Double(10));
//        dataset.setValue(" 需求与设计", new Double(10));
//        dataset.setValue(" 执行控制", new Double(35));
        dataset.setValue(" 非高新企业", new Double(17));
        dataset.setValue(" 高新企业", new Double(83));
        return dataset;
    }
}