package ex4_java_client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphics extends JFrame implements ActionListener {
    DirectedWeightedGraph graph; //The given graph from json file.
    DirectedWeightedGraphAlgorithms AlgoGraph; //Given algorithm graph.
    double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth(); //Dimension of screen.
    double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public Graphics(DirectedWeightedGraphAlgorithms AlgoGraph) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Initialize X to close operation.
        this.setSize((int) WIDTH, (int) HEIGHT); //Set screen size.

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
