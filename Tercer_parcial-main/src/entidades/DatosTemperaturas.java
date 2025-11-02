package entidades;

import java.time.LocalDate;

public class DatosTemperaturas {

    private String ciudad;
    private LocalDate fecha;
    private double temperatura;

    public DatosTemperaturas(String ciudad, LocalDate fecha, double temperatura) {
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.temperatura = temperatura;
    }

    public String getCiudad() {
        return ciudad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTemperatura() {
        return temperatura;
    }

}
