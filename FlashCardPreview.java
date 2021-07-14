import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardPreview {
    private  JFrame frame;
    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator cardIterator;
    private FlashCard currentCard;
    private JButton showAnswer;
    private boolean isShowAnswer;
    public FlashCardPreview(){

        // Add frame
        frame = new JFrame("Flash Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add JPanel
        JPanel panel = new JPanel();

        //Font
        Font myFont = new Font("TimesRoman",Font.BOLD,22);

        //Add the question text Area
        display = new JTextArea(10,20);
        display.setEditable(false);
        display.setFont(myFont);

        // Add the answer text Area
        answer = new JTextArea(6,8);
        answer.setEditable(false);
        answer.setFont(myFont);

        //Add scrollbar for question text field
        JScrollPane qScrollPane = new JScrollPane(display);
        qScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Add scrollbar for answer text field
        JScrollPane aScrollPane = new JScrollPane(answer);
        aScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        aScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Add Jbutton next

        showAnswer = new JButton("Show Answer");


       // Add Labels
        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        // Create menu

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openCardSet = new JMenuItem("Open Card Set");


        menuBar.add(fileMenu);
        fileMenu.add(openCardSet);



        //ActionListner for menu
       openCardSet.addActionListener(new openMenuItemActionListenr());


       //ActionListner for nextButton
        showAnswer.addActionListener(new showAnswerActionListner());


        //Add to JPanel
        panel.add(qLabel);
        panel.add(qScrollPane);
      // panel.add(aLabel);
       // panel.add(aScrollPane);
        panel.add(showAnswer);

        //Add to Frame
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(450,600);
        frame.setJMenuBar(menuBar);



        // Display the frame
        frame.setVisible(true);







    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               new FlashCardPreview();
            }
        });

    }

    private class openMenuItemActionListenr implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(frame);
        loadFile(fileOpen.getSelectedFile());

        }
    }

    private void loadFile(File selectedFile) {

        cardList = new ArrayList<FlashCard>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;
            while ((line=reader.readLine())!= null){
                makecard(line);
            }

        }catch(Exception e){
            System.out.println("Card not found or card is stored in inappropriate format");
        }

        cardIterator = cardList.iterator();
        showNextCard();
    }

    private void makecard(String lineToParse) {
     String[] result = lineToParse.split("/");
     FlashCard card = new FlashCard(result[0],result[1]);
     cardList.add(card);
        System.out.println("card made");


    }

    private void showNextCard(){

        currentCard = (FlashCard) cardIterator.next();
        display.setText(currentCard.getQuestion());
        showAnswer.setText("Show Answer");
        isShowAnswer = true;
    }

    private class showAnswerActionListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
       if(isShowAnswer){
           display.setText(currentCard.getAnswer());
           showAnswer.setText("Next Card");
           isShowAnswer =false;
       }else{
           if(cardIterator.hasNext()){
               showNextCard();

           }
           else {
               display.setText("That was last card");
               showAnswer.setEnabled(false);

           }       }



        }
    }
}
