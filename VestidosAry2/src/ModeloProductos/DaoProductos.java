/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductos;

import ModeloClientes.Clientes;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class DaoProductos {

    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    Productos bean = new Productos();

    public boolean registrarProducto(Productos bean) {
        boolean ban = false;
        String sql = "insert into productos (clave,precio,color,tipo,fecharegistro,foto,usuario_id) values(?,?,?,?,GETDATE(),?,'" + bean.getUsuario_id() + "')";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, bean.getClave());
            ps.setInt(2, bean.getPrecio());
            ps.setString(3, bean.getColor());
            ps.setString(4, bean.getTipo());
            FileInputStream archivoFoto = new FileInputStream(bean.getFotoStrin());
            ps.setBinaryStream(5, archivoFoto);
            ban = ps.executeUpdate() == 1;
            ban = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos RegistrarProductos " + e);
        }
        return ban;

    }
//Consultar si el producto existe

    public Productos consultaExiste(String clave) {

        String sql = "select * from productos where clave=?";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, clave);
            rs = ps.executeQuery();
            while (rs.next()) {
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
// ==================================== CONSULTA  TODOS ====================================

    public ResultSet consultaTodos() {
        Clientes bean = new Clientes();
        String sql = "select * from productos ";

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoProductos,  consultaTodos " + e);
        }
        return rs;
    }

// ==================================== CONSULTA  imagenes ====================================
    public Productos consultarImage(int idpro) {
        Productos bean = new Productos();
        String sql = "select foto from productos where idproductos=? ";

        try {
            con = conexion.getConnection();

            ps = con.prepareStatement(sql);
            System.out.println("soy id pro " + idpro);
            ps.setInt(1, idpro);
            rs = ps.executeQuery();
            while (rs.next()) {
                byte[] img = rs.getBytes("foto");
                bean.setFoto(img);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoProductos,  consultarImage " + e);
        }
        return bean;
    }
    // ==================================== CONSULTA  imagenes CON CLAVE ====================================
    public Productos consultarImageConClave(int idpro) {
        Productos bean = new Productos();
        String sql = "select foto from productos where clave=? ";

        try {
            con = conexion.getConnection();

            ps = con.prepareStatement(sql);
            System.out.println("soy id pro " + idpro);
            ps.setInt(1, idpro);
            rs = ps.executeQuery();
            while (rs.next()) {
                byte[] img = rs.getBytes("foto");
                bean.setFoto(img);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje en daoProductos,  consultarImage " + e);
        }
        return bean;
    }

//=================================== OBTENER IMAGEN ================================================
    public Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {

            param.setSourceSubsampling(4, 4, 0, 0);

        }
        return reader.read(0, param);

    }
//=============== Consulta Especifica para modificar =================================

    public Productos consultaEspecifica(int idPro) {

        String sql = "select * from productos where idproductos=?";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPro);
            rs = ps.executeQuery();
            while (rs.next()) {
                bean.setIdProductos(rs.getInt(1));
                bean.setClave(rs.getString(2));
                bean.setPrecio(rs.getInt(3));
                bean.setColor(rs.getString(4));
                bean.setTipo(rs.getString(5));
                byte[] img = rs.getBytes("foto");
                bean.setFoto(img);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos consultaEspecifica " + e);
        }
        return bean;
    }
//============== Modificar Productos con nueva foto =================================
public boolean modificarProductoConFoto(Productos bean) {
        boolean ban = false;
        String sql = "update productos set clave=?,precio=?,color=?,tipo=?,foto=? where idproductos=?";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, bean.getClave());
            ps.setInt(2, bean.getPrecio());
            ps.setString(3, bean.getColor());
            ps.setString(4, bean.getTipo());
            FileInputStream archivoFoto = new FileInputStream(bean.getFotoStrin());
            ps.setBinaryStream(5, archivoFoto);
            ps.setInt(6, bean.getIdProductos());
            ban = ps.executeUpdate() == 1;
            ban = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos ModificarProductos " + e);
        }
        return ban;

    }
//============== Modificar Productos foto actual =================================
public boolean modificarProductoFotoActual(Productos bean) {
        boolean ban = false;
        String sql = "update productos set clave=?,precio=?,color=?,tipo=? where idproductos=?";
        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, bean.getClave());
            ps.setInt(2, bean.getPrecio());
            ps.setString(3, bean.getColor());
            ps.setString(4, bean.getTipo());
            
            ps.setInt(5, bean.getIdProductos());
            ban = ps.executeUpdate() == 1;
            ban = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje DaoProductos ModificarProductos " + e);
        }
        return ban;

    }
}
