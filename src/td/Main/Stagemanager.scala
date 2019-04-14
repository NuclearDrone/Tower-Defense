package td.Main

import td._
import td.Main._
import scala.collection.mutable.Buffer

class Stagemanager {
  
  private val enemyWaves = Array[Array[Enemy]]()
  
  def determinePath(fieldWithPos: Array[Array[(Square, Int, Int)]], factor: Int): Buffer[(Int, Int)] = {
    var path = Buffer[(Int, Int)]()
    val pathSquaresWithPos = fieldWithPos.flatten.filter(x => x._1.letter == "P")
    val pathSquareCoords = pathSquaresWithPos.map(x => (x._2, x._3))
    try {
      path += pathSquareCoords.find(x => x._1 == 0).getOrElse(throw new IllegalArgumentException("Field doesn't have a path in the left or rightmost column."))
     
      path += pathSquareCoords.find(x => x._1 == fieldWithPos(0).length - 1 ).getOrElse(throw new IllegalArgumentException("Field doesn't have a path in the left or rightmost column."))
    } catch {
      case e: Throwable => println(e)
    }
    path.map(x => (factor + x._1*factor*2, factor + x._2*factor*2))
  }
  
  def nextStage(game: Gamestate, factor: Int) = {
    game.setLevel(game.getLevel + 1)
    
    val temporary = spawnEnemies(game.getLevel, determinePath(game.getField.fieldWithPos, factor))
    game.getField.enemies = temporary
  }
  def spawnEnemies(level: Int, path: Buffer[(Int, Int)]) = {
    val enemyData = 0
    
    
    List[Enemy](new Enemy(path(0)._1, path(0)._2), new Enemy(path(1)._1, path(0)._2))
  }
}
object BattleHandler extends Runnable {
  def run() = {
    
  }
}