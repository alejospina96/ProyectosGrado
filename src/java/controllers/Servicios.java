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

    public int actualizarTrabajoGrado(TrabajoGrado t) {
        String sqlActualizar = "UPDATE trabajo_grado SET ";
        if (t.getArchivo() != null) {
            sqlActualizar += " tg_archivo='" + t.getArchivo().getName() + "',";
        }
        if (t.getEstado() != null) {
            sqlActualizar += " tg_concepto_estado=" + t.getEstado().getId() + ",";
        }
        if (t.getFechaDefensa() != null) {
            sqlActualizar += " fecha_defensa='" + t.getFechaDefensa() + "',";
        }
        sqlActualizar = sqlActualizar.substring(0, sqlActualizar.length() - 1);
        sqlActualizar += " WHERE tg_id=" + t.getId();

        System.out.println(sqlActualizar);
        return conexion.executeUpdateStatement(sqlActualizar);
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
                + "'" + t.getTematica().toUpperCase() + "'"
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

    public int estudiantesProponer(Propuesta p) throws ErrorException {
        ArrayList<Estudiante> estudiantes = p.getEstudiantes();
        int i = 0;
        for (Estudiante estudiante : estudiantes) {
            String sqlAgregar = "INSERT INTO estudiante_propuesta VALUES("
                    + "" + estudiante.getCodigo() + ","
                    + "" + p.getTrabajo().getId()
                    + ")";
            System.out.println(sqlAgregar);
            i = conexion.executeUpdateStatement(sqlAgregar);
            if (i <= 0) {
                deleteEstudiantesPropuestos(p);
                break;
            }
        }
        return i;
    }

    public void deleteEstudiantesPropuestos(Propuesta p) {
        String sqlBorrar = "DELETE FROM trabajo_grado WHERE ep_trabajo_propuesto=" + p.getTrabajo().getId();
        conexion.executeUpdateStatement(sqlBorrar);
    }

    public void deleteTrabajo(TrabajoGrado t) {
        String sqlBorrar = "DELETE FROM trabajo_grado WHERE tg_id=" + t.getId();
        conexion.executeUpdateStatement(sqlBorrar);
    }

    public ResultSet listPropuestasPorVencerse() {
        String sqlLista = "select `universidad`.`trabajo_grado`.`tg_id` AS `trabajo`,\n"
                + "`universidad`.`propuesta`.`propuesta_fecha` AS `fecha`,\n"
                + "`universidad`.`modalidad_trabajo`.`modalidad_descripcion` AS `descripcion`,\n"
                + "`universidad`.`trabajo_grado`.`tg_tematica` AS `tematica`,\n"
                + "`universidad`.`estudiante_propuesta`.`ep_estudiante` AS `estudiante`,\n"
                + "`universidad`.`estado_propuesta`.`ep_descripcion` AS `estado`,\n"
                + "`universidad`.`plazo`.`plazo_fecha_fin` AS `plazo_correcciones`,\n"
                + "`universidad`.`estado_propuesta`.`ep_id` AS `id_estado` \n"
                + "FROM propuesta \n"
                + "INNER JOIN trabajo_grado ON propuesta.propuesta_trabajo=trabajo_grado.tg_id\n"
                + "INNER JOIN modalidad_trabajo ON trabajo_grado.tg_modalidad = modalidad_trabajo.modalidad_id\n"
                + "INNER JOIN estudiante_propuesta ON propuesta.propuesta_trabajo=estudiante_propuesta.ep_trabajo_propuesto\n"
                + "INNER JOIN estado_propuesta ON propuesta_concepto_estado = ep_id \n"
                + "LEFT JOIN plazo ON plazo_id = propuesta.propuesta_plazo_correcciones \n"
                + "WHERE plazo_fecha_inicio > DATE_SUB(NOW(), INTERVAL 60 DAY) \n"
                + "AND ep_id = 2 \n"
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
        System.out.println(sqlLista);
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
        String sqlLista = "select `universidad`.`trabajo_grado`.`tg_id` AS `id`, "
                + "`universidad`.`trabajo_grado`.`tg_tematica` AS `tematica`, "
                + "`universidad`.`modalidad_trabajo`.`modalidad_descripcion` AS `modalidad`, "
                + "`universidad`.`estado_trabajo_grado`.`epg_descripcion` AS `estado`, "
                + "`universidad`.`trabajo_grado`.`fecha_defensa` AS `fecha_defensa`, "
                + "`universidad`.`plazo`.`plazo_fecha_fin` AS `plazo_entrega`, "
                + "`universidad`.`trabajo_grado`.`tg_concepto_estado` AS `id_estado` "
                + "FROM trabajo_grado "
                + "INNER JOIN jurado_trabajo_grado on jurado_trabajo_grado.jtg_trabajo_grado=tg_id "
                + "INNER JOIN modalidad_trabajo on trabajo_grado.tg_modalidad = modalidad_id "
                + "INNER JOIN estado_trabajo_grado on trabajo_grado.tg_concepto_estado = epg_id "
                + "LEFT JOIN plazo on trabajo_grado.tg_plazo_entrega = plazo.plazo_id "
                + "WHERE jtg_persona =" + identificacionJurado;
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listPropuestas(int idEstado) {
        String sqlLista = "SELECT * FROM `v_propuestas` WHERE id_estado=" + idEstado;
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listTrabajosGradoVigentes() {
        String sqlLista = "SELECT * FROM v_trabajos_grado WHERE id_estado = 5 OR id_estado = 2";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listPropuestas() {
        String sqlLista = "SELECT * FROM `v_propuestas` ";
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
