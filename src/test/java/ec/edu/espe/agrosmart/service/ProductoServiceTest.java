package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private ProductoEntity productoValido;
    private ProductoEntity productoPrecioCero;
    private ProductoEntity productoSinCorreos;

    @BeforeEach
    void setUp() {
        productoValido = new ProductoEntity();
        productoValido.setId(1L);
        productoValido.setNombre("Rosas Rojas");
        productoValido.setCategoria("Flores");
        productoValido.setPrecio(12.50);
        productoValido.setStockKg(500);
        productoValido.setCorreosNotificacion(List.of("ventas@flores.ec"));

        productoPrecioCero = new ProductoEntity();
        productoPrecioCero.setId(2L);
        productoPrecioCero.setNombre("Muestra Pruebas");
        productoPrecioCero.setCategoria("Flores");
        productoPrecioCero.setPrecio(0.0);
        productoPrecioCero.setStockKg(50);
        productoPrecioCero.setCorreosNotificacion(List.of("prueba@flores.ec"));

        productoSinCorreos = new ProductoEntity();
        productoSinCorreos.setId(3L);
        productoSinCorreos.setNombre("Girasoles Sin Notif");
        productoSinCorreos.setCategoria("Flores");
        productoSinCorreos.setPrecio(8.00);
        productoSinCorreos.setStockKg(100);
        productoSinCorreos.setCorreosNotificacion(Collections.emptyList());
    }

    @Test
    @DisplayName("obtenerProductosComercializables solo debe retornar productos que cumplan las reglas de negocio")
    void obtenerProductosComercializables_FiltraProductosInvalidos() {
        when(productoRepository.findAll()).thenReturn(List.of(productoValido, productoPrecioCero, productoSinCorreos));

        StepVerifier.create(productoService.obtenerProductosComercializables())
                .expectNextMatches(p -> p.id().equals(1L) && p.nombre().equals("Rosas Rojas"))
                .verifyComplete();
    }

    @Test
    @DisplayName("buscarPorId retorna Mono con el ProductoRecord si el ID existe")
    void buscarPorId_CuandoExiste_DevuelveProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoValido));

        StepVerifier.create(productoService.buscarPorId(1L))
                .expectNextMatches(p -> p.id().equals(1L) && p.precio() == 12.50)
                .verifyComplete();
    }

    @Test
    @DisplayName("buscarPorId emite ProductoNoEncontradoException si el ID no existe")
    void buscarPorId_CuandoNoExiste_LanzaExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        StepVerifier.create(productoService.buscarPorId(99L))
                .expectError(ProductoNoEncontradoException.class)
                .verify();
    }
}