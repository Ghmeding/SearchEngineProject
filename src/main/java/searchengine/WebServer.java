package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Represents a webserver
 * 
 * @author Gustav Henrik Meding, Thomas Lindskov Christensen & Solveig Felbo
 * @author solf@itu.dk, gusm@itu.dk & thic@itu.dk
 * @version 1.0
 */

public class WebServer {
  private static final int PORT = 8080;
  private static final int BACKLOG = 0;
  private static final Charset CHARSET = StandardCharsets.UTF_8;
  public HttpServer server;
  private QueryHandler queryHandler;
  private FileSystem fs = FileSystem.getInstance();

  /**
   * Creates and launches a WebServer with the specified information.
   * 
   * @param port     The port that the webserver is launched on (statically set to
   *                 8080)
   * @param filename Name of the dataset to be read
   * @throws IOException IOException if the dataset is not found
   */
  public WebServer(int port, String filename) throws IOException {
    queryHandler = new QueryHandler(filename);
    /*
     * Creation of webpage application
     */

    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    server.createContext("/", io -> respond(io, 200, "text/html", fs.getFile("web/index.html")));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", fs.getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", fs.getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", fs.getFile("web/style.css")));
    /*
     * The query endpoint
     */
    // Performs a search
    server.createContext("/search", io -> searchResponder(io));

    // Updates the webserver
    server.start();
    // Creates a link to the webserver in the terminal
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  ///// Private methods used to run the webserver /////

  /**
   * Handles search requests by returning a list of matching pages and their
   * titles.
   * 
   * @param io The http object where the header includes the search term
   */
  private void searchResponder(HttpExchange io) {
    try {
      String[] searchTermRaw = io.getRequestURI().getRawQuery().split("=");
      String searchTerm = null;
      if (searchTermRaw.length > 1) {
        searchTerm = searchTermRaw[1].toLowerCase();
      }
      ArrayList<PageWordInfo> result = queryHandler.getMatchingWebPagesFor_AND_and_OR_Searches(searchTerm);
      ArrayList<String> response = new ArrayList<String>();
      result.sort(Comparator
          .comparing(PageWordInfo::getScore).reversed());
      for (PageWordInfo pageWordInfo : result) {
        response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
            pageWordInfo.getUrl().substring(6), pageWordInfo.getTitle()));
      }
      byte[] bytes = response.toString().getBytes(CHARSET);
      respond(io, 200, "application/json", bytes);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 
   * Sends a response to an HTTP request.
   * 
   * @param io       the {@link HttpExchange} object for the current request
   * @param code     the HTTP response code to send
   * @param mime     the MIME type of the response
   * @param response the response data to send
   */
  private void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders().set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) { // Catches all errors
    } finally {
      io.close();
    }
  }

  public static void main(final String... args) throws IOException {
    String filename = Files.readString(Paths.get("config.txt")).trim();
    new WebServer(PORT, filename);
  }

}