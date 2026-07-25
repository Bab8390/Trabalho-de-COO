import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Fase {

    private final List<EventoFase> eventos; 
    private int proximoEvento; //numero do proximo evento 
    private long inicioFase; //marca tempo que começou
    private Special_enemy chefeDaFase;

    public Fase(String arquivoConfig) throws IOException {
        this.eventos = new ArrayList<>();

        //abre o arquivo txt da fase 
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoConfig))) {
            String linha;
            // le o arquivo linha por linha até o final
            while ((linha = br.readLine()) != null) {
                linha = linha.trim(); //tira tanto os espaços vazios do comeco quanto do fim
                // se linha estiver vazia 
                if (linha.isEmpty()) continue;

                // corta a linha nos espacos em branco e guarda. Ex: "INIMIGO 1 2 3 4" vira ["INIMIGO", "1", "2", "3", "4"]
                String[] campos = linha.split("\\s+"); 
                String palavraChave = campos[0].toUpperCase(); //pega o primeiro (indice 0)

                //transforma o texto txt para os dados do Java e joga na fila de eventos 
                switch (palavraChave) {
                    case "INIMIGO": {
                        int tipo = Integer.parseInt(campos[1]);
                        long quando = Long.parseLong(campos[2]);
                        double x = Double.parseDouble(campos[3]);
                        double y = Double.parseDouble(campos[4]);
                        eventos.add(new EventoFase(EventoFase.TipoEvento.INIMIGO, tipo, quando, x, y, 0));
                        break;
                    }
                    case "CHEFE": {
                        int tipo = Integer.parseInt(campos[1]);
                        int pontosVida = Integer.parseInt(campos[2]);
                        long quando = Long.parseLong(campos[3]);
                        double x = Double.parseDouble(campos[4]);
                        double y = Double.parseDouble(campos[5]);
                        eventos.add(new EventoFase(EventoFase.TipoEvento.CHEFE, tipo, quando, x, y, pontosVida));
                        break;
                    }
                    case "POWERUP": {
                        int tipo = Integer.parseInt(campos[1]);
                        long quando = Long.parseLong(campos[2]);
                        double x = Double.parseDouble(campos[3]);
                        double y = Double.parseDouble(campos[4]);
                        eventos.add(new EventoFase(EventoFase.TipoEvento.POWERUP, tipo, quando, x, y, 0));
                        break;
                    }
                    default:
                        throw new IOException("Palavra-chave desconhecida no arquivo de fase: " + palavraChave);
                }
            }
        }
        //garante que os eventos estão ordenados por tempo de ocorrencia (quando)
        eventos.sort(Comparator.comparingLong(EventoFase::quando));
    }

    //zera relogio da fase e fila ao iniciar fase
    public void iniciar(long currentTime) {
        this.inicioFase = currentTime;
        this.proximoEvento = 0;
        this.chefeDaFase = null;
    }

    
    //usado pela Main para atualizar a fase, criar inimigos, chefes e powerups no momento certo
    public void atualizar(long currentTime, List<Enemy> inimigos, List<PowerUps> powerUps) {
        long decorrido = currentTime - this.inicioFase;

        //enquanto ainda tiver evento a ser processado e o tempo de fase decorrido for maior ou igual ao tempo do evento.
        while (proximoEvento < eventos.size() && eventos.get(proximoEvento).quando() <= decorrido) {
            EventoFase ev = eventos.get(proximoEvento);

            //faz a criacao do objeto e coloca nas listas na Main 
            switch (ev.tipoEvento()) {
                
                case INIMIGO: {
                    if (ev.tipo() == 1) {
                        Enemy1 e1 = new Enemy1(ev.x(), ev.y());
                        e1.setNextShot(currentTime + 500);
                        inimigos.add(e1);
                    } else {
                        inimigos.add(new Enemy2(ev.x(), ev.y()));
                    }
                    break;
                }
                
                case CHEFE: {
                    Special_enemy chefe = ev.tipo() == 1
                            ? new Boss1(ev.x(), ev.y(), ev.pontosVida())
                            : new Boss2(ev.x(), ev.y(), ev.pontosVida());
                    inimigos.add(chefe);
                    this.chefeDaFase = chefe;
                    break;
                }
                
                case POWERUP: {
                    PowerUps p = ev.tipo() == 1
                            ? new PowerUp1(ev.x(), ev.y())
                            : new PowerUp2(ev.x(), ev.y());
                    powerUps.add(p);
                    break;
                }
            }

            proximoEvento++; // passa para o proximo evento da lista
        }
    }

   //fase concluida quando o chefe da fase foi spawnado e derrotado 
    public boolean isConcluida() {
        return this.chefeDaFase != null && this.chefeDaFase.getState() == Entidade.INACTIVE;
    }
}
