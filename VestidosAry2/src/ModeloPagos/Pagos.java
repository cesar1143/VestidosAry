/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloPagos;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Pagos {
    private int idPagos,abono,deudaTotal_id;
    private String fechaRegistro,status;
    private Date fechaRegistroDate;
    public static int usuario_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    
    public int getIdPagos() {
        return idPagos;
    }

    public void setIdPagos(int idPagos) {
        this.idPagos = idPagos;
    }

    public int getAbono() {
        return abono;
    }

    public void setAbono(int abono) {
        this.abono = abono;
    }

    public int getDeudaTotal_id() {
        return deudaTotal_id;
    }

    public void setDeudaTotal_id(int deudaTotal_id) {
        this.deudaTotal_id = deudaTotal_id;
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

    public Date getFechaRegistroDate() {
        return fechaRegistroDate;
    }

    public void setFechaRegistroDate(Date fechaRegistroDate) {
        this.fechaRegistroDate = fechaRegistroDate;
    }
    
    
    
    
}
