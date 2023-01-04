package com.jonatbergn.core.iceandfire.foundation.entity.interact

///**
// * Use case to load the next page of houses, if such page exists
// */
//abstract class LoadNextEntityPage2<T : Entity>(
//    private val repo: Repo<T>,
//    private val fetchDependents: (T) -> Interaction,
//) : Interaction {
//
//
//    abstract fun onFetchNextPageStarted(currentLastPage: Page?)
//    abstract fun onFetchNextPageSucceeded(previousLastPage: Page?, currentLastPage: Page?)
//    abstract fun onFetchDependentsStarted()
//    abstract fun onFetchDependentsSucceeded()
//
//    override suspend fun invoke() {
//        val oldLastPage = repo.pages?.lastOrNull()
//
//        if (repo.hasMorePagesToFetch == false) {
//            onFetchNextPageSucceeded(oldLastPage, oldLastPage)
//            return
//        }
//
//        onFetchNextPageStarted(
//            currentLastPage = oldLastPage,
//        )
//
//        repo.fetchNextPage()
//
//        val newLastPage = repo.pages?.lastOrNull()
//
//        onFetchNextPageSucceeded(
//            previousLastPage = oldLastPage,
//            currentLastPage = newLastPage,
//        )
//
//        if (oldLastPage != newLastPage && newLastPage != null) {
//            newLastPage.data
//                .mapNotNull { repo[it.url] }
//                .map {
//                    coroutineScope {
//                        async {
//                            onFetchDependentsStarted(it)
//                            fetchDependents(it).awaitAll()
//                            onFetchDependentsSucceeded(it)
//                        }
//                    }
//                }
//                .awaitAll()
//        }
//    }
//}
