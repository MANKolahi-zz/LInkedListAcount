package LinkedList;

public class Node<Data> {
    private Data data;
    private Node<Data> next;
    private Node<Data> previous;

    public Node(Data data) {
        this.data = data;
        next = null;
        previous = null;
    }

    Node(){
        next = null;
        previous = null;
    };

    Node<Data> getNext() {
        return next;
    }

    void setNext(Node<Data> next) {
        this.next = next;
    }

    Node<Data> getPrevious() {
        return previous;
    }

    void setPrevious(Node<Data> previous) {
        this.previous = previous;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
