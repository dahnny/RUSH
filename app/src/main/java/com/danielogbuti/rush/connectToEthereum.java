package com.danielogbuti.rush;

import android.util.Log;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

public class connectToEthereum {
    private final String TAG = "akiliHealth";

    public boolean ConnectToEthereum() {
        //Connection to blockchain
        // FIXME: Add your own API key here
        Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/2107afd46330452dbdf527b52339614c"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                //Connected
                Log.e(TAG,"Succesfully Connected To Block");
                return true;

            }
            else {
                //Show Error
                Log.e(TAG,"Not connected");
                return false;

            }
        }
        catch (Exception e) {
            //Show Error
            Log.e(TAG,"Error");
            return false;

        }

        //Endconnection to blockchain

        //Verify connection to blockchan


        //End verification to connection f blockchain

    }
}
