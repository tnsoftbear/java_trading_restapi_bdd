Feature: Security feature
  Scenario: add security and search
    Given security with name "BMV" does not exist
    When register security with name "BMV"
    Then security is created with name "BMV"
    And can find security by name "BMV"
    And cannot find security by name "bmv"

  Scenario: delete user
    Given security with name "BMV" exists
    When delete security with name "BMV"
    Then cannot find security by name "BMV"