package td

import java.io._
import td._
class Filemanager {
  
  def saveGame(file: File, game: Gamestate) = {
    
  }
  
  def loadGame(file: File): Gamestate = {
    val input = new BufferedReader(new FileReader(file))
    val game = new Gamestate()
    try {
      var fileEnded = false
      var rows = 0
      var cols = 0
      while(!fileEnded) {
        input.readLine() match {
          case "DATA:" => 
            val gameData = input.readLine()
            val gameScore = gameData.takeWhile(_ != ' ')
            val gameHealth = gameData.drop(gameScore.length + 1).takeWhile(_ != ' ')
            val gameLevel = gameData.drop(gameHealth.length + 1).drop(gameScore.length + 1).takeWhile(_ != ' ')
            
            game.setScore(gameScore.toInt)
            game.setPlayerHealth(gameHealth.toInt)
            game.setLevel(gameLevel.toInt)
            
          case "FIELD:" =>
            def squareType(in: String): Square = {
              in match {
                case "T" => new TowerSquare
                case "P" => new PathSquare
                case "B" => new BlockedSquare
              }
            }
            val fieldSize = input.readLine()
            val rows = fieldSize.takeWhile(_ != ' ')
            val cols = fieldSize.drop(rows.length + 1)
            val data = Array.ofDim[Square](rows.toInt, cols.toInt)
            for (x <- 0 until rows.toInt) {
              data(x) = input.readLine().split(" ").map(squareType)
            }
            game.setField(new Field(rows.toInt, cols.toInt, data))
          
          case "TOWERS:" =>
            var towers = List[Array[(Int, Int)]]()
            var tower1 = input.readLine().drop(2).trim
            if (tower1.nonEmpty) towers = towers :+ tower1.split(" ").map( x => (x.head.toInt ,x.last.toInt ))
            val tower2 = input.readLine().drop(2).trim
            if (tower2.nonEmpty) towers = towers :+ tower2.split(" ").map( x => (x.head.toInt ,x.last.toInt ))
            val tower3 = input.readLine().drop(2).trim
            if (tower3.nonEmpty) towers = towers :+ tower3.split(" ").map( x => (x.head.toInt ,x.last.toInt ))
            val tower4 = input.readLine().drop(2).trim
            if (tower4.nonEmpty) towers = towers :+ tower4.split(" ").map( x => (x.head.toInt ,x.last.toInt ))
            println(towers)
            var i = 1
            for (x <- towers) {
              for(y <- x) {
                game.getField.field(y._1-48)(y._2-48).setTower(i)
              }
              i += 1
            }
            
          case "END" => fileEnded = true
          case x => throw new IOException("Incorrect file syntax")
          
        }
      }
      
    } catch {
      case e: IOException => println("IOException caught: " + e)
      //case x: Throwable => println("Exception caught: " + x)
    } finally {
      input.close()
    }
    game
  }
  
  def newGame = {
    this.loadGame(new File("data/DefaultField.txt"))
  }
  
}