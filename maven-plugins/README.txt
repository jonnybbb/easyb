Maven 2.1 and above users:

This project uses the SHITTY plugin from Codehaus - and is targeted at alpha1. With the release of Maven 2.1, this plugin *no longer works*.

If you wish to build the plugin, you need to get a prior version (e.g. 2.0.10) or checkout the source for the alpha-3 version of the SHITTY,
find my (Richard Vowles) attachment to the MSHITTY-10 issue and apply it, then build and install the plugin. On Windows, the tests won't run as they
don't honour CRLFs properly.

Linux/Mac Os X:
mvn clean source:jar install

Windows:
mvn -Dmaven.test.skip=true clean source:jar install

You then need to change the dependency to alpha-3 of the SHITTY plugin, and then you can build it. 