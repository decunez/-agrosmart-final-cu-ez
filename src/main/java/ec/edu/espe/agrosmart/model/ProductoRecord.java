package ec.edu.espe.agrosmart.model;

import java.math.BigDecimal;
import java.util.List;

public record ProductoRecord(
        Long id,
        String nombre,
        BigDecimal precioUsd,
        Integer stockKg,
        String categoria,
        List<String> correosNotificacion
) {}