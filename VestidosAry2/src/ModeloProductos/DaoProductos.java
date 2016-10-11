/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductos;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoProductos {
    Connection con=null;
    PreparedStatement ps ;
    ResultSet rs;
     Productos bean = new Productos();
    public boolean registrarProducto(Productos bean){
        boolean ban=false;
        String sql="insert into productos (clave,precio,color,tipo,fecharegistro,foto,usuario_id) values(?,?,?,?,GETDATE(),?,'"+bean.getUsuario_id()+"')";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, bean.getClave());
            ps.setInt(2, bean.getPrecio());
            ps.setString(3,bean.getColor());
            ps.setString(4,bean.getTipo());
            FileInputStream archivoFoto = new FileInputStream(bean.getFotoStrin());
            ps.setBinaryStream(5, archivoFoto);
            ban=ps.executeUpdate()==1;
            ban=true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos RegistrarProductos " + e);
        }
        return ban;
        
    }
//Consultar si el producto existe
    public Productos consultaExiste(String clave){
        
        String sql="select * from productos where clave=?";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, clave);
            rs=ps.executeQuery();
            while(rs.next()){
                bean.setIdProductos(rs.getInt(1));
                bean.setClave(rs.getString(2));
                bean.setPrecio(rs.getInt(3));
                bean.setColor(rs.getString(4));
                bean.setTipo(rs.getString(5));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos consultaExiste " + e);
        }
       return bean;
    }
    
}
