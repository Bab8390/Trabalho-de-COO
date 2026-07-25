import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void busyWait(long time) {
        while (System.currentTimeMillis() < time)
            Thread.yield();
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Uso: java Main <arquivo_de_configuracao_do_jogo>");
            return;
        }

        // =======================================================
        // 1. LEITURA DO ARQUIVO DE CONFIGURAÇÃO DO JOGO
        // =======================================================
        int pontosVidaJogador;
        List<Fase> fases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            pontosVidaJogador = Integer.parseInt(br.readLine().trim());
            int numeroFases = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < numeroFases; i++) {
                String arquivoFase = br.readLine().trim();
                fases.add(new Fase(arquivoFase));
            }
        }

        if (fases.isEmpty()) {
            System.out.println("Nenhuma fase configurada em " + args[0]);
            return;
        }

        boolean running = true;
        boolean vitoria = false;
        long delta;
        long currentTime = System.currentTimeMillis();

        // =======================================================
        // 2. INSTANCIAÇÃO (FORA DO LOOP)
        // =======================================================
        Player player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, pontosVidaJogador);

        ArrayList<Projetil> projeteisPlayer = new ArrayList<>();
        ArrayList<Projetil> projeteisEnemy = new ArrayList<>();
        ArrayList<Enemy> inimigos = new ArrayList<>();
        ArrayList<PowerUps> powerUps = new ArrayList<>();

        Background background1 = new Background1(20, 0.070);
        Background background2 = new Background2(50, 0.045);

        int faseAtualIndex = 0;
        Fase faseAtual = fases.get(faseAtualIndex);
        faseAtual.iniciar(currentTime);

        GameLib.initGraphics();
        Som musicaFundo = new Som("sons/musica_fundo.wav");
        musicaFundo.setVolume(0.5f);
        musicaFundo.tocarEmLoop();

        // =======================================================
        // 3. GAME LOOP
        // =======================================================
        while (running) {

            delta = System.currentTimeMillis() - currentTime;
            currentTime = System.currentTimeMillis();

            /* A fase cria os inimigos/chefe/power-ups programados para este instante */
            faseAtual.atualizar(currentTime, inimigos, powerUps);

            /* Atualização do Player e Tiro */
            player.atualizar(GameLib.iskeyPressed(GameLib.KEY_UP), GameLib.iskeyPressed(GameLib.KEY_DOWN),
                    GameLib.iskeyPressed(GameLib.KEY_LEFT), GameLib.iskeyPressed(GameLib.KEY_RIGHT), delta, currentTime);

            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.getState() == Entidade.ACTIVE) {
                if (currentTime > player.getNextShot()) {
                    dispararTiroJogador(player, projeteisPlayer);
                    player.setNextShot(currentTime + 120);
                }
            }

            /* Atualização das Entidades (Movimento e Remoção) */
            atualizarProjeteis(projeteisPlayer, delta);
            atualizarProjeteis(projeteisEnemy, delta);

            for (int i = 0; i < inimigos.size(); i++) {
                Enemy e = inimigos.get(i);
                e.atualizar(delta, currentTime, player, projeteisEnemy);
                if (e.getState() == Entidade.INACTIVE) {
                    inimigos.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < powerUps.size(); i++) {
                PowerUps p = powerUps.get(i);
                p.atualizar(delta);
                if (p.getState() == Entidade.INACTIVE) {
                    powerUps.remove(i);
                    i--;
                }
            }

            /* Colisões */
            Colisao.verificarColisoes(currentTime, player, projeteisPlayer, projeteisEnemy, inimigos, powerUps);

            /* Fim de jogo por perda de todos os pontos de vida do jogador */
            if (player.isGameOver()) {
                running = false;
            }

            /* Fase concluída (chefe derrotado): avança para a próxima ou vence o jogo */
            if (faseAtual.isConcluida()) {
                faseAtualIndex++;
                if (faseAtualIndex >= fases.size()) {
                    vitoria = true;
                    running = false;
                } else {
                    faseAtual = fases.get(faseAtualIndex);
                    inimigos.clear();
                    projeteisEnemy.clear();
                    powerUps.clear();
                    faseAtual.iniciar(currentTime);
                }
            }

            /* Tecla de sair */
            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

            // =======================================================
            // 4. RENDERIZAÇÃO
            // =======================================================
            background1.desenhaBackground(delta);
            background2.desenhaBackground(delta);

            player.desenhaPlayer(currentTime);

            for (Projetil p : projeteisPlayer) p.desenhar();
            for (Projetil p : projeteisEnemy) p.desenhar();
            for (Enemy e : inimigos) e.desenhaEnemy(currentTime);
            for (PowerUps p : powerUps) p.desenharPowerUp();

            GameLib.display();
            busyWait(currentTime + 3);
        }

        if (vitoria) {
            System.out.println("=========================================");
            System.out.println(" PARABENS! VOCE DERROTOU TODOS OS BOSSES E VENCEU O JOGO! ");
            System.out.println("=========================================");
            musicaFundo.parar();
        } else {
            System.out.println("=========================================");
            System.out.println("                FIM DE JOGO               ");
            System.out.println("=========================================");
            musicaFundo.parar();
        }
    }

    /** Dispara os projéteis do jogador, respeitando o número de tiros simultâneos do power-up de tiro múltiplo. */
    private static void dispararTiroJogador(Player player, List<Projetil> projeteisPlayer) {
        int numTiros = player.getNumTiros();
        double baseX = player.getX();
        double baseY = player.getY() - 2 * player.getRadius();

        if (numTiros == 1) {
            projeteisPlayer.add(new Projetil_p(baseX, baseY));
        } else {
            double espacamento = 10.0;
            double inicio = -((numTiros - 1) * espacamento) / 2.0;
            for (int i = 0; i < numTiros; i++) {
                projeteisPlayer.add(new Projetil_p(baseX + inicio + i * espacamento, baseY));
            }
        }
    }

    private static void atualizarProjeteis(List<Projetil> projeteis, long delta) {
        for (int i = 0; i < projeteis.size(); i++) {
            Projetil p = projeteis.get(i);
            p.atualizar(delta);
            if (p.getState() == Entidade.INACTIVE) {
                projeteis.remove(i);
                i--;
            }
        }
    }
}
