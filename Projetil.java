// classe mãe da Projetil_e e Projetil_p
public abstract class Projetil{
  
	//atributos
	protected int state; 
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;

  //getters e setters
	public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getVx() { return vx; }
    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return vy; }
    public void setVy(double vy) { this.vy = vy; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

  //métodos
	public abstract void atualizar (long delta);
	public abstract void desenhar();
} 
