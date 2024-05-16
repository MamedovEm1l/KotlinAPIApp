package com.example.work

import UserAdapter
import UserResponse
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим RecyclerView в макете activity_main.xml
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Инициализируем адаптер для RecyclerView
        adapter = UserAdapter(emptyList())
        recyclerView.adapter = adapter

        // Выполняем запрос к серверу для получения данных о пользователях
        val call = ApiClient.randomUserService.getUsers()
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val users = userResponse?.results ?: emptyList()

                    // Обновляем данные в адаптере после успешного получения
                    adapter.updateData(users)
                } else {
                    // Обработка ошибки при получении данных
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // Обработка ошибки при выполнении запроса
                Log.e("MainActivity", "Error: ${t.message}", t)
            }
        })
    }
}
