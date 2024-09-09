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
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
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
    public void inventariar(String nombreProducto, int cantidad) {
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

    @Override
    public List<Producto> consultarProductosPorNombre(String nombreProducto) {
        List<Producto> productos = new ArrayList<>();
        try {
            // Obtener la base de datos y la colección
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Producto> coleccion = base.getCollection(nombreColeccion, Producto.class);

            // Crear una expresión regular para buscar productos cuyo nombre contenga la cadena proporcionada
            Document filtro = new Document("nombre", new Document("$regex", nombreProducto).append("$options", "i"));

            // Consultar los productos que coincidan con el filtro
            coleccion.find(filtro).into(productos);

            // Log de información
            logger.log(Level.INFO, "Se encontraron {0} productos con el nombre que contiene '{1}'", new Object[]{productos.size(), nombreProducto});
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al consultar productos por nombre", ex);
        }
        return productos;
    }

    @Override
    public List<Producto> consultarProductosPorCategoria(String nombreCategoria) {
        List<Producto> productos = new ArrayList<>();
        try {
            // Obtener la base de datos y la colección
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Producto> coleccion = base.getCollection(nombreColeccion, Producto.class);

            // Crear una expresión regular para buscar productos cuyo nombre contenga la cadena proporcionada
            Document filtro = new Document("categoria", new Document("$regex", nombreCategoria).append("$options", "i"));

            // Consultar los productos que coincidan con el filtro
            coleccion.find(filtro).into(productos);

            // Log de información
            logger.log(Level.INFO, "Se encontraron {0} productos con el nombre que contiene '{1}'", new Object[]{productos.size(), nombreCategoria});
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al consultar productos por nombre", ex);
        }
        return productos;
    }

    @Override
    public List<Producto> consultarProductosPorAgotarse() {
        List<Producto> productos = new ArrayList<>();
        try {
            // Obtener la base de datos y la colección
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Producto> coleccion = base.getCollection(nombreColeccion, Producto.class);

            // Crear un filtro para productos cuya cantidad sea menor o igual a 5
            Document filtro = new Document("cantidad", new Document("$lte", 5));

            // Consultar los productos que coincidan con el filtro
            coleccion.find(filtro).into(productos);

            // Log de información
            logger.log(Level.INFO, "Se encontraron {0} productos que están por agotarse", productos.size());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al consultar productos por agotarse", ex);
        }
        return productos;
    }

}
