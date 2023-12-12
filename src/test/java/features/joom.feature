Feature: Joom
  Scenario Outline: Reachability of product categories
    Given I am a user of the website
    When I visit the shopping website
    And I click on the "<Category>" category
    Then I should be taken to "<Category>" category
    And the category should show at least 10 products
    When I click on the first product in the results
    Then I should be taken to the details page for that product

    Examples:
    |Category|
    |Kids            |
    |Watches & Clocks|
    |Office & School |
    |Pet Supplies    |
    |Jewellery       |

  Scenario: Search functionality
    Given I am a user of the website
    When I visit the shopping website
    And I search for a product using the term "book"
    Then I should see the search results for "book"
    And there should be at least 5 products in the search results
    When I click on the first product in the results
    Then I should be taken to the details page for that product
