Changelog
=========
<sub><sup>(Fix builds, which were pushed to master without release are in ***italics***)</sup></sub>

###TT Platform 1.2
* Removed getting configuration as it includes private data.
* Now requires `tt-core`***>=2.0.0-rc***
* Support `LexxDataFetcher`
* `tt.properties` now has parameter `firststart` to fill DB for the first time and `ldf` to get LDF credentials.

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