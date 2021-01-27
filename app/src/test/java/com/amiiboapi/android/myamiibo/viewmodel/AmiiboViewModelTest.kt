package com.amiiboapi.android.myamiibo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.amiiboapi.android.myamiibo.model.AmiiboRelease
import com.amiiboapi.android.myamiibo.repository.AmiiboRepository
import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AmiiboViewModelTest : TestCase() {

    val amiiboRepository: AmiiboRepository = mock(AmiiboRepository::class.java)
    val dataBaseHandler: DataBaseHandler = mock(DataBaseHandler::class.java)
    var amiiboViewModel: AmiiboViewModel = AmiiboViewModel(amiiboRepository, dataBaseHandler)

    @Test
    fun testGetAmiibo() {
        val amiiboData: MutableLiveData<Amiibo> = MutableLiveData(amiibo)
        `when`(amiiboRepository.getAmiiboList()).thenReturn(amiiboData)
        assertNotNull(amiiboViewModel.getAmiibo())
    }

    @Test
    fun testDeleteDatabase() {
        `when`(dataBaseHandler.deleteAllData()).thenReturn(true)
        assertTrue(amiiboViewModel.deleteDatabase())
    }

    private val amiibo = Amiibo(amiibo = listOf(
        AmiiboData(0,"","","","","","", AmiiboRelease("","","",""),"","")))
}