package ec.edu.espe.agrosmart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PublicidadService {

    private static final Logger log = LoggerFactory.getLogger(PublicidadService.class);

    private final AgroAiAssistant aiAssistant;

    public PublicidadService(AgroAiAssistant aiAssistant) {
        this.aiAssistant = aiAssistant;
    }

    public Mono<String> generarPublicidad(String producto, String audiencia) {
        return Mono.fromCallable(() -> aiAssistant.generarPublicidad(producto, audiencia))
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(ex -> {
                    log.error("Error al conectar con la IA de OpenAI: {}", ex.getMessage());
                    return Mono.just("¡Descubre la calidad superior de " + producto + " ideal para " + audiencia + "!");
                });
    }
}