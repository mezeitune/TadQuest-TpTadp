package model
import scala.util.{Try, Success, Failure}

class Mision(val tareas: List[Tarea] = List(), val recompensa: Recompensa) { //VER CÓMO MODELAR LAS RECOMPENSAS (!!!)
  
  def serRealizadaPor(equipo: Equipo): Try[Equipo] = {
    val equipoLuegoDeMision = tareas.foldLeft(Try(equipo)) {(equipo, tarea) =>
      equipo.flatMap(unEquipo => tarea.ejecutar(heroeQueVaARealizaTarea(unEquipo, tarea), unEquipo))
      }
    equipoLuegoDeMision.map(equipo => cobrarRecompensa(equipo))
  }
  
  def heroeQueVaARealizaTarea(equipo: Equipo, tarea: Tarea): Option[Heroe] = { //VER SI CONVIENE QUE ESTÉ EN LA TAREA
    equipo.mejorHeroeSegun{heroe=>
      tarea.facilidad(heroe, equipo).getOrElse(Int.MinValue)
    }
  }
  
  def cobrarRecompensa(equipo: Equipo) = recompensa.apply(equipo)
  
}