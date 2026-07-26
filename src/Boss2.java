import java.awt.Color;
import java.util.ArrayList;

/**
 * Chefe 2 - "Sentinela": entra pelo topo e passa a orbitar em torno de um
 * ponto fixo, disparando rajadas radiais de projéteis que vão alternando de 
 * forma semelhante a um catavento em todas as direções. Assim como o Chefe1,
 * fica mais agressivo abaixo de 50% de vida.
 */

public class Boss2 extends Special_enemy {

    private static final double ALTURA_ORBITA = 150.0;
    private static final double RAIO_ORBITA = 90.0;

    private long nextShot;
    private double anguloOrbita;
    private double anguloRotacao;

    public Boss2(double x, double y, int pontosVida) {
        super(x, y, pontosVida);
        this.radius = 34.0;
        this.velocidade = 0.0016; // velocidade angular (rad/ms)
        this.anguloOrbita = 0.0;
        this.anguloRotacao = 0.0;
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
            this.y += 0.05 * delta;
        } else {
            this.anguloOrbita += this.velocidade * delta;
            this.x = GameLib.WIDTH / 2.0 + Math.cos(this.anguloOrbita) * RAIO_ORBITA;
            this.y = ALTURA_ORBITA + Math.sin(this.anguloOrbita) * (RAIO_ORBITA * 0.4);
        }
        manterDentroDaTela();

        double velRotacao = this.vida.getPercentual() < 0.5 ? 0.005 : 0.0025;
        this.anguloRotacao += velRotacao * delta;

        long intervalo = this.vida.getPercentual() < 0.5 ? 180 : 350;
        if (currentTime > this.nextShot) {
            int nTiros = 10;
            double velocidadeTiro = 0.25;
            for (int i = 0; i < nTiros; i++) {
                double a = (2 * Math.PI / nTiros) * i * this.anguloRotacao;
                tirosInimigo.add(new Projetil_e(this.x, this.y, Math.cos(a) * velocidadeTiro, Math.sin(a) * velocidadeTiro));
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
            GameLib.setColor(Color.WHITE);
            GameLib.drawCircle(this.x, this.y, this.radius);
            GameLib.drawCircle(this.x, this.y, this.radius * 0.4);
            double px = this.x + Math.cos(this.anguloRotacao) * this.radius;
            double py = this.y + Math.sin(this.anguloRotacao) * this.radius;
            
            GameLib.drawCircle(px, py, 6.0);

            GameLib.drawLine(this.x, this.y, px, py);
        }
        GameLib.drawCircle(this.x, this.y, this.radius * 1.3);
        desenharBarraDeVida();
    }
}