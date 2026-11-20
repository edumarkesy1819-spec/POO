import java.util.List;

public class TesteRateio {
    public static void main(String[] args) throws PlanejamentoException {
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

        imprimirSituacao(planejamento, participantes);

        System.out.println("\nAlterando o orçamento da Ana para 8000...\n");

        ana.setOrcamento(8000);
        int totalAna = planejamento.calcularTotalDe(ana);
        System.out.println("  Ana deve pagar: " + totalAna);

        int excedenteAna = planejamento.calcularExcedenteDe(ana);
        if (excedenteAna > 0) {
            System.out.println("  Ana excedeu o orçamento em: " + excedenteAna);
        } else {
            System.out.println("  Ana está dentro do orçamento.");
        }

        ana.setOrcamento(7000);

        System.out.println("\nRemovendo a despesa de atividade recreativa...\n");

        planejamento.removerDespesa(atividadeRecreativa);
        imprimirSituacao(planejamento, participantes);

        System.out.println("\nEditando despesa fixa Transporte por uma despesa por pessoa Sobremesa...\n");

        Despesa testeSobremesa = new DespesaPorPessoa("Sobremesa", 1500, List.of(carla, ana, bruno));
        planejamento.editarDespesa(transporte, testeSobremesa);

        imprimirSituacao(planejamento, participantes);

        System.out.println("\nTeste de despesa com centavos...\n");

        Despesa testeCentavos = new DespesaFixa("Teste centavos", 10000, List.of(carla, ana, bruno));

        for (Participante participante : participantes) {
            int parteTesteCentavos = testeCentavos.calcularParteDe(participante);
            System.out.println("  " + participante.getNome() + " deve pagar: " + parteTesteCentavos);
        }

        System.out.println("\nTeste de entradas inválidas...\n");

        try {
            new Participante("Diego", -100, 4);
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            new Participante("   ", 5000, 5);
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            new DespesaFixa("Hospedagem", 5000, List.of());
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            new DespesaPorPessoa("Jantar", -2000, List.of(ana, bruno));
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            planejamento.adicionarParticipante(new Participante("Eduardo", 4000, 1));
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            ana.setOrcamento(-500);
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            new DespesaFixa("Jantar", 2000,
                    List.of(ana, new Participante("Ana Clara", 5000, 1)));
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            planejamento.editarDespesa(transporte, almoco);
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        System.out.println("Orçamento da Ana: " + ana.getOrcamento());

        System.out.println("\nTotal gasto: " + planejamento.calcularCustoTotal());

        System.out.println("\nTeste de salvar e reiniciar...\n");

        RepositorioPlanejamento repositorio = new ArquivoPlanejamento("planejamento.dat");
        try {
            repositorio.salvar(planejamento);
            repositorio.salvar(planejamento);
            System.out.println("Planejamento salvo duas vezes em planejamento.dat");

            Planejamento carregado = repositorio.carregar();
            System.out.println("Planejamento carregado do arquivo.\n");

            imprimirSituacao(carregado, carregado.getParticipantes());
            System.out
                    .println("Participantes: " + carregado.getParticipantes().size() + " | Despesas: "
                            + carregado.getDespesas().size());

        } catch (PlanejamentoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\nTeste de carregar arquivo inexistente...\n");

        RepositorioPlanejamento repositorioInexistente = new ArquivoPlanejamento("arquivo_inexistente.dat");
        RepositorioPlanejamento repositorioInvalido = new ArquivoPlanejamento("../README.md");

        try {
            repositorioInexistente.carregar();
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        try {
            repositorioInvalido.carregar();
            System.out.println("ERRO: deveria ter sido recusado!");
        } catch (PlanejamentoException e) {
            System.out.println("Recusado: " + e.getMessage());
        }

        System.out.println("\nPlanejamento em uso depois das falhas...\n");

        imprimirSituacao(planejamento, planejamento.getParticipantes());

        System.out.println("\nIniciando um planejamento vazio...\n");

        System.out.println("Total gasto: " + new Planejamento().calcularCustoTotal());
    }

    private static void imprimirSituacao(Planejamento planejamento, List<Participante> participantes) {
        for (Participante participante : participantes) {
            int total = planejamento.calcularTotalDe(participante);
            System.out.println("  " + participante.getNome() + " deve pagar: " + total);

            int excedente = planejamento.calcularExcedenteDe(participante);
            if (excedente > 0) {
                System.out.println("  " + participante.getNome() + " excedeu o orçamento em: " + excedente);
            } else {
                System.out.println("  " + participante.getNome() + " está dentro do orçamento.");
            }
        }
        System.out.println("Total gasto: " + planejamento.calcularCustoTotal());
    }
}