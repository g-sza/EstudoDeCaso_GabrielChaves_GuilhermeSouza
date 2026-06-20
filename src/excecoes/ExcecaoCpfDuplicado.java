package excecoes;

public class ExcecaoCpfDuplicado extends RuntimeException {

    public ExcecaoCpfDuplicado(String mensagem) {
        super(mensagem);
    }
}