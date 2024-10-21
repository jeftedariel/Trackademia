/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import java.sql.SQLException;
import java.util.List;
import edu.utn.trackademia.entities.Grupo;

/**
 *
 * @author alexledezma
 */
public class MatriculaService {
    
    private MatriculaDAO matriculaDAO;

    public MatriculaService() {
        this.matriculaDAO = new MatriculaDAO();
    }

    public void matricularEstudiante(int idUsuario, List<Grupo> grupos) throws SQLException {
        int idMatricula = matriculaDAO.insertarMatricula(idUsuario);
        for (Grupo grupo : grupos) {
            matriculaDAO.insertarMatriculaGrupo(idMatricula, grupo.getIdGrupo());
        }
    }
}
