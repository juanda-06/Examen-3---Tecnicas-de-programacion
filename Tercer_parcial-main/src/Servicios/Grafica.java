package Servicios;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafica {

    public static ChartPanel crearGrafico(Map<String, Double> promediosPorCiudad) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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
        chart.getTitle().setFont(new Font("Italic", Font.ITALIC, 16));
        chart.getTitle().setPaint(Color.BLACK);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(198, 210, 255));
        plot.setRangeGridlinePaint(new Color(12, 10, 9));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(67, 32, 45));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setShadowVisible(false);
   
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));


        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    // METODOO PARA PROBAR Y PERSONALIZAR EL GRAFICO
    public static void main(String[] args) {
    // Datos de ejemplo
    Map<String, Double> datos = Map.of(
        "Bogotá", 18.5,
        "Medellín", 24.3,
        "Cali", 27.1,
        "Barranquilla", 30.2
    );

    // Crear el gráfico
    ChartPanel panel = crearGrafico(datos);

    // Crear una ventana para mostrarlo
    javax.swing.JFrame ventana = new javax.swing.JFrame("Vista previa de la gráfica");
    ventana.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    ventana.setSize(800, 600);
    ventana.add(panel);
    ventana.setVisible(true);
}

}
