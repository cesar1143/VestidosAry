/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloPendientes;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Pendientes {

    private int idProductosApartados, producto_id, cliente_id;
    private String fechaRegistro, fechaEntrega, status;
    private Date fechaRegistroDate, fechaEntregaDate;
    public static int usuario_id;
    private int idClientes, telefono;
    private String nombre, apaterno, amaterno;
    public static int usuarios_id;
      private int idProductos,precio;
    private String clave,color,tipo,fecha,fotoStrin;
    private Date fechaDate;
    private byte[] foto;

    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFotoStrin() {
        return fotoStrin;
    }

    public void setFotoStrin(String fotoStrin) {
        this.fotoStrin = fotoStrin;
    }

    public Date getFechaDate() {
        return fechaDate;
    }

    public void setFechaDate(Date fechaDate) {
        this.fechaDate = fechaDate;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
   

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

    public static int getUsuario_id() {
        return usuario_id;
    }

    public static void setUsuario_id(int usuario_id) {
        Pendientes.usuario_id = usuario_id;
    }

    public int getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(int idClientes) {
        this.idClientes = idClientes;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public static int getUsuarios_id() {
        return usuarios_id;
    }

    public static void setUsuarios_id(int usuarios_id) {
        Pendientes.usuarios_id = usuarios_id;
    }

    @Override
    public String toString() {
        return String.valueOf(idProductosApartados)+ nombre +  apaterno + clave + color + String.valueOf(precio) + tipo + status;
    }

    

}
