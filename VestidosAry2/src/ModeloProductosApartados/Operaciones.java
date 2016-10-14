/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloProductosApartados;

import ModeloFechasPruebas.DaoFechasPruebas;
import ModeloFechasPruebas.FechasPruebas;
import ModeloMedidas.DaoMedidas;
import ModeloMedidas.Medidas;
import static Pantallas.Todo.idCliente;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Usuario
 */
public class Operaciones {
    Medidas beanMedidas = new Medidas();
    DaoMedidas daoMedidas = new DaoMedidas();
    public boolean registrar(JTable jTable3,double arreMedidas[][],String arreFechas[][],String estado){
         boolean banr=false;
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
                        for (int k = 0; k < arreFechas.length; k++) {//vemos si existe el id en el arre fechas
                            System.out.println("entramos al for para buscar fechas");
                            if (Integer.parseInt(idProducto.toString()) == Integer.parseInt(String.valueOf(arreFechas[k][0]))) {//si si existe obtenemos las fechas
                                System.out.println("si tiene fechas");
                                beanPA.setFechaEntrega(arreFechas[k][2]);
                                boolean ban=daoPA.registrarVenta(beanPA);
                                if (ban) {
                                    
                                    
                                    banr = true;
                                    
                                    boolean banM=registrarMedidas(arreMedidas[i][1],arreMedidas[i][2],arreMedidas[i][3],arreMedidas[i][4],arreMedidas[i][5],arreMedidas[i][6],arreMedidas[i][7],arreMedidas[i][8]);
                                     if(banM){
                                         JOptionPane.showMessageDialog(null, "Las medidas se registraron correctamente");
                                    boolean banFechas=registraFechas(arreFechas[k][1]);
                                     if(banFechas){
                                         JOptionPane.showMessageDialog(null, "La fecha prueba se registro correctamente");
                                     }else{
                                         JOptionPane.showMessageDialog(null, "Erro al registra la fecha Prueba " + "ERROR " + 0);
                                     }
                                     }else{
                                         JOptionPane.showMessageDialog(null, "Erro al registrar las medidas " + "ERROR " + 0);
                                     }
                                } else {
                                    

                                }
  
                            } else {
                                //si no tiene fechas

                            }
                        }
                    } else {
                        System.out.println(" no encontro el id en el arreglo");
                    }

                }

            }
       
        
        return banr;
    }
    
    public boolean registrarMedidas(double talle,double sise,double hombros,double busto,double largoFalda,double anchoPuño,double cintura,double cadera){
        boolean ban=false;
        //buscar el ultimo registro en la tabla apartados
        int ultimo_id_apartados=daoMedidas.obtener_id();
        
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
          
          boolean banM=daoMedidas.registrar(beanMedidas);
          if(banM){
              ban=true;
          }else{
              
          }
          
          
        
        return ban;
    }
    
    public boolean registraFechas(String fechaPrueba){
        FechasPruebas bean= new FechasPruebas();
        DaoFechasPruebas dao = new DaoFechasPruebas();
        boolean ban=false;
         int ultimo_id_apartados=daoMedidas.obtener_id();
        //llenamso el bean de fechas
        bean.setFechaPrueba(fechaPrueba);
        bean.setProductosApartados_id(ultimo_id_apartados);
        boolean banF=dao.registrar(bean);
        if(banF){
            ban=true;
        }else{
            
        }
        
        
        return ban;
        
    }
}
