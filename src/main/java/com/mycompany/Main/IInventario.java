/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.Main;

import java.util.List;

/**
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
 */
public interface IInventario {

    public List<Producto> obtenerInventarioCompleto();

    public List<Producto> consultarProductosPorNombre(String nombreProducto);

    public List<Producto> consultarProductosPorCategoria(String nombreCategoria);

    public List<Producto> consultarProductosPorAgotarse();

    public void inventariar(String nombreProducto, int cantidad);
}
