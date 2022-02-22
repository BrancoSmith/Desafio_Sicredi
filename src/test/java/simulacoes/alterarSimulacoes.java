package simulacoes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import converters.consultaSimulacoesConverter;
import listas.consultaSimulacoes;
import utils.consultarDadosNoServico;
import utils.deletarDadosJsonNoServico;
import utils.gerais;
import utils.insereDadosJsonNoServico;

public class alterarSimulacoes {
	private static String nome = "Bruno Morais Guida";
	private static String nomeAlterado = "Java da Silva Test";
	private static Long cpf = 58063164083l;
	private static Long cpfInexistente = 11111111111l;
	private static Long cpfSujo = 60094146012l;
	private static String email = "brunomorais444@gmail.com";
	private static int valor = 1000;
	private static int parcelas = 12;
	private static boolean seguro = true;
	private JSONObject retornoPost;
	private List<consultaSimulacoes> retornoConvertido;
	private Object nomePut;
	private Object cpfPut;
	private Object emailPut;
	private Object valorPut;
	private Object parcelasPut;
	private Object seguroPut;
	private Object idTransacao;
	private List<consultaSimulacoes> retornoConvertidoDuplicado;
	private JSONObject retornoPut;
	private JSONObject dadosConsultaComRestricoes;
	private int codServico1;
	private List<consultaSimulacoes> retornoRestricao;
	private List<consultaSimulacoes> retornoInexistente;

	@BeforeClass
	public void inserirDadosNoServico() throws ClientProtocolException, IOException {

		
		//Envia valores para serem gravados no serviço
		retornoPost = insereDadosJsonNoServico.inserirSimulacao(cpf, nome, email, valor, parcelas, seguro);

		//Altera o nome do registro inserido na linha 45 
		retornoPut = insereDadosJsonNoServico.alterarSimulacao(cpf, nomeAlterado, email, valor, parcelas, seguro);
		
		//Converte os valores do JSON
		retornoConvertido = consultaSimulacoesConverter.consulta(retornoPut,false);

		//Define os valores como variáveis
		nomePut = retornoConvertido.get(0).getNome();
		cpfPut = retornoConvertido.get(0).getCpf();
		emailPut = retornoConvertido.get(0).getEmail();
		valorPut = retornoConvertido.get(0).getValor();
		parcelasPut = retornoConvertido.get(0).getParcelas();
		seguroPut = retornoConvertido.get(0).getParcelas();
		idTransacao = retornoConvertido.get(0).getId();
		
		//Log para mostrar os dados gravados
		gerais.logExecucao("\n" + "ID Transação:" + idTransacao + "\n" + "Nome original: "+nome+"\n"+"Nome alterado: " + nomePut + "\n"
				+ "CPF inserido: " + cpfPut + "\n" + "E-mail inserido: " + emailPut + "\n" + "Valor inserido: "
				+ valorPut + "\n" + "Parcelas inseridas: " + parcelasPut + "\n" + "Seguro inserido: " + seguroPut
				+ "\n");

		//Nova solicitação para simular dados duplicados
		retornoPost = insereDadosJsonNoServico.inserirSimulacao(cpf, nome, email, valor, parcelas, seguro);
		retornoConvertidoDuplicado = consultaSimulacoesConverter.consulta(retornoPost,true);
		
		
		//TODO:Verifica se o cpf possui restrição, porém os CPF's com restrição ainda sim estão sendo alterados.
		//dadosConsultaComRestricoes = insereDadosJsonNoServico.alterarSimulacao(cpfSujo, nome, email, valor, parcelas, seguro);
		//retornoRestricao = consultaSimulacoesConverter.consulta(dadosConsultaComRestricoes,true);

		//Validação para dados inexistentes, se não existir, retorna 404
		retornoPut = insereDadosJsonNoServico.alterarSimulacao(cpfInexistente, nomeAlterado, email, valor, parcelas, seguro);
		retornoInexistente = consultaSimulacoesConverter.consulta(retornoPut,true);

		
		//Deleção do registro para evitar testblock
		String del = deletarDadosJsonNoServico.deletarSimulacao(idTransacao);
		assertEquals(del, "OK", "FALHA AO DELETAR O REGISTRO");

	}

	@Test(description = "FP - Verifica se o registro foi gravado no serviço")
	public void verificarRegistro() {
		assertEquals(retornoConvertido.get(0).getNome(), nomeAlterado, "Erro no serviço, procurar analista");

	}

	@Test(description = "FP - Verifica se existem dados vazios no retorno do serviço")
	public void verificaDadosVazios() {
		assertNotEquals(idTransacao, null, "Id retornou vazio");
		assertNotEquals(nomePut, null, "Nome retornou vazio");
		assertNotEquals(cpfPut, null, "CPF retornou vazio");
		assertNotEquals(emailPut, null, "Email retornou vazio");
		assertNotEquals(valorPut, null, "Valor retornou vazio");
		assertNotEquals(seguroPut, null, "Seguro retornou vazio");
		assertNotEquals(parcelasPut, null, "Parcela retornou vazio");
	}
	@Test(description = "FA - Verifica mensagem de CPF duplicado")
	public void verificaCpfDuplicado() {
		assertEquals(retornoConvertidoDuplicado.get(0).getMessage(), "CPF duplicado", "Tratamento para dados duplicados com falha");
	}
	
	@Test(description = "FA - Verifica mensagem de CPF inexistente")
	public void verificaCpfInexistente() {
		assertEquals(retornoInexistente.get(0).getMessage(), "CPF "+cpfInexistente+" não encontrado");
	}
}
