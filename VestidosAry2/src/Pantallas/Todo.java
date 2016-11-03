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
import ModeloMedidas.Medidas;
import ModeloPagos.DaoPagos;
import ModeloPagos.Pagos;
import ModeloProductos.DaoProductos;
import ModeloProductos.Productos;
import ModeloProductosApartados.DaoProductosApartados;
import ModeloProductosApartados.Operaciones;
import ModeloProductosApartados.OpreacionesCondeuda;
import ModeloProductosApartados.ProductosApartados;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import servicios.conexion;

/**
 *
 * @author Usuario
 */
public class Todo extends javax.swing.JFrame {

    /**
     * Creates new form Todo
     */
    PreparedStatement ps;
    ResultSet rs;
    Connection conec = null;
    DefaultTableModel tablaVentas, tableApartados, tablePagos;

    int totalPagar = 0;
    private int deudaTotal_id;

    int con = 0;
    int conFechas = 0;
    double arreMedidas[][] = new double[2][9];
    String arreFechas[][] = new String[2][3];
    public static int idCliente;
    public static String nom, apa, ama;

    Connection conex = null;
    JDialog ver = new JDialog(new Frame(), "reporte", true);

    public Todo() {
        tablaVentas = new DefaultTableModel(null, getColumnas());
        tableApartados = new DefaultTableModel(null, getColumnasPA());
        tablePagos = new DefaultTableModel(null, getColumnasPagos());

        initComponents();
        jTable1.setDefaultRenderer(Object.class, new ColorearTabla());

        this.setExtendedState(MAXIMIZED_BOTH);

    }

    public void infoPrincipal() {
        daoCliente daoCliente = new daoCliente();
        DaoDeudaTotal daoDeuda = new DaoDeudaTotal();
        DaoPagos daoPagos = new DaoPagos();
        System.out.println("nombre " + nom);
        Clientes beanCliente = daoCliente.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
        System.out.println("soy el id del cliente " + idCliente);
        DeudaTotal beanDeuda = daoDeuda.consultarDeuda(idCliente);
        System.out.println("bean get deuda " + beanDeuda.getDeudaTotal());

        int sumaPagos = daoPagos.sumaabonos(beanDeuda.getIdDeudaTotal());
        int deudaMenosPagos = beanDeuda.getDeudaTotal() - sumaPagos;
        jLabel4.setText(String.valueOf(beanDeuda.getDeudaTotal()));
        jLabel6.setText(String.valueOf(deudaMenosPagos));

    }

    public String[] getColumnas() {
        String columnas[] = new String[]{"Id", "clave", "Precio", "Tipo"};
        return columnas;
    }

    public String[] getColumnasPagos() {
        String columnas[] = new String[]{"Id", "Abono", "Fecha registro", "Usuario"};
        return columnas;
    }

    public String[] getColumnasPA() {
        String columnas[] = new String[]{"Id", "clave", "Precio", "Tipo", "Estatus", "Fecha Prueba", "Fecha Evento"};
        return columnas;
    }

    public void setFilasPA() {
        daoCliente dao = new daoCliente();
        System.out.println("nombre " + nom);
        Clientes bean = dao.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
        System.out.println("bean.getidclieten " + bean.getIdClientes());
        String sql = "select productosapartados.idproductosapartados,productos.clave,productos.precio,productos.tipo,productosapartados.status,fechaspruebas.fechaprueba,productosapartados.fechaentrega,clientes.nombre,clientes.idclientes\n"
                + "from productos join productosapartados on productos.idproductos=productosapartados.producto_id join clientes on clientes.idclientes= productosapartados.cliente_id  left join  fechaspruebas on fechaspruebas.productosapartados_id=productosapartados.idproductosapartados "
                + "where   clientes.idclientes='" + bean.getIdClientes() + "' and productosapartados.status='Apartado'  ;";
        try {

            conec = conexion.getConnection();
            ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();

            Object fila[] = new Object[7];
            while (rs.next()) {
                for (int i = 0; i < 7; i++) {
                    fila[i] = rs.getObject(i + 1);

                }
                tableApartados.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje Todo setFeilasPA");
        }

    }

    public void setFilasPagos() {
        daoCliente dao = new daoCliente();

        Clientes bean = dao.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
        String sql = "select pagos.idpagos,pagos.abono,pagos.fecharegistro,pagos.usuario_id\n"
                + "from pagos join deudatotal on pagos.deudatotal_id=deudatotal.iddeudatotal where deudatotal.cliente_id='" + bean.getIdClientes() + "' and deudatotal.status='No pagado';";
        try {

            conec = conexion.getConnection();
            ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            Object fila[] = new Object[4];
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tablePagos.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mensaje Todo setFeilasPagos");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        producto = new javax.swing.JTextField();
        fechaPrueba = new com.toedter.calendar.JDateChooser();
        fechaEvento = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        CheckSi = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(1));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Cliente: ");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("jLabel2");

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Productos apartados", 0, 0, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(733, 253));

        jTable1.setModel(tableApartados);
        jTable1.setPreferredSize(new java.awt.Dimension(733, 253));
        jScrollPane1.setViewportView(jTable1);

        jPanel7.setBackground(new java.awt.Color(204, 255, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Medidas", 0, 0, new java.awt.Font("Arial", 0, 11))); // NOI18N

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton8))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Pagos", 0, 0, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(733, 253));

        jTable2.setModel(tablePagos);
        jTable2.setPreferredSize(new java.awt.Dimension(733, 253));
        jScrollPane2.setViewportView(jTable2);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Deuda total: ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Restan: ");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("jLabel6");

        jPanel6.setBackground(new java.awt.Color(204, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Agregar abono", 0, 0, new java.awt.Font("Arial", 0, 11))); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475467111_sign-add.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton7))
        );

        jButton9.setText("reporte");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(140, 140, 140)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addGap(33, 33, 33)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Registro venta", 0, 0, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Producto: ");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Fecha prueba: ");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Fecha evento: ");

        producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                productoKeyTyped(evt);
            }
        });

        jTable3.setModel(tablaVentas);
        jScrollPane3.setViewportView(jTable3);

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Total a pagar: ");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 51));

        jButton3.setText("Registrar venta");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Quitar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(0), "Registro Producto", 0, 0, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/1475467326_sign-add.png"))); // NOI18N
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(32, 32, 32))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Medidas: ");

        CheckSi.setText("Si");
        CheckSi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CheckSiStateChanged(evt);
            }
        });
        CheckSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckSiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(36, 36, 36)
                                .addComponent(producto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(25, 25, 25)
                                .addComponent(jButton2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CheckSi)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fechaEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(fechaPrueba, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(fechaPrueba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(fechaEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(CheckSi))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton5)))
                .addGap(64, 64, 64))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(696, 696, 696))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(415, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productoKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_productoKeyTyped

    private void productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productoKeyReleased
        // TODO add your handling code here:
        DaoProductos dao = new DaoProductos();
        String clave = producto.getText().toString();
        Productos bean = dao.consultaExiste(clave);
        System.out.println("bean +  " + bean.getClave());
        try {
            if (bean.getClave().equals(clave)) {
                JOptionPane.showMessageDialog(null, "\n Precio: " + bean.getPrecio(), "Acerca del producto", -1);
            } else if (bean.getClave().equals(null)) {
                JOptionPane.showMessageDialog(null, "No existe el producto ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El producto no esta registrado");
        }
    }//GEN-LAST:event_productoKeyReleased

    private void CheckSiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckSiStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_CheckSiStateChanged

    private void CheckSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckSiActionPerformed
        // TODO add your handling code here:
        if (CheckSi.isSelected()) {
            MedidasRegistrar mr = new MedidasRegistrar();
            mr.setVisible(true);
        } else {

            MedidasRegistrar mr = new MedidasRegistrar();

            mr.setVisible(false);

        }
    }//GEN-LAST:event_CheckSiActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int sumaPagos = 0;
        int abono = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresar abono"));
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            Object pagos = jTable2.getValueAt(i, 1);
            sumaPagos = sumaPagos + Integer.parseInt(pagos.toString());

        }
        int total = sumaPagos + abono;
        System.out.println("suma pagos  " + total);
        int deudaMenosPagos = Integer.parseInt(jLabel4.getText().toString()) - total;
        if (total == Integer.parseInt(jLabel4.getText().toString())) {
            System.out.println("los pagos son igual ala deuda");
            //entonces registramos y enviamos un mensaje de ultimo pago registrado
            daoCliente daoCliente = new daoCliente();
            DaoDeudaTotal daoDeuda = new DaoDeudaTotal();
            DaoPagos daoPagos = new DaoPagos();
            Clientes beanCliente = daoCliente.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
            System.out.println("soy el id del cliente " + idCliente);
            DeudaTotal beanDeuda = daoDeuda.consultarDeuda(idCliente);

            Pagos beanPagos = new Pagos();

            beanPagos.setAbono(abono);
            beanPagos.setDeudaTotal_id(beanDeuda.getIdDeudaTotal());
            boolean ban = daoPagos.registrar(beanPagos);
            if (ban) {
                JOptionPane.showMessageDialog(null, "Ultimo pago registrado correctamente");
                limpiarTablaPagos();
                setFilasPagos();
                jLabel6.setText(String.valueOf(deudaMenosPagos));
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el Ultimo pago ", "ERROR", 0);
            }

        } else {
            // registramos con mensaje de el abono se registro correctamente
            daoCliente daoCliente = new daoCliente();
            DaoDeudaTotal daoDeuda = new DaoDeudaTotal();
            DaoPagos daoPagos = new DaoPagos();
            Clientes beanCliente = daoCliente.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
            System.out.println("soy el id del cliente " + idCliente);
            DeudaTotal beanDeuda = daoDeuda.consultarDeuda(idCliente);

            Pagos beanPagos = new Pagos();

            beanPagos.setAbono(abono);
            beanPagos.setDeudaTotal_id(beanDeuda.getIdDeudaTotal());
            boolean ban = daoPagos.registrar(beanPagos);
            if (ban) {
                JOptionPane.showMessageDialog(null, "El abono registrado correctamente");
                limpiarTablaPagos();
                setFilasPagos();
                jLabel6.setText(String.valueOf(deudaMenosPagos));
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el  abono ", "ERROR", 0);

            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        RegistroProducto pr = new RegistroProducto();
        pr.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if (jTable1.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona la fila");
        } else {
            int fila = jTable1.getSelectedRow();
            Object idPA = jTable1.getValueAt(fila, 0);
            System.out.println("este es el valor de la fila " + idPA);

            VerMedidas vm = new VerMedidas();
            vm.setFilas(Integer.parseInt(idPA.toString()));
            vm.setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String claveP = producto.getText().toString();
        if (claveP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresar clave de producto");

        } else {
            //aqui entraria el codigo

            // consulta para obtener datos del producto
            DaoProductos dao = new DaoProductos();
            String clave = producto.getText().toString();
            Productos bean = dao.consultaExiste(clave);

            if (CheckSi.isSelected()) {//Ver si el check medidas esta o no seleccionado
                if (fechaPrueba.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccionar la fecha de la prueba");
                } else if (fechaEvento.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccionar la fecha del evento");
                } else {
                    tablaVentas.addRow(new Object[]{bean.getIdProductos(), bean.getClave(), bean.getPrecio(), bean.getTipo()});
                    totalPagar = totalPagar + bean.getPrecio();
                    jTextField2.setText(String.valueOf(totalPagar));
                    //Obtenemos las medidas
                    double talle1 = Double.parseDouble(MedidasRegistrar.talle.getText().toString());
                    double sise1 = Double.parseDouble(MedidasRegistrar.sisa.getText().toString());
                    double hombros1 = Double.parseDouble(MedidasRegistrar.hombros.getText().toString());
                    double busto1 = Double.parseDouble(MedidasRegistrar.busto.getText().toString());
                    double cintura1 = Double.parseDouble(MedidasRegistrar.cintura.getText().toString());
                    double cadera1 = Double.parseDouble(MedidasRegistrar.cadera.getText().toString());
                    double largoFalda1 = Double.parseDouble(MedidasRegistrar.largoFalda.getText().toString());
                    double anchoPuño1 = Double.parseDouble(MedidasRegistrar.anchoPuño.getText().toString());
                    int dia = fechaPrueba.getCalendar().get(Calendar.DAY_OF_MONTH);
                    int mes = fechaPrueba.getCalendar().get(Calendar.MARCH);
                    int año = fechaPrueba.getCalendar().get(Calendar.YEAR);
                    String fechaP = año + "-" + mes + "-" + dia;
                    int diaE = fechaEvento.getCalendar().get(Calendar.DAY_OF_MONTH);
                    int mesE = fechaEvento.getCalendar().get(Calendar.MARCH);
                    int añoE = fechaEvento.getCalendar().get(Calendar.YEAR);
                    String fechaEven = añoE + "-" + mesE + "-" + diaE;
                    System.out.println("si estoy seleccionado");
                    arreMedidas[con][0] = bean.getIdProductos();
                    arreMedidas[con][1] = talle1;
                    arreMedidas[con][2] = sise1;
                    arreMedidas[con][3] = hombros1;
                    arreMedidas[con][4] = busto1;
                    arreMedidas[con][5] = cintura1;
                    arreMedidas[con][6] = cadera1;
                    arreMedidas[con][7] = largoFalda1;
                    arreMedidas[con][8] = anchoPuño1;
                    arreFechas[conFechas][0] = String.valueOf(bean.getIdProductos());
                    arreFechas[conFechas][1] = fechaP;
                    arreFechas[conFechas][2] = fechaEven;
                    conFechas++;
                    con++;
                    /*
                     for (int i = 0; i < arreFechas.length; i++) {

                     System.out.println("========= Fechas  chek seleccionado ==============");
                     System.out.println("id " + "Posicion " + i + " " + 0 + arreFechas[i][0]);
                     System.out.println("fecha Prueba " + "Posicion " + i + " " + 1 + arreFechas[i][1]);
                     System.out.println("Fecha Evento " + "Posicion " + i + " " + 2 + arreFechas[i][2]);
                     System.out.println("========== termina impresion fechas ============");

                     }
                     for (int j = 0; j < arre.length; j++) {

                     System.out.println("======= imprimir medidas ================");

                     System.out.println("idvestido " + "posicion " + j + 0 + arre[j][0]);
                     System.out.println("talle " + "posicion " + j + 1 + arre[j][1]);
                     System.out.println("sise " + "posicion " + j + 2 + arre[j][2]);
                     System.out.println("hombros " + "posicion " + j + 3 + arre[j][3]);
                     System.out.println("busto " + "posicion " + j + 4 + arre[j][4]);
                     System.out.println("cintura " + "posicion " + j + 5 + arre[j][5]);

                     System.out.println("cadera " + "posicion " + j + 6 + arre[j][6]);
                     System.out.println("largoFalda " + "posicion " + j + 7 + arre[j][7]);
                     System.out.println("ancho puño" + "posicion " + j + 8 + arre[j][8]);

                     }
                     */
                    //limpia campo de producto
                    producto.setText("");        // TODO add your handling code here:
                    fechaPrueba.setCalendar(null);
                    fechaEvento.setCalendar(null);
                    //limpiar campos de Medidas
                    MedidasRegistrar.talle.setText("");
                    MedidasRegistrar.sisa.setText("");
                    MedidasRegistrar.hombros.setText("");
                    MedidasRegistrar.busto.setText("");
                    MedidasRegistrar.cintura.setText("");
                    MedidasRegistrar.cadera.setText("");
                    MedidasRegistrar.largoFalda.setText("");
                    MedidasRegistrar.anchoPuño.setText("");
                    CheckSi.setSelected(false);
                    MedidasRegistrar m = new MedidasRegistrar();
                    System.out.println("si paso por aqui");
                    m.setVisible(false);

                }//cierra el if de fechas
            } else {//si no esta seleccionad check medidas
                try {
                    System.out.println(" entro en el checl no seleccionado");
                    int dia = fechaPrueba.getCalendar().get(Calendar.DAY_OF_MONTH);
                    int mes = fechaPrueba.getCalendar().get(Calendar.MARCH);
                    int año = fechaPrueba.getCalendar().get(Calendar.YEAR);
                    String fechaP = año + "-" + mes + "-" + dia;
                    int diaE = fechaEvento.getCalendar().get(Calendar.DAY_OF_MONTH);
                    int mesE = fechaEvento.getCalendar().get(Calendar.MARCH);
                    int añoE = fechaEvento.getCalendar().get(Calendar.YEAR);
                    String fechaEven = añoE + "-" + mesE + "-" + diaE;
                    arreFechas[conFechas][0] = String.valueOf(bean.getIdProductos());
                    arreFechas[conFechas][1] = fechaP;
                    arreFechas[conFechas][2] = fechaEven;

                    conFechas++;
                    /*
                     for (int i = 0; i < arreFechas.length; i++) {

                     System.out.println("========= Fechas sin chek seleccionado ==============");
                     System.out.println("id " + "Posicion " + i + " " + 0 + arreFechas[i][0]);
                     System.out.println("fecha Prueba " + "Posicion " + i + " " + 1 + arreFechas[i][1]);
                     System.out.println("Fecha Evento " + "Posicion " + i + " " + 2 + arreFechas[i][2]);
                     System.out.println("========== termina impresion fechas ============");

                     }
                     */
                } catch (Exception e) {
                    System.out.println("No hay fechas " + e);
                }
                tablaVentas.addRow(new Object[]{bean.getIdProductos(), bean.getClave(), bean.getPrecio(), bean.getTipo()});
                totalPagar = totalPagar + bean.getPrecio();
                jTextField2.setText(String.valueOf(totalPagar));
                //limpia campo de producto
                producto.setText("");        // TODO add your handling code here:
                fechaPrueba.setCalendar(null);
                fechaEvento.setCalendar(null);
            }

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        producto.setText("");        // TODO add your handling code here:
        fechaPrueba.setCalendar(null);
        fechaEvento.setCalendar(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (jTable3.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccionar la fila");
        } else {
            int fila = jTable3.getSelectedRow();
            Object valorPrecio = jTable3.getValueAt(fila, 2);
            tablaVentas.removeRow(fila);
            totalPagar = totalPagar - Integer.parseInt(valorPrecio.toString());
            jTextField2.setText(String.valueOf(totalPagar));
        }

    }//GEN-LAST:event_jButton5ActionPerformed
    public void limpiarTabla() {
        for (int i = 0; i < jTable3.getRowCount(); i++) {
            tablaVentas.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaPA() {
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            tableApartados.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTablaPagos() {
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            tablePagos.removeRow(i);
            i = i - 1;
        }
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        limpiarTabla();
        totalPagar = 0;
        con = 0;
        conFechas = 0;
        jTextField2.setText(String.valueOf(totalPagar));

    }//GEN-LAST:event_jButton4ActionPerformed
    //ya no se utilizo
    /*
     public void limpiarArregloMedidas(double arreMedidas[][]) {
     for (int i = 0; i < arreMedidas.length; i++) {
     for (int j = 0; j < arreMedidas.length; j++) {
     arreMedidas[i][j] = 0.0;

     }

     }
     }

     public void limpiarArregloFechas(String arreMedidas[][]) {
     for (int i = 0; i < arreMedidas.length; i++) {
     for (int j = 0; j < arreMedidas.length; j++) {
     arreMedidas[i][j] = null;

     }

     }
     }
     */

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        daoCliente daoCliente = new daoCliente();
        DaoDeudaTotal daoDeuda = new DaoDeudaTotal();
        DaoPagos daoPagos = new DaoPagos();
        Clientes beanCliente = daoCliente.consultaEspecificaNombreAndApaternoAndAmaterno(nom, apa, ama);
        System.out.println("soy el id del cliente " + idCliente);
        DeudaTotal beanDeuda = daoDeuda.consultarDeuda(idCliente);

        System.out.println("bean get deuda " + beanDeuda.getDeudaTotal());
        int totalDeuda = beanDeuda.getDeudaTotal() + Integer.parseInt(jTextField2.getText().toString());
        System.out.println("esto es lo que debees " + totalDeuda);

        System.out.println("filas " + jTable3.getRowCount());
        int pago = Integer.parseInt(JOptionPane.showInputDialog("Ingresar Pago"));
        System.out.println("pago " + pago);

        int sumaPagos = daoPagos.sumaabonos(beanDeuda.getIdDeudaTotal());
        int sumarPagos = sumaPagos + pago;

        int deudaMenosPagos = totalDeuda - sumarPagos;
        System.out.println("esto debes descontando los pagos " + deudaMenosPagos);
        Operaciones o = new Operaciones(pago);
//===================== BUSCAMOS SI TIENE DEUDA ====================================================
        if (beanDeuda.getDeudaTotal() != 0) {// si tiene deuda solo modificaremos la deuda
            System.out.println("si tiene deuda solo modificaremos la deuda");
            OpreacionesCondeuda opera = new OpreacionesCondeuda(pago, beanDeuda.getIdDeudaTotal(), totalDeuda);

            if (sumarPagos == totalDeuda) {

                System.out.println("entro al pago es igual ala deuda");
                //creamos el item para el status
                String status[] = {"Pagado entregado", "Pagado NO entregado"};
                Object estado = JOptionPane.showInputDialog(this, "Status", "Seleccionar status", JOptionPane.INFORMATION_MESSAGE, null, status, status[0]);
                if (estado.equals("Pagado entregado")) {//si se cumple se registra en apartados y vendidos
                    System.out.println("entro al estado pagado entregado");
                    boolean ban = opera.registrar(jTable3, arreMedidas, arreFechas, estado.toString());//Registro en la tabla apartados

                    if (ban) {
                        JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                        limpiarTabla();
                        limpiarTablaPA();
                        setFilasPA();
                        limpiarTablaPagos();
                        setFilasPagos();
                        totalPagar = 0;
                        jTextField2.setText(String.valueOf(totalPagar));
                        con = 0;
                        conFechas = 0;
                        jLabel4.setText(String.valueOf(totalDeuda));
                        jLabel6.setText(String.valueOf(deudaMenosPagos));
                        //-------------->>>>>otra opcion es mandar a cambiarlo con el status del ultimo registro de venta
                        CambiarStatusProductosApartados cs = new CambiarStatusProductosApartados();
                        cs.setFilasPA();
                        cs.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                    }

                } else {//si el estado es igual a pagado no entregado solo registramos en la tabla apartados
                    System.out.println("entro al estado pagado NO entregado");
                    boolean ban1 = opera.registrarExecptoVendidos(jTable3, arreMedidas, arreFechas, estado.toString());
                    if (ban1) {
                        JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                        limpiarTabla();
                        limpiarTablaPA();
                        setFilasPA();
                        limpiarTablaPagos();
                        setFilasPagos();
                        totalPagar = 0;
                        jTextField2.setText(String.valueOf(totalPagar));
                        con = 0;
                        conFechas = 0;
                        jLabel4.setText(String.valueOf(totalDeuda));
                        jLabel6.setText(String.valueOf(deudaMenosPagos));
                        CambiarStatusProductosApartados cs = new CambiarStatusProductosApartados();
                        cs.setFilasPA();
                        cs.setVisible(true);
                        // jTextField2.setText(String.valueOf(totalPagar));

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                    }
                }

            } else {//si el pago no es igual ala deuda pero el check esta seleccionado
                System.out.println("El pago no es igual ala deuda");

                boolean ban2 = opera.registrarExecptoVendidos(jTable3, arreMedidas, arreFechas, "Apartado");
                if (ban2) {
                    JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                    limpiarTabla();
                    limpiarTablaPA();
                    setFilasPA();
                    limpiarTablaPagos();
                    setFilasPagos();
                    totalPagar = 0;
                    jTextField2.setText(String.valueOf(totalPagar));
                    con = 0;
                    conFechas = 0;
                    jLabel4.setText(String.valueOf(totalDeuda));
                    jLabel6.setText(String.valueOf(deudaMenosPagos));
                    //jTextField2.setText(String.valueOf(totalPagar));

                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                }
            }
            /*
             totalPagar = 0;
             jTextField2.setText(String.valueOf(totalPagar));
             con = 0;
             conFechas = 0;
             */

//------------------------ HASTA AQUI ----------------------------------------------------------------                
        } else {// si no tiene deuda se registrara todo
            System.out.println("se registra todo por que no tienes deuda");
            if (pago == Integer.parseInt(jTextField2.getText().toString())) {

                System.out.println("entro al pago es igual ala deuda");
                //creamos el item para el status
                String status[] = {"Pagado entregado", "Pagado NO entregado"};
                Object estado = JOptionPane.showInputDialog(this, "Status", "Seleccionar status", JOptionPane.INFORMATION_MESSAGE, null, status, status[0]);
                if (estado.equals("Pagado entregado")) {//si se cumple se registra en apartados y vendidos
                    System.out.println("entro al estado pagado entregado");
                    boolean ban = o.registrar(jTable3, arreMedidas, arreFechas, estado.toString());//Registro en la tabla apartados

                    if (ban) {
                        JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                        limpiarTabla();
                        limpiarTablaPA();
                        setFilasPA();
                        limpiarTablaPagos();
                        setFilasPagos();
                        totalPagar = 0;
                        jTextField2.setText(String.valueOf(totalPagar));
                        con = 0;
                        conFechas = 0;
                        jLabel4.setText(String.valueOf(totalDeuda));
                        jLabel6.setText(String.valueOf(deudaMenosPagos));

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                    }

                } else {//si el estado es igual a pagado no entregado solo registramos en la tabla apartados
                    System.out.println("entro al estado pagado NO entregado");
                    boolean ban1 = o.registrarExecptoVendidos(jTable3, arreMedidas, arreFechas, estado.toString());
                    if (ban1) {
                        JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                        limpiarTabla();
                        limpiarTablaPA();
                        setFilasPA();
                        limpiarTablaPagos();
                        setFilasPagos();
                        totalPagar = 0;
                        jTextField2.setText(String.valueOf(totalPagar));
                        con = 0;
                        conFechas = 0;
                        jLabel4.setText(String.valueOf(totalDeuda));
                        jLabel6.setText(String.valueOf(deudaMenosPagos));
                        // jTextField2.setText(String.valueOf(totalPagar));

                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                    }
                }

            } else {//si el pago no es igual ala deuda pero el check esta seleccionado
                System.out.println("El pago no es igual ala deuda");

                boolean ban2 = o.registrarExecptoVendidos(jTable3, arreMedidas, arreFechas, "Apartado");
                if (ban2) {
                    JOptionPane.showMessageDialog(null, "La venta se registro correctamente");
                    limpiarTabla();
                    limpiarTablaPA();
                    setFilasPA();
                    limpiarTablaPagos();
                    setFilasPagos();
                    totalPagar = 0;
                    jTextField2.setText(String.valueOf(totalPagar));
                    con = 0;
                    conFechas = 0;
                    jLabel4.setText(String.valueOf(totalDeuda));
                    jLabel6.setText(String.valueOf(deudaMenosPagos));
                    //jTextField2.setText(String.valueOf(totalPagar));

                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la venta ", "ERROR", 0);

                }
            }

        }//cerramos el if de no tiene deuda
        /*
         totalPagar = 0;
         jTextField2.setText(String.valueOf(totalPagar));
         con = 0;
         conFechas = 0;
         jLabel4.setText(String.valueOf(totalDeuda));
         jLabel6.setText(String.valueOf(deudaMenosPagos));
         */
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
/*
        try {
            
            JasperReport report;
            report = (JasperReport) JRLoader.loadObject("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\Sicoca\\VestidosAry\\VestidosAry\\VestidosAry2\\src\\Pantallas\\report1.jrxml");
            System.out.println("aaa " + report);
            JasperPrint print = JasperFillManager.fillReport(report, null, conex);
 
            JasperViewer v = new   JasperViewer(print);
            v.show();
            
        } catch (Exception e) {
             
            System.out.println("error " + e.getMessage());
        }
        */
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(Todo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Todo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Todo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Todo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Todo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckSi;
    private com.toedter.calendar.JDateChooser fechaEvento;
    private com.toedter.calendar.JDateChooser fechaPrueba;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTextField jTextField2;
    private javax.swing.JTextField producto;
    // End of variables declaration//GEN-END:variables
}
