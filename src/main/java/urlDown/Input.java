package urlDown;

public class Input {
    private  String url = null;
    private  String path = null;
    private  String open = null;
    public void  input(String[] args){
        if(args.length==1){
            this.url=args[0];
            if(url.equals("")){
                throw new IllegalArgumentException();
            }
        }else if(args.length==2){
            this.url=args[0];
            this.path=args[1];
        }else if(args.length==3){
            this.url=args[0];
            this.path=args[1];
            this.open=args[2];
        }
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getOpen() {
        return open;
    }
}
