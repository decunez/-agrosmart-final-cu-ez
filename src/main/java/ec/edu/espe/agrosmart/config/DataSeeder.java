package ec.edu.espe.agrosmart.config;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            // Limpia los datos corruptos anteriores para forzar la recarga
            repository.deleteAll();

            String cat = "Flores";

            // 3 Productos válidos para la categoría Flores
            ProductoEntity p1 = crearProducto("Rosas Rojas Exportación", cat, 12.50, 500, List.of("ventas@flores.ec", "export@flores.ec"));
            ProductoEntity p2 = crearProducto("Girasoles Cayambe", cat, 8.00, 300, List.of("contacto@flores.ec"));
            ProductoEntity p3 = crearProducto("Orquídeas Mindo Premium", cat, 22.00, 120, List.of("info@flores.ec"));

            // 2 Productos inválidos
            ProductoEntity p4 = crearProducto("Muestra Ramo Pruebas", cat, 0.0, 50, List.of("muestra@flores.ec"));
            ProductoEntity p5 = crearProducto("Flores Reserva Sin Notif", cat, 15.00, 80, Collections.emptyList());

            repository.saveAll(List.of(p1, p2, p3, p4, p5));
        };
    }

    private ProductoEntity crearProducto(String nombre, String categoria, Double precio, Integer stockKg, List<String> correos) {
        ProductoEntity p = new ProductoEntity();
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecio(precio);
        p.setStockKg(stockKg);
        p.setCorreosNotificacion(correos);
        return p;
    }
}