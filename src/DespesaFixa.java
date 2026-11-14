import java.util.List;

public class DespesaFixa extends Despesa {
    public DespesaFixa(String descricao, int valor, List<Participante> participantes) {
        super(descricao, valor, participantes);
    }

    @Override
    public int calcularParteDe(Participante participante) {
        return 0;
    }
}