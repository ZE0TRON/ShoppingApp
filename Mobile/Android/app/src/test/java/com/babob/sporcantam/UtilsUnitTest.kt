package com.babob.sporcantam

import com.babob.sporcantam.utility.CheckerUtil
import org.junit.Test

import org.junit.Assert.*


class UtilsUnitTest{

    @Test
    fun emailchecker_correct(){
        var _mail = "okanalan@gmail.com"
        assertEquals(true,CheckerUtil.emailChecker(_mail))
    }

}