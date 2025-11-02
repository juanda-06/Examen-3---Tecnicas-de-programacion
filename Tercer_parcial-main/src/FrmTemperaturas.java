import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;

import Servicios.Filtrado;
import Servicios.Grafica;
import entidades.DatosTemperaturas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import datechooser.beans.DateChooserCombo;

public class FrmTemperaturas extends JFrame {

    private JComboBox cmbCiudad;
    private DateChooserCombo dccDesde, dccHasta;
    private JTabbedPane tpCiudadClima;
    private JPanel pnlGrafica;
    private JPanel pnlOscilacion;
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
            public void ActionListener(ActionEvent evt) {
                btnDatosClick();
            }
        });
        tb.add(btnDatos);

        JPanel pnlCambios = new JPanel();
        pnlCambios.setLayout(new BoxLayout(pnlCambios, BoxLayout.Y_AXIS));

        JPanel pnlDatosProceso = new JPanel();
        pnlDatosProceso.setPreferredSize(new Dimension(pnlDatosProceso.getWidth(), 50)); // Altura fija de 100px
        pnlDatosProceso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pnlDatosProceso.setLayout(null);

        JLabel lblCiudad = new JLabel("Ciudad: ");
        lblCiudad.setBounds(40, 10, 100, 25);
        pnlDatosProceso.add(lblCiudad);

        cmbCiudad = new JComboBox();
        cmbCiudad.setBounds(100, 10, 100, 25);
        pnlDatosProceso.add(cmbCiudad);

        dccDesde = new DateChooserCombo();
        dccDesde.setBounds(330, 10, 100, 25);
        pnlDatosProceso.add(dccDesde);

        dccHasta = new DateChooserCombo();
        dccHasta.setBounds(220, 10, 100, 25);
        pnlDatosProceso.add(dccHasta);

        // FECHAS DISPONIBLES
        //Calendar fechaInicio = new GregorianCalendar(2025, Calendar.JANUARY, 1);
        //Calendar fechaFin = new GregorianCalendar(2025, Calendar.FEBRUARY, 28);
        //dccDesde.setMinDate(fechaInicio); dccDesde.setMaxDate(fechaFin);
        //dccHasta.setMinDate(fechaInicio); dccHasta.setMaxDate(fechaFin);

        pnlGrafica = new JPanel();
        JScrollPane spGrafica = new JScrollPane(pnlGrafica);

        pnlOscilacion = new JPanel();


        tpCiudadClima = new JTabbedPane();
        tpCiudadClima.addTab("Gráfica", spGrafica);
        tpCiudadClima.addTab("Oscilación", pnlOscilacion);





        pnlCambios.add(tpCiudadClima);
        pnlCambios.add(pnlDatosProceso);
        getContentPane().add(tb, BorderLayout.NORTH);
        getContentPane().add(pnlCambios, BorderLayout.CENTER);
}
    
    private void btnGraficarClick() {
    }

    private void btnDatosClick(){
    }
}
