import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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

    public String getName(){
        Elements namePerson = document.getElementsByClass("author-name bold");
        return namePerson.text();
    }

    public String getBio(){
        Elements bioPerson = document.getElementsByClass("author-bio");
        return bioPerson.text();
    }

    public String getImg(){
        Elements elements = document.getElementsByClass("user-avatar");
        String url = elements.attr("style");
        url = url.replace("background-image: url('", "");
        url = url.replace("')", "");
        return url;
    }

    public String getInfoPerson(){
        String info = "";

        info+="Имя "+getName()+"\n";
        info+="Статус "+getBio()+"\n";

        Elements names = document.getElementsByClass("info-stats-name");
        Elements values = document.getElementsByClass("info-stats-num");

        for (int i = 0; i < names.size(); i++){
            info+=names.get(i).text()+": "+values.get(i).text()+"\n";
        }
        info+=getBooks();
        return info;
    }

    public String getBooks(){
        try {
            booksDoc = Jsoup.connect("https://www.surgebook.com/" + nameAutor + "/books/all").get();
        }catch (IOException e){
            e.printStackTrace();
        }

        String text = "\nСписок книг: \n";
        ArrayList<String> textUrlBooks = new ArrayList<>();

        Elements books = booksDoc.getElementsByClass("book_view_mv1v2_title");
        Elements booksUrl = booksDoc.getElementsByClass("book_view_mv1v2_cover");

        for (int i = 0; i < books.size();i++){
            text+=books.get(i).text()+"\n";
            textUrlBooks.add(booksUrl.get(i).attr("href"));
        }

        getStatistics(textUrlBooks);

        text+="\n\nКоличество лайков на книгах: "+valuesLikesBooks+"\n";
        text+="Количество просмотров на книгах: "+valuesViewsBooks+"\n";
        text+="Количество комментариев на книгах: "+valuesCommentsBooks+"\n";

        return text;
    }

    private String getStatistics(ArrayList list) {
        for (int i=0; i<list.size();i++){
            try {
                booksDoc = Jsoup.connect(list.get(i).toString()).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = booksDoc.getElementsByClass("font-size-14 color-white m1-5");
            valuesLikesBooks+= Integer.valueOf(elements.get(0).text());
            valuesViewsBooks+= Integer.valueOf(elements.get(1).text());
            valuesCommentsBooks+= Integer.valueOf(elements.get(2).text());
        }
        return "";
    }
}
