import java.awt.Color;
public class PowerUp1 extends PowerUps { // Vai aumentar o número de projéteis disparados pelo jogador
    public PowerUp1(double x, double y) {
        super(x, y);
    }

    @Override
    public void desenharPowerUp() {
        if (this.state == Entidade.ACTIVE) {
            GameLib.setColor(Color.GREEN);
            GameLib.drawDiamond(this.x, this.y, this.radius);
            GameLib.drawCircle(this.x, this.y, this.radius * 0.45);
        }
    }

    @Override
    public void aplicarEfeito(Player player) {
        player.melhorarTiro();
    }
}