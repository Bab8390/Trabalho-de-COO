//vai ajudar na criação de personagens e inimigos, para que eles tenham vida e possam sofrer dano ou serem curados

public class Vida {

    private int pontos;
    private final int pontosMax;

    public Vida(int pontosIniciais) {
        this.pontosMax = pontosIniciais;
        this.pontos = pontosIniciais;
    }

    public int sofrerDano(int dano) {
        this.pontos -= dano;
        if (this.pontos < 0) this.pontos = 0;
        return this.pontos;
    }

    public void curar(int quantidade) {
        this.pontos += quantidade;
        if (this.pontos > this.pontosMax) this.pontos = this.pontosMax;
    }

    public boolean estaMorto() {
        return this.pontos <= 0;
    }

    public int getPontos() { return this.pontos; }
    public int getPontosMax() { return this.pontosMax; }

    public double getPercentual() { //vai ajudar a desenhar a barra de vida
        return this.pontosMax == 0 ? 0.0 : (double) this.pontos / this.pontosMax;
    }
}