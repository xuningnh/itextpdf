package com.nk.common;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.time.Year;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

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
//            Image img = Image.getInstance(FILE_URL+"N03S{0KBD3NLW8(M}G0{UMR.png");
//            img.setAlignment(Image.ALIGN_CENTER);
//            img.scaleAbsolute(328, 370);
//            document.add(img);
            document.newPage();
            //柱状-折线图
            bar_lineDataset(document, fontChinese_content);
            //生成饼状统计图
            pieDataSet(document, fontChinese_content);
            //柱状图
            barDataset(document, fontChinese_content);
            //折线图
            lineDataset(document, fontChinese_content);
            //堆叠柱状图-折线图
            table_dataset2(document, fontChinese_content);
            System.out.println("over");
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 折线图
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
        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance(), new DecimalFormat("0.00%")));

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
     */
    private static void pieDataSet(Document document, Font fontChinese_content) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("高新企业", new Double(83));
        dataset.setValue("非高新企业", new Double(17));
        JFreeChart chart = ChartFactory.createPieChart(
                "", // chart title
                dataset,// data
                true,// include legend
                false,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        //设置扇区颜色
        plot.setSectionPaint("非高新企业", new Color(222, 235, 247));
        plot.setSectionPaint("高新企业", new Color(91, 155, 213));
        // "取出" 非高新企业扇区
        plot.setExplodePercent("非高新企业", 0.05);
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
        chart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        // 图片中显示百分比:默认方式
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
        // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
//        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        // 设置每块饼和数据之间的线
//        plot.setLabelLinksVisible(false);
        plot.setSectionOutlinesVisible(false);//是否显示分界线
        // 设置简单标签
        plot.setSimpleLabels(true);
        // 设置分类标签与图的连接线边距
        plot.setLabelLinkMargin(0.05D);
        // 忽略无值的分类
        plot.setIgnoreZeroValues(true);
        // 设置饼与边框的距离
        plot.setLabelGap(0.00D);
        // 设置背景色为白色
        chart.setBackgroundPaint(Color.white);
        plot.setBackgroundPaint(Color.white);  //饼图背景色
        plot.setOutlinePaint(Color.white);// 设置绘图面板外边的填充颜色
        plot.setShadowPaint(Color.white);// 设置绘图面板阴影的填充颜色
        // 指定图片的透明度(0.0-1.0)
        plot.setForegroundAlpha(1.0f);
        // 背景纯白
        plot.setBackgroundAlpha(1f);
        // 指定显示的饼图上圆形(false)还是椭圆形(true)
        plot.setCircular(true);
        //下面设置的是:饼图里面的百分比去除标签边框,只显示百分比的数据 (4个都要设置,缺一不可)
        plot.setLabelOutlinePaint(null);// (1)自定义标签产生器,设置绘图面板外边的填充颜色
        plot.setLabelShadowPaint(null);//自定义标签产生器, 设置绘图面板阴影的填充颜色
        plot.setLabelOutlineStroke(null);//(2) 自定义标签产生器,设置绘图面板外边的填充颜色
        plot.setLabelBackgroundPaint(null);//自定义标签产生器,背景色
        //上面设置的是:饼图里面的百分比去除标签边框,只显示百分比的数据
        // 设置图标题的字体
//        java.awt.Font font = new java.awt.Font("黑体", java.awt.Font.CENTER_BASELINE, 20);
//        TextTitle title = new TextTitle("饼状图");
//        title.setFont(font);
//        chart.setTitle(title);
        try {
            FileOutputStream fos_jpg = new FileOutputStream(FILE_URL + "高新企业占比.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg, 1f, chart, 300, 300, null);
            fos_jpg.close();


            document.newPage();
            Paragraph pieParagraph = new Paragraph("二、高新企业占比", fontChinese_content);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);
            Image pieImage = Image.getInstance(FILE_URL + "高新企业占比.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 柱状图
     */
    private static void barDataset(Document document, Font fontChinese_content) {
        JFreeChart jFreeChart = CreateJfreeBarChart.iCreateBarChart();

        // 设置图标题的字体
//        java.awt.Font font2 = new java.awt.Font(" 黑体", java.awt.Font.CENTER_BASELINE, 20);
//        TextTitle title2 = new TextTitle("柱状图");
//        title2.setFont(font2);
//        jFreeChart.setTitle(title2);
        try {
            FileOutputStream fos_jpg2 = new FileOutputStream(FILE_URL + "企业专利申请量.jpg");
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

    /**
     * 柱状-折线图
     */
    private static void bar_lineDataset(Document document, Font fontChinese_content) {
        JFreeChart jFreeChart = CreateJfreeBarChart2.iCreateBarChart();
        // 添加折线数据
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        DefaultCategoryDataset defaultCategoryDataset = CreateJfreeBarChart2.lineDataset();
        plot.setDataset(1, CreateJfreeBarChart2.lineDataset());
        // 添加标签数字百分比显示
        LineAndShapeRenderer lasp = new LineAndShapeRenderer();
        // 设置折线的颜色
        lasp.setSeriesPaint(0, new Color(127, 127, 127));
        // 设置折点形状是否可见
        lasp.setDefaultShapesVisible(true);
        // 设置顶端显示数字
        lasp.setDefaultItemLabelsVisible(true);
        // 格式化
        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance(), new DecimalFormat("0.00%")));
        plot.setRenderer(1, lasp);
        // 折线在柱面前面显示
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        NumberAxis numberAxis1 = new NumberAxis();
        // 左侧刻度跨度为 1000 单位
        numberAxis1.setTickUnit(new NumberTickUnit(1000));
        // 设置Y轴左侧刻度
        plot.setRangeAxis(0, numberAxis1);
        NumberAxis numberAxis2 = new NumberAxis();
        // 手动指定右侧刻度区间
        numberAxis2.setRange(new Range(0, 1));
        // 右侧刻度跨度为 10 单位
        numberAxis2.setTickUnit(new NumberTickUnit(0.1));
        // 设置Y轴的数字为百分比样式显示
        DecimalFormat df = new DecimalFormat("#.##%");
        numberAxis2.setNumberFormatOverride(df);
        // 设置折点数字以百分比显示
        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", df));
        // 设置折点形状是否可见
        lasp.setDefaultShapesVisible(false);
        // 设置Y轴右侧刻度
        plot.setRangeAxis(1, numberAxis2);
        // 设置折线数据源应用Y轴右侧刻度
        plot.mapDatasetToRangeAxis(1, 1);

//        lasp.setBaseShapesVisible(true);
//        lasp.setBaseItemLabelsVisible(true);
//        lasp.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
//                NumberFormat.getInstance()));
//        lasp.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi
        try {
            FileOutputStream fos_jpg2 = new FileOutputStream(FILE_URL + "商标、专利申请件数情况 -授权率.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg2, 1f, jFreeChart, 1000, 400, null);
            fos_jpg2.close();
            document.newPage();
            Paragraph barParagraph = new Paragraph("一、商标、专利申请件数情况 -授权率", fontChinese_content);
            barParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(barParagraph);
            Image barImage = Image.getInstance(FILE_URL + "商标、专利申请件数情况 -授权率.jpg");
            barImage.setAlignment(Image.ALIGN_CENTER);
            barImage.scaleAbsolute(800, 600);
            document.add(barImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 堆叠柱状图-折线图
     */
    private static void table_dataset2(Document document, Font fontChinese_content) {
        CategoryDataset barDataset = createDataset3();
        DefaultCategoryDataset lineDataset = lineDataset();
        //参数： title,x轴说明，y轴说明，图例
        JFreeChart chart = ChartFactory.createBarChart("", "", "", barDataset, PlotOrientation.VERTICAL,
                true, false, true);
        //设置legend字体
        chart.getLegend().setItemFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();
        // 设置Y轴的提示文字样式
        rangeAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 12));
        // 设置Y轴刻度线的长度
        rangeAxis.setTickMarkInsideLength(10f);
        rangeAxis.setTickMarkOutsideLength(10f);
        //将默认放到左边的数值放到右边
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        // 设置刻度是否可见
        rangeAxis.setVisible(false);

        // X轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        // 设置X轴下的标签文字
        domainAxis.setLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 24));
        // 设置X轴上提示文字样式
        domainAxis.setTickLabelFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 16));
        // set right margin
        domainAxis.setUpperMargin(0.1);
        // set X axis Label lines
        domainAxis.setMaximumCategoryLabelLines(2);
        //NumberAxis na = (NumberAxis) plot.getRangeAxis();
        // stack bar chart
        StackedBarRenderer renderer = new StackedBarRenderer();
        plot.setRenderer(0, renderer);
        renderer.setDefaultItemLabelsVisible(true);
        DecimalFormat decimalformat1 = new DecimalFormat("#.##");
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
//        rederer.setDefaultItemLabelFont(fontChinese_content);
        renderer.setMaximumBarWidth(0.07);
//        renderer.setMinimumBarLength(20);
        // 设置柱子为平面图不是立体的
        renderer.setBarPainter(new StandardBarPainter());
        // 设置柱子的阴影，false代表没有阴影
        renderer.setShadowVisible(false);
        // 柱子颜色
        renderer.setSeriesPaint(0, new Color(250, 205, 140));
        renderer.setSeriesPaint(1, new Color(2, 167, 240));
        renderer.setSeriesPaint(2, new Color(128, 128, 255));
        renderer.setSeriesPaint(3, new Color(128, 255, 255));

        // line chart
        LineAndShapeRenderer lasp = new LineAndShapeRenderer();
        plot.setRenderer(1, lasp);

        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lasp.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.BASELINE_LEFT));
        lasp.setDefaultItemLabelsVisible(true);
        lasp.setDefaultShapesVisible(true);
        // 设置拐点形状(圆形拐点)
        lasp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
        // 设置折线与拐点颜色
        lasp.setSeriesPaint(0, new Color(245, 154, 35));

        // point style
//        lineRenderer.setSeriesShape(0, new Rectangle(5, 5));

        NumberAxis rightYAxis = new NumberAxis("");
        rightYAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        plot.setRangeAxis(1, rightYAxis);
        plot.setDataset(1, lineDataset);
        plot.mapDatasetToRangeAxis(1, 1);
        // set render order
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        // 背景纯白
        plot.setBackgroundAlpha(0f);

        try {
            FileOutputStream fos_jpg2 = new FileOutputStream(FILE_URL + "专利年度申请趋势.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg2, 1f, chart, 1000, 400, null);
            fos_jpg2.close();
            document.newPage();
            Paragraph barParagraph = new Paragraph("四、专利年度申请趋势", fontChinese_content);
            barParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(barParagraph);
            Image barImage = Image.getInstance(FILE_URL + "专利年度申请趋势.jpg");
            barImage.setAlignment(Image.ALIGN_CENTER);
            barImage.scaleAbsolute(800, 600);
            document.add(barImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CategoryDataset createDataset3() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "2016";
        String series2 = "2017";
        String series3 = "2018";
        String series4 = "2019";
        String series5 = "2020";
        String series6 = "2021";
        String type1 = "外观";
        String type2 = "实用新型";
        String type3 = "发明";
        String type4 = "PCT";

        result.addValue(200, type1, series1);
        result.addValue(250, type1, series2);
        result.addValue(300, type1, series3);
        result.addValue(310, type1, series4);
        result.addValue(410, type1, series5);
        result.addValue(320, type1, series6);

        result.addValue(100, type2, series1);
        result.addValue(110, type2, series2);
        result.addValue(120, type2, series3);
        result.addValue(130, type2, series4);
        result.addValue(110, type2, series5);
        result.addValue(120, type2, series6);

        result.addValue(50, type3, series1);
        result.addValue(60, type3, series2);
        result.addValue(120, type3, series3);
        result.addValue(140, type3, series4);
        result.addValue(90, type3, series5);
        result.addValue(80, type3, series6);

        result.addValue(50, type4, series1);
        result.addValue(11, type4, series2);
        result.addValue(30, type4, series3);
        result.addValue(20, type4, series4);
        result.addValue(15, type4, series5);
        result.addValue(12, type4, series6);

        return result;

    }

    private static DefaultCategoryDataset lineDataset() {
        DefaultCategoryDataset lineDataset1 = new DefaultCategoryDataset();
        //申请企业数
        lineDataset1.addValue(25d, "申请企业数", "2016");
        lineDataset1.addValue(31d, "申请企业数", "2017");
        lineDataset1.addValue(28d, "申请企业数", "2018");
        lineDataset1.addValue(36d, "申请企业数", "2019");
        lineDataset1.addValue(45d, "申请企业数", "2020");
        lineDataset1.addValue(37d, "申请企业数", "2021");
        return lineDataset1;
    }

    /**
     * XY柱状图
     */
    private static void table_xy_dataset(Document document, Font fontChinese_content) {
        TimeTableXYDataset dataset = new TimeTableXYDataset();

        dataset.add(new Year(2016), 200, "外观");
        dataset.add(new Year(2017), 250, "外观");
        dataset.add(new Year(2018), 300, "外观");
        dataset.add(new Year(2019), 310, "外观");
        dataset.add(new Year(2020), 410, "外观");
        dataset.add(new Year(2021), 320, "外观");

        dataset.add(new Year(2016), 100, "实用新型");
        dataset.add(new Year(2017), 110, "实用新型");
        dataset.add(new Year(2018), 120, "实用新型");
        dataset.add(new Year(2019), 130, "实用新型");
        dataset.add(new Year(2020), 110, "实用新型");
        dataset.add(new Year(2021), 120, "实用新型");

        dataset.add(new Year(2016), 50, "发明");
        dataset.add(new Year(2017), 60, "发明");
        dataset.add(new Year(2018), 120, "发明");
        dataset.add(new Year(2019), 140, "发明");
        dataset.add(new Year(2020), 90, "发明");
        dataset.add(new Year(2021), 80, "发明");

        dataset.add(new Year(2016), 10, "PCT");
        dataset.add(new Year(2017), 11, "PCT");
        dataset.add(new Year(2018), 30, "PCT");
        dataset.add(new Year(2019), 20, "PCT");
        dataset.add(new Year(2020), 15, "PCT");
        dataset.add(new Year(2021), 12, "PCT");

        DateAxis domainAxis = new DateAxis();
        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        domainAxis.setLowerMargin(0.01);
        domainAxis.setUpperMargin(0.01);
        // 设置横坐标为间隔一年
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy")));
        // 横坐标年份是否竖立
        domainAxis.setVerticalTickLabels(false);
        NumberAxis rangeAxis = new NumberAxis("Count");
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.10);  // leave some space for item labels
        StackedXYBarRenderer renderer = new StackedXYBarRenderer(0.15);
        renderer.setDrawBarOutline(false);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER));

        // 设置柱子为平面图不是立体的
        renderer.setBarPainter(new StandardXYBarPainter());
        // 设置柱状图之间的距离0.1代表10%；
        renderer.setMargin(0.7);
        // 设置柱子的阴影，false代表没有阴影
        renderer.setShadowVisible(false);

        // 柱子颜色
        renderer.setSeriesPaint(0, new Color(250, 205, 140));
        renderer.setSeriesPaint(1, new Color(2, 167, 240));
        renderer.setSeriesPaint(2, new Color(128, 128, 255));
        renderer.setSeriesPaint(3, new Color(128, 255, 255));
        XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        //将默认放到左边的数值放到右边
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        JFreeChart chart = new JFreeChart("", plot);
//        JFreeChart chart = ChartFactory.createXYBarChart("", null, true, null, dataset);

//        chart.removeLegend();
//        chart.addSubtitle(new TextTitle("PGA Tour, 1983 to 2003"));
//        TextTitle source = new TextTitle("http://www.golfdigest.com/majors/masters/index.ssf?/majors/masters/gw20040402albatross.html", new Font(bfChinese, 10, Font.NORMAL, BaseColor.BLACK););
//        chart.addSubtitle(source);
//        LegendTitle legend = new LegendTitle(plot);
//        legend.setBackgroundPaint(Color.white);
//        legend.setBorder(0,0,0,0);
//        legend.setPosition(RectangleEdge.BOTTOM);
//        chart.addSubtitle(legend);

        // 添加折线数据
//        CategoryPlot categoryPlot = chart.getCategoryPlot();

//        // 添加标签数字百分比显示
//        LineAndShapeRenderer lasp = new LineAndShapeRenderer();
//        // 设置折线的颜色
//        lasp.setSeriesPaint(0, new Color(127, 127, 127));
//        // 设置折点形状是否可见
//        lasp.setDefaultShapesVisible(true);
//        // 设置顶端显示数字
//        lasp.setDefaultItemLabelsVisible(true);
//        // 格式化
//        lasp.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance(), new DecimalFormat("0.00%")));
//        categoryPlot.setRenderer(1, lasp);
//        // 折线在柱面前面显示
//        categoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
//        NumberAxis numberAxis1 = new NumberAxis();
//        // 左侧刻度跨度为 1000 单位
//        numberAxis1.setTickUnit(new NumberTickUnit(1000));
//        // 设置Y轴左侧刻度
//        categoryPlot.setRangeAxis(0, numberAxis1);
//        NumberAxis numberAxis2 = new NumberAxis();
//        // 手动指定右侧刻度区间
//        numberAxis2.setRange(new Range(0, 100));
//        // 右侧刻度跨度为 10 单位
//        numberAxis2.setTickUnit(new NumberTickUnit(10));
//        // 设置Y轴右侧刻度
//        categoryPlot.setRangeAxis(1, numberAxis2);
//        // 设置折线数据源应用Y轴右侧刻度
//        plot.mapDatasetToRangeAxis(1, 1);

        try {
            FileOutputStream fos_jpg2 = new FileOutputStream(FILE_URL + "专利年度申请趋势.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg2, 1f, chart, 1000, 400, null);
            fos_jpg2.close();
            document.newPage();
            Paragraph barParagraph = new Paragraph("四、专利年度申请趋势", fontChinese_content);
            barParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(barParagraph);
            Image barImage = Image.getInstance(FILE_URL + "专利年度申请趋势.jpg");
            barImage.setAlignment(Image.ALIGN_CENTER);
            barImage.scaleAbsolute(800, 600);
            document.add(barImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}