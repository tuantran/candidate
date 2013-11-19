Candidate
=========

Requirements:
  1. JDK 7
  2. Maven 3.0.x
  3. Mysql 5.5.x

## Setup

Database:

    mysql --user=root --password=<your mysql root password>
    create database candidate character set utf8;
    # Add user
    grant all privileges on candidate.* to candidate identified by 'pingu123';
    grant all privileges on candidate.* to candidate@'localhost' identified by 'pingu123' ;
    FLUSH PRIVILEGES;
    /quit

Running candidate-webapp:

    mvn clean install
    cd candidate-webapp
    mvn jetty:run
    # With a browser.
    http://localhost:10047/candidate/
    


