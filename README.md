Google_IO_2013_poznan
=====================

Aplikacja na Google IO Poznań 2013

RestClient How To
=====================

````java
RestClient restClient = new RestClient(getUrlApiMethod("api_method_name"),
  			RequestMethod.POST);

restClient.addParam("param_name", param_value);

restClient.buildRequest();
restClient.executeRequest(); // Blocking

restClient.getResponse();
````
