public abstract class PowerUps implements Entidade {

    protected int state;
    protected double x;
    protected double y;
    protected double radius;
    protected double velocidade;

    public PowerUps(double x, double y) {
        this.x = x;
        this.y = y;
        this.state = Entidade.ACTIVE;
        this.radius = 10.0;
        this.velocidade = 0.12;
    }

    @Override
    public double getX() { return x; }
    @Override
    public void setX(double x) { this.x = x; }

    @Override
    public double getY() { return y; }
    @Override
    public void setY(double y) { this.y = y; }

    @Override
    public int getState() { return state; }
    @Override
    public void setState(int state) { this.state = state; }

    @Override
    public double getRadius() { return radius; }

    // Move o power-up para baixo e o desativa se sair da tela
    public void atualizar(long delta) {
        if (this.state == Entidade.ACTIVE) {
            this.y += this.velocidade * delta;
            if (this.y > GameLib.HEIGHT + 20) {
                this.state = Entidade.INACTIVE;
            }
        }
    }

    public abstract void desenharPowerUp();

    // Aplica o efeito do power-up ao jogador
    public abstract void aplicarEfeito(Player player);
}