# DAT250: Software Technology Experiment Assignment 3
## Introduction
In this assignment I created a SPA front end for the rest API back end that was previously created in the last assignment. I decided to use React and Typescript due to familiarity.

In the the front end you are able to create polls, add options and vote on existing polls. It has been deployed to the spring boot code, so that both front end and back end are hosted on the same port.
## Technical problems
I found that my biggest problems were caused by the design decisions or assumptions that were previously made in the last assignment. For example I had assumed that the votes would belong to the class of the user. However this made vote retrieval much more complicated as I would have to clumsily itterate over each user for each poll option in order to get a list of users who had voted on that poll option. On top of this I would need to communicate this to the front end in a clear way, as to both make it possible to write and to make it easier to work with. 

Finally I had decided that some of my back end code needed refactoring to fit the demands of the front end, a list of users who had voted for an option is now used to store votes, which is more intuitive for finding who voted on each option.
