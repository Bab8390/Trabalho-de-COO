// Para Main
import java.awt.Color;
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

        Player player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0, 90);
        ArrayList<Projetil> projeteisPlayer = new ArrayList<>();
        ArrayList<Projetil> projeteisEnemy = new ArrayList<>();
        ArrayList<Enemy> inimigos = new ArrayList<>();

        Background background1 = new Background1(20, 20, 0.070);
        Background background2 = new Background2(50, 50, 0.045);

        GameLib.initGraphics();

        while (running) {

            delta = System.currentTimeMillis() - currentTime;

            currentTime = System.currentTimeMillis();
            long nextEnemy1 = currentTime + 2000;
            long nextEnemy2 = currentTime + 7000;
            double enemy2_spawnX = GameLib.WIDTH * 0,20;
            int enemy2_count = 0;

            // lançar inimigos
            if (currentTime > nextEnemy1) {
                Enemy1 inimigo1 = new Enemy1(Math.random() * (GameLib.WIDTH - 20.0) + 10.0, -10.0);
                inimigo1.setNextShot(currentTime + 500);
                inimigos.add(inimigo1);
                nextEnemy1 = currentTime + 500
            }

            if (currentTime > nextEnemy2) {
                Enemy2 inimigo2 = new Enemy2(enemy2_spawnX, -10.0);
                inimigos.add(inimigos2);

                if (enemy2_count < 10)
                    nextEnemy2 = currentTime + 120;
                else {
                    enemy2_count = 0;
                    enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                    nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
                }
            }
        }
    }
}
