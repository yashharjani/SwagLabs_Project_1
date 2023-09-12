@positive
Feature: Feature to test the Login Functionality

  Scenario Outline: Test Login Functionality with Valid Credentials
    Description: This scenario is to test the Login Functionality with valid credentials, 
    and check whether the Login is successful by validating that Logout button is visible.

    When I am on "https://www.saucedemo.com/" url
    And I enter <username> and <password> as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds
    Then I validate that user has logged in

    Examples: 
      | username      | password     |
      | standard_user | secret_sauce |

  Scenario Outline: Test Login Functionality with no username and no password
    When I am on "https://www.saucedemo.com/" url
    And I enter <username> and <password> as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds
    Then I validate the error message "Epic sadface: Username is required"

    Examples: 
      | username | password |
      |          |          |

  Scenario Outline: Test Login Functionality with no password
    When I am on "https://www.saucedemo.com/" url
    And I enter <username> and <password> as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds
    Then I validate the error message "Epic sadface: Password is required"

    Examples: 
      | username      | password |
      | standard_user |          |

  Scenario Outline: Test Login Functionality with invalid credentials
    When I am on "https://www.saucedemo.com/" url
    And I enter <username> and <password> as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds
    Then I validate the error message "Epic sadface: Username and password do not match any user in this service"

    Examples: 
      | username | password |
      | admin    | admin    |

  Scenario Outline: Test Login Functionality with locked out username
    When I am on "https://www.saucedemo.com/" url
    And I enter <username> and <password> as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds
    Then I validate the error message "Epic sadface: Sorry, this user has been locked out."

    Examples: 
      | username        | password     |
      | locked_out_user | secret_sauce |

  Scenario: Test whether the user is able to navigate directly to the Home Page without Login
    Description: This test validates that the user is not able to navigate directly to the Home Page without Login.

    When I am on "https://www.saucedemo.com/inventory.html" url
    Then I validate the error message "Epic sadface: You can only access '/inventory.html' when you are logged in."

  Scenario: Test whether the user is able to navigate directly to the Cart Page without Login
    Description: This test validates that the user is not able to navigate directly to the Cart Page without Login.

    When I am on "https://www.saucedemo.com/cart.html" url
    Then I validate the error message "Epic sadface: You can only access '/cart.html' when you are logged in."
