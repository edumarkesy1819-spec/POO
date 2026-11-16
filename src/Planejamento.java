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

    public void removerDespesa(Despesa despesa) {
        despesas.remove(despesa);
    }

    public int calcularTotalDe(Participante participante) {
        int totalDe = 0;
        for (Despesa despesa : despesas) {
            totalDe += despesa.calcularParteDe(participante);
        }
        return totalDe;
    }

    public int calcularCustoTotal() {
        int totalGasto = 0;
        for (Participante participante : participantes) {
            totalGasto += calcularTotalDe(participante);
        }
        return totalGasto;
    }

    public int calcularExcedenteDe(Participante participante) {
        int totalDe = calcularTotalDe(participante);
        int orcamento = participante.getOrcamento();
        if (totalDe > orcamento) {
            return totalDe - orcamento;
        } else {
            return 0;
        }
    }
}