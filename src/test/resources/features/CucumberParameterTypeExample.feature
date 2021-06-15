Feature: Cucumber Parameter types example
  Scenario Outline: Colors of Solar objects
    When I ask what color is <solarObject>
    Then I get an answer it is <color>

    Examples:
    | solarObject | color |
    | Sun | Red |
    | Moon | White |
    | Earth | Blue |
    | Mercury | Silver  |