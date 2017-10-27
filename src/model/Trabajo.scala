package model

class Trabajo (val statPrincipal: String, val modificaciones: List[ModificacionStat]) {
  
}

object ninguno extends Trabajo("", List())