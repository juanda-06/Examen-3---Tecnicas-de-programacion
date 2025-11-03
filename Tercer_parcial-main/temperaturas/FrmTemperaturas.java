import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import Servicios.Filtrado;
import Servicios.Grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import datechooser.beans.DateChooserCombo;
import entidades.DatosTemperaturas;
import java.awt.Color;
import java.awt.Panel;
import org.jfree.chart.ChartPanel;

public class FrmTemperaturas extends JFrame {

    private JComboBox cmbCiudad;
    private DateChooserCombo dccDesde, dccHasta, dccDato;
    private JTabbedPane tpCambiosClima;
    private JPanel pnlGraficar, pnlRangoFechas, pnlFechaEspecifica;
    private JPanel pnlOscilacion, pnlClimaGeneral;
    private List<DatosTemperaturas> temperaturas;


    public FrmTemperaturas(){
        setTitle("Cambio Climático");
        setSize(700, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tb = new JToolBar();
        JButton btnGraficar = new JButton();
        btnGraficar.setIcon(new ImageIcon(getClass().getResource("/iconos/grafico.png")));
        btnGraficar.setToolTipText("Grafica Cambios vs Fecha");
        btnGraficar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGraficarClick();
            }
        });
        tb.add(btnGraficar);

        JButton btnDatos = new JButton();
        btnDatos.setIcon(new ImageIcon(getClass().getResource("/iconos/datosC.png")));
        btnDatos.setToolTipText("Variación climática");
        btnDatos.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDatosClick();
            }
        });
        tb.add(btnDatos);

        // 1er pnl Grafica 
        JPanel pnlCambios = new JPanel();
        pnlCambios.setLayout(new BoxLayout(pnlCambios, BoxLayout.Y_AXIS));

        pnlRangoFechas = new JPanel();
        pnlRangoFechas.setPreferredSize(new Dimension(pnlRangoFechas.getWidth(), 50)); // Altura fija de 100px
        pnlRangoFechas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlRangoFechas.setLayout(null);

        JLabel lblCiudad = new JLabel("Ciudad: ");
        lblCiudad.setBounds(40, 10, 100, 25);
        pnlRangoFechas.add(lblCiudad);

        cmbCiudad = new JComboBox();
        cmbCiudad.setBounds(100, 10, 100, 25);
        pnlRangoFechas.add(cmbCiudad);

        Calendar fechaMin = new GregorianCalendar(2025, Calendar.JANUARY, 1);
        Calendar fechaMax = new GregorianCalendar(2025, Calendar.FEBRUARY, 28);

        dccDesde = new DateChooserCombo();
        dccDesde.setBounds(220, 10, 100, 25);
        pnlRangoFechas.add(dccDesde);
        dccDesde.setMinDate(fechaMin);
        dccDesde.setMaxDate(fechaMax);

        dccHasta = new DateChooserCombo();
        dccHasta.setBounds(330, 10, 100, 25);
        pnlRangoFechas.add(dccHasta);
        dccHasta.setMaxDate(fechaMax);
        dccHasta.setMinDate(fechaMin);

        pnlGraficar = new JPanel();
        JScrollPane spGraficar = new JScrollPane(pnlGraficar);

        pnlCambios.add(pnlRangoFechas);
        pnlCambios.add(spGraficar);
  
        // 2do pnl climas en general
        pnlClimaGeneral = new JPanel();
        pnlClimaGeneral.setLayout(new BoxLayout(pnlClimaGeneral, BoxLayout.Y_AXIS));

        pnlFechaEspecifica = new JPanel();
        pnlFechaEspecifica.setPreferredSize(new Dimension(pnlFechaEspecifica.getWidth(), 50)); // Altura fija de 100px
        pnlFechaEspecifica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlFechaEspecifica.setLayout(null);

        JLabel lblFecha = new JLabel("Fecha: ");
        lblFecha.setBounds(40, 10, 100, 25);
        pnlFechaEspecifica.add(lblFecha);

        dccDato = new DateChooserCombo();
        dccDato.setBounds(100, 10, 100, 25);
        pnlFechaEspecifica.add(dccDato);
        dccDato.setMinDate(fechaMin);
        dccDato.setMaxDate(fechaMax);

        pnlOscilacion = new JPanel();

        pnlClimaGeneral.add(pnlFechaEspecifica);
        pnlClimaGeneral.add(pnlOscilacion);

        // Componentes
        tpCambiosClima = new JTabbedPane();
        tpCambiosClima.addTab("Gráfica", pnlCambios);
        tpCambiosClima.addTab("Climas", pnlClimaGeneral);


        getContentPane().add(tb, BorderLayout.NORTH);
        getContentPane().add(tpCambiosClima, BorderLayout.CENTER);

    }
    
private void btnGraficarClick() {
    tpCambiosClima.setSelectedIndex(0);
    temperaturas = Filtrado.getDatos("temperaturas/datos/Temperaturas.csv");

    LocalDate desde = dccDesde.getSelectedDate().getTime()
            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate hasta = dccHasta.getSelectedDate().getTime()
            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Map<String, Double> promedios = Filtrado.getPromedioPorCiudad(temperaturas, desde, hasta);
    pnlGraficar.removeAll();
    ChartPanel grafico = Grafica.crearGrafico(promedios);
    pnlGraficar.setLayout(new BorderLayout());
    pnlGraficar.add(grafico, BorderLayout.CENTER);
    pnlGraficar.revalidate();
    pnlGraficar.repaint();
    }

    private void btnDatosClick(){
        tpCambiosClima.setSelectedIndex(1);
        temperaturas = Filtrado.getDatos("temperaturas/datos/Temperaturas.csv");

        LocalDate fecha = dccDato.getSelectedDate().getTime()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Map<String, Double> estadisticas = Filtrado.EstadisticasPorCiudad(temperaturas, fecha);
        
        pnlOscilacion.removeAll();
        pnlOscilacion.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Estadísticas de Temperaturas para la fecha: " 
                + fecha.format(DateTimeFormatter.ofPattern("d/M/yyyy")));
        titulo.setFont(new Font("Consolas", Font.BOLD, 14));
        pnlOscilacion.add(titulo, BorderLayout.NORTH);

        String texto = estadisticas.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("\n"));

        JTextArea datos = new JTextArea(texto);
        datos.setFont(new Font("Consolas", Font.PLAIN, 12));
        datos.setEditable(false);
        datos.setLineWrap(true);
        datos.setWrapStyleWord(true);
        pnlOscilacion.add(datos, BorderLayout.CENTER);
        pnlOscilacion.revalidate();
        pnlOscilacion.repaint();
        }
    }

