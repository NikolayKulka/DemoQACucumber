Feature: Login

  Scenario: Log in to application positive
    Given Browser is launched
    And User clicks login
    And User enters username and password
    And User clicks login
    Then check user is logged in

  Scenario: Log in to application negative
    And User clicks logout
    And User enters invalid username and password
    And User clicks login
    Then check user is not logged in
    And Close browser


