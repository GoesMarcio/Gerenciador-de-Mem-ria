import memory.Memory;
import memory.MemoryFix;
import memory.MemoryVar;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MainTerminal{

    static Scanner in;
    static Memory mem;

    public static void main(String [] args) throws InterruptedException{
        in  = new Scanner(System.in);
        
        System.out.println("Informe o tamanho da memoria. (tamanho equivalente a uma potencia de 2)");
        int memSize = in.nextInt();
        

        System.out.println("Informe o tipo de particao. ('fixa' ou 'variavel')");
        String partitionType = in.next();
        //partitionType = "variaveis";

        switch(partitionType){
            case "fixa":
                System.out.println("Informe o tamanho da particao.");
                int partSize = in.nextInt();
                mem = new MemoryFix(memSize, partSize);
                break;
            case "variavel":
                System.out.println("Informe o tipo de alocacao. ('best-fit', 'worst-fit', 'first-fit', 'next-fit')");
                String alocation = in.next();
                //String alocation = "next-fit";
                mem = new MemoryVar(memSize, alocation);
                break;
            default: break;
        }

        readFile("entrada.txt");
        in.close();
    }



    public static void readFile(String fileName) throws InterruptedException{
        try{
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);

            System.out.print("\t \t \t");
            System.out.println(mem.print());
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replaceAll(" ","");
                String [] split = data.split("[\\(\\)]");
                
                Thread.sleep(600);
                
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
            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("Erro: "+e);
            System.out.println("Arquivo nao encontrado");
        }
    }

    public static void optionIN(String data){
        System.out.print("IN " +data+"\t \t \t");
        String [] map = data.split(",");
        String key = map[0];
        int value = Integer.parseInt(map[1]);

        boolean i = mem.insert(key, value);
        String res = mem.print();
        System.out.print(res);
        if(!i) System.out.print(" ESPAÇO INSUFICIENTE DE MEMÓRIA!");
        System.out.println();
    }

    public static void optionOUT(String data){
        System.out.print("OUT "+data+"\t \t \t");

        mem.remove(data);
        String res = mem.print();
        System.out.println(res);
    }
}