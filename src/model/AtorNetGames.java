import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;

public class AtorNetGames implements OuvidorProxy {
    private Proxy proxy;

    public AtorNetGames() {
        this.proxy = Proxy.getInstance();
    }

    public boolean conectar(String servidor, String nome) {
        // TODO verificar se o metodo levanta excecoes ou algo parecido
        this.proxy.conectar(servidor, nome);
    }
}
