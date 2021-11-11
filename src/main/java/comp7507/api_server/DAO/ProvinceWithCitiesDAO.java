package comp7507.api_server.DAO;

import comp7507.api_server.Entity.NationCity;
import org.springframework.jdbc.core.JdbcTemplate;
import comp7507.api_server.Entity.CityBean;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


import java.util.*;

/*
@ Intro: Returns the data of cities in each provinces
         Use of pinyin4j.jar as a translation tool.
         Some of city names cannot be translated correctly, so I translated them manually.
@ Author: Tia Yu
@ Date: 2020.04.02
 */

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
            sql = "SELECT * FROM city WHERE map_province_name = '" + Name + "'";
            list =  this.jdbcTemplate.queryForList(sql);
            readInfo(list, Name);
            sortList(city);
            proWithCity.setCities(this.city);
            this.return_list.add(proWithCity);
            proWithCity = new NationCity();
            city = new ArrayList<CityBean>();
            oneCity = new CityBean();
        }
    }

    public void readInfo(List<Map<String, Object>> list, String proName)
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
                        //processCn(value, proName);
                        oneCity.setCityNameCn(processCn(value, proName));
                        oneCity.setCityNameEn(transferPinyin(value));
                    }
                }
                city.add(oneCity);
            }
        }
    }

    public String processCn(String cityName,String proName)
    {
        if(cityName.equals("境外输入") || cityName.equals("境外输入人员")
        || cityName.substring(0, 2).equals("外地") || (cityName.substring(0, 2).equals("兵团") &&
                !cityName.equals("兵团第六师五家渠市") && !!cityName.equals("兵团第八师石河子市"))
        || cityName.equals("省级（湖北输入）") || cityName.equals("省十里丰监狱"))
            return cityName;
        if(!proName.equals("北京市") && !proName.equals("天津市")
        && !proName.equals("上海市") && !proName.equals("重庆市"))
        {
            if(proName.equals("新疆维吾尔自治区"))
            {
                if(cityName.equals("伊犁州"))
                    return "伊犁哈萨克自治州";
                else if(cityName.equals("吐鲁番市"))
                    return cityName;
                else if(cityName.equals("巴州"))
                    return "巴音郭楞蒙古自治州";
                else if(cityName.equals("兵团第六师五家渠市"))
                    return "五家渠市";
                else if(cityName.equals("兵团第八师石河子市"))
                    return "石河子市";
                else if(cityName.equals("昌吉州"))
                    return "昌吉回族自治州";
                else if(cityName.equals("阿克苏地区"))
                    return cityName;
            }
            else if(proName.equals("内蒙古自治区"))
            {
                if(cityName.equals("锡林郭勒盟"))
                    return cityName;
                else if(cityName.equals("乌海市"))
                    return cityName;
                else if(cityName.equals("兴安盟"))
                    return cityName;
            }
            else if(proName.equals("甘肃省"))
            {
                if(cityName.equals("甘南"))
                    return "甘南藏族自治州";
                else if(cityName.equals("临夏"))
                    return "临夏回族自治州";
            }
            else if(proName.equals("青海省"))
            {
                if(cityName.equals("海北州"))
                    return "海北藏族自治州";
            }
            else if(proName.equals("四川省"))
            {
                if(cityName.equals("甘孜州"))
                    return "甘孜藏族自治州";
                else if(cityName.equals("阿坝州"))
                    return "阿坝藏族羌族自治州";
                else if(cityName.equals("凉山州"))
                    return "凉山彝族自治州";
            }
            else if(proName.equals("云南省"))
            {
                if(cityName.equals("西双版纳"))
                    return "西双版纳傣族自治州";
                else if(cityName.equals("大理州"))
                    return "大理白族自治州";
                else if(cityName.equals("红河州"))
                    return "红河哈尼族彝族自治州";
                else if(cityName.equals("德宏州"))
                    return "德宏傣族景颇族自治州";
                else if(cityName.equals("楚雄州"))
                    return "楚雄彝族自治州";
                else if(cityName.equals("文山州"))
                    return "文山壮族苗族自治州";
                else if(cityName.substring(0, 2).equals("怒江"))
                    return "怒江傈僳族自治州";
                else if(cityName.substring(0, 2).equals("迪庆"))
                    return "迪庆藏族自治州";
            }
            else if(proName.equals("贵州省"))
            {
                if(cityName.equals("黔南州"))
                    return "黔南布依族苗族自治州";
                else if(cityName.equals("黔东南州"))
                    return "黔东南苗族侗族自治州";
                else if(cityName.equals("黔西南州"))
                    return "黔西南布依族苗族自治州";
            }
            else if(proName.equals("湖南省"))
            {
                if(cityName.equals("湘西自治州"))
                    return "湘西土家族苗族自治州";
            }
            else if(proName.equals("湖北省"))
            {
                if(cityName.equals("神农架林区"))
                    return "神农架林区";
                else if(cityName.equals("恩施州"))
                    return "恩施土家族苗族自治州";
            }
            else if(proName.equals("海南省"))
            {
                if(cityName.equals("琼中"))
                    return "琼中黎族苗族自治县";
                else if(cityName.equals("保亭"))
                    return "保亭黎族苗族自治县";
                else if(cityName.equals("陵水"))
                    return "陵水黎族自治县";
                else if(cityName.equals("乐东"))
                    return "乐东黎族自治县";
                else if(cityName.equals("昌江"))
                    return "昌江黎族自治县";
                else if(cityName.equals("临高"))
                    return "临高县";
                else if(cityName.equals("白沙"))
                    return "白沙黎族自治县";
                else if(cityName.equals("澄迈"))
                    return "澄迈县";
                else if(cityName.equals("定安"))
                    return "定安县";
            }
            else if(proName.equals("吉林省"))
            {
                if(cityName.equals("延边"))
                    return "延边朝鲜族自治州";
            }
            else if(cityName.equals("台湾"))
                return cityName;
            return cityName = cityName + "市";
        }
        return cityName;
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
        else if(chinese.equals("香港"))
            return "Hong Kong";
        else if(chinese.equals("澳门"))
            return "Macaw";
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
