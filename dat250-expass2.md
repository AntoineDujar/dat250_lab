# Short report REST API with Spring Boot poll application
## Introduction
This is an implementation of a simple REST API app for managing users, polls and votes. It was created using Spring Boot and Java.

It implements CRUD for fetching, updating, and deleting of users, polls, vote options, and votes.
## Technical problems
The biggest hurdle I faced implementing this was converting the given domain model into functioning code, as some details are left up to the programmer for interpretation.

For example attempting to keep all polls listed under each instance of a user, meant that should the main program require a list of all created polls, it has to iterate through each user's created poll list to obtain an up to date list. 

Creating a helper method helped with this, however the logic of organising the program this way remains convoluted in my opinion.

Votes having a direct relationship with voteOptions also proved to be challending as obtaining which poll the voteOption was related to through interacting with votes was also convoluted. 

Idealy the domain model should be reorganized to allow each class access of each other through IDs or other unique identifiers, or be connected to a managing class who can easily retreive relations between the objects.
## API usage documentation and examples
### User
#### Get
```
GET
http://localhost:8080/getUsers
```
#### Create
```
POST
http://localhost:8080/createUser
Body:
{
  "username": "Tom",
  "email": "tom@gmail.com"
}
```
#### Delete
```
DELETE
http://localhost:8080/deleteUser
Body:
{
  "username": "Tom",
  "email": "tom@gmail.com"
}
```
#### Update
```
PUT
http://localhost:8080/updateUser/Tom
{
  "username": "Tom",
  "email": "tom2@yahoo.com"
}
```
### Polls
#### GET
```
GET
http://localhost:8080/getPolls
```
#### Create
```
POST
http://localhost:8080/createPoll/Tom
Body:
{
  "id": 1,
  "question": "What colour is the sun?",
  "options": [
    {
      "caption": "orange",
      "presentationOrder": 1
    },
    {
      "caption": "yellow",
      "presentationOrder": 2
    }
  ]
}
```
#### Delete
```
DELETE
http://localhost:8080/deletePoll/Tom/1
```
#### Update
```
PUT
http://localhost:8080/updatePoll/1
Body:
{
  "id": 1,
  "question": "What colour is the sea?",
  "options": [
    {
      "caption": "blue",
      "presentationOrder": 1
    },
    {
      "caption": "dark blue",
      "presentationOrder": 2
    }
  ]
}
```
### Vote Options
#### GET
```
GET
http://localhost:8080/getVoteOption/1/2
```
#### Create
```
POST
http://localhost:8080/createVoteOption/1
Body:
{
  "caption": "green",
  "presentationOrder": 3
}
```
#### Delete
```
DELETE
http://localhost:8080/deleteVoteOption/1/1
```
#### Update
```
PUT
http://localhost:8080/updateVoteOption/1/2
Body:
{
  "caption": "aqua marine",
  "presentationOrder": 2
}
```
### Votes
#### GET
```
GET
http://localhost:8080/getVotes/1
```
#### Create
```
POST
http://localhost:8080/createVote/1/1/Tom
```
#### Delete
```
DELETE
http:localhost:8080/deleteVote/1/1/Tom
```
