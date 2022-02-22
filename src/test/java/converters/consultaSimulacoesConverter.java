package converters;

import java.util.*;
import org.json.JSONObject;

import listas.consultaSimulacoes;

public class consultaSimulacoesConverter {

	public static List<consultaSimulacoes> consulta(JSONObject json, boolean duplicado) {
		List<consultaSimulacoes> dados = new ArrayList<>();

		consultaSimulacoes dado = new consultaSimulacoes();
		if (duplicado == false) {
			dado.setId(json.get("id"));
			dado.setNome(json.get("nome"));
			dado.setCpf(json.get("cpf"));
			dado.setEmail(json.get("email"));
			dado.setValor(json.get("valor"));
			dado.setParcelas(json.get("parcelas"));
			dado.setSeguro(json.get("seguro"));

		}
		
		if(duplicado==true) {
			dado.setMessage(json.get("mensagem"));
		}
		dados.add(dado);

		return dados;

	}

}
