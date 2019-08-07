import java.io.*;
import java.net.*;

class Download implements Runnable
{  
    private URL url;
    private int size;
    private int downloaded;
    public int status;
    public float speed;
    public float time;
    public long start,end;
    
    public Thread thread;
    RandomAccessFile file = null;
    InputStream stream = null;
    
    int check=0;
    long starttime,timedif,temp;
    float tempspeed;
    
    int downloaded1;
    
    private static final int MAX_BUFFER_SIZE = 1024;
    public static final String STATUSES[] = {"Downloading","Paused","Completed", "Stopped", "Error","Appending"};
    public static final int DOWNLOADING = 0;
    public static final int PAUSED = 1;
    public static final int COMPLETE = 2;
    public static final int STOPPED = 3;
    public static final int ERROR = 4;
    public static final int APPENDING = 5;
    
    public Download(URL url,int size1) 
    {
        this.url = url;
        size = size1;
        downloaded = 0;
        speed=0;
        time=0;
        end=0;
        status = DOWNLOADING;
        download();
    }
    
    public Download(URL url, long start, long  end,int size1)
    {
        this.url = url;
        size = size1;
        downloaded = 0;
        speed=0;
        time=0;
        this.start = start;
        this.end = end;
        downloaded1=(int) start;
        status = DOWNLOADING;
        download();
    }
    
    private void download() 
    {   
        thread = new Thread(this);
        thread.start();
    }
    
    public URL getDirectUrl()
    {
        return url;
    }
    
    public String getUrl() {
        return url.toString();
    }
    
    public int getSize() {
        return size;
    }
    
    public int getDownloaded() {
        return downloaded;
    }
    
    public float getProgress() {
        
        if(size<0) return 0;
        return ((float)downloaded/size)*100;
    }
    
    public float getSpeed(){
        return speed;
    }
    
    public float getTime(){
        return time;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void pause() {
        status = PAUSED;
        check=1; 
        
        
    }
    
    public void resume() {
        status = DOWNLOADING;
        downloaded1=downloaded;
        download();
    }
    
    public void restart()
    {
        status = DOWNLOADING;
        downloaded=0;
        
        try{
            File nfile= new File("C:/Users/Sanjana/Documents/Downloads/" + getFileName(url));
            if(nfile.exists())
            {
                nfile.delete();
            }
        }catch(Exception e){}
        
        
        download();
    }
    
    public void stop() {
        status = STOPPED;
    }
    
    public void error() {
        status = ERROR;
    }
    
    public String getFileName(URL url) {
        String fileName = url.getFile();
        return fileName.substring(fileName.lastIndexOf('/') + 1);
    }
    
    
    @Override
    public void run() {
        
        try {
            
            if (url.toString().toLowerCase().startsWith("http://")){
                
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                
                if(end!=0){
                    connection.setRequestProperty("Range","bytes=" + downloaded1 + "-" + end);
                }
                else if(end==0){
                    connection.setRequestProperty("Range","bytes=" + downloaded + "-");
                }
                
                connection.connect();
                if (connection.getResponseCode() / 100 != 2) {
                    error();
                }
                
            
                File nfile= new File("C:/Users/Sanjana/Documents/Downloads/" + getFileName(url));
                
                if(size>1) file = new RandomAccessFile(nfile+"." + start + "-" + end, "rw");
                else file = new RandomAccessFile(nfile, "rw");
                
                file.seek(downloaded);
                stream = connection.getInputStream();
            }
            
            else if(url.toString().toLowerCase().startsWith("ftp://")){
                
                URLConnection connection =(URLConnection) url.openConnection();
                connection.connect();
                
                System.out.println("/ftp size/"+size);
            
                File nfile= new File("C:/Users/Sanjana/Documents/Downloads/" + getFileName(url));
                
                file = new RandomAccessFile(nfile, "rw");
                file.seek(downloaded);
                stream = connection.getInputStream();
            }
            
            if(check==0){
                starttime=System.currentTimeMillis();}
            else{
                starttime=starttime+System.currentTimeMillis()-temp;
                check=0;
            }
            
            while (status == DOWNLOADING) {
                
                byte buffer[]= new byte[MAX_BUFFER_SIZE];
                
                int read = stream.read(buffer);
                if (read == -1)
                    break;
                if(url.toString().toLowerCase().startsWith("http://") && size>1){
                    if(downloaded>=(end-start))break;
                }
                
                file.write(buffer, 0, read);
                downloaded += read;
                
                timedif=System.currentTimeMillis()-starttime;
                temp=System.currentTimeMillis();
                
                if(timedif==0){
                    speed=0;
                }
                else
                {
                    tempspeed=(downloaded/timedif);
                    speed=tempspeed;
                    if(tempspeed==0){}
                    else{
                        if(size<0){}
                        else{
                            time=(((size/4)-downloaded)/tempspeed)/1024;
                        }
                    }
                }
            }
            if (status == DOWNLOADING){
                if(url.toExternalForm().toLowerCase().startsWith("http://") && size>1)status = APPENDING;
                else if(url.toExternalForm().toLowerCase().startsWith("ftp://") 
                        ||(url.toExternalForm().toLowerCase().startsWith("http://") &&size<1))
                    status = COMPLETE;
            }
        }catch (IOException e) 
        {
            System.out.println("mara kha");
            error();
        }finally 
        {
            if (file != null) {
                try {
                    file.close();
                }catch (IOException e) {}
            }
            if (stream != null) {
                try {
                    stream.close();
                }catch (IOException e) {}
            }
        }
    }
}
