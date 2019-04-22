package td.Main

import td._
import scala.collection.mutable.Buffer
import scala.math._
import scala.util.Random

class Tower(val x: Double, val y: Double, val name: Int) {
  
  // Torni 1: Lyhyt kantama, nopea, matala vahinko
  // Torni 2: Laaja kantama, hidas, keskisuuri vahinko
  // Torni 3: Pitkä kantama suoraan tornista, keskinopea, kohtalainen vahinko
  // Torni 4: Kentänlaajuinen kantama, erittäin hidas, massiivinen vahinko
  //
  //    # # # # # # #    # # # # # # #    # # # O # # #
  //    # # # # # # #    # O O O O O #    # # # O # # #
  //    # # O O O # #    # O O O O O #    # # # O # # #
  //  1 # # O X O # #  2 # O O X O O #  3 O O O X O O O
  //    # # O O O # #    # O O O O O #    # # # O # # #
  //    # # # # # # #    # O O O O O #    # # # O # # #
  //    # # # # # # #    # # # # # # #    # # # O # # #
  private var cooldown = 0
  
  def passTime = cooldown -= 1
  def onCooldown = cooldown != 0
  
  // Laittaa tornin hyökkäksen tauolle.
  def setOnCooldown() = {
    cooldown = name match {
      case 1 => 50
      case 2 => 900
      case 3 => 400
      case 4 => new Random().nextInt(1000) + 1000
    }
  }
  def getCD = cooldown
  def resetCD = cooldown = 0
  
  // Palauttaa totuusarvon sen perusteella, onko vihollinen kantamalla.
  def inRange(e: Enemy, factor: Int): Boolean = {
    name match {
      case 1 => e.x >= factor * 2 && abs(e.x - this.x) <= factor * 3 && abs(e.y - this.y) <= factor * 3
      case 2 => e.x >= factor * 2 && abs(e.x - this.x) <= factor * 6 && abs(e.y - this.y) <= factor * 6
      case 3 => e.x >= factor * 2 && (abs(e.x - this.x) <= factor * 9 && abs(e.y - this.y) <= factor * 1) ||(abs(e.x - this.x) <= factor * 1 && abs(e.y - this.y) <= factor * 9)
      case 4 => e.x >= factor * 2
    }
  }
  // Hyökkää ensimmäiseen viholliseen, joka on kantamalla.
  def attack(enemies: Buffer[Enemy], factor: Int) = {
    val target = enemies.find( x => inRange(x, factor) && x.readyToMove )
    if (target.nonEmpty) {
      name match {
        case 1 => target.get.setHealth(target.get.getHealth - 10)
        case 2 => target.get.setHealth(target.get.getHealth - 35)
        case 3 => target.get.setHealth(target.get.getHealth - 25)
        case 4 => target.get.setHealth(target.get.getHealth - 90)
      }
      setOnCooldown()
    }
  }
}