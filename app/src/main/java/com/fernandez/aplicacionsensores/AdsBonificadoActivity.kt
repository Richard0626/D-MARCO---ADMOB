package com.fernandez.aplicacionsensores

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.activity_bonificado.*

class AdsBonificadoActivity:AppCompatActivity() {
    var mRewardedAd : RewardedAd? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bonificado)

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917",
            adRequest,object :RewardedAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                }
            })


        btnBonificado.setOnClickListener{
            if (mRewardedAd != null){
                mRewardedAd?.show(this){
                    fun onUserEarnedReward(rewardItem: RewardItem){
                        var rewardAmount = rewardItem.amount
                        var rewardType = rewardItem.type
                        Log.v("ADS_BONIFICADO",rewardAmount.toString())
                        Log.v("ADS_BONIFICADO",rewardType)
                    }
                    onUserEarnedReward(it)
                }
            }else{
                Log.v("ADS_BONIFICADO","Error")
            }
        }
    }
}