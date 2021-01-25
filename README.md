# Take Home Exercise
## Thoughts/Assumptions
- Since the response on the webpage requires all three attributes (pickup postcode, delivery postcode, and vehicle), I made the assumption that the vehicle field is a mandatory attibute on all POST requests, rather than being optional. Because of this, I chose not to have a Quote constructor without a vehicle parameter (except for the empty Quote constructor, which is required by Spring), and so refactored the unit tests already in the test suite to reflect this. These unit tests pass Vehicle.DEFAULT (with a markup of 0%) for the vehicle attribute to make sure that the basic functionality is still tested. However, it is impossible to send a POST request with Vehicle.DEFAULT using the webpage as there is no option on the form corresponding to this.

- Taking into account the recommended time limit, I decided to spend more time on the functionality of the quoting engine, as opposed to adding a CSS file for my webpage. For example, I added both client-side and server-side validation of the format of the postcode entered by the user. However, I did add the viewport meta tag to the HTML file to make the webpage responsive. 

- I have tested the webpage's functionality on the following browsers: Google Chrome, Firefox and Microsoft Edge.

## Commands
I completed the task on IntelliJ for Windows, running the `gradle bootRun` command on IntelliJ, and then the following commands on the command-line:

```
$body = @{pickupPostcode='SW1A1AA'; deliveryPostcode='EC2A3LT'; vehicle='BICYCLE'} | ConvertTo-Json
Invoke-WebRequest -Uri http://localhost:8080/quote -ContentType "application/json" -Method POST -Body $body
```
