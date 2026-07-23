public abstract class Enemy implements entidade {
    protected int state;
    protected double x;
    protected double y;
    protected double radius;
    protected double explosion_start;
    protected double explosion_end;
    protected double velocidade;
    protected double velocidade_rotacao;
    protected double angle;

    

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

    public double getVelocidade() { return velocidade; }
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

    public double getVelocidade_rotacao() { return velocidade_rotacao; }
    public void setVelocidade_rotacao(double velocidade_rotacao) { this.velocidade_rotacao = velocidade_rotacao; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }

    

    public abstract void atualizar(long delta, long currentTime, Player player, ArrayList<Projectile> TirosInimigo);
    public abstract void desenhaEnemy(long currentTime);
