package com.example.share2connect.Utils

import com.example.share2connect.Components.*

enum class AdvertEnum(val key: String, val type: Class<BaseComponentClass>) {

    GroupEvent("E001", E_001().javaClass),
    ClubEvent("E002", E_002().javaClass),
    RoadEvent("E003", E_003().javaClass),
    HomeEvent("E004", E_004().javaClass),
    StudyEvent("E005", E_006().javaClass),
    ConcertEvent("E006",E_005().javaClass),
    addOn("addOn",EditCard().javaClass);

    //  OtherEvent("E007",E_007().javaClass);



    companion object {
        fun get(
            key: String? = null,
            tempKey: String? = null,
            name: String? = null,
            type: BaseComponentClass? = null
        ): AdvertEnum? {
            key?.let { it1 ->
                return values().find { it.key == it1 }
            }

            name?.let { it1 ->
                return values().find { it.name == it1 }
            }
            type?.let { it1 ->
                return values().find { it.type == it1 }
            }
            return null
        }
    }

}