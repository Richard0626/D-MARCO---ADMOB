package com.fernandez.aplicacionsensores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mInterstitialAd : InterstitialAd?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
       // adViewBanner.loadAd(adRequest)


        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712",
            adRequest,object :InterstitialAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.v("ADS_ADMOB","Intersticial - ERROR")
                Log.v("ADS_ADMOB",adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.v("ADS_ADMOB","Intersticial - SUCCESS")
                mInterstitialAd = interstitialAd
            }
        })

        btnInstersticial.setOnClickListener{
            if(mInterstitialAd != null){
                mInterstitialAd?.show(this)
            }else{
                Log.v("ADS_ADMOB","Instersticial - NO ESTA INICIALIZADO")
            }
        }
    }


}