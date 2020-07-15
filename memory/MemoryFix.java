package memory;

public class MemoryFix implements Memory{
    private Node first;
    private int memSize;
    private int partSize;

    private static class Node{
        Node next;
        String key;
        int data;
    
        Node(String newKey, int newData){
            key = newKey;
            data = newData;
            next = null;
        }
    }

    public MemoryFix(int memSize, int partSize){
        first = null;
        this.memSize = memSize;
        this.partSize = partSize;
        for(int i=0; i<memSize/partSize; i++){
            insertNode("", partSize);
        }
    }
    
    private void insertNode(String key, int data){
        Node p = new Node(key, data);
        p.next = first;
        first = p;
    }

    public boolean insert(String key, int value){
        Node p = first;
        Boolean find = false;
        while ( p!= null ) {
            if(p.data>=value && p.key.equals("")){
                p.data = p.data - value;
                p.key = key;
                find = true;
                break;
            }
            p = p.next;
        }
        return find;
        
    }

    public void remove(String data){
        Node p = first;
        while ( p!= null ) {
            if(p.key.equals(data)){
                p.data = partSize;
                p.key = "";
            }
            p = p.next;
        }
    }
    
    public String print(){
        String res = "";
        Node p = first;
        int count;
        while ( p!= null ) {
        	count = 0;
        	Node aux = p;
        	if(aux.key.equals("")){
                Node n = p.next;
                count += p.data;
	        	while(n!=null && n.key.equals("")){
		            count+=n.data;
                    n = n.next;
                    p = p.next;
                }
                if(n!=null){
                    count += n.data;
                    p = p.next;
                }
	    	}

	    	if(!aux.key.equals("")){
                Node n = p.next;
                count += p.data;
                while(n!=null && n.key.equals("")){
                    count+=n.data;
                    n = n.next;
                    p = p.next;
		        }
            }
            if(count>0)
	    	    res += "["+count+"]";
            
            if(p == null) break;
            p = p.next;
        }
        

        // p = first;
        // while(p!=null){
        //     System.out.print("["+p.key+","+p.data+"]");
        //     p = p.next;
        // }
        // System.out.println();
        return res;
    }

    public String printMem(){
        String res = "";

        Node p = first;
        while(p!=null){
            res += "["+p.key+","+p.data+"]";
            p = p.next;
        }
        return res;
    }

    public void clear(){
        Node p = first;
        while(p!=null){
            p.key = "";
            p.data = partSize;
            p = p.next;
        }
    }

    public int size(){
        return this.memSize;
    }

    public int used(){
        int count = 0;
        Node p = first;
        while(p != null){
            if(!p.key.equals("")){
                count += this.partSize - p.data;
            }
            p = p.next;
        }

        return count;
    }

    public int free(){
        int count = 0;
        Node p = first;
        while(p != null){
            if(p.key.equals("")){
                count += p.data;
            }
            p = p.next;
        }

        return count;
    }

}