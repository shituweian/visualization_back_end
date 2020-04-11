package scc212.api_server.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import scc212.api_server.Entity.CityBean;
import scc212.api_server.Entity.NationChart;
import scc212.api_server.Entity.NationCity;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


import java.util.*;

public class ProvinceWithCitiesDAO
{
    private JdbcTemplate jdbcTemplate;
    private String input = null;
    private String sql = null;
    private NationCity proWithCity = new NationCity();
    private List<CityBean> city = new ArrayList<CityBean>();
    private CityBean oneCity = new CityBean();
    private List<NationCity> return_list = new ArrayList<NationCity>();
    private List<Map<String, Object>> list;

    public ProvinceWithCitiesDAO()
    {

    }

    public void access()
    {
        if(this.input.equals("all") == false)
            sql = "SELECT pinyin, Name, ID FROM protoen WHERE shortName = '" + input
                    + "' or Name = '" + input + "' or pinyin = '" + input + "'";
        else if(this.input.equals("all"))
            sql = "SELECT protoen.pinyin, protoen.Name, protoen.ID FROM protoen";
        List headerInfo = this.jdbcTemplate.queryForList(sql);
        //Get city English name.
        for(int i = 0; i < headerInfo.size(); i++)
        {
            String pinyin = headerInfo.get(i).toString().split(",")[0].split("=")[1].split("}")[0];
            String Name = headerInfo.get(i).toString().split(",")[1].split("=")[1].split("}")[0];
            String proID = headerInfo.get(i).toString().split(",")[2].split("=")[1].split("}")[0];
            proWithCity.setProID(proID);
            proWithCity.setProNameCN(Name);
            proWithCity.setProNameEN(pinyin);
            sql = "select * from city where map_province_name = '" + Name + "'";
            list =  this.jdbcTemplate.queryForList(sql);
            readInfo(list);
            sortList(city);
            proWithCity.setCities(this.city);
            this.return_list.add(proWithCity);
            proWithCity = new NationCity();
            city = new ArrayList<CityBean>();
            oneCity = new CityBean();
        }
    }

    public void readInfo(List<Map<String, Object>> list)
    {
        for (Map<String, Object> map : list)
        {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null)
            {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                oneCity = new CityBean();
                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();

                    String value = entry.getValue().toString();
                    if(entry.getKey().toString().equals("current_confirmed_count"))
                        oneCity.setCurrentConfirmedCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("confirmed_count"))
                        oneCity.setConfirmedCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("suspected_count"))
                        oneCity.setSusCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("cured_count"))
                        oneCity.setCuredCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("dead_count"))
                        oneCity.setDeadCount(Integer.parseInt(value));
                    else if(entry.getKey().toString().equals("time"))
                        oneCity.setTime(value);
                    else if(entry.getKey().toString().equals("city_name"))
                    {
                        oneCity.setCityNameCn(value);
                        oneCity.setCityNameEn(transferPinyin(value));
                    }
                }
                city.add(oneCity);
            }
        }
    }

    public void sortList(List<CityBean> city)
    {
        Comparator sorting = new Comparator<CityBean>()
        {
            @Override
            public int compare(CityBean c1, CityBean c2)
            {
                if(c1.getConfirmedCount() < c2.getConfirmedCount())
                    return 1;
                else
                    return -1;
            }
        };
        city.sort(sorting);
    }

    //Transfer the China city name to pinyin.
    public String transferPinyin(String chinese)
    {
        String returnName = null;
        if(chinese.substring(0, 2).equals("境外"))
            return "Oversea Input";
        else if(chinese.equals("呼和浩特"))
            return "Hohhot";
        else if(chinese.equals("哈尔滨"))
            return "Harbin";
        else if(chinese.equals("乌鲁木齐"))
            return "Urumchi";
        else if(chinese.substring(0, 2).equals("外地"))
            return "People from other provinces";
        else if(chinese.equals("鄂尔多斯"))
            return "Erdos";
        else if(chinese.substring(0, 2).equals("兵团"))
        {
            String endStr = null;
            if(chinese.equals("兵团第十二师"))
                endStr = numberEn("shier");
            else
                endStr = numberEn(ToPinyin(chinese.substring(3, 4)));
            return "Crops " + endStr;
        }
        else if(chinese.substring(0, 2).equals("待明"))
            return "Unknown Places";
        else
        {
            String firstChar = ToFirstChar(chinese).toUpperCase().substring(0, 1);
            String fullChar = ToPinyin(chinese);
            returnName = firstChar + fullChar.substring(1, fullChar.length());
        }
        return returnName;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public void reset()
    {
        input = null;
        sql = null;
        list = null;
        city =  new ArrayList<CityBean>();
        oneCity = new CityBean();
        proWithCity = new NationCity();
        return_list = new ArrayList<NationCity>();
    }

    public void setJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NationCity> getCityObjects()
    {
       //proWithCity.setCities(this.city);
        return this.return_list;
    }

    //Get the first char of the name; uppercase.
    public String ToFirstChar(String chinese)
    {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++)
        {
            boolean decide = isChinesePunctuation(newChar[i]);
            if(!decide)
            {
                if (newChar[i] > 128)
                {
                    if(chinese.equals("省级（湖北输入）"))
                        System.out.println(newChar[i]);
                    try {
                        pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                        if(pinyinStr == null)
                            continue;
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        //continue;
                        e.printStackTrace();
                    }
                }
                else
                    pinyinStr += newChar[i];
            }
            else
                continue;
        }
            return pinyinStr;
    }

    //Transfer the full name to pinyin.
    public String ToPinyin(String chinese)
    {
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++)
        {
            boolean decide = isChinesePunctuation(newChar[i]);
            if(!decide)
            {
                if (newChar[i] > 128)
                {
                    try {
                        pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                }
                else
                    pinyinStr += newChar[i];
            }
            else
                continue;
        }
        return pinyinStr;
    }

    //Transfer number to English
    public String numberEn(String number)
    {
        String returnNumber = null;
        switch(number)
        {
            case "yi":
                returnNumber = "First Divizio";
                break;
            case "er":
                returnNumber = "Second Divizio";
                break;
            case "san":
                returnNumber = "Third Divizio";
                break;
            case "si":
                returnNumber = "Forth Divizio";
                break;
            case "wu":
                returnNumber = "Fifth Divizio";
                break;
            case "liu":
                returnNumber = "Sixth Divizio";
                break;
            case "qi":
                returnNumber = "Seventh Divizio";
                break;
            case "ba":
                returnNumber = "Eighth Divizio";
                break;
            case "jiu":
                returnNumber = "Nineth Divizio";
                break;
            case "shi":
                returnNumber = "Tenth Divizio";
                break;
            case "shiyi":
                returnNumber = "Eleventh Divizio";
                break;
            case "shier":
                returnNumber = "Twelfth Divizio";
                break;
            default:
                break;
        }
        return returnNumber;
    }

    public boolean isChinesePunctuation(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else
            {
            return false;
        }
    }
}
