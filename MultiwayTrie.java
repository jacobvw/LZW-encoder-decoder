/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

public class MultiwayTrie {
  private Node head_;
  private int phraseCount_;
  private int size_;
  private int resetSymbol_;

  // global varibles used for the insert method
  private Node curr_;
  private boolean match_;
  private boolean newMatch_;
  private boolean addChild_;
  private int lastMatchedPhrase_;

  // constructor - takes a language creates a trie and primes it with the language
  public MultiwayTrie(int size) {
    size_ = (int)Math.pow(2,size);
    // initialise the trie
    initialise();
  }

  public void insert(byte data) {
    // if this flag was set it means the next phase needs to be inserted
    if(addChild_) {
      curr_.setChild(new Node(data, phraseCount_++));
      addChild_ = false;
    }

    // if the previous search finished the newMatch flag will be set. So reset the search position back to the head
    if(newMatch_) {
      curr_ = head_;
      newMatch_ = false;
    }

    // check all items in the current linked list until a match is found
    // or the end is reached
    match_ = false;
    Node tmp = curr_;
    while(tmp != null && !match_) {
      if(tmp.getData() == data) {
        match_ = true;
        curr_ = tmp;
        lastMatchedPhrase_ = curr_.getPhaseNum();
      }
      tmp = tmp.getNext();
    }

    // if a not does not have a child and a match was found
    if (curr_.getChild() == null && match_) {
      System.out.println(lastMatchedPhrase_);                                   // print the last matching phrase number.
      addChild_ = true;                                                         // set the flag that the next child needs to be added to trie.
      newMatch_ = true;                                                         // set flag to indicate a finished search.
    // if a match was not found
    } else if (!match_) {
      System.out.println(lastMatchedPhrase_);                                   // print the last matching phrase number.
      while(curr_.getNext() != null) curr_ = curr_.getNext();                   // skip to the end of the last matching phases children.
      curr_.setNext(new Node(data, phraseCount_++));                            // add the data.
      newMatch_ = true;                                                         // set flag to indicate a finished search.
      insert(data);                                                             // because the current data has not been encounted for we now to insert it.
    // if a match was found and it has a child
    } else {
      curr_ = curr_.getChild();                                                 // set current to the matched phrase so the next iteration can continue from it.
    }

    // if the trie is now full flush it, output reset symbol and re-initialise it (needs testing)
    if(phraseCount_ >= size_) resetTrie();
  }

  // ensures all data from the trie has been output to the file
  public void flush() {
    // if a search was not finished
    if(!newMatch_) {
      System.out.println(lastMatchedPhrase_);                                   // output the last matched phrase
    }
    // force system.out to flush
    System.out.flush();
  }

  // when the trie is full flush anything remaining and output the reset symbol
  private void resetTrie() {
    flush();
    System.out.println(256);
    initialise();
  }

  private void initialise() {
    head_ = null;
    phraseCount_ = 0;

    // prime the trie including the reset symbol as 0x100 (256 in decimal, 100000000 in binary) 
    head_ = new Node((byte)0, phraseCount_++);
    Node tmp = head_;
    for(int i = 1; i <= 256; i++) {
      tmp.setNext(new Node((byte)i, phraseCount_++));
      tmp = tmp.getNext();
    }

    match_ = false;
    newMatch_ = true;
    addChild_ = false;
  }
}

