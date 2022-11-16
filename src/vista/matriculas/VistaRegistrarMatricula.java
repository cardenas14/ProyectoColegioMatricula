/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.matriculas;

import controladores.ClienteControlador;
import controladores.CursoControlador;
import controladores.DetalleMatriculaControlador;
import controladores.MatriculaControlador;
import controladores.TipoComprobanteControlador;
import controladores.TipoMatriculaControlador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import metodos.HelperServicio;
import metodos.MensajeRespuesta;
import modelos.Cliente;
import modelos.Curso;
import modelos.DetalleMatricula;
import modelos.TipoComprobante;
import modelos.TipoMatricula;
import modelos.Usuario;
import modelos.jtable.DetalleMatriculaTableModel;

/**
 *
 * @author Novoa
 */
public class VistaRegistrarMatricula extends javax.swing.JFrame {
    
    DetalleMatriculaTableModel dmstm;
    ArrayList<DetalleMatricula> detalleMatriculas = new ArrayList<>();
    static Cliente cliente;
    Usuario usuario;
    MensajeRespuesta respuesta;
    
    /**
     * Creates new form VistaEmpleado
     */
    public VistaRegistrarMatricula() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.jdc_fecha.setDate(new Date());
        this.txt_clie.setEditable(false);
        this.txt_montoApli.setVisible(false);
        this.txt_total.setEditable(false);
        this.txt_total.setText("0.0");
        actualizarDetalleMatricula();
        llenarCurso();
        llenarTipoComprobante();
        llenarTipoMatricula();
        seleccionarTipoComprobante();
        seleccionarTipoMatricula();
    }
    
    public VistaRegistrarMatricula(Usuario usuario) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.jdc_fecha.setDate(new Date());
        this.txt_clie.setEditable(false);
        this.txt_montoApli.setVisible(false);
        this.txt_total.setEditable(false);
        this.txt_total.setText("0.0");
        actualizarDetalleMatricula();
        llenarCurso();
        llenarTipoComprobante();
        llenarTipoMatricula();
        seleccionarTipoComprobante();
        seleccionarTipoMatricula();
        this.usuario = usuario;
    }
    
    void actualizarDetalleMatricula() {
        this.dmstm = new DetalleMatriculaTableModel(this.detalleMatriculas);
        this.tbl_datos.setModel(dmstm);
    }
    
    void agregarCurso() {
        Curso curso = this.cbx_cursos.getItemAt(this.cbx_cursos.getSelectedIndex());
        Boolean cursoRepetido = false;
        double total = 0.0, adicional = 0.0;
        for (DetalleMatricula detalleMatricula : detalleMatriculas) {
            if (curso.getId() == detalleMatricula.getCurso().getId()) {
                cursoRepetido = true;
            }
            total = total + detalleMatricula.getCurso().getCosto();
        }
        if (!cursoRepetido) {
            this.detalleMatriculas.add(new DetalleMatricula(
                null, null, curso, curso.getCosto(), curso.getCosto()
            ));
            actualizarDetalleMatricula();
            total = total + curso.getCosto();
        }
        adicional = total * (Double.parseDouble(this.txt_porc.getText())/100);
        total = total + adicional;
        this.lbl_porc_moneda.setText("% = S/. " + String.valueOf(adicional));
        this.txt_montoApli.setText(String.valueOf(adicional));
        this.txt_total.setText(String.valueOf(total));
    }
    
    void llenarCurso() {
        DefaultComboBoxModel<Curso> comboBoxModel = new DefaultComboBoxModel<>();
        List<Curso> cursos = new CursoControlador().findByEstado(true);
        cursos.forEach((tp) -> {
            comboBoxModel.addElement(tp);
        });
        this.cbx_cursos.setModel(comboBoxModel);
    }
    
    void llenarTipoComprobante() {
        DefaultComboBoxModel<TipoComprobante> comboBoxModel = new DefaultComboBoxModel<>();
        List<TipoComprobante> tipoComprobantes = new TipoComprobanteControlador().index("");
        tipoComprobantes.forEach((tp) -> {
            comboBoxModel.addElement(tp);
        });
        this.cbx_tpcom.setModel(comboBoxModel);
    }
    
    void llenarTipoMatricula() {
        DefaultComboBoxModel<TipoMatricula> comboBoxModel = new DefaultComboBoxModel<>();
        List<TipoMatricula> tipoMatriculas = new TipoMatriculaControlador().index("");
        tipoMatriculas.forEach((tp) -> {
            comboBoxModel.addElement(tp);
        });
        this.cbx_tpmat.setModel(comboBoxModel);
    }
    
    void buscarCliente() {
        if (!this.txt_num.getText().isEmpty()) {
            ClienteControlador cc = new ClienteControlador();
            this.respuesta = cc.findByNumDoc(this.txt_num.getText());
            if (this.respuesta.getObjetoDevuelto() != null) {
                this.cliente = cc.findId(this.respuesta.getObjetoDevuelto().getId());
                this.txt_clie.setText(this.cliente.toString());
            } else {
                this.cliente = null;
                this.txt_clie.setText("");
                new FormCliente(this.txt_num.getText()).setVisible(true);
                JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
            }
        } else {
            new TableCliente().setVisible(true);
        }
    }
    
    public static void seleccionarCliente(Cliente cliente) {
        VistaRegistrarMatricula.cliente = cliente;
        VistaRegistrarMatricula.txt_clie.setText(VistaRegistrarMatricula.cliente.toString());
    }
    
    void guardarMatricula() {
        TipoMatricula tipoMatricula = this.cbx_tpmat.getItemAt(this.cbx_tpmat.getSelectedIndex());
        Double montoAplicado = !this.txt_montoApli.getText().isEmpty() ? Double.parseDouble(this.txt_montoApli.getText()) : 0;
        TipoComprobante tipoComprobante = this.cbx_tpcom.getItemAt(this.cbx_tpcom.getSelectedIndex());
        Date fecha = this.jdc_fecha.getDate() != null ? this.jdc_fecha.getDate() : null;
        Double costo = !this.txt_total.getText().isEmpty() ? Double.valueOf(this.txt_total.getText()) : null;
        MatriculaControlador mc = new MatriculaControlador();
        DetalleMatriculaControlador dmc = new DetalleMatriculaControlador();
        this.respuesta = mc.save(null, this.usuario.getEmpleado(), this.cliente,
                tipoMatricula, tipoMatricula.getPorcAplicado(), montoAplicado,
                tipoComprobante, null, fecha, costo, null, null);
        if (!this.respuesta.getError()) {
            if (this.respuesta.getObjetoDevuelto() != null) {
                this.respuesta = dmc.save(this.respuesta.getObjetoDevuelto().getId(), this.detalleMatriculas);
                if (!this.respuesta.getError()) {
                    this.dispose();
                    new VistaMatricula(this.usuario).setVisible(true);
                    JOptionPane.showMessageDialog(rootPane, "Matricula registrada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
                }
            }
        }
    }
    
    /*DetalleMatricula getDetalleMatriculaAt() {
        DetalleMatricula detalleMatricula = null;
        int fila = this.tbl_datos.getSelectedRow();
        if (fila >= 0) {
            detalleMatricula = this.dmstm.getDetalleMatriculaAt(fila);
        }
        return detalleMatricula;
    }*/
    
    void eliminarCurso() {
        int rs = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de querer quitar el Curso?");
        if (rs == 0) {
            int fila = this.tbl_datos.getSelectedRow();
            if (fila >= 0) {
                detalleMatriculas.remove(fila);
                actualizarDetalleMatricula();
                seleccionarTipoMatricula();
            }
        }
    }
    
    void seleccionarTipoComprobante() {
        TipoComprobanteControlador tcc = new TipoComprobanteControlador();
        TipoComprobante tipoComprobante = this.cbx_tpcom.getItemAt(this.cbx_tpcom.getSelectedIndex());
        tipoComprobante = tcc.findId(tipoComprobante.getId());
        String correlativo = String.valueOf(tipoComprobante.getCorrelativo() + 1);
        this.lbl_num_com.setText(tipoComprobante.getAbreviatura() + " N°" + 
                HelperServicio.correlativo(correlativo));
    }
    
    void seleccionarTipoMatricula() {
        TipoMatriculaControlador tmc = new TipoMatriculaControlador();
        TipoMatricula tipoMatricula = this.cbx_tpmat.getItemAt(this.cbx_tpmat.getSelectedIndex());
        this.txt_porc.setText(String.valueOf(tipoMatricula.getPorcAplicado()));
        double total = totalCursos();
        double adicional = total * (tipoMatricula.getPorcAplicado()/100);
        total = total + adicional;
        this.txt_total.setText(String.valueOf(total));
        this.txt_montoApli.setText(String.valueOf(adicional));
        this.lbl_porc_moneda.setText("% = S/. " + String.valueOf(adicional));
    }
    
    double totalCursos() {
        double total = 0.0;
        for (DetalleMatricula detalleMatricula : detalleMatriculas) {
            total = total + detalleMatricula.getCurso().getCosto();
        }
        return total;
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
        popup_eli = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_agregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_datos = new javax.swing.JTable();
        cbx_cursos = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_num = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_clie = new javax.swing.JTextField();
        txt_porc = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btn_guar = new javax.swing.JButton();
        btn_bus_dni = new javax.swing.JButton();
        jdc_fecha = new com.toedter.calendar.JDateChooser();
        txt_total = new javax.swing.JTextField();
        cbx_tpmat = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbx_tpcom = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lbl_num_com = new javax.swing.JLabel();
        lbl_porc_moneda = new javax.swing.JLabel();
        txt_montoApli = new javax.swing.JTextField();

        popup_eli.setText("Eliminar");
        popup_eli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popup_eliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popup_eli);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Matrícula");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Cursos"));

        jLabel1.setText("Buscar");

        btn_agregar.setText("Agregar");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
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
                        .addComponent(cbx_cursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_agregar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btn_agregar)
                    .addComponent(cbx_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Operaciones"));

        jLabel3.setText("Num. Doc.");

        txt_num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numActionPerformed(evt);
            }
        });

        jLabel4.setText("Cliente");

        jLabel5.setText("Porc. Aplicado");

        jLabel7.setText("Fecha");

        jLabel12.setText("Total S/.");

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

        cbx_tpmat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tpmatActionPerformed(evt);
            }
        });

        jLabel8.setText("Tipo Matrícula");

        cbx_tpcom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tpcomActionPerformed(evt);
            }
        });

        jLabel9.setText("Comprobante");

        lbl_num_com.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_num_com.setForeground(new java.awt.Color(255, 51, 51));
        lbl_num_com.setText("BV 0000001");

        lbl_porc_moneda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_porc_moneda.setText("% = S/.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_guar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_montoApli, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbx_tpcom, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_num_com, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txt_clie, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbx_tpmat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_bus_dni, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_porc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_porc_moneda)
                                .addGap(142, 142, 142)))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbx_tpcom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_num_com))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bus_dni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_clie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_tpmat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_porc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_porc_moneda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guar)
                    .addComponent(txt_montoApli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
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

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        this.agregarCurso();
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void txt_numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numActionPerformed
        this.buscarCliente();
    }//GEN-LAST:event_txt_numActionPerformed

    private void btn_bus_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_dniActionPerformed
        this.buscarCliente();
    }//GEN-LAST:event_btn_bus_dniActionPerformed

    private void btn_guarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guarActionPerformed
        if (this.jdc_fecha.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Fecha de Matrícula en Blanco.");
        } else if (this.txt_clie.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Cliente en Blanco.");
        } else if (this.tbl_datos.getRowCount() < 1) {
            JOptionPane.showMessageDialog(rootPane, "Ingrese los Cursos a Matricular.");
        } else {
            guardarMatricula();
        }
    }//GEN-LAST:event_btn_guarActionPerformed

    private void popup_eliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_eliActionPerformed
        this.eliminarCurso();
    }//GEN-LAST:event_popup_eliActionPerformed

    private void cbx_tpcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tpcomActionPerformed
        seleccionarTipoComprobante();
    }//GEN-LAST:event_cbx_tpcomActionPerformed

    private void cbx_tpmatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tpmatActionPerformed
        seleccionarTipoMatricula();
    }//GEN-LAST:event_cbx_tpmatActionPerformed

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
            java.util.logging.Logger.getLogger(VistaRegistrarMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaRegistrarMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaRegistrarMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaRegistrarMatricula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new VistaRegistrarMatricula().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_bus_dni;
    private javax.swing.JButton btn_guar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<Curso> cbx_cursos;
    private javax.swing.JComboBox<TipoComprobante> cbx_tpcom;
    private javax.swing.JComboBox<TipoMatricula> cbx_tpmat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdc_fecha;
    private javax.swing.JLabel lbl_num_com;
    private javax.swing.JLabel lbl_porc_moneda;
    private javax.swing.JMenuItem popup_eli;
    private javax.swing.JTable tbl_datos;
    private static javax.swing.JTextField txt_clie;
    private javax.swing.JTextField txt_montoApli;
    private javax.swing.JTextField txt_num;
    private javax.swing.JTextField txt_porc;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
