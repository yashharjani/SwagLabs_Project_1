Feature: Feature for end-to-end testing of Sauce Demo website
  Note: Two drivers are configured in this project: Chrome driver and Edge driver.

  Background: 
    When I am on "https://www.saucedemo.com/" url
    And I enter "standard_user" and "secret_sauce" as username and password
    And I wait for 1 seconds
    And I click on Login button
    And I wait for 1 seconds

  @positive
  Scenario: Test the end-to-end flow process of Sauce Demo
    Description: The following test cases are covered: (TC_04, TC_12, TC_15, TC_16, TC_21, TC_26, TC_27, TC_28)

    When I add items to the cart
      | item1 | Sauce Labs Backpack   |
      | item2 | Sauce Labs Bike Light |
    Then cart count should be displayed
    And I verify the "cart count"
      | cartCount | 2 |
    When I check the cart items by clicking on the cart button
    And I wait for 1 seconds
    Then I verify the "product names"
      | productName1 | Sauce Labs Backpack   |
      | productName2 | Sauce Labs Bike Light |
    When I click on checkout button
    And I wait for 1 seconds
    When I enter checkout details
      | firstName  | Standard |
      | lastName   | User     |
      | postalCode |    12820 |
    When I click on continue button
    Then I verify checkout title is displayed
    And I verify the "Product names"
      | productName1 | Sauce Labs Backpack   |
      | productName2 | Sauce Labs Bike Light |
    And I verify the "Item total"
      | itemTotal | $39.98 |
    When I click on finish button
    And I wait for 1 seconds
    Then I verify that the checkout title is displayed after placing an order
    And I verify the "completion message"
      | thankyouMessage | Thank you for your order! |

  @positive
  Scenario: Test to verify the session timeout (TC_31)
    Description: If user has opened Home page in two tabs, and if user logs out from one tab,
    then on performing any action on the other tab, the user should be automatically be logged out.

    When I add items to the cart
      | item1 | Sauce Labs Backpack   |
      | item2 | Sauce Labs Bike Light |
    When I open a new tab in the browser
    And I wait for 1 seconds
    When I am on "https://www.saucedemo.com/inventory.html" url
    And I wait for 1 seconds
    When I click on nav bar
    And I wait for 1 seconds
    When I click on Logout button
    And I wait for 1 seconds
    When I go back to previous tab
    And I wait for 1 seconds
    When I check the cart items by clicking on the cart button
    And I wait for 1 seconds
    Then I verify Login button is visible

  @positive
  Scenario: Test to verify that the items which were added to the cart should remain added even if the user gets logged out.
    Description: The following test case is covered: (TC_19)

    When I add items to the cart
      | item1 | Sauce Labs Backpack   |
      | item2 | Sauce Labs Bike Light |
    Then I verify the "cart count"
      | cartCount | 2 |
    When I click on nav bar
    And I wait for 1 seconds
    When I click on Logout button
    And I wait for 1 seconds
    Then I verify Login button is visible
    When I enter "standard_user" and "secret_sauce" as username and password
    And I wait for 1 seconds
    When I click on Login button
    And I wait for 1 seconds
    Then I verify the "cart count"
      | cartCount | 2 |
    And I wait for 1 seconds

  @negative
  Scenario: Test to verify that if the user enters given URL after login and no items are in the cart, the page access should be denied
    Description: The following test case is covered: (TC_13)

    When I am on "https://www.saucedemo.com/checkout-step-two.html" url
    And I wait for 1 seconds
    When I click on finish button
    And I wait for 1 seconds
    Then I verify that the checkout title is displayed after placing an order
    And I verify the "completion message"
      | thankyouMessage | Thank you for your order! |

  @negative
  Scenario: Test to verify that if item is added to the cart of one user, that item should not be added to the cart for another user.
    Description: The following test case is covered: (TC_20)

    When I add items to the cart
      | item1 | Sauce Labs Backpack   |
      | item2 | Sauce Labs Bike Light |
    Then I verify the "cart count"
      | cartCount | 2 |
    When I click on nav bar
    And I wait for 1 seconds
    When I click on Logout button
    And I wait for 1 seconds
    Then I verify Login button is visible
    When I enter "problem_user" and "secret_sauce" as username and password
    And I wait for 1 seconds
    When I click on Login button
    And I wait for 1 seconds
    Then I verify the "cart count"
      | cartCount | 2 |

  @negative
  Scenario: Test to verify that an error message should come if all items are removed from the cart list and then checkout button is clicked.
    Description: The following test case is covered: (TC_22)

    When I add items to the cart
      | item1 | Sauce Labs Backpack   |
      | item2 | Sauce Labs Bike Light |
    When I check the cart items by clicking on the cart button
    And I wait for 1 seconds
    When I click on remove button to remove items from the cart
    When I click on checkout button
    When I enter checkout details
      | firstName  | Standard |
      | lastName   | User     |
      | postalCode |    12820 |
    When I click on continue button
    And I wait for 1 seconds
    Then I verify checkout title is displayed
    When I click on finish button
    And I wait for 1 seconds
    Then I verify that the checkout title is displayed after placing an order
    And I verify the "completion message"
      | thankyouMessage | Thank you for your order! |
