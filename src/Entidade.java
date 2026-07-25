public interface Entidade {
    //declaracao das constantes
    public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

    //assinatura dos metodos

    public double getX();
    public void setX(double x);
    public double getY();
    public void setY(double y);
    public int getState();
    public void setState(int state);
    public double getRadius();
}
