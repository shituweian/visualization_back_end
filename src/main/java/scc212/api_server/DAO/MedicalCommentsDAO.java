package scc212.api_server.DAO;

import scc212.api_server.Entity.Medical_CommentsBean;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
This class aims to return the Medical Comments stored in MedicalComments.
Note that different paragraphs are stored in different index of String array.
Developer: Tian Yu 17722024
At 2020.04.11.
 */

public class MedicalCommentsDAO
{
    private Medical_CommentsBean comments = new Medical_CommentsBean();
    private List<Medical_CommentsBean> return_list = new ArrayList<Medical_CommentsBean>();

    public MedicalCommentsDAO()
    {
    }

    //Access the local txt file, then read data, and store in Arraylist.
    public void access()
    {
        String Path = System.getProperty("user.dir");
        File ctoFile = new File(Path + "/TextResources/MedicalComments.txt");
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
        int line = 0;
        boolean isSet = false;
        try {
            while ((txtline = counting.readLine()) != null)
            {

                if(txtline.substring(1, 2).equals("."))
                {
                    if(count != 0)
                        return_list.add(comments);
                    count++;
                    line = 0;
                    comments = new Medical_CommentsBean();
                    comments.setTitle(txtline);
                }
                if(!txtline.substring(1, 2).equals("."))
                {
                    if(line == 0)
                        comments.setContents(txtline, line);
                    else
                        comments.setContents(txtline, line);
                    line++;
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
