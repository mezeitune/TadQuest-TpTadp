package model

class Trabajo (val statPrincipal: Stat, val modificaciones: List[ModificacionStat]) {
  
}

case object Guerrero extends Trabajo(Fuerza, List())
case object Ladron extends Trabajo(Velocidad,List())
case object Mago extends Trabajo(Inteligencia,List())
case object Ninguno extends Trabajo(null, List())