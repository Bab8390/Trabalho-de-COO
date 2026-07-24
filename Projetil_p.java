import java.awt.Color;

public class Projetil_p extends Projetil {

    public Projetil_p(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0.0;
        this.vy = -1.0;
        this.state = Main.ACTIVE;
        this.radius = 0.0; 
    }

    @Override
    public void atualizar(long delta) {
        if (this.state == Main.ACTIVE) {
            if (this.y < 0.0) {
                this.state = Main.INACTIVE;
            } else {
                this.x += this.vx * delta;
                this.y += this.vy * delta;
            }
        }
    }

    @Override
    public void desenhar() {
        if (this.state == Main.ACTIVE) {
            GameLib.setColor(Color.GREEN);
            GameLib.drawLine(this.x, this.y - 5.0, this.x, this.y + 5.0);
            GameLib.drawLine(this.x - 1.0, this.y - 3.0, this.x - 1.0, this.y + 3.0);
            GameLib.drawLine(this.x + 1.0, this.y - 3.0, this.x + 1.0, this.y + 3.0);
        }
    }
}
