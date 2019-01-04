package LinkedList;

import static java.lang.System.out;

public class NewLinkedList<Data>{

    protected Node<Data> start;
    protected Integer length = 0;

    public NewLinkedList(Data data){
        setStart(new Node<>(data));
    }

    public NewLinkedList(){
        length = 0;
    }

    public NewLinkedList(Node<Data> start){
        setStart(start);
    }

    protected void setStart(Node<Data> node){
        start = node;
        start.setNext(start);
        start.setPrevious(start);
        length = 1;
    }

    public Node<Data> getNode(int nodeNumber){
        if(nodeNumber >= getLength() || nodeNumber < 0)
            throw new IllegalArgumentException("Exception:nodNumber Must be < list.length && >= 0");
        Node<Data> node = start;
        for (int i = 1; i <= nodeNumber ;i++){
            node = node.getNext();
        }
        return node;
    }

    public void addToFinal(Node<Data>... node){
        int i = 0;
        if(getLength() == 0) {
            setStart(node[0]);
            i++;
        }
        for(;i < node.length;i++) {
            addAfterNode(start.getPrevious(),node[i]);
        }
    }

    @SafeVarargs
    public final void addToFinal(Data... data){
        int i = 0;
        if(getLength() == 0) {
            setStart(new Node<Data>(data[0]));
            i++;
        }
        for(;i < data.length;i++) {
            addAfterNode(start.getPrevious(),new Node<Data>(data[i]));
        }
    }

    @SafeVarargs
    public final void addToStart(Data... data){
        if(getLength() == 0){
            setStart(new Node<Data>(data[0]));
        }
        else {
            addAfterNode(start.getPrevious(),new Node<Data>(data[0]));
            start = start.getPrevious();
        }
        Node<Data> preNode = start;
        for (int i = 1; i < data.length; i++) {
            addAfterNode(preNode,new Node<Data>(data[i]));
            preNode = preNode.getNext();
        }
    }

    @SafeVarargs
    public final void addToStart(Node<Data>... node){
        if(getLength() == 0){
            setStart(node[0]);
        }
        else {
            addAfterNode(start.getPrevious(),node[0]);
            start = start.getPrevious();
        }
        Node<Data> preNode = start;
        for (int i = 1; i < node.length; i++) {
            addAfterNode(preNode,node[i]);
            preNode = preNode.getNext();
        }
    }

    @SafeVarargs
    public final void addFrom(int nodeNumber, Data... data){
        if(nodeNumber >= getLength() || nodeNumber < 0)
            throw new IllegalArgumentException("nodeNumber must be >=  && < list.length");
        if(nodeNumber == 0){
            addToStart(data);
            return;
        }
        Node<Data> preNode = getNode(nodeNumber).getPrevious();
        for(Data newData:data){
            addAfterNode(preNode,new Node<Data>(newData));
            preNode = preNode.getNext();
        }
    }

    @SafeVarargs
    public final void addFrom(int nodeNumber, Node<Data>... node){
        if(nodeNumber >= getLength() || nodeNumber < 0)
            throw new IllegalArgumentException("nodeNumber must be >=  && < list.length");
        if(nodeNumber == 0){
            addToStart(node);
            return;
        }
        Node<Data> preNode = getNode(nodeNumber).getPrevious();
        for(Node<Data> newNode:node){
            addAfterNode(preNode,newNode);
            preNode = preNode.getNext();
        }
    }

    public  void removeNode(Integer nodeNumber){
        if(nodeNumber >= getLength() || nodeNumber < 0)
            throw new IllegalArgumentException("nodeNumber must be >=  && < list.length");
        if(getLength() == 1){
            start = null;
            length = 0;
            return;
        }
        Node<Data> temp = getNode(nodeNumber);
        temp.getPrevious().setNext(temp.getNext());
        temp.getNext().setPrevious(temp.getPrevious());
        if(nodeNumber == 0){
            start = temp.getNext();
        }
        length--;
    }

    protected void addAfterNode(Node<Data> preNode, Node<Data> newNode){
        preNode.getNext().setPrevious(newNode);
        newNode.setNext(preNode.getNext());
        preNode.setNext(newNode);
        newNode.setPrevious(preNode);
        length++;
    }

    public void display(){
        if(getLength() < 1) return;
        out.print("list : ");
        out.print("  ");
        out.print(start.getData());
        for(Node p = start.getNext() ;p != start; p = p.getNext()){
            out.print("  ");
            out.print(p.getData());
        }
        out.println();
    }

    public Integer getLength() {
        return length;
    }
}
