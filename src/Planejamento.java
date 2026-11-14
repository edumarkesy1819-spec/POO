import java.util.List;
import java.util.ArrayList;

public class Planejamento {
    private List<Despesa> despesas;
    private List<Participante> participantes;

    public Planejamento() {
        this.despesas = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    public void adicionarParticipante(Participante participante) {
        participantes.add(participante);
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }
}