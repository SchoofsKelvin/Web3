
Feature: Login
	Scenario Outline: I can register and login with valid details
		Given a userid "<userid>"
		When I register as "<name>" using password "<pass>"
		And I login with password "<pass>"
		Then I am logged in as "<name>"
	Examples:
		|userid|name|pass|
		|Myself|Le Me|me123|