import java.util.ArrayList;

public class Colisao {
    private static final int DANO_TIRO_PLAYER = 10; // Dano causado por um tiro do jogador contra um inimigo com pontos de vida (Chefe).

    public static void verificarColisoes(long currentTime, Player player, ArrayList<Projetil> playerProjectiles, ArrayList<Projetil> enemyProjectiles, ArrayList<Enemy> enemies, ArrayList<PowerUps> powerUps) {
        if(player.getState() == Entidade.ACTIVE){
            colisõesProjeteisvsEnemies(currentTime, playerProjectiles, enemies);
			colisõesPlayervsEnemies(currentTime, player, enemies);
			colisõesEnemysProjeteisvsPlayer(currentTime, player, enemyProjectiles);
            colisõesPlayervsPowerUps(player, powerUps);
        }
    }
    
    public static void colisõesProjeteisvsEnemies(long currentTime, ArrayList<Projetil> playerProjectiles, ArrayList<Enemy> enemies) {
		/* colisões projeteis(player) - inimigo */
        for(Projetil ep : playerProjectiles){
            if(ep.getState() == Entidade.ACTIVE){
                for(Enemy enemy : enemies){
                    if(enemy.getState() == Entidade.ACTIVE){
                        double dx = enemy.getX() - ep.getX();
                        double dy = enemy.getY() - ep.getY();
                        double dist = Math.sqrt(dx * dx + dy * dy);
                        if(dist < enemy.getRadius()){
                            if (enemy instanceof Danificavel danificavel) {
                                // Boss perde pontos de vida e só explode quando chegar a zero.
                                danificavel.sofrerDano(DANO_TIRO_PLAYER);
                                if (danificavel.estaMorto()) {
                                    enemy.setState(Entidade.EXPLODING);
                                    enemy.setExplosion_start(currentTime);
                                    enemy.setExplosion_end(currentTime + 800);
                                }
                            }else{
                                // Inimigo comum: morre com um único tiro.
                                enemy.setState(Entidade.EXPLODING);
                                enemy.setExplosion_start(currentTime);
                                enemy.setExplosion_end(currentTime + 500);
                            }
                            ep.setState(Entidade.INACTIVE);}
                    }
                }
            }
        }
    }

    public static void colisõesPlayervsEnemies(long currentTime, Player player, ArrayList<Enemy> enemies) {		
	    /* colisões player - inimigos */
        if(player.escudoAtivo) {
            return; // Se o escudo estiver ativo, não verifica colisões com inimigos
        }		

	    for(Enemy enemy : enemies){
            if(enemy.getState() == Entidade.ACTIVE){
			    double dx = enemy.getX() - player.getX();
			    double dy = enemy.getY() - player.getY();
			    double dist = Math.sqrt(dx * dx + dy * dy);
			    if(dist < (player.getRadius() + enemy.getRadius()) * 0.8){
				    player.setState(Entidade.EXPLODING);
				    player.setExplosion_start(currentTime);
				    player.setExplosion_end(currentTime + 2000);
			    }
            }
        }
    }

	public static void colisõesEnemysProjeteisvsPlayer(long currentTime, Player player, ArrayList<Projetil> enemyProjectiles) {		
        /* colisões projeteis (inimigos) - player */
        if(player.escudoAtivo) {
            return; // Se o escudo estiver ativo, não verifica colisões com projéteis
        }

		for(Projetil ep : enemyProjectiles){
			if(ep.getState() == Entidade.ACTIVE){
				double dx = ep.getX() - player.getX();
				double dy = ep.getY() - player.getY();
				double dist = Math.sqrt(dx * dx + dy * dy);
				if(dist < (player.getRadius() + ep.getRadius()) * 0.8){
					player.setState(Entidade.EXPLODING);
					player.setExplosion_start(currentTime);
					player.setExplosion_end(currentTime + 2000);
				}
			}
		}
	}

    public static void colisõesPlayervsPowerUps(Player player, ArrayList<PowerUps> powerUps) {
        for (PowerUps powerUp : powerUps) {
            if (powerUp.getState() == Entidade.ACTIVE) {
                double dx = powerUp.getX() - player.getX();
                double dy = powerUp.getY() - player.getY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < player.getRadius() + powerUp.getRadius()) {
                    powerUp.aplicarEfeito(player);
                    powerUp.setState(Entidade.INACTIVE);
                }
            }
        }
    }
}
