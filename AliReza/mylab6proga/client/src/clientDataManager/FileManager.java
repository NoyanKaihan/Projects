package clientDataManager;

import exceptions.EmptyPathException;
import exceptions.FileException;
import exceptions.FileWrongPermissionsException;
import exceptions.StreamAlreadyClosedException;

import java.io.*;

public class FileManager {
    private String path;
    public FileManager(String path){
        this.path = path;
    }

    public String read(){
        String str="";
        FileReader fileReader;
        try{
            if(path == null)throw new EmptyPathException();
            File file = new File(path);
            if(!file.exists()) throw new FileNotFoundException("File doesn't exist");
            if(!file.canRead()) throw new FileWrongPermissionsException("cannot read file");
            fileReader = new FileReader(file);
            int i ;
            while((i = fileReader.read()) != -1){
                str += (char)i;
            }
            fileReader.close();
        }catch (FileException exception){
            System.err.println(exception.getMessage());
        }
        catch (StreamAlreadyClosedException e){
            System.out.println("Stream has been closed");
        } catch(IOException e) {
            System.err.println("cannot access file");
        }
        return str;
    }
}
