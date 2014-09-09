Meta:
@capability customer-management
@cbg 
@scope (CBG phase 1 and phase 2 release)
@suite sanity

Narrative:
In order to enroll a customer
As a user for CBG mobile application
I want to be able to enter customer details



Scenario: Successful enrollment to CBG Co-op

Given I have launched the application
And I have accepted the terms and conditions
And I have entered Phone Number
And I have entered my HVC code
And I have entered Passcode
And I have entered Authentication Details SP1
And I have entered Authentication Details SP2
When I have entered Authentication Details SP3
Then I get successfully registered


Scenario: Navigate to various Account types and perform IAT and Bill Pay for any one of the account type

Given I have successfully registered to CBG
And I have navigated to view Accounts
And I have set Weekly Alert
And I have successfully performed Payment Transfer
And I have successfully performed Bill Pay
And I have cancelled Alerts Globally
And I have cancelled Mobile Banking Service
Then I have completed the Sanity successfully









