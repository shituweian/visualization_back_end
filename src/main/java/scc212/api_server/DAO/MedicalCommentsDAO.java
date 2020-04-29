package scc212.api_server.DAO;

import scc212.api_server.Entity.Medical_CommentsBean;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
@ Intro: This class aims to return the Medical Comments stored in MedicalComments.
         Note that different paragraphs are stored in different index of String array.
@ Author: Tian Yu 17722024
@ Date: 2020.04.11.
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
        InputStream is = this.getClass().getResourceAsStream("/TextResources/MedicalComments.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String txtline ="";
        int count = 0;
        int line = 0;
        boolean isSet = false;
        try {
            while((txtline=br.readLine())!=null)
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
        }catch (Exception e)
        {
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
