package utils;

import java.io.IOException;

import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.io.HttpMessageWriter;

public class consultarDadosNoServico {

	public static String url_restricoes = "http://localhost:8080/api/v1/restricoes/";
	public static String url_simulacao = "http://localhost:8080/api/v1/simulacoes/";
	public static int response;

	public static HttpResponse servicoConsultaCpf(Long cpf) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_restricoes + cpf);

		HttpResponse response = httpclient.execute(httpget);

		return response;

	}

	public static HttpResponse servicoConsultaSimulacoes() throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_simulacao);

		HttpResponse response = httpclient.execute(httpget);

		return response;
	}
}