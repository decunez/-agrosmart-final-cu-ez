package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.model.ProductoRecord;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    /**
     * Mapea una Entidad JPA a un Record Inmutable.
     * Transforma la cadena de correos separada por comas en una List<String>.
     */
    public ProductoRecord mapToRecord(ProductoEntity entity) {
        List<String> correosList = (entity.getCorreosNotificacion() == null || entity.getCorreosNotificacion().isBlank())
                ? Collections.emptyList()
                : Arrays.stream(entity.getCorreosNotificacion().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new ProductoRecord(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getPrecioUsd(),
                entity.getStockKg(),
                entity.getCategoria(),
                correosList
        );
    }

    /**
     * Pipeline Funcional (Streams API):
     * 1. Carga todas las entidades.
     * 2. Mapea a Record inmutable.
     * 3. Filtra la categoría "Flores".
     * 4. Filtra reglas de negocio: precio > 0 y al menos 1 correo de notificación.
     */
    public List<ProductoRecord> obtenerProductosProcesados() {
        return repository.findAll().stream()
                .map(this::mapToRecord)
                .filter(p -> "Flores".equalsIgnoreCase(p.categoria()))
                .filter(p -> p.precioUsd() != null && p.precioUsd().compareTo(BigDecimal.ZERO) > 0)
                .filter(p -> p.correosNotificacion() != null && !p.correosNotificacion().isEmpty())
                .collect(Collectors.toList());
    }
}