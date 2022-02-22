package simulacoes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import converters.consultaSimulacoesConverter;
import listas.consultaSimulacoes;
import utils.*;

public class consultarSimulacoesExistentesPorCpf {

	private JSONObject dadosConsultaSimulacoes;
	private Object parcelas;
	private java.util.List<consultaSimulacoes> dadosConsulta;
	private Object id;
	private Object nome;
	private Long cpf = 58063164083l;
	private Object email;
	private Object valor;
	private Object seguro;
	private static String nome1 = "Bruno Morais Guida";
	private static Long cpf1 = 58063164083l;
	private static String email1 = "brunomorais444@gmail.com";
	private static int valor1 = 1000;
	private static int parcelas1 = 12;
	private static boolean seguro1 = true;

	private String dadosConsultaLista;
	private HttpResponse dadosRetornadosServico;
	private JSONObject retornoPost;
	private Object idTransacao;

	@BeforeClass
	public void consultarSimulacoes() throws ClientProtocolException, IOException {

		// Consulta RC do servi�o
		dadosRetornadosServico = consultarDadosNoServico.servicoConsultaSimulacoes();

		// Consulta o primeiro registro para valida��o
		dadosConsultaSimulacoes = consultarDadosJsonNoServico.servicoConsultaSimulacoesPorCpf(cpf);

		// Converte os dados do JSON
		dadosConsulta = consultaSimulacoesConverter.consulta(dadosConsultaSimulacoes,false);

		// Seta os valores na respectivas variáveis
		id = dadosConsulta.get(0).getId();
		nome = dadosConsulta.get(0).getNome();
		email = dadosConsulta.get(0).getEmail();
		valor = dadosConsulta.get(0).getValor();
		parcelas = dadosConsulta.get(0).getParcelas();
		seguro = dadosConsulta.get(0).getSeguro();

		// Logs dos valores de primeiro registro
		gerais.logExecucao("RC:"+ dadosRetornadosServico.getStatusLine().getStatusCode());
		gerais.logExecucao("ID: " + id);
		gerais.logExecucao("NOME: " + nome);
		gerais.logExecucao("CPF: " + dadosConsulta.get(0).getCpf());
		gerais.logExecucao("EMAIL: " + email);
		gerais.logExecucao("VALOR: " + valor);
		gerais.logExecucao("PARCELAS: " + parcelas);
		gerais.logExecucao("SEGURO: " + seguro);



		String del = deletarDadosJsonNoServico.deletarSimulacao(idTransacao);


	}

	@Test(description = "Validar os valores retornados no primeiro registro")
	public void validarValoresPrimeiroRegistro() {
		assertNotEquals(id, null, "Id retornou vazio");
		assertNotEquals(nome, null, "Nome retornou vazio");
		assertNotEquals(cpf, null, "CPF retornou vazio");
		assertNotEquals(email, null, "Email retornou vazio");
		assertNotEquals(valor, null, "Valor retornou vazio");
		assertNotEquals(seguro, null, "Seguro retornou vazio");
		assertNotEquals(parcelas, null, "Parcela retornou vazio");
		
	}
	@Test(description = "FP - Verifica se o CPF cont�m todos os numeros")
	public void verificarNumerosCpf() {
		int cpfLen = 11;

		assertEquals(cpfLen, cpf.toString().length(), "CPF invalido");
	}
	
	@Test(description = "FP - Verifica se o valor esta zerado")
	public void validarValorZerado() {
		assertNotEquals(valor, 0, "Valor zerado");
	}
	
	@Test(description = "FP - Valida RC do servico")
	public void validarRc() {
		assertEquals(200,dadosRetornadosServico.getStatusLine().getStatusCode(), "Erro no servico");
	}
}