# feedback-agent
 Feedback Agent

 First build the backend app by running
`mvn clean package -DskipTests`

 To start the application the enviroment variables in docker-compose file need to be set up to connect feedback-agent to the already existing beam network, the blaze-store running in the Bridgehead and the Feedback hub, for example:
 
 ```
      - BEAM_PROXY_URI=http://192.168.0.109:8082

      - FEEDBACK_HUB_URL=http://192.168.0.109:8071

      - BLAZE_BASE_URL=http://192.168.0.100:8091/fhir
```

 Then run and access the user interface through Bridgehead UI
`docker-compose up -d  feedback-agent-be feedback-agent-ui`
