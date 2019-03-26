package td.UI

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
import td._
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.StackPane
import scalafx.scene.text.Text
import scalafx.scene.text.Font
import scalafx.geometry.Pos
import scalafx.scene.layout.Border

object UIApp extends JFXApp {
  
  
  
    
  stage = new PrimaryStage {
    title = "Tornipuolustus"
    scene = new Scene(800, 600) {
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Game")
      val newGame = new MenuItem("New")
      val openGame = new MenuItem("Open")
      val saveGame = new MenuItem("Save")
      val quitGame = new MenuItem("Quit")
      val fileChooser = new FileChooser {
        title = "Open Game File"
        extensionFilters ++= Seq( new ExtensionFilter("Text Files", "*.txt"))
        initialDirectory = new File(System.getProperty("user.home"))
      }
      newGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          new Filemanager().newGame
        }
      }
      openGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          val file = fileChooser.showOpenDialog(stage)
          if (file != null) {
            new Filemanager().loadGame(file)
          }
        }
      }
      saveGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          val file = fileChooser.showSaveDialog(stage)
          if (file != null) {
            new Filemanager().saveGame(file)
          }
        }
      }
      quitGame.onAction = new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent) {
          stage.close()
        }
      }
      gameMenu.items = List(newGame, openGame, saveGame, quitGame)
      menuBar.menus = List(gameMenu)
      
      val Button1 = new Button("Next round")
      val Button2 = new Button("Tower1")
      val Button3 = new Button("Tower2")
      val Button4 = new Button("Tower3")
      val Button5 = new Button("Tower4")
      val buttonPane = new TilePane
      val gameWindow = new BorderPane
      Button1.minWidth(100)
      buttonPane.layoutX = 200
      buttonPane.children += Button1
      buttonPane.children += Button2
      buttonPane.children += Button3
      buttonPane.children += Button4
      buttonPane.children += Button5
      buttonPane.setBackground(new Background(Array(new BackgroundFill(Color.Gray, CornerRadii.Empty, Insets.Empty))))
      
      val rootPane = new BorderPane
      rootPane.top = menuBar
      rootPane.left = buttonPane
      rootPane.center = gameWindow
      gameWindow.getChildren.addAll(new Tile(new TowerSquare))
      root = rootPane
    }
  }
  private class Tile(tileType: Square) extends StackPane {
    style =  "" +
    "-fx-background-color: " + tileType.color + ";" +
    "-fx-border-color: black;" +
    "-fx-border-width: 1;" +
    "-fx-border-radius: 6;" +
    "-fx-padding: 10;"
    alignment = Pos.CENTER
    val text = new Text(tileType.letter)
    text.setFont(Font.font(30))
    this.children.addAll(text)
  }
  
  
  
  
  
}