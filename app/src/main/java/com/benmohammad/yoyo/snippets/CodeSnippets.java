package com.benmohammad.yoyo.snippets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.benmohammad.yoyo.R;
import com.benmohammad.yoyo.adapter.CommAdapter;
import com.benmohammad.yoyo.adapter.Communicator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CodeSnippets extends AppCompatActivity implements Communicator {

    String[] files;
    ArrayList<Snip> arr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippets);
        arr = new ArrayList<>();
        loadSnips();
    }

    private void loadSnips() {
        arr.clear();
        Snip obj1 = new Snip("Lambda Example", "import java.util.ArrayList;\n" +
                "\n" +
                "public class MyClass {\n" +
                "  public static void main(String[] args) {\n" +
                "    ArrayList<Integer> numbers = new ArrayList<Integer>();\n" +
                "    numbers.add(5);\n" +
                "    numbers.add(9);\n" +
                "    numbers.add(8);\n" +
                "    numbers.add(1);\n" +
                "    numbers.forEach( (n) -> { System.out.println(n); } );\n" +
                "  }\n" +
                "}");
        Snip obj2 = new Snip("Lambda Example 2", "interface StringFunction {\n" +
                "  String run(String str);\n" +
                "}\n" +
                "\n" +
                "public class MyClass {\n" +
                "  public static void main(String[] args) {\n" +
                "    StringFunction exclaim = (s) -> s + \"!\";\n" +
                "    StringFunction ask = (s) -> s + \"?\";\n" +
                "    printFormatted(\"Hello\", exclaim);\n" +
                "    printFormatted(\"Hello\", ask);\n" +
                "  }\n" +
                "  public static void printFormatted(String str, StringFunction format) {\n" +
                "    String result = format.run(str);\n" +
                "    System.out.println(result);\n" +
                "  }\n" +
                "}");
        arr.add(obj1);
        arr.add(obj2);


        File f = new File("" + getFilesDir() + "/snip");
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".");
            }
        };

        if(!f.exists()) {
            f.mkdir();
        }
        files = f.list(filenameFilter);
        assert files != null;
        for(String fname: files) {
            try {
                String yourFilePath = getApplicationContext().getFilesDir() + "/snip/" + fname;
                FileInputStream fin = new FileInputStream(yourFilePath);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while(true) {
                    try {
                        if(!((line = bufferedReader.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(line + "\n");
                }
                Snip tempContainer = new Snip(fname.substring(0, fname.length() - 5), sb.toString());
                arr.add(tempContainer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ListView l1 = findViewById(R.id.sniplist);
        l1.setAdapter(new CommAdapter(arr, getApplicationContext(), this));
    }

    @Override
    public void customSetResult(String snippet) {
        Intent i  = new Intent();
        i.putExtra("snip", snippet);
        setResult(Activity.RESULT_OK, i);
        this.finish();
    }
}
