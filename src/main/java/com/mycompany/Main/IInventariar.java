/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.Main;

/**
 *
 * @author Ramosz
 */
public interface IInventariar {

    void agregarNuevoProducto(Producto nuevoProducto);

    void actualizarProducto(String nombreProducto);

    void eliminarProducto(String nombreProducto);
}
