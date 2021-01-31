package com.andrewbutch.androiddevelopertinkofffintech2021

import org.junit.Test


class DateUtilTest {

    @Test
    fun `long time to string correct`() {
        // date sample "Mar 16, 2013 7:24:21 AM"
        val actualStringDate = "Jan 31, 2021 1:09:12 PM"

        // input
        val inputLongDate = 1612087752000L // milliseconds

        val convertedDate = DateUtils.convertLongDateToString(inputLongDate)
        println("Actual: $actualStringDate")
        println("Tested: $convertedDate")
        assert(actualStringDate == convertedDate)
    }

    @Test
    fun `string time to long correct`() {
        // date sample "Mar 16, 2013 7:24:21 AM"
        val actualLongTime = 1612087752000L // milliseconds

        // input
        val inputStringDate = "Jan 31, 2021 1:09:12 PM"

        val convertedDate = DateUtils.convertStringDateToLong(inputStringDate)
        println("Actual: $actualLongTime")
        println("Tested: $convertedDate")

        assert(convertedDate == actualLongTime)
    }

    @Test
    fun `string to long then back to string correct`() {
        // input and actual
        val inputStringDate = "Jan 31, 2021 1:09:12 PM"

        val convertedToLong= DateUtils.convertStringDateToLong(inputStringDate)
        val convertedToString = DateUtils.convertLongDateToString(convertedToLong)
        println("Actual: $inputStringDate")
        println("Tested: $convertedToString")

        assert(inputStringDate == convertedToString)
    }

}