import java.util.List;

public class TesteRateio {
    public static void main(String[] args) {
        Participante ana = new Participante("Ana", 7000, 1);
        Participante bruno = new Participante("Bruno", 6000, 2);
        Participante carla = new Participante("Carla", 7000, 3);

        Despesa transporte = new DespesaFixa("Transporte", 9000, List.of(ana, bruno, carla));
        Despesa almoco = new DespesaPorPessoa("Almoço", 2000, List.of(ana, bruno, carla));
        Despesa atividadeRecreativa = new DespesaFixa("Atividade recreativa", 3000, List.of(ana, carla));
        Despesa sobremesa = new DespesaPorPessoa("Sobremesa", 1000, List.of(ana, bruno, carla));

        Planejamento planejamento = new Planejamento();
        planejamento.adicionarParticipante(ana);
        planejamento.adicionarParticipante(bruno);
        planejamento.adicionarParticipante(carla);
        planejamento.adicionarDespesa(transporte);
        planejamento.adicionarDespesa(almoco);
        planejamento.adicionarDespesa(atividadeRecreativa);
        planejamento.adicionarDespesa(sobremesa);

        List<Participante> participantes = List.of(ana, bruno, carla);

        for (Participante participante : participantes) {
            int total = planejamento.calcularTotalDe(participante);

            System.out.println("  " + participante.getNome() + " deve pagar: " + total);
        }
        System.out.println("Total gasto: " + planejamento.calcularCustoTotal());

        Despesa testeCentavos = new DespesaFixa("Teste centavos", 10000, List.of(carla, ana, bruno));

        for (Participante participante : participantes) {
            int parteTesteCentavos = testeCentavos.calcularParteDe(participante);
            System.out.println("  " + participante.getNome() + " deve pagar: " + parteTesteCentavos);
        }
    }
}