public interface Danificavel {
    // Aplica dano à entidade.
    // return true se o dano foi de fato aplicado
    boolean sofrerDano(int dano);
    //return true se a entidade não possui mais pontos de vida
    boolean estaMorto();
}
