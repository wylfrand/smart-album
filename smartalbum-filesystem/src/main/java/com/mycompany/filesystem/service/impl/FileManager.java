package  com.mycompany.filesystem.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import com.mycompany.filesystem.utils.FileUtils;


/** Classe m�re des managers manipulant des fichier (archivage, suppression, cr�ation de zip...) */
public abstract class FileManager {
	
    // Niveau des compression des fichiers zip (MAX)
    private final static int COMPRESSION_LEVEL=9;
    // Format de date utilis� lors du renommage des fichiers d'archive
    private static final String DATE_FORMAT="yyyy.MM.dd_HH.mm.ss";


    /** Gestion de l'archivage des fichiers. La politique de gestion est la suivante :
     *  - On ne garde dans l'historique que les NB_BACKUP derniers fichiers dans le r�pertoire root
     *  et dont le nom contient fileName. Les fichiers d'archives SMARTALBUM sont pr�fix�s par la date,
     *  il suffit donc de les trier par nom pour les avoir tri�s par date de cr�ation.
     */
    protected static synchronized void deleteDatedZip(File root, String fileMask, int nbBackup) throws Exception {
        if(!root.isDirectory()) throw new IllegalArgumentException(root.getName());
        String[] fileList = root.list(FileUtils.imageFilter);
        // R�cup�ration de la liste des fichiers archiv�s correspondants :
        // le nommage est de la forme DATE_FORMAT+ ~fileName~ + '.zip'
        List<String> archiveList=new ArrayList<String>();
        for ( int i=0;i<fileList.length; i++){
            if (!(new File(root+File.separator+fileList[i]).isDirectory())){
                if((fileList[i].toUpperCase().indexOf(fileMask.toUpperCase())!=-1)&&
                   (fileList[i].length()>=fileMask.length()+DATE_FORMAT.length()+4)&&
                   (fileList[i].endsWith(".zip"))&&
                   (checkDate(fileList[i].substring(0, DATE_FORMAT.length()))))
                   {
                    archiveList.add(fileList[i]);
                }
            }
        }
        if(archiveList.size()>nbBackup){
            // Trie de la liste des fichiers (la date est plac�e devant)
            for(int i=0;i<archiveList.size()-nbBackup;i++){
                File dummy=new File(root+File.separator+(String)archiveList.get(i));
                dummy.delete();
            }
        }
    }


    /** M�thode permettant de d�placer un fichier en le r��crivant au format zip compress�
     *  note : le fichier src est donc d�truit. Si un fichier destination de m�me nom
     *  existe d�j�, il sera d�truit aussi. En ce qui concerne l'identification des fichiers,
     *  cela n'entraine aucune modification du CRC32 du fichier zipp� (qui n'est pas le zip
     *  en lui-m�me).
     */
    protected static synchronized void renameAndZip(File src, File dest) throws ZipException{
        try{
            // Suppression d'un �ventuel fichier destination de m�me nom
            if (dest.exists()) dest.delete();

            // Taille du buffer de lecture interne
            int NbByteRead=10000;

            // Cr�ation d'un nouveau stream zipp� au niveau de compression choisi
            ZipOutputStream zipFileOutStream=new ZipOutputStream(new FileOutputStream(dest));
            zipFileOutStream.setLevel(COMPRESSION_LEVEL);

            // Le fichier zip ne contiendra qu'une entr�e, qui est le fichier source
            ZipEntry zippedFile = new ZipEntry(src.getName());
            zipFileOutStream.putNextEntry(zippedFile);

            BufferedInputStream BIStream=new BufferedInputStream(new FileInputStream(src));
            BufferedOutputStream BOStream=new BufferedOutputStream(zipFileOutStream);
            byte[] b=new byte[NbByteRead];
            int readResult;
            do{
                readResult=BIStream.read(b, 0, NbByteRead);
                if (readResult!=-1) BOStream.write(b,0, readResult);
            }
            while (readResult!=-1);
            // Vidage du BufferedOutputStream
            BOStream.flush();
            // Fermeture du fichier zip
            zipFileOutStream.closeEntry();
            zipFileOutStream.close();
            BIStream.close();
            // La cr�ation du zip est correcte, on supprime le fichier source
            src.delete();
        }
        catch(Exception e){
            throw new ZipException("Erreur lors de la cr�ation du fichier zip : "+dest.getName()+" "+e.getMessage());
        }
    }


    /** M�thode permettant de cr�er une archive contenant N fichiers au format zip compress�.
     *  note : Si un fichier destination de m�me nom existe d�j�, il sera d�truit. */
    protected static synchronized void groupAndZip(File[] src, File dest) throws ZipException{
        try{
            // Suppression d'un �ventuel fichier destination de m�me nom
            if (dest.exists()) dest.delete();

            // Taille du buffer de lecture interne
            int NbByteRead=10000;

            // Cr�ation d'un nouveau stream zipp� au niveau de compression choisi
            ZipOutputStream zipFileOutStream=new ZipOutputStream(new FileOutputStream(dest));
            zipFileOutStream.setLevel(COMPRESSION_LEVEL);
            BufferedOutputStream BOStream=new BufferedOutputStream(zipFileOutStream);
            for(int i=0;i<src.length;i++){
                zipFileOutStream.putNextEntry(new ZipEntry(src[i].getName()));
                BufferedInputStream BIStream=new BufferedInputStream(new FileInputStream(src[i]));
                byte[] b=new byte[NbByteRead];
                int nbRead;
                do{
                    nbRead=BIStream.read(b, 0, NbByteRead);
                    if (nbRead!=-1) BOStream.write(b,0, nbRead);
                }
                while (nbRead!=-1);
                BOStream.flush();
                zipFileOutStream.closeEntry();
                // Fermeture du flux de lecture
                BIStream.close();
            }
            zipFileOutStream.close();
        }
        catch(Exception e){
            throw new ZipException("Erreur lors de la cr�ation de l'archive zip : "+dest.getName()+" "+e.getMessage());
        }
    }


    /** Calcul du CRC32 d'un fichier, permet de savoir si 2 fichiers sont identiques ou pas */
    protected static long getCRC32(File file) throws FileNotFoundException, IOException{
        int NbByteRead=7000;
        CRC32 crc32=new CRC32();
        BufferedInputStream BIStream=new BufferedInputStream(new FileInputStream(file));
        byte[] b=new byte[NbByteRead];
        int readResult;
        do{
            readResult=BIStream.read(b, 0, NbByteRead);
            if (readResult!=-1) crc32.update(b,0, readResult);
        }
        while (readResult!=-1);
        BIStream.close();
        return crc32.getValue();
    }


    /** Renvoie la date au format chaine pour insertion dans les noms de fichiers archive */
    protected static String getDate(){
        // Format de date pour le nommage des fichiers
        SimpleDateFormat df= new SimpleDateFormat(DATE_FORMAT);
        return df.format(new Date());
    }


    /** V�rifie qu'une chaine correspond bien � une date au foramt d�fini pour les noms de fichiers archive */
    protected static boolean checkDate(String date){
        try{
            SimpleDateFormat df= new SimpleDateFormat(DATE_FORMAT);
            df.parse(date);
            return true;
        }
        catch (ParseException e){
            return false;
        }
    }
}