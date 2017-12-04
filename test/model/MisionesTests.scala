package model

import org.junit.Before
import org.junit.Test
import org.junit.Assert

class MisionesTests {

  val aragorn = new Heroe().trabajo(Guerrero)
  val gandalf = new Heroe().trabajo(Mago)
  val gollum = new Heroe().trabajo(Ladron)
  val frodo = new Heroe().trabajo(Guerrero)
  val comunidadDelAnillo = new Equipo("Comunidad del anillo", 0, List(aragorn,gandalf, frodo, gollum))
  object anilloUnico extends Item(List(), List(_.valorStatPrincipal() > 50000), List(IncrementarStatsEnPorcentajeDePrincipal(1000)), 1000000)
  val argumentoDeLotr = new Mision(List(robarTalisman, forzarPuerta, pelearContraMonstruo), {_.obtenerItem(anilloUnico)})

  @Test
  def laFacilidadDeLaTareaForzarPuertaSeCalculaCorrectamente() {
    Assert.assertEquals(90+1*10,forzarPuerta.facilidad(aragorn, comunidadDelAnillo).get)
  }
  
  @Test
  def laFacilidadDeLaTareaPelearContraMonstruoSeCalculaCorrectamente() {
    Assert.assertEquals(10,pelearContraMonstruo.facilidad(aragorn, comunidadDelAnillo).get)
  }
  
  @Test
  def laTareaRobarTalismanNoTieneFacilidadPorqueNoSePuedeRealizar() {
    Assert.assertTrue(robarTalisman.facilidad(gollum, comunidadDelAnillo).isEmpty)
  }
  
  @Test
  def laComunidadDelAnilloCompletaParcialmenteElArgumentoDeLOTRPorqueNadiePuedeRobarTalisman() {
    Assert.assertTrue(argumentoDeLotr.serRealizadaPor(comunidadDelAnillo).isInstanceOf[ParcialmenteFallido])
  }
  
  @Test
  def unEquipoCompletaCorrectamenteUnaMisionYRecibeSuRecompensa() {
    val argumentoDeLotrReducido = new Mision(List(forzarPuerta, pelearContraMonstruo), {_.obtenerItem(anilloUnico)})
    Assert.assertEquals(1000000,argumentoDeLotrReducido.serRealizadaPor(comunidadDelAnillo).get.pozoComun)
  }
  
  @Test
  def unEquipoEntrenaExitosamente(){
    val equipoFinal = comunidadDelAnillo.entrenar(List(argumentoDeLotr), {(equipo1, equipo2) => equipo1.lider.get.valorStatPrincipal() > equipo2.lider.get.valorStatPrincipal()})
    Assert.assertEquals(120, equipoFinal.get.lider().get.valorStatPrincipal())
  }

}