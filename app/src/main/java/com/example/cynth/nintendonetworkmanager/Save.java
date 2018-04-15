package com.example.cynth.nintendonetworkmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Save extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        index = (EditText)this.findViewById(R.id.index3); // associates xml objects into java
        out = (EditText)this.findViewById(R.id.display6);
    }
    EditText index;
    EditText out;

    public void saveThis(View view) {
        String s = index.getText().toString();
        //String fileName = input.nextLine();
        try {
            //MainActivity.tree.writeToFile(MainActivity.tree, s);
            writeToFileAndroid(MainActivity.tree, s);
            out.setText("File saved.");
        } catch (IOException e) {
            out.setText("File has IOException.");
        }
    }

    public static void writeToFileAndroid(NetworkTree tree, String filename) throws IOException {
        // generates text file that reflects the structure of the network tree
        // the format of the tree should match the format of the input file
        File outputFile = new File(filename);
        FileWriter fw = new FileWriter(outputFile, false);
        PrintWriter pw = new PrintWriter(fw);
        // pw.println(root.getName());
        // NetworkNode nodePtr = root;
        // now print the kids
        // if kids are not null, going to recursively call print node on the kid
        // indent add
        NetworkNode nodePtr = MainActivity.tree.getRoot(); // node we are at
        String pre = "";
        pw.print(saveTreeAndroid(nodePtr, pre));
        pw.flush();
        pw.close();
    }

    /**
     * Method that creates a description of the NetworkTree with a String (recursive)
     * @param node
     * Node to be represented with a String
     * @param prefix
     * The String of the number before the node's name
     * @return
     */
    private static String saveTreeAndroid(NetworkNode node, String prefix) {
        String result = "";
        // File outputFile = new File(filename);
        // FileWriter fw = new FileWriter(outputFile, false);
        // PrintWriter pw = new PrintWriter(fw);
        result += prefix;
        if (node.getIsNintendo()) {
            result += "-";
        }
        result += node.getName() + "\r\n"; // print it's node, then go to the node's kids
        for (int i = 0; i < node.getNumberOfChildren(); i++) {
            if (node != null) { // node!=null
                result += saveTreeAndroid(node.getChildren()[i], prefix + (i + 1));
            }
        }
        // System.out.println(result);
        return result;
    }


}
