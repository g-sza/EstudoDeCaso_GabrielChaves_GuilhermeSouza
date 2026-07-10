package entidades;

import excecoes.ExcecaoCpfDuplicado;
import excecoes.ExcecaoImovelIndisponivel;
import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

import java.util.ArrayList;
import java.util.List;

public class ServicoImobiliaria {


    private List<Cliente> clientes = new ArrayList<>();
    private List<Imovel> imoveis = new ArrayList<>();
    private List<Contrato> contratos = new ArrayList<>();

    public ServicoImobiliaria(List<Cliente> clientes, List<Imovel> imoveis, List<Contrato> contratos) {
        this.clientes = clientes;
        this.imoveis = imoveis;
        this.contratos = contratos;
    }

    public void cadastrarClientes(Cliente c) {
        if (c == null) {
            throw new IllegalArgumentException("O Cliente é inválido.");
        }
        if (clientes.contains(c)) {
            throw new ExcecaoCpfDuplicado("O CPF já existe.");        }

        clientes.add(c);
    }

    public void cadastrarImovel (Imovel i) {
        if (i == null) {
            throw new IllegalArgumentException("O imóvel é inválido.");
        }
        if (imoveis.contains(i)) {
            throw new ExcecaoImovelIndisponivel("O imóvel está indisponível.");
        }
        imoveis.add(i);
    }

    public List<Imovel> listarImoveis() {
        return new ArrayList<>(imoveis);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public void venderImovel(Imovel imovel, Cliente comprador) {
        validarImovelECliente(imovel, comprador);
        if (imovel.getStatus() != StatusImovel.Disponivel) {
            throw new ExcecaoImovelIndisponivel ("Não é possível vender um imóvel indisponível.");
        }

        Contrato contrato = new Contrato(
                comprador,
                imovel,
                "Venda",
                imovel.calcularValorFinal());

        imovel.setStatus(StatusImovel.Vendido);
        contratos.add(contrato);

        System.out.println(contrato.emitirContrato());
    }


    public void alugarImovel(Imovel imovel, Cliente locatario, double valorMensal) {
        validarImovelECliente(imovel, locatario);

        if (valorMensal <= 0) {
            throw new ExcecaoValorInvalido("O valor mensal do aluguel deve ser maior que zero.");
        }

        if (imovel.getStatus() != StatusImovel.Disponivel) {
            throw new ExcecaoImovelIndisponivel("Não é possível alugar um imóvel indisponível.");
        }

        Contrato contrato = new Contrato(
                locatario,
                imovel,
                "Aluguel",
                valorMensal
        );

        imovel.setStatus(StatusImovel.Alugado);
        contratos.add(contrato);

        System.out.println(contrato.emitirContrato());
    }


    private void validarImovelECliente(Imovel imovel, Cliente cliente) {
        if (imovel == null) {
            throw new ExcecaoValidacao("Imóvel inválido.");
        }

        if (cliente == null) {
            throw new ExcecaoValidacao("Cliente inválido.");
        }

        if (!imoveis.contains(imovel)) {
            throw new ExcecaoValidacao("Imóvel não cadastrado.");
        }

        if (!clientes.contains(cliente)) {
            throw new ExcecaoValidacao("Cliente não cadastrado.");
        }
    }
}
