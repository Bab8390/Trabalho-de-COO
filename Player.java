public class Player implements Entidade {
    private int state;
    private double x;
    private double y;
    private double radius;
    private double explosion_start;
    private double explosion_end;
    private long nextShot;
    private double velocidade;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.state = ACTIVE; 
        this.radius = 12.0;
        this.velocidade = 0.25;
    }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    public double getExplosion_start() { return explosion_start; }
    public void setExplosion_start(double explosion_start) { this.explosion_start = explosion_start; }

    public double getExplosion_end() { return explosion_end; }
    public void setExplosion_end(double explosion_end) { this.explosion_end = explosion_end; }

    public long getNextShot() { return nextShot; }
    public void setNextShot(long nextShot) { this.nextShot = nextShot; }

    public double getVelocidade() { return velocidade; }
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

    public void atualizar(boolean keyPressedUP, boolean keyPressedDOWN, boolean keyPressedLEFT, boolean keyPressedRIGHT, long delta) {
        if(this.state != ACTIVE) return; 
        
        if(keyPressedDOWN) this.y += delta * this.velocidade;
        if(keyPressedUP) this.y -= delta * this.velocidade;
        if(keyPressedLEFT) this.x -= delta * this.velocidade;
        if(keyPressedRIGHT) this.x += delta * this.velocidade;
        
        if(this.x < 0.0) this.x = 0.0;
        if(this.x >= GameLib.WIDTH) this.x = GameLib.WIDTH - 1;
        if(this.y < 25.0) this.y = 25.0;
        if(this.y >= GameLib.HEIGHT) this.y = GameLib.HEIGHT - 1;
    }

    public void desenhaPlayer(long currentTime) {
        if(this.state == EXPLODING) { 
            double alpha = ((currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start));
            GameLib.drawExplosion(this.x, this.y, alpha);
        }
        if(this.state == ACTIVE) {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.x, this.y, this.radius);
        }
    }
}
