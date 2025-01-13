package org.example;

import com.jme3.system.AppSettings;

public class Main {
    public static void main(String[] args) {

        Application app = new Application();
        AppSettings settings = new AppSettings(true);
        settings.setMinResolution(480, 320);
        settings.setResizable(true);
        settings.setFullscreen(false);
        settings.setTitle("Furniture Future");
        app.setSettings(settings);

        app.start();
    }
}