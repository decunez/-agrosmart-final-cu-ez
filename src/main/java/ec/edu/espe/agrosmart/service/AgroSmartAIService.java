package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.ProductoRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgroSmartAIService {

    private final ProductoService productoService;
    private final AgroAiAssistant aiAssistant;

    public AgroSmartAIService(ProductoService productoService, AgroAiAssistant aiAssistant) {
        this.productoService = productoService;
        this.aiAssistant = aiAssistant;
    }

    public String ejecutarAnalisis() {
        List<ProductoRecord> productos = productoService.obtenerProductosProcesados();

        if (productos.isEmpty()) {
            return "No existen productos válidos en la categoría Flores para analizar.";
        }

        String contexto = productos.stream()
                .map(p -> String.format("- %s | Precio: $%.2f | Stock: %d kg | Correos: %s",
                        p.nombre(), p.precio(), p.stockKg(), String.join("; ", p.correosNotificacion()))) // <--- Se cambió p.precioUsd() por p.precio()
                .collect(Collectors.joining("\n"));

        return aiAssistant.generarDiagnostico(contexto);
    }
}