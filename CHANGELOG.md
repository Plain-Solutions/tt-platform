Changelog
=========
<sub><sup>(Fix builds are in ***italics***)</sup></sub>

###***TT Platform 2.1.1***

* Fixes #7: proper status codes were not returned on error case.

###TT Platform 2.1
* Updated departments output (simplification).
* Updated timetables output (simplification).
* All or only full groups are now the parameter. 
* **Updated API. See [reference](https://github.com/Plain-Solutions/tt-platform/blob/dev/docs/API%20Reference.md)**

###TT Platform 2.0

* **Moved all data converting from TT Core to TT Platform**
* **Moved from Java Spark to JAX-RS and Jersey**
* **Updated API. See [reference](https://github.com/Plain-Solutions/tt-platform/blob/dev/docs/API%20Reference.md)**
* Added non-empty only group fetching (`/all` - all groups, `/nonemp` - for non-empty only)
* Now requeries:
	* `tt-core>=2.1.0`
	* `jersey-container-servlet=2.7`
	* `jersey-client=2.7`
* All the routes are properly encoded with `@Produces("application/json;charset=UTF-8")`
* Getting departments messages are moved to dedicated `/msg` route (refer API).
* Updated format of getting errors.
* Factory moved to TT Core. 

=====

###TT Platform 1.2
* Removed configuration file `tt.properties` from distribution as it includes private data.
* Now requires `tt-core`***>=2.0.0-rc***
* Support `LexxDataFetcher`
* `tt.properties` now has parameter `firststart` to fill DB for the first time and `ldf` to get LDF credentials.
* Added `TTTimer` support and jobs initialization. 

###TT Platform 1.1

* Reworked logics of `TomcatInit` - now it does not init database, only connects.
* `TTDataManagerFactory` is configured with help of local properties file.
* This configuration can be collected via native method, returning JSON array.

###TT Platform 1.0.3

* Added proper database check: now doen't require obligatory update on each deploy to Tomcat.
* Added **URL decoding**. Now SSU TT really support string group names.

###***TT Platform 1.0.2***
* Redirecting `/` to [developers page](ssutt.org/developers).

###***TT Platform 1.0.1***
* Fixed output results to proper format of `application/json` with headers fixing.


###TT Platform 1.0.0
* Release. Please refer [release page](https://github.com/Plain-Solutions/tt-platform/releases/tag/1.0.0).