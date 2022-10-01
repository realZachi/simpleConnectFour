package four;

import javax.swing.*;

class Cell extends JButton {

    public Cell() {
        super();
        setFocusPainted(false);
    }

    public Cell(String text) {
        super(text);
    }
}