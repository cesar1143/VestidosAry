/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosApartados;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class ProductosApartados {
    private int idProductosApartados,producto_id,cliente_id;
    private String fechaRegistro,fechaEntrega,status;
    private Date fechaRegistroDate, fechaEntregaDate;
    public static int usuario_id;
    public int getIdProductosApartados() {
        return idProductosApartados;
    }

    public void setIdProductosApartados(int idProductosApartados) {
        this.idProductosApartados = idProductosApartados;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFechaRegistroDate() {
        return fechaRegistroDate;
    }

    public void setFechaRegistroDate(Date fechaRegistroDate) {
        this.fechaRegistroDate = fechaRegistroDate;
    }

    public Date getFechaEntregaDate() {
        return fechaEntregaDate;
    }

    public void setFechaEntregaDate(Date fechaEntregaDate) {
        this.fechaEntregaDate = fechaEntregaDate;
    }
    
    
    
}
