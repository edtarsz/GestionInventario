/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.TestInventariar;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.mongodb.client.MongoCollection;
import com.mycompany.Conexion.IConexion;
import com.mycompany.Main.GestionInventario;
import com.mycompany.Main.Producto;
import org.bson.Document;
import org.bson.conversions.Bson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Ramosz
 */
public class AdminInventariarTest {

    private IConexion conexionMock;
    private MongoDatabase databaseMock;
    private MongoCollection<Document> collectionMock;
    private GestionInventario gestionInventario;

    public AdminInventariarTest() {

    }

    @BeforeEach
    void setUp() {
        // Crear mocks de las dependencias
        conexionMock = Mockito.mock(IConexion.class);
        databaseMock = Mockito.mock(MongoDatabase.class);
        collectionMock = Mockito.mock(MongoCollection.class);

        // Configurar el comportamiento de los mocks
        when(conexionMock.obtenerBaseDatos()).thenReturn(databaseMock);
        when(databaseMock.getCollection("productos")).thenReturn(collectionMock);

        // Inicializar la clase que vamos a probar
        gestionInventario = new GestionInventario(conexionMock);
    }

    @Test
    void testAgregarNuevoProducto() {
        // Crear un nuevo producto para agregar
        Producto nuevoProducto = new Producto("Principe Avellana", "Galletas sabor avellana", "Galletas", 20.00, 10);

        // Ejecutar el método agregarNuevoProducto
        gestionInventario.agregarNuevoProducto(nuevoProducto);

        // Verificar que el método insertOne fue llamado una vez
        verify(collectionMock, times(1)).insertOne(any(Document.class));
    }

    @Test
    void testActualizarProducto() {
        // Crear el producto actualizado
        Producto productoActualizado = new Producto("Emperador senzo", "Galletas sabor chocolate", "Galletas", 25.00, 10);

        // Ejecutar el método actualizarProducto
        gestionInventario.actualizarProducto("Principe Avellana", productoActualizado);

        // Verificar que el método updateOne fue llamado una vez
        verify(collectionMock, times(1)).updateOne(any(Document.class), any(Document.class));
    }

    @Test
    void testEliminarProducto() {
        // Ejecutar el método eliminarProducto
        gestionInventario.eliminarProducto("Principe Avellana");

        // Verificar que el método deleteOne fue llamado una vez
        verify(collectionMock, times(1)).deleteOne((Bson) any(Document.class));
    }
}
