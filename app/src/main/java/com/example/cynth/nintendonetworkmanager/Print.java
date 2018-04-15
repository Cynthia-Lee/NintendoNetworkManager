package com.example.cynth.nintendonetworkmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Print extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        display = (EditText)this.findViewById(R.id.display);

       // display.setText((MainActivity.tree.printTree()));
        printTreeAndroid();
    }

    //@Override
    //protected void onResume() { // saves the previous names when startup
        //super.onResume();
        //in.setText(""); // clear the box so they can enter a new name
        //display.setText(MainActivity.tree.printTree());
    //}
    EditText display;

    //display.setText(MainActivity.tree.printTree());
    //MainActivity.tree.printTree();

    private void printTreeAndroid(NetworkNode node, int indent) {
        // depth is how much space (tab) you move the node when printing
        for (int i = 0; i < indent; i++) {
            //display.append("\t");
            display.append("        ");

        }
        if (MainActivity.tree.getCursor() == node) {
            display.append("->");
        } else if (node.getIsNintendo()) {
            display.append("-");
        } else if (!node.getIsNintendo()) {
            display.append("+");
        }
        display.append(node.getName()); // print it's node, then go to the node's kids
        display.append("\n");
        for (int i = 0; i < node.getNumberOfChildren(); i++) {
            if (node != null) { // node!=null
                printTreeAndroid(node.getChildren()[i], indent + 1);
            }
        }
    }


    public void printTreeAndroid() {
        NetworkNode nodePtr = MainActivity.tree.getRoot(); // node we are at
        if (MainActivity.tree.getRoot() == null) { // the tree is empty, root is null
            display.setText("There is no tree to print.");
        } else {
            int indent = 0;
            printTreeAndroid(nodePtr, indent);
        }
    }

}
