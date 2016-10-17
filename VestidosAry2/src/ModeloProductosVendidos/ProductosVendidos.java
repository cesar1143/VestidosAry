/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosVendidos;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class ProductosVendidos {
    private int idproductoVendidos , productosApartados_id;
    private String fechaRegistro;
    private Date fechaRegistroDate;

    public int getIdproductoVendidos() {
        return idproductoVendidos;
    }

    public void setIdproductoVendidos(int idproductoVendidos) {
        this.idproductoVendidos = idproductoVendidos;
    }

    public int getProductosApartados_id() {
        return productosApartados_id;
    }

    public void setProductosApartados_id(int productosApartados_id) {
        this.productosApartados_id = productosApartados_id;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaRegistroDate() {
        return fechaRegistroDate;
    }

    public void setFechaRegistroDate(Date fechaRegistroDate) {
        this.fechaRegistroDate = fechaRegistroDate;
    }
    
    
    
    
}
