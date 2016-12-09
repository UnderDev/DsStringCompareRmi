#An Asynchronous RMI String Comparison Service. 


##Introduction


The following application use's the Java RMI framework to develop a remote, asynchronous string comparison service. 

There are 2 parts to this project, 

1) The Web App (comparator.war)

2) Service (string-service.jar)


The Web App:
Client's using the web app will be able to remotley connect and pass two strings to the service with the choosen algorithm for comparison
and get returned the edit Distance between the two strings.

Service:
The service can then use that string comparison algorithms to compute the edit distance or optimal alignment between the two strings.


#Features Added:

- Web App/Service is fully threaded.
- When client recives results, web app no longer refreshes.


#Running the Program


1) Service
- Run the "string-service.jar" via CMD with the following comand: java –cp ./string-service.jar ie.gmit.sw.Servant

2) Web Application
- Download Apache Tomcat(version 6.0.47 used for the development of this application)
- Drag the "comparator.war" File into the apache-tomcat\webapps folder,
- Navigate into the apache-tomcat\bin and run the "startUp.bat" file.

Once both have been started, open op a Web Browser and paste in the Url: http://localhost:8080/comparator

Boom! You now have An Asynchronous RMI String Comparison Service.

#Algorithms Implemented.

 
- Hamming Distance (1950)
- Levenshtein Distance (1965)
- Damerau-Levenshtein Distance (1966)
- Jaro–Winkler Distance (1990)
- Needleman-Wunsch (1970)
- Smith Waterman (1981)


The other 2 classes containing the following algorithms work, but dont return the right result,
Therefore they are not implemented Via the interface Algorithm. or on the webapp in a dropdown list.

- Hirschberg's Algorithm (1975)
- Euclidean Distance (~300BC)




