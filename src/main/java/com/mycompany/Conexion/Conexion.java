/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author
 */
public class Conexion implements IConexion {

    public String cadenaConexion = "mongodb://127.0.0.1:27017";
    public String nombreBaseDatos = "gestionInventario";

    public Conexion() {
    }

    /**
     * Obtiene la base de datos con la que se va trabajar en MongoDB
     *
     * @return regresa una base de datos de Mongo
     */
    @Override
    public MongoDatabase obtenerBaseDatos() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(cadenaConexion))
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient cliente = MongoClients.create(settings);

        //MongoClient cliente = MongoClients.create(cadenaConexion);
        MongoDatabase baseDatos = cliente.getDatabase(nombreBaseDatos);
        return baseDatos;
    }
;

}
