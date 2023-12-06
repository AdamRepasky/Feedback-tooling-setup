library(DSI)
library(DSOpal)
library(dsBaseClient)

token <- "{}"
projects <- "{}"

builder <- DSI::newDSLoginBuilder(.silent = FALSE)
builder$append(server='DockerOpal', url="https://opal:8443/opal/", token=token, table=projects, driver="OpalDriver", options = "list(ssl_verifyhost=0,ssl_verifypeer=0)")
builder$append(server='DockerOpal', url="https://opal:8443/opal/", token=token, table=projects, driver="OpalDriver", options = "list(ssl_verifyhost=0,ssl_verifypeer=0)")
builder$append(server='DockerOpal', url="https://opal:8443/opal/", token=token, table=projects, driver="OpalDriver", options = "list(ssl_verifyhost=0,ssl_verifypeer=0)")
builder$append(server='DockerOpal', url="https://opal:8443/opal/", token=token, table=projects, driver="OpalDriver", options = "list(ssl_verifyhost=0,ssl_verifypeer=0)")

logindata <- builder$build()
connections <- DSI::datashield.login(logins = logindata, assign = TRUE, symbol = "D")

ds.dim(x="D")
datashield.tables(connections)
