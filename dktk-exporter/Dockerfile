FROM samply/exporter:develop

COPY templates/ /app/templates/
COPY fhir-packages/ /app/fhir-packages/
COPY converter.xml /app/converter/converter.xml

WORKDIR /app
RUN mkdir /app/exporter-files
RUN mkdir /app/exporter-files/output
RUN mkdir /app/exporter-files/temporal

ENV WRITE_FILE_DIRECTORY /app/exporter-files/output
ENV TEMPORAL_FILE_DIRECTORY /app/exporter-files/temporal
ENV FHIR_PACKAGES_DIRECTORY /app/fhir-packages
ENV CLEAN_TEMP_FILES_CRON_EXPRESSION: "0 0 1 * * *"
ENV TEMP_FILES_LIFETIME_IN_DAYS: "1"
ENV CLEAN_WRITE_FILES_CRON_EXPRESSION: "0 0 2 * * *"
ENV WRITE_FILES_LIFETIME_IN_DAYS: "30"
