package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.List;

public record ProductoRecord(
        Long id,
        String nombre,
        String categoria,
        Double precio,
        Integer stockKg,
        List<String> correosNotificacion
) {}