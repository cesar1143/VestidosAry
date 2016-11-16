/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import ModeloClientes.Clientes;
import ModeloClientes.daoCliente;
import ModeloDeudaTotal.DaoDeudaTotal;
import ModeloDeudaTotal.DeudaTotal;
import ModeloPendientes.Pendientes;
import ModeloProductos.DaoProductos;
import ModeloProductos.Productos;
import ModeloProductosApartados.DaoProductosApartados;
import ModeloProductosVendidos.DaoProductosVendidos;
import ModeloProductosVendidos.ProductosVendidos;
import static Pantallas.Todo.ama;
import static Pantallas.Todo.apa;
import static Pantallas.Todo.nom;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class Principal extends javax.swing.JFrame {

    DefaultTableModel tableModel, tableVerProductos, tablePendientes;
    ResultSet rs;
    private Object btnBoton;
    //estas variables sirven para que solo se abra un jframe
    public static boolean controlClienteAdd = false;
    public static boolean controlClienteMod = false;
    public static boolean controlProductoMod = false;
    public static boolean controlProductoAdd = false;
    public static boolean controlSesionReporte = false;

    public Principal() {
        tableModel = new DefaultTableModel();
        tableVerProductos = new DefaultTableModel(null, getColumnasVP());
        tablePendientes = new DefaultTableModel(null, getColumnasPen());
        tableModel.setColumnIdentifiers(new Object[]{"Id", "Nombre", "Paterno", "Materno", "Telefono"});
        initComponents();
        setFilas();
        setFilasVP();
        setFeilasPendientes();
        soloLetras(TextNombre);
        this.setExtendedState(MAXIMIZED_BOTH);

        //ocultamos las cosas en reporte hasta que inicie sesion
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jComboBox2.setVisible(false);
        jDateChooser1.setVisible(false);
        jYearChooser1.setVisible(false);
        jDateChooser2.setVisible(false);
        jButton3.setVisible(false);
        jTextField1.setVisible(false);
        
        jButton6.setVisible(false);

    }

    public String[] getColumnasVP() {
        String columnas[] = new String[]{"Id", "Clave", "Color", "Precio", "Tipo"};
        return columnas;
    }

    public String[] getColumnasPen() {
        String columnas[] = new String[]{"Id", "nombre", "Paterno", "Clave", "Color", "Precio", "Tipo", "Status"};
        return columnas;
    }

    public void vaciarTabla() {

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            tableModel.removeRow(i);

            i -= 1;

        }

    }

    public void vaciarTablaPendientes() {

        for (int i = 0; i < jTable3.getRowCount(); i++) {
            tablePendientes.removeRow(i);

            i -= 1;

        }

    }

    public void vaciarTablaVP() {

        for (int i = 0; i < jTable2.getRowCount(); i++) {
            tableVerProductos.removeRow(i);

            i -= 1;

        }

    }

    public void setFeilasPendientes() {
        System.out.println("entro al se filas pendientes");
        Connection conec = null;

        String sql = "select productosapartados.idproductosapartados,clientes.nombre,clientes.apaterno, productos.clave,productos.color,productos.precio,productos.tipo,productosapartados.status\n"
                + "from productos join productosapartados on productos.idproductos=productosapartados.producto_id join clientes on clientes.idclientes= productosapartados.cliente_id left join  fechaspruebas on fechaspruebas.productosapartados_id=productosapartados.idproductosapartados where   productosapartados.status='Pagado NO entregado' ;";
        try {

            conec = conexion.getConnection();
            PreparedStatement ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();

            Object fila[] = new Object[8];
            while (rs.next()) {
                for (int i = 0; i < 8; i++) {
                    fila[i] = rs.getObject(i + 1);

                }
                tablePendientes.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje Principal boton buscar setFeilasPA");

        }

    }

    public void setFilasVP() {
        System.out.println("ento en set filas Vp");
        DaoProductos dao = new DaoProductos();
        rs = dao.consultaTodos();
        try {

            while (rs.next()) {
                System.out.println("entro al while de set fila ver");
                int id = rs.getInt("idproductos");
                String clave = rs.getString("clave");
                String color = rs.getString("color");
                int precio = rs.getInt("precio");
                String tipo = rs.getString("tipo");

                tableVerProductos.addRow(new Object[]{id, clave, color, precio, tipo});

            }

        } catch (Exception e) {
        }

    }

    public void setFilas() {

        daoCliente dao = new daoCliente();
        rs = dao.consultaTodos();
        try {

            while (rs.next()) {
                int id = rs.getInt("idclientes");
                String nombre1 = rs.getString("nombre");
                String apaterno1 = rs.getString("apaterno");
                String amaterno1 = rs.getString("amaterno");
                int telefono1 = rs.getInt("telefono");

                tableModel.addRow(new Object[]{id, nombre1, apaterno1, amaterno1, telefono1});

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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        panelPrincipal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextNombre = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        todos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        labelFoto = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        panelPrincipal2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        TextNombre1 = new javax.swing.JTextField();
        Buscar1 = new javax.swing.JButton();
        todos1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("jMenu4");

        jMenu5.setText("jMenu5");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1340, 460));

        panelPrincipal.setBackground(new java.awt.Color(204, 255, 204));
        panelPrincipal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        panelPrincipal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panelPrincipalStateChanged(evt);
            }
        });
        panelPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPrincipalMouseClicked(evt);
            }
        });

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

        todos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        todos.setText("Todos");
        todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todosActionPerformed(evt);
            }
        });

        jTable1.setModel(tableModel);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel12.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("Cerrar sesión");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1877, Short.MAX_VALUE)
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
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1867, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(todos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
        );

        panelPrincipal.addTab("Clientes", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Productos");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Buscar por: ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Novia", "IV años", "Presentacion", "Arras", "anillos", "Biblias", "Lazos" }));

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Todos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(tableVerProductos);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        labelFoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Editar producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(206, 104));

        jButton4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475276232_icon-136-document-edit.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Registro producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(206, 104));

        jButton5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475467326_sign-add.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(28, 28, 28)
                                        .addComponent(jButton2))
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(548, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(labelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(282, Short.MAX_VALUE))
        );

        panelPrincipal.addTab("Productos", jPanel2);

        panelPrincipal2.setBackground(new java.awt.Color(255, 0, 0));
        panelPrincipal2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        panelPrincipal2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPrincipal2MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("Cerrar sesion");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Reportes de ventas");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Seleccionar el tipo de reporte: ");

        jComboBox2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecciona...", "Dia", "Semana", "Año", "Mes con mas ventas" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Generar reporte");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Ingresar fecha:");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Ingresar año:");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Ingresar fecha final:");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Venta");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 0, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1844, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(465, 465, 465)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel10)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(0, 0, 0)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(659, 659, 659)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addGap(74, 74, 74)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton3)
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(395, Short.MAX_VALUE))
        );

        panelPrincipal2.addTab("Ventas", jPanel3);

        panelPrincipal.addTab("Reportes", panelPrincipal2);

        jPanel7.setBackground(new java.awt.Color(204, 255, 204));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Pendientes");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Nombre: ");

        TextNombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNombre1ActionPerformed(evt);
            }
        });

        Buscar1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Buscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475275692_search.png"))); // NOI18N
        Buscar1.setText("Buscar");
        Buscar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar1ActionPerformed(evt);
            }
        });

        todos1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        todos1.setText("Todos");
        todos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todos1ActionPerformed(evt);
            }
        });

        jTable3.setModel(tablePendientes);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable3MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton6.setText("Cambiar todos");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(312, 312, 312)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(Buscar1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(todos1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(18, 18, 18)
                                                .addComponent(TextNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(545, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(TextNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(todos1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(268, 268, 268))
        );

        panelPrincipal.addTab("Pendientes", jPanel7);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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
            .addComponent(panelPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombreActionPerformed

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
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(), bean.getTelefono()});
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
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(), bean.getTelefono()});
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }

            } else if (tamaño == 3) {
                System.out.println("entro en la consulta con nombre y apaterno y amaterno");
                String nombre1 = "";
                String apaterno = "";

                nombre1 = arrayNombre[0];
                apaterno = arrayNombre[1];
                String amaterno = arrayNombre[2];
                Clientes bean = dao.consultaEspecificaNombreAndApaternoAndAmaterno(nombre1, apaterno, amaterno);

                System.out.println("nombre " + bean.getNombre());
                if (bean.getNombre() != null && bean.getApaterno() != null) {
                    vaciarTabla();
                    tableModel.addRow(new Object[]{bean.getIdClientes(), bean.getNombre(), bean.getApaterno(), bean.getAmaterno(), bean.getTelefono()});
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "ERROR", 0);
                }

            }

        }

    }//GEN-LAST:event_BuscarActionPerformed

    private void todosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todosActionPerformed
        // TODO add your handling code here:
        daoCliente dao = new daoCliente();
        rs = dao.consultaTodos();
        try {
            vaciarTabla();
            while (rs.next()) {
                int id = rs.getInt("idclientes");
                String nombre1 = rs.getString("nombre");
                String apaterno1 = rs.getString("apaterno");
                String amaterno1 = rs.getString("amaterno");
                int telefono1 = rs.getInt("telefono");
                System.out.println("nombre " + nombre1);
                tableModel.addRow(new Object[]{id, nombre1, apaterno1, amaterno1, telefono1});

            }

        } catch (Exception e) {
        }

    }//GEN-LAST:event_todosActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
        int fila = jTable1.getSelectedRow();
        Object nombre = jTable1.getValueAt(fila, 1);
        Object apaterno = jTable1.getValueAt(fila, 2);
        Object amaterno = jTable1.getValueAt(fila, 3);
        Object idCliente = jTable1.getValueAt(fila, 0);

        String nomCompleto = nombre.toString() + " " + apaterno.toString() + " " + amaterno.toString();
        Todo t = new Todo();
        Todo.nom = nombre.toString();
        Todo.apa = apaterno.toString();
        Todo.ama = amaterno.toString();

        Todo.idCliente = Integer.parseInt(idCliente.toString());
        Todo.jLabel2.setText(nomCompleto);
        t.infoPrincipal();
        t.setFilasPA();
        t.setFilasPagos();
        t.setVisible(true);
    }//GEN-LAST:event_jTable1MousePressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        ClienteAgregar ca = new ClienteAgregar();

        if (controlClienteAdd == false) {
            ca.setVisible(true);
            controlClienteAdd = true;

        } else {
            JOptionPane.showMessageDialog(null, "Ya esta abierto esta ventana");

        }


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccionar la fila");
        } else {

            int fila = jTable1.getSelectedRow();
            Object nombre = jTable1.getValueAt(fila, 1);
            Object apaterno = jTable1.getValueAt(fila, 2);
            Object amaterno = jTable1.getValueAt(fila, 3);
            Object telefono = jTable1.getValueAt(fila, 4);
            Object id = jTable1.getValueAt(fila, 0);
            ;

            ClienteModificar cm = new ClienteModificar();
            ClienteModificar.nombre.setText(nombre.toString());
            ClienteModificar.apaterno.setText(apaterno.toString());
            ClienteModificar.amaterno.setText(amaterno.toString());
            ClienteModificar.telefono.setText(telefono.toString());
            ClienteModificar.id = Integer.parseInt(id.toString());
            if (controlClienteMod == false) {

                controlClienteMod = true;
                cm.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Ya esta abierto esta ventana");

            }

        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void panelPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panelPrincipalStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_panelPrincipalStateChanged

    private void panelPrincipal2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPrincipal2MouseClicked
        // TODO add your handling code here:
        sesionReportes in = new sesionReportes();
        if (controlSesionReporte == false) {
            in.setVisible(true);
            controlSesionReporte = true;

        } else {
            JOptionPane.showMessageDialog(null, "Ya esta abierto esta ventana");

        }


    }//GEN-LAST:event_panelPrincipal2MouseClicked

    private void panelPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPrincipalMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_panelPrincipalMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "cerrar sesion");

        //ocultamos las cosas en reporte hasta que inicie sesion
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jComboBox2.setVisible(false);
        jDateChooser1.setVisible(false);
        jYearChooser1.setVisible(false);
        jDateChooser2.setVisible(false);
        jButton3.setVisible(false);
        jTextField1.setVisible(false);

        panelPrincipal.setSelectedIndex(0);
        controlSesionReporte = false;


    }//GEN-LAST:event_jLabel5MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        String tipoReporte = jComboBox2.getSelectedItem().toString();
        if (tipoReporte.equals("Selecciona...")) {
            jTextField1.setText("");
            jLabel11.setText("Venta");
            jLabel8.setVisible(false);
            jDateChooser1.setVisible(false);
            jLabel9.setVisible(false);
            jYearChooser1.setVisible(false);
            jLabel10.setVisible(false);
            jDateChooser2.setVisible(false);
        }
        if (tipoReporte.equals("Dia")) {
            jLabel8.setText("Ingresar fecha");
            jLabel8.setVisible(true);
            jDateChooser1.setVisible(true);
            jLabel9.setVisible(false);
            jYearChooser1.setVisible(false);
            jLabel10.setVisible(false);
            jDateChooser2.setVisible(false);
            jTextField1.setText("");

        } else if (tipoReporte.equals("Semana")) {
            jLabel8.setText("Fecha inicial");
            jLabel8.setVisible(true);
            jDateChooser1.setVisible(true);
            jLabel10.setText("Fecha final");

            jLabel10.setVisible(true);
            jDateChooser2.setVisible(true);
            jLabel9.setVisible(false);
            jYearChooser1.setVisible(false);
            jTextField1.setText("");

        } else if (tipoReporte.equals("Año")) {
            jLabel8.setVisible(false);
            jDateChooser1.setVisible(false);
            jLabel9.setVisible(true);
            jYearChooser1.setVisible(true);
            jLabel10.setVisible(false);
            jDateChooser2.setVisible(false);
            jTextField1.setText("");

        } else if (tipoReporte.equals("Mes con mas ventas")) {
            jLabel8.setVisible(false);
            jDateChooser1.setVisible(false);
            jLabel9.setVisible(true);
            jYearChooser1.setVisible(true);
            jLabel10.setVisible(false);
            jDateChooser2.setVisible(false);
            jTextField1.setText("");

        }

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        // TODO add your handling code here:
        int fila = jTable2.getSelectedRow();
        Object idPro = jTable2.getValueAt(fila, 0);
        DaoProductos dao = new DaoProductos();
        Productos bean = dao.consultarImage(Integer.parseInt(idPro.toString()));
        Image imagen;
        try {
            imagen = dao.getImage(bean.getFoto(), false);
            Icon icon = new ImageIcon(imagen.getScaledInstance(323, 578, Image.SCALE_DEFAULT));
            labelFoto.setIcon(icon);
        } catch (Exception ex) {
            System.out.println("error al cargar al imagen " + ex);
        }

    }//GEN-LAST:event_jTable2MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        vaciarTablaVP();

        String tipoB = jComboBox1.getSelectedItem().toString();

        DaoProductos dao = new DaoProductos();
        Connection conec = null;

        String sql = "select idproductos,clave,color,precio,tipo from productos where tipo='" + tipoB + "'";
        try {

            conec = conexion.getConnection();
            PreparedStatement ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("soy rs " + rs);

            Object fila[] = new Object[5];
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);

                }
                tableVerProductos.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje Principal boton buscar setFeilasPA");

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        vaciarTablaVP();

        DaoProductos dao = new DaoProductos();
        Connection conec = null;

        String sql = "select idproductos,clave,color,precio,tipo from productos";
        try {

            conec = conexion.getConnection();
            PreparedStatement ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("soy rs " + rs);

            Object fila[] = new Object[5];
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {
                    fila[i] = rs.getObject(i + 1);

                }
                tableVerProductos.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje Principal boton todos setFeilasPA");

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        RegistroProducto rp = new RegistroProducto();
        if (controlProductoAdd == false) {
            rp.setVisible(true);
            controlProductoAdd = true;

        } else {
            JOptionPane.showMessageDialog(null, "Ya esta abierto esta ventana");

        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (jTable2.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccionar fila");
        } else {
            ModificarProducto mp = new ModificarProducto();
            int fila = jTable2.getSelectedRow();
            Object idPro = jTable2.getValueAt(fila, 0);
            DaoProductos dao = new DaoProductos();
            System.out.println("idPro " + idPro);
            Productos bean = dao.consultaEspecifica(Integer.parseInt(idPro.toString()));
            RegistroProducto.idProducto = bean.getIdProductos();
            System.out.println("get clave " + bean.getClave());
            ModificarProducto.codigo.setText(bean.getClave());
            ModificarProducto.precio.setText(String.valueOf(bean.getPrecio()));
            ModificarProducto.color.setText(bean.getColor());
            ModificarProducto.comboTipo.setSelectedItem(bean.getTipo());
            ModificarProducto.idProducto = bean.getIdProductos();
            Image imagen;
            try {
                imagen = dao.getImage(bean.getFoto(), false);
                Icon icon = new ImageIcon(imagen.getScaledInstance(170, 130, Image.SCALE_DEFAULT));
                ModificarProducto.jLabel2.setIcon(icon);
            } catch (Exception ex) {
                System.out.println("error al cargar al imagen " + ex);
            }
            if (controlProductoMod == false) {
                mp.setVisible(true);
                controlProductoMod = true;

            } else {
                JOptionPane.showMessageDialog(null, "Ya esta abierto esta ventana");

            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DaoDeudaTotal dao = new DaoDeudaTotal();
        String tipoReporte = jComboBox2.getSelectedItem().toString();
        if (tipoReporte.equals("Selecciona...")) {
            JOptionPane.showMessageDialog(null, "Seleccionar un tipo de reporte");
        } else if (tipoReporte.equals("Dia")) {
            if (jDateChooser1.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingresar la fecha");
            } else {
                int dia = jDateChooser1.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mes = jDateChooser1.getCalendar().get(Calendar.MONTH) + 1;
                int año = jDateChooser1.getCalendar().get(Calendar.YEAR);
                String fechaDia = año + "-" + mes + "-" + dia;
                String fechaDia2 = dia + "-" + mes + "-" + año;
                System.out.println("Fechaaa " + fechaDia);

                int ventaXDia = dao.consultarDeudaRporteDia(fechaDia);

                jLabel11.setText("Venta del dia  " + fechaDia2);
                System.out.println("venta por dia  " + ventaXDia);
                jTextField1.setText(String.valueOf(ventaXDia));
            }
        } else if (tipoReporte.equals("Semana")) {
            if (jDateChooser1.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingresar la fecha inicial");
            } else if (jDateChooser2.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Ingresar la fecha final");

            } else {
                //primera fecha
                int dia1 = jDateChooser1.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mes1 = jDateChooser1.getCalendar().get(Calendar.MONTH) + 1;
                int año1 = jDateChooser1.getCalendar().get(Calendar.YEAR);
                String fechaInicial = año1 + "-" + mes1 + "-" + dia1;
                String fechaInicial1 = dia1 + "-" + mes1 + "-" + año1;
                // segunda fecha
                int dia2 = jDateChooser2.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mes2 = jDateChooser2.getCalendar().get(Calendar.MONTH) + 1;
                int año2 = jDateChooser2.getCalendar().get(Calendar.YEAR);
                String fechaFinal = año2 + "-" + mes2 + "-" + dia2;
                String fechaFinal2 = dia2 + "-" + mes2 + "-" + año2;

                //obtenemos la cantida de ventas y lo mostramos
                int ventaXSemana = dao.consultarDeudaRporteSemana(fechaInicial, fechaFinal);

                jLabel11.setText("Venta semanal del  " + fechaInicial1 + " al " + fechaFinal2);

                jTextField1.setText(String.valueOf(ventaXSemana));

            }
        } else if (tipoReporte.equals("Año")) {
            int año = jYearChooser1.getYear();

            String fechaInicialAño = año + "-" + "01" + "-" + "01";
            String fechaFinalAño = año + "-" + "12" + "-" + "31";

            String fechaInicialAño1 = "01" + "-" + "01" + "-" + año;
            String fechaFinalAño1 = "31" + "-" + "12" + "-" + año;

            //obtenemos la cantida de ventas y lo mostramos
            int ventaXSemana = dao.consultarDeudaRporteSemana(fechaInicialAño, fechaFinalAño);

            jLabel11.setText("Venta anual del  " + fechaInicialAño1 + " al " + fechaFinalAño1);

            jTextField1.setText(String.valueOf(ventaXSemana));

        } else if (tipoReporte.equals("Mes con mas ventas")) {
            int conMes = 1;
            int añoMes = jYearChooser1.getYear();
            DeudaTotal ventaMesMasVentas = null;
            DeudaTotal arre[] = new DeudaTotal[12];
            /*
             String fechaInicialAño1 = "01"+"-"+"01"+"-"+añoMes;
             String fechaFinalAño1 = "31"+"-"+"12"+"-"+añoMes;
             */

            while (conMes <= 12) {
                System.out.println("entro al while " + conMes);

                //String getFechas=dao.obtenerFechas(fechaInicialAño, fechaFinalAño);
                if (conMes == 1 || conMes == 3 || conMes == 5 || conMes == 7 || conMes == 8 || conMes == 10 || conMes == 12) {
                    String fechaInicialAño = añoMes + "-" + conMes + "-" + "01";
                    String fechaFinalAño = añoMes + "-" + conMes + "-" + "31";

                    ventaMesMasVentas = dao.consultarDeudaRporteMesMasVentas(fechaInicialAño, fechaFinalAño, conMes);
                    System.out.println("soy las ventas " + ventaMesMasVentas.getDeudaTotal());
                    System.out.println("conMes 1 " + conMes);

                    arre[conMes - 1] = ventaMesMasVentas;

                } else if (conMes == 4 || conMes == 6 || conMes == 9 || conMes == 11) {
                    String fechaInicialAño = añoMes + "-" + conMes + "-" + "01";
                    String fechaFinalAño = añoMes + "-" + conMes + "-" + "30";
                    ventaMesMasVentas = dao.consultarDeudaRporteMesMasVentas(fechaInicialAño, fechaFinalAño, conMes);
                    System.out.println("soy las ventas " + ventaMesMasVentas.getDeudaTotal());

                    arre[conMes - 1] = ventaMesMasVentas;
                } else if (conMes == 2) {
                    String fechaInicialAño = añoMes + "-" + conMes + "-" + "01";
                    String fechaFinalAño = añoMes + "-" + conMes + "-" + "29";
                    ventaMesMasVentas = dao.consultarDeudaRporteMesMasVentas(fechaInicialAño, fechaFinalAño, conMes);
                    System.out.println("soy las ventas " + ventaMesMasVentas.getDeudaTotal());

                    arre[conMes - 1] = ventaMesMasVentas;
                } else {
                    System.out.println("else");
                }

                conMes++;
            }

            for (int i = 0; i < arre.length; i++) {
                for (int j = i + 1; j < arre.length; j++) {
                    if (arre[i].getDeudaTotal() < arre[j].getDeudaTotal()) {
                        System.out.println("hay un cambio");
                        DeudaTotal aux = arre[i];
                        arre[i] = arre[j];
                        arre[j] = aux;
                    } else {
                        System.out.println("no hay cambios");
                    }

                }

            }
            if (arre[0].getDeudaTotal() == 0) {
                jLabel11.setText("El mes con mas ventas del año " + añoMes + " es: ");
                jTextField1.setText(String.valueOf("No hay"));
            } else {
                if (arre[0].getConMes() == 1) {
                    jLabel11.setText("El mes con mas ventas del año " + añoMes + " es: ");
                    jTextField1.setText(String.valueOf("Enero"));
                } else if (arre[0].getConMes() == 2) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Febrero"));
                } else if (arre[0].getConMes() == 3) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Marzo"));
                } else if (arre[0].getConMes() == 4) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Abril"));
                } else if (arre[0].getConMes() == 5) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Mayo"));
                } else if (arre[0].getConMes() == 6) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Junio"));
                } else if (arre[0].getConMes() == 7) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Julio"));
                } else if (arre[0].getConMes() == 8) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Agosto"));
                } else if (arre[0].getConMes() == 9) {
                    jLabel11.setText("El mes con mas ventas del año " + añoMes);
                    jTextField1.setText(String.valueOf("Septiembre"));
                } else if (arre[0].getConMes() == 10) {
                    jLabel11.setText("El mes con mas ventas del año " + añoMes + " es: ");
                    jTextField1.setText(String.valueOf("Octubre"));
                } else if (arre[0].getConMes() == 11) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Noviembre"));
                } else if (arre[0].getConMes() == 12) {
                    jLabel11.setText("Venta del mes mas vendido");
                    jTextField1.setText(String.valueOf("Diciembre"));
                } else {
                    System.out.println("se salio del año");
                }

            }

            System.err.println("este es la cantidad mas grande " + arre[0].getDeudaTotal() + " - " + arre[0].getFechaRegistro() + " - " + arre[0].getConMes());
        }
        jDateChooser1.setCalendar(null);
        jDateChooser2.setCalendar(null);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        InicioSesion in = new InicioSesion();
        in.setVisible(true);

    }//GEN-LAST:event_jLabel12MouseClicked

    private void TextNombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNombre1ActionPerformed

    private void Buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar1ActionPerformed
        // TODO add your handling code here:

        if (TextNombre1.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresar nombre o apellido del cliente");
        } else {
            String dato = TextNombre1.getText().toString();
            daoCliente dao = new daoCliente();

            List<Pendientes> listaPen = dao.consultaEspecificaNombreOApaternoOAmaterno(dato);

            vaciarTablaPendientes();

            for (int i = 0; i < listaPen.size(); i++) {
                System.out.println("entro al for");
                tablePendientes.addRow(new Object[]{String.valueOf(listaPen.get(i).getIdProductosApartados()), listaPen.get(i).getNombre(), listaPen.get(i).getApaterno(), listaPen.get(i).getClave(), listaPen.get(i).getColor(), listaPen.get(i).getPrecio(), listaPen.get(i).getTipo(), listaPen.get(i).getStatus()});
            }

            TextNombre1.setText("");
        }
        jButton6.setVisible(true);

    }//GEN-LAST:event_Buscar1ActionPerformed

    private void todos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todos1ActionPerformed
        // TODO add your handling code here:
        vaciarTablaPendientes();
        setFeilasPendientes();
        jButton6.setVisible(false);
    }//GEN-LAST:event_todos1ActionPerformed

    private void jTable3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MousePressed
        // TODO add your handling code here:
        int fila = jTable3.getSelectedRow();
        Object idPro = jTable3.getValueAt(fila, 3);
        DaoProductos dao = new DaoProductos();
        Productos bean = dao.consultarImageConClave(Integer.parseInt(idPro.toString()));
        Image imagen;
        try {
            imagen = dao.getImage(bean.getFoto(), false);
            Icon icon = new ImageIcon(imagen.getScaledInstance(330, 540, Image.SCALE_DEFAULT));
            jLabel15.setIcon(icon);
        } catch (Exception ex) {
            System.out.println("error al cargar al imagen " + ex);
        }
    }//GEN-LAST:event_jTable3MousePressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ProductosVendidos bean = new ProductosVendidos();
        DaoProductosApartados daoPA = new DaoProductosApartados();
        DaoProductosVendidos dao = new DaoProductosVendidos();
        boolean ban = false;
        boolean banPA = false;
        if (jTable3.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "La tabla esta vacia");
        } else {

            for (int i = 0; i < jTable3.getRowCount(); i++) {
                //registramos en vendidos y cambiamos status
                Object idPA = jTable3.getValueAt(i, 0);
                banPA = daoPA.modificarStatus("Pagado entregado", Integer.parseInt(idPA.toString()));

            }
            if (banPA) {
                JOptionPane.showMessageDialog(null, "Se cambio el status correcatamente");
                for (int i = 0; i < jTable3.getRowCount(); i++) {
                    Object idPA = jTable3.getValueAt(i, 0);
                    bean.setProductosApartados_id(Integer.parseInt(idPA.toString()));
                    ban = dao.registrar(bean);
                }
                if(ban){
                    vaciarTablaPendientes();
                setFeilasPendientes();
                jButton6.setVisible(false);
                }else{
                  JOptionPane.showMessageDialog(null, "Error al registrar en vendidos ", "ERROR", 0);   
                }
                

            } else {
                JOptionPane.showMessageDialog(null, "Error al cambiar el status ", "ERROR", 0);
            }

        }


    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Buscar1;
    private javax.swing.JTextField TextNombre;
    private javax.swing.JTextField TextNombre1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox jComboBox1;
    public static javax.swing.JComboBox jComboBox2;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTextField jTextField1;
    public static com.toedter.calendar.JYearChooser jYearChooser1;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JTabbedPane panelPrincipal;
    private javax.swing.JTabbedPane panelPrincipal2;
    private javax.swing.JButton todos;
    private javax.swing.JButton todos1;
    // End of variables declaration//GEN-END:variables
}
