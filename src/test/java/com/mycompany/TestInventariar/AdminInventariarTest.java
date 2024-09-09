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
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
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
        conexionMock = Mockito.mock(IConexion.class);
        databaseMock = Mockito.mock(MongoDatabase.class);
        collectionMock = Mockito.mock(MongoCollection.class);
        when(conexionMock.obtenerBaseDatos()).thenReturn(databaseMock);
        when(databaseMock.getCollection("productos")).thenReturn(collectionMock);
        gestionInventario = new GestionInventario(conexionMock);
    }

    @Test
    void testAgregarNuevoProducto() {
        Producto nuevoProducto = new Producto("Principe Avellana", "Galletas sabor avellana", "Galletas", 20.00, 10);
        gestionInventario.agregarNuevoProducto(nuevoProducto);
        verify(collectionMock, times(1)).insertOne(any(Document.class));
    }

    @Test
    void testActualizarProducto() {
        Producto productoActualizado = new Producto("Emperador senzo", "Galletas sabor chocolate", "Galletas", 25.00, 10);
        gestionInventario.actualizarProducto("Principe Avellana", productoActualizado);
        verify(collectionMock, times(1)).updateOne(any(Document.class), any(Document.class));
    }

    @Test
    void testEliminarProducto() {
        gestionInventario.eliminarProducto("Principe Avellana");
        verify(collectionMock, times(1)).deleteOne((Bson) any(Document.class));
    }
}
