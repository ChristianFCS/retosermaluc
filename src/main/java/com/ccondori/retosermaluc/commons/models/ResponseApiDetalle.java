package com.ccondori.retosermaluc.commons.models;

public class ResponseApiDetalle {
    private String mensaje;

    public ResponseApiDetalle(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
