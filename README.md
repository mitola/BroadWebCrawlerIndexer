BroadWebCrawlerIndexer
======================

A simple web crawler which parses through all the links on a website and is saving them into sqllite database. No setup required


File: database/MainDBClass.java:
  -Contains Database interaction table creation 
File: webcrawler/WebCrawler.java:
  -Has a simple input menu with basic parsing functionality
  

About
======================
It was created entirely as a proof of concept. To create a simple, easy to read and manipulate a basic crawler that indexes
the web from a set starting point trough all the links and saving them on the way.
The code doesn't require any db installation since it uses sqllite. 



TODO:
-Add calls to get page Title for all the new links with jsoup
