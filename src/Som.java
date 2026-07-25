import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// tem que converter o arquivo de musica para MP3 -> WAV
public class Som {

    private Clip clip;

    public Som(String caminhoArquivo) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(caminhoArquivo));
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Não foi possível carregar o áudio '" + caminhoArquivo + "': " + e.getMessage());
            this.clip = null;
        }
    }

    /** Toca em loop contínuo, do início — uso típico para música de fundo. */
    public void tocarEmLoop() {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Toca uma única vez, reiniciando do começo — uso típico para efeitos sonoros (tiro, explosão, etc). */
    public void tocarUmaVez() {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void parar() {
        if (clip != null) clip.stop();
    }

    /** @param volume entre 0.0 (mudo) e 1.0 (volume máximo do arquivo original) */
    public void setVolume(float volume) {
        if (clip == null || !clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) return;

        FloatControl controle = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log10(Math.max(volume, 0.0001)) * 10.0);
        dB = Math.max(controle.getMinimum(), Math.min(controle.getMaximum(), dB));
        controle.setValue(dB);
    }
}
