package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.domain.ProductoRecord;
import ec.edu.espe.agrosmart.service.ProductoService;
import ec.edu.espe.agrosmart.service.PublicidadService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AgroSmartController {

    private final ProductoService productoService;
    private final PublicidadService publicidadService;

    public AgroSmartController(ProductoService productoService, PublicidadService publicidadService) {
        this.productoService = productoService;
        this.publicidadService = publicidadService;
    }

    // 1. Obtener todos los productos comercializables
    @GetMapping("/api/productos")
    public Flux<ProductoRecord> obtenerProductosComercializables() {
        return productoService.obtenerProductosComercializables();
    }

    // 2. Buscar producto por ID
    @GetMapping("/api/productos/{id}")
    public Mono<ProductoRecord> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    // 3. Generar frase publicitaria
    @GetMapping(value = "/api/agrosmart/publicidad", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> generarPublicidad(
            @RequestParam String producto,
            @RequestParam String audiencia) {
        return publicidadService.generarPublicidad(producto, audiencia);
    }
}