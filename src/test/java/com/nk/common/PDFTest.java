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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PDFTest {
    private static final String FILE_URL = "C:\\Users\\xn\\Pictures\\";
    private static final int alarmNum = 123;

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
            //生成饼状统计图
            pieDataSet(document, fontChinese_content);
            //柱状图
//            barDataset(document, fontChinese_content);
            //折线图
//            lineDataset(document, fontChinese_content);

            System.out.println("over");
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 折线图
     *
     * @return
     */
    private static void lineDataset(Document document, Font fontChinese_content) {
        DefaultCategoryDataset lineDataset1 = new DefaultCategoryDataset();

        //申请量
        lineDataset1.addValue(3000, "申请量", "2016");
        lineDataset1.addValue(3980, "申请量", "2017");
        lineDataset1.addValue(3025, "申请量", "2018");
        lineDataset1.addValue(3500, "申请量", "2019");
        lineDataset1.addValue(6100, "申请量", "2020");
        lineDataset1.addValue(5900, "申请量", "2021");

        DefaultCategoryDataset lineDataset2 = new DefaultCategoryDataset();
        //申请成功量
        lineDataset1.addValue(2015, "申请成功量", "2016");
        lineDataset1.addValue(3100, "申请成功量", "2017");
        lineDataset1.addValue(2030, "申请成功量", "2018");
        lineDataset1.addValue(2600, "申请成功量", "2019");
        lineDataset1.addValue(4100, "申请成功量", "2020");
        lineDataset1.addValue(4160, "申请成功量", "2021");

        DefaultCategoryDataset lineDataset3 = new DefaultCategoryDataset();
        //申请企业数
        lineDataset3.addValue(12, "0", "2016");// 虚拟数据,用来把下面的真实数据挤到series 2
        lineDataset3.addValue(12, "1", "2016");// 虚拟数据,用来把下面的真实数据挤到series 2
        lineDataset3.addValue(12, "申请企业数", "2016");
        lineDataset3.addValue(20, "申请企业数", "2017");
        lineDataset3.addValue(13, "申请企业数", "2018");
        lineDataset3.addValue(24, "申请企业数", "2019");
        lineDataset3.addValue(26, "申请企业数", "2020");
        lineDataset3.addValue(32, "申请企业数", "2021");

        JFreeChart lineChart = ChartFactory.createLineChart(
                "",
                "",
                "",
                lineDataset1,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );
        CategoryPlot plot = lineChart.getCategoryPlot();

        NumberAxis numberAxis1 = new NumberAxis();
        // 左侧刻度跨度为 1000 单位
        numberAxis1.setTickUnit(new NumberTickUnit(1000));
        // 设置Y轴左侧刻度
        plot.setRangeAxis(0, numberAxis1);

        //设置数据源dataset3
        plot.setDataset(1, lineDataset2);
        plot.setDataset(2, lineDataset3);
        plot.mapDatasetToRangeAxis(0, 0);
//        if (lineDataset1.getColumnCount() > 0 && lineDataset3.getColumnCount() > 0) {
        NumberAxis numberAxis2 = new NumberAxis();
        // 手动指定右侧刻度区间
        numberAxis2.setRange(new Range(0, 60));
        // 右侧刻度跨度为 10 单位
        numberAxis2.setTickUnit(new NumberTickUnit(10));
        // 设置Y轴右侧刻度
        plot.setRangeAxis(1, numberAxis2);
        // 设置数据源dataset3应用Y轴右侧刻度
        plot.mapDatasetToRangeAxis(2, 1);
//        }
        // 设置样式
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) plot.getRenderer();
        // 设置偏移量(同值是否重叠)
        lasp.setUseSeriesOffset(false);
        // 设置拐点可见
        lasp.setDefaultShapesVisible(true);
        // 设置拐点不同用不同的形状
//        lasp.setDrawOutlines(true);
        // 设置拐点的大小
        lasp.setSeriesOutlineStroke(0, new BasicStroke(1.5F));
        // 设置拐点形状(圆形拐点)
        lasp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
        lasp.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
        lasp.setSeriesShape(2, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
        // 设置折线与拐点颜色
        lasp.setSeriesPaint(0, new Color(2, 167, 240));
        lasp.setSeriesPaint(1, new Color(245, 154, 35));
        lasp.setSeriesPaint(2, new Color(127, 127, 127));
//        lineChart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));
        //获取title
//        lineChart.getTitle().setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 16));

        // 设置顶端显示数字
        lasp.setDefaultItemLabelsVisible(true);
        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        //获取绘图区对象
        CategoryPlot linePlot = lineChart.getCategoryPlot();
        // 背景纯白
        linePlot.setBackgroundAlpha(0f);

        //获取坐标轴对象
        CategoryAxis lineAxis = linePlot.getDomainAxis();
        //设置坐标轴字体
        lineAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        //设置坐标轴标尺值字体（x轴）
        lineAxis.setTickLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        //获取数据轴对象（y轴）
        ValueAxis rangeAxis = linePlot.getRangeAxis();
        rangeAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));

        try {
            // 生成图片
            FileOutputStream fos = new FileOutputStream(FILE_URL + "折线图.jpg");
            ChartUtils.writeChartAsJPEG(fos, 1f, lineChart, 800, 300);
            Paragraph lineParagraph = new Paragraph("03、折线图测试", fontChinese_content);
            lineParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(lineParagraph);
            Image image = Image.getInstance(FILE_URL + "折线图.jpg");
            image.setAlignment(Image.ALIGN_CENTER);
            image.scaleAbsolute(800, 300);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 饼状图
     *
     * @return
     */
    private static void pieDataSet(Document document, Font fontChinese_content) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("高新企业", new Double(83));
        dataset.setValue("非高新企业", new Double(17));
        JFreeChart chart = ChartFactory.createPieChart(
                "园区报告", // chart title
                dataset,// data
                false,// include legend
                false,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        //设置扇区颜色
        plot.setSectionPaint("非高新企业", new Color(222, 235, 247));
        plot.setSectionPaint("高新企业", new Color(91, 155, 213));
        // "取出" 非高新企业扇区
        plot.setExplodePercent("非高新企业", 0.1);
        // 设置扇区的线条颜色
        plot.setDefaultSectionOutlinePaint(new Color(255, 255, 255));
        // 设置扇区的线条大小
        plot.setDefaultSectionOutlineStroke(new BasicStroke(5));
        // 完全关闭/开启片区外廓
        plot.setSectionOutlinesVisible(false);
        // 片区起始角度
        plot.setStartAngle(90);
        //设置Label字体
        plot.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        //设置legend字体
//        chart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        // 图片中显示百分比:默认方式
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
        // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
//        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        // 设置背景色为白色
        chart.setBackgroundPaint(Color.white);
        // 指定图片的透明度(0.0-1.0)
        plot.setForegroundAlpha(1.0f);
        // 背景纯白
        plot.setBackgroundAlpha(0f);
        // 指定显示的饼图上圆形(false)还是椭圆形(true)
        plot.setCircular(true);
        // 设置图标题的字体
        java.awt.Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE, 20);
        TextTitle title = new TextTitle("饼状图");
        title.setFont(font);
        chart.setTitle(title);
        FileOutputStream fos_jpg = null;
        try {
            fos_jpg = new FileOutputStream(FILE_URL + "饼状图.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg, 1f, chart, 800, 1000, null);
            fos_jpg.close();


            document.newPage();
            Paragraph pieParagraph = new Paragraph("02、饼状图测试", fontChinese_content);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);
            Image pieImage = Image.getInstance(FILE_URL + "饼状图.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 柱状图
     *
     * @param document
     * @param fontChinese_content
     */
    private static void barDataset(Document document, Font fontChinese_content) {
        JFreeChart jFreeChart = CreateJfreeBarChart.iCreateBarChart();
        CreateJfreeBarChart.iSetBarChart(jFreeChart);

        // 设置图标题的字体
        java.awt.Font font2 = new java.awt.Font(" 黑体", java.awt.Font.CENTER_BASELINE, 20);
        TextTitle title2 = new TextTitle("柱状图");
        title2.setFont(font2);
        jFreeChart.setTitle(title2);
        FileOutputStream fos_jpg2 = null;
        try {
            fos_jpg2 = new FileOutputStream(FILE_URL + "企业专利申请量.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg2, 1f, jFreeChart, 800, 600, null);
            fos_jpg2.close();
            document.newPage();
            Paragraph barParagraph = new Paragraph("六、企业专利申请量top10", fontChinese_content);
            barParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(barParagraph);
            Image barImage = Image.getInstance(FILE_URL + "企业专利申请量.jpg");
            barImage.setAlignment(Image.ALIGN_CENTER);
            barImage.scaleAbsolute(800, 600);
            document.add(barImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}