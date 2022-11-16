/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas.cursos;

import controladores.CategoriaCursoControlador;
import controladores.CursoControlador;
import controladores.ProfesorControlador;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import metodos.MensajeRespuesta;
import modelos.CategoriaCurso;
import modelos.Curso;
import modelos.Profesor;
import modelos.jtable.CursoTableModel;

/**
 *
 * @author Novoa
 */
public class VistaCurso extends javax.swing.JFrame {

    CursoControlador cc = new CursoControlador();
    CursoTableModel ctm;
    static Profesor profesor;
    MensajeRespuesta respuesta;
    
    /**
     * Creates new form VistaEmpleado
     */
    public VistaCurso() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.txt_id.setVisible(false);
        this.txt_prof.setEditable(false);
        this.chx_esta.setSelected(true);
        buscarCurso();
        llenarCategoriaCurso();
    }
    
    void buscarCurso() {
        this.ctm = new CursoTableModel(this.cc.index(this.txt_bus.getText()));
        this.tbl_datos.setModel(ctm);
    }
    
    void llenarCategoriaCurso() {
        DefaultComboBoxModel<CategoriaCurso> comboBoxModel = new DefaultComboBoxModel<CategoriaCurso>();
        List<CategoriaCurso> categoriaCursos = new CategoriaCursoControlador().index("");
        categoriaCursos.forEach((tp) -> {
            comboBoxModel.addElement(tp);
        });
        this.cbx_cat.setModel(comboBoxModel);
    }
    
    void nuevoCurso() {
        this.profesor = null;
        this.txt_id.setText("");
        this.txt_num.setText("");
        this.txt_prof.setText("");
        this.cbx_cat.setSelectedIndex(0);
        this.txt_nom.setText("");
        this.txt_des.setText("");
        this.jdc_ini.setDate(null);
        this.jdc_ter.setDate(null);
        this.cbx_dur.setSelectedIndex(0);
        this.txt_cos.setText("");
        this.chx_esta.setSelected(true);
    }
    
    void buscarProfesor() {
        if (!this.txt_num.getText().isEmpty()) {
            ProfesorControlador pc = new ProfesorControlador();
            this.respuesta = pc.findByNumDoc(this.txt_num.getText());
            if (this.respuesta.getObjetoDevuelto() != null) {
                this.profesor = pc.findId(this.respuesta.getObjetoDevuelto().getId());
                this.txt_prof.setText(this.profesor.toString());
            } else {
                this.profesor = null;
                this.txt_prof.setText("");
                JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
            }
        } else {
            new FormProfesor().setVisible(true);
        }
    }
    
    public static void seleccionarProfesor(Profesor profesor) {
        VistaCurso.profesor = profesor;
        VistaCurso.txt_prof.setText(VistaCurso.profesor.toString());
    }
    
    void guardarCurso() {
        String id = !this.txt_id.getText().isEmpty() ? this.txt_id.getText() : "";
        String numDoc = !this.txt_num.getText().isEmpty() ? this.txt_num.getText() : null;
        CategoriaCurso categoriaCurso = this.cbx_cat.getItemAt(this.cbx_cat.getSelectedIndex());
        String nombre = !this.txt_nom.getText().isEmpty() ? this.txt_nom.getText() : null;
        String descripcion = !this.txt_des.getText().isEmpty() ? this.txt_des.getText() : null;
        Date fecInicio = this.jdc_ini.getDate() != null ? this.jdc_ini.getDate() : null;
        Date fecTermino = this.jdc_ter.getDate() != null ? this.jdc_ter.getDate() : null;
        String duracion = this.cbx_dur.getSelectedItem().toString();
        Double costo = !this.txt_cos.getText().isEmpty() ? Double.valueOf(this.txt_cos.getText()) : null;
        Boolean estado = this.chx_esta.isSelected();
        this.respuesta = this.cc.save(id, categoriaCurso, this.profesor, nombre,
                descripcion, fecInicio, fecTermino, duracion, costo, estado);
        if (!this.respuesta.getError()) {
            buscarCurso();
            if (this.respuesta.getObjetoDevuelto() != null) {
                this.txt_id.setText(this.respuesta.getObjetoDevuelto().getId());
            }
        }
        JOptionPane.showMessageDialog(rootPane, respuesta.getMensaje());
    }
    
    Curso getCursoAt() {
        Curso curso = null;
        int fila = this.tbl_datos.getSelectedRow();
        if (fila >= 0) {
            curso = this.ctm.getCursoAt(fila);
        }
        return curso;
    }
    
    void editarCurso() {
        Curso curso = this.getCursoAt();
        if (curso != null) {
            this.nuevoCurso();
            profesor = curso.getProfesor();
            this.txt_id.setText(curso.getId());
            this.txt_num.setText(curso.getProfesor().getNumDoc());
            this.txt_prof.setText(curso.getProfesor().toString());
            this.cbx_cat.setSelectedItem(curso.getCategoriaCurso());
            this.txt_nom.setText(curso.getNombre());
            this.txt_des.setText(curso.getDescripcion());
            this.jdc_ini.setDate(curso.getFecInicio());
            this.jdc_ter.setDate(curso.getFecTermino());
            this.cbx_dur.setSelectedItem(curso.getDuracion());
            this.txt_cos.setText(String.valueOf(curso.getCosto()));
            this.chx_esta.setSelected(curso.getEstado());
        }
    }
    
    void eliminarCurso() {
        Curso curso = this.getCursoAt();
        if (curso != null) {
            int rs = JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de querer eliminar el Curso?");
            if (rs == 0) {
                respuesta = cc.delete(curso.getId());
                if (!respuesta.getError()) {
                    buscarCurso();
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
        jLabel3 = new javax.swing.JLabel();
        txt_num = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_prof = new javax.swing.JTextField();
        txt_nom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbx_dur = new javax.swing.JComboBox<>();
        chx_esta = new javax.swing.JCheckBox();
        btn_guar = new javax.swing.JButton();
        btn_bus_dni = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_des = new javax.swing.JTextArea();
        jdc_ini = new com.toedter.calendar.JDateChooser();
        jdc_ter = new com.toedter.calendar.JDateChooser();
        txt_cos = new javax.swing.JTextField();
        cbx_cat = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

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
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Cursos"));

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
                        .addGap(0, 0, Short.MAX_VALUE)))
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

        jLabel3.setText("Num. Doc.");

        txt_num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numActionPerformed(evt);
            }
        });

        jLabel4.setText("Profesor");

        jLabel5.setText("Nom. Curso");

        jLabel6.setText("Descripción");

        jLabel7.setText("Fecha Inicio");

        jLabel10.setText("Fecha Termino");

        jLabel11.setText("Duración");

        jLabel12.setText("Costo");

        jLabel13.setText("Estado");

        cbx_dur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 SEMANA", "2 SEMANAS", "3 SEMANAS", "4 SEMANAS", "5 SEMANAS", "6 SEMAMAS", "7 SEMANAS", "8 SEMANAS", "9 SEMANAS", "10 SEMANAS", "1 MES", "2 MESES", "3 MESES", "4 MESES", "5 MESES", "6 MESES", "7 MESES", "8 MESES", "9 MESES", "10 MESES", "11 MESES", "12 MESES", "1 AÑO" }));

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

        txt_des.setColumns(20);
        txt_des.setRows(5);
        jScrollPane2.setViewportView(txt_des);

        jLabel8.setText("Categoría");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdc_ini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdc_ter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbx_dur, 0, 130, Short.MAX_VALUE)
                            .addComponent(txt_cos))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txt_nom, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_prof, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(chx_esta, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_guar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_bus_dni, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbx_cat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(32, 32, 32))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bus_dni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_prof, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_cat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdc_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jdc_ter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbx_dur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(chx_esta))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guar)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        this.buscarCurso();
    }//GEN-LAST:event_txt_busKeyReleased

    private void btn_busActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_busActionPerformed
        this.buscarCurso();
    }//GEN-LAST:event_btn_busActionPerformed

    private void txt_numActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numActionPerformed
        this.buscarProfesor();
    }//GEN-LAST:event_txt_numActionPerformed

    private void btn_bus_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bus_dniActionPerformed
        this.buscarProfesor();
    }//GEN-LAST:event_btn_bus_dniActionPerformed

    private void btn_guarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guarActionPerformed
        if (this.txt_prof.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Profesor en Blanco.");
        } else if (this.txt_nom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nombre del Curso en Blanco.");
        } else if (this.txt_des.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Descripción del Curso en Blanco.");
        } else if (this.jdc_ini.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Fecha de Inicio en Blanco.");
        } else if (this.jdc_ter.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Fecha de Término en Blanco.");
        } else if (this.txt_cos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Campo Costo del Curso en Blanco.");
        } else {
            guardarCurso();
        }
    }//GEN-LAST:event_btn_guarActionPerformed

    private void btn_nueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nueActionPerformed
        this.nuevoCurso();
    }//GEN-LAST:event_btn_nueActionPerformed

    private void popup_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_editActionPerformed
        this.editarCurso();
    }//GEN-LAST:event_popup_editActionPerformed

    private void popup_eliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popup_eliActionPerformed
        this.eliminarCurso();
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
            java.util.logging.Logger.getLogger(VistaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCurso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCurso().setVisible(true);
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
    private javax.swing.JComboBox<CategoriaCurso> cbx_cat;
    private javax.swing.JComboBox<String> cbx_dur;
    private javax.swing.JCheckBox chx_esta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdc_ini;
    private com.toedter.calendar.JDateChooser jdc_ter;
    private javax.swing.JMenuItem popup_edit;
    private javax.swing.JMenuItem popup_eli;
    private javax.swing.JTable tbl_datos;
    private javax.swing.JTextField txt_bus;
    private javax.swing.JTextField txt_cos;
    private javax.swing.JTextArea txt_des;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nom;
    private javax.swing.JTextField txt_num;
    private static javax.swing.JTextField txt_prof;
    // End of variables declaration//GEN-END:variables
}
