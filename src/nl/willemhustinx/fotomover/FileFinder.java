package nl.willemhustinx.fotomover;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileFinder {

    File root;
    private int files;
    private int filesToMove;
    private int folders;
    private List fileList;
    private Map<String, Integer> extensions;

    public FileFinder(File root){
        this.root = root;
        this.files = 0;
        this.folders = 1;
        this.fileList = new ArrayList();
        this.extensions = new HashMap<>();
    }

    public void findFiles(){
        findFiles(this.root);
    }

    private void findFiles(File root){

        if( !root.exists() || !root.isDirectory() )
            return;

        File[] list = root.listFiles();
        if (list != null) {
            for (File child : list) {
                if(child.exists())
                {
                    if(child.isDirectory())
                    {
                        this.folders++;
                        this.findFiles(child);
                    }
                    else if(child.isFile()) {
                        fileFound(child);
                    }
                }
                System.out.print("\rScanned " + files + " files in " + folders + " folders");
            }
        }
    }

    private void fileFound(File file){
        this.files++;

        String name = file.getName();
        String extension = name.substring(name.lastIndexOf("."));

        if(this.extensions.containsKey(extension)){
            this.extensions.put(extension, this.extensions.get(extension) + 1);
        } else {
            this.extensions.put(extension, 1);
        }

        if(Main.validExtensions.contains(extension)){
            this.filesToMove++;
            this.fileList.add(file.getAbsolutePath());
        }
    }

    public void printExtensions(){
        System.out.println("Files:");
        this.extensions.entrySet().forEach(entry->{
            String color;
            if(Main.validExtensions.contains(entry.getKey())){
                color = Main.ANSI_GREEN;
            } else {
                color = Main.ANSI_RED;
            }

            System.out.println(color + entry.getKey() + " " + entry.getValue() + Main.ANSI_RESET);
        });

        System.out.println(filesToMove + " files to move");
    }

    public int getFilesToMove(){
        return filesToMove;
    }

    public List getFiles(){
        return fileList;
    }
}
