import java.awt.Color;

public class Projetil_e extends Projetil {

    public Projetil_e(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = Main.ACTIVE;
        this.radius = 2.0; // Ele usa a variável da mãe automaticamente!
    }

    @Override
    public void atualizar(long delta) {
        if(this.state == Main.ACTIVE) {
            if(this.y > GameLib.HEIGHT) {
                this.state = Main.INACTIVE;
            } else {
                this.x += this.vx * delta;
                this.y += this.vy * delta;
            }
        }
    }

    @Override
    public void desenhar() {
        if(this.state == Main.ACTIVE) { 
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(this.x, this.y, this.radius);
        }
    }
}
