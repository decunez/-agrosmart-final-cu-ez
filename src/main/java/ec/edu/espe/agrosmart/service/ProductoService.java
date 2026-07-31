package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.ProductoRecord;
import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 1. Obtener productos comercializables reactivos
    public Flux<ProductoRecord> obtenerProductosComercializables() {
        return Mono.fromCallable(productoRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .filter(p -> "Flores".equalsIgnoreCase(p.getCategoria()))
                .filter(p -> p.getPrecio() != null && p.getPrecio() > 0.0)
                .filter(p -> p.getCorreosNotificacion() != null && !p.getCorreosNotificacion().isEmpty())
                .map(this::mapToRecord);
    }

    // 2. Buscar por ID
    public Mono<ProductoRecord> buscarPorId(Long id) {
        return Mono.fromCallable(() -> productoRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> Mono.justOrEmpty(optional))
                .map(this::mapToRecord)
                .switchIfEmpty(Mono.error(new ProductoNoEncontradoException(id)));
    }

    // 3. Obtener productos procesados para el análisis de IA (filtrados)
    public List<ProductoRecord> obtenerProductosProcesados() {
        return productoRepository.findAll().stream()
                .filter(p -> "Flores".equalsIgnoreCase(p.getCategoria()))
                .filter(p -> p.getPrecio() != null && p.getPrecio() > 0.0)
                .filter(p -> p.getCorreosNotificacion() != null && !p.getCorreosNotificacion().isEmpty())
                .map(this::mapToRecord)
                .toList();
    }

    private ProductoRecord mapToRecord(ProductoEntity entity) {
        return new ProductoRecord(
                entity.getId(),
                entity.getNombre(),
                entity.getCategoria(),
                entity.getPrecio(),
                entity.getStockKg(),
                entity.getCorreosNotificacion()
        );
    }
}