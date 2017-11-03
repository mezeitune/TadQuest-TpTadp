package model

import org.junit.Before
import org.junit.Test
import org.junit.Assert

class HeroesTests {
  
  var heroe: Heroe = null
  var mago: Heroe = null
  var espada: Item = null
  var lanza: Item = null
  
  @Before
  def setup(){
    heroe = new Heroe().trabajo(Guerrero)
    mago = new Heroe().trabajo(Mago)
    espada = new Item(List(Mano), List((heroe => heroe.stats.get(Fuerza).get > 50)), List(VariarStatEn(Fuerza,100)), 200)
    lanza = new Item(List(Mano), List((heroe => heroe.stats.get(Fuerza).get > 200)), List(), 250)
  }
  
  //Tests: Punto 1 -> Forjando un heroe
  
  @Test
  def heroeEquipaItemCorrectamente(){
    val heroeEquipado = heroe.equipar(espada)
    Assert.assertTrue(heroeEquipado.inventario.contains(espada))
  }
   
  @Test
  def heroeNoPuedeEquiparItemPorRestriccion(){
    val heroeEquipado = heroe.equipar(lanza)
    Assert.assertFalse(heroeEquipado.inventario.contains(lanza))
  }
  
  @Test
  def heroeEquipaEspadaQueAumentaFuerza(){
    val heroeEquipado = heroe.equipar(espada)
    Assert.assertEquals(215, heroeEquipado.getStat(Fuerza))
  }
  
  @Test
  def heroeCambiaDeTrabajo(){
    val heroeNuevoTrabajo = heroe.trabajo(Mago)
    Assert.assertEquals(Mago, heroeNuevoTrabajo.trabajo)
  }
  
  @Test
  def elStatPrincipalDelMagoEs120DeInteligencia(){
    Assert.assertEquals(120, mago.valorStatPrincipal())
  }
  
  @Test
  def HeroeDesequipaItemCorrectamente(){
    val heroeEquipado = heroe.equipar(espada)
    val heroeDesequipado = heroeEquipado.desequipar(espada)
    Assert.assertFalse(heroeDesequipado.inventario.contains(espada))
  }
  
  @Test
  def elStatBaseDelHeroeAumentaCorrectamente(){
    val fuerzaOriginal = heroe.getStat(Fuerza)
    val heroeConMayorStat = heroe.incrementarStatBase(Fuerza, 100)
    Assert.assertEquals(fuerzaOriginal+100,heroeConMayorStat.getStat(Fuerza))
  }
  
  @Test
  def seVariaLaFuerzaEn100YSeIncrementanStatsEnPorcentajeDePrincipalPor50Correctamente(){
    val modificaciones: List[Modificacion] =  List(VariarStatEn(Fuerza,100),IncrementarStatsEnPorcentajeDePrincipal(50))
    val heroeModificado = heroe.aplicarModificaciones(modificaciones)
    Assert.assertEquals(5965,heroeModificado.getStat(Fuerza))
  }
  
}