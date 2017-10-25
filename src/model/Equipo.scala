package model

case class Equipo(val nombre:String, val pozoComun: Int = 0, val heroes: List[Heroe] = List()) {
  
  def mejorHeroeSegun(criterio: Heroe => Int) = heroes.sortWith((heroe1, heroe2) => criterio.apply(heroe1) >= criterio.apply(heroe2)).headOption
  
  def lider() = mejorHeroeSegun {_.valorStatPrincipal()}
  
  def obtenerItem(item: Item) = {} // PENDIENTE (!!!)
  
  def obtenerMiembro(nuevoMiembro: Heroe) = copy(heroes = heroes ++ List(nuevoMiembro))
  
  def reemplazarMiembro(nuevoMiembro: Heroe, heroeAEliminar: Heroe) = copy(heroes = heroes.diff(List(heroeAEliminar)) ++ List(nuevoMiembro))
  
}