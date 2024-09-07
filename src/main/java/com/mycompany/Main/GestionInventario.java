/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.Main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.Conexion.IConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Ramosz
 */
public class GestionInventario implements IInventariar {

    public IConexion conexion;
    public String nombreColeccion = "productos";
    static final Logger logger = Logger.getLogger(GestionInventario.class.getName());

    @Override
    public void agregarNuevoProducto(Producto nuevoProducto) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Document> coleccion = base.getCollection(nombreColeccion);

            // Crear un nuevo documento para el producto
            Document productoNuevo = new Document()
                    .append("nombre", nuevoProducto.getNombre())
                    .append("descripcion", nuevoProducto.getDescripcion())
                    .append("precio", nuevoProducto.getPrecio())
                    .append("cantidad", nuevoProducto.getCantidad());

            // Insertar el nuevo producto en la colección
            coleccion.insertOne(productoNuevo);

            // Log de información
            logger.log(Level.INFO, "Se agregó un nuevo producto: {0}", productoNuevo.toJson());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al agregar nuevo producto", ex);
        }
    }

    @Override
    public void actualizarProducto(String nombreProducto) {

    }

    @Override
    public void eliminarProducto(String nombreProducto) {

    }

}
