/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionBD;

import java.sql.*;
/**
 *
 * @author daniel
 */
public class ConexionBD {
    // ----------------------------------------------------------
    //  Constantes
    // ----------------------------------------------------------

    /**
     * Constante que representa la clase a través de la cual se establece
     * conexión con mySql
     */
    public static final String CLASE_CONEXION = "com.mysql.jdbc.Driver";

    
    // ----------------------------------------------------------
    //  Atributos
    // ----------------------------------------------------------
    
    private String ipAdress;
    private String user;
    private String password;
    private String url;
    private Connection conn;
    private Statement stmt;
    
    
    // ----------------------------------------------------------
    //  Constructores
    // ----------------------------------------------------------
    
    public ConexionBD( )
    {
        url = "jdbc:mysql://localhost/universidad";
        user = "universidad";
        password = "universidad";

        conectar( );
    }
 
    public ConexionBD ( String pIpAdress, String pUser, String pPassword, 
                        String pUrl  )
    {
        ipAdress = pIpAdress;
        user = pUser;
        password = pPassword;
        url = pUrl;
        
        conectar( );
    }
    
    private void conectar( )
    {
        try
        {
            Class.forName( CLASE_CONEXION ).newInstance( );
            conn = DriverManager.getConnection( url, user, password );
        }
        catch( ClassNotFoundException cnfe )
        {
            System.out.println( "Error al cargar la clase: " + cnfe.getMessage( ) );
        }
        catch( SQLException se )
        {
            System.out.println( "Error al conectarse a la BD: " + se.getMessage( ) );
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage( ) );
        }
    }
    
    /**
     * Cierra la conexion actual con las base de datos
     */
    public void closeConection( )
    {
        try
        {
            conn.close( );
        }
        catch( SQLException se )
        {
            System.out.println( "Error al cerrar la BD: " + se.getMessage( ) );
        }
    }
    
    public int executeUpdateStatement( String cadena ) 
    {
        int r = 0;
        try
        {
            stmt = conn.createStatement( );
            r = stmt.executeUpdate( cadena );
        }
        catch( SQLException se )
        {
            System.out.println( "Error en la actualización: " + se.getMessage( ) );
        }
        
        return r;
    }

    public ResultSet executeQueryStatement( String cadena )
    {
        ResultSet r = null;
        try
        {
            stmt = conn.createStatement( );
            r = stmt.executeQuery( cadena );
        }
        catch( SQLException se )
        {
            System.out.println( "Error en la actualización: " + se.getMessage( ) );
        }
        
        return r;
    }
}
