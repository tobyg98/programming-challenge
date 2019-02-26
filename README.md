# Programming Challenge

## Purpose 

- Gauge skills and provide a medium for discussion during interview
- Your response will be reviewed before the interview and will be used as a basis for the technical questions and discussion. Please note, your final solution will be reviewed for how it solves the given problem but also how the tools provided have been utilised, i.e Git etc.

## Expectations

- Should not take much more than an hour, we do not mind if you spend longer we just realise that people have lives. 
- Complete the problems to the best of your ability. There is no hard requirement to complete every part of the challenge, just try to ensure what you do complete works. 
- Complete the challenge in any way you wish, the only provision is that it meets the requirements.
- You are welcome to make improvements outside of the set of the requirements however it is not expected.



## Data 

Sourced from [QLD Gov OpenData Portal](https://data.qld.gov.au/dataset/ambient-estuarine-water-quality-monitoring-data-near-real-time-sites-2012-to-present-day/resource/0ca6f77c-4088-4d77-8c88-beae2b57ce14).



## The Application

The application reads in a csv file containing data about Baffle Creek (Current  velocity & direction), it then processes this data and outputs some summary information in a HTML for consumption via a browser. 



## Technology 

You will need, 

-  JDK 11 which is available [here](https://jdk.java.net/11/), and 

- Maven 3 which can be found [here](https://maven.apache.org/download.cgi).

To run the given application, 

1. ``mvn clean install``
2. ``mvn exec:java`` // TODO: Make sure this loads the properties file properly

To run tests, 

``mvn test``

It is recommended to make use of an IDE such as Eclipse or Intellij. 



__NOTE:__ This activity makes use of a library called Lombok to reduce boiler plate code (ide, constructors, getters, setters, hashcode etc), if you make use of an IDE  you may need to enable support. Details of the lombok project can be found [here](https://projectlombok.org/).

## Task 

1. Fork the repository

2. Fix the tests for the existing application to ensure that it is working correctly, we do not expect this to take more than a couple of minutes.

3. Update the Summary produced by the application to include the following details, 

   - Average the totals  for each of the columns (excluding timestamp), - Please note, while the values in each cell represent averages, the task is to find the average of all values for each column.  It is worth noting that there are many ways to achieve this with no _correct_ way. 

   These will be presented in the `index.html` file that is produced through a browser. No special styling is required. 

4. Update the tests to provide coverage for these changes. 

   1. Create test/s to cover any new methods you create so that it provides what you believe is adequate enough coverage for you to be confident that it is working as expected.

5. Alter the application to read the latest data set from the QLD Govevernment Open data portal. HINT: Open data is hosted via an application called CKAN. There are multiple ways of achieving this. The data is accessible [here](https://data.qld.gov.au/dataset/ambient-estuarine-water-quality-monitoring-data-near-real-time-sites-2012-to-present-day).

6. Create a merge request for your fork for us to review.


## Help

The files and methods that you will need to edit include but may not be limited to (depending on your solution), 

- ``VelocityAndDirectionService::buildColumns``

TODO:



Add a http client with JSON dependency 

broken tests.