package redcap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import util.Config;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;



public class TestGetData {

	@Test
	public void TestGetData() {
		final List<NameValuePair> params;
		final HttpPost post;
		HttpResponse resp;
		final HttpClient client;
		int respCode;
		BufferedReader reader;
		final StringBuffer result;
		String line;
		ArrayList<String> array;

		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("token", Config.REDCAP_TOKEN));
		params.add(new BasicNameValuePair("content", "record"));
		params.add(new BasicNameValuePair("format", "json"));
		params.add(new BasicNameValuePair("type", "flat"));
		params.add(new BasicNameValuePair("csvDelimiter", ""));
		array = new ArrayList<String>();
		params.add(new BasicNameValuePair("fields", array.toString()));
		params.add(new BasicNameValuePair("rawOrLabel", "raw"));
		params.add(new BasicNameValuePair("rawOrLabelHeaders", "raw"));
		params.add(new BasicNameValuePair("exportCheckboxLabel", "false"));
		params.add(new BasicNameValuePair("exportSurveyFields", "false"));
		params.add(new BasicNameValuePair("exportDataAccessGroups", "false"));
		params.add(new BasicNameValuePair("returnFormat", "json"));

		post = new HttpPost(Config.REDCAP_API_URL);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} catch (final Exception e) {
			e.printStackTrace();
		}

		result = new StringBuffer();
		client = HttpClientBuilder.create().build();
		respCode = -1;
		reader = null;
		line = null;

		resp = null;

		try {
			resp = client.execute(post);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		if (resp != null) {
			respCode = resp.getStatusLine().getStatusCode();

			try {
				reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		if (reader != null) {
			try {
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("respCode: " + respCode);
		System.out.println("result: " + result.toString());

	}

}
