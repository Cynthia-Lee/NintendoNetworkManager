package com.example.cynth.nintendonetworkmanager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Load extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        name = (EditText)this.findViewById(R.id.message); // associates xml objects into java
        out = (EditText)this.findViewById(R.id.statement);
        //context = getApplicationContext();
    }
    //Context context;
    EditText name;
    EditText out;
    public void load (View view) {
        String s = name.getText().toString(); // gets string that the user enters in the text box
        // directory of downloads
        //File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // File file = new File(downloads, name);
        try {
            MainActivity.tree = readFromFileAndroid(s);
            out.setText(s + " loaded");
        } catch (FileNotFoundException e) {
            out.setText(s + " not found.");
        } catch (CannotAddChildException e) {
            out.setText("Cannot add a child at a Nintendo node.");
        } catch (InvalidArgumentException e) {
            out.setText("Could not reach child.");
        } catch (HoleInArrayException e) {
            out.setText("Could not add child correctly. Adding would create a hole in the array.");
        } catch (FullTreeException e) {
            out.setText("The tree is full, could not add child correctly.");
        } catch (NoTreeException e) {
            out.setText("There is no tree.");
        } catch (IOException e) {
            out.setText("Input or output file exception.");
        }
        name.setText(""); // clear the box so they can enter a new name




    }

    public NetworkTree readFromFileAndroid(String filename)
            throws FileNotFoundException, InvalidArgumentException, HoleInArrayException, FullTreeException, NoTreeException, CannotAddChildException, IOException{
        NetworkTree tree = new NetworkTree();
        //File inputFile = new File(filename);
        //Scanner fs = new Scanner(inputFile);
        int letterIndex = 0;
        boolean hasDash = false;
        String number = "";
        String name;
        tree.cursorToRoot();

        //*Don't* hardcode "/sdcard"
        //File place = Environment.getExternalStorageDirectory();
        File place = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //for (int i = 0; i < place.listFiles().length; i++) {
            //out.setText(place.listFiles()[0].getName());
        //}
        //String fpath = Environment.getExternalStorageDirectory() + filename;

        //Get the text file
        //File file = new File(place,filename); // original
        //File file = new File(getExternalFilesDir(null) + filename);

        /*
        FileInputStream fis = openFileInput(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        */
        //String dir = getFilesDir().getAbsolutePath();

        //FileInputStream fis = context.openFileInput(filename);
        //InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        //BufferedReader br = new BufferedReader(isr);
        //StringBuilder sb = new StringBuilder();
        //String line;

        //Read text from file
        StringBuilder text = new StringBuilder();

        // Original
        //BufferedReader br = new BufferedReader(new FileReader(file));
        //String line;

        //BufferedReader br = new BufferedReader(new FileReader(fpath));
/*
        InputStream inputStream = openFileInput(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line;
*/
        AssetManager assetManager = getAssets();
        InputStream is = assetManager.open(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line;

        while ((line = br.readLine()) != null) {
            hasDash = false;
            if (Character.isDigit(line.charAt(0))) {
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isLetter(line.charAt(i))) {
                        letterIndex = i;
                        number = line.substring(0, letterIndex);
                        break;
                    } else if (line.charAt(i) == '-') {
                        hasDash = true;
                        letterIndex = i + 1;
                        number = line.substring(0, i);
                        break;
                    }
                }
                // String number = line.substring(0, letterIndex);
                // now have the name and the number
                name = line.substring(letterIndex);
                // add the node to the tree!
                int j;
                while (number.length() > 1) {
                    j = number.charAt(0) - '0' - 1;
                    tree.cursorToChild(j);
                    number = number.substring(1);
                }
                tree.addChild(number.charAt(0) - '0' - 1, new NetworkNode(name, hasDash));
                tree.cursorToRoot();

            } else { // doesn't start with a number (root)
                tree.setRoot(new NetworkNode(line, false)); // raspberry pi
                tree.setCursor(tree.getRoot());
            }

        }
        br.close();

        return tree;

        /*
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        //fis = openFileInput("out.txt");
        fis = openFileInput(filename);
        DataInputStream dataIO = new DataInputStream(fis);
        String strLine = null;

        while ((strLine = dataIO.readLine()) != null) {
            //storedString.append(strLine);

        }

        dataIO.close();
        fis.close();

        return tree;
        */

    }



}
