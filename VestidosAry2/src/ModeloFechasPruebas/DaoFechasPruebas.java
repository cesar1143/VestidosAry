/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloFechasPruebas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoFechasPruebas {
    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;
    
    public boolean registrar(FechasPruebas bean){
        boolean ban=false;
       String sql="insert into fechaspruebas(fechaprueba,fecharegistro,productosapartados_id)"
               + "values(?,GETDATE(),?)";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, bean.getFechaPrueba());
            ps.setInt(2,bean.getProductosApartados_id());
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
        }
        return ban;
    }
}
