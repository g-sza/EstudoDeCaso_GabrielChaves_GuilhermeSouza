package entidades;

import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

public class Contrato {
    private Cliente cliente;
    private Imovel imovel;
    private String tipoContrato;
    private double valorAcordado;

    public Contrato(Cliente cliente, Imovel imovel, String tipoContrato, double valorAcordado){
        setCliente(cliente);
        setImovel(imovel);
        setTipoContrato(tipoContrato);
        setValorAcordado(valorAcordado);
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    private void setCliente(Cliente cliente){
        if (cliente != null){
            this.cliente = cliente;
        } else throw new ExcecaoValidacao("Cliente Inválido.");
    }

    public Imovel getImovel(){
        return this.imovel;
    }

    private void setImovel(Imovel imovel){
        if (imovel != null){
            this.imovel = imovel;
        } else throw new ExcecaoValidacao("Imóvel Inválido.");
    }

    public String getTipoContrato(){
        return this.tipoContrato;
    }

    private void setTipoContrato(String tipoContrato){
        if ("Aluguel".equals(tipoContrato) || "Venda".equals(tipoContrato)){
            this.tipoContrato = tipoContrato;
        } else throw new ExcecaoValidacao("Tipo de Contrato Inválido.");
    }

    public double getValorAcordado(){
        return this.valorAcordado;
    }

    private void setValorAcordado(double valorAcordado){
        if (valorAcordado >= 0){
            this.valorAcordado = valorAcordado;
        } else throw new ExcecaoValorInvalido("Valor de Contrato Inválido.");
    }

    public String emitirContrato(){
        return String.format(
            "======================================================== CONTRATO DE %s ========================================================\n" +
            "► DADOS DO CLIENTE:\n%s\n\n" +
            "► DADOS DO IMÓVEL:\n%s\n\n" +
            "► VALOR ACORDADO: R$%.2f\n" +
            "=======================================================================================================================================",
            this.tipoContrato.toUpperCase(), this.cliente.toString(), this.imovel.toString(), this.valorAcordado);
    }
}