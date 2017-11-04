package model
import scala.util.{Try, Success, Failure}

class Mision(val tareas: List[Tarea] = List(), val recompensa: Recompensa) {
  
  def serRealizadaPor(equipo: Equipo): Try[Equipo] = {
    val equipoLuegoDeMision = tareas.foldLeft(Try(equipo)) {(equipo, tarea) =>
      equipo.flatMap(unEquipo => tarea.realizarsePor(unEquipo))
      }
    equipoLuegoDeMision.map(equipo => cobrarRecompensa(equipo))
  }
  
  def cobrarRecompensa(equipo: Equipo) = recompensa.apply(equipo)
  
}