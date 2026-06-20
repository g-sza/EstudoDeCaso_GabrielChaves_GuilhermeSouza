package excecoes;

public class ExcecaoImovelIndisponivel extends RuntimeException {

    public ExcecaoImovelIndisponivel(String mensagem) {
        super(mensagem);
    }
}