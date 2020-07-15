package memory;

public interface Memory{
    public boolean insert(String data, int size);
    public void remove(String data);
    public void clear();
    public String print();
    public String printMem();
    public int size();
    public int used();
    public int free();
}