package model
import scala.util.Try

//FALTA AGREGAR MODIFICACIONES DE CADA TAREA (!!!)

abstract class Tarea(modificaciones: List[Modificacion]){

  def facilidad(heroe: Heroe, equipo: Equipo) : Option[Int]
  
  def ejecutar(heroe: Option[Heroe], equipo:Equipo): Try[Equipo] = {
    heroe match {
      case Some(heroePosta) => {
        val heroeModificado = heroePosta.aplicarModificaciones(modificaciones)
        val equipoActualizado = equipo.reemplazarMiembro(heroeModificado, heroePosta)
        Try(equipoActualizado)
      }
      case None => Try(throw new TareaFallidaError(this, equipo))
    }
  }
  
}

class TareaFallidaError(val tarea: Tarea, val equipo: Equipo) extends RuntimeException

object pelearContraMonstruo extends Tarea(List()){
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
        equipo.lider().map(_.trabajo match {
          case Guerrero           => 20
          case _                  => 10         
        })
    }
    
    
}

object forzarPuerta extends Tarea(List()){
 
    def facilidad(heroe: Heroe, equipo: Equipo) = {
       Some(heroe.getStat(Inteligencia) + equipo.cantidadMiembrosConTrabajo(Ladron)*10)
    }

}

object robarTalisman extends Tarea(List()){
  
    def facilidad(heroe: Heroe, equipo: Equipo) = {
        equipo.lider().flatMap(_.trabajo match {  //te deja un unico option //VERIFICAR QUE ANDE BIEN!!
          case Ladron            => Some(heroe.getStat(Velocidad))
          case _                 => None
        }) 
    }
  
}