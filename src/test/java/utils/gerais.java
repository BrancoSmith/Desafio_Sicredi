package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class gerais {
		public static void logExecucao(String mensagemASerApresenta) {
			System.err
					.println("[INFO] - [" + new SimpleDateFormat("dd/MM-HH:mm:ss").format(Calendar.getInstance().getTime())
							+ "] -> " + mensagemASerApresenta);
		}
}
