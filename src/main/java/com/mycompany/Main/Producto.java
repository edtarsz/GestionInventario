/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Main;

/**
 *
 * @author Ramosz
 */
public class Producto {

    private String nombre;
    private String descripcion;
    private String categoria;
    private double precio;
    private int cantidad;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, String categoria, double precio, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto{");
        sb.append("nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", categoria=").append(categoria);
        sb.append(", precio=").append(precio);
        sb.append(", cantidad=").append(cantidad);
        sb.append('}');
        return sb.toString();
    }
}
