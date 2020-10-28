package urlDown;

import java.io.IOException;

public class GetFileName {
    private String expansionString = "";
    private String fileName = "";
    private String url = null;
    private ActionsWithURL actions = null;

    public GetFileName(String url, ActionsWithURL actions) {
        this.expansionString = url.substring(url.lastIndexOf('.'));
        this.url = url;
        this.actions = actions;
    }

    public String getName() throws IOException {
        if (url.indexOf("//") == url.lastIndexOf("//")) {
            return actions.isHtmlPage() ? "index.html" : "index" + getExpansionString();
        } else {
            String tmpVar = url;
            if (actions.isHtmlPage()) {
                if (url.contains("?")) {
                    tmpVar = (tmpVar.substring(0, tmpVar.lastIndexOf("?")));
                    return tmpVar.substring(tmpVar.lastIndexOf("/") + 1);
                } else {
                    tmpVar = tmpVar.substring(0, tmpVar.lastIndexOf("/"));
                    return tmpVar.substring(tmpVar.lastIndexOf("/") + 1);
                }
            } else {
                return tmpVar.substring(tmpVar.lastIndexOf("/") + 1);
            }
        }
    }

    public String getExpansionString() {
        return expansionString;
    }
    public ActionsWithURL getActions() {
        return actions;
    }
}
