package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.HttpService;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpRequest;
import com.beust.jcommander.JCommander.Builder;

import com.google.gson.JsonObject;

public class consultarDadosJsonNoServico {

	public static int response;
	public static String url_restricoes = "http://localhost:8080/api/v1/restricoes/";
	public static String url_simulacao = "http://localhost:8080/api/v1/simulacoes/";
	private static String json;
	private static int id;
	private static String output;

	public static String servicoConsultaSimulacoesRetorno() throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_simulacao);

		HttpResponse response = httpclient.execute(httpget);

		HttpEntity entity = response.getEntity();

		String jsonString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

		return jsonString;
	}

	public static JSONObject servicoConsultaSimulacoesPrimeiroRegistro() throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_simulacao);

		HttpResponse response = httpclient.execute(httpget);

		HttpEntity entity = response.getEntity();

		String jsonString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

		output = jsonString.replace("[", "").replace("]", "");

		JSONObject jsonObject = new JSONObject(output);

		return jsonObject;
	}
	
	public static JSONObject servicoConsultarRestricoes(Long cpf) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_restricoes+cpf);

		HttpResponse response = httpclient.execute(httpget);

		HttpEntity entity = response.getEntity();

		String jsonString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

		output = jsonString.replace("[", "").replace("]", "");

		JSONObject jsonObject = new JSONObject(output);

		return jsonObject;
	}
	
	public static JSONObject servicoConsultaSimulacoesPorCpf(Long cpf) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(url_simulacao+cpf);

		HttpResponse response = httpclient.execute(httpget);

		HttpEntity entity = response.getEntity();

		String jsonString = EntityUtils.toString(entity, StandardCharsets.UTF_8);

		output = jsonString.replace("[", "").replace("]", "");

		JSONObject jsonObject = new JSONObject(output);

		return jsonObject;
	}

}
