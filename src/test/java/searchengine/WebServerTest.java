
package searchengine;

import static org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {
    WebServer server = null;
    FileSystem fs = FileSystem.getInstance();

    @BeforeAll
    void setUp() {
        try {
            var rnd = new Random();
            while (server == null) {
                try {
                    server = new WebServer(rnd.nextInt(60000) + 1024, "data/test-file.txt");
                } catch (BindException e) {
                    // port in use. Try again
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDown() {
        server.server.stop(0);
        server = null;
    }

    @Test
    void lookupWebServer() {
        String baseURL = String.format("http://localhost:%d/search?q=",
        server.server.getAddress().getPort());
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}, {\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
        httpGet(baseURL + "word1"));
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
        httpGet(baseURL + "word2"));
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}]",
        httpGet(baseURL + "word3"));
        assertEquals("[]",
        httpGet(baseURL + "word4"));
    }

    private Object httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }    }

    @Test
    void fileIsNoneInvertedIndex() {
        InvertedIndex invertedIndex = new InvertedIndex("test-file_empty.txt");
        HashMap<String, HashMap<String, PageWordInfo>> index = invertedIndex.getIndex();
        assertTrue(index.isEmpty());
    }

    @Test
    void searchTermIsOneWordWebServer() {
        String ioInput = "...=One";
        assertEquals("one", ioInput.split("=")[1].toLowerCase());
    }

    @Test
    void searchTermIsMoreWordsWebServer() {
        String ioInput = "...=One Two OR Three";
        assertEquals("one two or three", ioInput.split("=")[1].toLowerCase());
    }

}
