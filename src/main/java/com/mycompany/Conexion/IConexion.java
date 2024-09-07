/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.Conexion;

import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Abel
 */
public interface IConexion {

    /**
     * Obtiene la base de datos con la que se va a trabajar en MongoDB
     * @return valor de la base de datos
     */
    public MongoDatabase obtenerBaseDatos();
}
