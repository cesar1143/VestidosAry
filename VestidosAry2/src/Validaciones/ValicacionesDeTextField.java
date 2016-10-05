/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validaciones;

import java.awt.TextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Usuario
 */
public class ValicacionesDeTextField {
    
    public boolean  validarCampoInicioSesion(String campo){
        boolean ban= false;
        if(campo.equals("")){
            JOptionPane.showMessageDialog(null, "Ingresar contraseña");
            ban=true;
            
        }else{
            //JOptionPane.showMessageDialog(null, "bien");
        }
        return ban;
        
    }
}
