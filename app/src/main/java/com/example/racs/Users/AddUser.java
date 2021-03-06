package com.example.racs.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.App;
import com.example.racs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    private TextView name, surname, patronymic, card_id, email;
    private Spinner role;
    private Button add_btn;
    public static final String ADDED_NEW_VALUE = "ADDED NEW VALUE";
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        bindViews();

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UserPostPOJO body = new UserPostPOJO();
                body.setFirstName(String.valueOf(name.getText()));
                body.setLastName(String.valueOf(surname.getText()));
                body.setPatronymic(String.valueOf(patronymic.getText()));
                body.setCardId(String.valueOf(card_id.getText()));
                body.setEmail(String.valueOf(email.getText()));
                body.setRole(role.getSelectedItem().toString());

                App.getUserApi().addUser(settings.getString(ACCESS_TOKEN, ""), body).enqueue(new Callback<UsersPOJO>() {
                    @Override
                    public void onResponse(Call<UsersPOJO> call, Response<UsersPOJO> response) {
                        UsersPOJO user = response.body();
                        Toast.makeText(AddUser.this, "Пользователь успешно добавлен!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra(ADDED_NEW_VALUE, true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<UsersPOJO> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private void bindViews(){
        name = findViewById(R.id.first_name);
        surname = findViewById(R.id.last_name);
        patronymic = findViewById(R.id.patronymic);
        card_id = findViewById(R.id.ed_card_id);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        add_btn = findViewById(R.id.add_user_btn);
    }
}
