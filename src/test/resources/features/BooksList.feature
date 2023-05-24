Feature: Books list

  Scenario: Check books list in UI equals to Books list from API
    Given Browser is launched
    And Get books list from UI
    And Get books list from Api
    Then Check books list in UI equals to Books list from API