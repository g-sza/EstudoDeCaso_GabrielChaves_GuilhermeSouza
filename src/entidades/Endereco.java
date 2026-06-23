package entidades;

import excecoes.ExcecaoValidacao;

public class Endereco {
    private String logradouro;
    private String bairro;
    private String cidade;
    private int numero;

    public Endereco (String logradouro, String bairro, String cidade, int numero){
        setLogradouro(logradouro);
        setBairro(bairro);
        setCidade(cidade);
        setNumero(numero);
    }

    @Override
    public String toString(){
        return String.format(
            "Endereço: Rua %s, %s, %s, %s.", this.logradouro, (this.numero == 0 ? "S/N" : this.numero), this.bairro, this.cidade);
    }

    public String getLogradouro(){
        return this.logradouro;
    }

    public void setLogradouro(String logradouro){
        if (logradouro!=null && !logradouro.isBlank())
            this.logradouro = logradouro;
        else throw new ExcecaoValidacao("Logradouro Inválido.");
    }

    public String getBairro(){
        return this.bairro;
    }

    public void setBairro(String bairro){
        if (bairro!=null && !bairro.isBlank())
            this.bairro = bairro;
        else throw new ExcecaoValidacao("Bairro Inválido.");
    }

    public String getCidade(){
        return this.cidade;
    }

    public void setCidade(String cidade){
        if (cidade!=null && !cidade.isBlank())
            this.cidade = cidade;
        else throw new ExcecaoValidacao("Cidade Inválida.");
    }

    public int getNumero(){
        return this.numero;
    }

    public void setNumero(int numero){
        if (numero>=0)
            this.numero = numero;
        else throw new ExcecaoValidacao("Numero Inválido.");
    }
}
