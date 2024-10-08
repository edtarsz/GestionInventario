/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.GUI;

import com.mycompany.Conexion.Conexion;
import com.mycompany.Conexion.IConexion;
import com.mycompany.Main.ControlInventario;
import com.mycompany.Main.GestionInventario;
import com.mycompany.Main.IInventariar;
import com.mycompany.Main.IInventario;
import com.mycompany.Main.Producto;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
 */
public class frmInventariar extends javax.swing.JFrame {

    IInventariar inventariar;
    IInventario inventario;
    IConexion conexion;
    List<Producto> productos = null;

    /**
     * Creates new form frmInventariar
     */
    public frmInventariar() {
        initComponents();
        conexion = new Conexion();
        this.inventariar = new GestionInventario(conexion);
        this.inventario = new ControlInventario(conexion);
        llenarTabla(productos);
    }

    public void resetCantidades() {
        txtNombre.setText("");
        txtInventariar.setText("");
    }

    private void llenarTabla(List<Producto> productos) {
        // Crear un modelo de tabla y añadir las columnas
        DefaultTableModel inventarioEncontrado = new DefaultTableModel();
        inventarioEncontrado.addColumn("Nombre");
        inventarioEncontrado.addColumn("Descripción");
        inventarioEncontrado.addColumn("Categoría");
        inventarioEncontrado.addColumn("Precio");
        inventarioEncontrado.addColumn("Cantidad");

        // Determinar la fuente de datos para llenar la tabla
        List<Producto> inventarioDespliegue;
        if (productos == null) {
            // Si productos es null, obtener el inventario completo
            inventarioDespliegue = inventario.obtenerInventarioCompleto();
        } else {
            // Usar la lista proporcionada
            inventarioDespliegue = productos;
        }

        // Añadir los datos al modelo de tabla
        for (Producto inventarioDesplegar : inventarioDespliegue) {
            Object[] fila = {
                inventarioDesplegar.getNombre(),
                inventarioDesplegar.getDescripcion(),
                inventarioDesplegar.getCategoria(),
                inventarioDesplegar.getPrecio(),
                inventarioDesplegar.getCantidad()
            };
            inventarioEncontrado.addRow(fila);
        }

        // Asignar el modelo a la tabla
        jTInventario.setModel(inventarioEncontrado);
    }

    public void mostrarMensajeError(String mensaje) {
        // Mostrar un cuadro de diálogo emergente con el mensaje de error
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Busca productos por nombre y actualiza la tabla con los resultados de la
     * búsqueda.
     *
     * @param nombre El nombre del producto a buscar.
     */
    private void buscarPorNombre(String nombre) {
        List<Producto> inventarioDespliegue = inventario.consultarProductosPorNombre(nombre);
        if (inventarioDespliegue != null && !inventarioDespliegue.isEmpty()) {
            llenarTabla(inventarioDespliegue);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron productos con el nombre especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorCateogoria(String nombre) {
        List<Producto> inventarioDespliegue = inventario.consultarProductosPorCategoria(nombre);
        if (inventarioDespliegue != null && !inventarioDespliegue.isEmpty()) {
            llenarTabla(inventarioDespliegue);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron productos con el nombre especificado.", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTInventario = new javax.swing.JTable();
        btnInventariar = new javax.swing.JButton();
        Limpiar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtInventariar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Inventariar productos");

        jTInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Categoría", "Cantidad"
            }
        ));
        jTInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTInventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTInventario);

        btnInventariar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInventariar.setText("Agregar");
        btnInventariar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventariarActionPerformed(evt);
            }
        });

        Limpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Limpiar.setText("Limpiar");
        Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Inventariar (Cantidad)");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Producto");

        jButton1.setText("Regresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Buscar por");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "Categoria" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(123, 123, 123)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(txtNombre)
                        .addComponent(btnInventariar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtInventariar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInventariar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnInventariar)
                        .addGap(18, 18, 18)
                        .addComponent(Limpiar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInventariarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventariarActionPerformed
        StringBuilder cambiosInventario = new StringBuilder();
        int seleccion = jTInventario.getSelectedRow();
        String nombrePrevio = jTInventario.getValueAt(seleccion, 0).toString();

        cambiosInventario.append("Producto: ").append(nombrePrevio).append(", Cantidad a actualizar: ").append(txtInventariar.getText()).append("x\n");

        if (seleccion == -1) {
            mostrarMensajeError("No se ha seleccionado ningún producto.");
            return;
        }

        // Mostrar un JOptionPane para confirmar la actualización del inventario
        int opcionConfirmacion = JOptionPane.showConfirmDialog(null, "¿Desea confirmar la actualización del inventario?\n\nCambios a realizar:\n\n" + cambiosInventario.toString(), "Confirmar actualización de inventario", JOptionPane.YES_NO_OPTION);

        if (opcionConfirmacion == JOptionPane.YES_OPTION) {
            inventario.inventariar(nombrePrevio, Integer.parseInt(txtInventariar.getText()));

            resetCantidades();
            llenarTabla(productos);
        } else {
            resetCantidades();
        }
    }//GEN-LAST:event_btnInventariarActionPerformed

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarActionPerformed
        resetCantidades();
    }//GEN-LAST:event_LimpiarActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void jTInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTInventarioMouseClicked
        int seleccion = jTInventario.getSelectedRow();
        txtNombre.setText(jTInventario.getValueAt(seleccion, 0).toString());
    }//GEN-LAST:event_jTInventarioMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmInicio voc;
        voc = new frmInicio();
        voc.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String busqueda = txtBuscador.getText().trim();
        String opcionSeleccionada = (String) jComboBox1.getSelectedItem();

        if (opcionSeleccionada.equalsIgnoreCase("Nombre")) {
            buscarPorNombre(busqueda);
        } else if (opcionSeleccionada.equalsIgnoreCase("Categoria")) {
            buscarPorCateogoria(busqueda);
        } else if (opcionSeleccionada.equalsIgnoreCase("")) {
            llenarTabla(productos);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscadorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Limpiar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnInventariar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInventario;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JTextField txtInventariar;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
