package controllers;

import com.google.inject.Inject;
import play.db.Database;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.*;
import repositories.*;

public class SportController extends Controller {

    @Inject public SportRepository sportRepository;
    @Inject Database database;

    public Result findAll() {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findAll())).as("application/json");
        });
    }

    public Result findById(int id) {
        return database.withConnection(conn -> {
            return ok(Json.toJson(sportRepository.findById(id))).as("application/json");
        });
    }

    public Result create() {
        return database.withConnection(conn -> {
            Customer sportRequest = Json.fromJson(request().body().asJson(), Customer.class);

            sportRepository.add(sportRequest);

            return ok(Json.toJson(sportRequest)).as("application/json");
        });
    }

    public Result update(int id) {
        return database.withConnection(conn -> {
            Customer sportRequest = Json.fromJson(request().body().asJson(), Customer.class);

            sportRequest.setId(id);

            sportRepository.update(sportRequest);

            return ok(Json.toJson(sportRequest)).as("application/json");
        });
    }

    public Result delete(int id) {
        return database.withConnection(conn -> {
            sportRepository.delete(id);
            return ok("{}").as("application/json");
        });
    }
}