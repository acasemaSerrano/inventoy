package com.acasema.inventory.iu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.acasema.inventory.R;
import com.acasema.inventory.iu.dependency.DependencyActiviti;
import com.acasema.inventory.iu.sections.SectionsActivity;

public class DashActivity extends AppCompatActivity {

    private Button btDependency;
    private Button btSectores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);

        btDependency = findViewById(R.id.btDependency);
        btSectores = findViewById(R.id.btSectores);

        inicialice();


    }

    private void inicialice() {
        btDependency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDependency();
            }
        });
        btSectores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSections();
            }
        });
    }

    private void startSections() {
        Intent intent = new Intent(DashActivity.this, SectionsActivity.class);
        startActivity(intent);
    }

    private void startDependency() {
        Intent intent = new Intent(DashActivity.this, DependencyActiviti.class);
        startActivity(intent);
    }
}
