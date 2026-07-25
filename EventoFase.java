public class EventoFase {

    public enum TipoEvento { INIMIGO, CHEFE, POWERUP }

    private final TipoEvento tipoEvento;
    private final int tipo;
    private final long quando;
    private final double x;
    private final double y;
    private final int pontosVida;

    //recebe os valores do txt e preenche a classe
    public EventoFase(TipoEvento tipoEvento, int tipo, long quando, double x, double y, int pontosVida) {
        this.tipoEvento = tipoEvento;
        this.tipo = tipo;
        this.quando = quando;
        this.x = x;
        this.y = y;
        this.pontosVida = pontosVida;
    }

    public TipoEvento tipoEvento() {
        return tipoEvento;
    }
    public int tipo() {
        return tipo;
    }
    public long quando() {
        return quando;
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public int pontosVida() {
        return pontosVida;
    }
}
