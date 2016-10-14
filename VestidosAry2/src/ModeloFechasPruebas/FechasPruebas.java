/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloFechasPruebas;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class FechasPruebas {
    private int idFechasPruebas,productosApartados_id;
    private String fechaPrueba,fechaRegistro;
    private Date fechaPruebaDate,fechaRegistroDate;

    public int getIdFechasPruebas() {
        return idFechasPruebas;
    }

    public void setIdFechasPruebas(int idFechasPruebas) {
        this.idFechasPruebas = idFechasPruebas;
    }

    public int getProductosApartados_id() {
        return productosApartados_id;
    }

    public void setProductosApartados_id(int productosApartados_id) {
        this.productosApartados_id = productosApartados_id;
    }

    public String getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(String fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaPruebaDate() {
        return fechaPruebaDate;
    }

    public void setFechaPruebaDate(Date fechaPruebaDate) {
        this.fechaPruebaDate = fechaPruebaDate;
    }

    public Date getFechaRegistroDate() {
        return fechaRegistroDate;
    }

    public void setFechaRegistroDate(Date fechaRegistroDate) {
        this.fechaRegistroDate = fechaRegistroDate;
    }
    
    
    
    
}
