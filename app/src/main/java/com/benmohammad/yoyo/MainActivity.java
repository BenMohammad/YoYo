package com.benmohammad.yoyo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benmohammad.yoyo.api.ApiHandler;
import com.benmohammad.yoyo.api.ApiService;
import com.benmohammad.yoyo.api.PostData;
import com.benmohammad.yoyo.snippets.CodeSnippets;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final int MYREQUEST = 11;
    EditText codeBox;
    TextView outputBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeBox = findViewById(R.id.codebox);
        outputBox = findViewById(R.id.outputbox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.runcodebutton:
                compileCode();
                return true;
            case R.id.snippetscode:
                openSnippets();
                return true;
            default:
                return false;
        }
    }

    private void compileCode(){
        ApiService apiService = ApiHandler.getRetrofit();
        Call<String> execute = apiService.execute(new PostData(codeBox.getText().toString()));

        execute.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if(response.isSuccessful()) {
                        JSONObject responseJson = new JSONObject(response.body().toString());
                        String output = responseJson.getString("output");
                        outputBox.setText(output);
                    } else {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Parsing error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                outputBox.setText("Failed");


            }
        });
    }

    private void openSnippets(){
        Intent intent = new Intent(this, CodeSnippets.class);
        startActivityForResult(intent, MYREQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MYREQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                String snip = data.getStringExtra("snip");
                EditText editText = findViewById(R.id.codebox);
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                editable.insert(start, snip);
            }
        }
    }

}