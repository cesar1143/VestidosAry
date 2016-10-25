/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class ColorearTabla extends DefaultTableCellRenderer {

    PreparedStatement ps;
    ResultSet rs;
    Connection con = null;
    private Component componente;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        componente.setForeground(Color.black);
        Object v = table.getValueAt(row, 0);
        System.out.println("valor celda " + v);
        boolean ban = ver(Integer.parseInt(v.toString()));
        System.out.println("ban " + ban);
        if (ban) {
            System.out.println("entro a amarillo");
            componente.setBackground(Color.YELLOW);
        } else {
            componente.setBackground(Color.white);
            System.out.println("entro a blanco");
        }
        return componente;
    }
    
    public boolean ver(int fila) {
        boolean ban = false;
        System.out.println("entro a colorear ");
        
        con = conexion.getConnection();
        System.out.println("entro a colorear  111111");        
        String sql = "select  productosapartados.idproductosapartados from productosapartados where idproductosapartados in (select medidas.productosapartados_id from medidas) and idproductosapartados='" + fila + "' and productosapartados.status='Apartado' ";
        System.out.println("sqql " + sql);
        try {
            System.out.println("entro a colorear 22222222222 ");
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                ban = true;
            }
            
        } catch (Exception e) {
            
        }
        return ban;
    }
}
