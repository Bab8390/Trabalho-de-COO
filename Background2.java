public class Background2 extends Background {

    public Background2(int estrelas, double velocidade) {
        super(estrelas, velocidade);
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
