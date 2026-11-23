package model;

import java.io.Serializable;

public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int orcamento;
    private int codigo;

    public Participante(String nome, int orcamento, int codigo) throws PlanejamentoException {
        validarNome(nome);
        validarOrcamento(orcamento);

        this.nome = nome.trim();
        this.orcamento = orcamento;
        this.codigo = codigo;

    }

    public String getNome() {
        return nome;
    }

    public int getOrcamento() {
        return orcamento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) throws PlanejamentoException {
        validarNome(nome);
        this.nome = nome.trim();
    }

    public void setOrcamento(int orcamento) throws PlanejamentoException {
        validarOrcamento(orcamento);
        this.orcamento = orcamento;
    }

    public void alterarDados(String novoNome, int novoOrcamento) throws PlanejamentoException {
        validarNome(novoNome);
        validarOrcamento(novoOrcamento);
        this.nome = novoNome.trim();
        this.orcamento = novoOrcamento;
    }

    private void validarOrcamento(int orcamento) throws PlanejamentoException {
        if (orcamento < 0) {
            throw new PlanejamentoException("Orçamento do participante não pode ser negativo.");
        }
    }

    private void validarNome(String nome) throws PlanejamentoException {
        if (nome == null || nome.isBlank()) {
            throw new PlanejamentoException("Nome do participante não pode ser nulo ou vazio.");
        }
    }

    @Override
    public int hashCode() {
        return codigo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Participante))
            return false;
        Participante other = (Participante) obj;
        return codigo == other.codigo;
    }

    @Override
    public String toString() {
        return "Participante: " + nome + ", Código: " + codigo;
    }
}