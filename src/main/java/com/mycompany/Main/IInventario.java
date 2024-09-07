/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.Main;

import java.util.List;

/**
 *
 * @author Ramosz
 */
public interface IInventario {

    public List<Producto> obtenerInventarioCompleto();

    public void actualizarInventario(String nombreProducto, int cantidad);
}
