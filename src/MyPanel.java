import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class MyPanel extends JPanel {

    private JPanel photoPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private BufferedImage backgroundImage;




    private ArrayList<BufferedImage> fileArray;
    private final JLabel titleLabel = new JLabel("Photo album");
    private JLabel reminder = new JLabel();



    private JButton next_button = new JButton(">");
    private JButton previous_button = new JButton("<");
    private JButton random_button = new JButton("Random");


    private int photoIndex = 0;


    public MyPanel(){



        String currentFile = System.getProperty("user.dir");
        Path path = Paths.get(currentFile, "Photos");
        String folderPath = path.toString();
        File MyFolder = new File(folderPath);


        fileArray = new ArrayList<>();
        if (MyFolder.exists() && MyFolder.isDirectory()){
            File[] files = MyFolder.listFiles();
            if (files != null){
                for (File file : files){
                    try{
                        BufferedImage image = ImageIO.read(file);
                        if ( image != null){
                            fileArray.add(image);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        int pictureNumber = fileArray.size();
        String current = System.getProperty("user.dir");
        Path path2 = Paths.get(current, "BackgroundImg.jpg");
        String backgroundPath = path2.toString();


        try{
            backgroundImage = ImageIO.read(new File(backgroundPath));
        } catch (IOException e) {
            e.printStackTrace();
        }



        previous_button.setPreferredSize(new Dimension(90,30));
        next_button.setPreferredSize(new Dimension(90,30));
        previous_button.setBackground(Color.decode("#FFBF00"));
        next_button.setBackground(Color.decode("#FFBF00"));
        random_button.setPreferredSize(new Dimension(100,30));
        random_button.setBackground(Color.decode("#FFBF00"));




        previous_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (photoIndex > 0) {
                    photoIndex--;
                    System.out.println("Image: " + (photoIndex+1));
                    reminder.setText(String.valueOf(photoIndex+1) + "/" + pictureNumber);
                    updatePhotoPanel();
                }else{
                    photoIndex = 0;
                }
            }
        });
        next_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (photoIndex < pictureNumber-1) {
                    photoIndex++;
                    System.out.println("Image: " + (photoIndex+1));
                    reminder.setText(String.valueOf(photoIndex+1) + "/" + pictureNumber);
                    updatePhotoPanel();
                }else{
                    photoIndex = fileArray.size();
                }

            }
        });
        random_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int indexRandom = random.nextInt(pictureNumber);
                photoIndex = indexRandom;
                System.out.println("Random image: " + (photoIndex+1));
                reminder.setText(String.valueOf(photoIndex+1) + "/" + pictureNumber);
                updatePhotoPanel();
            }
        });







        buttonPanel.setBackground(Color.red);
        buttonPanel.add(previous_button);
        buttonPanel.add(next_button);
        buttonPanel.add(random_button);


        Font myFont = new Font("Arial",Font.BOLD ,36);
        titleLabel.setFont(myFont);
        photoPanel.setPreferredSize(new Dimension(400,500));
        /*photoPanel.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.DARK_GRAY);

                int borderThickness = 5;
                for ( int i = 0; i < borderThickness; i++){
                    g.drawRect(x+i,y+i,width-2 * i - 1, height - 2 * i - 1);
                }
            }

            @Override
            public Insets getBorderInsets(Component c) {
                int borderThickness = 5;
                return new Insets(borderThickness,borderThickness,borderThickness,borderThickness);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });*/


        updatePhotoPanelStart();

        this.add(titleLabel);
        this.add(photoPanel);
        this.add(buttonPanel);
        this.add(reminder);


        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(600,700));

    }

    private void updatePhotoPanel() {
        photoPanel.removeAll();
        BufferedImage image = fileArray.get(photoIndex);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setLayout(null);
        imageLabel.setBounds(0,0,photoPanel.getWidth(), photoPanel.getHeight());
        photoPanel.add(imageLabel);
        reminder.setText(String.valueOf(photoIndex + 1) + "/" + fileArray.size());
        revalidate();
        repaint();
    }
    private void updatePhotoPanelStart() {
        photoPanel.removeAll();
        if (fileArray != null) {
            BufferedImage image = fileArray.get(0);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            photoPanel.add(imageLabel);
            reminder.setText(String.valueOf(photoIndex + 1) + "/" + fileArray.size());

        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0,0,getWidth(),getHeight(),this);
        }



    }


}
