/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erp_system;

/**
 *
 * @author Marcio
 */
public class Node {
    
    private Product element;
    private Node parent;
    private Node left;
    private Node right;
    
    public Node(Product element)
    {
        this.element = element;
    }

    public Product getElement() {
        return element;
    }

    public void setElement(Product element) {
        this.element = element;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    
}
