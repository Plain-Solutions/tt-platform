TT  Platform 
===========
[SSU TimeTables](http://ssutt.org) is a project maintained by Plain Solutions, group of several students, which is created to bring university schedules to your palm - your mobile devices.

General
=======
TT Platform is a connector between [TT Core Library](http://github.com/plain-solutions/tt-core) and the web interface. This module allows creating Java Servlets and database initialization via `HttpServlet`.

Please refer [TT Core Library](http://github.com/plain-solutions/tt-core) README.

Now Platform consist of three modules:
 
 * `TomcatInit` - database and `TTFactory` initialization module represented by `ServerContextListener`, so it runs on Tomcat startup as well. Here creates a factory with requires implementations of TT Core abstractions: DataFetcher, SQL manager and so on. Next, Update mechanism is configured. 
 * `AbstractDataConverter` - moved back from TT Core - representing abstraction. In this package you can see `JSONConverter`, which is used to deliver data to clients.
 *  `Resources` - Java servlet created with JAX-RS, delivered by Jersey, providing access to TT Core features and returning them. All the data returned in web-friendly format - JSON.

Dependencies
============
The whole library is built with Maven, but here is the brief list of main dependencies:

* Java 7 (jdk>=1.7.0)
* TT Core should be provided in local maven repository. (tt-core>=2.1.0)
* Glassfish implementation of Jersey:
     * jersey-container-servlet >=2.7
     * jersey-client >=2.7
* Google GSON
     * gson >=2.2.4

License
=======
TT Platform is licensed under Apache License v2.

Authors
=======
Plain Solutions Dev Team.

Contacts
========
You can find more information (in Russian, however, we believe that all the developer information should be represeneted in Engish) in our VK community: 

[SSU TT](http://vk.com/ssutt)