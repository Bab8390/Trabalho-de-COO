import java.awt.Color;
public abstract class Special_enemy extends Enemy implements Danificavel {

    protected Vida vida;

    public Special_enemy(double x, double y, int pontosVida) {
        this.x = x;
        this.y = y;
        this.vida = new Vida(pontosVida);
        this.state = Entidade.ACTIVE;
    }

    @Override
    public boolean sofrerDano(int dano) {
        this.vida.sofrerDano(dano);
        return true;
    }

    @Override
    public boolean estaMorto() {
        return this.vida.estaMorto();
    }

    public Vida getVida() { return this.vida; }

    //Vai impedir que o chefe saia da área visível do jogo
    protected void manterDentroDaTela() {
        if (this.x < this.radius) this.x = this.radius;
        if (this.x > GameLib.WIDTH - this.radius) this.x = GameLib.WIDTH - this.radius;
        if (this.y < this.radius) this.y = this.radius;
        if (this.y > GameLib.HEIGHT - this.radius) this.y = GameLib.HEIGHT - this.radius;
    }

// Vai desenhar a barra de vida restante do chefe
    public void desenharBarraDeVida(){
        double largura = 240.0;
        double altura = 10.0;
        double xCentro = GameLib.WIDTH / 2.0;

        double yCentro = 50.0; 

        double larguraAtual = largura * this.vida.getPercentual();
        double xCentroAtual = xCentro - (largura - larguraAtual) / 2.0;

        GameLib.setColor(Color.DARK_GRAY);
        GameLib.fillRect(xCentro, yCentro, largura, altura);

        //pinta a barra de vida atual por cima de Verde
        GameLib.setColor(Color.RED);
        GameLib.fillRect(xCentroAtual, yCentro, larguraAtual, altura);
    }
}
