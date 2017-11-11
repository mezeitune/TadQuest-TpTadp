package object model {
  type Modificacion = Heroe => Heroe
  type Restriccion = Heroe => Boolean
  type Recompensa = Equipo => Equipo
  type CriterioMision = (Equipo, Equipo) => Boolean
}