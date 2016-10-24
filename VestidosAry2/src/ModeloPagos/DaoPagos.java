/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloPagos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoPagos {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;

    public boolean registrar(Pagos bean) {
        boolean ban = false;
        String sql = "insert into pagos(abono,fecharegistro,deudatotal_id,usuario_id)"
                + "values(?,GETDATE(),?,'" + bean.getUsuario_id() + "')";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, bean.getAbono());
            ps.setInt(2, bean.getDeudaTotal_id());
            ban = ps.executeUpdate() == 1;
            ban = true;

        } catch (Exception e) {
            System.out.println("Mensaje daoPAgos registrar " + e);
        }

        return ban;
    }

    //Obtener el ultimo registro en deuda total
    public int obtener_id_deudaTotal() {
        int ultimo_id = 0;
        String sql = "select top 1 * from deudatotal\n"
                + "order by iddeudatotal desc;";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ultimo_id = rs.getInt("iddeudatotal");
            }
        } catch (Exception e) {
            System.out.println("Mensaje  Dao pagos  obtener_id_deudaTotal " + e);
        }
        return ultimo_id;
    }

   //obtener la suma de de todos los abonos del cliente
public int sumaabonos(int iddeudaTotal){
    int sumaPagos=0;
    String sql="select abono from pagos where deudatotal_id=?";
    try {
        con=conexion.getConnection();
        ps=con.prepareStatement(sql);
        ps.setInt(1, iddeudaTotal);
        rs=ps.executeQuery();
        while(rs.next()){
            sumaPagos=sumaPagos+rs.getInt("abono");
        }
    } catch (Exception e) {
        System.out.println("Mensaje DaoPagos sumaAbonos");
    }
    return sumaPagos;
}
}
