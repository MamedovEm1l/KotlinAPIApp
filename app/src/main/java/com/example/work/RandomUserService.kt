import retrofit2.Call
import retrofit2.http.GET

interface RandomUserService {
    @GET("api/?results=10") // указываем конечную точку API и параметры запроса
    fun getUsers(): Call<UserResponse> // метод для получения списка пользователей
}
