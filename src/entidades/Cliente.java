package entidades;

import excecoes.ExcecaoValidacao;
import java.util.Objects;

public class Cliente {

    private String nome, cpf, telefone, email;

    public Cliente(String nome, String cpf, String telefone, String email) {
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
        setTelefone(telefone);
    }



    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        if (nome == null || nome.isBlank() ) {
            throw new ExcecaoValidacao("Nome do cliente não pode estar vazio.");
        }
    }



    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        String cpfLimpo = limparCpf(cpf);

        if (!cpfValido(cpfLimpo)) {
            throw new ExcecaoValidacao("CPF inválido.");
        }
        this.cpf = cpfLimpo;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new ExcecaoValidacao("E-mail inválido.");
        }

        this.email = email.trim();
    }





    private String limparCpf(String cpf) {
        if (cpf == null) {
            return "";
        }

        return cpf.replaceAll("[^0-9]", "");
    }

    private boolean cpfValido(String cpf) {
        if (cpf.length() != 11) {
            return false;
        }

        if (todosDigitosIguais(cpf)) {
            return false;
        }

        int primeiroDigito = calcularDigito(cpf, 9);
        int segundoDigito = calcularDigito(cpf, 10);

        return primeiroDigito == Character.getNumericValue(cpf.charAt(9))
                && segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean todosDigitosIguais(String cpf) {
        char primeiro = cpf.charAt(0);

        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != primeiro) {
                return false;
            }
        }

        return true;
    }

    private int calcularDigito(String cpf, int quantidadeDigitos) {
        int soma = 0;
        int peso = quantidadeDigitos + 1;

        for (int i = 0; i < quantidadeDigitos; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso;
            peso--;
        }

        int resto = soma % 11;

        if (resto < 2) {
            return 0;
        }

        return 11 - resto;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}
