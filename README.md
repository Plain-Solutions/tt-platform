TT  Platform 
===========
[SSU TimeTables](http://ssutt.org) is a project maintained by Plain Solutions, group of several students, which is created to bring university schedules to your palm - your mobile devices.

General
=======
TT Platform is a connector between [TT Core Library](http://github.com/plain-solutions/tt-core) and the web interface. This module allows creating Java Servlets and database initialization via `HttpServlet`.

Now Platform consist of three modules:
 
 * `TTDataManagerFactory` - module, which instance is created on Tomcat startup. It configures parameters for `AbstractDataManager`, converting it into `SSUDataManager` with all the SSU implementations of TT Core abstractions. Please refer [TT Core Library](http://github.com/plain-solutions/tt-ore) README.
 * `TomcatInit` - database and `DataManager` initialization module represented by `ServerContextListener`, so it runs on Tomcat startup as well. 
 *   ` Actions` - Java servlets created with Java Spark, providing access to TT Core features and returning them. All the data returned in web-friendly format - JSON.

Dependencies
============
The whole library is built with Maven, but here is the brief list of main dependencies:

* Java 7 (jdk>=1.7.0)
* TT Core should be provided in local maven repository. (tt-core>=2.0.0-rc)
* Java Spark Core (spark-core=1.1.1)

License
=======
TT Core Library is licensed under Apache License v2.

Authors
=======
Plain Solutions Dev Team.

Contacts
========
You can find more information (in Russian, however, we believe that all the developer information should be represeneted in Engish) in our VK community: 

[SSU TT](http://vk.com/ssutt)