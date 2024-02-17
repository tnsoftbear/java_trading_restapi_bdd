Feature: User feature
  Scenario: add user and search
    Given user with username "DiamondJr" does not exist
    When register user with username "DiamondJr" and password "Password1"
    Then user is created with username "DiamondJr" and password "Password1"
    And can find user by username "DiamondJr"
    And cannot find user by username "DiamondJr2"

  Scenario: delete user
    Given user with username "DiamondJr" exists
    When delete user with username "DiamondJr"
    Then cannot find user by username "DiamondJr"