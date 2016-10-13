/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloMedidas;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Medidas {
    private int idMedidas,productosApartados_id,producto_id;
    private double talle,sise,hombros,busto,largoFalda,AnchoPuño,cintura,cadera;
    private String fechaRegistro;
    private Date fechaRegistroDate;

    public Medidas() {
        
        this.productosApartados_id=0;
        this.producto_id=0;
        this.talle=0.0;
        this.sise=0.0;
        this.hombros=0.0;
        this.busto=0.0;
        this.largoFalda=0.0;
        this.AnchoPuño=0.0;
        this.cintura=0.0;
        this.cadera=0.0;
    }

    public Medidas(int producto_id, double talle, double sise, double hombros, double busto, double largoFalda, double AnchoPuño, double cintura, double cadera) {
        this.producto_id = producto_id;
        this.talle = talle;
        this.sise = sise;
        this.hombros = hombros;
        this.busto = busto;
        this.largoFalda = largoFalda;
        this.AnchoPuño = AnchoPuño;
        this.cintura = cintura;
        this.cadera = cadera;
    }


    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }
    
    public int getIdMedidas() {
        return idMedidas;
    }

    public void setIdMedidas(int idMedidas) {
        this.idMedidas = idMedidas;
    }

    public int getProductosApartados_id() {
        return productosApartados_id;
    }

    public void setProductosApartados_id(int productosApartados_id) {
        this.productosApartados_id = productosApartados_id;
    }

    public double getTalle() {
        return talle;
    }

    public void setTalle(double talle) {
        this.talle = talle;
    }

    public double getSise() {
        return sise;
    }

    public void setSise(double sise) {
        this.sise = sise;
    }

    public double getHombros() {
        return hombros;
    }

    public void setHombros(double hombros) {
        this.hombros = hombros;
    }

    public double getBusto() {
        return busto;
    }

    public void setBusto(double busto) {
        this.busto = busto;
    }

    public double getLargoFalda() {
        return largoFalda;
    }

    public void setLargoFalda(double largoFalda) {
        this.largoFalda = largoFalda;
    }

    public double getAnchoPuño() {
        return AnchoPuño;
    }

    public void setAnchoPuño(double AnchoPuño) {
        this.AnchoPuño = AnchoPuño;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public double getCadera() {
        return cadera;
    }

    public void setCadera(double cadera) {
        this.cadera = cadera;
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
