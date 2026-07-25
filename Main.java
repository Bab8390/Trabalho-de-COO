import java.util.ArrayList;
public class Main {

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    public static void busyWait(long time) {
        while (System.currentTimeMillis() < time)
            Thread.yield();
    }

    public static void main(String[] args) {

        boolean running = true;
        long delta;
        long currentTime = System.currentTimeMillis();

        // =======================================================
        // 1. INSTANCIAÇÃO (FORA DO LOOP)
        // =======================================================
        Player player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, 10); // 10 pontos de vida
        ArrayList<Projetil> projeteisPlayer = new ArrayList<>();
        ArrayList<Projetil> projeteisEnemy = new ArrayList<>();
        ArrayList<Enemy> inimigos = new ArrayList<>();
        ArrayList<PowerUps> powerUps = new ArrayList<>();

        Background background1 = new Background1(20, 0.070);
        Background background2 = new Background2(50, 0.045);

        long nextEnemy1 = currentTime + 2000;
        long nextEnemy2 = currentTime + 7000;
        double enemy2_spawnX = GameLib.WIDTH * 0.20;
        int enemy2_count = 0;

        GameLib.initGraphics();

        // =======================================================
        // 2. GAME LOOP
        // =======================================================
        while (running) {

            delta = System.currentTimeMillis() - currentTime;
            currentTime = System.currentTimeMillis();

            /* Lançamento de Inimigos */
            if (currentTime > nextEnemy1) {
                Enemy1 inimigo1 = new Enemy1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0);
                inimigo1.setNextShot(currentTime + 500);
                inimigos.add(inimigo1);
                nextEnemy1 = currentTime + 500;
            }

            if (currentTime > nextEnemy2) {
                Enemy2 inimigo2 = new Enemy2(enemy2_spawnX, -10.0);
                inimigos.add(inimigo2);
                enemy2_count++;

                if (enemy2_count < 10) {
                    nextEnemy2 = currentTime + 120;
                } else {
                    enemy2_count = 0;
                    enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                    nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
                }
            }

            /* Atualização do Player e Tiro */
            player.atualizar(GameLib.iskeyPressed(GameLib.KEY_UP), GameLib.iskeyPressed(GameLib.KEY_DOWN), GameLib.iskeyPressed(GameLib.KEY_LEFT), GameLib.iskeyPressed(GameLib.KEY_RIGHT), delta, currentTime);

            // =======================================================
            // SISTEMA DE RESPAWN ADICIONADO AQUI
            // =======================================================
            if (player.getState() == Entidade.INACTIVE) {
                // Cria uma nave nova exatamente na posição inicial
                player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90, player.getVida().sofrerDano(1)); // 1 ponto de vida
            }
            // =======================================================

            // Substituído o '1' por 'Entidade.ACTIVE'
            if (GameLib.iskeyPressed(GameLib.KEY_CONTROL) && player.getState() == Entidade.ACTIVE) {
                if (currentTime > player.getNextShot()) {
                    Projetil_p tirop = new Projetil_p(player.getX(), player.getY() - 2 * player.getRadius());
                    projeteisPlayer.add(tirop);
                    player.setNextShot(currentTime + 120);
                }
            }

            /* Atualização das Entidades (Movimento e Remoção) */
            for (int i = 0; i < projeteisPlayer.size(); i++) {
                Projetil pp = projeteisPlayer.get(i);
                pp.atualizar(delta);
                if (pp.getState() == Entidade.INACTIVE) {
                    projeteisPlayer.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < projeteisEnemy.size(); i++) {
                Projetil ep = projeteisEnemy.get(i);
                ep.atualizar(delta);
                if (ep.getState() == Entidade.INACTIVE) {
                    projeteisEnemy.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < inimigos.size(); i++) {
                Enemy e = inimigos.get(i);
                e.atualizar(delta, currentTime, player, projeteisEnemy);
                if (e.getState() == Entidade.INACTIVE) {
                    inimigos.remove(i);
                    i--;
                }
            }

            // =======================================================
            // ADICIONADO AQUI: CHECAGEM DE COLISÕES
            // =======================================================
            Colisao.verificarColisoes(currentTime, player, projeteisPlayer, projeteisEnemy, inimigos, powerUps);


            /* Tecla de sair */
            if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

            // =======================================================
            // 3. RENDERIZAÇÃO (Desenhando na tela)
            // =======================================================
            background1.desenhaBackground(delta);
            background2.desenhaBackground(delta);

            player.desenhaPlayer(currentTime);
            
            for (Projetil p : projeteisPlayer) p.desenhar();
            for (Projetil p : projeteisEnemy) p.desenhar();
            for (Enemy e : inimigos) e.desenhaEnemy(currentTime);

            GameLib.display();
            busyWait(currentTime + 3);
        }
		System.exit(0); // Encerra o programa quando o loop termina
    }
}
