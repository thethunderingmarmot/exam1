package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();

    private final Logic logic;

    private int getIndex(int size, Pair<Integer, Integer> position) {
        return (position.getY() * size) + position.getX();
    }
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);

        logic = new LogicImpl(new Pair<Integer,Integer>(size, size));
        logic.begin();
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	this.cells.get(getIndex(size, logic.getPosition())).setText(String.valueOf(logic.getNumber()));
            boolean isGoing = logic.nextStep();
            if(!isGoing) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.add(jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
