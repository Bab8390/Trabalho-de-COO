import java.awt.Color;

public class Background1 extends Background {

    public Background1(int estrelas, double velocidade) {
        super(estrelas, velocidade); //no caso aqui seria passado o numero de estrelas (20) e a velocidade ()

    }
    @Override
    public void desenhaBackground(long delta) {
        GameLib.setColor(Color.GRAY);
        this.count += this.velocidade * delta;
        for(int i = 0; i < this.x.length; i++) {
			GameLib.fillRect(this.x[i], (this.y[i] + this.count) % GameLib.HEIGHT, 3, 3);
        }
    }
}    
