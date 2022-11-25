package com.example.share2connect.Utils

import com.example.share2connect.Models.BaseComponent

enum class AdvertEnum(val key: String,  val type: Class<BaseComponent>) {

         GroupEvent("E001",E_001().javaClass),
         ClubEvent("E002",E_002().javaClass),
         RoadEvent("E003",E_003().javaClass),
         HomeEvent("E004",E_004().javaClass),
         ConcertEvent("E005",E_005().javaClass),
         StudyEvent("E006",E_006().javaClass),
         OtherEvent("E007",E_007().javaClass);

}