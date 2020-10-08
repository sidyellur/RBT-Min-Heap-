import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define a public class for RedBlack Tree data structure
public class RedBlackTree 
{

    private final int red = 0;
    private final int black = 1;

    private class Node {
        Building key;
        int color = black;
        Node left = nil, right = nil, prent = nil;

        Node(Building key) {
            this.key = key;
        } 
    }

    private final Node nil = new Node(new Building(-1, -1, -1)); 
    private Node root = nil;

    

    public Building getNode(int buildingNumber) 
    {
        return findNode(buildingNumber, root).key;
    }
    
    //Used to print the contents of the tree
    public void printTree(Node node) 
    {
        if (node == nil) 
        {
            return;
        }
        printTree(node.left);
        System.out.print(((node.color==red)?"Color: Red ":"Color: Black ")+"Key: "+node.key+" Parent: "+node.prent.key+"\n");
        printTree(node.right);
    }

     private List<Building> findNodes(int bn1, int bn2, Node node, List<Building> list) 
     {
        if (bn1 < node.key.buildingNumber && bn2 > node.key.buildingNumber) 
        {
            list.add(node.key);
            if (node.left != nil) 
            {
                list = findNodes(bn1, bn2, node.left, list);
            }
            if (node.right != nil) 
            {
                list = findNodes(bn1, bn2, node.right, list);
            }
        } else if (bn1 > node.key.buildingNumber) 
         {
            if (node.left != nil) 
            {
                list = findNodes(bn1, bn2, node.left, list);
            }
         } else if (bn2 < node.key.buildingNumber) 
        {
            if (node.right != nil) {
                list = findNodes(bn1, bn2, node.right, list);
            }
        }
        return list; 
    }
    
    private Node findNode(int buildingNumber, Node node) 
    {
        if (root == nil) 
        {
            return null;
        }

        if (buildingNumber < node.key.buildingNumber) 
        {
            if (node.left != nil)
            {
                return findNode(buildingNumber, node.left);
            }
        } else if (buildingNumber > node.key.buildingNumber) 
        {
            if (node.right != nil) 
            {
                return findNode(buildingNumber, node.right);
            }
        } else if (buildingNumber == node.key.buildingNumber) 
        {
            return node;
        }
        return nil;
    }

    public List<Building> getNodes(int bn1, int bn2) 
    {
        if (root == nil) 
        {
            return new ArrayList<Building>();
        }
        return findNodes(bn1, bn2, root, new ArrayList<Building>());
        
    }

    
    //Used to insert the new node into the red black tree
    private void insert(Node node) 
    {
        Node temp = root;
        if (root == nil) 
        {
            root = node;
            node.color = black;
            node.prent = nil;
        } else 
        {
            node.color = red;
            while (true) {
                if (node.key.buildingNumber < temp.key.buildingNumber) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.prent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.key.buildingNumber >= temp.key.buildingNumber) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.prent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }
    
    public void insert(Building b) 
    {
        Node n = new Node(b);
        insert(n);
    }

    //Takes the newly inserted node as the argument and is used to perform the insertion modules
    private void fixTree(Node node) 
    {
        while (node.prent.color == red) 
        {
            Node uncle = nil;
            if (node.prent == node.prent.prent.left) 
            {
                uncle = node.prent.prent.right;

                if (uncle != nil && uncle.color == red) 
                {
                    node.prent.color = black;
                    uncle.color = black;
                    node.prent.prent.color = red;
                    node = node.prent.prent;
                    continue;
                } 
                if (node == node.prent.right) 
                {
                //Double rotation required
                    node = node.prent;
                    rotateLeft(node);
                } 
                node.prent.color = black;
                node.prent.prent.color = red;
                //if the "else if" code doesn't run, then  we only need a single rotation 
                rotateRight(node.prent.prent);
            } else {
                uncle = node.prent.prent.left;
                 if (uncle != nil && uncle.color == red) 
                 {
                    node.prent.color = black;
                    uncle.color = black;
                    node.prent.prent.color = red;
                    node = node.prent.prent;
                    continue;
                }
                if (node == node.prent.left) 
                {
                    //Double rotation required
                    node = node.prent;
                    rotateRight(node);
                }
                node.prent.color = black;
                node.prent.prent.color = red;
                //if the "else if" code hasn't run, this means we only need a single rotation
                rotateLeft(node.prent.prent);
            }
        }
        root.color = black;
    }
    
    //Function to perform right rotation
    void rotateRight(Node node) 
    {
        if (node.prent != nil) 
        {
            if (node == node.prent.left) 
            {
                node.prent.left = node.left;
            } else {
                node.prent.right = node.left;
            }

            node.left.prent = node.prent;
            node.prent = node.left;
            if (node.left.right != nil) 
            {
                node.left.right.prent = node;
            }
            node.left = node.left.right;
            node.prent.right = node;
        } else 
        {
            //Need to rotate the root
            Node left = root.left;
            root.left = root.left.right;
            left.right.prent = root;
            root.prent = left;
            left.right = root;
            left.prent = nil;
            root = left;
        }
    }

    //Function to perform Left Rotation
    void rotateLeft(Node node) 
    {
        if (node.prent != nil)
         {
            if (node == node.prent.left) 
            {
                node.prent.left = node.right;
            } else {
                node.prent.right = node.right;
            }
            node.right.prent = node.prent;
            node.prent = node.right;
            if (node.right.left != nil) 
            {
                node.right.left.prent = node;
            }
            node.right = node.right.left;
            node.prent.left = node;
        } else 
        { 
            //Need to rotate the root
            Node right = root.right;
            root.right = right.left;
            right.left.prent = root;
            root.prent = right;
            right.left = root;
            right.prent = nil;
            root = right;
        }
    }


    //Deletes the whole tree
    void deleteTree()
    {
        root = nil;
    }
    
    //Deletion Code
    
    //Operation doesn't consider the new node's relation with the previous node's
    // right and left child. 
    void transplant(Node target, Node with)
    { 
          if(target.prent == nil)
          {
              root = with;
          }else if(target == target.prent.left)
          {
              target.prent.left = with;
          }else
              target.prent.right = with;
          with.prent = target.prent;
    }
    
    //Function used to delete from the RedBlack Tree
    public boolean delete(int bn){
        Node z;
        if((z = findNode(bn, root))==null)return false;
        Node x;
        Node y = z;                      // temporary reference y
        int y_original_color = y.color;
        
        if(z.left == nil){
            x = z.right;  
            transplant(z, z.right);  
        }else if(z.right == nil){
            x = z.left;
            transplant(z, z.left); 
        }else{
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.prent == z)
                x.prent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.prent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.prent = y;
            y.color = z.color; 
        }
        if(y_original_color==black)
            deleteFixup(x);  
        return true;
    }
    
    // Function used to return the root of the subtree
    Node treeMinimum(Node subTreeRoot)
    {
        while(subTreeRoot.left!=nil)
        {
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }

    void deleteFixup(Node x)
    {
        while(x!=root && x.color == black){ 
            if(x == x.prent.left)
            {
                Node w = x.prent.right;
                if(w.color == red){
                    w.color = black;
                    x.prent.color = red;
                    rotateLeft(x.prent);
                    w = x.prent.right;
                }
                if(w.left.color == black && w.right.color == black){
                    w.color = red;
                    x = x.prent;
                    continue;
                }
                else if(w.right.color == black){
                    w.left.color = black;
                    w.color = red;
                    rotateRight(w);
                    w = x.prent.right;
                }
                if(w.right.color == red){
                    w.color = x.prent.color;
                    x.prent.color = black;
                    w.right.color = black;
                    rotateLeft(x.prent);
                    x = root;
                }
            }
                else
            {
                Node w = x.prent.left;
                if(w.color == red){
                    w.color = black;
                    x.prent.color = red;
                    rotateRight(x.prent);
                    w = x.prent.left;
                }
                if(w.right.color == black && w.left.color == black){
                    w.color = red;
                    x = x.prent;
                    continue;
                }
                else if(w.left.color == black){
                    w.right.color = black;
                    w.color = red;
                    rotateLeft(w);
                    w = x.prent.left;
                }
                if(w.left.color == red){
                    w.color = x.prent.color;
                    x.prent.color = black;
                    w.left.color = black;
                    rotateRight(x.prent);
                    x = root;
                }
            }
        }
        x.color = black; 
    }
    
    
    
}