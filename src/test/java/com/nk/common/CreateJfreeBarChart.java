package com.nk.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
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

public class CreateJfreeBarChart {
    // 设置柱状图上方的标题
    private static String chartTitle = "环境污染指数分布图";

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
        //        iSetBarChart(chart);
//        WriteChartAsImage.writeChartAsImage(chart, getSaveImgUrlAndName(), getWidth(), getHeight());
//        return ChartFactory.createBarChart(getChartTitle(), getDomainAxisLabel(), getRangeAxisLabel(), getDataset(), PlotOrientation.VERTICAL, true, false, false);
        return ChartFactory.createBarChart(getChartTitle(), null, null, getDataset(), PlotOrientation.HORIZONTAL, false, false, false);
    }

    /**
     * 设置柱状图的样式
     *
     * @param chart
     */
    public static CategoryPlot iSetBarChart(JFreeChart chart) {
        CategoryPlot categoryplot = chart.getCategoryPlot();// 图本身
//        ValueAxis rangeAxis = categoryplot.getRangeAxis();
        // 设置Y轴的提示文字样式
//        rangeAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        // 设置Y轴刻度线的长度
//        rangeAxis.setTickMarkInsideLength(10f);
//        rangeAxis.setTickMarkOutsideLength(10f);

        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // 设置X轴下的标签文字
        domainAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 24));
        // 设置X轴上提示文字样式
        domainAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 16));

        // 设置Y轴的数字为百分比样式显示
//        DecimalFormat df = new DecimalFormat("0.0%");
        NumberAxis vn = (NumberAxis) categoryplot.getRangeAxis();
        // 隐藏Y轴数字
        vn.setVisible(false);
//        vn.setNumberFormatOverride(df);
        // 使柱状图反过来显示
        // vn.setInverted(true);
        // vn.setVerticalTickLabels(true);

        // 自定义柱状图中柱子的样式
        BarRenderer barRenderer = (BarRenderer) categoryplot.getRenderer();
        // 给series Bar 指定配色
        barRenderer.setSeriesPaint(0, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(1, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(2, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(3, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(4, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(5, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(6, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(7, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(8, Color.decode("#5b9bd5"));
        barRenderer.setSeriesPaint(9, Color.decode("#5b9bd5"));

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
        barRenderer.setItemMargin(0.05);
        // 设置柱子宽度
        barRenderer.setMaximumBarWidth(0.02);
        // 设置柱子的阴影，false代表没有阴影
        barRenderer.setShadowVisible(false);

        // 设置图的背景为白色
        categoryplot.setBackgroundPaint(Color.WHITE);
        // 设置背景虚线的颜色
        categoryplot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
        // 去掉柱状图的背景边框，使边框不可见
        categoryplot.setOutlineVisible(false);
        // 设置标题的字体样式
        chart.getTitle().setFont(new Font("微软雅黑", Font.PLAIN, 24));
        // 设置图表下方图例上的字体样式
//        chart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN, 12));

        categoryplot.setRenderer(barRenderer);

        return categoryplot;
    }

    /**
     * 数据集合
     *
     * @return
     */
    public static CategoryDataset createDataset2() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "泸州智通自动化设备有限公司";
        String series2 = "四川长江工程起重机有限责任公司";
        String series3 = "泸州市驰腾科技有限公司";
        String series4 = "四川海搏液压机械有限公司";
        String series5 = "泸州睿大智能变速器有限公司";
        String series6 = "四川邦立重机有限公司";
        String series7 = "泸州成邦机械工程有限公司";
        String series8 = "泸州长江液压密封件有限公司";
        String series9 = "泸州长江机械有限公司";
        String series10 = "泸州邦立减速机有限公司";
        String type1 = "公司";
        result.addValue(88, type1, series1);
        result.addValue(79, type1, series2);
        result.addValue(54, type1, series3);
        result.addValue(50, type1, series4);
        result.addValue(44, type1, series5);
        result.addValue(43, type1, series6);
        result.addValue(42, type1, series7);
        result.addValue(42, type1, series8);
        result.addValue(40, type1, series9);
        result.addValue(38, type1, series10);
        return result;

    }


    public static String getChartTitle() {
        return chartTitle;
    }

    public static void setChartTitle(String chartTitle) {
        CreateJfreeBarChart.chartTitle = chartTitle;
    }

    public static String getRangeAxisLabel() {
        return rangeAxisLabel;
    }

    public static void setRangeAxisLabel(String rangeAxisLabel) {
        CreateJfreeBarChart.rangeAxisLabel = rangeAxisLabel;
    }

    public static String getDomainAxisLabel() {
        return domainAxisLabel;
    }

    public static void setDomainAxisLabel(String domainAxisLabel) {
        CreateJfreeBarChart.domainAxisLabel = domainAxisLabel;
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
        CreateJfreeBarChart.baseItemLabelsVisible = baseItemLabelsVisible;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        CreateJfreeBarChart.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        CreateJfreeBarChart.height = height;
    }

}