/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDeudaTotal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoDeudaTotal {
     PreparedStatement ps;
    ResultSet rs;
    Connection con = null;
    
    public boolean registrar(DeudaTotal bean){
        boolean ban=false;
        String sql="insert into deudatotal(deudatotal,status,fecharegistro,cliente_id)"
                + "values(?,?,GETDATE(),?)";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, bean.getDeudaTotal());
            ps.setString(2, bean.getStatus());
            ps.setInt(3, bean.getCliente_id());
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
            System.out.println("Mensaje daoDeudaTotal registrar " + e);
        }
        return ban;
    }
    
    public DeudaTotal consultarDeuda(int idCliente){
        DeudaTotal bean= new DeudaTotal();
        String sql="select * from deudatotal  where deudatotal.cliente_id=? and deudatotal.status='No pagado';";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs=ps.executeQuery();
            while(rs.next()){
                bean.setIdDeudaTotal(rs.getInt(1));
                bean.setDeudaTotal(rs.getInt(2));
                bean.setStatus(rs.getString(3));
                
                
                
            }
            
        } catch (Exception e) {
             System.out.println("Mensaje daoDeudaTotal consultarDeuda " + e);
        }
        
    return bean;
    }
    
    public boolean modificarDeuda(int nuevadeuda,int iddeudaTotal){
        boolean ban=false;
        String sql="update deudatotal set deudatotal=? where iddeudatotal=?";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, nuevadeuda);
            ps.setInt(2, iddeudaTotal);
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
        }
        return ban;
    }
}
