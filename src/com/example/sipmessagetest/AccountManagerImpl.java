package com.example.sipmessagetest;

import com.telestax.tavax.sip.clientauthutils.AccountManager;
import com.telestax.tavax.sip.clientauthutils.UserCredentials;

import tavax.sip.ClientTransaction;


public class AccountManagerImpl implements AccountManager {
    

    public UserCredentials getCredentials(ClientTransaction challengedTransaction, String realm) {
       return new UserCredentialsImpl(SipStackAndroid.getInstance().sipUserName,SipStackAndroid.getInstance().remoteIp,SipStackAndroid.getInstance().sipPassword);
    }

}
