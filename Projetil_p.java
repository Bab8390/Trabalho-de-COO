public class Projetil_p extends Projetil {

    public Projetil_p(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0.0; 
        this.vy = -1.0;
        this.state = ACTIVE; 
    }

    @Override
    public void atualizar(long delta) {
        if(this.state == ACTIVE) { 
            
            if(this.y < 0) { 
                this.state = INACTIVE; 
            } else {
                this.x += this.vx * delta;
                this.y += this.vy * delta;
            }
        }
    }

    @Override
    public void desenhar() {
        if(this.state == ACTIVE) {
            GameLib.setColor(Color.GREEN);
            
            GameLib.drawLine(this.x, this.y - 5, this.x, this.y + 5);
            GameLib.drawLine(this.x - 1, this.y - 3, this.x - 1, this.y + 3);
            GameLib.drawLine(this.x + 1, this.y - 3, this.x + 1, this.y + 3);
        }
    }
}
