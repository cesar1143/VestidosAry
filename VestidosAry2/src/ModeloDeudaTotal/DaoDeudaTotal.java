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
     public boolean modificarDeudaAndStatus(int nuevadeuda,int iddeudaTotal){
        boolean ban=false;
        String sql="update deudatotal set deudatotal=?, status='Pagado' where iddeudatotal=?";
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
 //Obtener la venta del dia para el reporte 
      public int consultarDeudaRporteDia(String fecha){
        int sumVentas=0;
        String sql="select deudatotal.deudatotal from deudatotal where CONVERT(date, deudatotal.fecharegistro, 101)='"+fecha+"' and deudatotal.status='Pagado';";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
            System.out.println("fecha en dao " + fecha);
            System.out.println("sql " + sql);
            
            rs=ps.executeQuery();
            while(rs.next()){
                sumVentas=sumVentas+rs.getInt("deudatotal");
                
                
                
                
            }
            System.out.println("sum venta s dao " + sumVentas);
            
        } catch (Exception e) {
             System.out.println("Mensaje daoDeudaTotal consultarDeudaRporteDia " + e);
        }
        
    return sumVentas;
    }
//Obtener la venta semanal para el reporte 
      public int consultarDeudaRporteSemana(String fechaInicial,String fechaFinal){
        int sumVentas=0;
        String sql="select deudatotal.deudatotal from deudatotal where CONVERT(date, deudatotal.fecharegistro, 101)>='"+fechaInicial+"'  AND CONVERT(date, deudatotal.fecharegistro, 101)<='"+fechaFinal+"' and deudatotal.status='Pagado';";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
           
            System.out.println("sql " + sql);
            
            rs=ps.executeQuery();
            while(rs.next()){
                sumVentas=sumVentas+rs.getInt("deudatotal");
            }
            System.out.println("sum venta s dao " + sumVentas);
            
        } catch (Exception e) {
             System.out.println("Mensaje daoDeudaTotal consultarDeudaRporteDia " + e);
        }
        
    return sumVentas;
    }
      //Obtener la venta semanal para el reporte 
      public String obtenerFechas(String fechaInicial,String fechaFinal){
        String getFechas="";
        String sql="select CONVERT(date, deudatotal.fecharegistro, 101)as fechas,status,sum(deudatotal)from deudatotal group by  CONVERT(date, deudatotal.fecharegistro, 101),status having CONVERT(date, deudatotal.fecharegistro, 101)>='"+fechaInicial+"'  AND CONVERT(date, deudatotal.fecharegistro, 101)<='2016-10-31' and deudatotal.status='Pagado';";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
           
            System.out.println("sql " + sql);
            
            rs=ps.executeQuery();
            while(rs.next()){
                getFechas=rs.getString("fechas");
                System.out.println("obtenmos las fechas " + getFechas);
            }
            
            
        } catch (Exception e) {
             System.out.println("Mensaje daoDeudaTotal obtenerFechas " + e);
        }
        
    return getFechas;
    }
      //Obtener la venta semanal para el reporte 
      public DeudaTotal consultarDeudaRporteMesMasVentas(String fechaInicial,String fechaFinal,int conMes){
        DeudaTotal bean= new DeudaTotal();
          int sumVentas=0;
        String sql="select CONVERT(date, deudatotal.fecharegistro, 101)as fechas,status,sum(deudatotal)as suma from deudatotal group by  CONVERT(date, deudatotal.fecharegistro, 101),status having CONVERT(date, deudatotal.fecharegistro, 101)>='"+fechaInicial+"'  AND CONVERT(date, deudatotal.fecharegistro, 101)<='"+fechaFinal+"' and deudatotal.status='Pagado';;";
        try {
            con=conexion.getConnection();
            ps=con.prepareStatement(sql);
           
            System.out.println("sql " + sql);
            
            rs=ps.executeQuery();
            while(rs.next()){
                bean.setFechaRegistro(rs.getString("fechas"));
                bean.setStatus(rs.getString("status"));
                sumVentas=sumVentas+rs.getInt("suma");
                bean.setDeudaTotal(sumVentas);
                bean.setConMes(conMes);
            }
            System.out.println("sum venta s dao " + sumVentas);
            
        } catch (Exception e) {
             System.out.println("Mensaje daoDeudaTotal consultarDeudaRporteDia " + e);
        }
        
    return bean;
    }
}
