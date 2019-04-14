package td.Main

import td._
import td.Main._
import scala.collection.mutable.Buffer

class Stagemanager {
  
  private val enemyWaves = Array[Array[Enemy]]()
  
  def determinePath(fieldWithPos: Array[(Array[(Square, Int)], Int)], factor: Int): Array[(Int, Int)] = {
    val pathSquaresWithPos = fieldWithPos.map(x => x._1.map(y => (y._1, y._2, x._2))).flatten.filter(x => x._1.letter == "P")
    pathSquaresWithPos.map(x => (x._2, x._3))
  }
  
  def nextStage(game: Gamestate, factor: Int) = {
    game.setLevel(game.getLevel + 1)
    
    spawnEnemies(game.getLevel, determinePath(game.getField.fieldWithPos, factor))
  }
  def spawnEnemies(level: Int, path: Array[(Int, Int)]) = {
    
  }
}