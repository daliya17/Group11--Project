# Rating Management Service

### Description: 
In this service a user can perform various activities related to rating a service:

1. ### Submit a rating:
    Using this a user can submit a rating for a service.
    The user needs to provide a valid rating between 0 and 5 in multiples of 0.5 like 1.5 or 3.0 but ratings like 4.2 are not accepted.

2. ### Update a rating:
    Using this a user can update any existing rating for a service. In order to update a rating a valid rating id is required otherwise id not found exception is thrown and the rating should be valid i.e. between 0 and 5 in multiples of 0.5 like 1.5 or 3.0 but ratings like 4.2 are not accepted.

3. ### Delete a rating:
    Using this a user can delete any existing rating for a service.In order to delete a rating a valid rating id is required otherwise id not found exception is thrown.

4. ### Find average of ratings:
    Using this a user can get the average of all the ratings. In case there is no rating present then 0.0 is returned as average.

5. ### Find count of ratings:
    Using this a user can get the count of various ratings by different users.In case there are no ratings then it returns an empty list.


