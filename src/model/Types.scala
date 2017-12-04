package object model {
  type Restriccion = Heroe => Boolean
  type Recompensa = Equipo => Equipo
  type TablonDeAnuncios = List[Mision]
  type CriterioMision = (Equipo, Equipo) => Boolean
}