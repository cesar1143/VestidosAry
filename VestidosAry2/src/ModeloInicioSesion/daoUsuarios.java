/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloInicioSesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class daoUsuarios {
    PreparedStatement ps;
    ResultSet rs;
    Connection con=null;
 
    public Usuarios consultaEspecifica(){
        Usuarios bean= new Usuarios();
        String sql="select * from usuarios";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
          
            rs=ps.executeQuery();
            while(rs.next()){
                bean.setIdusuarios(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                bean.setApaterno(rs.getString(3));
                bean.setAmaterno(rs.getString(4));
                bean.setUsuario(rs.getString(5));
                bean.setContra(rs.getString(6));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje en daoUsuarios, consultaEspecifica " + e);
        }
        return bean;
    }
    
}
