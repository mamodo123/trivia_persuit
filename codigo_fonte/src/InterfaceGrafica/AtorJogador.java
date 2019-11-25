package InterfaceGrafica;

import Rede.AtorNetgames;

public class AtorJogador {
	
	protected AtorNetgames ngServer;

	public AtorJogador(AtorNetgames ator_netgames) {
		ngServer = ator_netgames;
	}

	public String conectar(String string, String string2) {
		return ngServer.conectar(string, string2);
	}
	
	public String desconectar() {
		return ngServer.desconectar();
	}
	
	public String iniciarPartida() {
		return ngServer.iniciarPartida();
	}

}
