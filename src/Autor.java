import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Autor {
    private Document document;
    private Document booksDoc;
    private String nameAutor="";

    private int valuesLikesBooks;
    private int valuesViewsBooks;
    private int valuesCommentsBooks;

    public Autor(String nameAutor) {
        this.nameAutor = nameAutor;
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://www.surgebook.com/" + nameAutor).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
