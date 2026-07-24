public abstract class Background{
    private double x;
    private double y;
    private double velocidade;
    private double count;

    public Background(double x, double y, double velocidade) {
        this.x = x;
        this.y = y;
        this.velocidade = 0.045;
        this.count = 0.0;
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
    public double getVelocidade() { return velocidade; }
    @Override
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }
    public abstract void desenhaBackground(long delta);
}