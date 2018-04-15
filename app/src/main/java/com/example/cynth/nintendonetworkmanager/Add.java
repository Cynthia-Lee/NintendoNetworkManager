package com.example.cynth.nintendonetworkmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        index = (EditText)this.findViewById(R.id.index); // associates xml objects into java
        out = (EditText)this.findViewById(R.id.display2); // associates xml objects into java
        if (MainActivity.tree.getCursor() == null) { // if the tree is empty, does not prompt for child index number
            index.setVisibility(View.GONE);
        }
        deviceName = (EditText)this.findViewById(R.id.deviceName);
        checkBox = (CheckBox) findViewById(R.id.checkBox4);
    }
    EditText index;
    EditText out;
    EditText deviceName;
    CheckBox checkBox;

    public void add (View view) {
        String in = index.getText().toString(); // gets string that the user enters in the text box
        String dName = deviceName.getText().toString();
        int i = 0;
        if (MainActivity.tree.getCursor() == null) { // if the tree is empty, does not prompt for child index number
            // tree is empty so
            // emptyTree = true;
            i = 1;
        } else {
            //System.out.print("Please enter an index: ");
            i = Integer.parseInt(in);
        }
        //System.out.print("Please enter device name: ");
        //String name = input.nextLine();
        //System.out.print("Is this Nintendo (y/n): ");
        //String isNintendo = input.nextLine();
        try {
            if (checkBox.isChecked()) {
                NetworkNode created = new NetworkNode(dName, true);
                // if (!emptyTree) {
                MainActivity.tree.addChild(i - 1, created);
                // } else {

                // }
                out.setText("Nintendo added.");
                // move cursor to the new child added
                MainActivity.tree.setCursor(created);
            } else {
                NetworkNode created = new NetworkNode(dName, false);
                MainActivity.tree.addChild(i - 1, created);
                out.setText("Non-Nintendo added.");
                // move cursor to the new child added
                MainActivity.tree.setCursor(created);
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
