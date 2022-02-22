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

public class deletarSimulacoes {
	private static String nome = "Bruno Morais Guida";
	private static Long cpf = 58063164083l;
	private static String email = "brunomorais444@gmail.com";
	private static int valor = 1000;
	private static int parcelas = 12;
	private static boolean seguro = true;
	private JSONObject retornoPost;
	private List<consultaSimulacoes> retornoConvertido;
	private Object nomeDel;
	private Object cpfDel;
	private Object emailDel;
	private Object valorDel;
	private Object parcelasDel;
	private Object seguroDel;
	private Object idTransacao;
	private List<consultaSimulacoes> retornoConvertidoDuplicado;
	private String del;

	@BeforeClass
	public void inserirDadosNoServico() throws ClientProtocolException, IOException {

		// Envia valores para serem gravados no serviço
		retornoPost = insereDadosJsonNoServico.inserirSimulacao(cpf, nome, email, valor, parcelas, seguro);

		// Converte os valores do JSON
		retornoConvertido = consultaSimulacoesConverter.consulta(retornoPost, false);

		// Define os valores como variáveis
		nomeDel = retornoConvertido.get(0).getNome();
		cpfDel = retornoConvertido.get(0).getCpf();
		emailDel = retornoConvertido.get(0).getEmail();
		valorDel = retornoConvertido.get(0).getValor();
		parcelasDel = retornoConvertido.get(0).getParcelas();
		seguroDel = retornoConvertido.get(0).getParcelas();
		idTransacao = retornoConvertido.get(0).getId();

		// Deleção do registro
		del = deletarDadosJsonNoServico.deletarSimulacao(idTransacao);

		// Log para mostrar os dados gravados e excluídos es sequencia
		gerais.logExecucao("\n" + "ID Transação:" + idTransacao + "\n" + "Nome inserido: " + nomeDel + "\n"
				+ "CPF inserido: " + cpfDel + "\n" + "E-mail inserido: " + emailDel + "\n" + "Valor inserido: "
				+ valorDel + "\n" + "Parcelas inseridas: " + parcelasDel + "\n" + "Seguro inserido: " + seguroDel
				+ "\n");

	}

	@Test(description = "FP - Verifica se o registro foi deletado")
	public void verificarRegistroDeletado() {
		assertEquals(del, "OK", "Falha ao deletar registro");

	}
}