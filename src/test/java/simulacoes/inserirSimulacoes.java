package simulacoes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import converters.consultaSimulacoesConverter;
import listas.consultaSimulacoes;
import utils.deletarDadosJsonNoServico;
import utils.gerais;
import utils.insereDadosJsonNoServico;

public class inserirSimulacoes {
	private static String nome = "Bruno Morais Guida";
	private static Long cpf = 58063164083l;
	private static String email = "brunomorais444@gmail.com";
	private static int valor = 1000;
	private static int parcelas = 12;
	private static boolean seguro = true;
	private JSONObject retornoPost;
	private List<consultaSimulacoes> retornoConvertido;
	private Object nomePost;
	private Object cpfPost;
	private Object emailPost;
	private Object valorPost;
	private Object parcelasPost;
	private Object seguroPost;
	private Object idTransacao;
	private List<consultaSimulacoes> retornoConvertidoDuplicado;

	@BeforeClass
	public void inserirDadosNoServico() throws ClientProtocolException, IOException {

		
		//Envia valores para serem gravados no servi�o
		retornoPost = insereDadosJsonNoServico.inserirSimulacao(cpf, nome, email, valor, parcelas, seguro);

		//Converte os valores do JSON
		retornoConvertido = consultaSimulacoesConverter.consulta(retornoPost,false);

		//Define os valores como vari�veis
		nomePost = retornoConvertido.get(0).getNome();
		cpfPost = retornoConvertido.get(0).getCpf();
		emailPost = retornoConvertido.get(0).getEmail();
		valorPost = retornoConvertido.get(0).getValor();
		parcelasPost = retornoConvertido.get(0).getParcelas();
		seguroPost = retornoConvertido.get(0).getParcelas();
		idTransacao = retornoConvertido.get(0).getId();
		
		//Log para mostrar os dados gravados
		gerais.logExecucao("\n" + "ID Transa��o:" + idTransacao + "\n" + "Nome inserido: " + nomePost + "\n"
				+ "CPF inserido: " + cpfPost + "\n" + "E-mail inserido: " + emailPost + "\n" + "Valor inserido: "
				+ valorPost + "\n" + "Parcelas inseridas: " + parcelasPost + "\n" + "Seguro inserido: " + seguroPost
				+ "\n");

		//Nova solicita��o para simular dados duplicados
		retornoPost = insereDadosJsonNoServico.inserirSimulacao(cpf, nome, email, valor, parcelas, seguro);
		retornoConvertidoDuplicado = consultaSimulacoesConverter.consulta(retornoPost,true);


	}

	@Test(description = "FP - Verifica se o registro foi gravado no servi�o")
	public void verificarRegistro() {
		assertEquals(retornoConvertido.get(0).getNome(), nome, "Erro no servi�o, procurar analista");

	}

	@Test(description = "FP - Verifica se existem dados vazios no retorno do servi�o")
	public void verificaDadosVazios() {
		assertNotEquals(idTransacao, null, "Id retornou vazio");
		assertNotEquals(nomePost, null, "Nome retornou vazio");
		assertNotEquals(cpfPost, null, "CPF retornou vazio");
		assertNotEquals(emailPost, null, "Email retornou vazio");
		assertNotEquals(valorPost, null, "Valor retornou vazio");
		assertNotEquals(seguroPost, null, "Seguro retornou vazio");
		assertNotEquals(parcelasPost, null, "Parcela retornou vazio");
	}
	@Test(description = "FA - Verifica mensagem de CPF duplicado")
	public void verificaCpfDuplicado() {
		assertEquals(retornoConvertidoDuplicado.get(0).getMessage(), "CPF duplicado", "Tratamento para dados duplicados com falha");
	}
}
