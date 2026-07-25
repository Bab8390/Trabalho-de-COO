import java.awt.Color;
public class PowerUp2 extends PowerUps { // Vai ativar o escudo do jogador e curar 1 de vida

    private static final long DURACAO_ESCUDO_MS = 5000;

    public PowerUp2(double x, double y) {
        super(x, y);
    }

    @Override
    public void desenharPowerUp() {
        if (this.state == Entidade.ACTIVE) {
            GameLib.setColor(Color.PINK);
            GameLib.drawCircle(this.x, this.y, this.radius);
            GameLib.drawCircle(this.x, this.y, this.radius * 1.5);
        }
    }

    @Override
    public void aplicarEfeito(Player player) {
        player.ativarEscudo(DURACAO_ESCUDO_MS);
    }
}