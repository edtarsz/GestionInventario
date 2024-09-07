/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.Conexion.IConexion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
}
