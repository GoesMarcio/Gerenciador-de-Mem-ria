package memory;

public class MemoryVar implements Memory{
    private Node first, last;
    private int memSize;
    private String alocation;

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

    public MemoryVar(int memSize, String alocation){
        first = last = new Node("", memSize);
        this.memSize = memSize;
        this.alocation = alocation;
    }
    
    public boolean insert(String key, int value) {
        switch(alocation){
            case "best-fit":
                return bestFit(key, value);

            case "worst-fit":
                return worstFit(key, value);

            case "first-fit":
                return firstFit(key, value);

            case "next-fit":
                return nextFit(key, value);

            default: break;
        }
        
        return false;
    }

    private boolean bestFit(String key, int value){
        //System.out.println("Best-fit");
        Node p = first;
        Node m = first;
        while(p != null){
            if(p.key.equals("")){
                m = p;
                break;
            }
            p = p.next;
        }

        p = first;
        while(p != null){
            if(p.data >= value && m.data >= p.data && p.key.equals("")){
                m = p;
            }
            p = p.next;
        }
        
        if(m.key.equals("")){
            int aux = m.data - value;
            m.key = key;
            m.data = value;
            Node n = new Node("", aux);
            n.next = m.next;
            m.next = n;
            return true;
        }
        
        return false;
        
    }

    private boolean worstFit(String key, int value){
        // System.out.println("Worst-fit");
        Node p = first;
        Node m = first;
        while(p != null){
            if(p.key.equals("")){
                m = p;
                break;
            }
            p = p.next;
        }

        p = first;
        while(p != null){
            if(p.data >= value && m.data <= p.data && p.key.equals("")){
                m = p;
            }
            p = p.next;
        }
        
        if(m.key.equals("")){
            int aux = m.data - value;
            m.key = key;
            m.data = value;
            Node n = new Node("", aux);
            n.next = m.next;
            m.next = n;
            return true;
        }
        return false;
    }
    
    private boolean firstFit(String key, int value){
        Node p = first;
        boolean fit = false;
        while(p!= null){
            if(p.data>=value && p.key.equals("")){
                int aux = p.data - value;
                p.data = value;
                p.key = key;
                Node n = new Node("", aux);
                n.next = p.next;
                p.next = n;
                fit = true;
                break;
            }
          p = p.next;
        }
        
        return fit;
    }    

    private boolean nextFit(String key, int value){
        //System.out.println("next-fit");
        Node p = last.next;
        boolean fit = false;

        while(p != last && p != null){
            if(p.data>=value && p.key.equals("")){
                int aux = p.data - value;
                p.data = value;
                p.key = key;
                Node n = new Node("", aux);
                n.next = p.next;
                p.next = n;
                last = p;
                fit = true;
                break;
            }
            p = p.next;
            if(p == null) p = first;
        }

        if(first == last && first.key.equals("")){
            int aux = first.data - value;
            first.data = value;
            first.key = key;
            Node n = new Node("", aux);
            n.next = first.next;
            first.next = n;
            last = first;
            fit = true;
        }

        return fit;

    }
    
    public void remove(String data){
        Node p = first;
        while(p!= null){
            if(p.key.equals(data)){
                p.key = "";
                Node aux = p.next;
                while(aux != null && aux.key.equals("")){
                    p.data += aux.data;
                    aux = aux.next;
                }
                p.next = aux;
            }else if(p.key.equals("")){
                p.key = "";
                Node aux = p.next;
                while(aux != null && (aux.key.equals("") || aux.key.equals(data))){
                    p.data += aux.data;
                    aux = aux.next;
                }
                p.next = aux;
                
            }
            p = p.next;
        }
    }
    
    public String print(){
        String res = "";
        Node p = first;
        while (p!= null){
        	if(p.key.equals("")){
                res += "["+p.data+"]";
	    	}
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
        first = last = new Node("", memSize);
    }

    public int size(){
        return this.memSize;
    }

    public int used(){
        int count = 0;
        Node p = first;
        while(p != null){
            if(!p.key.equals("")){
                count += p.data;
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