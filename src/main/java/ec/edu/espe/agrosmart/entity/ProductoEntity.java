package ec.edu.espe.agrosmart.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_productos_base_86")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto") // <--- Vincula 'nombre' con la columna de PostgreSQL
    private String nombre;

    private String categoria;

    private Double precio;

    @Column(name = "stock_kg")
    private Integer stockKg;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> correosNotificacion;

    public ProductoEntity() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStockKg() {
        return stockKg;
    }

    public void setStockKg(Integer stockKg) {
        this.stockKg = stockKg;
    }

    public List<String> getCorreosNotificacion() {
        return correosNotificacion;
    }

    public void setCorreosNotificacion(List<String> correosNotificacion) {
        this.correosNotificacion = correosNotificacion;
    }
}