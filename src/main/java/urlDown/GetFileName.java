package urlDown;

import java.io.IOException;

/**
 * Getting the file name. All according to the task condition
 */
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
        String editUrlForName = url.substring(url.lastIndexOf("/") + 1);
        if (editUrlForName.equals("")) {
            return actions.isHtmlPage() ? "index.html" : "index" + getExpansionString();
        } else {
            if (actions.isHtmlPage()) {
                if (url.contains("?")) {
                    editUrlForName = (editUrlForName.substring(0, editUrlForName.lastIndexOf("?")));
                    return editUrlForName;
                } else {
                    return editUrlForName + ".html";
                }
            } else {
                return url.substring(url.lastIndexOf("/") + 1);
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
