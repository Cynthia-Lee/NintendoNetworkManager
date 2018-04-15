package com.example.cynth.nintendonetworkmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) this.findViewById(R.id.tv); // associates xml objects into java
    }

    TextView tv;
    protected static NetworkTree tree = new NetworkTree();
    protected static NetworkNode cut = null;

    public void switchToLoad(View view) {
        Intent i = new Intent(getApplicationContext(),Load.class);
        startActivity(i);
        tv.setText("");
    }

    public void switchToPrint(View view) {
        Intent i = new Intent(getApplicationContext(),Print.class);
        startActivity(i);
        tv.setText("");
    }
    public void switchToChild(View view) {
        Intent i = new Intent(getApplicationContext(),Child.class);
        startActivity(i);
        tv.setText("");
    }

    public void toRoot(View view) {
        tree.cursorToRoot();
        if (tree.getRoot() == null) {
            tv.setText("There is no tree.");
        } else {
            tv.setText("Cursor moved to " + tree.getCursor().getName());
        }
    }

    public void toParent(View view) {
        try {
            tree.cursorToParent();
            tv.setText("Cursor moved to " + tree.getCursor().getName());
        } catch (InvalidCursorPositionException e) {
            tv.setText("Can't move the cursor up, will be at an invalid position.");
        }
    }

    public void switchToAddChild(View view) {
        Intent i = new Intent(getApplicationContext(),Add.class);
        startActivity(i);
        tv.setText("");
    }

    public void cut(View view) {
        try {
            cut = tree.cutCursor();
            if (tree.getRoot() == null) {
                tv.setText(cut.getName() + " cut, the tree is cleared.\n");
            } else {
                tv.setText(cut.getName() + " cut, cursor is at " + tree.getCursor().getName() + "\n");
            }
        } catch (NoTreeException e) {
            tv.setText("No tree, no nodes to cut.\n");
        }
    }

    public void switchToPaste(View view) {
        Intent i = new Intent(getApplicationContext(),Paste.class);
        startActivity(i);
        tv.setText("");
    }

    public void switchToSave(View view) {
        Intent i = new Intent(getApplicationContext(),Save.class);
        startActivity(i);
        tv.setText("");
    }


}
