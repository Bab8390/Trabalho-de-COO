public class Colisões {
    public static void verificarColisoes(long currentTime, Player player, ArrayList<Projetil> playerProjectiles, ArrayList<Projetil> enemyProjectiles, ArrayList<Enemy> enemies) {
        if(player.getState() == Entidade.ACTIVE){
            colisõesProjeteisvsEnemies(currentTime, playerProjectiles, enemies);
			colisõesPlayervsEnemies(currentTime, player, enemies);
			colisõesEnemysProjeteisvsPlayer(currentTime, player, enemyProjectiles);

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
                            enemy.setState(Entidade.EXPLODING);
                            enemy.setExplosion_start(currentTime);
                            enemy.setExplosion_end(currentTime + 500);
                            ep.setState(Entidade.INACTIVE);
                        }
                    }
                }
            }
        }
    }

    public static void colisõesPlayervsEnemies(long currentTime, Player player, ArrayList<Enemy> enemies) {		
	    /* colisões player - inimigos */			
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
}