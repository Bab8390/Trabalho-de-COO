// classe mãe da Projetil_e e Projetil_p
public abstract class Projetil implements Entidade{
  
	//atributos
	protected int state;
	protected double x;
	protected double y;
	protected double vx;
	protected double vy;
    protected double radius;

    //getters e setters
    @Override
	public double getX() { return x; }
    @Override
    public void setX(double x) { this.x = x; }
    
    @Override   
    public double getY() { return y; }
    @Override
    public void setY(double y) { this.y = y; }

    
    public double getVx() { return vx; }
    public void setVx(double vx) { this.vx = vx; }

    public double getVy() { return vy; }
    public void setVy(double vy) { this.vy = vy; }

    @Override
    public int getState() { return state; }
    @Override
    public void setState(int state) { this.state = state; }

    @Override
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    //métodos
	public abstract void atualizar (long delta);
	public abstract void desenhar();
}
