import java.awt.Color;

public class Projetil_p extends Projetil {
   public Projetil_p(double var1, double var3) {
      this.x = var1;
      this.y = var3;
      this.vx = (double)0.0F;
      this.vy = (double)-1.0F;
      this.state = 1;
   }

   public void atualizar(long var1) {
      if (this.state == 1) {
         if (this.y < (double)0.0F) {
            this.state = 0;
         } else {
            this.x += this.vx * (double)var1;
            this.y += this.vy * (double)var1;
         }
      }

   }

   public void desenhar() {
      if (this.state == 1) {
         GameLib.setColor(Color.GREEN);
         GameLib.drawLine(this.x, this.y - (double)5.0F, this.x, this.y + (double)5.0F);
         GameLib.drawLine(this.x - (double)1.0F, this.y - (double)3.0F, this.x - (double)1.0F, this.y + (double)3.0F);
         GameLib.drawLine(this.x + (double)1.0F, this.y - (double)3.0F, this.x + (double)1.0F, this.y + (double)3.0F);
      }

   }
}
