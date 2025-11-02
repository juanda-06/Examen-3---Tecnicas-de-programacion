package Servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entidades.DatosTemperaturas;

public class Filtrado {

    public static List<DatosTemperaturas> getDatos(String nombreArchivo) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            Stream<String> lineas = Files.lines(Paths.get(nombreArchivo));

            return lineas.skip(1)
                    .map(linea -> linea.split(","))
                    .map(textos -> new DatosTemperaturas(textos[0],
                            LocalDate.parse(textos[1], formatoFecha),
                            Double.parseDouble(textos[2])))
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            System.out.println(ex);
            return Collections.emptyList();
        }
    }

    public static List<String> getCiudades(List<DatosTemperaturas> temperaturas) {
        return temperaturas.stream()
                .map(DatosTemperaturas::getCiudad)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<DatosTemperaturas> filtrar(List<DatosTemperaturas> temperaturas,
            String ciudad, LocalDate desde, LocalDate hasta) {
        return temperaturas.stream()
                .filter(item -> item.getCiudad().equals(ciudad) &&
                        !(item.getFecha().isAfter(hasta) || item.getFecha().isBefore(desde)))
                .collect(Collectors.toList());
    }

    public static Map<LocalDate, Double> extraerDatosGrafica(Object datosFiltrados) {
        return datosFiltrados.stream()
                .collect(Collectors.toMap(DatosTemperaturas::getFecha, DatosTemperaturas::getTemperatura));
    }

    public static double getPromedio(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double getMaximo(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }

    public static double getMinimo(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }

    public static Map<String, Double> getEstadisticas(List<DatosTemperaturas> temperaturas,
            String ciudad, LocalDate desde, LocalDate hasta) {
                var datosFiltrados=filtrar(temperaturas, ciudad, desde, hasta);
                var temperatura=datosFiltrados.stream().map(DatosTemperaturas::getTemperatura).collect(Collectors.toList());

        Map<String, Double> estadisticas = new LinkedHashMap<>();
        estadisticas.put("Promedio", getPromedio(temperatura));
        estadisticas.put("Máximo", getMaximo(temperatura));
        estadisticas.put("Mínimo", getMinimo(temperatura));
        return estadisticas;
    }

    public static Map<String, Double> getPromedioPorCiudad(List<DatosTemperaturas> temperaturas,
        LocalDate desde, LocalDate hasta) {

    return temperaturas.stream()
            .filter(t -> !(t.getFecha().isBefore(desde) || t.getFecha().isAfter(hasta)))
            .collect(Collectors.groupingBy(
                    DatosTemperaturas::getCiudad,
                    Collectors.collectingAndThen(
                            Collectors.mapping(DatosTemperaturas::getTemperatura, Collectors.toList()),
                            Filtrado::getPromedio // usa tu método existente
                    )
            ));
}

}
