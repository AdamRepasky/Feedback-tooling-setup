library(DSI)
library(DSOpal)
library(dsBaseClient)

#user <- "david"
#password <- "e735vwZa88OLfcryU2cq%"
user <- "administrator"
password <- "JPHXQ60EB79hVVmI4yiq"
builder <- DSI::newDSLoginBuilder(.silent = FALSE)
builder$append(server= 'DockerOpal', url = "https://opal:8443/opal/", user=user, password=password,
               table="test.tumor_data", driver="OpalDriver", options = "list(ssl_verifyhost=0,ssl_verifypeer=0)" )
# DEV: Test without certificate:
# options = "list(ssl_verifyhost=0,ssl_verifypeer=0)" found in https://datashield.discourse.group/t/question-wrt-assigning-data-from-opal-server-dsbase-6-0-1/273

logindata <- builder$build()
connections <- DSI::datashield.login(logins = logindata, assign = TRUE, symbol = "D")

ds.dim(x="D")
datashield.tables(connections)
