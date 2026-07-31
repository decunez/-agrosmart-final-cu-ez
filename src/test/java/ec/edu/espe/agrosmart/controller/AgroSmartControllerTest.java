package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.domain.ProductoRecord;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.service.ProductoService;
import ec.edu.espe.agrosmart.service.PublicidadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgroSmartControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private PublicidadService publicidadService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        AgroSmartController controller = new AgroSmartController(productoService, publicidadService);
        this.webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    @DisplayName("GET /api/productos retorna HTTP 200 y la lista reactiva de productos")
    void obtenerProductosComercializables_Retorna200() {
        ProductoRecord producto = new ProductoRecord(1L, "Rosas", "Flores", 12.50, 500, List.of("v@flores.ec"));
        when(productoService.obtenerProductosComercializables()).thenReturn(Flux.just(producto));

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductoRecord.class)
                .hasSize(1);
    }

    @Test
    @DisplayName("GET /api/productos/{id} retorna HTTP 200 cuando el producto existe")
    void buscarPorId_Existente_Retorna200() {
        ProductoRecord producto = new ProductoRecord(1L, "Rosas", "Flores", 12.50, 500, List.of("v@flores.ec"));
        when(productoService.buscarPorId(1L)).thenReturn(Mono.just(producto));

        webTestClient.get()
                .uri("/api/productos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Rosas");
    }

    @Test
    @DisplayName("GET /api/agrosmart/publicidad retorna texto plano con HTTP 200")
    void generarPublicidad_Retorna200TextoPlano() {
        when(publicidadService.generarPublicidad("Rosas", "Floristerias"))
                .thenReturn(Mono.just("¡Rosas hermosas a precio mayorista!"));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/agrosmart/publicidad")
                        .queryParam("producto", "Rosas")
                        .queryParam("audiencia", "Floristerias")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/plain;charset=UTF-8")
                .expectBody(String.class).isEqualTo("¡Rosas hermosas a precio mayorista!");
    }
}