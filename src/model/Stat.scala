package model

sealed trait Stat

case object HP extends Stat
case object Velocidad extends Stat
case object Fuerza extends Stat
case object Inteligencia extends Stat


sealed trait Slot

case object Cabeza extends Slot
case object Torso extends Slot
case object ManoIzq extends Slot
case object ManoDer extends Slot
