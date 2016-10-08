/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloClientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class daoCliente {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;

    public Clientes consultaEspecifica(String nombre) {
        Clientes  bean = new Clientes();
        String sql = "select * from clientes  where nombre=? or apaterno=? or amaterno=?";

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, nombre);
             ps.setString(3, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
               
                bean.setIdClientes(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                bean.setApaterno(rs.getString(3));
                bean.setAmaterno(rs.getString(4));
                bean.setTelefono(rs.getInt(5));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoCliente, consultaEspecifica " + e);
        }
        return bean;
    }
// ==================================== CONSULTA ESPECIFICA CON NOMBRE Y APATERNO ====================================
    public Clientes consultaEspecificaNombreAndApaterno(String nombre,String apaterno) {
        Clientes  bean = new Clientes();
        String sql = "select * from clientes  where nombre=? and apaterno=?";

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apaterno);
            rs = ps.executeQuery();
            while (rs.next()) {
               
                bean.setIdClientes(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                bean.setApaterno(rs.getString(3));
                bean.setAmaterno(rs.getString(4));
                bean.setTelefono(rs.getInt(5));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoCliente, consultaEspecificaNombreAndApaterno " + e);
        }
        return bean;
    }
    
// ==================================== CONSULTA ESPECIFICA CON NOMBRE Y APATERNO Y AMATERNO ====================================
    public Clientes consultaEspecificaNombreAndApaternoAndAmaterno(String nombre,String apaterno,String amaterno) {
        Clientes  bean = new Clientes();
        String sql = "select * from clientes  where nombre=? and apaterno=? and amaterno=?";

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apaterno);
            ps.setString(3, amaterno);
            rs = ps.executeQuery();
            while (rs.next()) {
               
                bean.setIdClientes(rs.getInt(1));
                bean.setNombre(rs.getString(2));
                bean.setApaterno(rs.getString(3));
                bean.setAmaterno(rs.getString(4));
                bean.setTelefono(rs.getInt(5));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoCliente,  consultaEspecificaNombreAndApaternoAndAmaterno " + e);
        }
        return bean;
    }
// ==================================== CONSULTA  TODOS ====================================
    public ResultSet consultaTodos() {
        Clientes  bean = new Clientes();
        String sql = "select * from clientes ";

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoCliente,  consultaTodos " + e);
        }
        return rs;
    }
    
// ==================================== AGREGAR CLIENTES ==================================== 
    public boolean registrarCliente(Clientes bean){
        boolean ban=false;
        String sql="insert into clientes (nombre,apaterno,amaterno,telefono,fecharegistro,usuario_id)"
                + "values (?,?,?,?,GETDATE(),'"+bean.getUsuarios_id()+"')";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, bean.getNombre());
            ps.setString(2, bean.getApaterno());
            ps.setString(3, bean.getAmaterno());
            ps.setInt(4, bean.getTelefono());
            ban=ps.executeUpdate()==1;
            ban=true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoCliente,  registrarCliente " + e);
            System.out.println("me " + e);
        }
        return ban;
    }
    
// ==================================== UPDATE CLIENTES ==================================== 
    public boolean modificar (Clientes bean){
        boolean ban=false;
        String sql="Update clientes set nombre=? , apaterno=?, amaterno=?, telefono=? where idclientes=?";
        try {
           con=conexion.getConnection();
           ps=con.prepareStatement(sql);
           ps.setString(1, bean.getNombre());
           ps.setString(2,bean.getApaterno());
           ps.setString(3, bean.getAmaterno());
           ps.setInt(4, bean.getTelefono());
           ps.setInt(5, bean.getIdClientes());
          ban=ps.executeUpdate()==1;
          ban=true;
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"Mensaje daoCliente modificar " + e);
        }
        return ban;
        
    }
}
