package com.acasema.inventory.iu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.acasema.inventory.R;
import com.acasema.inventory.utils.commonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private Button btSInUp;
    private TextInputEditText tiedUser;
    private TextInputEditText tiedpw1;
    private TextInputEditText tiedemail;

    private TextInputLayout tilUser;
    private TextInputLayout tilpw1;
    private TextInputLayout tilemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tiedUser=findViewById(R.id.tiedUser);
        tiedpw1=findViewById(R.id.tiedPassword1);
        tiedemail=findViewById(R.id.tiedEmail);

        tilUser=findViewById(R.id.tilUser);
        tilpw1=findViewById(R.id.tilPassword1);
        tilemail=findViewById(R.id.tilEmail);

        tiedUser.addTextChangedListener(new SingnUpWuatvher(tiedUser));
        tiedpw1.addTextChangedListener(new SingnUpWuatvher(tiedpw1));
        tiedemail.addTextChangedListener(new SingnUpWuatvher(tiedemail));



        btSInUp=findViewById(R.id.btSInUp);
        btSInUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validad();
            }
        });

        //SingnUpWuatvher singnUpWuatvher = new SingnUpWuatvher();


    }
    /**
     *metodo qie conprueca la validaz de todos los campos
     */
    private void validad() {
        if ( validateEmal(tiedemail.getText().toString())
                & validatePassword(tiedpw1.getText().toString())
                & validateUser(tiedUser.getText().toString())){
            //1. se guarda los datos en la BD
            //2. envio correo al ususario (firebase)
            //3. se pasa a la ventana Login



            finish();

        }
    }

    /**
     * este metodo valida el usuario
     * 1. no vacio
     */
    private boolean validateUser(String user) {
        if (TextUtils.isEmpty(user)){
            tiedUser.setError(getString(R.string.errUserEmpty));
            displaySoftKeyboard(tilUser);
            return false;
        }
        else
        {
            tiedUser.setError(null);
            return true;
        }
    }

    /**
     * este metodo avre wl teclado cuando una vista tenga el foco
     *
     */
    private void displaySoftKeyboard(View view) {
        if (view.requestFocus())
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);

    }

    /**
     * balida los requerimiento de contraseñas
     * 1. conraseña iguales
     * 2. min 8 y una mayuscula y un numero
     * 3. no vacio
     */
    private boolean validatePassword(String pw1) {
        if (commonUtils.checkPatternPassword(pw1)){
            tiedpw1.setError(null);
            return true;
        }
        else {
            tiedpw1.setError(getString(R.string.errPw));

            displaySoftKeyboard(tilpw1);
            return false;
        }
    }

    /**
     * valida el email
     * 1, no vacio
     * 2.
     * @return
     */
    private boolean validateEmal(String email) {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tiedemail.setError(null);
            return true;
        }
        else {
            displaySoftKeyboard(tilemail);
            tiedemail.setError(getString(R.string.errEm));
            return false;
        }
    }

    class SingnUpWuatvher implements TextWatcher{

        private View view;
        private SingnUpWuatvher(View view){
            this.view = view;
        }



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.tiedUser:{
                    validateUser(((TextInputEditText)view).getText().toString());
                    break;
                }
                case R.id.tiedPassword1:{
                     validatePassword(((TextInputEditText)view).getText().toString());
                     break;
                }
                case R.id.tiedEmail:{
                     validateEmal(((TextInputEditText)view).getText().toString());
                     break;
                }

            }
        }
    }
}
