/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosApartados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoProductosApartados {
    PreparedStatement ps;
    ResultSet rs;
    Connection con=null;
    
    public boolean registrarVenta(ProductosApartados bean){
        boolean ban=false;
     String sql="insert into productosapartados (producto_id,cliente_id,fecharegistro,fechaentrega,status,usuario_id)"
             + "values (?,?,GETDATE(),?,?,'"+bean.getUsuario_id()+"')";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, bean.getProducto_id());
            ps.setInt(2,bean.getCliente_id());
            ps.setString(3, bean.getFechaEntrega());
            ps.setString(4, bean.getStatus());
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
            System.out.println("Mensaje DaoProductosApartados registrarVenta " + e);
        }
        return ban;
    }
}
