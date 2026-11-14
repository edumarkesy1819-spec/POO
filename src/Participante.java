public class Participante {
    private String nome;
    private int orcamento;
    private int codigo;

    public Participante(String nome, int orcamento, int codigo) {
        this.nome = nome;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrcamento(int orcamento) {
        this.orcamento = orcamento;
    }

}