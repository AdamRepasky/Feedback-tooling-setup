# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [1.0.2 - 2023-11-21]
### Added
- Templates
- Converter config
- Gitlab CI
- Anonym in dktk.xml
- Extract relativ id in dktk.xml
- Condition in surgery op and radiation therapy end in dktk.xml
- Attribute join fhir path
- Source ID in templates
- teiler-files directory with output and temporal subdirectories
- Add opal template
- Json Format
- Xml Format
- QB
- FHIR Packages
- Opal Permissions
- Clean Temp Files configuration
- CQL Converter
- CQL to Data
- ExLiquid Template
- id and link in containers and attributes
- default name
- Blaze Page Size environment variable

### Changed
- Boolean Type -> Show value in dktk.xml
- Redefine Progress in dktk.xml
- Complete TNMc/p
- Unify TNMc and TNMp in TNM
- dktk.xml -> ccp.xml
- Rename teiler to exporter
- Deactivate Version Validation
- Move fhir packages
- Rename FHIR_QUERY to FHIR_PATH
- Rename FHIR_PATH to FHIR_SEARCH
- Opal: User exporter instead of administrator
- Opal-ccp.xml: opal-project="PROJECT-${PROJECT-ID}"


### Fixed
- Mapping in dktk.xml
- Add opal prefix
- Cql converter
