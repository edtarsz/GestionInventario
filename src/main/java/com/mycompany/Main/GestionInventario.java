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
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
 */
public class GestionInventario implements IInventariar {

    public IConexion conexion;
    public String nombreColeccion = "productos";
    static final Logger logger = Logger.getLogger(GestionInventario.class.getName());

    public GestionInventario(IConexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void agregarNuevoProducto(Producto nuevoProducto) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Document> coleccion = base.getCollection(nombreColeccion);

            // Crear un nuevo documento para el producto
            Document productoNuevo = new Document()
                    .append("nombre", nuevoProducto.getNombre())
                    .append("descripcion", nuevoProducto.getDescripcion())
                    .append("categoria", nuevoProducto.getCategoria())
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
    public void actualizarProducto(String nombreProducto, Producto productoActualizado) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Document> coleccion = base.getCollection(nombreColeccion);

            // Crear el filtro para encontrar el producto por nombre
            Document filtro = new Document("nombre", nombreProducto);

            // Crear el nuevo documento con los datos actualizados
            Document actualizacion = new Document("$set", new Document()
                    .append("nombre", productoActualizado.getNombre())
                    .append("descripcion", productoActualizado.getDescripcion())
                    .append("categoria", productoActualizado.getCategoria())
                    .append("precio", productoActualizado.getPrecio())
                    .append("cantidad", productoActualizado.getCantidad()));

            // Actualizar el producto
            coleccion.updateOne(filtro, actualizacion);

            // Log de información
            logger.log(Level.INFO, "Producto actualizado: {0}", productoActualizado.getNombre());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar el producto", ex);
        }
    }

    @Override
    public void eliminarProducto(String nombreProducto) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Document> coleccion = base.getCollection(nombreColeccion);

            // Crear el filtro para eliminar el producto por nombre
            Document filtro = new Document("nombre", nombreProducto);

            // Eliminar el producto
            coleccion.deleteOne(filtro);

            // Log de información
            logger.log(Level.INFO, "Producto eliminado: {0}", nombreProducto);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar el producto", ex);
        }
    }

}
