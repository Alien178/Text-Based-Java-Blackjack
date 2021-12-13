import java.util.Scanner;

public class Blackjack {
  public static void main(String[] args) {

    // Welcome Message
    // System.out.print(ConsoleColors.CLEAR + ConsoleColors.BCYAN);
    System.out.print(ConsoleColors.CLUB + "-");
    System.out.print(ConsoleColors.DIAMOND + "-");
    System.out.print(ConsoleColors.SPADE + "-");
    System.out.print(ConsoleColors.HEART + "-");
    System.out.print("Welcome to Blackjack");
    System.out.print(ConsoleColors.HEART + "-");
    System.out.print(ConsoleColors.SPADE + "-");
    System.out.print(ConsoleColors.DIAMOND + "-");
    System.out.print(ConsoleColors.CLUB + "\n");

    // Create our playing deck
    Deck playingDeck = new Deck();
    playingDeck.createFullDeck();
    playingDeck.shuffle();

    // Create a deck for the player
    Deck playerDeck = new Deck();

    Deck dealerDeck = new Deck();

    double playerMoney = 100.0;

    Scanner userInput = new Scanner(System.in);

    // Game Loop
    while (playerMoney > 0) {
      // Take the players bet
      System.out.println(ConsoleColors.BYELLOW + "You have $" + playerMoney + ", how much would you like to bet?"
          + ConsoleColors.YELLOW);
      double playerBet = userInput.nextDouble();

      if (playerBet > playerMoney) {
        System.out.println(ConsoleColors.BRED + "You cannot bet more than you have. Please leave.");
        break;
      }

      boolean endRound = false;

      // Start Dealing
      // Player gets two cards
      playerDeck.draw(playingDeck);
      playerDeck.draw(playingDeck);

      // Dealer gets two cards
      dealerDeck.draw(playingDeck);
      dealerDeck.draw(playingDeck);

      while (true) {
        System.out.print(ConsoleColors.BBLUE + "\nYour hand:" + ConsoleColors.BLUE);
        System.out.println(playerDeck.toString());
        System.out.println(ConsoleColors.BYELLOW + "\nYour deck is valued at: " + playerDeck.cardsValue());

        // Display Dealer Hand
        System.out
            .println(ConsoleColors.BPURPLE + "\nDealer Hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");

        // What does the player want to do?
        System.out.println(ConsoleColors.BCYAN + "\nWould you like to (1) Hit or (2) Stand?");
        int response = userInput.nextInt();

        // If HIT
        if (response == 1) {
          playerDeck.draw(playingDeck);
          System.out
              .println(ConsoleColors.BBLUE + "You draw a:" + ConsoleColors.BLUE
                  + playerDeck.getCard(playerDeck.deckSize() - 1).toString());

          // Bust if > 21
          if (playerDeck.cardsValue() > 21) {
            System.out.println(ConsoleColors.BRED + "Bust. Currently valued at: " + playerDeck.cardsValue());
            playerMoney -= playerBet;
            endRound = true;
            break;
          }
        }

        // If STAND
        if (response == 2) {
          break;
        }
      }

      // Reveal Dealer Cards
      System.out.println(ConsoleColors.BPURPLE + "Dealer Cards: " + dealerDeck.toString());

      // Check if dealer has more points than player
      if ((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false) {
        System.out.println(ConsoleColors.BRED + "Dealer beats you!");
        playerMoney -= playerBet;
        endRound = true;
      }

      // Dealer Draws at 16, stand at 17
      while ((dealerDeck.cardsValue() < 17) && endRound == false) {
        dealerDeck.draw(playerDeck);
        System.out.println(
            ConsoleColors.BPURPLE + "Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
      }

      // Display Total Value for Dealer
      System.out.println(ConsoleColors.BPURPLE + "Dealer's Hand is valued at: " + dealerDeck.cardsValue());

      // Determine if dealer busted
      if ((dealerDeck.cardsValue() > 21) && endRound == false) {
        System.out.println(ConsoleColors.BGREEN + "Dealer busts! You Win!!");
        playerMoney += playerBet;
        endRound = true;
      }

      // Determine if push
      if ((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false) {
        System.out.println("Push");
        endRound = true;
      }

      if ((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false) {
        System.out.println(ConsoleColors.BGREEN + "You win the hand!");
        playerMoney += playerBet;
        endRound = true;

      } else if (endRound == false) {
        System.out.println(ConsoleColors.BRED + "You lose the hand.");
        playerMoney -= playerBet;
        endRound = true;
      }

      playerDeck.moveAllToDeck(playingDeck);
      dealerDeck.moveAllToDeck(playerDeck);

      System.out.println(ConsoleColors.BCYAN + "End of hand.\n");

    }

    System.out.println(ConsoleColors.BRED + "Game Over! You are out of money. :(");

    userInput.close();

  }
}
