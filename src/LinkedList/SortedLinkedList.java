package LinkedList;

import Data.ComparableData;

import java.math.BigDecimal;

public class SortedLinkedList extends NewLinkedList<ComparableData> {

    private void singleSortedInsert(ComparableData comparableData){
        if(length == 0){
            setStart(new Node<>(comparableData));
        }
        else{
            Node<ComparableData> node = findFirstBiggerNode(comparableData);
            if(node == null){
                addToFinal(comparableData);
            }
            else if(node == start){
                addToStart(comparableData);
            }
            else{
                addAfterNode(node.getPrevious(), new Node<>(comparableData));
            }
        }
    }

    public void sortedInsert(ComparableData... comparableData){
        for (ComparableData data:
             comparableData) {
            singleSortedInsert(data);
        }
    }

    private Node<ComparableData> findFirstBiggerNode(ComparableData data){
        Node<ComparableData> node = start;
        for(int i = 0; i < length; node = node.getNext() , i++){
            if(data.getComparableValue().compareTo(node.getData().getComparableValue()) < 0)
                return node;
        }
        return null;
    }

    public int searchNode(Node<ComparableData> node){
        Node<ComparableData> cNode;
        cNode = start;
        for(int i = 0 ; i<getLength(); i++ , cNode = cNode.getNext()){
            if(cNode.getData().getComparableValue().equals(node.getData().getComparableValue())){
                return i;
            }
            else if(cNode.getData().getComparableValue().compareTo(node.getData().getComparableValue()) > 0){
                return -1;
            }
        }
        return -1;
    }

    public int searchNode(BigDecimal data){
        Node<ComparableData> cNode;
        cNode = start;
        for(int i = 0 ; i<getLength(); i++ , cNode = cNode.getNext()){
            if(cNode.getData().getComparableValue().equals(data)){
                return i;
            }
            else if(cNode.getData().getComparableValue().compareTo(data) > 0){
                return -1;
            }
        }
        return -1;
    }

    public void removeData(BigDecimal data){
        Node<ComparableData> cNode;
        cNode = start;
        for(int i = 0 ; i<getLength(); i++ , cNode = cNode.getNext()){
            if(cNode.getData().getComparableValue().equals(data)){
                removeNode(i);
            }
            else if(cNode.getData().getComparableValue().compareTo(data) > 0){
                return;
            }
        }
    }
}
