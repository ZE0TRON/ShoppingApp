package com.babob.sporcantam.utility

import java.util.regex.Pattern

class CheckerUtil {

    companion object {
        /**
         * method is used for checking valid email id format.
         *
         * @param email
         * @return boolean true for valid false for invalid
         */
        fun emailChecker(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun checkPassword(password: String):Boolean{
            if(password.length < 6){
                return false
            }
            return true
        }

        fun responseListChecker(rl:List<String>):String{

            if (rl.isEmpty()){
                return "Server Connection Problem"
            }
            else{
                return rl[1]
            }


            return "1"
        }

    }
}