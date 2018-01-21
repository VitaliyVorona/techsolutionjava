Feature: Create a Customer
  As a user of the system
  I want to create a customer in the system providing it with parameters
  So I can use the customer for further workflows

  Scenario: Create a new Customer
    Given the positive Create a new Customer scenario
    When I provide Customers mandatory fields with id "0171" First Name "Elon" and Last Name "Musk"
    And I make a POST request to the target endpoint
    Then I should get 201 response status code
    And successfully created response message

  Scenario Outline: Create a new Customer Negative
    Given the negative Create a new Customer scenario
    When I dont provide or its invalid one of Customers id "<id>" First Name "<first_name>" or Last Name "<last_name>" parameters
    And I make a POST request to the target endpoint
    Then I should get 400 response status code
    And Bad Request response message
    Examples:
          | first_name  | last_name  |  id  | age |	dob		| active |
          | vitaliy    	| vorona  	 |  	| 34  | 160184  | true 	 |
          |         	| vorona   	 |  1684| 34  |	160184	| true 	 |
          | vitaliy    	|          	 |	1684| 34  |	160184	| true 	 |
          |         	|          	 |		| 34  |	160184	| true 	 |
          | 123Vitaliy 	| vorona  	 |  	| 34  | 160184  | true 	 |
          | vitaliy    	| vorona123	 |  	| 34  | 160184  | true 	 |
          | vitaliy    	| vorona  	 |  16BA| 34  | 160184  | true 	 |
