package com.example.cynth.nintendonetworkmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        index = (EditText)this.findViewById(R.id.enterIndex); // associates xml objects into java
        out = (EditText)this.findViewById(R.id.display3);
    }

    EditText index;
    EditText out;

    public void addChild(View view) {
        String in = index.getText().toString(); // gets string that the user enters in the text box
        int i = Integer.parseInt(in);
        try {
            MainActivity.tree.cursorToChild(i - 1);
            out.setText("Cursor moved to " + MainActivity.tree.getCursor().getName());
        } catch (InvalidArgumentException e) {
            out.setText("Invalid index was entered.");
        } catch (NoTreeException e) {
           out.setText("No tree, no children to move cursor to.");
        }
    }
}
