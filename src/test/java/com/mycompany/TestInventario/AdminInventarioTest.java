package com.mycompany.TestInventario;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mycompany.Conexion.IConexion;
import com.mycompany.Main.ControlInventario;
import com.mycompany.Main.Producto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminInventarioTest {

    private ControlInventario controlInventario;
    private IConexion conexionMock;
    private MongoDatabase databaseMock;
    private MongoCollection<Producto> coleccionMock;
    private MongoCursor<Producto> cursorMock;
    private FindIterable<Producto> findIterableMock;

    @BeforeEach
    void setUp() {
        conexionMock = mock(IConexion.class);
        databaseMock = mock(MongoDatabase.class);
        coleccionMock = mock(MongoCollection.class);
        cursorMock = mock(MongoCursor.class);
        findIterableMock = mock(FindIterable.class);

        when(conexionMock.obtenerBaseDatos()).thenReturn(databaseMock);
        when(databaseMock.getCollection(anyString(), eq(Producto.class))).thenReturn(coleccionMock);
    }

    @Test
    void testObtenerInventarioCompleto() {
        // Datos de prueba
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Producto1", "Descripción1", "Categoría1", 10.0, 5));
        productos.add(new Producto("Producto2", "Descripción2", "Categoría2", 20.0, 10));

        when(coleccionMock.find()).thenReturn(findIterableMock);
        when(findIterableMock.into(anyList())).thenAnswer(invocation -> {
            List<Producto> result = invocation.getArgument(0);
            result.addAll(productos);
            return result;
        });

        controlInventario = new ControlInventario(conexionMock);

        List<Producto> resultado = controlInventario.obtenerInventarioCompleto();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Producto1", resultado.get(0).getNombre());
        assertEquals("Producto2", resultado.get(1).getNombre());
    }

    @Test
    void testConsultarProductosPorNombre() {
        String nombreProducto = "Producto";
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(nombreProducto, "Descripción", "Categoría", 10.0, 5));

        when(coleccionMock.find(eq(new Document("nombre", new Document("$regex", nombreProducto).append("$options", "i")))))
                .thenReturn(findIterableMock);
        when(findIterableMock.into(anyList())).thenAnswer(invocation -> {
            List<Producto> result = invocation.getArgument(0);
            result.addAll(productos);
            return result;
        });

        controlInventario = new ControlInventario(conexionMock);

        List<Producto> resultado = controlInventario.consultarProductosPorNombre(nombreProducto);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(nombreProducto, resultado.get(0).getNombre());
    }

    @Test
    void testConsultarProductosPorCategoria() {
        String categoria = "Categoría";
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Producto", "Descripción", categoria, 10.0, 5));

        when(coleccionMock.find(eq(new Document("categoria", new Document("$regex", categoria).append("$options", "i")))))
                .thenReturn(findIterableMock);
        when(findIterableMock.into(anyList())).thenAnswer(invocation -> {
            List<Producto> result = invocation.getArgument(0);
            result.addAll(productos);
            return result;
        });

        controlInventario = new ControlInventario(conexionMock);

        List<Producto> resultado = controlInventario.consultarProductosPorCategoria(categoria);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(categoria, resultado.get(0).getCategoria());
    }

    @Test
    void testConsultarProductosPorAgotarse() {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Producto1", "Descripción1", "Categoría1", 10.0, 3));

        // Corregir el paréntesis y agregar la coma que falta
        when(coleccionMock.find(eq(new Document("cantidad", new Document("$lte", 5)))))
                .thenReturn(findIterableMock);
        when(findIterableMock.into(anyList())).thenAnswer(invocation -> {
            List<Producto> result = invocation.getArgument(0);
            result.addAll(productos);
            return result;
        });

        controlInventario = new ControlInventario(conexionMock);

        List<Producto> resultado = controlInventario.consultarProductosPorAgotarse();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCantidad() <= 5);
    }

    @Test
    void testActualizarInventario() {
        String nombreProducto = "Producto1";
        int cantidad = -2;

        // Crear el producto simulado
        Producto producto = new Producto(nombreProducto, "Descripción", "Categoría", 10.0, 5);

        // Configurar el mock para que devuelva el producto cuando se consulta por nombre
        when(coleccionMock.find(eq(new Document("nombre", nombreProducto)))).thenReturn(findIterableMock);
        when(findIterableMock.first()).thenReturn(producto);

        // Ejecutar el método que se está probando
        controlInventario = new ControlInventario(conexionMock);
        controlInventario.actualizarInventario(nombreProducto, cantidad);

        // Verificar que updateOne se llamó con los argumentos correctos
        verify(coleccionMock).updateOne(eq(new Document("nombre", nombreProducto)),
                eq(new Document("$inc", new Document("cantidad", cantidad))));
    }

}
