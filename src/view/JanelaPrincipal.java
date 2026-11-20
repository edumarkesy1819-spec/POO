package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import controller.PlanejamentoController;

public class JanelaPrincipal extends JFrame {
    private PlanejamentoController controller;

    public JanelaPrincipal(PlanejamentoController controller) {
        this.controller = controller;
        setTitle("Cabe no bolso");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane abas = new JTabbedPane();
        add(abas);
        abas.addTab("Participantes", new PainelParticipantes(controller));
    }
}
