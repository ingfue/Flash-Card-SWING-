
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    public FlashCardBuilder()
    {

        // Instantiate Array List;

        cardList = new ArrayList<>();
        //System.out.println(cardList.size());

        // add the JFrame
        frame = new JFrame("Flash Card Maker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel
        JPanel mainPanel = new JPanel();

        //Create Font
        Font myFont = new Font("TimesRoman",Font.BOLD,22);

        //Create Question area
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(myFont);

        //Create Answer area
        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(myFont);


        //Create ScrollPanel for Question
        JScrollPane qScrollPane = new JScrollPane(question);
        qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Create ScrollPanel for Answer
        JScrollPane aScrollPane = new JScrollPane(answer);
        aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        aScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Create JButton
        JButton nextButton = new JButton("Next");

        // Add actionListener to button
        nextButton.addActionListener(new nextButtonActionListner());


        // Create Label
        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        // Create Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");


        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);

        menuBar.add(fileMenu);


        // ActionListner for Menu
        fileMenu.addActionListener(new fileMenuActionListner());
        newMenuItem.addActionListener(new newMenuItemActionListner());
        saveMenuItem.addActionListener(new saveMenuItemActionListner());



        //Add components to mainPanel

        mainPanel.add(qLabel);
        mainPanel.add(qScrollPane);
        mainPanel.add(aLabel);
        mainPanel.add(aScrollPane);
        mainPanel.add(nextButton);

       // Add to Frame
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(450,600);
        frame.setJMenuBar(menuBar);

        // Frame visibility
        frame.setVisible(true);






    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });



    }



    private class nextButtonActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("next button pressed");
           cardList.add(new FlashCard(question.getText(),answer.getText()));

           //System.out.println(cardList.size());

            // Implement the clear screen method;
            clearScreen();

        }
    }



    private class fileMenuActionListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("File clicked");
        }
    }

    private class saveMenuItemActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Save Clicked");

            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

            // Create a save dailogue box with File Chooser
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private class newMenuItemActionListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new FlashCardBuilder();
        }
    }


    private void clearScreen() {
        answer.setText("");
        question.setText("");
    }

    private void saveFile(File selectedFile) {
        try{

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

            for(FlashCard card : cardList){
                bufferedWriter.write(card.getQuestion()+"/");
                bufferedWriter.write(card.getAnswer()+"\n");
            }
            bufferedWriter.close();

        }  catch (Exception e){


        }
    }

}
