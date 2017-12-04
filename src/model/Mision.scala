package model
import scala.util.{Try, Success, Failure}

abstract class EstadoEquipo (equipo: Equipo){
  def realizar(tarea: Tarea): EstadoEquipo
  def flatMap(f: Equipo => EstadoEquipo): EstadoEquipo = f(equipo)//PUEDE CAMBIAR LIBREMENTE DE ESTADO (!!!)
  def map(f: Equipo => Equipo): EstadoEquipo
  def get = equipo
  def isSuccess = true
}

case class SinFallos(equipo: Equipo) extends EstadoEquipo(equipo){
  def realizar(tarea: Tarea) = tarea.realizarsePor(equipo).map{ e => SinFallos(e) }.getOrElse(ParcialmenteFallido(equipo, tarea))
  def map(f: Equipo => Equipo) = copy(equipo = f(equipo))
}

case class ParcialmenteFallido(equipo: Equipo, tareaFallada: Tarea) extends EstadoEquipo(equipo){
  def realizar(tarea: Tarea): EstadoEquipo = tarea.realizarsePor(equipo).map{ e => ParcialmenteFallido(e,tareaFallada) }
    .getOrElse(Fallido(equipo, tarea))
  def map(f: Equipo => Equipo) = copy(equipo = f(equipo))
}

case class Fallido(equipo: Equipo, tareaFallada: Tarea) extends EstadoEquipo(equipo){
  def realizar(tarea: Tarea) = this
  override def flatMap(f: Equipo => EstadoEquipo) = this
  def map(f: Equipo => Equipo) = this
  override def isSuccess = false
}

class Mision(val tareas: List[Tarea] = List(), val recompensa: Recompensa) {
  
  def serRealizadaPor(equipo: Equipo): EstadoEquipo = {
    val equipoLuegoDeMision = tareas.foldLeft(SinFallos(equipo): EstadoEquipo) {(equipo, tarea) =>
      equipo.realizar(tarea)
      }
    equipoLuegoDeMision.map(equipo => cobrarRecompensa(equipo))
  }
  
  def cobrarRecompensa(equipo: Equipo) = recompensa.apply(equipo)
  
}