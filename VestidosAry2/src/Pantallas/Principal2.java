/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import ModeloClientes.Clientes;
import ModeloClientes.daoCliente;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class Principal2 extends javax.swing.JFrame {

    DefaultTableModel tableModel;
  ResultSet rs;
    /**
     * Creates new form Principal
     */
    public Principal2() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Id", "Nombre", "Paterno", "Materno","Telefono"});
        initComponents();
        setFilas();
        this.setExtendedState(MAXIMIZED_BOTH);
      
        //tableModel.addRow(new Object[]{"1", "cesar", "lopez"});
        //this.setExtendedState(MAXIMIZED_BOTH);
        soloLetras(TextNombre);
    }
   
     
    public void setFilas(){
        daoCliente dao= new daoCliente();
        rs=dao.consultaTodos();
        try {
           
        while(rs.next()){
            int id=rs.getInt("idclientes");
            String nombre1= rs.getString("nombre");
            String apaterno1=rs.getString("apaterno");
            String amaterno1=rs.getString("amaterno");
            int telefono1=rs.getInt("telefono");
           
            tableModel.addRow(new Object[]{id,nombre1,apaterno1,amaterno1,telefono1});
            
        }
      
            
        } catch (Exception e) {
        }
        
    }
            
        
    

    public void soloLetras(JTextField a) {
        a.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    getToolkit().beep();
                    e.consume();
                }

            }
        });
    }

    public void soloNumeros(JTextField a) {
        a.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) //getToolkit().beep();
                {
                    e.consume();
                }

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        todos = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Clientes");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Nombre: ");

        TextNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNombreActionPerformed(evt);
            }
        });

        Buscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475275692_search.png"))); // NOI18N
        Buscar.setText("Buscar");
        Buscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(tableModel);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        todos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        todos.setText("Todos");
        todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(565, 565, 565)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(todos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(todos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/opciones.png"))); // NOI18N
        jMenu1.setText("Opciones");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        jMenuItem1.setText("Agregar Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475276232_icon-136-document-edit.png"))); // NOI18N
        jMenuItem2.setText("Modificar Clientes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
        int fila=jTable1.getSelectedRow();
        Object nombre=jTable1.getValueAt(fila,1);
        Object apaterno=jTable1.getValueAt(fila,2);
        Object amaterno=jTable1.getValueAt(fila,3);
         Object idCliente=jTable1.getValueAt(fila,0);
         
        String nomCompleto=nombre.toString() + " " + apaterno.toString() + " " + amaterno.toString();
        Todo t = new Todo();
        Todo.nom=nombre.toString();
        Todo.apa=apaterno.toString();
        Todo.ama=amaterno.toString();
        
        Todo.idCliente=Integer.parseInt(idCliente.toString());
        Todo.jLabel2.setText(nomCompleto);
        t.infoPrincipal();
        t.setFilasPA();
        t.setFilasPagos();
        t.setVisible(true);
    }//GEN-LAST:event_jTable1MousePressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        ClienteAgregar ca = new ClienteAgregar();
        ca.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if(jTable1.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"Seleccionar la fila");
        }else{
         int fila =jTable1.getSelectedRow();
         Object nombre=jTable1.getValueAt(fila, 1);
         Object apaterno=jTable1.getValueAt(fila, 2);
         Object amaterno=jTable1.getValueAt(fila, 3);
         Object telefono=jTable1.getValueAt(fila, 4);
         Object id=jTable1.getValueAt(fila, 0);
         ;
         
         
        ClienteModificar cm = new ClienteModificar();
        ClienteModificar.nombre.setText(nombre.toString());
         ClienteModificar.apaterno.setText(apaterno.toString());
          ClienteModificar.amaterno.setText(amaterno.toString());
           ClienteModificar.telefono.setText(telefono.toString());
            ClienteModificar.id=Integer.parseInt(id.toString());
        
        cm.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreActionPerformed
    public void vaciarTabla() {
   
        for (int i = 0; i <jTable1.getRowCount(); i++) {
            tableModel.removeRow(i);
 
            i-=1;
           
        }
       
    }
    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        // TODO add your handling code here:
        
        String nombre = TextNombre.getText().toString();
        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresar el nombre del cliente");
        } else {
            String arrayNombre[] = nombre.split(" ");
            int tamaño = arrayNombre.length;
            System.out.println("tamaño " + tamaño);
            //entraria la consulta
            daoCliente dao = new daoCliente();

            if (tamaño == 1) {
                System.out.println("entro en la consulta con nombre ");

                Clientes bean = dao.consultaEspecifica(arrayNombre[0]);

                System.out.println("nombre " + bean.getNombre());
                if (bean.getNombre() != null) {
                    vaciarTabla();
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(),bean.getTelefono()});
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }
            } else if (tamaño == 2) {
                System.out.println("entro en la consulta con nombre y apaterno");
                String nombre1 = "";
                String apaterno = "";

                nombre1 = arrayNombre[0];
                apaterno = arrayNombre[1];

                Clientes bean = dao.consultaEspecificaNombreAndApaterno(nombre1, apaterno);

                System.out.println("nombre " + bean.getNombre());
                if (bean.getNombre() != null && bean.getApaterno() != null) {
                    vaciarTabla();
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(),bean.getTelefono()});
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }

            } else if (tamaño == 3) {
                System.out.println("entro en la consulta con nombre y apaterno y amaterno");
                String nombre1 = "";
                String apaterno = "";
 
                nombre1 = arrayNombre[0];
                apaterno = arrayNombre[1];
                String amaterno=arrayNombre[2];
                Clientes bean = dao.consultaEspecificaNombreAndApaternoAndAmaterno(nombre1, apaterno,amaterno);

                System.out.println("nombre " + bean.getNombre());
                if (bean.getNombre() != null && bean.getApaterno() != null) {
                    vaciarTabla();
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(),bean.getTelefono()});
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }

            }

        }
        
     
    }//GEN-LAST:event_BuscarActionPerformed

    private void todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todosActionPerformed
        // TODO add your handling code here:
        daoCliente dao= new daoCliente();
        rs=dao.consultaTodos();
        try {
           vaciarTabla();
        while(rs.next()){
            int id=rs.getInt("idclientes");
            String nombre1= rs.getString("nombre");
            String apaterno1=rs.getString("apaterno");
            String amaterno1=rs.getString("amaterno");
            int telefono1=rs.getInt("telefono");
            System.out.println("nombre " + nombre1);
            tableModel.addRow(new Object[]{id,nombre1,apaterno1,amaterno1,telefono1});
            
        }
            
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_todosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Buscar;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton todos;
    // End of variables declaration//GEN-END:variables
}