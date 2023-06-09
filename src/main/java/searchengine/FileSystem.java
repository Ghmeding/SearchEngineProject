package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** Helper class who reads the file */
public class FileSystem {
  private static final FileSystem instance = new FileSystem();
  private int numPageInDataset;

  /** Constructs the file system */
  private FileSystem() {
  }

  /**
   * Gets the singleton instance
   * 
   * @return A FileSystem instance
   */
  public static FileSystem getInstance() {
    return instance;
  }

  /**
   * Gets the dataset in the format of an encyclopedia
   * 
   * @param filename Name of the dataset to be read
   * @return A list of lists of strings representing the pages in the encyclopedia
   */
  public List<List<String>> getPages(String filename) {
    List<List<String>> pages = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(Paths.get(filename));
      int lastIndex = lines.size();
      for (int i = lines.size() - 1; i >= 0; --i) {
        if (lines.get(i).startsWith("*PAGE") && !lines.get(i + 1).startsWith("*PAGE")
            && !lines.get(i + 2).startsWith("*PAGE")) {
          pages.add(lines.subList(i, lastIndex));
          lastIndex = i;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();

    } catch (IndexOutOfBoundsException q) {
      q.printStackTrace();
    }
    this.numPageInDataset = pages.size();
    return pages;
  }

  /**
   * Gets the dataset as binary data. If the file name is not valid an empty byte
   * array is returned.
   * 
   * @param filename Name of the dataset to be read
   * @return A byte array representing the dataset stored as binary data
   */
  public byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  public int getNumPageInDataset() {
    return numPageInDataset;
  }

}
