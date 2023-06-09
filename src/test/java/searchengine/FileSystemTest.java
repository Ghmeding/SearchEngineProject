package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest{
    FileSystem fs = FileSystem.getInstance();

    //getPages test - no pages in filesystem
    @Test 
    void getPages_zeroPages(){
        List<List<String>> pages = fs.getPages("test-file_empty.txt");
        assertEquals(0, pages.size());
    }


   //getPages test - more pages in filesystem 
   @Test 
   void getPages_morePages(){
        List<List<String>> pages = fs.getPages("enwiki-medium.txt");
        System.out.println(pages);
    } 

   
   //getFile test - checks whether filesystem is empty
    @Test
    void getFile_isArgumentEmpty(){
        assertTrue(fs.getFile("").length == 0);
    }

    //getFile test - check whether the filesystem has data when given a web-url
    @Test
    void getFile_isArgumentNonEmpty(){
        assertTrue(fs.getFile("web/index.html").length > 0);
    }

    /*@Test
    void getNumPageInDataset_IsEqualToPageSize(){
        fs.getPages("test_file.txt").size(); //.getpages returner pages. Så .size() burde vel virke efter???
        assertEquals(2, fs.getPages("test.txt").size());

    }*/
}