# ToyApplication

- Week 1 - 안드로이드 설정 기본 및 디자인 패턴
    - 프로젝트 설정
        - build.gradle
        - androidManifest
    - 디자인패턴 : Model - View - ViewModel
    - [https://developer.android.com/codelabs/kotlin-android-training-view-model?hl=en&continue=https%3A%2F%2Fcodelabs.developers.google.com%2F%3Fcat%3Dandroid#0](https://developer.android.com/codelabs/kotlin-android-training-view-model?hl=en&continue=https%3A%2F%2Fcodelabs.developers.google.com%2F%3Fcat%3Dandroid#0)
    - [https://medium.com/hongbeomi-dev/aac를-사용하여-mvvm-pattern을-구현한-안드로이드-앱-만들기-1d6d73689bd0](https://medium.com/hongbeomi-dev/aac%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-mvvm-pattern%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%9C-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-1d6d73689bd0)
    - [https://ddangeun.tistory.com/81](https://ddangeun.tistory.com/81)
    - [https://hanyeop.tistory.com/168](https://hanyeop.tistory.com/168)
- Week 2 - Layout
    - Activity
        - [https://developer.android.com/guide/components/activities/intro-activities?hl=ko](https://developer.android.com/guide/components/activities/intro-activities?hl=ko)
        - [https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ko](https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ko)
        - [https://developer.android.com/guide/components/activities/state-changes?hl=ko](https://developer.android.com/guide/components/activities/state-changes?hl=ko)
        - [https://developer.android.com/guide/components/activities/parcelables-and-bundles?hl=ko](https://developer.android.com/guide/components/activities/parcelables-and-bundles?hl=ko)
        - launchMode
            - [https://developer.android.com/guide/components/activities/tasks-and-back-stack?hl=ko](https://developer.android.com/guide/components/activities/tasks-and-back-stack?hl=ko)
            - [https://choboit.tistory.com/24](https://choboit.tistory.com/24)
            - [https://onepinetwopine.tistory.com/325](https://onepinetwopine.tistory.com/325)
    - Fragment
        - [https://developer.android.com/guide/fragments?hl=ko](https://developer.android.com/guide/fragments?hl=ko)
        - [https://www.charlezz.com/?p=44128](https://www.charlezz.com/?p=44128)
        - LifeCycle
            - [https://developer.android.com/guide/fragments/lifecycle?hl=ko](https://developer.android.com/guide/fragments/lifecycle?hl=ko)

    - Intent filter
        - https://developer.android.com/guide/components/intents-filters
    - Single Activity
        - [https://handnew04.github.io/posts/2020-11-15/](https://handnew04.github.io/posts/2020-11-15/)


- Week 3 - 생명 주기, RecyclerView
    - ConstraintLayout
        - match_parent, wrap_content
            - 디바이스 크기에 따른 차이
        - left vs start
        - barrier
        - chaining
            - [https://medium.com/@nomanr/constraintlayout-chains-4f3b58ea15bb](https://medium.com/@nomanr/constraintlayout-chains-4f3b58ea15bb)
        - [https://developer.android.com/training/constraint-layout](https://developer.android.com/training/constraint-layout)
        - [https://developer.android.com/codelabs/constraint-layout#0](https://developer.android.com/codelabs/constraint-layout#0)

    - RecyclerView
        - [https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#0](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#0)
    - ListAdapter
        - [https://thdev.tech/kotlin/2020/09/22/kotlin_effective_03/](https://thdev.tech/kotlin/2020/09/22/kotlin_effective_03/)
    - ConcatAdapter
        - [https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter)
    - Different type of view holder
        - https://lktprogrammer.tistory.com/190
    - LIfecycle
        - [https://developer.android.com/topic/libraries/architecture/lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        - [https://developer.android.com/codelabs/android-lifecycles#0](https://developer.android.com/codelabs/android-lifecycles#0)
    - LiveData
    - ViewModel
        - [https://developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)

- Week 4
    - Retrofit
        - MVVM
            - V  : 화면
                -
            - VM : 상태 / 로직
                - 통신 관련 작업
                - 통신 모듈의 종속성 :
                    Repository Module (데이터 저장 / 접근)
                        - LocalRepository  : sharedPreference / Room DB
                        - RemoteRepository : API 통신 (github) , 공공데이터
                        GithubRepository()
                        class GithubViewModel (val githubRepo : GithubRepository()) : ViewModel(){
                            val _list = MutableLiveData<User>()
                            val list = LiveData()
                                get() = _list.value

                            fun getUserList(){
                                _list.value =  githubRepo.getUserList()

                            }
                        }
            - M  : 모델
                -
            흐름 : 뷰 (쿼리) -> 뷰모델 (쿼리 -> 통신 모듈 -> API 통신 -> 결과 -> 뷰모델) -> 뷰 observe

    - Glide  
    - Coroutines
    - 비동기처리
        - 사용자 UX 최적화
            - Main Thread /  Background Thread
                - Main : UI 관련 작업만
                - Background : 시간이 오래 걸리는 작업 (API 통신, DB 작업, etc)
                    - Looper / handler
                    - AsyncTask : Deprecated
                    - RxJava : android 생명 주기 -> RxAndroid
                    - Coroutines(Google)
            [https://developer.android.com/guide/components/processes-and-threads?hl=ko] (https://developer.android.com/guide/components/processes-and-threads?hl=ko)
    - DI (DEPENDENCY INJECTION)
        -
    - 숙제 : 화면 그리기 / 연습 소스 커밋업 ** 드로이드 나이츠?!
        - Client.kt
        - CoroutineGithubRepository - coroutineFetchUser
        - CoroutineGithubListViewModel.kt
        - getUser()
- Week 5
    - Paging
        - [https://developer.android.com/topic/libraries/architecture/paging/v3-overview](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
    - Dependency Injection
    - Koin
         - [https://spoqa.github.io/2020/11/02/android-dependency-injection-with-koin.html](https://spoqa.github.io/2020/11/02/android-dependency-injection-with-koin.html)
         - [https://salix97.tistory.com/265](https://salix97.tistory.com/265)

- Week 6 - 3rd party
    - Lottie, ~~Circle ImageView~~
    - Room, SharedPreference
- Week 7 - Coroutines 7-8주


- Kotlin in action

- https://developer.android.com/training/constraint-layout
