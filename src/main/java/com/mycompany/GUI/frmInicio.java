/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.GUI;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mycompany.Conexion.Conexion;
import com.mycompany.Conexion.IConexion;
import com.mycompany.Main.ControlInventario;
import com.mycompany.Main.GestionInventario;
import com.mycompany.Main.IInventariar;
import com.mycompany.Main.IInventario;
import com.mycompany.Main.Producto;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Ramosz
 */
public class frmInicio extends javax.swing.JFrame {

    IInventariar inventariar;
    IInventario inventario;
    IConexion conexion;
    List<Producto> productos = null;

    /**
     * Creates new form frmInicio
     */
    public frmInicio() {
        initComponents();
        conexion = new Conexion();
        this.inventariar = new GestionInventario(conexion);
        this.inventario = new ControlInventario(conexion);
        llenarTabla(productos);
    }

    public boolean validarDatos() {
        // Obtener los valores de los campos de texto
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        String cantidadStr = txtCantidad.getText();
        String precioStr = txtPrecio.getText();

        // Verificar que el campo de nombre no esté vacío
        if (nombre.isEmpty()) {
            // Mostrar mensaje de error
            mostrarMensajeError("El nombre no puede estar vacío.");
            return false;
        }

        // Verificar que el campo de descripción no esté vacío
        if (descripcion.isEmpty()) {
            // Mostrar mensaje de error
            mostrarMensajeError("La descripción no puede estar vacía.");
            return false;
        }

        // Verificar que el campo de cantidad no esté vacío
        if (cantidadStr.isEmpty()) {
            // Mostrar mensaje de error
            mostrarMensajeError("La cantidad no puede estar vacía.");
            return false;
        }

        // Verificar que el campo de precio no esté vacío
        if (precioStr.isEmpty()) {
            // Mostrar mensaje de error
            mostrarMensajeError("El precio no puede estar vacío.");
            return false;
        }

        // Verificar que el campo de cantidad sea un número válido
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Mostrar mensaje de error
            mostrarMensajeError("La cantidad debe ser un número entero mayor que cero.");
            return false;
        }

        // Verificar que el campo de precio sea un número válido
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            // Mostrar mensaje de error
            mostrarMensajeError("El precio debe ser un número decimal mayor que cero.");
            return false;
        }

        // Todos los campos son válidos
        return true;
    }

    public void mostrarMensajeError(String mensaje) {
        // Mostrar un cuadro de diálogo emergente con el mensaje de error
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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

        // Crear un JTable con el modelo
        jTInventario.setModel(inventarioEncontrado);

        // Asignar un renderizador personalizado para la columna "Cantidad"
        jTInventario.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Verificar si el valor es menor o igual a 5 y cambiar el color de la letra
                if (value instanceof Number && ((Number) value).intValue() <= 5) {
                    cell.setForeground(Color.RED);
                } else {
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        });
    }

    public void resetCantidades() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }

    /**
     * Busca clientes por nombre y actualiza la tabla con los resultados de la
     * búsqueda.
     *
     * @param nombre El nombre del cliente a buscar.
     */
    private void buscarPorNombre(String nombre) {
        List<Producto> inventarioDespliegue = inventario.consultarProductosPorNombre(nombre);
        if (inventarioDespliegue != null && !inventarioDespliegue.isEmpty()) {
            llenarTabla(inventarioDespliegue);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron clientes con el nombre especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorCateogoria(String nombre) {
        List<Producto> inventarioDespliegue = inventario.consultarProductosPorCategoria(nombre);
        if (inventarioDespliegue != null && !inventarioDespliegue.isEmpty()) {
            llenarTabla(inventarioDespliegue);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron clientes con el nombre especificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorAgotarse() {
        List<Producto> inventarioDespliegue = inventario.consultarProductosPorAgotarse();
        llenarTabla(inventarioDespliegue);
    }

    // Método para generar el PDF
    private String generarPDF() {
        Document document = new Document();
        String filePath = "reporte_agotarse.pdf"; // Ruta del archivo PDF

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Formato para la fecha y hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaYHora = LocalDateTime.now().format(formatter);

            // Título del reporte
            Paragraph titulo = new Paragraph("Reporte de Productos por Agotarse", 
                                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.BOLD, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Añadir fecha y hora de la solicitud
            Paragraph fecha = new Paragraph("Fecha y hora del reporte: " + fechaYHora,
                                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.GRAY));
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setSpacingAfter(20); // Espacio después de la fecha
            document.add(fecha);

            // Tabla para los datos de los productos
            PdfPTable table = new PdfPTable(5); // 5 columnas (Nombre, Descripción, Categoría, Precio, Cantidad)
            table.setWidthPercentage(100); // Ancho completo de la página

            // Añadir encabezados de la tabla
            table.addCell("Nombre");
            table.addCell("Descripción");
            table.addCell("Categoría");
            table.addCell("Precio");
            table.addCell("Cantidad");

            // Obtener los datos de la tabla y añadirlos al PDF
            List<String> datosTabla = obtenerDatosDeLaTabla();
            for (String dato : datosTabla) {
                // Separar las celdas por comas (según el formato en obtenerDatosDeLaTabla)
                String[] celdas = dato.split(", ");
                for (String celda : celdas) {
                    table.addCell(celda.split(": ")[1]); // Añadir solo el valor (sin el título)
                }
            }

            document.add(table); // Añadir la tabla al documento
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    private List<String> obtenerDatosDeLaTabla() {
        List<String> datos = new ArrayList<>();

        // Iterar sobre todas las filas de la tabla
        for (int i = 0; i < jTInventario.getRowCount(); i++) {
            StringBuilder fila = new StringBuilder();

            // Obtener los valores de cada columna de la fila actual
            fila.append("Nombre: ").append(jTInventario.getValueAt(i, 0)).append(", ");
            fila.append("Descripción: ").append(jTInventario.getValueAt(i, 1)).append(", ");
            fila.append("Categoría: ").append(jTInventario.getValueAt(i, 2)).append(", ");
            fila.append("Precio: ").append(jTInventario.getValueAt(i, 3)).append(", ");
            fila.append("Cantidad: ").append(jTInventario.getValueAt(i, 4));

            // Añadir la fila a la lista de datos
            datos.add(fila.toString());
        }

        return datos;
    }


    // Método para mostrar la ventana con botones
    private void mostrarVentanaOpciones(String filePath) {
        JFrame frame = new JFrame("Opciones");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Botón para descargar el PDF
        JButton btnDescargar = new JButton("Descargar PDF");
        btnDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí podrías abrir el PDF o simplemente informar que fue creado
                JOptionPane.showMessageDialog(null, "PDF generado en: " + filePath);
                frame.dispose();
            }
        });

        // Botón para cerrar la ventana
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Cerrar la ventana
            }
        });

        panel.add(btnDescargar);
        panel.add(btnCerrar);

        frame.add(panel);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
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
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();
        btnReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(780, 550));
        setMinimumSize(new java.awt.Dimension(780, 550));
        setPreferredSize(new java.awt.Dimension(780, 550));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(770, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Gestión De Inventarios");

        jTInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Producto", "Descripción", "Categoría", "Precio", "Cantidad"
            }
        ));
        jTInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTInventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTInventario);

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Descripción");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Precio");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Categoría");

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bebida", "Sabritas", "Galletas", "Otro" }));

        jButton1.setText("Inventariar");
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

        btnReporte.setText("Generar Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(118, 118, 118))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(btnReporte))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnEliminar))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizar)
                            .addComponent(btnLimpiar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 69, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 520));
        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (validarDatos()) {
            String nombreProducto = txtNombre.getText();
            inventariar.eliminarProducto(nombreProducto);
            resetCantidades();
            llenarTabla(productos);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (validarDatos()) {
            Producto nuevoProducto = new Producto(txtNombre.getText(), txtDescripcion.getText(), cmbCategoria.getSelectedItem().toString(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtCantidad.getText()));
            inventariar.agregarNuevoProducto(nuevoProducto);
            resetCantidades();
            llenarTabla(productos);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        resetCantidades();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (validarDatos()) {
            int seleccion = jTInventario.getSelectedRow();
            if (seleccion == -1) {
                // No se ha seleccionado ninguna fila, mostrar un mensaje o manejar el caso de manera apropiada
                mostrarMensajeError("No se ha seleccionado ningún producto.");
                return; // Salir del método para evitar continuar con la lógica que depende de la selección
            }

            // Se ha seleccionado una fila, continuar con el proceso de actualización del producto
            String nombrePrevio = jTInventario.getValueAt(seleccion, 0).toString();
            Producto nuevoProducto = new Producto(txtNombre.getText(), txtDescripcion.getText(), cmbCategoria.getSelectedItem().toString(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtCantidad.getText()));
            inventariar.actualizarProducto(nombrePrevio, nuevoProducto);
            llenarTabla(productos);
            resetCantidades();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jTInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTInventarioMouseClicked
        // TODO add your handling code here:
        int seleccion = jTInventario.getSelectedRow();
        txtNombre.setText(jTInventario.getValueAt(seleccion, 0).toString());
        txtDescripcion.setText(jTInventario.getValueAt(seleccion, 1).toString());
        cmbCategoria.setSelectedItem(jTInventario.getValueAt(seleccion, 2).toString());
        txtPrecio.setText(jTInventario.getValueAt(seleccion, 3).toString());
        txtCantidad.setText(jTInventario.getValueAt(seleccion, 4).toString());
    }//GEN-LAST:event_jTInventarioMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmInventariar voc;
        voc = new frmInventariar();
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

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        buscarPorAgotarse();
       
        String filePath = generarPDF();

        mostrarVentanaOpciones(filePath);
    }//GEN-LAST:event_btnReporteActionPerformed

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
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTInventario;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
