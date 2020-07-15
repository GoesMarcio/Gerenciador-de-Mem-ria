import memory.Memory;
import memory.MemoryFix;
import memory.MemoryVar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import java.util.Scanner;

public class FXMLHomeController implements Initializable {

    private Memory mem;
    private int memSize, partSize;
    private String alocation;
    private String[] dataNextStep;
    private int indexNextStep;

    @FXML
    private Button init;

    @FXML
    private Button btn_exit;

    @FXML
    private Pane simulation;

    @FXML
    private Button back;

    @FXML
    private TextArea text_in;

    @FXML
    private Label title;

    @FXML
    private TextArea text_out;

    @FXML
    private Pane home;

    @FXML
    private Pane barra;

    @FXML
    private TextArea text_mem;

    @FXML
    private Button btn_execute;

    @FXML
    private Button btn_file;

    @FXML
    private Button config;

    @FXML
    private Button btn_nextStep;

    @FXML
    private Button clearMem;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init.setDisable(true);
    }


    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void configMemory(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Escolha o tamanho da memória");
        dialog.setHeaderText("Memória");
        dialog.setContentText("Informe o tamanho da memória: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            memSize = Integer.parseInt(result.get());
        }


        List<String> choices = new ArrayList<>();
        choices.add("Fixa");
        choices.add("Variável");

        ChoiceDialog<String> dialog2 = new ChoiceDialog<>("Fixa", choices);
        dialog2.setTitle("Selecione o tipo de partição da memória");
        dialog2.setHeaderText("Tipo de partição");
        dialog2.setContentText("Escolha o tipo de partição: ");

        Optional<String> result2 = dialog2.showAndWait();
        if (result2.isPresent()) {
            String partitionType = result2.get();

            if(partitionType.equals("Fixa")){
                TextInputDialog dialog3 = new TextInputDialog("");
                dialog3.setTitle("Escolha o tamanho das partições");
                dialog3.setHeaderText("Partição");
                dialog3.setContentText("Informe o tamanho das partições da memória: ");
        
                Optional<String> result3 = dialog3.showAndWait();
                if (result3.isPresent()) {
                    partSize = Integer.parseInt(result3.get());

                    mem = new MemoryFix(memSize, partSize);
                    init.setDisable(false);
                }
            }else{
                List<String> choices2 = new ArrayList<>();
                choices2.add("best-fit");
                choices2.add("worst-fit");
                choices2.add("first-fit");
                choices2.add("next-fit");

                ChoiceDialog<String> dialog4 = new ChoiceDialog<>("best-fit", choices2);
                dialog4.setTitle("Selecione a política de alocacao");
                dialog4.setHeaderText("Política de alocação");
                dialog4.setContentText("Escolha a política de alocação: ");

                Optional<String> result4 = dialog4.showAndWait();
                if (result4.isPresent()) {
                    alocation = result4.get();
                    mem = new MemoryVar(memSize, alocation);
                    init.setDisable(false);
                }
            }

        }
    }

    @FXML
    void start(ActionEvent event) {
        simulation.toFront();
        text_out.setEditable(false);
        text_mem.setEditable(false);

        reloadMemory();
    }

    @FXML
    void execute(ActionEvent event) {
        if(!text_in.editableProperty().getValue()){
            mem.clear();
            text_out.clear();
            text_in.clear();
            for(int i = 0; i < dataNextStep.length; i++){
                text_in.setText(text_in.getText() + dataNextStep[i] + "\n");
            }
            text_in.setEditable(true);
        }
        
        String text = text_in.getText();
        String data[] = text.split("\\n");
        
        String res = "\t\t " + mem.print();
        text_out.setText(text_out.getText() + res+"\n");
        
        for(int i = 0; i<data.length; i++){
            if(!data[i].equals("")){
                String [] split = data[i].split("[\\(\\)]");

                switch(split[0]){
                    case "IN":
                        optionIN(split[1]);
                        break;
                    case "OUT":
                        optionOUT(split[1]);
                        break; 
                    default: break;
                }

            }
            
        }
        
    }

    void reloadMemory(){
        text_mem.clear();
        if(mem instanceof MemoryFix){
            text_mem.setText("Memória de partição Fixa\nPartição de tamanho: " + this.partSize + "\n");
        } else {
            text_mem.setText("Memória de partição Variável\nPolítica de alocação: " + this.alocation + "\n");
        }
        text_mem.setText(text_mem.getText() + "Espaço da memória: "+ mem.size()+"\n");
        text_mem.setText(text_mem.getText() + "Espaço livre: "+ mem.free()+"\n");
        text_mem.setText(text_mem.getText() + "Espaço usado: "+ mem.used()+"\n");
        text_mem.setText(text_mem.getText() + "Memória:\n "+ mem.printMem()+"\n");
    }

    @FXML
    void selectFile(ActionEvent event) throws IOException{
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Texto files (*.txt)", "*.txt");

            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("INFORMAÇÃO");
                alert.setHeaderText(null);
                alert.setContentText("Arquivo carregado com sucesso!");
                alert.showAndWait();
                
                Scanner myReader = new Scanner(file);
                String data = "";
                while (myReader.hasNextLine()) {
                    data += myReader.nextLine()+"\n";
                    data = data.replaceAll(" ","");
                }

                text_in.setText(data);
                myReader.close();

            }
        }catch(IOException e){
            Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("INFORMAÇÃO");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao carregar arquivo!");
                alert.showAndWait();
        }
    }

    @FXML
    void nextStep(ActionEvent event) {
        if(text_in.editableProperty().getValue()){
            text_in.setEditable(false);
            String text = text_in.getText();
            dataNextStep = text.split("\\n");
            indexNextStep = 0;
            String res = "\t\t " + mem.print();
            text_out.setText(text_out.getText() + res+"\n");
        }
        if(!dataNextStep[indexNextStep].equals("")){
            String [] split = dataNextStep[indexNextStep].split("[\\(\\)]");
            
            switch(split[0]){
                case "IN":
                    optionIN(split[1]);
                    break;
                case "OUT":
                    optionOUT(split[1]);
                    break; 
                default: break;
            }
            indexNextStep++;
        }
        text_in.clear();
        for(int i = 0; i < dataNextStep.length; i++){
            String aux = dataNextStep[i];
            if(i == indexNextStep-1 && i < dataNextStep.length-1) 
                aux = aux + " <--";

            text_in.setText(text_in.getText() + aux + "\n");
        }
        if(indexNextStep >= dataNextStep.length){
            text_in.setEditable(true);
        }
    }

    @FXML
    void backHome(ActionEvent event) {
        text_in.clear();
        text_out.clear();
        text_mem.clear();
        home.toFront();
    }

    @FXML
    void clearMemory(ActionEvent event) {
        mem.clear();
        reloadMemory();
        text_out.clear();
        if(dataNextStep != null){
            text_in.clear();
            for(int i = 0; i < dataNextStep.length; i++){
                text_in.setText(text_in.getText() + dataNextStep[i] + "\n");
            }
        }
        text_in.setEditable(true);
    }

    private void optionIN(String data){
        data = data.replaceAll(" ","");
        String [] map = data.split(",");

        String key = map[0];
        int value = Integer.parseInt(map[1]);

        boolean insert = mem.insert(key, value);
        String res = "IN " +data+"\t " + mem.print();
        if(!insert){
            res = "IN " +data+"\t " + mem.print()+ " - ESPAÇO INSUFICIENTE DE MEMÓRIA";
        }
        text_out.setText(text_out.getText() + res+"\n");
        reloadMemory();
    }

    private void optionOUT(String data){
        data = data.replaceAll(" ","");
        mem.remove(data);
        String res = "OUT " +data+"\t " + mem.print();
        text_out.setText(text_out.getText() + res+"\n");
        reloadMemory();
    }

}
