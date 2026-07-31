package ec.edu.espe.agrosmart.exception;

public enum ErrorCodigo {
    PRODUCTO_NO_ENCONTRADO("ERR-001", "El producto solicitado no existe"),
    ERROR_INTERNO("ERR-500", "Error interno en el servidor");

    private final String codigo;
    private final String mensaje;

    ErrorCodigo(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
}