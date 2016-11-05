/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosApartados;

import ModeloDeudaTotal.DaoDeudaTotal;
import ModeloDeudaTotal.DeudaTotal;
import ModeloFechasPruebas.DaoFechasPruebas;
import ModeloFechasPruebas.FechasPruebas;
import ModeloMedidas.DaoMedidas;
import ModeloMedidas.Medidas;
import ModeloPagos.DaoPagos;
import ModeloPagos.Pagos;
import ModeloProductosVendidos.DaoProductosVendidos;
import ModeloProductosVendidos.ProductosVendidos;
import Pantallas.Todo;
import static Pantallas.Todo.idCliente;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Usuario
 */
public class Operaciones {

    private int pago;

    Medidas beanMedidas = new Medidas();
    DaoMedidas daoMedidas = new DaoMedidas();

    public Operaciones(int pago) {
        this.pago = pago;
    }

    public boolean registrar(JTable jTable3, double arreMedidas[][], String arreFechas[][], String estado) {
        boolean banr = false;
        System.out.println("filas " + jTable3.getRowCount());
        for (int j = 0; j < jTable3.getRowCount(); j++) {//obtenemos el id de la tabla

            System.out.println("valores " + jTable3.getValueAt(j, 0));
            Object idProducto = jTable3.getValueAt(j, 0);

            for (int i = 0; i < arreMedidas.length; i++) {//buscamos si el id de la tabla existe el arre medidas
                System.out.println("idProducto " + idProducto);
                System.out.println("id arreglo " + (int) ((arreMedidas[i][0])));

                if (Integer.parseInt(idProducto.toString()) == (int) (arreMedidas[i][0])) {//si si existe obtenemos las medidas
                    System.out.println(" si existe este id en medidas y en la tabla");
                    System.out.println("idTabla " + idProducto);
                    System.out.println("id arreglo medidas " + (int) arreMedidas[i][0] + " posicion " + i + " " + 0);
                    ProductosApartados beanPA = new ProductosApartados();
                    DaoProductosApartados daoPA = new DaoProductosApartados();
                    //LLENAMOS EL BEAN DE PROAPAR
                    beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                    beanPA.setCliente_id(idCliente);
                    beanPA.setStatus(estado.toString());
                    System.out.println("long arre fechas " + arreFechas.length);

                    for (int k = 0; k < arreFechas.length; k++) {//vemos si existe el id en el arre fechas
                        if (arreFechas[k][0] == null) {
                            arreFechas[k][0] = String.valueOf("0");
                            System.out.println("entro al if " + arreFechas[k][0]);
                        } else {

                        }
                        System.out.println("entramos al for para buscar fechas");
                        if (Integer.parseInt(idProducto.toString()) == Integer.parseInt(String.valueOf(arreFechas[k][0]))) {//si si existe obtenemos las fechas
                            System.out.println("si tiene fechas");
                            System.out.println("las fechas " + arreFechas[k][2] + " " + " posicino " + k);
                            beanPA.setFechaEntrega(arreFechas[k][2]);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;
                                //si se registra en apartados registramos medidas
                                boolean banM = registrarMedidas(arreMedidas[i][1], arreMedidas[i][2], arreMedidas[i][3], arreMedidas[i][4], arreMedidas[i][5], arreMedidas[i][6], arreMedidas[i][7], arreMedidas[i][8]);
                                if (banM) {
                                    JOptionPane.showMessageDialog(null, "Las medidas se registraron correctamente");
                                    //si se registra en medidas registramos fechas
                                    boolean banFechas = registraFechas(arreFechas[k][1]);
                                    if (banFechas) {
                                        JOptionPane.showMessageDialog(null, "La fecha prueba se registro correctamente");
                                        //si se registra en fechas registramos vendidos
                                        System.out.println("soy el estado " + estado);
                                        if (estado.equals("Pagado entregado")) {
                                            boolean banPV = registrarProductosVendidos();
                                            if (banPV) {
                                                //---------------------------------->>  //ver donde poner el registro de deuda total y pagos por que deben registrarse solo 1 vez
                                                JOptionPane.showMessageDialog(null, "Se registro correctamente en vendidos");

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Erro al registra en vendidos", "ERROR", 0);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "No se registra en vendidos por que el estado es no Pagado entregado en vendidos");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro al registra la fecha Prueba " + "ERROR " + 0);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erro al registrar las medidas " + "ERROR " + 0);
                                }
                            } else {

                            }
                            k = arreFechas.length;
                        } else {
                            //si no tiene fechas
                            System.out.println(" nose encontro el id en fechas");

                        }

                    }
                    i = arreMedidas.length;
                } else {

                    /*No se encontro el id de la tabla en arreglo medias, osea no hay medidas*/
                    ProductosApartados beanPA = new ProductosApartados();
                    DaoProductosApartados daoPA = new DaoProductosApartados();
                    System.out.println(" no encontro el id en el arreglo arre medidas");
                    //LLENAMOS EL BEAN DE PROAPAR
                    beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                    beanPA.setCliente_id(idCliente);
                    beanPA.setStatus(estado.toString());
                    for (int k = 0; k < arreFechas.length; k++) {//vemos si existe el id en el arre fechas
                        if (arreFechas[k][0] == null) {
                            arreFechas[k][0] = String.valueOf("0");
                            System.out.println("entro al if " + arreFechas[k][0]);
                        } else {

                        }
                        System.out.println("entramos al for para buscar fechas");
                        if (Integer.parseInt(idProducto.toString()) == Integer.parseInt(String.valueOf(arreFechas[k][0]))) {//si si existe obtenemos las fechas
                            System.out.println("si tiene fechas");
                            System.out.println("las fechas " + arreFechas[k][2] + " " + " posicino " + k);
                            beanPA.setFechaEntrega(arreFechas[k][2]);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;
                                //si se registra en apartados registramos FECHAS PRUEBA

                                boolean banFechas = registraFechas(arreFechas[k][1]);
                                if (banFechas) {
                                    JOptionPane.showMessageDialog(null, "La fecha prueba se registro correctamente");
                                    //si se registra en fechas registramos vendidos
                                    System.out.println("soy el estado " + estado);
                                    if (estado.equals("Pagado entregado")) {
                                        boolean banPV = registrarProductosVendidos();
                                        if (banPV) {
                                            //---------------------------------->>  //ver donde poner el registro de deuda total y pagos por que deben registrarse solo 1 vez
                                            JOptionPane.showMessageDialog(null, "Se registro correctamente en vendidos");

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Erro al registra en vendidos", "ERROR", 0);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se registra en vendidos por que el estado es no Pagado entregado en vendidos");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erro al registra la fecha Prueba " + "ERROR " + 0);
                                }

                            } else {

                            }
                            k = arreFechas.length;
                        } else {
                            //si no tiene fechas y tampoco medidas
                            System.out.println(" nose encontro el id en fechas y tampoco medidas");

                            System.out.println(" no encontro el id en el arreglo arre medidas");
                            //LLENAMOS EL BEAN DE PROAPAR
                            beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                            beanPA.setCliente_id(idCliente);
                            beanPA.setStatus(estado.toString());

                            beanPA.setFechaEntrega(null);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;
                                //si se registra en apartados registramos FECHAS PRUEBA

                               System.out.println("soy el estado " + estado);
                                        if(estado.equals("Pagado entregado")){
                                             boolean banPV = registrarProductosVendidos();
                                              if (banPV) {
                                            //---------------------------------->>  //ver donde poner el registro de deuda total y pagos por que deben registrarse solo 1 vez
                                            JOptionPane.showMessageDialog(null, "Se registro correctamente en vendidos");

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Erro al registra en vendidos", "ERROR", 0);
                                        }
                                        }else{
                                            JOptionPane.showMessageDialog(null, "No se registra en vendidos por que el estado es no Pagado entregado en vendidos"); 
                                        }

                            }
                            k = arreFechas.length;
                        }

                    }
                    //===================== HASTA AQUI ========================================
                    i = arreMedidas.length;
                }

            }

        }
        //si se registra en vendidos registramos deuda total
        boolean banDT = resgistraDeudaTotla();
        if (banDT) {
            JOptionPane.showMessageDialog(null, "Se resgistro correctamente en deuda total");
            //si se registra la deuda total registramos en pagos
            boolean banP = registrarPagos();
            if (banP) {
                JOptionPane.showMessageDialog(null, "El pago se registro correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registra el pago", "ERROR", 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro al registrar en deuda total", "ERROR", 0);
        }

        return banr;
    }

    /* REGISTRO CASI COMPLETO SOLO NO SE RESGITRA EN VENTIDOS YA QUE EL ESTATUS ES PAGADO NO  ENTREGADO */
//aqui lo unico que cambia es el estado y tampoco registramos en vanta trabajar aqui
    public boolean registrarExecptoVendidos(JTable jTable3, double arreMedidas[][], String arreFechas[][], String estado) {
        boolean banr = false;

        System.out.println("filas " + jTable3.getRowCount());
        for (int j = 0; j < jTable3.getRowCount(); j++) {//obtenemos el id de la tabla

            System.out.println("valores " + jTable3.getValueAt(j, 0));
            Object idProducto = jTable3.getValueAt(j, 0);
            for (int i = 0; i < arreMedidas.length; i++) {//buscamos si el id de la tabla existe el arre medidas
                System.out.println("idProducto " + idProducto);
                System.out.println("id arreglo " + (int) ((arreMedidas[i][0])));
                if (Integer.parseInt(idProducto.toString()) == (int) (arreMedidas[i][0])) {//si si existe obtenemos las medidas
                    System.out.println(" si existe este id en medidas y en la tabla");
                    System.out.println("idTabla " + idProducto);
                    System.out.println("id arreglo medidas " + (int) arreMedidas[i][0] + " posicion " + i + " " + 0);
                    ProductosApartados beanPA = new ProductosApartados();
                    DaoProductosApartados daoPA = new DaoProductosApartados();
                    //LLENAMOS EL BEAN DE PROAPAR
                    beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                    beanPA.setCliente_id(idCliente);
                    beanPA.setStatus(estado.toString());
                    System.out.println("long arre fechas " + arreFechas.length);

                    for (int k = 0; k < arreFechas.length; k++) {//vemos si existe el id en el arre fechas
                        if (arreFechas[k][0] == null) {
                            arreFechas[k][0] = String.valueOf("0");
                            System.out.println("entro al if " + arreFechas[k][0]);
                        } else {

                        }
                        System.out.println("entramos al for para buscar fechas");
                        if (Integer.parseInt(idProducto.toString()) == Integer.parseInt(String.valueOf(arreFechas[k][0]))) {//si si existe obtenemos las fechas
                            System.out.println("si tiene fechas");
                            System.out.println("las fechas " + arreFechas[k][2] + " " + " posicino " + k);
                            beanPA.setFechaEntrega(arreFechas[k][2]);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;
                                //si se registra en apartados registramos medidas
                                boolean banM = registrarMedidas(arreMedidas[i][1], arreMedidas[i][2], arreMedidas[i][3], arreMedidas[i][4], arreMedidas[i][5], arreMedidas[i][6], arreMedidas[i][7], arreMedidas[i][8]);
                                if (banM) {
                                    JOptionPane.showMessageDialog(null, "Las medidas se registraron correctamente");
                                    //si se registra en medidas registramos fechas
                                    boolean banFechas = registraFechas(arreFechas[k][1]);
                                    if (banFechas) {
                                        JOptionPane.showMessageDialog(null, "La fecha prueba se registro correctamente");

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro al registra la fecha Prueba " + "ERROR " + 0);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erro al registrar las medidas " + "ERROR " + 0);
                                }
                            } else {

                            }
                            k = arreFechas.length;
                        } else {
                            //si no tiene fechas
                            System.out.println(" nose encontro el id en fechas");

                        }

                    }
                    i = arreMedidas.length;
                } else {
                    System.out.println(" no encontro el id en el arreglo arre medidas");
                    /*No se encontro el id de la tabla en arreglo medias, osea no hay medidas*/
                    ProductosApartados beanPA = new ProductosApartados();
                    DaoProductosApartados daoPA = new DaoProductosApartados();

                    //LLENAMOS EL BEAN DE PROAPAR
                    beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                    beanPA.setCliente_id(idCliente);
                    beanPA.setStatus(estado.toString());
                    for (int k = 0; k < arreFechas.length; k++) {//vemos si existe el id en el arre fechas

                        System.out.println("entramos al for para buscar fechas");
//                        System.out.println("arreFechas " + Integer.parseInt(String.valueOf(arreFechas[k][0])));
                        System.out.println("STring " + arreFechas[k][0]);
                        if (arreFechas[k][0] == null) {
                            arreFechas[k][0] = String.valueOf("0");
                            System.out.println("entro al if " + arreFechas[k][0]);
                        } else {

                        }
                        System.out.println("STring ya  " + arreFechas[k][0]);
                        if (Integer.parseInt(idProducto.toString()) == Integer.parseInt(String.valueOf(arreFechas[k][0]))) {//si si existe obtenemos las fechas

                            System.out.println("si tiene fechas");
                            System.out.println("las fechas " + arreFechas[k][2] + " " + " posicino " + k);
                            beanPA.setFechaEntrega(arreFechas[k][2]);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;
                                //si se registra en apartados registramos FECHAS PRUEBA

                                boolean banFechas = registraFechas(arreFechas[k][1]);
                                if (banFechas) {
                                    JOptionPane.showMessageDialog(null, "La fecha prueba se registro correctamente");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Erro al registra la fecha Prueba " + "ERROR " + 0);
                                }

                            } else {

                            }
                            k = arreFechas.length;
                        } else {
                            //si no tiene fechas y tampoco medidas
                            System.out.println(" nose encontro el id en fechas y tampoco medidas");

                            System.out.println(" no encontro el id en el arreglo arre medidas");
                            //LLENAMOS EL BEAN DE PROAPAR
                            beanPA.setProducto_id(Integer.parseInt(idProducto.toString()));
                            beanPA.setCliente_id(idCliente);
                            beanPA.setStatus(estado.toString());

                            beanPA.setFechaEntrega(null);
                            boolean ban = daoPA.registrarVenta(beanPA);//registramos en apartados
                            if (ban) {

                                banr = true;

                            }
                            k = arreFechas.length;
                        }

                    }
                    //===================== HASTA AQUI ========================================
                    i = arreMedidas.length;
                }

            }

        }
        //si se registra en vendidos registramos deuda total
        boolean banDT = resgistraDeudaTotla2();
        if (banDT) {
            JOptionPane.showMessageDialog(null, "Se resgistro correctamente en deuda total");
            //si se registra la deuda total registramos en pagos
            boolean banP = registrarPagos();
            if (banP) {
                JOptionPane.showMessageDialog(null, "El pago se registro correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registra el pago", "ERROR", 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro al registrar en deuda total", "ERROR", 0);
        }

        return banr;
    }

//============================================= HASTA AQUI ===============================================    
    public boolean registrarMedidas(double talle, double sise, double hombros, double busto, double largoFalda, double anchoPuño, double cintura, double cadera) {
        boolean ban = false;
        //buscar el ultimo registro en la tabla apartados
        int ultimo_id_apartados = daoMedidas.obtener_id();

        //llenamos el bean medidas
        beanMedidas.setTalle(talle);
        beanMedidas.setSise(sise);
        beanMedidas.setHombros(hombros);
        beanMedidas.setBusto(busto);
        beanMedidas.setLargoFalda(largoFalda);
        beanMedidas.setAnchoPuño(anchoPuño);
        beanMedidas.setCintura(cintura);
        beanMedidas.setCadera(cadera);
        beanMedidas.setProductosApartados_id(ultimo_id_apartados);

        boolean banM = daoMedidas.registrar(beanMedidas);
        if (banM) {
            ban = true;
        } else {

        }

        return ban;
    }

    public boolean registraFechas(String fechaPrueba) {
        FechasPruebas bean = new FechasPruebas();
        DaoFechasPruebas dao = new DaoFechasPruebas();
        boolean ban = false;
        int ultimo_id_apartados = daoMedidas.obtener_id();
        //llenamso el bean de fechas
        bean.setFechaPrueba(fechaPrueba);
        bean.setProductosApartados_id(ultimo_id_apartados);
        boolean banF = dao.registrar(bean);
        if (banF) {
            ban = true;
        } else {

        }

        return ban;

    }

    public boolean registrarProductosVendidos() {
        boolean ban = false;
        DaoProductosVendidos dao = new DaoProductosVendidos();
        ProductosVendidos bean = new ProductosVendidos();
        int ultimo_id_apartados = daoMedidas.obtener_id();
        bean.setProductosApartados_id(ultimo_id_apartados);
        boolean banPV = dao.registrar(bean);
        if (banPV) {
            ban = true;
        } else {

        }

        return ban;
    }

    public boolean resgistraDeudaTotla() {
        boolean ban = false;
        DeudaTotal bean = new DeudaTotal();
        DaoDeudaTotal dao = new DaoDeudaTotal();
        bean.setDeudaTotal(Integer.parseInt(Todo.jTextField2.getText().toString()));
        bean.setCliente_id(Todo.idCliente);
        bean.setStatus("Pagado");
        boolean banDT = dao.registrar(bean);
        if (banDT) {
            ban = true;
        } else {

        }
        return ban;
    }
    /* cambiasmo de estatus en deuda total */

    public boolean resgistraDeudaTotla2() {
        boolean ban = false;
        DeudaTotal bean = new DeudaTotal();
        DaoDeudaTotal dao = new DaoDeudaTotal();
        bean.setDeudaTotal(Integer.parseInt(Todo.jTextField2.getText().toString()));
        bean.setCliente_id(Todo.idCliente);
        bean.setStatus("No pagado");
        boolean banDT = dao.registrar(bean);
        if (banDT) {
            ban = true;
        } else {

        }
        return ban;
    }
//===================== hasta aqui ========================

    public boolean registrarPagos() {
        boolean ban = false;
        Pagos bean = new Pagos();
        DaoPagos dao = new DaoPagos();
        int ultimo_id_deudaTotal = dao.obtener_id_deudaTotal();
        bean.setAbono(pago);
        bean.setDeudaTotal_id(ultimo_id_deudaTotal);
        boolean banP = dao.registrar(bean);
        if (banP) {
            ban = true;

        } else {

        }

        return ban;
    }
}
