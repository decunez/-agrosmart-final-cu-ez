package ec.edu.espe.agrosmart.config;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                String cat = "Flores";

                // 3 Productos válidos para la categoría Flores
                ProductoEntity p1 = new ProductoEntity(null, "Rosas Rojas Exportación", new BigDecimal("12.50"), 500, cat, "ventas@flores.ec, export@flores.ec");
                ProductoEntity p2 = new ProductoEntity(null, "Girasoles Cayambe", new BigDecimal("8.00"), 300, cat, "contacto@flores.ec");
                ProductoEntity p3 = new ProductoEntity(null, "Orquídeas Mindo Premium", new BigDecimal("22.00"), 120, cat, "info@flores.ec");

                // 2 Productos inválidos (1 con precio cero, 1 sin correos)
                ProductoEntity p4 = new ProductoEntity(null, "Muestra Ramo Pruebas", BigDecimal.ZERO, 50, cat, "muestra@flores.ec");
                ProductoEntity p5 = new ProductoEntity(null, "Flores Reserva Sin Notif", new BigDecimal("15.00"), 80, cat, "");

                repository.saveAll(List.of(p1, p2, p3, p4, p5));
            }
        };
    }
}