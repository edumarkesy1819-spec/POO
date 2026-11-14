import java.util.List;

public class DespesaPorPessoa extends Despesa {
    public DespesaPorPessoa(String descricao, int valor, List<Participante> participantes) {
        super(descricao, valor, participantes);
    }

    @Override 
    public int calcularParteDe(Participante participante) {
        return 0;
    }
}