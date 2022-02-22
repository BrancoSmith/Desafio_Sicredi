package restricoes;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import converters.consultaRestricoesConverter;
import utils.consultarDadosJsonNoServico;
import utils.consultarDadosNoServico;
import utils.gerais;

public class consultarRestricoesCpf {

	private HttpResponse dadosConsultaSemRestricoes;
	private int codServico1;
	private int codServico2;

	private HttpResponse dadosConsultaComRestricoes;
	private Long cpfRestrito;
	private long cpfLimpo;
	private JSONObject consultaMensagemServ;
	private Object mensagem;

	@BeforeClass
	public void consultarCpf() throws ClientProtocolException, IOException {

		cpfRestrito = 84809766080l;
		cpfLimpo = 49289225149l;

		// Consulta dados de CPF com restri��es

		dadosConsultaComRestricoes = consultarDadosNoServico.servicoConsultaCpf(cpfRestrito);

		codServico1 = dadosConsultaComRestricoes.getStatusLine().getStatusCode();

		gerais.logExecucao("Status retornado para CPF com restri��es: " + codServico1);

		//Consultar Mensagem
		consultaMensagemServ = consultarDadosJsonNoServico.servicoConsultarRestricoes(cpfRestrito);

		mensagem = consultaRestricoesConverter.consulta(consultaMensagemServ).get(0).getMensagem();

		gerais.logExecucao("Mensagem: " + mensagem);

		// Consulta dados de CPF sem restri��es
		dadosConsultaSemRestricoes = consultarDadosNoServico.servicoConsultaCpf(cpfLimpo);

		codServico2 = dadosConsultaSemRestricoes.getStatusLine().getStatusCode();

		gerais.logExecucao("Status retornado para CPF sem restri��es: " + codServico2);

	}

	@Test(description = "FP - Verifica se o servi�o est� retornando dados v�lidos para CPF limpos")
	public void verificarStatusCpfLimpo() {

		assertEquals(200, codServico1, "Erro no servi�o para CPF limpo");
	}

	@Test(description = "FP - Verifica se o servi�o est� retornando dados v�lidos para CPF sujo")
	public void verificarStatusCpfSujo() {
		assertEquals(204, codServico2, "Erro no servi�o para CPF sujo");

	}

	@Test(description = "FP - Verifica se o serviço está retornando a mensagem correta")
	public void verificarMensagem() {
		assertEquals(mensagem, "O CPF " + cpfRestrito + " tem problema");
	}

}
