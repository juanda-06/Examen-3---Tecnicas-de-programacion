package Servicios;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartPanel;

public class Grafica {

    public static ChartPanel crearGrafico(Map<String, Double> promediosPorCiudad) {

        var dataset = new DefaultCategoryDataset();
        promediosPorCiudad.forEach((ciudad, promedio) ->
            dataset.addValue(promedio, "Promedio", ciudad)
        );

        JFreeChart chart = ChartFactory.createBarChart(
                "Promedio de Temperaturas por Ciudad",
                "Ciudad",
                "Temperatura (°C)",
                dataset
        );

        chart.setBackgroundPaint(new Color(206, 250, 254));
        chart.getTitle().setFont(new Font("Consolas", Font.BOLD, 16));
        chart.getTitle().setPaint(Color.BLACK);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(198, 210, 255));
        plot.setRangeGridlinePaint(new Color(12, 10, 9));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(67, 32, 45));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(false);
   
        plot.getDomainAxis().setLabelFont(new Font("Consolas", Font.BOLD, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Consolas", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Consolas", Font.BOLD, 12));


        ChartPanel grafico = new ChartPanel(chart);
        return grafico;
    }
}