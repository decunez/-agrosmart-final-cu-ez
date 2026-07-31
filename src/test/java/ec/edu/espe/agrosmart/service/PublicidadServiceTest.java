package ec.edu.espe.agrosmart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicidadServiceTest {

    @Mock
    private AgroAiAssistant aiAssistant;

    @InjectMocks
    private PublicidadService publicidadService;

    @Test
    @DisplayName("generarPublicidad retorna la frase generada por la IA cuando no hay errores")
    void generarPublicidad_Exito() {
        when(aiAssistant.generarPublicidad("Rosas", "Exportadores"))
                .thenReturn("¡Rosas de alta calidad para exportación!");

        StepVerifier.create(publicidadService.generarPublicidad("Rosas", "Exportadores"))
                .expectNext("¡Rosas de alta calidad para exportación!")
                .verifyComplete();
    }

    @Test
    @DisplayName("generarPublicidad activa el fallback onErrorResume si la IA falla")
    void generarPublicidad_CuandoFallaIA_RetornaMensajeFallback() {
        when(aiAssistant.generarPublicidad(anyString(), anyString()))
                .thenThrow(new RuntimeException("Error de cuota o red de OpenAI"));

        StepVerifier.create(publicidadService.generarPublicidad("Rosas", "Exportadores"))
                .expectNextMatches(res -> res.contains("Descubre la calidad superior de Rosas"))
                .verifyComplete();
    }
}