Feature: Login

Scenario: Successful Admin login
	Given I am at the login page
	When I type in a username of "admin"
	And I type in a password of "admin"
	And I click the login button
	Then I should be redirected to the admin homepage
	
Scenario: Successful Customer login
	Given I am at the login page
	When I type in a username of "jymm.customer"
	And I type in a password of "password"
	And I click the login button
	Then the customer page should show