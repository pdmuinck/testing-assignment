package com.testing.sportradar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
  public static void main(String... args) {
    String url = "https://lsc.fn.sportradar.com/sportradar/en/Europe:Berlin/gismo/event_fullfeed/0/24";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      List<Game> games = new ArrayList<>();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(response.body());
      JsonNode sports = rootNode.get("doc").get(0).get("data");
      if (sports.isArray()) {
        for (JsonNode sport : sports) {
          String sportName = sport.get("name").asText();
          if (sportName.equals("Soccer")) {
            JsonNode categories = sport.get("realcategories");
            if (categories.isArray()) {
              for (JsonNode category : categories) {
                JsonNode tournaments = category.get("tournaments");
                if (tournaments.isArray()) {
                  for (JsonNode tournament : tournaments) {
                    JsonNode matches = tournament.get("matches");
                    if (matches.isArray()) {
                      for (JsonNode match : matches) {
                        Game game = new Game(match.get("_id").asText(), match.get("_dt").get("date").asText(),
                            match.get("_dt").get("time").asText(), tournament.get("name").asText(), sportName,
                            match.get("teams").get("home").get("name").asText(),
                            match.get("teams").get("away").get("name").asText());
                        games.add(game);
                      }
                    }
                  }
                }
              }
            }
          }
          System.out.println(games.stream().map(Game::toString).collect(Collectors.joining("\n")));
        }
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}

