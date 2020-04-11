package scc212.api_server.DAO;

import scc212.api_server.Entity.Medical_CommentsBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalCommentsDAO
{
    private Medical_CommentsBean comments = new Medical_CommentsBean();
    private List<Medical_CommentsBean> return_list = new ArrayList<Medical_CommentsBean>();

    public MedicalCommentsDAO()
    {

    }

    public void access()
    {
        String Path = System.getProperty("user.dir");
        File ctoFile = new File(Path + "/Medical_Comments/Comments.txt");
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
                        return_list.add(comments);
                    count++;
                    comments = new Medical_CommentsBean();
                    comments.setTitle(txtline);
                }
                if(!txtline.substring(1, 2).equals("."))
                {
                    if(comments.getContents() == null)
                        comments.setContents(txtline);
                    else
                        comments.setContents(comments.getContents() + txtline);
                }
            }
            return_list.add(comments);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset()
    {
        comments = new Medical_CommentsBean();
        return_list = new ArrayList<Medical_CommentsBean>();
    }

    public List<Medical_CommentsBean> getReturn_list()
    {
        return this.return_list;
    }
}
