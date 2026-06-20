package excecoes;

public class ExcecaoValidacao extends RuntimeException {

    public ExcecaoValidacao(String mensagem) {
        super(mensagem);
    }
}
