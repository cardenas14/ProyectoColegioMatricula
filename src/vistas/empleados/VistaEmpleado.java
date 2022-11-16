/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas.empleados;

import controladores.EmpleadoControlador;
import controladores.TipoDocumentoControlador;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import metodos.MensajeRespuesta;
import modelos.Empleado;
import modelos.TipoDocumento;
import modelos.jtable.EmpleadoTableModel;

/**
 *
 * @author Novoa
 */
public class VistaEmpleado extends javax.swing.JFrame {

    EmpleadoControlador ec = new EmpleadoControlador();
    EmpleadoTableModel etm;
    MensajeRespuesta respuesta;
    
    /**
     * Creates new form VistaEmpleado
     */
    public VistaEmpleado() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.txt_id.setVisible(false);
        this.rdb_m.setSelected(true);
        this.chx_esta.setSelected(true);
        buscarEmpleado();
        llenarTipoDocumentos();
    }
    
    void buscarEmpleado() {
        this.etm = new EmpleadoTableModel(this.ec.index(this.txt_bus.getText()));
        this.tbl_datos.setModel(etm);
    }
    
    void llenarTipoDocumentos() {
        DefaultComboBoxModel<TipoDocumento> comboBoxModel = new DefaultComboBoxModel<TipoDocumento>();
        List<TipoDocumento> tipoDocumentos = new TipoDocumentoControlador().index();
        tipoDocumentos.forEach((tp) -> {
            comboBoxModel.addElement(tp);
        });
        this.cbx_tipo.setModel(comboBoxModel);
    }
    
    void nuevoEmpleado() {
        this.txt_id.setText("");
        this.cbx_tipo.setSelectedIndex(0);
        this.txt_num.setText("");
        this.txt_pri.setText("");
        this.txt_seg.setText("");
        this.txt_pat.setText("");
        this.txt_mat.setText("");
        this.dch_nac.setDate(null);
        this.rdb_m.setSelected(true);
        this.txt_dir.setText("");
        this.txt_tel.setText("");
        this.cbx_car.setSelectedIndex(0);
        this.chx_esta.setSelected(true);
    }
    
    void buscarNumDoc() {
        if (!this.txt_num.getText().isEmpty()) {
            this.respuesta = ec.findByNumDoc(this.txt_num.getText());
            JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
        }
    }
    
    void guardarEmpleado() {
        String id = !this.txt_id.getText().isEmpty() ? this.txt_id.getText() : "";
        TipoDocumento tipoDocumento = this.cbx_tipo.getItemAt(this.cbx_tipo.getSelectedIndex());
        String numDoc = !this.txt_num.getText().isEmpty() ? this.txt_num.getText() : null;
        String priNom = !this.txt_pri.getText().isEmpty() ? this.txt_pri.getText() : null;
        String segNom = !this.txt_seg.getText().isEmpty() ? this.txt_seg.getText() : null;
        String apePat = !this.txt_pat.getText().isEmpty() ? this.txt_pat.getText() : null;
        String apeMat = !this.txt_mat.getText().isEmpty() ? this.txt_mat.getText() : null;
        Date fecNac = this.dch_nac.getDate();
        String genero = rdb_m.isSelected() ? "M" : "F";
        String direccion = !this.txt_dir.getText().isEmpty() ? this.txt_dir.getText() : null;
        String telefono = !this.txt_tel.getText().isEmpty() ? this.txt_tel.getText() : null;
        String cargo = this.cbx_car.getSelectedItem().toString();
        Boolean estado = this.chx_esta.isSelected();
        this.respuesta = this.ec.save(id, tipoDocumento, numDoc, priNom, segNom, 
                apePat, apeMat, fecNac, genero, direccion, telefono, cargo, estado);
        if (!this.respuesta.getError()) {
            buscarEmpleado();
            if (this.respuesta.getObjetoDevuelto() != null) {
                this.txt_id.setText(this.respuesta.getObjetoDevuelto().getId());
            }
        }
        JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
    }
    
    Empleado getEmpleadoAt() {
        Empleado empleado = null;
        int fila = this.tbl_datos.getSelectedRow();
        if (fila >= 0) {
            empleado = this.etm.getEmpleadoAt(fila);
        }
        return empleado;
    }
    
    void editarEmpleado() {
        Empleado empleado = this.getEmpleadoAt();
        if (empleado != null) {
            this.nuevoEmpleado();
            this.txt_id.setText(empleado.getId());
            this.cbx_tipo.setSelectedItem(empleado.getTipoDocumento());
            this.txt_num.setText(empleado.getNumDoc());
            this.txt_pri.setText(empleado.getPriNom());
            this.txt_seg.setText(empleado.getSegNom());
            this.txt_pat.setText(empleado.getApePat());
            this.txt_mat.setText(empleado.getApeMat());
            this.dch_nac.setDate(empleado.getFecNac());
            this.rdb_m.setSelected(empleado.getGenero().equals("M"));
            this.txt_dir.setText(empleado.getDireccion());
            this.txt_tel.setText(empleado.getTelefono());
            this.cbx_car.setSelectedItem(empleado.getCargo());
            this.chx_esta.setSelected(empleado.getEstado());
        }
    }
    
    void eliminarEmpleado() {
        Empleado empleado = this.getEmpleadoAt();
        if (empleado != null) {
            int rs = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de querer eliminar el Empleado?");
            if (rs == 0) {
                respuesta = ec.delete(empleado.getId());
                if (!respuesta.getError()) {
                    buscarEmpleado();
                }
                JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        popup_edit = new javax.swing.JMenuItem();
        popup_eli = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_bus = new javax.swing.JTextField();
        btn_bus = new javax.swing.JButton();
        btn_nue = new javax.swing.JButton();
        btn_cer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_datos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbx_tipo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_num = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_pri = new javax.swing.JTextField();
        txt_seg = new javax.swing.JTextField();
        txt_pat = new javax.swing.JTextField();
        txt_mat = new javax.swing.JTextField();
        rdb_m = new javax.swing.JRadioButton();
        rdb_f = new javax.swing.JRadioButton();
        txt_dir = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_tel = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbx_car = new javax.swing.JComboBox<>();
        chx_esta = new javax.swing.JCheckBox();
        btn_guar = new javax.swing.JButton();
        btn_bus_dni = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        dch_nac = new com.toedter.calendar.JDateChooser();

        popup_edit.setText("Editar");
        popup_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_editActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_edit);

        popup_eli.setText("Eliminar");
        popup_eli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_eliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_eli);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Empleados"));

        jLabel1.setText("Buscar");

        txt_bus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busKeyReleased(evt);
            }
        });

        btn_bus.setText("Buscar");
        btn_bus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_busActionPerformed(evt);
            }
        });

        btn_nue.setText("Nuevo");
        btn_nue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nueActionPerformed(evt);
            }
        });

        btn_cer.setText("Cerrar");
        btn_cer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerActionPerformed(evt);
            }
        });

        tbl_datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_datos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_datos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tbl_datos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_bus)
                        .addGap(18, 18, 18)
                        .addComponent(btn_nue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cer))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_bus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bus)
                    .addComponent(btn_nue)
                    .addComponent(btn_cer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Operaciones"));

        jLabel2.setText("Tipo Doc.");

        jLabel3.setText("Num. Doc.");

        txt_num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numActionPerformed(evt);
            }
        });

        jLabel4.setText("Prim. Nombre");

        jLabel5.setText("Seg. Nombre");

        jLabel6.setText("Ape. Paterno");

        jLabel7.setText("Ap. Materno");

        jLabel8.setText("Fec. Nacimiento");

        jLabel9.setText("Genero");

        jLabel10.setText("Dirección");

        buttonGroup1.add(rdb_m);
        rdb_m.setText("M");

        buttonGroup1.add(rdb_f);
        rdb_f.setText("F");

        jLabel11.setText("Teléfono");

        jLabel12.setText("Cargo");

        jLabel13.setText("Estado");

        cbx_car.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PROFESOR(A)", "RECEPCIONISTA" }));

        chx_esta.setText("Activo");

        btn_guar.setText("Guardar");
        btn_guar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guarActionPerformed(evt);
            }
        });

        btn_bus_dni.setText("Bus");
        btn_bus_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bus_dniActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbx_car, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_pat)
                            .addComponent(txt_mat)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rdb_m)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdb_f))
                                    .addComponent(chx_esta)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_guar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_dir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_pri, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_bus_dni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(cbx_tipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_seg, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dch_nac, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbx_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bus_dni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_pri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_seg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_pat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_mat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(dch_nac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdb_m)
                    .addComponent(rdb_f)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbx_car, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(chx_esta))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guar)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

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

    private void txt_busKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busKeyReleased
        this.buscarEmpleado();
    }//GEN-LAST:event_txt_busKeyReleased

    private void btn_busActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busActionPerformed
        this.buscarEmpleado();
    }//GEN-LAST:event_btn_busActionPerformed

    private void txt_numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numActionPerformed
        this.buscarNumDoc();
    }//GEN-LAST:event_txt_numActionPerformed

    private void btn_bus_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_dniActionPerformed
        this.buscarNumDoc();
    }//GEN-LAST:event_btn_bus_dniActionPerformed

    private void btn_guarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guarActionPerformed
        if (this.txt_num.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Número de Documento en Blanco.");
        } else if (this.txt_pri.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nombre en Blanco.");
        } else if (this.txt_pat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Apellido Paterno en Blanco.");
        } else if (this.txt_mat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Apellido Materno en Blanco.");
        } else if (this.dch_nac.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Fec. Nacimiento en Blanco.");
        } else if (this.txt_dir.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Dirección en Blanco.");
        } else if (this.txt_tel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Teléfono en Blanco.");
        } else {
            guardarEmpleado();
        }
    }//GEN-LAST:event_btn_guarActionPerformed

    private void btn_nueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nueActionPerformed
        this.nuevoEmpleado();
    }//GEN-LAST:event_btn_nueActionPerformed

    private void popup_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_editActionPerformed
        this.editarEmpleado();
    }//GEN-LAST:event_popup_editActionPerformed

    private void popup_eliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_eliActionPerformed
        this.eliminarEmpleado();
    }//GEN-LAST:event_popup_eliActionPerformed

    private void btn_cerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerActionPerformed

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
            java.util.logging.Logger.getLogger(VistaEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bus;
    private javax.swing.JButton btn_bus_dni;
    private javax.swing.JButton btn_cer;
    private javax.swing.JButton btn_guar;
    private javax.swing.JButton btn_nue;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbx_car;
    private javax.swing.JComboBox<TipoDocumento> cbx_tipo;
    private javax.swing.JCheckBox chx_esta;
    private com.toedter.calendar.JDateChooser dch_nac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem popup_edit;
    private javax.swing.JMenuItem popup_eli;
    private javax.swing.JRadioButton rdb_f;
    private javax.swing.JRadioButton rdb_m;
    private javax.swing.JTable tbl_datos;
    private javax.swing.JTextField txt_bus;
    private javax.swing.JTextField txt_dir;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_mat;
    private javax.swing.JTextField txt_num;
    private javax.swing.JTextField txt_pat;
    private javax.swing.JTextField txt_pri;
    private javax.swing.JTextField txt_seg;
    private javax.swing.JTextField txt_tel;
    // End of variables declaration//GEN-END:variables
}
