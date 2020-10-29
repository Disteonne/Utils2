package urlDown;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;

/**All actions related to the url:
 *Checking for an html page;
 *Getting the encoding
 */
public class ActionsWithURL {
    private String type="";
    private URLConnection connection=null;
    private URL url;
    public  ActionsWithURL(String URL) {
        try {
            this.url = new URL(URL);
            this.connection=url.openConnection();
        }catch (MalformedURLException exception){
            System.out.println("error");
        }catch (IOException exception){
            System.out.println("Connect error ActionsWithURL");
        }
    }

    public URL getUrl() {
        return url;
    }

    public boolean isHtmlPage() throws IOException {
        if(url!=null) {
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setAllowUserInteraction(false);
            urlc.setDoInput(true);
            urlc.setDoOutput(false);
            urlc.setUseCaches(true);
            urlc.setRequestMethod("HEAD");
            urlc.connect();
            this.type = urlc.getContentType();
            return type.startsWith("text/html") ? true : false;
        }else
            throw new NoSuchElementException(); //element not find
    }
    public String getEncoding(boolean isHtmlPage){
        if(!type.equals("") && isHtmlPage) {
            return type.substring(type.lastIndexOf("=") + 1, type.length());
        }else
            return "UTF-8";
    }
    public URLConnection getConnection() {
        return connection;
    }
}
