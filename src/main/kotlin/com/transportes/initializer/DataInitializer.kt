package com.transportes.initializer

import com.transportes.domain.Vehiculo
import com.transportes.domain.documentos.DocumentoFletero
import com.transportes.domain.documentos.DocumentoTransporte
import com.transportes.domain.usuarios.Administrador
import com.transportes.domain.usuarios.Fletero
import com.transportes.domain.usuarios.Transporte
import com.transportes.domain.viajes.Estado
import com.transportes.domain.viajes.Postulacion
import com.transportes.domain.viajes.Viaje
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

    var admin: Administrador? = null
    var transporte: Transporte? = null
    var fletero: Fletero? = null
    var vehiculo: Vehiculo? = null
    var documentoFletero: DocumentoFletero? = null
    var documentoTransporte: DocumentoTransporte? = null
    var estadoPublicado: Estado? = null
    var estadoConFletero: Estado? = null
    var viaje1: Viaje? = null
    var viaje2: Viaje? = null
    var viaje3: Viaje? = null
    var viaje4: Viaje? = null
    var viaje5: Viaje? = null
    var postulacion: Postulacion? = null

    override fun afterPropertiesSet() {
        if (profile.equals("dev")) {
            inicializarUsers()
            inicializaDocumentos()
            inicializaVehiculos()
            inicializarEstados()
            inicializarViajes()
            inicializaPostulaciones()
            println("Datos inicializados")
        }
    }

    fun inicializarUsers() {
        admin = Administrador("admin@gmail.com", passwordEncoder.encode("admin"))
        transporte = Transporte("transporte@gmail.com", passwordEncoder.encode("transporte"))
        fletero = Fletero("fletero@gmail.com", passwordEncoder.encode("fletero"))
        repositorioUser.save(admin!!)
        repositorioUser.save(transporte!!)
        repositorioUser.save(fletero!!)
    }

    private fun inicializaVehiculos() {
        vehiculo = Vehiculo("Chevrolet", fletero!!)
        repositorioVehiculo.save(vehiculo!!)
    }

    private fun inicializaDocumentos() {
        documentoFletero = DocumentoFletero("DNI", "", "https://s3aws.com/uhdeuijkednc", fletero!!)
        documentoTransporte = DocumentoTransporte("DNI", "", "https://s3aws.com/uhdeuijkednc", transporte!!)
        repositorioDocumento.save(documentoFletero!!)
        repositorioDocumento.save(documentoTransporte!!)
    }

    private fun inicializarEstados() {
        estadoPublicado = Estado("PUBLICADO")
        estadoConFletero = Estado("ACORDADO")
        repositorioEstado.save(estadoPublicado!!)
        repositorioEstado.save(estadoConFletero!!)
    }

    private fun inicializarViajes() {
        viaje1 = Viaje(
            transporte!!,
            null,
            estadoPublicado!!,
            "Buenos Aires",
            "Córdoba",
            LocalDateTime.now().plusDays(10),
            500.0,
            "Viaje de prueba",
        )
        viaje2 = Viaje(
            transporte!!,
            null,
            estadoPublicado!!,
            "Buenos Aires",
            "Mendoza",
            LocalDateTime.now().plusDays(5),
            700.0,
            "Viaje de prueba 2",
        )
        viaje3 = Viaje(
            transporte!!,
            null,
            estadoPublicado!!,
            "Buenos Aires",
            "Rosario",
            LocalDateTime.now().plusDays(3),
            300.0,
            "Viaje de prueba 3",
        )
        viaje4 = Viaje(
            transporte!!,
            null,
            estadoPublicado!!,
            "Buenos Aires",
            "La Plata",
            LocalDateTime.now().plusDays(1),
            150.0,
            "Viaje de prueba 4",
        )
        viaje5 = Viaje(
            transporte!!,
            null,
            estadoPublicado!!,
            "Buenos Aires",
            "Tucumán",
            LocalDateTime.now().plusDays(15),
            1000.0,
            "Viaje de prueba 5",
        )

        repositorioViaje.save(viaje1!!)
        repositorioViaje.save(viaje2!!)
        repositorioViaje.save(viaje3!!)
        repositorioViaje.save(viaje4!!)
        repositorioViaje.save(viaje5!!)
    }

    private fun inicializaPostulaciones() {
        postulacion = Postulacion(
            viaje1!!,
            fletero!!,
            600.0
        )
        repositorioPostulacion.save(postulacion!!)
    }
}