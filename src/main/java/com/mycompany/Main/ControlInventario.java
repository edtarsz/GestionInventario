/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mycompany.Conexion.IConexion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Ramosz
 */
public class ControlInventario implements IInventario {

    public IConexion conexion;
    public String nombreColeccion = "productos";
    static final Logger logger = Logger.getLogger(ControlInventario.class.getName());

    public ControlInventario(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene el inventario de las bebidas
     *
     * @return lista de Productos de tipo bebida
     */
    @Override
    public List<Producto> obtenerInventarioCompleto() {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Producto> coleccion = base.getCollection(nombreColeccion, Producto.class);

            List<Producto> productos = new ArrayList<>();
            coleccion.find().into(productos);
            logger.log(Level.INFO, "Se consultaron {0} productos", productos.size());

            return productos;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener inventario", ex);
        }
        return null;
    }

    @Override
    public void actualizarInventario(String nombreProducto, int cantidad) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Producto> coleccion = base.getCollection(nombreColeccion, Producto.class);

            // Consultar el producto por su nombre
            Producto producto = coleccion.find(eq("nombre", nombreProducto)).first();

            if (producto != null) {
                // Actualizar la cantidad del producto usando $inc para restar la cantidad proporcionada
                coleccion.updateOne(eq("nombre", nombreProducto),
                        new Document("$inc", new Document("cantidad", cantidad)));

                // Log de información
                logger.log(Level.INFO, "Se actualizó el inventario de {0} restando {1}", new Object[]{nombreProducto, cantidad});
            } else {
                logger.log(Level.WARNING, "No se encontró la bebida {0} en el inventario", nombreProducto);
            }

            // Devolver todos los productos
            List<Producto> productos = new ArrayList<>();
            coleccion.find().into(productos);
            logger.log(Level.INFO, "Se consultaron {0} productos", productos.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar inventario", ex);
        }
    }
}
