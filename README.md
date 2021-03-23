# aws-request-signingv4

Aws request signing using apache interceptor for v4 requests.

**Why do we need to sign requests?**

When you send HTTP requests to AWS, you sign the requests so that AWS can identify who sent them. You sign requests with your AWS access key, which consists of an access key ID and secret access key

**Why requests are signed**

- Verify the identity of the requester
- Protect data in transit
- Protect against potential replay attacks


In order to send the requests we need to sign the requests and hence we require the following : 
- request_url
- service_name
- region_name
- access_key
- secret_key

**Reference :** https://docs.aws.amazon.com/general/latest/gr/signing_aws_api_requests.html


**1] Add the following aws-request-signing-apache-interceptor dependency in POM.xml file**
               
      <repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
	<dependency>
		<groupId>com.github.awslabs</groupId>
		<artifactId>aws-request-signing-apache-interceptor</artifactId>
		<version>b3772780da</version>
	</dependency>



**2] We have used the following code :**   

    String serviceName = "request_url";
	HttpGet httpGet = new HttpGet(serviceName);
	CloseableHttpClient httpClient = signingClientForServiceName(serviceName);
	CloseableHttpResponse response = httpClient.execute(httpGet);
	String inputLine = null;
	StringBuffer str = new StringBuffer();
    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    try {
		        while ((inputLine = br.readLine()) != null) {
			    System.out.println(inputLine);
			    str.append(inputLine);
		    }
		    br.close();
		} catch (IOException e) {
			    e.printStackTrace();
	    }
	}

	CloseableHttpClient signingClientForServiceName(String serviceName) {
	AWS4Signer signer = new AWS4Signer();
	signer.setServiceName("service_name");
	signer.setRegionName("region_name");

	AWSCredentialsProvider credentials = new StaticCredentialsProvider(
	new BasicAWSCredentials("access_key", "secret_key"));
	HttpRequestInterceptor interceptor = new  AWSRequestSigningApacheInterceptor(serviceName, signer, credentials);
	return HttpClients.custom().addInterceptorLast(interceptor).build();
}
