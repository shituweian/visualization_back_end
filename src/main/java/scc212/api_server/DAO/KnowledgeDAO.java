package scc212.api_server.DAO;

import scc212.api_server.Entity.Knowledge;
import scc212.api_server.Entity.Medical_CommentsBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeDAO
{
    private Knowledge contents = new Knowledge();
    private List<Knowledge> return_list = new ArrayList<Knowledge>();

    public KnowledgeDAO()
    {

    }

    public void access()
    {
        String Path = System.getProperty("user.dir");
        File ctoFile = new File(Path + "/TextResources/Knowledge.txt");
        InputStreamReader reading = null;
        String title[] = new String[2];
        String content[] = new String[2];
        try {
            reading = new InputStreamReader(new FileInputStream(ctoFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader counting = new BufferedReader(reading);
        String txtline = null;
        int count = 0;
        boolean isSet = false;
        try {
            while ((txtline = counting.readLine()) != null)
            {
                if(txtline.substring(1, 2).equals("."))
                {
                    if(count != 0)
                        return_list.add(contents);
                    count++;
                    contents = new Knowledge();
                    contents.setTitle(txtline);
                }
                if(!txtline.substring(1, 2).equals("."))
                {
                    if(contents.getContents() == null)
                        contents.setContents(txtline);
                    else
                        contents.setContents(contents.getContents() + txtline);
                }
            }
            return_list.add(contents);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset()
    {
        contents = new Knowledge();
        return_list = new ArrayList<Knowledge>();
    }

    public List<Knowledge> getReturn_list()
    {
        return this.return_list;
    }
}
