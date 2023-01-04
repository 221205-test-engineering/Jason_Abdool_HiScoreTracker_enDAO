package com.abdool.app;

import com.abdool.Scores;
import com.abdool.daos.ScoreDAO;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.List;

public class HiScoreTrackerApp {

    public static void main(String[] args) {

        ScoreDAO scoresDAO = new ScoreDAO();

        Javalin app = Javalin.create();

        app.get("/scores", ctx -> {
            // we want to write code that will communicate with the database
            // and return a list<Scores> that we can marshall into json
            // and send back as a response
            // We'll use the DAO design pattern
            List<Scores> scores = scoresDAO.getAll();

            ctx.status(200);
            ctx.json(scores);    //issue, made users a string and not use list.
        });

        app.get("/scores/{id}", ctx -> {

            int id = Integer.parseInt(ctx.pathParam("id"));

            Scores s = scoresDAO.getById(id);

            if (s != null) {
                ctx.status(200);
                ctx.json(s);

            } else {
                ctx.status(HttpStatus.NOT_FOUND);
                ctx.result("No user found with id " + id);
            }
        });

        //Using Postman, we can add new users.
        app.post("/scores", ctx -> {
            Scores scoreToAdd = ctx.bodyAsClass(Scores.class);
            Scores addedScore = scoresDAO.add(scoreToAdd);
            if (addedScore != null) {
                ctx.status(201);
                ctx.json(addedScore);
            } else {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);     //The request was well-formed but was unable to be followed due to semantic errors.
                ctx.result("Something went wrong.");
            }
        });

        app.put("/scores/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            Scores scoreToUpdate = ctx.bodyAsClass(Scores.class);

            if(scoresDAO.getById(id) != null) {
                scoreToUpdate.setId(id);
                scoresDAO.update(scoreToUpdate);
                ctx.status(200);
            } else {
                ctx.status(404);
                ctx.result("User score with id " + id + " does not exist.");
            }

        });

        app.delete("/scores/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            if(scoresDAO.getById(id) != null) {
                scoresDAO.delete(id);
                ctx.status(200);
            } else {
                ctx.status(404);
                ctx.result("User score with id " + id + " does not exist.");
            }

        });

        app.start(8080);
    }
}
