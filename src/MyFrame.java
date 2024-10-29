import javax.swing.*;

public class MyFrame extends JFrame {




    public MyFrame(){
        setTitle("SLIDESHOW");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(new MyPanel());
        this.setResizable(false);





        pack();
    }



}
