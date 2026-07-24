public abstract class Enemy1 extends Enemy {
    private int states;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radius;
    private double explosion_start;
    private double explosion_end;
    private long nextShot;
    private double angulo;
    private double vr; //velocidade de rotação

    public Enemy1(double x, double y, double vx, double vy, double velocidade, double angulo, double vr) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0.20 + Math.random() * 0.15;
        this.state = ACTIVE;
        this.radius = 9.0;
        this.angle = angulo;
        this.RV = vr;
    }

    public long getNextShot() { return nextShot; }
    public void setNextShot(long nextShot) { this.nextShot = nextShot; }

    public void atualiza(long delta, long currentTime, Player player, ArrayList<Projectile> TirosInimigo){
        for(int i = 0; i < this.states.length; i++){

            if(this.states == EXPLODING){

                if(currentTime > this.explosion_end){
                    this.states = INACTIVE;
                }
            }

            if(this.states == ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(this.y > GameLib.HEIGHT + 10) {
                    this.states = INACTIVE;
                }

                else {
                    this.x += this.vy * Math.cos(this.angle) * delta;
                    this.y += this.vy * Math.sin(this.angle) * delta * (-1.0);
                    this.angle += this.RV * delta;

                    if(currentTime > this.nextShot && this.y < player.getY()){
                        Projetil_e projetil = new Projetil_e(this.x, this.y, Math.cos(this.angle) * 0.45, Math.sin(this.angle) * 0.45 * (-1.0));
                        this.nextShot = (long) (currentTime + 200 + Math.random() * 500);
                    }
                }
            }
        }
    }


    public void desenhaEnemy(long currentTime){ //vai desenhar o inimigo
        for(int i = 0; i < this.states.length; i++){
            if(this.states == EXPLODING){
                double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
                GameLib.drawExplosion(this.x, this.y, alpha);
            }

            if(this.states == ACTIVE){
                GameLib.setColor(Color.CYAN);
                GameLib.drawCircle(this.x, this.y, this.radius);
            }
        }
    }

}
