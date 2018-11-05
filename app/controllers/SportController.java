package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.db.Database;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.*;
import repositories.*;
import scala.Option;
import java.sql.SQLException;
import java.util.*;

public class SportController extends Controller {

    @Inject
    private SportRepository sportRepository;
    @Inject
    private Database database;

    public Result findAll(Option<String> nombre, Option<String> apellido, Option<Integer> dni, Option<String> telefono, Option<String> email) {

        // Optional values to check
        final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findAll(nombre, apellido, dni, telefono, email, entries))).as("application/json");
        });
    }

    public Result create() {
        JsonNode json = request().body().asJson();
        Customers customers = Json.fromJson(json, Customers.class);
        if (customers.toString().equals("")){
            return badRequest("Missing parameter");
        }
        customers.save();
        return ok(Json.toJson(customers)).as("application/json");
    }

    public Result update(int id) throws SQLException {

        Customers customers = Customers.find.byId(id);
        if (customers == null){
            return notFound("Customers not found");
        }else{
            JsonNode json = request().body().asJson();
            Customers customersToBe = Json.fromJson(json, Customers.class);
            sportRepository.update(customers, customersToBe);
        }
        return ok(Json.toJson(customers)).as("application/json");
    }

    public Result delete(int id) {
        return database.withConnection(conn -> {
            sportRepository.delete(id);
            return ok("{}").as("application/json");
        });
    }


    // ********** deprecated methods ************
    public Result findById(int id) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findById(id))).as("application/json");
        });
    }
    public Result findByDni(int dni) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findByDni(dni))).as("application/json");
        });
    }
    public Result findByNombre(String nombre) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findByNombre(nombre))).as("application/json");
        });
    }
    public Result findByApellido(String apellido) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findByApellido(apellido))).as("application/json");
        });
    }
    public Result findByTelefono(String telefono) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findByTelefono(telefono))).as("application/json");
        });
    }
    public Result findByEmail(String email) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findByEmail(email))).as("application/json");
        });
    }
    // ********** deprecated methods ************

}