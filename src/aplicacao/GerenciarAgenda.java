package aplicacao;

import entidades.*;
import excecoes.ExcecaoValidacao;

import java.util.List;
import java.util.Scanner;

public class GerenciarAgenda {

    private static final Scanner sc = new Scanner(System.in);
    private static final ServicoImobiliaria servico = new ServicoImobiliaria();

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Digite a sua opção: ");

            try {
                executarOpcao(opcao);
            } catch (RuntimeException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }

        } while (opcao != 8);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("SISTEMA IMOBILIÁRIA ");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Cadastrar Imóvel");
        System.out.println("3 - Listar Imóveis");
        System.out.println("4 - Vender Imóvel");
        System.out.println("5 - Alugar Imóvel");
        System.out.println("6 - Buscar Imóvel");
        System.out.println("7 - Relatórios");
        System.out.println("8 - Sair do Sistema");
    }

    private static void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarCliente();
                break;
            case 2:
                cadastrarImovel();
                break;
            case 3:
                listarImoveis();
                break;
            case 4:
                venderImovel();
                break;
            case 5:
                alugarImovel();
                break;
            case 6:
                buscarImovel();
                break;
            case 7:
                servico.gerarRelatorios();
                break;
            case 8:
                System.out.println("Encerrando o sistema...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void cadastrarCliente() {
        System.out.println();
        System.out.println("----- Cadastro de Cliente -----");

        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        String telefone = lerTexto("Telefone: ");
        String email = lerTexto("E-mail: ");

        Cliente cliente = new Cliente(nome, cpf, telefone, email);
        servico.cadastrarClientes(cliente);

        System.out.println("Cliente cadastrado com sucesso.");
    }

    private static void cadastrarImovel() {
        System.out.println();
        System.out.println("----- Cadastro de Imóvel -----");
        System.out.println("1 - Casa");
        System.out.println("2 - Apartamento");
        System.out.println("3 - Terreno");

        int tipoImovel = lerInteiro("Escolha o tipo de imóvel: ");

        Endereco endereco = lerEndereco();

        double valor = lerDouble("Valor do imóvel: ");
        double area = lerDouble("Área do imóvel: ");

        Imovel imovel;

        switch (tipoImovel) {
            case 1:
                imovel = criarCasa(endereco, valor, area);
                break;
            case 2:
                imovel = criarApartamento(endereco, valor, area);
                break;
            case 3:
                imovel = criarTerreno(endereco, valor, area);
                break;
            default:
                throw new ExcecaoValidacao("Tipo de imóvel inválido.");
        }

        servico.cadastrarImovel(imovel);

        System.out.println("Imóvel cadastrado com sucesso.");
        System.out.println(imovel);
    }

    private static Endereco lerEndereco() {
        System.out.println();
        System.out.println("----- Endereço -----");

        String logradouro = lerTexto("Logradouro: ");
        int numero = lerInteiro("Número: ");
        String bairro = lerTexto("Bairro: ");
        String cidade = lerTexto("Cidade: ");

        return new Endereco(logradouro, bairro, cidade, numero);
    }

    private static Casa criarCasa(Endereco endereco, double valor, double area) {
        int nrQuartos = lerInteiro("Número de quartos: ");
        boolean garagem = lerBoolean("Possui garagem? ");
        double iptu = lerDouble("Valor do IPTU: ");

        return new Casa(endereco, valor, area, nrQuartos, garagem, iptu);
    }

    private static Apartamento criarApartamento(Endereco endereco, double valor, double area) {
        int andar = lerInteiro("Andar: ");
        int numeroApt = lerInteiro("Número do apartamento: ");
        double vlrCondominio = lerDouble("Valor do condomínio: ");
        double iptu = lerDouble("Valor do IPTU: ");

        return new Apartamento(
                endereco,
                valor,
                area,
                andar,
                numeroApt,
                vlrCondominio,
                iptu
        );
    }

    private static Terreno criarTerreno(Endereco endereco, double valor, double area) {
        System.out.println("1 - Residencial");
        System.out.println("2 - Comercial");

        int opcaoTipo = lerInteiro("Tipo do terreno: ");

        TipoTerreno tipo;

        switch (opcaoTipo) {
            case 1:
                tipo = TipoTerreno.Residencial;
                break;
            case 2:
                tipo = TipoTerreno.Comercial;
                break;
            default:
                throw new ExcecaoValidacao("Tipo de terreno inválido.");
        }

        return new Terreno(endereco, valor, area, tipo);
    }

    private static void listarImoveis() {
        System.out.println();
        System.out.println("----- Lista de Imóveis -----");

        List<Imovel> imoveis = servico.listarImoveis();
        imprimirListaImoveis(imoveis);
    }

    private static void venderImovel() {
        System.out.println();
        System.out.println("----- Venda de Imóvel -----");

        int codigo = lerInteiro("Código do imóvel: ");
        String cpf = lerTexto("CPF do comprador: ");

        Imovel imovel = servico.buscarImovelPorCodigo(codigo);
        Cliente comprador = servico.buscarClientePorCpf(cpf);

        servico.venderImovel(imovel, comprador);

        System.out.println("Venda realizada com sucesso.");
    }

    private static void alugarImovel() {
        System.out.println();
        System.out.println("----- Aluguel de Imóvel -----");

        int codigo = lerInteiro("Código do imóvel: ");
        String cpf = lerTexto("CPF do locatário: ");
        double valorMensal = lerDouble("Valor mensal do aluguel: ");

        Imovel imovel = servico.buscarImovelPorCodigo(codigo);
        Cliente locatario = servico.buscarClientePorCpf(cpf);

        servico.alugarImovel(imovel, locatario, valorMensal);

        System.out.println("Aluguel realizado com sucesso.");
    }

    private static void buscarImovel() {
        System.out.println();
        System.out.println("----- Buscar Imóvel -----");
        System.out.println("1 - Buscar por tipo");
        System.out.println("2 - Buscar por status");

        int opcao = lerInteiro("Escolha a busca: ");

        List<Imovel> resultado;

        switch (opcao) {
            case 1:
                String tipo = lerTexto("Digite o tipo: Casa, Apartamento, Terreno, Residencial ou Comercial: ");
                resultado = servico.buscarImovelPorTipo(tipo);
                break;
            case 2:
                String status = lerTexto("Digite o status: Disponivel, Alugado ou Vendido: ");
                resultado = servico.buscarImovelPorStatus(status);
                break;
            default:
                throw new ExcecaoValidacao("Opção de busca inválida.");
        }

        imprimirListaImoveis(resultado);
    }

    private static void imprimirListaImoveis(List<Imovel> imoveis) {
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel encontrado.");
            return;
        }

        for (Imovel imovel : imoveis) {
            System.out.println(imovel);
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException erro) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(sc.nextLine().replace(",", "."));
            } catch (NumberFormatException erro) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static boolean lerBoolean(String mensagem) {
        while (true) {
            String resposta = lerTexto(mensagem + "Digite S para sim ou N para não: ");

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            }

            if (resposta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Resposta inválida.");
        }
    }
}