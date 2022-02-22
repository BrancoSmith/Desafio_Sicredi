package simulacoes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.awt.List;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sonatype.inject.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import converters.*;
import listas.consultaSimulacoes;
import utils.consultarDadosNoServico;
import utils.consultarDadosJsonNoServico;
import java.util.*;
import utils.gerais;

public class consultarSimulacoesExistentes {

	private JSONObject dadosConsultaSimulacoes;
	private Object parcelas;
	private java.util.List<consultaSimulacoes> dadosConsulta;
	private Object id;
	private Object nome;
	private Object cpf;
	private Object email;
	private Object valor;
	private Object seguro;
	private String dadosConsultaLista;
	private HttpResponse dadosRetornadosServico;

	@BeforeClass
	public void consultarSimulacoes() throws ClientProtocolException, IOException {

		// Consulta e envia ao console a lista retornada no JSON.
		dadosConsultaLista = consultarDadosJsonNoServico.servicoConsultaSimulacoesRetorno();
		gerais.logExecucao("JSON retornado pelo servico: " + dadosConsultaLista);
		
		// Consulta RC do servi�o
		dadosRetornadosServico = consultarDadosNoServico.servicoConsultaSimulacoes();

		// Consulta o primeiro registro para valida��o
		dadosConsultaSimulacoes = consultarDadosJsonNoServico.servicoConsultaSimulacoesPrimeiroRegistro();

		// Converte os dados do JSON
		dadosConsulta = consultaSimulacoesConverter.consulta(dadosConsultaSimulacoes,false);

		// Seta os valores na respectivas variáveis
		id = dadosConsulta.get(0).getId();
		nome = dadosConsulta.get(0).getNome();
		cpf = dadosConsulta.get(0).getCpf();
		email = dadosConsulta.get(0).getEmail();
		valor = dadosConsulta.get(0).getValor();
		parcelas = dadosConsulta.get(0).getParcelas();
		seguro = dadosConsulta.get(0).getSeguro();

		// Logs dos valores de primeiro registro
		gerais.logExecucao("RC:"+ dadosRetornadosServico.getStatusLine().getStatusCode());
		gerais.logExecucao("ID primeiro registro: " + id);
		gerais.logExecucao("NOME primeiro registro: " + nome);
		gerais.logExecucao("CPF primeiro registro: " + cpf);
		gerais.logExecucao("EMAIL primeiro registro: " + email);
		gerais.logExecucao("VALOR primeiro registro: " + valor);
		gerais.logExecucao("PARCELAS primeiro registro: " + parcelas);
		gerais.logExecucao("SEGURO primeiro registro: " + seguro);

	}

	@Test(description = "Validar os valores retornados no primeiro registro")
	public void validarValoresPrimeiroRegistro() {
		assertEquals(id, 11, "Id inválido");
		assertEquals(nome, "Fulano", "Nome inválido");
		assertEquals(cpf, "66414919004", "cpf inválido");
		assertEquals(email, "fulano@gmail.com", "E-mail inválido");
		assertEquals(valor,  11000.00, "Valor inválido");
		assertEquals(parcelas, 3, "Número de parcelas inválido");
		assertEquals(seguro,true, "Seguro inválido");
		
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