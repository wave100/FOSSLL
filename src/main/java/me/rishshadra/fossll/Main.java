/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.rishshadra.fossll;

import java.util.Scanner;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class Main {

    public static MicrophoneInterface m;
    public static SoundProcessor s;
    public static MainWindow w;

    public static void main(String[] args) {
        m = new MicrophoneInterface();
        s = new SoundProcessor();
        w = new MainWindow();
        
        w.setVisible(true);
    }
}
