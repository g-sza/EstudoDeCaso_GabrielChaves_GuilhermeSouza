package entidades;

import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

public abstract class Imovel implements Calculavel {
    private int cod;
    private Endereco endereco;
    private double valor;
    private double area;
    private StatusImovel status;
    private static int codAtual = 0;

    public Imovel(Endereco endereco, double valor, double area) {
        this.cod = ++codAtual;
        setEndereco(endereco);
        setValor(valor);
        setArea(area);
        this.status = StatusImovel.Disponivel;
    }

    public int getCod() {
        return this.cod;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    private void setEndereco(Endereco endereco) {
        if (endereco != null)
            this.endereco = endereco;
        else
            throw new ExcecaoValidacao("Endereço Inválido.");
    }

    public double getValor() {
        return this.valor;
    }

    private void setValor(double valor) {
        if (valor > 0)
            this.valor = valor;
        else
            throw new ExcecaoValorInvalido("Valor de Imóvel Inválido.");
    }

    public double getArea() {
        return this.area;
    }

    private void setArea(double area) {
        if (area > 0)
            this.area = area;
        else
            throw new ExcecaoValorInvalido("Área Inválida.");
    }

    public StatusImovel getStatus() {
        return this.status;
    }

    public void setStatus(StatusImovel status){
        if (status != null)
            this.status = status;
        else
            throw new ExcecaoValidacao("Status Inválido.");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Imovel other = (Imovel) obj;
        if (cod != other.cod)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.cod);
    }
}