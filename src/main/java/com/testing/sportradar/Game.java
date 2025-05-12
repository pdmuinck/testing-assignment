package com.testing.sportradar;

public record Game(
    String id,
    String event_date,
    String event_time,
    String tournament,
    String sport,
    String homeTeam,
    String awayTeam
) {
  @Override
  public String toString() {
    return String.join(",", id, event_date, event_time, tournament, sport, homeTeam, awayTeam);
  }
}
