/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.rishshadra.fossll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class MicrophoneInterface implements Runnable {

    private AudioFormat format;
    private TargetDataLine microphone;
    private boolean listening = false;
    private Thread t;
    private ByteArrayOutputStream out;

    public MicrophoneInterface() {
        out = new ByteArrayOutputStream();
        format = format = new AudioFormat(8000.0f, 8, 1, true, true);
        try {
            microphone = AudioSystem.getTargetDataLine(format);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MicrophoneInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startListening() {
        t = new Thread(this);
        if (!listening) {
            listening = true;
            t.start();
        }
    }

    public void stopListening() {
        Main.w.jProgressBar1.setValue(0);
        listening = false;
    }

    public boolean isListening() {
        return listening;
    }

    @Override
    public void run() {
        int bytesRead;
        byte[] audio = new byte[microphone.getBufferSize() / 5];

        try {
            microphone.open(format);
            microphone.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MicrophoneInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (listening) {
            bytesRead = microphone.read(audio, 0, audio.length);
            out.write(audio, 0, bytesRead);
            Main.w.jProgressBar1.setValue(Main.s.calculateRMSLevel(audio));
        }
        microphone.stop();
        microphone.close();
    }

}
