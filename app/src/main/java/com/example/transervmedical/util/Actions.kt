package com.example.transervmedical.util

import android.content.Context
import android.content.Intent

object Actions {

    fun openDashboardIntent(context: Context?) =
        internalIntent(context, "action.dashboard.open")


    fun openLogInIntent(context: Context) =
        internalIntent(context, "android.intent.action.MAIN")


    private fun internalIntent(context: Context?, action: String) =
        Intent(action).setPackage(context?.packageName)

}