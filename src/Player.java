import java.awt.Color;

public class Player implements Entidade {
    private int state;
    private double x;
    private double y;
    private double radius;
    private double explosion_start;
    private double explosion_end;
    private long nextShot;
    private double velocidade;

    private final double spawnX;
    private final double spawnY;

    private final Vida vida;

    private int numTiros;
    private static final int MAX_TIROS = 3;

    public boolean escudoAtivo;
    private long escudoFim;

    public Player(double x, double y, int pontosVida) {
        this.x = x;
        this.y = y;
        this.state = Entidade.ACTIVE;
        this.radius = 12.0;
        this.velocidade = 0.25;
        this.spawnX = x;
        this.spawnY = y;
        this.vida = new Vida(pontosVida); 
        this.numTiros = 1;
        this.escudoAtivo = false;  
    }

    @Override
    public int getState() { return state; }
    @Override
    public void setState(int state) { this.state = state; }

    @Override   
    public double getX() { return x; }
    @Override
    public void setX(double x) { this.x = x; }

    @Override
    public double getY() { return y; }
    @Override
    public void setY(double y) { this.y = y; }

    @Override
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    public double getExplosion_start() { return explosion_start; }
    public void setExplosion_start(double explosion_start) { this.explosion_start = explosion_start; }

    public double getExplosion_end() { return explosion_end; }
    public void setExplosion_end(double explosion_end) { this.explosion_end = explosion_end; }

    public long getNextShot() { return nextShot; }
    public void setNextShot(long nextShot) { this.nextShot = nextShot; }

    public double getVelocidade() { return velocidade; }
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

    public Vida getVida() { return vida; } //pegar a vida do player

    public int getNumTiros() { return numTiros; } //pegar o número de tiros do player

    public void melhorarTiro() { //power-up que aumenta o número de tiros do player
        if (this.numTiros < MAX_TIROS) this.numTiros++;
    }

    public void ativarEscudo(long duracao) { //ativa o escudo do player
        this.escudoAtivo = true;
        this.vida.curar(1); // Ganha uma vida ao ativar o escudo
        this.escudoFim = System.currentTimeMillis() + duracao;
    }

    public boolean isEscudoAtivo() { return escudoAtivo; } //verifica se o escudo está ativo

    public boolean isGameOver() { //verifica se o jogo acabou, ou seja, se o player está morto e inativo
        return this.vida.estaMorto() && this.state == Entidade.INACTIVE;
    }

    public void desenharVidasPlayer() {
        int vidasRestantes = this.vida.getPontos(); // obtém o número de vidas restantes do jogador
        double startX = 30.0;
        double y = GameLib.HEIGHT - 30.0;
        double tamanho = 6.0;

        GameLib.setColor(Color.RED);
        for (int i = 0; i < vidasRestantes; i++) {
            double x = startX + (i * 20.0);
        
            // desenha um coração utilizando a GameLib
            GameLib.drawCircle(x - tamanho/2, y - tamanho/2, tamanho/2);
            GameLib.drawCircle(x + tamanho/2, y - tamanho/2, tamanho/2);
            GameLib.drawLine(x - tamanho, y - tamanho/4, x, y + tamanho);
            GameLib.drawLine(x + tamanho, y - tamanho/4, x, y + tamanho);
        }
    }


    public void atualizar(boolean keyPressedUP, boolean keyPressedDOWN, boolean keyPressedLEFT, boolean keyPressedRIGHT, long delta, long currentTime) {
        
        if (this.escudoAtivo && currentTime >= this.escudoFim) {
            this.escudoAtivo = false;
        }

        if(this.state == Entidade.ACTIVE) {
            if(keyPressedDOWN) this.y += delta * this.velocidade;
            if(keyPressedUP) this.y -= delta * this.velocidade;
            if(keyPressedLEFT) this.x -= delta * this.velocidade;
            if(keyPressedRIGHT) this.x += delta * this.velocidade;
            
            if(this.x < 0.0) this.x = 0.0;
            if(this.x >= GameLib.WIDTH) this.x = GameLib.WIDTH - 1;
            if(this.y < 25.0) this.y = 25.0;
            if(this.y >= GameLib.HEIGHT) this.y = GameLib.HEIGHT - 1;
        }
        
        // se a nave estiver EXPLODINDO, o jogo agora consegue ler isso:
        else if(this.state == Entidade.EXPLODING) {
            if(currentTime >= this.explosion_end) {
                if (this.vida.estaMorto()) {
                    // sem mais pontos de vida: fica INACTIVE definitivamente (fim de jogo).
                    this.state = Entidade.INACTIVE;
                } else {
                    // ainda tem vida: renasce na posição inicial.
                    this.x = this.spawnX;
                    this.y = this.spawnY;
                    this.state = Entidade.ACTIVE;
                }
            }
        }
    }

    public void desenhaPlayer(long currentTime) {
        if (this.state == Entidade.ACTIVE) {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.x, this.y, this.radius);
            desenharVidasPlayer();
            if (this.escudoAtivo) { // se o escudo estiver ativo, desenha um círculo em volta da nave
                GameLib.setColor(Color.PINK);
                GameLib.drawCircle(this.x, this.y, this.radius * 1.6);
            }
        } 
        else if (this.state == Entidade.EXPLODING) {
            double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            
            // travas de segurança para não crashar o jogo!
            if (alpha > 1.0) alpha = 1.0; 
            if (alpha < 0.0) alpha = 0.0;
            
            GameLib.drawExplosion(this.x, this.y, alpha);
        }
    }
}
