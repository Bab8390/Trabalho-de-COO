public class Background2 extends Background {
    private double x;
    private double y;
    private double velocidade;
    private double count;

    public Background2(double x, double y, double velocidade) {
        super(x, y, velocidade);
        this.x = x;
        this.y = y;
        this.velocidade = 0.045;
        this.count = 0.0;
    }

    @Override
    public void desenhaBackground(long delta) {
        GameLib.setColor(Color.DARK_GRAY);
        this.count += this.velocidade * delta;
        for(int i = 0; i < this.x.length; i++) {
            GameLib.fillRect(this.x[i], (this.y[i] + this.count) % GameLib.HEIGHT, 2, 2);
        }
    }
}