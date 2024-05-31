package com.ccondori.retosermaluc.commons.models;

public class ResponseApiPayload<T> {
    private Boolean estado;
    private String mensaje;
    private T body;

    public ResponseApiPayload() {
    }

    public ResponseApiPayload(Boolean estado, String mensaje, T body) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.body = body;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
