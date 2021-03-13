package com.example.splatchscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.form_validation.constant.PasswordPattern;
import com.github.dhaval2404.form_validation.rule.EmailRule;
import com.github.dhaval2404.form_validation.rule.LengthRule;
import com.github.dhaval2404.form_validation.rule.MaxLengthRule;
import com.github.dhaval2404.form_validation.rule.MinLengthRule;
import com.github.dhaval2404.form_validation.rule.NonEmptyRule;
import com.github.dhaval2404.form_validation.rule.NumberRule;
import com.github.dhaval2404.form_validation.rule.PasswordRule;
import com.github.dhaval2404.form_validation.rule.RegexRule;
import com.github.dhaval2404.form_validation.validation.FormValidator;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity  {
    //variables
    TextInputLayout user,password,name,email,phone;
    Button btn_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //declarations
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        btn_sign = findViewById(R.id.sign_up);
        // btn click
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
       if(!isValidForm()) return;
       Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidForm() {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        FormValidator f = new FormValidator();
        f.addField(user,
                new NonEmptyRule("Champ vide non autorisé"),
                new MinLengthRule(5, "min 5 caractères"),
                new MaxLengthRule(10, "max 10 caractères"),
                new RegexRule(RegexRule.USERNAME_PATTERN,  "Pas valide"));

        f.addField(name,
                new NonEmptyRule( "Champ vide non autorisé"),
                new MinLengthRule(2, "min 2 caractères"),
                new MaxLengthRule(10, "max 10 caractères"));

        f.addField(password,new RegexRule(PASSWORD_PATTERN, "Mot de passe faible"));

        f.addField(phone,
                new NumberRule("Numero pas valide"),
                new LengthRule(9, "Numero pas valide"));


        f.addField(email,new EmailRule("Email pas valide"));
        return  f.validate();
    }

    /*private Boolean validateName() {
        String name = user.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\S+$)";
        if(name.isEmpty()){
            user.setError("Champ Obligatoire");
            return false;
        }
        else if(name.length()>15){
            user.setError("Taille max est dépassée");
            return false;
        }
        else if(!name.matches(noWhiteSpace)){
            user.setError("espaces pas autorisés");
            return false;
        }
        else{
            user.setError(null);
            return true;
        }
    }*/
}