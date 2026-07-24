public abstract class Background{
    protected double[] x;
    protected double[] y;
    protected double velocidade;
    protected double count;

    public Background(int estrelas, double velocidade) {
        this.x = new double [estrelas];
        this.y = new double [estrelas];
        this.velocidade = velocidade;
        this.count = 0.0;

        for(int i = 0; i < estrelas; i++) {
            this.x[i] = Math.random() * GameLib.WIDTH;
            this.y[i] = Math.random() * GameLib.HEIGHT;
        }
    }

    public void setX(double[] x) { this.x = x; }
    
    public void setY(double[] y) { this.y = y; }
    
    public double getVelocidade() { return velocidade; }
    
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

    
    public abstract void desenhaBackground(long delta);
}
