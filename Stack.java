import java.io.Serializable;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nikki
 */
public class Stack implements Serializable {

    Node head; 
    int size;
    
    Stack(Event current) {
        this.head = new Node(current);
        this.size++;
    }

    void addEventToStack(Event current) {
        Node temp = new Node(current);
        head.setParent(temp);
        temp.setChild(head);
        head = temp;
        size++;
    }
    
    public Event pop() {
        // set head to node beneath current head
        Node temp = head;
        head = head.getChild(head);
        Event ret = temp.getContent(temp);
                size--;
        return ret;
    }

    public int getSize() {
        return size;
    }

    
    public class Node implements Serializable {
        private Node parent = null, child = null;
        private Event content;

        private Node(Event current) {
            content = current;
        }
        private Event getContent(Node current) {
            return current.content;
        }
        
        private Node getChild(Node current) {
            Node temp = current.child;
            return temp;
        }
        private void setChild(Node child) {
            this.child = child;
        }
        
        private Node getParent(Node current) {
            Node temp = current.parent;
            return temp;
        }
        private void setParent(Node parent) {
            this.parent = parent;
        }
        
    }
    
    public Event[] toArray(Stack arrStack) {
        Event[] row = new Event[arrStack.getSize() - 1];
        for (int i = arrStack.getSize() - 2;  i > -1;  i--)
        {
            row[i] = arrStack.pop();

        }
        for (int i = row.length - 1; i  > -1; i--) {
            arrStack.addEventToStack(row[i]);
        }
        return row;
    }
}
