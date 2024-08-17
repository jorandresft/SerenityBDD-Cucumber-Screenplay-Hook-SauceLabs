#author: Jorge Franco
#date 15/08/2024

Feature: Validate login the app Sauce Labs

  Background:
    Given The "user" open the web site

  @successful
  Scenario Outline: Validate login with valid credentials
    And The "user" send his credentials user "<userName>" and password "<password>"
    When He clicks on the login button
    Then He should see the title "Products" with login is successful

    Examples:
      | userName      | password     |
      | standard_user | secret_sauce |

  @successful
  Scenario Outline: Validate login with incorrect credentials
    And The "user" send his credentials user "<userName>" and password "<password>"
    When He clicks on the login button
    Then He should see the title "Epic sadface: Username and password do not match any user in this service"

    Examples:
      | userName | password |
      | standard | secret   |

  @successful
  Scenario Outline: Validate login with password it is missing
    And The "user" send his credentials user "<userName>" and password "<password>"
    When He clicks on the login button
    Then He should see the title "Epic sadface: Password is required"

    Examples:
      | userName | password |
      | standard |          |