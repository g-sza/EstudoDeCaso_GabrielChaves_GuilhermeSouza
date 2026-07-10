package entidades;

import excecoes.ExcecaoCpfDuplicado;
import excecoes.ExcecaoImovelIndisponivel;
import excecoes.ExcecaoValidacao;
import excecoes.ExcecaoValorInvalido;

import java.text.Normalizer;
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


    public List<Imovel> buscarImovelPorTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new ExcecaoValidacao("Tipo de imóvel inválido.");
        }

        List<Imovel> encontrados = new ArrayList<>();
        String tipoNormalizado = normalizarTexto(tipo);

        for (Imovel imovel : imoveis) {
            if (tipoNormalizado.equals("casa") && imovel instanceof Casa) {
                encontrados.add(imovel);
            } else if (tipoNormalizado.equals("apartamento") && imovel instanceof Apartamento) {
                encontrados.add(imovel);
            } else if (tipoNormalizado.equals("terreno") && imovel instanceof Terreno) {
                encontrados.add(imovel);
            } else if (imovel instanceof Terreno) {
                Terreno terreno = (Terreno) imovel;

                if (normalizarTexto(terreno.getTipo().name()).equals(tipoNormalizado)) {
                    encontrados.add(imovel);
                }
            }
        }

        return encontrados;
    }


    public List<Imovel> buscarImovelPorStatus(String status) {
        StatusImovel statusConvertido = converterStatus(status);
        List<Imovel> encontrados = new ArrayList<>();

        for (Imovel imovel : imoveis) {
            if (imovel.getStatus() == statusConvertido) {
                encontrados.add(imovel);
            }
        }

        return encontrados;
    }


    public Cliente buscarClientePorCpf(String cpf) {
        String cpfLimpo = limparCpf(cpf);

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpfLimpo)) {
                return cliente;
            }
        }

        throw new ExcecaoValidacao("Cliente não encontrado.");
    }


    public Imovel buscarImovelPorCodigo(int codigo) {
        for (Imovel imovel : imoveis) {
            if (imovel.getCod() == codigo) {
                return imovel;
            }
        }

        throw new ExcecaoValidacao("Imóvel não encontrado.");
    }


    public void gerarRelatorios() {
        int qtdDisponiveis = 0;
        int qtdVendidos = 0;
        int qtdAlugados = 0;

        double totalVendas = 0;
        double totalAlugueis = 0;

        Imovel imovelMaisCaro = null;

        for (Imovel imovel : imoveis) {
            if (imovel.getStatus() == StatusImovel.Disponivel) {
                qtdDisponiveis++;
            } else if (imovel.getStatus() == StatusImovel.Vendido) {
                qtdVendidos++;
            } else if (imovel.getStatus() == StatusImovel.Alugado) {
                qtdAlugados++;
            }

            if (imovelMaisCaro == null ||
                    imovel.calcularValorFinal() > imovelMaisCaro.calcularValorFinal()) {
                imovelMaisCaro = imovel;
            }
        }

        for (Contrato contrato : contratos) {
            if ("Venda".equals(contrato.getTipoContrato())) {
                totalVendas += contrato.getValorAcordado();
            } else if ("Aluguel".equals(contrato.getTipoContrato())) {
                totalAlugueis += contrato.getValorAcordado();
            }
        }

        System.out.println("RELATÓRIO DA IMOBILIÁRIA");
        System.out.println("Quantidade de imóveis disponíveis: " + qtdDisponiveis);
        System.out.println("Quantidade de imóveis vendidos: " + qtdVendidos);
        System.out.printf("Total arrecadado com imóveis vendidos: R$%.2f%n", totalVendas);
        System.out.println("Quantidade de imóveis alugados: " + qtdAlugados);
        System.out.printf("Total arrecadado com aluguéis: R$%.2f%n", totalAlugueis);

        if (imovelMaisCaro == null) {
            System.out.println("Imóvel mais caro: nenhum imóvel cadastrado.");
        } else {
            System.out.println("Imóvel mais caro:");
            System.out.println(imovelMaisCaro);
        }
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

    private StatusImovel converterStatus(String status) {
        String statusNormalizado = normalizarTexto(status);

        if (statusNormalizado.equals("disponivel")) {
            return StatusImovel.Disponivel;
        }

        if (statusNormalizado.equals("alugado")) {
            return StatusImovel.Alugado;
        }

        if (statusNormalizado.equals("vendido")) {
            return StatusImovel.Vendido;
        }

        throw new ExcecaoValidacao("Status inválido. Use Disponivel, Alugado ou Vendido.");
    }

    private String limparCpf(String cpf) {
        if (cpf == null) {
            return "";
        }

        return cpf.replaceAll("[^0-9]", "");
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        String semAcento = Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return semAcento.trim().toLowerCase();
    }
}

