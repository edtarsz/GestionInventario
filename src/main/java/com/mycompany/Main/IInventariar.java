/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.Main;

/**
 * @author Eduardo Talavera Ramos | 00000245244
 * @author Ana Cristina Castro Noriega | 00000247580
 * @author Jesus Francisco Tapia Maldonado | 00000245136
 * @date 09/08/2024
 */
public interface IInventariar {

    void agregarNuevoProducto(Producto nuevoProducto);

    void actualizarProducto(String nombreProducto, Producto productoActualizado);

    void eliminarProducto(String nombreProducto);
}
