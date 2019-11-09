package com.yawjenn.mvvmpractice.posts

import androidx.lifecycle.*
import com.yawjenn.mvvmpractice.data.DataRepository
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.util.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class PostsViewModel(private val dataRepository: DataRepository)  : ViewModel() {
    private val _userId = MutableLiveData<String>()
    val userId : LiveData<String>
        get() = _userId

    private val _userPosts : LiveData<List<Post>> = _userId.switchMap {
        liveData {
            try {
                emitSource(dataRepository.getPosts(it))
            } catch (e: HttpException){
                e.toString().logError()
            }
        }
    }
    val userPosts : LiveData<List<Post>>
        get() = _userPosts

    val unreadPostCountLabel : LiveData<String> = _userPosts.map { posts ->
        "You have ${posts.count { !it.read }} unread posts"
    }

    fun loadUser(userId: String?){
        _userId.value = userId
    }

    fun onPostToggled(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.updatePost(post.copy(read = !post.read))
        }
    }
}
