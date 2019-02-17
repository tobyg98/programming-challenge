# Programming Challenge

## Purpose 

- Gauge skills and provide a medium for discussion during interview
- Your response will be reviewed before the interview and will be used as a basis for the technical questions and discussion. Please note, your final solution will be reviewed for how it solves the given problem but also how the tools provided have been utilised, i.e Git etc.

## Expectations

- Should not take more than an hour 
- Complete the problems to the best of your ability
- Complete the challenge in any way you wish, the only provision is that it meets the requirements.
- You are welcome to make improvements outside of the set of the requirements however it is not expected.



## Data 

Sourced from [QLD Gov OpenData Portal](https://data.qld.gov.au/dataset/ambient-estuarine-water-quality-monitoring-data-near-real-time-sites-2012-to-present-day/resource/0ca6f77c-4088-4d77-8c88-beae2b57ce14).



## The Application

The application reads in a csv file containing data about Baffle Creek (Current  velocity & direction), it then processes this data and outputs some summary information in a HTML for consumption via a browser. 

## Task 

1. Fix the tests for the existing application to ensure that it is working correctly

2. Update the Summary produced by the application to include the following details, 

   - Average for each of the columns (excluding timestamp),
   - TODO: Do we want more here. 

   These will be presented in the `index.html` file that is produced through a browser. No special styling is required. 

3. Update the tests to provide coverage for these changes. 

4. Alter the application to read the latest data set from the QLD Govevernment Open data portal. HINT: Open data is hosted via an application called CKAN. There are multiple ways of achieving this.

   

