/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

public class Node {
  private byte data_;
  private int phaseNum_;
  private int parentPhraseNum_;
  private Node next_;
  private Node child_;

  public Node(byte data, int phaseNum) {
    data_ = data;
    phaseNum_ = phaseNum;
    parentPhraseNum_ = -1;    // if the phrase/node has a parentPhraseNumber of -1 it does not have one.
    child_ = null;
  }
  public Node(byte data, int phaseNum, int parentPhaseNum) {
    data_ = data;
    phaseNum_ = phaseNum;
    parentPhraseNum_ = parentPhaseNum;
    child_ = null;
  }

  // getter / setter methods
  public Node getNext() { return next_; }
  public void setNext(Node tmp) { next_ = tmp; }

  public byte getData() { return data_; }
  public void setData(byte data) { data_ = data; }

  public Node getChild() { return child_; }
  public void setChild(Node child) { child_ = child; }

  public int getPhaseNum() { return phaseNum_; }
  public int getParentPhaseNum() { return parentPhraseNum_; }
}
