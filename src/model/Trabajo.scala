package model

class Trabajo (val statPrincipal: Stat, val modificaciones: List[ModificacionStat])

case object Guerrero extends Trabajo(Fuerza, List(VariarStatEn(HP,10), VariarStatEn(Fuerza,15), VariarStatEn(Inteligencia,-10)))
case object Mago extends Trabajo(Inteligencia, List(VariarStatEn(Inteligencia,20), VariarStatEn(Fuerza,-20)))
case object Ladron extends Trabajo(Velocidad, List(VariarStatEn(Velocidad,10), VariarStatEn(HP,-5)))