/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDeudaTotal;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class DeudaTotal {
    private int idDeudaTotal,deudaTotal,cliente_id;
    private String status;
    private String fechaRegistro;
    private Date fechaRegistroDate;

    public int getIdDeudaTotal() {
        return idDeudaTotal;
    }

    public void setIdDeudaTotal(int idDeudaTotal) {
        this.idDeudaTotal = idDeudaTotal;
    }

    public int getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(int deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
