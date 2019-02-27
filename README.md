# PowerETL

Extraction, transfer, transformation and loading tool.

[![Build Status](https://travis-ci.org/poweretl/poweretl-framework.svg?branch=master)](https://travis-ci.org/poweretl/poweretl-framework) [![codecov](https://codecov.io/gh/poweretl/poweretl-framework/branch/master/graph/badge.svg)](https://codecov.io/gh/poweretl/poweretl-framework)

# Build

`mvn clean install`

# Release

- Make sure the codebase is update to date by issuing a pull
- Make a build first from the project root
- Then `mvn release:prepare` from the project root. You will be prompted to enter the new project versions. (For now, keep all dependency versions the same as the project root version)
- If all goes well, issue `mvn release:perform`. This will result in
  - A tag created in the git repo with the release version
  - Version numbers will be automatically changed in all POMs (however the codes are still not pushed yet)
- Push the new changes to the repo to reflect the new versions in the codes

(note: if there is an error, just issue `mvn release:rollback` to rollback all changes)
