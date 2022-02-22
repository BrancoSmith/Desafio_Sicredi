package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class insereDadosJsonNoServico {

	public static int response;
	public static String url_restricoes = "http://localhost:8080/api/v1/restricoes/";
	public static String url_simulacao = "http://localhost:8080/api/v1/simulacoes/";

	private static String output;

	public static JSONObject inserirSimulacao(Long cpf, String nome, String email, int valor, int parcelas,
			Boolean seguro) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		String post = "{\r\n" + "  \"nome\": \"" + nome + "\",\r\n" + "  \"cpf\": " + cpf + ",\r\n" + "  \"email\": \""
				+ email + "\",\r\n" + "  \"valor\": " + valor + ",\r\n" + "  \"parcelas\": " + parcelas + ",\r\n"
				+ "  \"seguro\": " + seguro + "\r\n" + "}";

		StringEntity entity = new StringEntity(post);

		HttpPost httppost = new HttpPost(url_simulacao);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");

		HttpResponse response = httpclient.execute(httppost);

		HttpEntity entity2 = response.getEntity();

		String jsonString = EntityUtils.toString(entity2, StandardCharsets.UTF_8);

		output = jsonString.replace("[", "").replace("]", "");

		JSONObject jsonObject = new JSONObject(output);

		return jsonObject;
	}
	public static JSONObject alterarSimulacao(Long cpf, String nome, String email, int valor, int parcelas,
			Boolean seguro) throws ClientProtocolException, IOException {

		HttpClient httpclient = HttpClients.createDefault();

		String put = "{\r\n" + "  \"nome\": \"" + nome + "\",\r\n" + "  \"cpf\": " + cpf + ",\r\n" + "  \"email\": \""
				+ email + "\",\r\n" + "  \"valor\": " + valor + ",\r\n" + "  \"parcelas\": " + parcelas + ",\r\n"
				+ "  \"seguro\": " + seguro + "\r\n" + "}";

		StringEntity entity = new StringEntity(put);

		HttpPut httpput = new HttpPut(url_simulacao+cpf);
		httpput.setEntity(entity);
		httpput.setHeader("Accept", "application/json");
		httpput.setHeader("Content-type", "application/json");

		HttpResponse response = httpclient.execute(httpput);

		HttpEntity entity2 = response.getEntity();

		String jsonString = EntityUtils.toString(entity2, StandardCharsets.UTF_8);

		output = jsonString.replace("[", "").replace("]", "");

		JSONObject jsonObject = new JSONObject(output);

		return jsonObject;
	}

}
