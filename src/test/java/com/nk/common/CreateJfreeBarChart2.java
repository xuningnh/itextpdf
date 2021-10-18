package com.nk.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class CreateJfreeBarChart2 {
    // 设置柱状图上方的标题
    private static String chartTitle = "";

    // 设置Y轴的标注
    private static String rangeAxisLabel = "污染指数";

    // 设置X轴的标注
    private static String domainAxisLabel = "城市";

    // 设置数据集合
    private static CategoryDataset Dataset = createDataset2();
    // 设置柱状图的柱子顶部是否显示数据
    private static boolean baseItemLabelsVisible = true;
    // 设置生成图片的宽度
    private static int width = 500;
    // 设置生成图片的高度
    private static int height = 800;

    /**
     * 创建柱状图
     */
    public static JFreeChart iCreateBarChart() {
        // TODO Auto-generated method stub
//        WriteChartAsImage.writeChartAsImage(chart, getSaveImgUrlAndName(), getWidth(), getHeight());
//        return ChartFactory.createBarChart(getChartTitle(), getDomainAxisLabel(), getRangeAxisLabel(), getDataset(), PlotOrientation.VERTICAL, true, false, false);
        JFreeChart chart = ChartFactory.createBarChart(getChartTitle(), null, null, getDataset(), PlotOrientation.VERTICAL, false, false, false);
        iSetBarChart(chart);
        return chart;
    }

    /**
     * 设置柱状图的样式
     *
     * @param chart
     */
    public static void iSetBarChart(JFreeChart chart) {
        CategoryPlot categoryplot = chart.getCategoryPlot();// 图本身
        ValueAxis rangeAxis = categoryplot.getRangeAxis();
        // 设置Y轴的提示文字样式
        rangeAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        // 设置Y轴刻度线的长度
        rangeAxis.setTickMarkInsideLength(10f);
        rangeAxis.setTickMarkOutsideLength(10f);

        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // 设置X轴下的标签文字
        domainAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 24));
        // 设置X轴上提示文字样式
        domainAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 16));

        // 设置Y轴的数字为百分比样式显示
//        DecimalFormat df = new DecimalFormat("0.0%");
        NumberAxis vn = (NumberAxis) categoryplot.getRangeAxis();
        // 显示Y轴数字
        vn.setVisible(true);
//        vn.setNumberFormatOverride(df);
        // 使柱状图反过来显示
        // vn.setInverted(true);
        // vn.setVerticalTickLabels(true);

        // 自定义柱状图中柱子的样式
        BarRenderer barRenderer = (BarRenderer) categoryplot.getRenderer();
        // 给series Bar 指定配色
        barRenderer.setSeriesPaint(0, new Color(91, 155, 213));
        barRenderer.setSeriesPaint(1, new Color(237, 237, 237));

        // 设置柱状图的顶端显示数字
        barRenderer.setIncludeBaseInRange(true);
        barRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer.setDefaultItemLabelsVisible(isBaseItemLabelsVisible());
        //如果数值没有显示空间，设置显示格式
        ItemLabelPosition itemLabelPositionFallback = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER,
                TextAnchor.HALF_ASCENT_LEFT, 0D);
        barRenderer.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
        barRenderer.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);
        categoryplot.setRenderer(barRenderer);

        // 设置柱子为平面图不是立体的
        barRenderer.setBarPainter(new StandardBarPainter());
        // 设置柱状图之间的距离0.1代表10%；
        barRenderer.setItemMargin(0);
        // 设置柱子宽度
        barRenderer.setMaximumBarWidth(0.3);
        // 设置柱子的阴影，false代表没有阴影
        barRenderer.setShadowVisible(false);

        // 设置图的背景为白色
        categoryplot.setBackgroundPaint(Color.WHITE);
        // 设置背景虚线的颜色
        categoryplot.setRangeGridlinePaint(Color.WHITE);
        // 去掉柱状图的背景边框，使边框不可见
        categoryplot.setOutlineVisible(false);
        // 设置标题的字体样式
        chart.getTitle().setFont(new Font("微软雅黑", Font.PLAIN, 24));
        // 设置图表下方图例上的字体样式
//        chart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN, 12));

        categoryplot.setRenderer(barRenderer);

    }

    /**
     * 折线图
     */
    public static DefaultCategoryDataset lineDataset() {
        DefaultCategoryDataset lineDataset1 = new DefaultCategoryDataset();
        //申请量
        lineDataset1.addValue(87.00d, "授权率", "商标");
        lineDataset1.addValue(57.00d, "授权率", "专利外观");
        lineDataset1.addValue(80.00d, "授权率", "实用新型");
        lineDataset1.addValue(22.00d, "授权率", "专利发明");
        lineDataset1.addValue(0.00d, "授权率", "PCT国际");
        return lineDataset1;
    }

    /**
     * 柱状图数据集合
     *
     * @return
     */
    public static CategoryDataset createDataset2() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "商标";
        String series2 = "专利外观";
        String series3 = "实用新型";
        String series4 = "专利发明";
        String series5 = "PCT国际";
        String type1 = "整体申请件数";
        String type2 = "整体注册/授权件数";

        result.addValue(8080, type1, series1);
        result.addValue(782, type1, series2);
        result.addValue(2436, type1, series3);
        result.addValue(808, type1, series4);
        result.addValue(16, type1, series5);

        result.addValue(7052, type2, series1);
        result.addValue(449, type2, series2);
        result.addValue(1953, type2, series3);
        result.addValue(181, type2, series4);
        result.addValue(0, type2, series5);
        return result;

    }


    public static String getChartTitle() {
        return chartTitle;
    }

    public static void setChartTitle(String chartTitle) {
        CreateJfreeBarChart2.chartTitle = chartTitle;
    }

    public static String getRangeAxisLabel() {
        return rangeAxisLabel;
    }

    public static void setRangeAxisLabel(String rangeAxisLabel) {
        CreateJfreeBarChart2.rangeAxisLabel = rangeAxisLabel;
    }

    public static String getDomainAxisLabel() {
        return domainAxisLabel;
    }

    public static void setDomainAxisLabel(String domainAxisLabel) {
        CreateJfreeBarChart2.domainAxisLabel = domainAxisLabel;
    }

    public static CategoryDataset getDataset() {
        return Dataset;
    }

    public static void setDataset(CategoryDataset dataset) {
        Dataset = dataset;
    }

    public static boolean isBaseItemLabelsVisible() {
        return baseItemLabelsVisible;
    }

    public static void setBaseItemLabelsVisible(boolean baseItemLabelsVisible) {
        CreateJfreeBarChart2.baseItemLabelsVisible = baseItemLabelsVisible;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        CreateJfreeBarChart2.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        CreateJfreeBarChart2.height = height;
    }

}