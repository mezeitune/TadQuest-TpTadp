package object model {
  type Restriccion = Heroe => Boolean
  type Recompensa = Equipo => Equipo
  type CriterioMision = (Equipo, Equipo) => Boolean
}