/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.Course;
import edu.utn.trackademia.entities.User;
import edu.utn.trackademia.entities.UserSession;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jefte
 */

public class UserDAO {

    private IDBAdapter adapter;

    public UserDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }

    //Validates that users exists and has that password.
    public boolean authUser(String email, String password) {
        for (User n : getUsers()) {
            if (n.email().equals(email) && n.password().equals(password)) {
                // Assuming you have a method to retrieve user ID (n.idUsuario())
                UserSession.getInstance().login(n.idUsuario(), n.name(), n.surname(), n.email(), n.role());
                return true;
            }
        }
        return false;
    }
    
    
    public List<String> getStudentsEmail(){
        List<String> emails = new ArrayList<>();
        getUsers().stream().forEach(n->{
            if(n.role()==3){
                emails.add(n.email());
            }
        });
        return emails;
    }
    
    public List<User> getStudents(){
        List<User> students = new ArrayList<>();
        
        getUsers().stream().forEach(n->{
            if(n.role()==3){
                students.add(n);
            }
        });
                
        return students;
    }
    
    public List<Course> getCourses(int id_user){
        List<Course> courses = new ArrayList<>();
        try {
            String consultSQL = "SELECT c.nombre_curso, g.numero_grupo, g.horario, g.numero_aula, g.plataforma, g.enlance FROM matriculas m INNER JOIN matriculas_grupos mg ON m.id_matricula = mg.id_matricula INNER JOIN grupos g ON mg.id_grupo = g.id_grupo INNER JOIN cursos c ON g.curso = c.id_curso WHERE m.id_estudiante = ?;";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, id_user);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Retrieve courses
                String course_name = resultSet.getString("nombre_curso");
                int group_number = resultSet.getInt("numero_grupo");
                String schedule = resultSet.getString("horario");
                int room_number = resultSet.getInt("numero_aula");
                String platform = resultSet.getString("plataforma");
                String url = resultSet.getString("enlance");
                
                // Create a User instance with the idUsuario
                courses.add(new Course (course_name, group_number, room_number, schedule, platform, url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }

        return courses;
    }

    //Getting a list of users (?) obvious
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            // Update the SQL query to select the user ID (assumed to be `id_usuario`)
            String consultSQL = "SELECT u.id_usuario, u.correo, u.contraseña, u.rol, p.Nombre, p.Apellidos, p.Telefono, p.fecha_nacimiento "
                    + "FROM usuarios u LEFT JOIN personas p ON u.persona = p.id_persona;";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Retrieve the id_usuario
                int idUsuario = resultSet.getInt("id_usuario");
                String email = resultSet.getString("correo");
                String password = resultSet.getString("contraseña");
                int role = resultSet.getInt("rol");
                String name = resultSet.getString("Nombre");
                String surname = resultSet.getString("Apellidos");

                // Create a User instance with the idUsuario
                users.add(new User(idUsuario, email, password, role, name, surname));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }

        return users;
    }
}
