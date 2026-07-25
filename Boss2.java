import java.awt.Color;
import java.util.ArrayList;

/**
 * Chefe 2 - "Sentinela": entra pelo topo e passa a orbitar em torno de um
 * ponto fixo, disparando rajadas radiais de projéteis em todas as direções
 * (padrão diferente do Chefe1, que mira no jogador). Assim como o Chefe1,
 * fica mais agressivo abaixo de 50% de vida.
 */
public class Boss2 extends Special_enemy {

    private static final double ALTURA_ORBITA = 150.0;
    private static final double RAIO_ORBITA = 90.0;

    private long nextShot;
    private double anguloOrbita;

    public Boss2(double x, double y, int pontosVida) {
        super(x, y, pontosVida);
        this.radius = 34.0;
        this.velocidade = 0.0016; // velocidade angular (rad/ms)
        this.anguloOrbita = 0.0;
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

        if (this.y < ALTURA_ORBITA) {
            this.y += 0.10 * delta;
        } else {
            this.anguloOrbita += this.velocidade * delta;
            this.x = GameLib.WIDTH / 2.0 + Math.cos(this.anguloOrbita) * RAIO_ORBITA;
            this.y = ALTURA_ORBITA + Math.sin(this.anguloOrbita) * (RAIO_ORBITA * 0.4);
        }
        manterDentroDaTela();

        long intervalo = this.vida.getPercentual() < 0.5 ? 220 : 450;
        if (currentTime > this.nextShot) {
            int nTiros = 8;
            for (int i = 0; i < nTiros; i++) {
                double a = (2 * Math.PI / nTiros) * i;
                tirosInimigo.add(new Projetil_e(this.x, this.y, Math.cos(a) * 0.28, Math.sin(a) * 0.28));
            }
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
            GameLib.setColor(Color.YELLOW);
            GameLib.drawCircle(this.x, this.y, this.radius);
            GameLib.drawCircle(this.x, this.y, this.radius * 0.5);
            GameLib.drawCircle(this.x, this.y, this.radius * 1.4);
            desenharBarraDeVida();
        }
    }
}