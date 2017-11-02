package model

import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import org.junit.Test
import org.junit.Assert

class HeroesTests {
  
  var heroe: Heroe = null
  var espada: Item = null
  var lanza: Item = null
  
  @Before
  def setup(){
    heroe = new Heroe()
    espada = new Item(List(Cabeza,Torso,Mano),List((heroe => heroe.stats.get(Fuerza).get > 50)),List(),200)
    lanza = new Item(List(Cabeza,Torso,Mano),List((heroe => heroe.stats.get(Fuerza).get > 200)),List(),200)
  }
  
  @Test
  def heroeEquipaItemCorrectamente() = {
    val heroeEquipado = heroe.equipar(espada)
    Assert.assertTrue(heroeEquipado.inventario.contains(espada))
  }
   
  @Test
  def heroeNoPuedeEquiparItemPorRestriccion(){
    val heroeEquipado = heroe.equipar(lanza)
    Assert.assertFalse(heroeEquipado.inventario.contains(lanza))
  }
  
}