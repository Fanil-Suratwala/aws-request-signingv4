package com.aws.requestsigner.signapi;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AWSRequestSigningApacheInterceptor;
import com.amazonaws.internal.StaticCredentialsProvider;

@SuppressWarnings("deprecation")
public class SignRequest {

	public CloseableHttpClient signingClientForServiceName(String serviceName) {
		AWS4Signer signer = new AWS4Signer();
		signer.setServiceName("service_name");// add the service name here
		signer.setRegionName("region_name"); // add the region name here

		AWSCredentialsProvider credentials = new StaticCredentialsProvider(
				new BasicAWSCredentials("access_key", "secret_key")); // Add your aws access key and secret key here,
																		// this is required for signing the api
																		// requests.
		HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentials);
		return HttpClients.custom().addInterceptorLast(interceptor).build();

	}
}
