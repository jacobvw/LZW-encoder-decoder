/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

import java.util.Arrays;
import java.io.DataInputStream;
import java.io.EOFException;

public class Encoder {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // check for correct number of arguments supplied
    if(args.length != 1) {
      System.err.println("Usage: java encoder <size>");
      System.exit(1);
    }

    int size = 10;
    try {
      size = Integer.parseInt(args[0]);
    } catch(Exception e) {
      System.err.println("Usage: java encoder <size>");
      System.err.println("Invalid size supplied");
      System.exit(1);
    }
    // default numbers of bits to encode phrase numbers
    if(size > 24) size = 24;  // max allowed size
    if(size < 9) size = 9;    // min allowed size

    // create the trie
    MultiwayTrie trie = new MultiwayTrie(size);

    // read in a byte at a time inserting each into the trie
    try {
      DataInputStream in = new DataInputStream(System.in);
      byte b;
    
      // keep looping till the input stream is empty. The EOFException will be triggered when it is.
      while(true) {
        b = in.readByte();
        trie.insert(b);
      }
    } 
    catch (EOFException e) {
      // this exception occurs once the end of the input stream has been reached
      // the trie now needs to be flushed
      trie.flush();
    }
    catch(Exception ex) { System.err.println(ex); System.exit(1); }

  }
}

