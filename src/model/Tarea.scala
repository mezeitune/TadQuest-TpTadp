package model
import scala.util.{Try, Success, Failure}

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
  }

}

class TareaFallidaError(val tarea: Tarea, val equipo: Equipo) extends RuntimeException

object pelearContraMonstruo extends Tarea(List(VariarStatEnSi(HP, (-4), {_.getStat(Fuerza)<20}))) {

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    equipo.lider().map(_.trabajo match {
      case Some(Guerrero) => 20
      case _ => 10
    })
  }

}

object forzarPuerta extends Tarea(List(VariarStatEnSi(Fuerza, 1, {heroe => !heroe.tieneTrabajo(Ladron) && !heroe.tieneTrabajo(Mago)}),
    VariarStatEnSi(HP, (-5), {heroe => !heroe.tieneTrabajo(Ladron) && !heroe.tieneTrabajo(Mago)}))) {//NO ESTA BUENO REPETIR LA CONDICIÓN

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    Some(heroe.getStat(Inteligencia) + equipo.cantidadMiembrosConTrabajo(Ladron) * 10)
  }

}

object robarTalisman extends Tarea(List(AgregarItem(TalismanDelMinimalismo))) {

  def facilidad(heroe: Heroe, equipo: Equipo) = {
    equipo.lider().flatMap(_.trabajo match {
      case Some(Ladron) => Some(heroe.getStat(Velocidad))
      case _ => None
    })
  }

}