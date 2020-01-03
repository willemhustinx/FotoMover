package nl.willemhustinx.fotomover;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileMover {

    SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private List files;
    private File destFolder;
    private int filesToMove;
    private int filesMoved;

    public FileMover(List files, File destFolder){
        this.files = files;
        this.destFolder = destFolder;
        this.filesToMove = this.files.size();
        this.filesMoved = 0;
    }

    public void move() {


        for (int i = 0; i < this.files.size(); i++) {

            this.copyFile((String) this.files.get(i));
            this.update();

        }
    }

    private void copyFile(String filePath){
        File source = new File(filePath);

        BasicFileAttributes attrs = null;

        try {
            attrs = Files.readAttributes(source.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileTime time = attrs.lastModifiedTime();

        String day = sdfDay.format(new Date(time.toMillis()));
        String month = sdfMonth.format(new Date(time.toMillis()));
        String year = sdfYear.format(new Date(time.toMillis()));

        File folder = new File(destFolder + "\\" + year + "_" + month + "_" + day);
        folder.mkdirs();

        File dest = new File(destFolder + "\\" + year + "_" + month + "_" + day + "\\" + source.getName());

        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.filesMoved++;
    }

    private void update(){

        int procent = (this.filesMoved * 100) / this.filesToMove;

        System.out.print("\r[====================] " + procent + "%");
    }
}
