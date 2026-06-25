package entidades;

import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

public class Casa extends Imovel{
    private int nrQuartos;
    private boolean garagem;
    private double iptu;
    
    public Casa(Endereco endereco, double valor, double area, int nrQuartos, boolean garagem, double iptu){
        super(endereco, valor, area);
        setNrQuartos(nrQuartos);
        setGaragem(garagem);
        setIptu(iptu);
    }

    @Override
    public String toString(){
        return String.format("CASA - Código: %d, Endereço: %s, Valor Final: R$%.2f, Área: %.2fm², Quartos: %s, Garagem: %s, IPTU: R$%.2f",
            getCod(), getEndereco(), calcularValorFinal(), getArea(), this.nrQuartos, (this.garagem ? "Sim" : "Não"), this.iptu);
    }
    
    public int getNrQuartos(){
        return this.nrQuartos;
    }
    
    private void setNrQuartos(int nrQuartos){
        if (nrQuartos > 0){
            this.nrQuartos = nrQuartos;
        } else throw new ExcecaoValidacao("Quantidade de Quartos Inválida.");
    }

    public boolean getGaragem(){
        return this.garagem;
    }

    private void setGaragem(boolean garagem){
        this.garagem = garagem;
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
        return getValor() + this.iptu;
    }
}
