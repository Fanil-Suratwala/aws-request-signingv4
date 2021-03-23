package com.aws.requestsigner.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.annotations.Test;

import com.aws.requestsigner.signapi.SignRequest;

public class TestCase {

	@Test
	public void test() throws ClientProtocolException, IOException {
		SignRequest signRequest = new SignRequest();
		String serviceName = "request_url"; // Add the request URI here.
		HttpGet httpGet = new HttpGet(serviceName);
		CloseableHttpClient httpClient = signRequest.signingClientForServiceName(serviceName);
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

}