package td

import td._

class Gamestate {
  private var field:Field = null
  private var score = 0
  private var playerHealth = 100
  private var level = 1
  
  def setScore(a: Int): Unit = score = a
  def setPlayerHealth(a: Int): Unit = playerHealth = a
  def setLevel(a: Int): Unit = level = a
  def setField(a: Field): Unit = field = a
  def getField: Field = field
  def getScore: String = "Score: " + score
  def getHealth: String = "Player Health: " + playerHealth
  def getLevel: String = "Level: " + level
  
  override def toString: String = {
    "Score: " + score + " Player Health: " + playerHealth + " Level: " + level
  }
}