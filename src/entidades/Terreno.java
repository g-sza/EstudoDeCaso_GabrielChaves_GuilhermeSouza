package entidades;

import excecoes.ExcecaoValidacao;

public class Terreno extends Imovel {
    private TipoTerreno tipo;

    public Terreno(Endereco endereco, double valor, double area, TipoTerreno tipo){
        super(endereco, valor, area);
        setTipo(tipo);
    }

    @Override
    public String toString(){
        return String.format("TERRENO - Código: %d, Endereço: %s, Valor Final: R$%.2f, Área: %.2fm², Tipo: %s, Taxa Administrativa: 8%%",
                getCod(), getEndereco(), calcularValorFinal(), getArea(), this.tipo);
    }

    public TipoTerreno getTipo(){
        return this.tipo;
    }

    private void setTipo(TipoTerreno tipo){
        if (tipo != null){
            this.tipo = tipo;
        } else throw new ExcecaoValidacao("Tipo de Terreno Inválido.");
    }

    @Override
    public double calcularValorFinal(){
        return getValor() * 1.08;
    }
}