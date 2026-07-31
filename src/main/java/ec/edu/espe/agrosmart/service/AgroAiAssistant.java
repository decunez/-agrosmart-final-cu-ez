package ec.edu.espe.agrosmart.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AgroAiAssistant {

    @SystemMessage("""
        Eres un asistente agroindustrial experto en la categoría Flores.
        Tu trabajo es analizar el listado de productos procesados y generar un diagnóstico breve,
        profesional y conciso en español con recomendaciones sobre precios y stock.
        """)
    String generarDiagnostico(@UserMessage String datosProductos);
}