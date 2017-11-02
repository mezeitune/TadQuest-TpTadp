package model

import org.junit.Before
import org.junit.Test
import org.junit.Assert

class HeroesTests {
  
  var heroe: Heroe = null
  var espada: Item = null
  var lanza: Item = null
  
  @Before
  def setup(){
    heroe = new Heroe().trabajo(Guerrero)
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
  
}