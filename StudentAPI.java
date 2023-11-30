package org.example;

import java.sql.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static spark.Spark.*;

public class StudentAPI {
  private static final String DB_URL = "jdbc:postgresql://localhost:9999/doczilaTest";
  private static final String DB_USER = "testuser";
  private static final String DB_PASSWORD = "testpassword";

  public static void main(String[] args) {
    port(9923);
    enableCORS("*", "*", "*");

    // Add a student
    post("/students", (request, response) -> {
      try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String sql = "INSERT INTO students (first_name, last_name, middle_name, date_of_birth, group_id, unique_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, request.queryParams("first_name"));
        pstmt.setString(2, request.queryParams("last_name"));
        pstmt.setString(3, request.queryParams("middle_name"));
        pstmt.setDate(4, java.sql.Date.valueOf(request.queryParams("date_of_birth")));
        pstmt.setInt(5, Integer.valueOf(request.queryParams("group_id")));
        pstmt.setInt(6, Integer.valueOf(request.queryParams("unique_id")));
        pstmt.executeUpdate();
        return "Student added successfully";
      } catch (SQLException e) {
        return "Error adding student: " + e.getMessage();
      }
    });

    // Delete a student by unique number
    delete("/students/:unique_id", (request, response) -> {
      try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String sql = "DELETE FROM students WHERE unique_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Integer.valueOf(request.params("unique_id")));
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
          return "Student deleted successfully";
        } else {
          return "Student not found";
        }
      } catch (SQLException e) {
        return "Error deleting student: " + e.getMessage();
      }
    });

    // Get a list of all students
    get("/students", (request, response) -> {
      JsonArray studentsArray = new JsonArray();
      try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM students";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          JsonObject studentObject = new JsonObject();
          studentObject.addProperty("Name", rs.getString("first_name") + " " + rs.getString("last_name"));
          studentObject.addProperty("MiddleName", rs.getString("middle_name"));
          studentObject.addProperty("Birthday", rs.getString("date_of_birth"));
          studentObject.addProperty("Group", rs.getString("group_id"));
          studentObject.addProperty("UniqueNumber", rs.getString("unique_id"));
          studentsArray.add(studentObject);
        }
        return studentsArray.toString();
      } catch (SQLException e) {
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("Error", "Error retrieving students: " + e.getMessage());
        return errorObject.toString();
      }
    });
  }

  // Enable CORS
  private static void enableCORS(final String origin, final String methods, final String headers) {
    options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }
      return "OK";
    });
    before((request, response) -> response.header("Access-Control-Allow-Origin", origin));
  }
}