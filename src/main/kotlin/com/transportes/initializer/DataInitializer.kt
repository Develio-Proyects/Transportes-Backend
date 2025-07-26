package com.transportes.initializer

import com.transportes.domain.Truck
import com.transportes.domain.documents.Document
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.enums.CargoType
import com.transportes.domain.usuarios.Administrator
import com.transportes.domain.usuarios.Employee
import com.transportes.domain.usuarios.MultiCarrier
import com.transportes.domain.usuarios.SoloCarrier
import com.transportes.domain.viajes.Offer
import com.transportes.domain.viajes.Trip
import com.transportes.domain.viajes.Dimensions
import com.transportes.repositories.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class DataInitializer: InitializingBean {
    @Value("\${SPRING_PROFILES_ACTIVE}") lateinit var profile: String
    private val logger = LoggerFactory.getLogger("Data initializer")
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var employeeRepository: EmployeeRepository
    @Autowired lateinit var documentRepository: DocumentRepository
    @Autowired lateinit var truckRepository: TruckRepository
    @Autowired lateinit var tripRepository: TripRepository
    @Autowired lateinit var offerRepository: OfferRepository

    // USERS
    lateinit var admin: Administrator
    lateinit var multiCarrier1: MultiCarrier
    lateinit var multiCarrier2: MultiCarrier
    lateinit var employee: Employee
    lateinit var soloCarrier1: SoloCarrier
    lateinit var soloCarrier2: SoloCarrier
    lateinit var soloCarrier3: SoloCarrier

    // TRIPS
    lateinit var trip1: Trip
    lateinit var trip2: Trip
    lateinit var trip3: Trip
    lateinit var trip4: Trip
    lateinit var trip5: Trip

    // OFFERS
    lateinit var offer1: Offer
    lateinit var offer2: Offer
    lateinit var offer3: Offer
    lateinit var offer4: Offer

    // TRUCKS
    lateinit var truck: Truck

    // DOCUMENTS
    lateinit var soloCarrierDocument: Document
    lateinit var employeeDocument: Document

    override fun afterPropertiesSet() {
        if (profile == "dev") {
            setUsers()
            setDocuments()
            setTrucks()
            setTrips()
            setOffers()
            linkTripsWithOffers()
            logger.info("Data initialized successfully")
        }
    }

    fun setUsers() {
        admin = Administrator("admin@gmail.com", passwordEncoder.encode("1234"))
        multiCarrier1 = MultiCarrier("flota1@gmail.com", passwordEncoder.encode("1234"), "Herrera e hijos", 1234567890)
        multiCarrier2 = MultiCarrier("flota2@gmail.com", passwordEncoder.encode("1234"), "Transportistas SA", 1234567890)
        employee = Employee("Martin", "benedetto", multiCarrier1)
        soloCarrier1 = SoloCarrier("uni1@gmail.com", passwordEncoder.encode("1234"), "Ignacio", "Herrera", 1234567890)
        soloCarrier2 = SoloCarrier("uni2@gmail.com", passwordEncoder.encode("1234"), "Tobias", "Riccone", 1234567891)
        soloCarrier3 = SoloCarrier("uni3@gmail.com", passwordEncoder.encode("1234"), "Lucas", "Morales", 1234567892)

        userRepository.save(admin)
        userRepository.save(multiCarrier1)
        userRepository.save(multiCarrier2)
        employeeRepository.save(employee)
        userRepository.save(soloCarrier1)
        userRepository.save(soloCarrier2)
        userRepository.save(soloCarrier3)
    }

    private fun setTrucks() {
        truck = Truck("IVECO", "v4", "GDF-654", soloCarrier1)
        truckRepository.save(truck)
    }

    private fun setDocuments() {
        soloCarrierDocument = Document(soloCarrier1, null, "DNI", "https://s3aws.com/uhdeuijkednc")
        employeeDocument = Document(null, employee, "DNI", "https://s3aws.com/uhdeuijkednc")
        documentRepository.save(soloCarrierDocument)
        documentRepository.save(employeeDocument)
    }

    private fun setTrips() {
        trip1 = Trip(
            multiCarrier1,
            null,
            StateTrip.OPEN,
            "Buenos Aires",
            "Córdoba",
            LocalDateTime.now().plusDays(10),
            LocalDateTime.now().minusDays(4),
            1500.0,
            CargoType.PERISHABLE,
            250.0,
            Dimensions(2.0, 2.0, 2.0),
            "Viaje prueba 1"
        )
        trip2 = Trip(
            multiCarrier1,
            null,
            StateTrip.OPEN,
            "Buenos Aires",
            "Mendoza",
            LocalDateTime.now().plusDays(5),
            LocalDateTime.now(),
            700.0,
            CargoType.PERISHABLE,
            500.0,
            Dimensions(2.0, 1.5, 5.0),
            "Viaje de prueba 2"
        )
        trip3 = Trip(
            multiCarrier2,
            null,
            StateTrip.OPEN,
            "Buenos Aires",
            "Rosario",
            LocalDateTime.now().plusDays(3),
            LocalDateTime.now(),
            300.0,
            CargoType.PERISHABLE,
            300.0,
            Dimensions(6.0, 2.5, 15.0),
            "Viaje de prueba 3",
        )
        trip4 = Trip(
            multiCarrier1,
            null,
            StateTrip.OPEN,
            "Buenos Aires",
            "La Plata",
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now(),
            150.0,
            CargoType.FROZEN,
            100.0,
            Dimensions(10.0, 5.0, 16.0),
            "Viaje de prueba 4",
        )
        trip5 = Trip(
            multiCarrier1,
            null,
            StateTrip.ASSIGNED,
            "Buenos Aires",
            "Tucumán",
            LocalDateTime.now().plusDays(15),
            LocalDateTime.now(),
            1000.0,
            CargoType.DRY,
            200.0,
            Dimensions(8.0, 2.0, 20.0),
            "Viaje de prueba 5",
        )

        tripRepository.save(trip1)
        tripRepository.save(trip2)
        tripRepository.save(trip3)
        tripRepository.save(trip4)
        tripRepository.save(trip5)
    }

    private fun setOffers() {
        offer1 = Offer(
            trip1,
            soloCarrier1,
            600.0
        )
        offer2 = Offer(
            trip1,
            soloCarrier2,
            1000.0
        )
        offer3 = Offer(
            trip1,
            soloCarrier3,
            500.0
        )
        offer4 = Offer(
            trip5,
            soloCarrier3,
            900.0
        )
        offerRepository.save(offer1)
        offerRepository.save(offer2)
        offerRepository.save(offer3)
        offerRepository.save(offer4)
    }

    private fun linkTripsWithOffers() {
        trip5.chosenOffer = offer4
        tripRepository.save(trip5)
    }
}