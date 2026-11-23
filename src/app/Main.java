package app;

import persistencia.ArquivoPlanejamento;
import persistencia.RepositorioPlanejamento;
import controller.PlanejamentoController;
import view.JanelaPrincipal;

public class Main {
    public static void main(String[] args) {
        // Inicialização do aplicativo
        RepositorioPlanejamento repositorio = new ArquivoPlanejamento("planejamento.dat");
        PlanejamentoController controller = new PlanejamentoController(repositorio);
        JanelaPrincipal janela = new JanelaPrincipal(controller);
        janela.setVisible(true);
    }
}