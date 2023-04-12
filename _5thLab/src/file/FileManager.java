package file;

import exceptions.FileException;
import exceptions.PathException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for Reading File and Writing in File
 */
public class FileManager {
    private String path;
    private ArrayList<String>paths = new ArrayList<>();
    public FileManager(){
        this("");
    }
    public FileManager(String path){
        this.path = path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void addPath(String path){
        paths.add(path);
    }
    public ArrayList<String> getPaths() {
        return paths;
    }

    /**
     * Method for reading the file
     * @return String containing file contents
     */
    public String read(){
        StringBuffer sb = new StringBuffer();
        try {
            if (path == null) throw new PathException();
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);
            if (!file.exists()) System.err.println("File doesn't exist :(");
            if(!file.canRead()) System.err.println("cannot read file :(");
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNext()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
            scanner.close();
        }  catch (PathException | IOException  e) {
            System.err.println("cannot access file :(");
        }
        return sb.toString();
    }

    /**
     * Method  for Writing in file
     * @param content
     * @return true if content was written in file, false if content wasn't written
     */
    public boolean write(String content){
        boolean res = true;
        try{
            if (path == null) throw new PathException();

            File file = new File(path);

            if(!file.exists()) {
                System.err.println("file " + path +" doesnt exist, trying to create new file");
                create(file);
            }
            if(!file.canWrite()) throw new FileException("cannot write file");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        } catch(PathException e){
            System.err.println(e.getMessage());
            res = false;
        } catch (IOException e) {
            res = false;
            System.err.println("cannot access file");
        }
        return res;
    }
    private void create(File file) throws FileException {
        try{
            file.createNewFile();
        } catch(IOException e){
            throw new FileException();
        }
    }
    public void clearFileContents(){
        try {
            create(new File(path));
        }catch (FileException e){

        }
    }
}
