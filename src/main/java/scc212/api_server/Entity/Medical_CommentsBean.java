package scc212.api_server.Entity;

public class Medical_CommentsBean
{
    private String title;
    private String contents[] = new String[10];

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String[] getContents()
    {
        return contents;
    }

    public void setContents(String contents, int i)
    {
        this.contents[i] = contents;
    }
}
