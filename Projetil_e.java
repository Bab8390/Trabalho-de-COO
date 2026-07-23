public class EnemyProjectile extends Projectile {
    private double radius; 

    public EnemyProjectile(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = ACTIVE; 
        this.radius = 2.0; 
    }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    @Override
    public void movimentar(long delta) {
        if(this.state == ACTIVE) { 
            
            if(this.y > GameLib.HEIGHT) { 
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
            GameLib.setColor(Color.RED);
            GameLib.drawCircle(this.x, this.y, this.radius);
        }
    }
}
