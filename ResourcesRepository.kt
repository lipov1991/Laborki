package pl.krzysztof.lipka.laborki.data

import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.data.model.Resource
import pl.krzysztof.lipka.laborki.data.model.ResourceType
import javax.inject.Inject
import javax.inject.Singleton

interface ResourcesRepository {

    fun getResources(): List<Resource>
}

@Singleton
class ResourcesRepositoryImpl @Inject constructor() : ResourcesRepository {

    override fun getResources() = listOf(
        Resource(
            1L,
            "M1 Abrams",
            "Silnik turbowałowy Avco Lycoming AGT-1500C o mocy 1103 kW (1500 KM) przy 3000 obr./min. Zasięg: 498 km.",
            R.drawable.czolg,
            R.drawable.m1_abrams,
            ResourceType.LAND_FORCES,
            true
        ),
        Resource(
            2L,
            "M270 Multiple Launch Rocket System",
            "Trakcja gąsienicowa. Zasięg: 483 km",
            R.drawable.wyrzutnia,
            R.drawable.m270,
            ResourceType.LAND_FORCES,
            false
        ),
        Resource(
            3L,
            "USS „Abraham Lincoln”",
            "Lotniskowiec. Prędkość: 30 węzłów.",
            R.drawable.okret_wojenny,
            R.drawable.uss_abraham_lincoln,
            ResourceType.MARINE_FLEET,
            false
        ),
        Resource(
            4L,
            "Okręt podwodny typu Ohio",
            "Wyrzutnie torpedowe: dziobowe, rufowe. Wyrzutnie rakietowe: SSBN: 24 SLBM, SSGN (154 komory startowe VLS).",
            R.drawable.lodz_podwodna,
            R.drawable.ohio,
            ResourceType.MARINE_FLEET,
            false
        ),
        Resource(
            5L,
            "Boeing AH-64 Apache",
            "2 silniki turbowałowe T700-GE-701. Moc: 1265 kW.",
            R.drawable.helikopter,
            R.drawable.ah_64,
            ResourceType.LAND_FORCES,
            false
        )
    )
}
