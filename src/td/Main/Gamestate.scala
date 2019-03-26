package td

import td._

class Gamestate {
  private var field:Field = null
  private var score = 0
  private var playerHealth = 100
  private var level = 1
  
  def setScore(a: Int) = score = a
  def setPlayerHealth(a: Int) = playerHealth = a
  def setLevel(a: Int) = level = a
  def setField(a: Field) = field = a
  
  override def toString = {
    "Score: " + score + " Player Health: " + playerHealth + " Level: " + level
  }
}