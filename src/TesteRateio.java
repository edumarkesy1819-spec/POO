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

        List<Despesa> despesas = List.of(transporte, almoco, atividadeRecreativa, sobremesa);
        List<Participante> participantes = List.of(ana, bruno, carla);

        int totalGasto = 0;
        for (Participante participante : participantes) {
            int total = 0;

            for (Despesa despesa : despesas) {
                total += despesa.calcularParteDe(participante);
            }

            totalGasto += total;
            System.out.println("  " + participante.getNome() + " deve pagar: " + total);
        }
        System.out.println("Total gasto: " + totalGasto);

        Despesa testeCentavos = new DespesaFixa("Teste centavos", 10000, List.of(carla, ana, bruno));

        for (Participante participante : participantes) {
            int parteTesteCentavos = testeCentavos.calcularParteDe(participante);
            System.out.println("  " + participante.getNome() + " deve pagar: " + parteTesteCentavos);
        }
    }
}