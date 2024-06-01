package com.ccondori.retosermaluc.commons.models;

import org.springframework.http.HttpStatus;

public class ResponseApi<T> {

    private Boolean estado;
    private Integer status;
    private String mensaje;
    private ResponseApiPayload<T> payload;
    private ResponseApiDetalle detalle;

    public ResponseApi() {
        this.status=HttpStatus.OK.value();
        this.estado=true;
        this.mensaje="El proceso se completó satisfactoriamente.";
        this.payload= new ResponseApiPayload<>();
    }


    public ResponseApi<?> responseError(String mensaje,String mensajeDetalle) {

        this.status=HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.estado=false;
        this.mensaje=mensaje;
        detalle = new ResponseApiDetalle(mensajeDetalle);
        return this;
    }

    public static <G> ResponseApi<G> build(G g){
        ResponseApi<G> responseApi = new ResponseApi<G>();
        responseApi.getPayload().setEstado(true);
        responseApi.getPayload().setMensaje("Proceso completado con éxito.");
        responseApi.getPayload().setBody(g);
        return responseApi;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ResponseApiPayload<T> getPayload() {
        return payload;
    }

    public void setPayload(ResponseApiPayload<T> payload) {
        this.payload = payload;
    }

    public ResponseApiDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(ResponseApiDetalle detalle) {
        this.detalle = detalle;
    }
}
