package td

import td._
import td.Main._

class Gamestate {
  val stageManager: Stagemanager = new Stagemanager()
  private var field:Field = null
  private var score = 0
  private var playerHealth = 100
  private var level = 1
  private var lost = false
  
  def setScore(a: Int): Unit = score.synchronized {score += a}
                                                 // Lisää parametrin arvon pelaajan pisteisiin.
  def setHealth(a: Int): Unit = playerHealth = a // Asettaa pelaajan elämäpisteet parametrin arvoksi.
  def setLevel(a: Int): Unit = level = a         // Asettaa pelaajan tason parametrin arvoksi.
  def setField(a: Field): Unit = field = a       // Asettaa kentän parametrin arvoksi.
  def setLost: Unit = lost = true                // Asettaa pelin häviötilan 'hävityksi'.
  def getField: Field = field                    // Palauttaa pelikentän.
  def getScore: Int = score                      // Palauttaa pelaajan pisteet.
  def getHealth: Int = playerHealth              // Palauttaa pelaajan elämäpisteet.
  def getLevel: Int = level                      // Palauttaa pelaajan tason.
  def getLost: Boolean = lost                    // Palauttaa pelin häviötilan.
  override def toString: String = {
    "Score: " + score + " Player Health: " + playerHealth + " Level: " + level
  }
}