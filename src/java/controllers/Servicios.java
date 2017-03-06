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
        String sqlLista = "SELECT *\n"
                + "FROM `v_propuestas` \n"
                + "INNER JOIN propuesta on propuesta.propuesta_trabajo = v_propuestas.trabajo\n"
                + "WHERE NOW() > DATE_SUB(v_propuestas.plazo_correcciones, INTERVAL 60 DAY)\n"
                + "AND propuesta.propuesta_concepto_estado=2\n"
                + "ORDER BY plazo_correcciones";
        return conexion.executeQueryStatement(sqlLista);
    }

    public ResultSet listTrabajosGradoPorVencerse() {
        String sqlLista = "SELECT *\n"
                + "FROM `v_trabajos_grado` \n"
                + "INNER JOIN trabajo_grado on v_trabajos_grado.id = trabajo_grado.tg_id\n"
                + "WHERE NOW() > DATE_SUB(v_trabajos_grado.plazo_entrega, INTERVAL 365 DAY)\n"
                + "AND trabajo_grado.tg_concepto_estado = 2\n"
                + "ORDER BY plazo_entrega";
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
        String sqlLista = "select `universidad`.`trabajo_grado`.`tg_id` AS `id`, \n"
                + "`universidad`.`trabajo_grado`.`tg_tematica` AS `tematica`, \n"
                + "`universidad`.`modalidad_trabajo`.`modalidad_descripcion` AS `modalidad`, \n"
                + "`universidad`.`estado_trabajo_grado`.`epg_descripcion` AS `estado`, \n"
                + "`universidad`.`trabajo_grado`.`fecha_defensa` AS `fecha_defensa`, \n"
                + "`universidad`.`plazo`.`plazo_fecha_fin` AS `plazo_entrega`, \n"
                + "`universidad`.`trabajo_grado`.`tg_concepto_estado` AS `id_estado`, \n"
                + "concat(persona.persona_p_nombre,' ',persona.persona_p_apellido) as jurado \n"
                + "FROM trabajo_grado \n"
                + "INNER JOIN jurado_trabajo_grado on jurado_trabajo_grado.jtg_trabajo_grado=tg_id \n"
                + "INNER JOIN modalidad_trabajo on trabajo_grado.tg_modalidad = modalidad_id \n"
                + "INNER JOIN estado_trabajo_grado on trabajo_grado.tg_concepto_estado = epg_id \n"
                + "LEFT JOIN plazo on trabajo_grado.tg_plazo_entrega = plazo.plazo_id \n"
                + "INNER JOIN persona on jtg_persona = persona.persona_identificacion\n"
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
