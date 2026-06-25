package entidades;

import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

public class Apartamento extends Imovel {
    private int andar;
    private int numeroApt;
    private double vlrCondominio;
    private double iptu;
    
    public Apartamento(Endereco endereco, double valor, double area, int andar, int numeroApt, double vlrCondominio, double iptu){
        super(endereco, valor, area);
        setAndar(andar);
        setNumeroApt(numeroApt);
        setVlrCondominio(vlrCondominio);
        setIptu(iptu);
    }

    @Override
    public String toString(){
        return String.format("APARTAMENTO - Código: %d, Endereço: %s, Valor Final: R$%.2f, Área: %.2fm², Andar: %dº, Número Apt: %d, Condomínio: R$%.2f, IPTU: R$%.2f", 
            getCod(), getEndereco(), calcularValorFinal(), getArea(), this.andar, this.numeroApt, this.vlrCondominio, this.iptu);
    }
    
    public int getAndar(){
        return this.andar;
    }
    
    private void setAndar(int andar){
        if (andar >= 0){
            this.andar = andar;
        } else throw new ExcecaoValidacao("Número de Andar Inválido.");
    }

    public int getNumeroApt(){
        return this.numeroApt;
    }

    private void setNumeroApt(int numeroApt){
        if (numeroApt > 0){
            this.numeroApt = numeroApt;
        } else throw new ExcecaoValidacao("Número de Apartamento Inválido.");
    }

    public double getVlrCondominio(){
        return this.vlrCondominio;
    }

    private void setVlrCondominio(double vlrCondominio){
        if (vlrCondominio >= 0){
            this.vlrCondominio = vlrCondominio;
        } else throw new ExcecaoValorInvalido("Valor de Condomínio Inválido.");
    }

    public double getIptu(){
        return this.iptu;
    }
    
    private void setIptu(double iptu){
        if (iptu >= 0){
            this.iptu = iptu;
        } else throw new ExcecaoValorInvalido("Valor de IPTU Inválido.");
    }

    @Override
    public double calcularValorFinal(){
        return getValor() + this.vlrCondominio + this.iptu;
    }
}