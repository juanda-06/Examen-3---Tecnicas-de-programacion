package Servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entidades.DatosTemperaturas;
import java.io.IOException;

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

        } catch (IOException ex) {
            System.out.println("Error al leer el archivo: " + ex.getMessage());
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

    public static Map<LocalDate, Double> extraerDatosGrafica(Collection<DatosTemperaturas> datosFiltrados) {
        return datosFiltrados.stream()
                .collect(Collectors.toMap(DatosTemperaturas::getFecha, DatosTemperaturas::getTemperatura));
    }

    public static double getPromedio(Map<String,Double> tempPorCiudad) {
        return tempPorCiudad.isEmpty() ? 0 : tempPorCiudad.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double getMaximo(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }

    public static double getMinimo(List<Double> datos) {
        return datos.isEmpty() ? 0 : datos.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }

public static Map<String, Double> EstadisticasPorCiudad(
    List<DatosTemperaturas> temperaturas,
    LocalDate fecha) {

    Map<String, List<Double>> datosPorCiudad = temperaturas.stream()
        .filter(item -> item.getFecha().isEqual(fecha))
        .collect(Collectors.groupingBy(
            DatosTemperaturas::getCiudad,
            Collectors.mapping(DatosTemperaturas::getTemperatura, Collectors.toList())
        ));

    var maxCiudad = datosPorCiudad.entrySet().stream()
        .map(e -> Map.entry(e.getKey(), e.getValue().stream().max(Double::compareTo).orElse(Double.NaN)))
        .max(Map.Entry.comparingByValue())
        .orElse(null);

    var minCiudad = datosPorCiudad.entrySet().stream()
        .map(e -> Map.entry(e.getKey(), e.getValue().stream().min(Double::compareTo).orElse(Double.NaN)))
        .min(Map.Entry.comparingByValue())
        .orElse(null);

    Map<String, Double> estadisticas = new LinkedHashMap<>();
    estadisticas.put("Ciudad más calurosa: " + maxCiudad.getKey(), maxCiudad.getValue());
    estadisticas.put("Ciudad más fría: " + minCiudad.getKey(), minCiudad.getValue());

    return estadisticas;
}


    public static Map<String, Double> getPromedioPorCiudad(
        List<DatosTemperaturas> temperaturas,
        LocalDate desde, LocalDate hasta) {

        return temperaturas.stream()
            .filter(item -> 
                !item.getFecha().isBefore(desde) &&
                !item.getFecha().isAfter(hasta))
            .collect(Collectors.groupingBy(
                DatosTemperaturas::getCiudad,
                Collectors.averagingDouble(DatosTemperaturas::getTemperatura)
            ));
    }

    }
