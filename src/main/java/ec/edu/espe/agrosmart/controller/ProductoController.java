package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.model.ProductoRecord;
import ec.edu.espe.agrosmart.service.AgroAiService;
import ec.edu.espe.agrosmart.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final AgroAiService agroAiService;

    public ProductoController(ProductoService productoService, AgroAiService agroAiService) {
        this.productoService = productoService;
        this.agroAiService = agroAiService;
    }

    @GetMapping("/procesados")
    public List<ProductoRecord> listarProcesados() {
        return productoService.obtenerProductosProcesados();
    }

    @GetMapping("/diagnostico-ia")
    public String obtenerDiagnosticoIa() {
        return agroAiService.ejecutarAnalisis();
    }
}