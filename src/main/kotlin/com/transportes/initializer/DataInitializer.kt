package com.transportes.initializer

import com.transportes.domain.Vehiculo
import com.transportes.domain.documentos.Documento
import com.transportes.domain.enums.EstadosViaje
import com.transportes.domain.usuarios.Administrador
import com.transportes.domain.usuarios.Flota
import com.transportes.domain.usuarios.Unipersonal
import com.transportes.domain.viajes.Estado
import com.transportes.domain.viajes.Postulacion
import com.transportes.domain.viajes.Viaje
import com.transportes.domain.viajes.Dimensiones
import com.transportes.repositories.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class DataInitializer: InitializingBean {
    @Value("\${SPRING_PROFILES_ACTIVE}") lateinit var profile: String
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var repositorioUser: UsuarioRepository
    @Autowired lateinit var repositorioDocumento: DocumentoRepository
    @Autowired lateinit var repositorioVehiculo: VehiculoRepository
    @Autowired lateinit var repositorioEstado: EstadoRepository
    @Autowired lateinit var repositorioViaje: ViajeRepository
    @Autowired lateinit var repositorioPostulacion: PostulacionRepository

    // Usuarios
    var admin: Administrador? = null
    var flota: Flota? = null
    var flota2: Flota? = null
    var unipersonal1: Unipersonal? = null
    var unipersonal2: Unipersonal? = null
    var unipersonal3: Unipersonal? = null

    // Viajes
    var viaje1: Viaje? = null
    var viaje2: Viaje? = null
    var viaje3: Viaje? = null
    var viaje4: Viaje? = null
    var viaje5: Viaje? = null

    // Estados
    var estadoEnSubasta: Estado? = null
    var estadoConFletero: Estado? = null

    // Postulaciones
    var postulacion1: Postulacion? = null
    var postulacion2: Postulacion? = null
    var postulacion3: Postulacion? = null
    var postulacion4: Postulacion? = null

    // Vehiculos
    var vehiculo: Vehiculo? = null

    // Documentos
    var documentoFlota: Documento? = null
    var documentoUnipersonal: Documento? = null

    override fun afterPropertiesSet() {
        if (profile.equals("dev")) {
            inicializarUsers()
            inicializaDocumentos()
            inicializaVehiculos()
            inicializarEstados()
            inicializarViajes()
            inicializaPostulaciones()
            vincularViajesConPostulaciones()
            println("Datos inicializados")
        }
    }

    fun inicializarUsers() {
        admin = Administrador("admin@gmail.com", passwordEncoder.encode("admin"))
        flota = Flota("flota@gmail.com", passwordEncoder.encode("flota"), "Flota de Transporte", 1234567890)
        flota2 = Flota("flota2@gmail.com", passwordEncoder.encode("flota"), "Flota de Transporte", 1234567890)
        unipersonal1 = Unipersonal("unipersonal1@gmail.com", passwordEncoder.encode("unipersonal"), "Ignacio", "Herrera", 1234567890)
        unipersonal2 = Unipersonal("unipersonal2@gmail.com", passwordEncoder.encode("unipersonal"), "Tobias", "RichOne", 1234567891)
        unipersonal3 = Unipersonal("unipersonal3@gmail.com", passwordEncoder.encode("unipersonal"), "Lucas", "Morales", 1234567892)
        repositorioUser.save(admin!!)
        repositorioUser.save(flota!!)
        repositorioUser.save(flota2!!)
        repositorioUser.save(unipersonal1!!)
        repositorioUser.save(unipersonal2!!)
        repositorioUser.save(unipersonal3!!)
    }

    private fun inicializaVehiculos() {
        vehiculo = Vehiculo("IVECO", "v4", "GDF-654", "https://imgv2-1-f.scribdassets.com/img/document/435324717/original/1c73ae3a14/1?v=1", "https://imgv2-1-f.scribdassets.com/img/document/435324717/original/1c73ae3a14/1?v=1", unipersonal1!!)
        repositorioVehiculo.save(vehiculo!!)
    }

    private fun inicializaDocumentos() {
        documentoFlota = Documento(flota!!, "DNI", "", "https://s3aws.com/uhdeuijkednc")
        documentoUnipersonal = Documento(unipersonal1!!, "DNI", "", "https://s3aws.com/uhdeuijkednc")
        repositorioDocumento.save(documentoFlota!!)
        repositorioDocumento.save(documentoUnipersonal!!)
    }

    private fun inicializarEstados() {
        estadoEnSubasta = Estado(EstadosViaje.SUBASTA)
        estadoConFletero = Estado(EstadosViaje.ACORDADO)
        repositorioEstado.save(estadoEnSubasta!!)
        repositorioEstado.save(estadoConFletero!!)
    }

    private fun inicializarViajes() {
        viaje1 = Viaje(
            flota!!,
            null,
            estadoEnSubasta!!,
            "Buenos Aires",
            "Córdoba",
            LocalDateTime.now().plusDays(10),
            LocalDateTime.now().minusDays(4),
            1500.0,
            "Fragil",
            250.0,
            Dimensiones(2.0, 2.0, 2.0),
            "Viaje prueba 1"
        )
        viaje2 = Viaje(
            flota!!,
            null,
            estadoEnSubasta!!,
            "Buenos Aires",
            "Mendoza",
            LocalDateTime.now().plusDays(5),
            LocalDateTime.now(),
            700.0,
            "Seco",
            500.0,
            Dimensiones(2.0, 1.5, 5.0),
            "Viaje de prueba 2"
        )
        viaje3 = Viaje(
            flota2!!,
            null,
            estadoEnSubasta!!,
            "Buenos Aires",
            "Rosario",
            LocalDateTime.now().plusDays(3),
            LocalDateTime.now(),
            300.0,
            "Frio",
            300.0,
            Dimensiones(6.0, 2.5, 15.0),
            "Viaje de prueba 3",
        )
        viaje4 = Viaje(
            flota!!,
            null,
            estadoEnSubasta!!,
            "Buenos Aires",
            "La Plata",
            LocalDateTime.now().plusDays(1),
            LocalDateTime.now(),
            150.0,
            "Expuesta",
            100.0,
            Dimensiones(10.0, 5.0, 16.0),
            "Viaje de prueba 4",
        )
        viaje5 = Viaje(
            flota!!,
            null,
            estadoConFletero!!,
            "Buenos Aires",
            "Tucumán",
            LocalDateTime.now().plusDays(15),
            LocalDateTime.now(),
            1000.0,
            "Peligrosa",
            200.0,
            Dimensiones(8.0, 2.0, 20.0),
            "Viaje de prueba 5",
        )

        repositorioViaje.save(viaje1!!)
        repositorioViaje.save(viaje2!!)
        repositorioViaje.save(viaje3!!)
        repositorioViaje.save(viaje4!!)
        repositorioViaje.save(viaje5!!)
    }

    private fun inicializaPostulaciones() {
        postulacion1 = Postulacion(
            viaje1!!,
            unipersonal1!!,
            600.0
        )
        postulacion2 = Postulacion(
            viaje1!!,
            unipersonal2!!,
            1000.0
        )
        postulacion3 = Postulacion(
            viaje1!!,
            unipersonal3!!,
            500.0
        )
        postulacion4 = Postulacion(
            viaje5!!,
            unipersonal3!!,
            900.0
        )
        repositorioPostulacion.save(postulacion1!!)
        repositorioPostulacion.save(postulacion2!!)
        repositorioPostulacion.save(postulacion3!!)
        repositorioPostulacion.save(postulacion4!!)
    }

    private fun vincularViajesConPostulaciones() {
        viaje5!!.postulacionElegida = postulacion4
        repositorioViaje.save(viaje5!!)
    }
}