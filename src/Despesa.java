import java.util.List;

abstract class Despesa {
    private String descricao;
    private int valor;
    private List<Participante> participantes;

    public Despesa(String descricao, int valor, List<Participante> participantes) {
        this.descricao = descricao;
        this.valor = valor;
        this.participantes = participantes;
    }

    public abstract int calcularParteDe(Participante participante);

    public int getValor() {
        return valor;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }
}