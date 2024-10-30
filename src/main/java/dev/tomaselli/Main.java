package dev.tomaselli;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        JFrame window = new JFrame();
        window.setResizable(false);
        window.setTitle("Evolution Simulator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulatorPanel simulatorPanel = new SimulatorPanel();
        KeyHandler.simulatorPanel = simulatorPanel;
        window.add(simulatorPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}