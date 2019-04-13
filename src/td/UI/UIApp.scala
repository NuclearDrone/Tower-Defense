package td.UI

import td._
import td.Main._
import java.io._
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.MenuBar
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuItem
import scalafx.scene.control.SplitPane
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.TilePane
import scalafx.scene.layout.Background
import scalafx.scene.paint.Paint
import scalafx.scene.layout.CornerRadii
import scalafx.scene.layout.BackgroundFill
import javafx.event.EventHandler
import javafx.event.ActionEvent
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.StackPane
import scalafx.scene.text.Text
import scalafx.scene.text.Font
import scalafx.geometry.Pos
import scalafx.scene.layout.Border
import scala.collection.mutable.Buffer
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.scene.input.MouseEvent
import scalafx.animation.AnimationTimer
import scalafx.scene.control.ToggleButton
import scalafx.scene.control.ToggleGroup
import javafx.embed.swing.JFXPanel
import scalafx.application.Platform
import scalafx.stage.Stage

object UIApp extends JFXApp {
  
  private var game = new Filemanager().newGame
  private val factor = 25
  
  
  stage = new PrimaryStage {
    title = "Tornipuolustus"
    scene = new SceneWithParams(800, 600, game) {
      
      var tiles = Buffer[Tile]()
      
      // Käyttöliittymän elementit
      val menuBar = new MenuBar
      val gameMenu = new Menu("Game")
      val scoreMenu = new Menu("Score: " + game.getScore)
      val healthMenu = new Menu("Health: " + game.getHealth)
      val levelMenu = new Menu("Level: " + game.getLevel)
      val newGame = new MenuItem("New")
      val openGame = new MenuItem("Open")
      val saveGame = new MenuItem("Save")
      val quitGame = new MenuItem("Quit")
      val nextButton = new Button("Next round")
      val toggles = new ToggleGroup
      val towerButton1 = new ToggleButton("Tower 1")
      val towerButton2 = new ToggleButton("Tower 2")
      val towerButton3 = new ToggleButton("Tower 3")
      val towerButton4 = new ToggleButton("Tower 4")
      val buttonPane = new TilePane
      val gameWindow = new BorderPane
      val rootPane = new BorderPane
      val canvas = new Canvas(600, 600)
      val gc = canvas.graphicsContext2D
      
      //Tiedoston valitsija pelin tallennus- ja latausmetodeille
      val fileChooser = new FileChooser {
        title = "Choose a Game File"
        extensionFilters ++= Seq( new ExtensionFilter("Text Files", "*.txt"))
        initialDirectory = new File(System.getProperty("user.home"))
      }
      //Uuden pelin aloitus, pelin tallennus- ja lataus sekä poistumismetodien kutsut
      newGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          game = new Filemanager().newGame
        }
      }
      
      openGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          val file = fileChooser.showOpenDialog(stage)
          if (file != null) {
            game = new Filemanager().loadGame(file)
            gameWindow.getChildren().clear()
            gameWindow.getChildren().addAll(canvas)
            gc.clearRect(0,0,600, 600)
            placeTiles()
            
          }
        }
      }
      
      saveGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          val file = fileChooser.showSaveDialog(stage)
          if (file != null) {
            new Filemanager().saveGame(file, game, factor)
          }
        }
      }
      
      quitGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          stage.close()
        }
      }
      
      
      toggles.toggles = List(towerButton1, towerButton2, towerButton3, towerButton4)
      for (i <- 0 until toggles.toggles.length) {
        toggles.toggles(i).setUserData(i+1)
      }
      
      canvas.onMouseClicked = (me: MouseEvent) => {
        game.getField.enemies ::= new Enemy(me.x, me.y)
        println("" + me.x + " " + me.y )
      }
      
      //Animaatioajastin, joka uudelleenpiirtää viholliset ja tornit ruudulle muutosten tapahtuessa
      val timer = AnimationTimer { time =>
        gc.fill = Color.Black
        for (i <- game.getField.towers) {
          i.name match {
            case 1 =>
              gc.fill = Color.Aqua
              gc.fillRect(i.x-factor/2, i.y-factor/2, factor, factor)
            case 2 =>
              gc.fill = Color.Green
              gc.fillRect(i.x-factor/2, i.y-factor/2, factor, factor)
            case 3 =>
              gc.fill = Color.Purple
              gc.fillRect(i.x-factor/2, i.y-factor/2, factor, factor)
            case 4 =>
              gc.fill = Color.Silver
              gc.fillRect(i.x-factor/2, i.y-factor/2, factor, factor)
            case x => 
              gc.fill = Color.Black
              gc.fillRect(i.x-factor/2, i.y-factor/2, factor, factor)
          }
        }
        
        gc.fill = Color.PaleGoldrenrod
        for (i <- game.getField.enemies) {
          gc.fillOval(i.x-factor/2, i.y-factor/2, factor, factor)
        }
      }
      // Metodi joka muuttaa pelikentän ruudut käyttöliittymän Tile-elementeiksi. Ne myös sisältävät datan niissä jo olevista torneista.
      def placeTiles() = {
        tiles.clear()
        for {x <- 0 until game.getField.rows
             y <- 0 until game.getField.cols} {
           val tile = new Tile(game.getField.field(x)(y))
           val letter = game.getField.field(x)(y).letter
           tile.translateX = factor + y*factor*2
           tile.translateY = factor + x*factor*2
           if (tile.tower != 0) {
             val tower = new Tower(tile.translateX(), tile.translateY(), tile.tower)
             game.getField.towers ::= tower
           }
           tile.onMouseClicked = (me: MouseEvent) => {
             if(letter == "T" && toggles.selectedToggle.isNotNull()()) {
               val tower = new Tower(tile.translateX(), tile.translateY(), (toggles.selectedToggleProperty().get().getUserData + "").toInt)
               game.getField.towers ::= tower
             }
           }  
           tiles += tile
        }
        tiles.foreach(gameWindow.getChildren.addAll(_))
      }
      placeTiles()
      gameMenu.items = List(newGame, openGame, saveGame, quitGame)
      menuBar.menus = List(gameMenu, scoreMenu, healthMenu, levelMenu)
      nextButton.minWidth(100)
      buttonPane.layoutX = 200
      buttonPane.children += nextButton
      buttonPane.children += towerButton1
      buttonPane.children += towerButton2
      buttonPane.children += towerButton3
      buttonPane.children += towerButton4
      buttonPane.setBackground(new Background(Array(new BackgroundFill(Color.Gray, CornerRadii.Empty, Insets.Empty))))
      canvas.width.bind(gameWindow.width)
      canvas.height.bind(gameWindow.height)      
      gameWindow.getChildren.addAll(canvas)
      rootPane.top = menuBar
      rootPane.left = buttonPane
      rootPane.center = gameWindow
      canvas.toFront()
      canvas.setMouseTransparent(true)
      root = rootPane
      
      timer.start()
      
    }
  }
  private class Tile(tileType: Square) extends StackPane {
    var tower = tileType.tower
    style =  "" +
    "-fx-background-color: " + tileType.color + ";" +
    "-fx-border-color: " + tileType.color + ";" +
    "-fx-border-width: 1;" +
    "-fx-border-radius: 6;" +
    "-fx-padding: 10;"
    alignment = Pos.CENTER
    val text = new Text(tileType.letter)
    text.setFont(Font.font(30))
    val image = new ImageView(new Image( new FileInputStream(tileType.letter match {
      case "P" => "data/Ground_01.png"
      case "T" => "data/Grass_01.png"
      case "B" => "data/Wall_01.png"
    }), factor*2, factor*2, true, true))
    this.children.addAll(text, image)
  }
  
  private class SceneWithParams(width: Int, length: Int, game: Gamestate) extends Scene(width, length)
  
  
  
}