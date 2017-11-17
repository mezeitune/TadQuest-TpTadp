package model
import scala.util.{Try, Success, Failure}

//FALTA AGREGAR MODIFICACIONES DE CADA TAREA (!!!)

abstract class Tarea(modificaciones: List[Modificacion]) {

  def facilidad(heroe: Heroe, equipo: Equipo): Option[Int]

  def realizarsePor(equipo: Equipo): Try[Equipo] = {
    def heroeQueVaARealizarla(equipo: Equipo): Option[Heroe] = {
      equipo.mejorHeroeSegun { heroe => facilidad(heroe, equipo).getOrElse(Int.MinValue) }
        .flatMap { heroe => if (facilidad(heroe, equipo).isDefined) Some(heroe) else None }//En caso de que ninguno pueda realizarla
    }
    heroeQueVaARealizarla(equipo).fold[Try[Equipo]](Failure(new TareaFallidaError(this, equipo))) { heroePosta =>
      val heroeModificado = heroePosta.aplicarModificaciones(modificaciones)
      val equipoActualizado = equipo.reemplazarMiembro(heroeModificado, heroePosta)
      Success(equipoActualizado)
    }

    //Otra forma:
    //    heroeQueVaARealizarla(equipo).map { heroePosta =>
    //      val heroeModificado = heroePosta.aplicarModificaciones(modificaciones)
    //      val equipoActualizado = equipo.reemplazarMiembro(heroeModificado, heroePosta)
    //      Success(equipoActualizado)
    //    }.getOrElse(Failure(new TareaFallidaError(this, equipo)))
  }

}

class TareaFallidaError(val tarea: Tarea, val equipo: Equipo) extends RuntimeException

object pelearContraMonstruo extends Tarea(List()) {

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    equipo.lider().map(_.trabajo match {
      case Guerrero => 20
      case _ => 10
    })
  }

}

object forzarPuerta extends Tarea(List()) {

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    Some(heroe.getStat(Inteligencia) + equipo.cantidadMiembrosConTrabajo(Ladron) * 10)
  }

}

object robarTalisman extends Tarea(List()) {

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    equipo.lider().flatMap(_.trabajo match { //te deja un unico option //VERIFICAR QUE ANDE BIEN!!
      case Ladron => Some(heroe.getStat(Velocidad))
      case _ => None
    })
  }

}