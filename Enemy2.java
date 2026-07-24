public class Enemy2 extends Enemy {

    public Enemy2(double x, double y) {
        this.x = x;
        this.y = y;
        this.state = ACTIVE;
        this.radius = 12.0;
        this.velocidae = 0.42;
        this.angle = (3 * Math.PI) / 2;
        this.rv = 0.0;
    }

    public void desenhaEnemy(long currentTime) {

        for (int i = 0; i < this.state.length; i++) { // atenção nisso AQUI DEPOIS

            if (this.state == EXPLODING) {

                double alpha = (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
                GameLib.drawExplosion(this.x, this.y, alpha);
            }

            if (this == ACTIVE) {

                GameLib.setColor(Color.MAGENTA);
                GameLib.drawDiamond(this.x, this.y, this.tamanho);
            }
        }
    }

    public void atualizar(long delta, long currentTime, Player player, ArrayList<Projectile> TirosInimigo) {

        for (int i = 0; i < enemy2_states.length; i++) { // atenção nisso AQUI DEPOIS

            if (this.state == EXPLODING) {

                if (currentTime > this.explosion_end) {

                    this.state = INACTIVE;
                }
            }

            if (this.state == ACTIVE) {

                /* verificando se inimigo saiu da tela */
                if (this.x < -10 || this.x > GameLib.WIDTH + 10) {

                    enemy2_states[i] = INACTIVE;
                } else {

                    boolean shootNow = false;
                    double previousY = this.y;

                    this.x += this.velocidade * Math.cos(this.angle) * delta;
                    this.y += this.velocidade * Math.sin(this.angle) * delta * (-1.0);
                    this.angle += this.rv * delta;

                    double threshold = GameLib.HEIGHT * 0.30;

                    if (previousY < threshold && this.y >= threshold) {

                        if (this.x < GameLib.WIDTH / 2)
                            this.rv = 0.003;
                        else
                            this.rv = -0.003;
                    }

                    if (this.rv > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05) {

                        this.rv = 0.0;
                        this.angle = 3 * Math.PI;
                        shootNow = true;
                    }

                    if (this.rv < 0 && Math.abs(this.angle) < 0.05) {

                        this.rv = 0.0;
                        this.angle = 0.0;
                        shootNow = true;
                    }

                    if (shootNow) {

                        double[] angles = { Math.PI / 2 + Math.PI / 8, Math.PI / 2, Math.PI / 2 - Math.PI / 8 };

                        double a = angles + Math.random() * Math.PI / 6 - Math.PI / 12;
                        double vx = Math.cos(a);
                        double vy = Math.sin(a);

                        Projetil_e projetil = new Projetil_e(this.x, this.y, vx * 0, 30, vy * 0, 30);
                    }
                }
            }
        }
    }

}
