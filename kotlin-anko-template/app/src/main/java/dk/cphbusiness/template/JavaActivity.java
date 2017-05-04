package dk.cphbusiness.template;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JavaActivity extends Activity {

    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        message = (TextView)findViewById(R.id.message);
        message.setText("A Java Activity");
        Button toastButton = (Button)findViewById(R.id.toastButton);
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        JavaActivity.this,
                        "Toast button pressed",
                        Toast.LENGTH_LONG
                        ).show();
                }
            } );
        }

    public void showKotlinClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }

    }
