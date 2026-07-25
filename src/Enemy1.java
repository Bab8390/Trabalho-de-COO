import java.util.ArrayList;
import java.awt.Color;

public class Enemy1 extends Enemy { // 1. Corrigido: Adicionado o nome da classe
    private long nextShot;
    
    public Enemy1(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocidade = 0.20 + Math.random() * 0.15;
        this.state = Entidade.ACTIVE; // Refere-se à constante lá do Main
        this.radius = 9.0;
        this.angle = (3 * Math.PI) / 2;
        this.rv = 0.0;
    }

    public long getNextShot() { return nextShot; }
    public void setNextShot(long nextShot) { this.nextShot = nextShot; }

    @Override // 2. O @Override protege contra erros de digitação no nome!
    public void atualizar(long delta, long currentTime, Player player, ArrayList<Projetil> TirosInimigo){
        
        // 3. Removido o FOR fantasma e corrigido 'states' para 'state'
        if(this.state == Entidade.EXPLODING){
            if(currentTime > this.explosion_end){
                this.state = Entidade.INACTIVE;
            }
        }

        if(this.state == Entidade.ACTIVE){

            /* verificando se inimigo saiu da tela */
            if(this.y > GameLib.HEIGHT + 10) {
                this.state = Entidade.INACTIVE;
            } else {
                // 4. Trocado 'vy' pela variável correta 'velocidade'
                this.x += this.velocidade * Math.cos(this.angle) * delta;
                this.y += this.velocidade * Math.sin(this.angle) * delta * (-1.0);
                this.angle += this.rv * delta;

                if(currentTime > this.nextShot && this.y < player.getY()){
                    Projetil_e projetil = new Projetil_e(this.x, this.y, Math.cos(this.angle) * 0.45, Math.sin(this.angle) * 0.45 * (-1.0));
                    
                    // 5. Corrigido para o nome do parâmetro recebido
                    TirosInimigo.add(projetil); 
                    
                    this.nextShot = (long) (currentTime + 200 + Math.random() * 500);
                }
            }
        }
    }

    @Override
    public void desenhaEnemy(long currentTime){ //vai desenhar o inimigo
        // Corrigido de 'states' para 'state'
        if(this.state == Entidade.EXPLODING){
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.x, this.y, alpha);
        }

        if(this.state == Entidade.ACTIVE){
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(this.x, this.y, this.radius);
        }
    }
}
