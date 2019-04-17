package pl.krzysztof.lipka.laborki.data

import javax.inject.Inject

interface CoordinatesRepository {

    var longitude: String?
    var latitude: String?
    var recipientEmail: String?
}

class CoordinatesRepositoryImpl @Inject constructor() : CoordinatesRepository {

    override var longitude: String? = null
    override var latitude: String? = null
    override var recipientEmail: String? = null
}
