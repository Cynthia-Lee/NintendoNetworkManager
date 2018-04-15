package com.example.cynth.nintendonetworkmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Paste extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paste);
        index = (EditText)this.findViewById(R.id.index2); // associates xml objects into java
        out = (EditText)this.findViewById(R.id.display4);

    }

    EditText index;
    EditText out;

    public void pasteTree(View view) {
        if (MainActivity.cut == null) {
            out.setText("Cannot paste, nothing to paste.\n");
        } else {
            String in = index.getText().toString(); // gets string that the user enters in the text box
            int i = Integer.parseInt(in);
            try {
                // tree.addChild(i - 1, cut);
                if (MainActivity.tree.getRoot() != null) { // tree is not empty, do this
                    MainActivity.tree.addChild(i - 1, MainActivity.cut);
                    out.setText(MainActivity.cut.getName() + " pasted as child as of " + MainActivity.cut.getParent().getName());
                    MainActivity.cut = null;
                } else { // tree empty cause root is null
                    if (i == 1) { // make sure the index is valid when the tree is empty
                        // index should be 1, it is the only choice?
                        MainActivity.tree.addChild(i - 1, MainActivity.cut);
                        out.setText(MainActivity.cut.getName() + " is pasted.");
                        MainActivity.tree.setCursor(MainActivity.cut);
                        MainActivity.cut = null;
                    } else {
                        out.setText("Cannot paste at that index. Since the tree is empty, should paste at index 1.");
                    }
                }
            } catch (CannotAddChildException e) {
                out.setText("Cannot add a child at a Nintendo node.");
            } catch (HoleInArrayException e) {
                out.setText("The index entered is invalid as it would make a hole in the child array.");
            } catch (InvalidArgumentException e) {
                out.setText("Cannot add a child with an invalid index.");
            } catch (FullTreeException e) {
                out.setText("Cannot add a child if the parent's child array is full.");
            }
        }
    }
}
