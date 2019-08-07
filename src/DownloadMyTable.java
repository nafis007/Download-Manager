import java.util.*;
import javax.swing.*;
import javax.swing.table.*;


class DownloadMyTable extends AbstractTableModel implements Observer
{
    private static final String[] columnNames = {"File Name", "Size", "Progress","Speed", "Time Remaining" ,"Status"};
    private static final Class[] columnClasses = {String.class, String.class, JProgressBar.class,String.class, 
                                                        String.class,String.class};
    
    private ArrayList<HttpAccel> downloadList = new ArrayList<>();
    
    public void addDownload(HttpAccel download) {
        
        download.addObserver(this);
        downloadList.add(download);
        fireTableRowsInserted(getRowCount(), getRowCount());
    }
    
    public HttpAccel getDownload(int row) {
        
        return (HttpAccel)downloadList.get(row);
    }
    
    public void clearDownload(int row) {
        
        downloadList.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public int getColumnCount() {
        
        return columnNames.length;
    }
    
    
    public String getColumnName(int col) {
        
        return columnNames[col];
    }
    
    
    public Class getColumnClass(int col) {
        
        return columnClasses[col];
    }
    
    
    public int getRowCount() {
        
        return downloadList.size();
    }
    
    
    public Object getValueAt(int row, int col) {
        
        HttpAccel download = (HttpAccel) downloadList.get(row);
        
        switch (col) {
            case 0:
                return download.getFileName(download.getDirectUrl());
                
            case 1: 
                int size = download.getSize();
                if(size<0)return "Size unknown";
                else if (size>=0 && size<1024) return String.format("%.2d byte(s)",(int)size);
                else if(size >=1024 && size<1.049e+6) return String.format("%.2f KB(s)",(float)(size/1024));
                else if(size >=1.049e+6 && size<1.074e+9)  return String.format("%.2f MB(s)",((float)(size/1.049e+6)));
                else return String.format("%.2f GB(s)",(float)(size/1.074e+9));
                
            case 2: 
                return new Float(download.getProgress());
                
            case 3:
                return download.getSpeed();
                
            case 4:
                return download.getTime();
                
            case 5:
                return Download.STATUSES[download.getStatus()];
        }
        return "";
    }
    
    
    public void update(Observable o, Object arg) {
        int index = downloadList.indexOf(o);
        fireTableRowsUpdated(index, index);
    }
}
