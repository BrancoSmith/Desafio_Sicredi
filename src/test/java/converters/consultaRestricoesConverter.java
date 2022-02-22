package converters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import listas.consultaRestricoes;
import listas.consultaSimulacoes;

public class consultaRestricoesConverter {

	public static List<consultaRestricoes> consulta(JSONObject json) {
		List<consultaRestricoes> dados = new ArrayList<>();

		consultaRestricoes dado = new consultaRestricoes();

		dado.setMensagem(json.get("mensagem"));

		dados.add(dado);

		return dados;

	}

}
