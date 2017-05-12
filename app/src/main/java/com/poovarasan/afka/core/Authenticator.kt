package com.poovarasan.afka.core

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract




/**
 * Created by poovarasanv on 11/5/17.

 * @author poovarasanv
 * *
 * @project Afka
 * *
 * @on 11/5/17 at 12:51 PM
 */

class Authenticator(val context : Context) : AbstractAccountAuthenticator(context) {
	override fun getAuthTokenLabel(authTokenType: String?): String? {
		return null
	}
	
	override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle? {
	 return null
	}
	
	override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle? {
		return null
	}
	
	override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
		val result = Bundle()
		result.putString(AccountManager.KEY_ACCOUNT_NAME, account!!.name)
		result.putString(AccountManager.KEY_ACCOUNT_TYPE, SyncStateContract.Constants.ACCOUNT_TYPE)
		return result
	}
	
	override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle? {
		return null;
	}
	
	override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle? {
	 return null
	}
	
	override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
		val intent = Intent(context, AuthenticatorActivity::class.java)
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
		
		val bundle = Bundle()
		bundle.putParcelable(AccountManager.KEY_INTENT, intent)
		return bundle
	}
	
}
