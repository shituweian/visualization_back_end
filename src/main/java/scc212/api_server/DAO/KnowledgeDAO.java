/**
 * Knowledge API (FQAs)
 * Please see the details in wiki
 *
 * @author Chongyang Zhoao
 *
 * @date 2020/04/29
 */

package scc212.api_server.DAO;

import scc212.api_server.Entity.Knowledge;
import scc212.api_server.Entity.Medical_CommentsBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeDAO {
    private Knowledge contents = new Knowledge();
    private List<Knowledge> return_list = new ArrayList<Knowledge>();

    public KnowledgeDAO() {

    }

    public void access() {
        InputStream is = this.getClass().getResourceAsStream("/TextResources/Knowledge.txt");
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
                        return_list.add(contents);
                    count++;
                    line = 0;
                    contents = new Knowledge();
                    contents.setTitle(txtline);
                }
                if(!txtline.substring(1, 2).equals("."))
                {
                    if(line == 0)
                        contents.setContents(txtline, line);
                    else
                        contents.setContents(txtline, line);
                    line++;
                }
            }
            return_list.add(contents);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void reset() {
        contents = new Knowledge();
        return_list = new ArrayList<Knowledge>();
    }

    public List<Knowledge> getReturn_list() {
        return this.return_list;
    }
}
