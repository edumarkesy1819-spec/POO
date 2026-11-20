import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Planejamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Despesa> despesas;
    private List<Participante> participantes;

    public Planejamento() {
        this.despesas = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    public List<Participante> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    public List<Despesa> getDespesas() {
        return Collections.unmodifiableList(despesas);
    }

    public void adicionarParticipante(Participante participante) throws PlanejamentoException {
        for (Participante p : participantes) {
            if (p.getCodigo() == participante.getCodigo()) {
                throw new PlanejamentoException("Já existe um participante com o mesmo código.");
            }
        }
        participantes.add(participante);
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    public void removerDespesa(Despesa despesa) {
        despesas.remove(despesa);
    }

    public void editarDespesa(Despesa despesaAtual, Despesa despesaNova) throws PlanejamentoException {
        int indice = despesas.indexOf(despesaAtual);
        if (indice == -1) {
            throw new PlanejamentoException("Despesa não encontrada na lista.");
        }
        despesas.set(indice, despesaNova);
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