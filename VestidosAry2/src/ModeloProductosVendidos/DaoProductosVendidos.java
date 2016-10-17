/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosVendidos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoProductosVendidos {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;

    public boolean registrar(ProductosVendidos bean) {
        boolean ban = false;
        String sql="insert into productosvendidos (fecharegistro,productosapartados_id)"
                + "values(GETDATE(),?)";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1,bean.getProductosApartados_id());
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
            System.out.println("Mensaje dao Producto vendidos registrar " + e);
        }

        return ban;
    }

}
