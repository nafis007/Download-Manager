import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;

public class HttpAccel extends Observable implements Runnable{
    
    private URL url;
    private int size;
    
    public Download[] Threads;
    private Download single;
    private Thread thread;
    
    public int count = 4;
    int tempstatus;
    
    private String filename;
    private String[] parts;
    
    private int downloaded1=0,downloaded2;
    private float sp,tempsp=0;
    private float progress,tempprogress=0;
    private float tp=0,tp1;
    
    
    public HttpAccel(URL url)
    {
        this.url = url;
        size=-1;
        tempstatus=0;
        try
        {
            if(url.toString().toLowerCase().startsWith("http://")){
                HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
                connection.connect();
                size = connection.getContentLength();
            }
            else{
                URLConnection connection = url.openConnection();
                connection.connect();
                size = connection.getContentLength();
            }
            
        }catch (IOException e) {}
        
        filename = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
        Threads = new Download[count];
        
        if(url.toString().toLowerCase().startsWith("http://") && size>1){
                
                for (int i = 0; i <count; i++){
                    if (i == 0){
                        Threads[i] = new Download(url,0,size /count,size);
                    } 
                    else if (i == count - 1){
                        Threads[i] = new Download(url, ((size / count) * i) + 1, size,size);
                    } 
                    else{
                        Threads[i] = new Download(url, ((size / count) * i) + 1, ((size / count) * (i + 1)),size);
                    }
                }
            }
        
            else if(url.toString().toLowerCase().startsWith("ftp://") ||
                    (url.toString().toLowerCase().startsWith("http://") &&size<1)){
                single = new Download(url,size); 
            }
        download();
    }
    
    private void download(){   
        thread = new Thread(this);
        thread.start();
    }
    
    public URL getDirectUrl(){
        return url;
    }
    
    public String getUrl() {
        return url.toString();
    }
    
    public int getDownloaded(){
        return downloaded2;
    }
    
    public int getSize() {
        return size;
    }
    
    public float getProgress() {
        return progress;
    }
    
    public String getSpeed(){
        return  String.valueOf(sp)+" kBps";
    }
    
    public String getTime(){
        String time;
        
        int hrs, mins, sec;
        float temphrs, tempmins, tempsec;
        
        if(tp1>60 && tp1<3600){
                tempmins=tp1/60;
                mins=(int)(tp1/60);
                tempsec=(tempmins-mins);
                sec=(int)(tempsec*60);
                time=String.format("%d min %d s",mins,sec);
         }
                                
         else if(tp1>=3600){
                temphrs=tp1/3600;
                hrs=(int)(tp1/3600);
                tempmins=(temphrs-hrs);
                mins=(int)(tempmins*60);
                time=String.format("%d hr %d min",hrs,mins);
         }
                                
         else{
                time=String.format("%.1f s",tp1);
         }
        
        return time;
    }
    
    public String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }
    
    public int getStatus() 
    {
        if(url.toString().toLowerCase().startsWith("http://")&&size>1){
          
            if(Threads[0].getStatus()==Threads[1].getStatus()&&Threads[0].getStatus()==Threads[2].getStatus()&&Threads[0].getStatus()==Threads[3].getStatus())
                return Threads[0].getStatus();
            else 
                return 0;           
     }
        else if(url.toString().toLowerCase().startsWith("ftp://") ||
                    (url.toString().toLowerCase().startsWith("http://") &&size<1)){ 
            return single.status;
        }
        return 0;
    }
    
    private void stateChanged() {
        setChanged();
        notifyObservers();
    }
    
    public void pause()
    {
        tempstatus=1;
        if(url.toString().toLowerCase().startsWith("http://") && size>1){   
            for(int j=0;j<count;j++){
                Threads[j].pause();
                stateChanged();
                
            }
        }
        
        else if(url.toString().toLowerCase().startsWith("ftp://") ||
                        (url.toString().toLowerCase().startsWith("http://") && size<1)){
            single.pause();
            stateChanged();
        }
    }
    
    public void resume() 
    {
        tempstatus=0;
        if(url.toString().toLowerCase().startsWith("http://")&&size>1){   
            for(int j=0;j<count;j++){
                Threads[j].resume();
                stateChanged();
            }
        }
        else if(url.toString().toLowerCase().startsWith("ftp://") ||
                        (url.toString().toLowerCase().startsWith("http://") && size<1)){
            single.resume();
            stateChanged();
            download();
        }
        
        
    }
    
    public void restart()
    {
        tempstatus=0;
        stateChanged();
        single.restart();
        download();
        
    }
    
    public void stop()
    {
        tempstatus=3;
        if(url.toString().toLowerCase().startsWith("http://") && size>1){
            for(int j=0;j<count;j++)
            {
                Threads[j].stop();
                stateChanged();
            }
        }
        else if(url.toString().toLowerCase().startsWith("ftp://") ||
                (url.toString().toLowerCase().startsWith("http://") && size<1)){
            single.stop();
            stateChanged();
        }
        
    }
    
    public void error()
    {
        tempstatus=4;
        if(url.toString().toLowerCase().startsWith("http://") && size>1){
            for(int j=0;j<count;j++)
            {
                Threads[j].error();
                stateChanged();
            }
        }
        else if(url.toString().toLowerCase().startsWith("ftp://") ||
                (url.toString().toLowerCase().startsWith("http://") && size<1)){
            single.error();
            stateChanged();
        }
    }
    
    public void setHistory(URL url,int size)
    {
        try
        {
            String sz= new String();
            if(size<0)sz= "Size unknown";
            else if (size>=0 && size<1024) sz= String.format("%.2d byte(s)",(int)size);
            else if(size >=1024 && size<1.049e+6) sz=String.format("%.2f KB(s)",(float)(size/1024));
            else if(size >=1.049e+6 && size<1.074e+9)  sz=String.format("%.2f MB(s)",((float)(size/1.049e+6)));
            else sz=String.format("%.2f GB(s)",(float)(size/1.074e+9));
                
            String name=String.valueOf(url);
            String filename= "history.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.append(name+" [size: "+sz+"]\n");
            fw.close();
        }
        catch(IOException ioe){}
    }
    
    public void append() throws Exception 
    {
        System.out.println("in append function");
        
        FileOutputStream fos = new FileOutputStream("C:/Users/Sanjana/Documents/Downloads/"+filename);
        
	for (int j = 0; j < count; j++){
            
            InputStream fis = new FileInputStream(parts[j]);
            while (true){
                 byte buffer[] = new byte[32768];
                 int b = fis.read(buffer);
                 if (b == -1){
                    break;
                 }
                 fos.write(buffer,0,b);
                        
            }
            fis.close();
	}
                
        for (int i = 0; i < count; i++){
            
            String path = "C:/Users/Sanjana/Documents/Downloads/";
            String extension = "";
                   
            if (i == 0){
                extension = new String("." + 0 + "-" + (size / count));
            }
            else if (i == count - 1){
                extension = new String("." + (((size / count)*i) + 1) + "-" + size);
            } 
            else{
                extension =  new String("." +  (((size/count)*i) + 1) + "-" + ((size /count)*(i+1)));
            }
            File file1 = new File(path + "/" + filename+ extension);
            
            if(file1.delete()){
    		System.out.println("part file deleted");
            }
        }
                
        fos.close();
        for(int j=0;j<count;j++){
           Threads[j].status = 2;
        }
        setHistory(url,size);
        stateChanged();
    }

    
    @Override
    public void run() {
        System.out.println("accel run");
        try
        {
            if(url.toString().toLowerCase().startsWith("http://") && size>1){
                for(int i=0;i<count;i++){
                    if(Threads[i].getStatus()== 4){
                        error();
                        break;
                    }
               }
            }
            
            else if(url.toString().toLowerCase().startsWith("ftp://") ||
                    (url.toString().toLowerCase().startsWith("http://") &&size<1)){
                        if(single.getStatus()==4){
                            error();
                        }
            }
            
            while(true){
                
                    if(url.toString().toLowerCase().startsWith("http://") && size>1){
                        //download
                        for(int j = 0 ;j<count;j++){
                            downloaded1 += Threads[j].getDownloaded();
                            stateChanged();
                        }
                        downloaded2=downloaded1;
                        
                        
                        //time remaining
                        for(int j = 0 ;j<count;j++){
                            tp += Threads[j].getTime();
                            stateChanged();
                        }
                        tp1=tp;
                        tp=0;
                    
                        //speed
                        for(int j = 0 ;j<count;j++){
                            tempsp+=Threads[j].getSpeed();
                            stateChanged();
                        }
                        sp=tempsp;
                        tempsp=0;
                        
                        //progress
                        for(int j = 0 ;j<count;j++){
                            tempprogress+= Threads[j].getProgress();
                            stateChanged();
                        }
                        progress=tempprogress;
                        tempprogress=0;
                        
                        if(downloaded1>=size) {
                            break;
                        }
                        downloaded1=0;
                    }
                    
                    else if(url.toString().toLowerCase().startsWith("ftp://") ||
                        (url.toString().toLowerCase().startsWith("http://") &&size<1)){
                        
                            downloaded1 = single.getDownloaded();
                            stateChanged();
                            downloaded2=downloaded1;
                            
                            
                            tempsp=single.getSpeed();
                            stateChanged();
                            sp=tempsp;
                            tempsp=0;
                            
                            tp=single.getTime();
                            stateChanged();
                            tp1=tp;
                            tp=0;
                            
                            tempprogress = single.getProgress();
                            stateChanged();
                            progress=tempprogress;
                            tempprogress=0;
                
                            if(single.getStatus()==2){
                                break;
                            }
                            downloaded1=0;
                    }
            }//end of while loop
            
           if(url.toString().toLowerCase().startsWith("http://")&& size>1){
                
               System.out.println("before appending");
               for(int i=0;i<count;i++){
                   Threads[i].thread.join();
               }
            
            
                parts = new String[count] ;
                for (int i = 0; i < count; i++){
                    if (i == 0){
                    parts[i] = new String("C:/Users/Sanjana/Documents/Downloads/"+ filename+"." + 0 + "-" + (size / count));
                    }
                    else if (i == count - 1) {
                        parts[i] =  new String("C:/Users/Sanjana/Documents/Downloads/"+filename+ "." + (((size /count)*i) + 1) + "-" + size);
                    } 
                    else {
                        parts[i] =   new String("C:/Users/Sanjana/Documents/Downloads/"+ filename+ "." +  (((size/count)*i) + 1) + "-" + ((size /count)*(i+1)));
                    }
                }
                append();
        
            }
           
            else if(url.toString().toLowerCase().startsWith("ftp://") ||
                    (url.toString().toLowerCase().startsWith("http://") &&size<1)){
                single.status=2;
                setHistory(url,size);
                stateChanged();
            }
           
        } catch (Exception ex){}
    }
}
    
