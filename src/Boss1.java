import java.awt.Color;
import java.util.ArrayList;

/**
 * Chefe 1 - "Guardião": entra pelo topo, se estabiliza numa altura fixa
 * e passa a se mover lateralmente, disparando rajadas de 3 tiros mirados
 * no jogador. Fica mais agressivo (atira mais rápido) abaixo de 50% de vida.
 */

public class Boss1 extends Special_enemy {

    private static final double ALTURA_COMBATE = 90.0;

    private long nextShot;
    private double direcao;

    public Boss1(double x, double y, int pontosVida) {
        super(x, y, pontosVida);
        this.radius = 30.0;
        this.velocidade = 0.09;
        this.direcao = 1.0;
        this.nextShot = 0;
    }

    @Override
    public void atualizar(long delta, long currentTime, Player player, ArrayList<Projetil> tirosInimigo) {

        if (this.state == Entidade.EXPLODING) {
            if (currentTime > this.explosion_end) {
                this.state = Entidade.INACTIVE;
            }
            return;
        }

        if (this.state != Entidade.ACTIVE) return;

        if (this.y < ALTURA_COMBATE) {
            this.y += this.velocidade * delta;
        } else {
            this.x += this.velocidade * delta * this.direcao * 3.0;
            if (this.x <= this.radius || this.x >= GameLib.WIDTH - this.radius) {
                this.direcao *= -1.0;
            }
        }

        manterDentroDaTela();

        if (currentTime > this.nextShot) {
            double dx = player.getX() - this.x;
            double dy = player.getY() - this.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < 1.0) dist = 1.0;
            double vx = (dx / dist) * 0.35;
            double vy = (dy / dist) * 0.35;

            tirosInimigo.add(new Projetil_e(this.x, this.y + this.radius, vx, vy));
            tirosInimigo.add(new Projetil_e(this.x, this.y + this.radius, vx * 0.85 - vy * 0.20, vy * 0.85 + vx * 0.20));
            tirosInimigo.add(new Projetil_e(this.x, this.y + this.radius, vx * 0.85 + vy * 0.20, vy * 0.85 - vx * 0.20));

            long intervalo = this.vida.getPercentual() < 0.5 ? 350 : 700;
            this.nextShot = currentTime + intervalo;
        }
    }

    @Override
    public void desenhaEnemy(long currentTime) {
        if (this.state == Entidade.EXPLODING) {
            double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.x, this.y, alpha);
            return;
        }

        if (this.state == Entidade.ACTIVE) {
            GameLib.setColor(Color.ORANGE);
            GameLib.drawCircle(this.x, this.y, this.radius);
            GameLib.drawCircle(this.x, this.y, this.radius * 0.6);
            GameLib.drawDiamond(this.x, this.y, this.radius * 1.3);
            desenharBarraDeVida();
        }
    }
}