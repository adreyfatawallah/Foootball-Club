package com.example.adrey.footballclub.utils

import com.example.adrey.footballclub.utils.Utils.formatTime
import com.example.adrey.footballclub.utils.Utils.formatDate
import org.junit.Test
import org.junit.Assert.*

class UtilsTest {

    @Test
    fun testFormatDate() {
        val string = "23/09/18"
        assertEquals("Min, 23 Sep 2018", formatDate(string))
    }

    @Test
    fun testFormatTime() {
        val string = "16:40:27"
        assertEquals("16:40 PM", formatTime(string))
    }
}