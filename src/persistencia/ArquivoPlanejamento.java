package persistencia;

import model.Planejamento;
import model.PlanejamentoException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArquivoPlanejamento implements RepositorioPlanejamento {
    private String arquivo;

    public ArquivoPlanejamento(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(Planejamento planejamento) throws PlanejamentoException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(planejamento);
        } catch (IOException e) {
            throw new PlanejamentoException("Não foi possível salvar o planejamento: " + e.getMessage());
        }
    }

    @Override
    public Planejamento carregar() throws PlanejamentoException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object inObj = in.readObject();
            if (!(inObj instanceof Planejamento)) {
                throw new PlanejamentoException("Arquivo não contém um planejamento válido.");
            }
            return (Planejamento) inObj;
        } catch (IOException | ClassNotFoundException e) {
            throw new PlanejamentoException("Não foi possível carregar o planejamento: " + e.getMessage());
        }
    }
}