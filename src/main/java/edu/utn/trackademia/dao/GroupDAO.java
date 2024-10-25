/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.EnrolledGroup;
import edu.utn.trackademia.entities.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexledezma
 */
public class GroupDAO {

    private IDBAdapter adapter;

    public GroupDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }

    public boolean hasAvailableGroups(int idUsuario) {
        return (getGrupos(idUsuario).stream().count() > 0);
    }

    public List<Grupo> getGrupos(int idUsuario) {

        List<Grupo> grupos = new ArrayList<>();

        try {
            String consultSQL = "SELECT g.id_grupo, g.numero_grupo, g.modalidad, g.numero_aula, g.curso, g.horario, g.profesor, g.capacidad_maxima, g.enlance, g.plataforma, c.creditos, c.horas_lectivas FROM grupos g JOIN usuarios u ON (u.id_usuario = g.profesor OR u.rol = 1) JOIN cursos c ON g.curso = c.id_curso WHERE u.id_usuario = ?";

            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, idUsuario);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int idGrupo = resultSet.getInt("id_grupo");
                int numeroGrupo = resultSet.getInt("numero_grupo");
                int numeroAula = resultSet.getInt("numero_aula");
                String horario = resultSet.getString("horario");
                int capacidadMaxima = resultSet.getInt("capacidad_maxima");
                String id_curso = resultSet.getString("curso");
                int creditos = resultSet.getInt("creditos");  // Retrieve creditos from cursos
                int horasLectivas = resultSet.getInt("horas_lectivas");  // Retrieve horas_lectivas from cursos

                Grupo grupo = new Grupo(idGrupo, numeroGrupo, numeroAula, horario, capacidadMaxima, id_curso, creditos, horasLectivas);
                grupos.add(grupo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return grupos;
    }

    public List<EnrolledGroup> getEnrolledGroups(int idUsuario){
        List<EnrolledGroup> eGroups = new ArrayList<>();
        try {
            String consultSQL = "SELECT g.id_grupo,g.numero_grupo, c.nombre_curso,CONCAT(p.nombre, ' ', p.apellidos) AS profesor, c.creditos,g.horario, g.numero_aula,g.enlance, g.plataforma,c.horas_lectivas FROM matriculas m JOIN matriculas_grupos mg ON m.id_matricula = mg.id_matricula JOIN grupos g ON mg.id_grupo = g.id_grupo JOIN cursos c ON g.curso = c.id_curso JOIN usuarios u ON g.profesor = u.id_usuario JOIN personas p ON u.persona = p.id_persona WHERE m.id_estudiante = ?";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, idUsuario);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int idGroup = resultSet.getInt("id_grupo");
                int groupNumber = resultSet.getInt("numero_grupo");
                String courseName = resultSet.getString("nombre_curso");
                String teacher = resultSet.getString("profesor");
                int credits = resultSet.getInt("creditos");  
                String schedule = resultSet.getString("horario");
                int classroom = resultSet.getInt("numero_aula");
                String url = resultSet.getString("enlance");
                String platform = resultSet.getString("plataforma");
                int hours = resultSet.getInt("horas_lectivas");  // Retrieve horas_lectivas from cursos

                EnrolledGroup eGroup = new EnrolledGroup(idGroup, groupNumber, courseName, teacher, credits,schedule,classroom,url, platform,hours);
                System.out.println(eGroup.toString());
                eGroups.add(eGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return eGroups;
    }

}
