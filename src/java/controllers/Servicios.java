/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import conexionBD.ConexionBD;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author daniel
 */
public class Servicios {

    private ConexionBD conexion;

    public Servicios() {
        conexion = new ConexionBD();
    }

    public int agregarPersona(Persona p) {
        String sNombre = (p.getsNombre() != null) ? p.getsNombre() : "(NULL)";
        String sqlAgregar = "INSERT INTO persona VALUES("
                + "" + p.getIdentificacion() + ","
                + "'" + p.getpNombre() + "',"
                + "'" + sNombre + "',"
                + "'" + p.getpApellido() + "',"
                + "'" + p.getsApellido() + "',"
                + "'" + p.getEmail() + "',"
                + "0"
                + ")";
        System.out.println(sqlAgregar);
        return conexion.executeUpdateStatement(sqlAgregar);
    }

    public int agregarEstudiante(Estudiante e) {
        int i = agregarPersona(e.getPersona());
        if (i > 0) {
            String sqlAgregar = "INSERT INTO estudiante VALUES("
                    + "" + e.getCodigo() + ","
                    + "" + e.getPersona().getIdentificacion() + ","
                    + "'" + e.getPrograma().getCodigo() + "'"
                    + ")";
            System.out.println(sqlAgregar);
            i = conexion.executeUpdateStatement(sqlAgregar);
            if (i <= 0) {
                deletePersona(e.getPersona());
            }
        }
        return i;
    }

    private void deletePersona(Persona p) {
        String sqlBorrar = "DELETE FROM persona WHERE persona_identificacion=" + p.getIdentificacion();
        conexion.executeUpdateStatement(sqlBorrar);
    }

    public int nextIdTrabajo() {
        String sqlMayor = "SELECT MAX(tg_id) as 'id' from trabajo_grado";
        ResultSet r = conexion.executeQueryStatement(sqlMayor);
        try {
            r.next();
            return r.getInt("id") + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public ResultSet listProgramas() {
        String sqlLista = "SELECT * FROM programa WHERE 1";
        return conexion.executeQueryStatement(sqlLista);
    }

    public int agregarTrabajo(TrabajoGrado t) throws ErrorException {
        String sqlAgregar = "INSERT INTO trabajo_grado(tg_modalidad, tg_tematica) VALUES("
                + "" + t.getModalidad().getId() + ","
                + "'" + t.getTematica() + "'"
                + ")";
        System.out.println(sqlAgregar);
        return conexion.executeUpdateStatement(sqlAgregar);
    }

    public int agregarPropuesta(Propuesta p) throws ErrorException {
        int i = agregarTrabajo(p.getTrabajo());
        if (i > 0) {
            String sqlAgregar = "INSERT INTO propuesta(propuesta_trabajo,propuesta_fecha,propuesta_archivo) VALUES("
                    + "" + p.getTrabajo().getId() + ","
                    + "'" + new Date(new java.util.Date().getTime()) + "',"
                    + "'" + p.getArchivo().getName() + "'"
                    + ")";
            System.out.println(sqlAgregar);
            i = conexion.executeUpdateStatement(sqlAgregar);
            if (i > 0) {
                i = estudiantesProponer(p);
            }
            if (i <= 0) {
                deleteTrabajo(p.getTrabajo());
            }

        }
        return i;
    }
    public int estudiantesProponer(Propuesta p)throws ErrorException{
        ArrayList<Estudiante> estudiantes = p.getEstudiantes();
        int i = 0;
        for (Estudiante estudiante : estudiantes) {
            String sqlAgregar = "INSERT INTO estudiante_propuesta VALUES("
                    + "" + estudiante.getCodigo() + ","
                    + "" + p.getTrabajo().getId() 
                    + ")";
            System.out.println(sqlAgregar);
            i = conexion.executeUpdateStatement(sqlAgregar);
            if(i<=0){
                deleteEstudiantesPropuestos(p);
                break;
            }
        }
        return i;
    }
    public void deleteEstudiantesPropuestos(Propuesta p){
        String sqlBorrar = "DELETE FROM trabajo_grado WHERE ep_trabajo_propuesto=" + p.getTrabajo().getId();
        conexion.executeUpdateStatement(sqlBorrar);
    }
    public void deleteTrabajo(TrabajoGrado t) {
        String sqlBorrar = "DELETE FROM trabajo_grado WHERE tg_id=" + t.getId();
        conexion.executeUpdateStatement(sqlBorrar);
    }

    public ResultSet listPropuestasPorVencerse() {
        String sqlLista = "SELECT propuesta.* "
                + "FROM propuesta "
                + "INNER JOIN estado_propuesta ON propuesta_concepto_estado = ep_id "
                + "INNER JOIN plazo ON plazo_id = propuesta_plazo_correcciones "
                + "WHERE plazo_fecha_inicio > DATE_SUB(NOW(), INTERVAL 60 DAY) "
                + "AND ep_id = 2 "
                + "ORDER BY plazo_fecha_fin";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listTrabajosGradoPorVencerse() {
        String sqlLista = "SELECT trabajo_grado.* "
                + "FROM trabajo_grado "
                + "INNER JOIN propuesta ON propuesta_trabajo = tg_id "
                + "INNER JOIN estado_propuesta ON propuesta_concepto_estado = ep_id "
                + "INNER JOIN estado_trabajo_grado ON propuesta_concepto_estado = ep_id "
                + "INNER JOIN plazo ON plazo_id = tg_plazo_correcciones "
                + "WHERE plazo_fecha_inicio > DATE_SUB(NOW(), INTERVAL 365 DAY) "
                + "AND epg_id = 2 "
                + "AND ep_id = 3 "
                + "ORDER BY plazo_fecha_fin";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listModalidades() {
        String sqlLista = "SELECT * FROM modalidad_trabajo WHERE 1";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listJurados() {
        String sqlLista = "SELECT * FROM persona WHERE persona_es_jurado = 1";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listTrabajosGradoPorJurado(Long identificacionJurado) {
        String sqlLista = "SELECT * FROM jurado_trabajo_grado WHERE jtg_persona = " + identificacionJurado;
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listPropuestas(int idEstado) {
        String sqlLista = "SELECT * FROM modalidad_trabajo WHERE propuesta_concepto_estado = " + idEstado;
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listTrabajosGradoVigentes() {
        String sqlLista = "SELECT * FROM trabajo_grado WHERE tg_concepto_estado = 2 OR tg_concepto_estado = 7";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listPropuestas() {
        String sqlLista = "SELECT * FROM propuesta WHERE 1";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listEstadosPropuesta() {
        String sqlLista = "SELECT * FROM estado_propuesta WHERE 1";
        return conexion.executeQueryStatement(sqlLista);
    }

    public Programa buscarPrograma(String codigo) {
        String sql = "SELECT * FROM programa WHERE programa_codigo LIKE " + codigo;
        ResultSet res = conexion.executeQueryStatement(sql);
        try {
            res.next();
            return new Programa(res.getString(1), res.getString(2), res.getString(3));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }

    public Modalidad buscarModalidad(int id) {
        String sql = "SELECT * FROM modalidad_trabajo WHERE modalidad_id=" + id;
        ResultSet res = conexion.executeQueryStatement(sql);
        try {
            res.next();
            return new Modalidad(res.getInt(1), res.getString(2));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return null;
        }
    }

}
