package com.example.chimatcha; // Sesuaikan dengan package Anda

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvErrorUsername, tvErrorPassword, tvRegister;
    private Button btnLogin;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Menghubungkan ke XML

        // 1. Inisialisasi View
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvErrorUsername = findViewById(R.id.tvErrorUsername);
        tvErrorPassword = findViewById(R.id.tvErrorPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // 2. Logika Toggle Password Visibility (Ikon Mata)
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - 50)) {

                        if (isPasswordVisible) {
                            // Sembunyikan Password
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            isPasswordVisible = false;
                        } else {
                            // Tampilkan Password
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            isPasswordVisible = true;
                        }
                        // Kembalikan kursor ke posisi akhir
                        etPassword.setSelection(etPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        // 3. Logika Klik Tombol Login & Validasi
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndLogin();
            }
        });

        // 4. Logika Pindah Halaman (Register)
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke Halaman Register
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); // Pastikan class RegisterActivity sudah dibuat
                startActivity(intent);
            }
        });
    }

    private void validateAndLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isValid = true;

        // Reset pesan error
        tvErrorUsername.setVisibility(View.GONE);
        tvErrorPassword.setVisibility(View.GONE);

        // Validasi Username (> 6 karakter)
        if (username.isEmpty() || username.length() <= 6) {
            tvErrorUsername.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Validasi Password (Tidak boleh kosong)
        if (password.isEmpty()) {
            tvErrorPassword.setVisibility(View.VISIBLE);
            isValid = false;
        }

        // Jika semua valid, lanjutkan pindah halaman ke Home/Main
        if (isValid) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // Pindah ke Halaman Utama
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Tutup halaman login agar user tidak bisa 'back' ke halaman ini setelah login
        }
    }
}