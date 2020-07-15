import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.lang.Exception;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GerenciadorMemoria extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("assets/project.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage.getIcons().add(new Image(this.getClass().getResourceAsStream("images/m.png")));
            stage.show();
        }catch(Exception e){
            System.out.println("Erro ao iniciar programa");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
