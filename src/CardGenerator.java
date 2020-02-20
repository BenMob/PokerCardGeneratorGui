/*******************
 * SE 370
 * Poker Card Generator
 ******************/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CardGenerator implements ActionListener{

    // Attributes
    final static int numberOfCards = 52;
    final static int numberOfCardsOnDisplay = 5;

    protected JFrame window = null;
    protected JPanel body = null;
    protected JButton button = null;

    protected ImageIcon[] images = new ImageIcon[numberOfCards]; // image Icons array
    protected String[] imageLocations = new String[numberOfCards]; // image locations array
    protected JLabel[] cards = new JLabel[numberOfCards]; // image Icon Labels ready for display
    protected JLabel[] cardsOnDisplay = new JLabel[numberOfCardsOnDisplay]; // will store 5 random cards from cards[]

    /************************************************************
     * The constructor configures the window and all the widgets.
     * ***********************************************************/
    public CardGenerator(){
      // Configure Frame
      window = new JFrame("Poker Card Generator");
      window.setSize(900, 600);
      window.setBackground(Color.green);
      window.setLocationRelativeTo(null); //centers the frame
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Configure Body
      body = new JPanel(new FlowLayout());
      body.setBackground(Color.black);

      // Configure Button(s) TODO:
      button = new JButton("Shuffle");
      button.setAlignmentX(50);
      button.setOpaque(false);
      button.setFont(Font.getFont("sans-serif"));
      button.setAlignmentY(30);
      button.addActionListener(this); // Triggers the only action listener in the program located right before the main

      // Configure cards
      locateCards();
      loadCards();
      configureCards();
      initializeCardsContainer();
      displayCards();

      /*
      TODO: Figure out how to trigger a function when button click
       */
    }
    /*******************************************************************
     * This method resize each cardIcon and
     * creates a unique label for each of the 52 card
     * and stores them in the cards[] array
     * (This is the last step in making the cards ready for display)
     ******************************************************************/
    public void configureCards(){
        try {
            // This array will store instances of Images
            Image Deck[] = new Image[numberOfCards];

            // This loop performs 4 tasks:
            // 1. Converts each imageIcon object into an Image object
            // 2. Take advantage of getScaledInstance() to resize the Image object
            // 3. Creates a label for the imageIcon
            // 4. Converts the resized Image object back into an imageIcon object

            for (int currentCard = 0; currentCard < numberOfCards; currentCard++) {
                Deck[currentCard] = images[currentCard].getImage();
                Deck[currentCard] = Deck[currentCard].getScaledInstance(100, 135, Image.SCALE_SMOOTH); //resizing images

                // creates a label for the image
                cards[currentCard] = new JLabel(); // creates an instance of a label
                cards[currentCard].setIcon(new ImageIcon(Deck[currentCard])); // adds resized image as icons

                // turns image back into an icon
                images[currentCard] = new ImageIcon(Deck[currentCard]);
            }
        }catch (Exception e){
            System.out.println(e + " Failure in resizing images");
        }
    }

    /*************************************************
     * This method shuffles the cards to be displayed
     ************************************************/
    public void initializeCardsContainer(){
        //Fills the cardsOnDisplay[] array with random cards from cards[]
        Random randomNumber = new Random();
        for (int i = 0; i < numberOfCardsOnDisplay; i++){
            cardsOnDisplay[i] = cards[randomNumber.nextInt(numberOfCards)];
        }
    }

    /************************************************************************
    * locate all cards and append their paths inside the cardLocations array.
    ************************************************************************/
    public void locateCards(){
        try{
            if (imageLocations.length != numberOfCards){
                throw new ArrayStoreException();
            }else {
                // building card paths
                final String extension = ".png";
                final String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "K", "J", "Q"};
                final char[] cardSuits = {'C', 'D', 'H', 'S'};

                // Important: update the first 4 components of this filepath to match your computer before running
                String filePath = "/Users/benjaminmobole/Desktop/SE370-Lab/Card-Generator/src/pokerCards/";
                String card = null;

                // Command line msg
                System.out.println("\nThe following paths were generated, however they may need to be changed depending on the OS.");

                byte index = 0;
                for (String value : cardValues) {
                    card = value;
                    for (char suit : cardSuits) {
                        imageLocations[index] = filePath + card + suit + extension;
                        System.out.println(filePath + card + suit + extension);  // Command line msg

                        // 0 - 51
                        index++;
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e + " Cards were not located properly.");
        }
    }

    /**********************************
     * Loads image icons in cards array
     *********************************/
    public void loadCards(){
        try{
            if ((images.length != numberOfCards) || (imageLocations.length != numberOfCards)){
                throw new ArrayStoreException();
            }else{
                System.out.println("\nNow loading Image icons: ");
                for (int i = 0; i < numberOfCards; i++){

                    images[i] = new ImageIcon(imageLocations[i]);
                    System.out.println("Image " + (i+1) + " loaded");
                }
            }
        }catch (Exception e){
            System.out.println(e + " Cards were not loaded properly.");
        }
    }

    /********************************************
     * This function  displays cards on th screen
     *********************************************/
    public void displayCards(){
        //adds the ready to be displayed cards on the screen
        if(cardsOnDisplay != null) {
            for (int i = 0; i < numberOfCardsOnDisplay; i++) {
                body.add(cardsOnDisplay[i]);
            }
        }else throw new ArrayStoreException(" No cards to display. Fill cardsToDisplay[] first by calling shuffle().\n");
    }

    /******************************************
     * Launches the window with all the gadgets
     *****************************************/
    public void launch(){
        try {
            body.add(button);

            window.add(body);  // adds the body onto the frame
            window.setVisible(true);
        }catch (Exception e){
            System.out.println(e + " Failure launching the window.");
        }
    }

    /**************************************************
     * This is the button action listener implementation
     * I update the cards being displayed on the screen
     * when button is clicked.
     **************************************************/
    public void actionPerformed(ActionEvent a){
        Random randomNumber = new Random();
        for (int i = 0; i < numberOfCardsOnDisplay; i++) {
            cardsOnDisplay[i].setIcon(images[randomNumber.nextInt(numberOfCards)]);
        }
        displayCards();
        window.setVisible(true);
    }

    // Main function
    public static void main(String[] args){
        CardGenerator myCards = new CardGenerator();
        myCards.launch();
    }
}


/***********************************************************
 * This method define how cards will be positioned on display
 ************************************************************
 public void positionCardsOnDisplay(){

 try {
 // This loop sets the position of each card on the screen
 for (int i = 0; i < numberOfCardsOnDisplay; i++) {
 cardPositions[i] = new GridBagConstraints();
 cardPositions[i].fill = GridBagConstraints.VERTICAL;
 cardPositions[i].gridx = i;
 cardPositions[i].gridy = 0;
 cardPositions[i].weightx = 0.1;
 cardPositions[i].ipady = 80;
 }

 // Now positioning the button
 buttonPosition.fill = GridBagConstraints.HORIZONTAL;
 buttonPosition.gridx = 2;
 buttonPosition.gridy = 3;
 buttonPosition.weighty = 0.1;
 buttonPosition.ipady = 50;

 }catch (Exception e){
 System.out.println(e + " Failure positioning image");
 }
 }
 */