package com.birthdaylist.model;

import lombok.Data;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class Guest {
    private  int id;
    private String name;
    private String phone;
    private String kinship;

    public Guest(){

    }
    public Guest(String name, String phone, String kinship){
        this.name = name;
        this.phone = phone;
        this.kinship = kinship;
    }


    public static Set<Guest> getDataInDataBase(){
        try {
            ConnectionDataBase connectionDb = new ConnectionDataBase();
            connectionDb.getConexao();
            Connection connection = connectionDb.getConexao();
            Statement statementDb = connection.createStatement();
            ResultSet result = statementDb.executeQuery("SELECT * from birth_list");
            Set<Guest> studentList = new HashSet<>();
            while (result.next()) {
                Guest student = new Guest();
                Integer idGuest = result.getInt("id");
                student.setId(idGuest);
                String nameGuest = result.getString("name");
                student.setName(nameGuest);
                String phoneGuest = result.getString("phone");
                student.setPhone(phoneGuest);
                String kinshipGuest = result.getString("kinship");
                student.setKinship(kinshipGuest);
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }


    }

    public static void DeleteInDataBase(Integer idGuest){
        try {
            ConnectionDataBase connectionDb = new ConnectionDataBase();
            connectionDb.getConexao();
            Connection connection = connectionDb.getConexao();
            String query = "DELETE  FROM birth_list WHERE id = ?";
            PreparedStatement statementDb = connection.prepareStatement(query);
            statementDb.setInt(1, idGuest);
            int rowsAffected = statementDb.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addDataInDataBase(String name, String phone, String kinship){
        try {
            ConnectionDataBase connectionDb = new ConnectionDataBase();
            connectionDb.getConexao();
            Connection connection = connectionDb.getConexao();
            String insercaoSQL = "INSERT INTO birth_list (name, phone, kinship) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insercaoSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, kinship);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateInDataBase( Integer idGuest, String name, String phone, String relations){
        try {
            ConnectionDataBase connectionDb = new ConnectionDataBase();
            connectionDb.getConexao();
            Connection connection = connectionDb.getConexao();
            String insercaoSQL = "UPDATE birth_list SET name = ?, phone = ?, kinship = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(insercaoSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, relations);
            preparedStatement.setInt(4, idGuest);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Update ok!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Guest searchGuest(String name){
        try {
            ConnectionDataBase connectionDb = new ConnectionDataBase();
            connectionDb.getConexao();
            Connection connection = connectionDb.getConexao();
            String query = "SELECT * FROM birth_list WHERE name = ?";
            PreparedStatement statementDb = connection.prepareStatement(query);
            statementDb.setString(1, name);
            ResultSet result = statementDb.executeQuery();
            while (result.next()) {
                Guest guest = new Guest();
                int idGuest = result.getInt("id");
                guest.setId(idGuest);
                String nameGuest = result.getString("name");
                guest.setName(nameGuest);
                String phoneGuest = result.getString("phone");
                guest.setPhone(phoneGuest);
                String kinshipGuest = result.getString("kinship");
                guest.setKinship(kinshipGuest);
                return guest;
            }
            result.close();
            statementDb.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static  void validatorEndSetDb(String name, String phone, String kinship){
       String nameUpper = name.toUpperCase();
       if(!guestExist(nameUpper)){
           addDataInDataBase(nameUpper, phone, kinship);
       }else {
          Guest guest = searchGuest(nameUpper);
          updateInDataBase(guest.getId(), nameUpper, phone, kinship );
       }

    }

    public static boolean guestExist(String name){
        if(searchGuest(name) != null){
            return true;
        }
        return false;
    }
}