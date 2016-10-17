/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloMedidas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoMedidas {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;
    

    public boolean registrar(Medidas bean) {
        boolean ban = false;
        String sql = "insert into medidas(talle,sise,hombros,busto,largoFalda,anchoPuño,cintura,cadera,fecharegistro,productosapartados_id)"
                + "values(?,?,?,?,?,?,?,?,GETDATE(),?)";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, bean.getTalle());
            ps.setDouble(2, bean.getSise());
            ps.setDouble(3, bean.getHombros());
            ps.setDouble(4, bean.getBusto());
            ps.setDouble(5, bean.getLargoFalda());
            ps.setDouble(6, bean.getAnchoPuño());
            ps.setDouble(7, bean.getCintura());
            ps.setDouble(8, bean.getCadera());
            ps.setInt(9, bean.getProductosApartados_id());
            ban = ps.executeUpdate() == 1;
            ban = true;
        } catch (Exception e) {
            System.out.println("Mensaje Dao medidas registrar " + e);
        }
        return ban;

    }

    //Obtener el ultimo registro en apartados para registrar 

    public int obtener_id() {
        int ultimo_id = 0;
        String sql = "SELECT TOP 1 * FROM productosapartados \n"
                + "ORDER BY idproductosapartados DESC ";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ultimo_id = rs.getInt("idproductosapartados");
            }
        } catch (Exception e) {
        }
        return ultimo_id;
    }

}
