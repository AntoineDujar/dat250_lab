# DAT250: Software Technology Experiment Assignment 4
## Introduction
In this assignment I integrated JPA data into the project.

## Technical problems
I found that my biggest problems were the technical debt accumulated over the previous assignments, from assumption of the class diagram to methods that I designed, and that were absent from the provided tests. I had to modify some of the test to fit my class structure.

Adding the annotations to my existing classes was simple, however I got repeatedly stuck on setting relationships between my classes (OneToMany, ManyToMany, OneToOne) for those who were not base classes. The error logs were not super clear to me.

Finally I also got stuck on the problem that User is not a valid table name by default, so I also had to rename it to users.
