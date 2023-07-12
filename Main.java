import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

class Card {
    private int rank;
    private int suit;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return rank * suit;
    }

    public String toString() {
        return "Card S" + suit + "R" + rank;
    }
}

class DeckOfCards {
    private Card[] deck;
    private int size;

    public DeckOfCards(int maxRank, int numSuits) {
        size = maxRank * numSuits;
        deck = new Card[size];
        int index = 0;

        for (int suit = 1; suit <= numSuits; suit++) {
            for (int rank = 1; rank <= maxRank; rank++) {
                deck[index] = new Card(rank, suit);
                index++;
            }
        }
    }

    public void shuffle() {
        Random random = new Random();

        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = deck[j];
            deck[j] = deck[i];
            deck[i] = temp;
        }
    }

    public int getSize() {
        return size;
    }

    public int getMinValue() {
        int minValue = deck[0].getValue();
        for (int i = 1; i < size; i++) {
            int value = deck[i].getValue();
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    public int getMaxValue() {
        int maxValue = deck[0].getValue();
        for (int i = 1; i < size; i++) {
            int value = deck[i].getValue();
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public Card[] dealHand(int numCards) {
        Card[] hand = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            hand[i] = deck[i];
        }
        return hand;
    }

    public int[] histogram(int numCards, int numDeals) {
        int[] histogram = new int[(numCards * getMaxValue()) + 1];
        for (int i = 0; i < numDeals; i++) {
            shuffle();
            Card[] hand = dealHand(numCards);
            int handValue = calculateHandValue(hand);
            histogram[handValue]++;
        }
        return histogram;
    }

    private int calculateHandValue(Card[] hand) {
        int totalValue = 0;
        for (Card card : hand) {
            totalValue += card.getValue();
        }
        return totalValue;
    }

    public String toString() {
        return "Deck of " + size + " cards: low = " + getMinValue() + " high = " + getMaxValue() + " top = " + deck[0];
    }
}

public class Main {
    public static void main(String[] args) {
        String numSuitsInput = JOptionPane.showInputDialog("How many suits?");
        int numSuits = Integer.parseInt(numSuitsInput);

        String maxRankInput = JOptionPane.showInputDialog("How many ranks?");
        int maxRank = Integer.parseInt(maxRankInput);

        DeckOfCards deck = new DeckOfCards(maxRank, numSuits);
        JOptionPane.showMessageDialog(null, deck.toString());

        int choice;
        do {
            String choiceInput = JOptionPane.showInputDialog(
                    "1=shuffle, 2=deal 1 hand, 3=deal 100000 times, 4=quit");
            choice = Integer.parseInt(choiceInput);

            switch (choice) {
                case 1:
                    deck.shuffle();
                    JOptionPane.showMessageDialog(null, deck.toString());
                    break;
                case 2:
                    String numCardsInput = JOptionPane.showInputDialog("How many cards?");
                    int numCards = Integer.parseInt(numCardsInput);
                    Card[] hand = deck.dealHand(numCards);
                    JOptionPane.showMessageDialog(null, Arrays.toString(hand));
                    break;
                case 3:
                    String numCardsInput2 = JOptionPane.showInputDialog("How many cards?");
                    int numCards2 = Integer.parseInt(numCardsInput2);
                    int[] histogram = deck.histogram(numCards2, 100000);
                    printHistogram(histogram);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Exiting...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
    }

    private static void printHistogram(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] != 0) {
                System.out.println(i + ": " + histogram[i]);
            }
        }
    }
}
