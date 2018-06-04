/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Decoder {
  private static List<Node> pointers_ = new ArrayList<Node>();

  public static void main(String[] args) {

    // counts number of phrases
    int phraseCount = 0;
    boolean firstPass = true;

    // reader to read data from input stream
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // create and prime the trie
    Node head_ = new Node((byte)0, phraseCount++);
    Node tail_ = head_;
    Node tmp = head_;
    pointers_.add(head_);
    for(int i = 1; i < 257; i++) {
      tmp.setNext(new Node((byte)i, phraseCount++));
      tmp = tmp.getNext();
    }
    tail_ = tmp;

    try {
      // read the first byte from standard input
      String b = in.readLine();

      // loop until there is no more input
      while(b != null) {
        int phrase = Integer.parseInt(b);

        // if the input symbol was the reset symbol, reset the list
        if(phrase == 256) {
          // flush the standard output buffer
          System.out.flush();
          // re-initialise everything
          phraseCount = 0;
          pointers_.clear();
          head_ = new Node((byte)0, phraseCount++);
          tail_ = head_;
          tmp = head_;
          pointers_.add(head_);
          for(int i = 1; i < 257; i++) {
            tmp.setNext(new Node((byte)i, phraseCount++));
            tmp = tmp.getNext();
          }
          tail_ = tmp;
          // reset firstpass
          firstPass = true;

          // read next phrase
          b = in.readLine();

          // if its null we are done
          if(b == null) return;
          phrase = Integer.parseInt(b);
        }

        // get the b node, which is the phrase number passed in.
        tmp = getNode(head_, phrase);

        // this needs to be performed on all passes but the first.
        if(!firstPass) {
          tail_.setData(getLastPhraseData(head_, tmp));
        }
        firstPass = false;

        // recursivly print the node
        printPhrase(head_, tmp);

        // add new phrase to list, the data contained in this list will be set on next iteration
        tail_.setNext(new Node((byte)0, phraseCount++, phrase));
        tail_ = tail_.getNext();

        // add a pointer to every 1000th element
        if((phraseCount % 1000) == 0) pointers_.add(tail_);

        // read in the next phrase number
        b = in.readLine();
      }
      // flush the standard output buffer
      System.out.flush();

    } catch(Exception ex) {
      System.err.println(ex);
      System.exit(1);
    }
  }

  // returns the nth node
  public static Node getNode(Node head, int num) {
    // SPEEDING UP FINDING A NODE
    // would have been beter to implement a tree structure to allow for faster searching
    int k = 1;
    while(num >= 1000) {
      num -= 1000;
      head = pointers_.get(k++);
      head = head.getNext();
    }
    for(int i = 0; i < num; i++) head = head.getNext();
    return head;
  }

  // recursivly prints the phrases from the deepest point up.
  // if the node does not contain a parent meaning its a initial node n.getParentPhaseNum will return -1.
  // start backing up and printing in reverse order.
  public static void printPhrase(Node head, Node n) {
    if(n.getParentPhaseNum() != -1) printPhrase(head, getNode(head, n.getParentPhaseNum()));
    System.out.write(n.getData());
  }

  // recursivly goes to deepest link for a phrase and returns its data. Is needed to get the data for the newest phrase
  public static byte getLastPhraseData(Node head, Node n) {
    if(n.getParentPhaseNum() == -1) return n.getData();
    else return getLastPhraseData(head, getNode(head, n.getParentPhaseNum())); 
  }
}
