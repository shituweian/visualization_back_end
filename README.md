API Documentation
# 0  Specifications
## 0.1  Legend
### 0.1.1  Regex

|Symbol|Specification(s)|
| ---  | --- |
|  {}  |  union  |
|  <>  |  reference, required  |
|  []  |   optional  |
|  ^  |  xor  |
|  blank space  |  ignored  |
|  other  |  plain text  |



# 1  Web API Interfaces
## 1.1 Transmission Format
JSON for transmission over the Internet. 

                                              Table 1-1  API Specifications (Ref. 0.1.1)

| No. | API Name | URL | Data Formate | Remark | Developer
| --- | -------- | --- | ------------ | ------ |----------|
| 0   |     ADDRESS     |   http://112.125.95.205:8090   |       NULL       |    ROOT    | NULL
| 1   |     CN_CurrentData     |  <ADDRESS>/get/CurrentChina   |  Json Object  | Current data for China        | Tian Yu
| 2   |     CN_CurrentProvince |  <Address>/get/CurrentProInfo[?proName=proName]      |   Json Array|       One certain province or all provinces; ordered by total confirmed count DESC      |Tian Yu
| 3   |    CN_ProvinceCities     |  <ADDRESS>/get/CurrentCities[?proName=<En_name  _or_  Cn_ShprtName>]  |      Json Array        |    Cities for one province or all province; sorted by total confirmed count of cities    |Tian Yu
| 4   |     CN_ProvinceHistory     |   <ADDRESS>/get/getProHistoryData[?{&proName=<En_name  _or_  Cn_shortName>]^[&date=<yyyymmdd>}]  |  Json Object            |    One/All province(s) history data for one/all days   | Tian Yu
| 5   |     CN_NationHistory     | <ADDRESS>/get/NationHistory[?date=<yyyymmdddd>]   |       Json Array      |    One or all day for     | Tian Yu
| 6   |      CN_NationChart    |   <ADDRESS>/NationChart[?type=<chosenType>]  |       Json Object       |    chosenType ∈{overSeaInputTop10, nationHistoryChart, historyInfoForHubei, HubeiCompareOthers, provinceCompareFull}| Tian Yu
| 7   |     Medical_Comments     |  <ADDRESS>/MedicalComments   |        Json Array   |    6 medical comments    | Tian Yu
|8|COVID19_CurrentNews|<ADDRESS>/CurrentNews| Json Array | Latest news for covid-19| Tian Yu
|9| IP_CurProvince | <ADDRESS>/get/CurrentLocation | Json Array | Using IP address to get the current province of user; unavailable for abroad countries.|Tian Yu
|10|World_currentData|<ADDRESS>/get/Country?[&country={CountryName ^ null ^ all}]|JSON Array|Current data of the specifeid country. If country is not specified, return current data of all countries, inclouding China. |Chongyang Zhao
|11|World_History|<ADDRESS>/get/WorldHistory?[{&date = yyyymmdd ^ {&startDate = yyyymmdd &endDate = yyyymmdd}}][&country=CountryName ^ all]|JSON Array|History data in certain day of the specified country. If date is unspecified, return all data. If country is not specified, return data of all countries.|Chongyang Zhao
|12|World_History_Sum|<ADDRESS>/get/WorldHistorySum?[{&date=yyyymmdd ^ {&startDate=yyyymmdd&endDate=yyyymmdd}}][&name=ContinentName ^ world ^ all]|JSON Array|When name =ContinentName,return the overview of the continent;When name =world, return the overview of world;When name =all, return the all overview data except the world info|Chongyang Zhao
|13|COVID-19 Knowledge|<ADDRESS>/Knowledge|JSON Array|Some questions and answers about COVID-19|Chongyang Zhao



## 1.2  Data Format Examples (Ref. 0.1.1)
### ● 1.2.1  CN_CurrentData
http://112.125.95.205:8090/get/CurrentChina
```
{
  "nameCN": "中国",
  "nameEn": "China",
  "confirmedCount": "84330",
  "currentConfirmedCount": "1286",
  "curedCount": "78402",
  "deadCount": "4642",
  "suspectedCount": "1399",
  "overseaInput": "1423",
  "locationId": "100000"
}
```



### ● 1.2.2 CN_CurrentProvince
#### ● One Certain Province
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=Hubei
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=hubei
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=湖北省
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=湖北

```
[
  {
    "nameCN": "湖北省",
    "shortName": "湖北",
    "nameEn": "Hubei",
    "confirmedCount": 68128,
    "currentConfirmedCount": 23,
    "curedCount": 63593,
    "deadCount": 4512,
    "suspectedCount": 0,
    "locationId": 420000
  }
]
```
#### ● All provinces in China, ordered by total confirmed count DECS
http://112.125.95.205:8090/get/CurrentProInfo
```
[
  {
    "nameCN": "湖北省",
    "shortName": "湖北",
    "nameEn": "Hubei",
    "confirmedCount": 68128,
    "currentConfirmedCount": 23,
    "curedCount": 63593,
    "deadCount": 4512,
    "suspectedCount": 0,
    "locationId": 420000
  },
  {
    "nameCN": "广东省",
    "shortName": "广东",
    "nameEn": "Guangdong",
    "confirmedCount": 1585,
    "currentConfirmedCount": 43,
    "curedCount": 1534,
    "deadCount": 8,
    "suspectedCount": 11,
    "locationId": 440000
  },...
]
```
### ● 1.2.3 CN_ProvinceCities
#### ● Cities for one province, ordered by total comfirmed count DECS
##### http://112/125/95.205:8090/get/CurrentCities?proName=Hubei
##### http://112/125/95.205:8090/get/CurrentCities?proName=hubei
##### http://112/125/95.205:8090/get/CurrentCities?proName=湖北
##### http://112/125/95.205:8090/get/CurrentCities?proName=湖北省
```
[
  {
    "proNameEN": "Hubei",
    "proNameCN": "湖北省",
    "proID": "420000",
    "cities": [
      {
        "cityNameEn": "Wuhan",
        "cityNameCn": "武汉市",
        "currentConfirmedCount": 23,
        "confirmedCount": 50333,
        "susCount": 0,
        "curedCount": 46441,
        "deadCount": 3869,
        "time": "2020-04-25T23:37:20.237"
      },
      {
        "cityNameEn": "Xiaogan",
        "cityNameCn": "孝感市",
        "currentConfirmedCount": 0,
        "confirmedCount": 3518,
        "susCount": 0,
        "curedCount": 3389,
        "deadCount": 129,
        "time": "2020-04-25T23:37:20.237"
      },...
]
```
#### ● Cities for all provinces in China, including oversea input
```
[
  {
    "proNameEN": "Beijing",
    "proNameCN": "北京市",
    "proID": "110000",
    "cities": [...]
  },
  {
    "proNameEN": "Tianjin",
    "proNameCN": "天津市",
    "proID": "120000",
    "cities": [...]
  },...
]
```
### ● 1.2.4 CN_NationHistory
#### ● One certain day
http://112.125.95.205:8090/get/NationHistory?data=20200305
```
[
  {
    "date": "20200305",
    "confirmedCount": 80711,
    "confirmedIncr": 145,
    "curedCount": 53781,
    "curedIncr": 1684,
    "currentConfirmedCount": 23885,
    "currentCoonfirmedIncr": -1569,
    "deadCount": 3045,
    "deadIncr": 30
  }
]
```
#### ● All days from 2020-01-19
http://112.125.95.205:8090/get/NationHistory
```
[
  {
    "date": "20200119",
    "confirmedCount": 1,
    "confirmedIncr": 1,
    "curedCount": 0,
    "curedIncr": 0,
    "currentConfirmedCount": 1,
    "currentCoonfirmedIncr": 1,
    "deadCount": 0,
    "deadIncr": 0
  },
  {...},...
]
```
### ● 1.2.5 CN_ProvinceHistory 
#### ● All days for one provinces
http://112.125.95.205:8090/get/getProHistoryData?proName=湖北

```
{
  "enName": "Hubei",
  "cnName": "湖北",
  "proId": "420000",
  "pros": [
    {
      "date": "20200120",
      "confirmedCount": "270",
      "confirmedIncr": "270",
      "curedCount": "25",
      "curedIncr": "25",
      "currentConfirmedCount": "239",
      "currentCoonfirmedIncr": "239",
      "deadCount": "6",
      "deadIncr": "6"
    },
    {...},...]
}
```
#### ● One day for one provinces
http://112.125.95.205:8090/get/getProHistoryData?proName=湖北&date=20200305
```
{
  "enName": "Hubei",
  "cnName": "湖北",
  "proId": "420000",
  "pros": [
    {
      "date": "20200305",
      "confirmedCount": "67592",
      "confirmedIncr": "126",
      "curedCount": "41966",
      "curedIncr": "1487",
      "currentConfirmedCount": "22695",
      "currentCoonfirmedIncr": "-1390",
      "deadCount": "2931",
      "deadIncr": "29"
    }
  ]
}
```
### ● 1.2.6 CN_NationChart
####  ● Nation History Chart API
http://112.125.95.205:8090/NationChart?type=nationHistoryChart
```
{
  "chartName": "Nation Increase-Information Chart",
  "comment": "x1 is date; y1 is total confirmed, y2 is confirmed increase; y3 is current confirmed county4 is total cured count; y5 is total dead count.",
  "echartX1": [
    "20200119",
    "20200120",
    "20200121",
    "20200122",
    "20200123",
    "20200124",
    "20200125",
    "20200126",
    "20200127",
    ...],
  "echartX2": [],
  "echartY1": [
     1,
     293,
     435,
     567,
     866,
     1335,
     2015,
     2790,
     4533,
    ...],
  "echartY2": [
    1,
    292,
    142,
    132,
    299,
    469,
    680,
    775,
    1743,
    1477,
    ...],
  "echartY3": [
    1,
    262,
    398,
    522,
    809,
    ...],
  "echartY4": [
    0,
    25,
    28,
    28,
    32,
    ...],
  "echartY5": [
    0,
    6,
    9,
    17,
    25,
    ...]
}
```
####  ● Nation Oversea Input Top 10
http://112.125.95.205:8090/NationChart?type=overSeaInputTop10
```
{
  "chartName": "Oversea Input Top10",
  "comment": "Oversea Input Top10 of China",
  "echartX1": [
    "黑龙江省",
    "上海市",
    "北京市",
    ...],
  "echartX2": [
    "Heilongjiang",
    "Shanghai",
    "Beijing",
    "Inner Mongolia",
    ...],
  "echartY1": [
    386,
    302,
    174,
    121,
    64,
    ...],
  "echartY2": [],
  "echartY3": [],
  "echartY4": [],
  "echartY5": []
}
```
####  ● History Information for Hubei Province
http://112.125.95.205:8090/NationChart?type=historyInfoForHubei
```
{
  "chartName": "Hubei covid_19 info",
  "comment": "x is date; y1 is confirmed count; y2 is cured count; y3 is dead count",
  "echartX1": [
    "20200120",
    "20200121",
    "20200122",
    ...],
  "echartX2": [],
  "echartY1": [
    270,
    375,
    444,
    ...],
  "echartY2": [
    25,
    28,
    28,
    ...],
  "echartY3": [
    6,
    9,
    17,
    ...],
  "echartY4": [],
  "echartY5": []
}

```
####  ● Comparation with Hubei and other provinces in China
http://112.125.95.205:8090/NationChart?type=HubeiCompareOthers
```
{
  "chartName": "Compare Hubei and other provinces",
  "comment": "x1 is date; y1 is Hubei confirmed count; y2 is Hubei dead count; y3 is other total confirmed count; y4 is other total dead count",
  "echartX1": [
    "20200120",
    "20200121",
    "20200122",
    ...],
  "echartX2": [],
  "echartY1": [
    270,
    375,
    444,
    549,
    ...],
  "echartY2": [
    6,
    9,
    17,
    ...],
  "echartY3": [
    23,
    60,
    123,
    ...],
  "echartY4": [
    0,
    0,
    0,
    ...],
  "echartY5": []
}
```
### ● 1.2.7 Medical_Comments
http://112.125.95.205:8090/MedicalComments
```
[
  {
    "title": "1. What is COVID-19?",
    "contents": [
      "The International Committee on Taxonomy of viruses is a newly discovered coronavirus, named SARS-Cov-2. Because of the lack of immunity to new virus strains, people are generally susceptible. Novel coronavirus is novel coronavirus pneumonia. WHO is caused by SARS-Cov-2 coronavirus, and COVID-19 is named SARS-Cov-2 by the virus. Most of the infections can lead to pneumonia, and it is called new coronavirus pneumonia / new crown pneumonia. ",
      "At present, the initial cases are from the seafood market in Wuhan. The initial source of infection is wild animals, possibly the Chinese chrysanthemum head bat. Up to now novel coronavirus pneumonia novel coronavirus infected pneumonia diagnosis scheme (trial version sixth) has been reported. The main source of infection is new coronavirus pneumonia, and asymptomatic people can also be called the source of infection.",
      null,
      null,
      null,
    ...],
    },
    {...}
]
```
### ● 1.2.8 COVID19_CurrentNews
http://112.125.95.205:8090/CurrentNews
```
[
  {
    "id": 34977,
    "pubDate": "Sun Apr 26 10:04:16 CST 2020",
    "pubDateStr": "12分钟前",
    "title": "Zhejiang province reported on the 26th that no new confirmed cases of newly diagnosed coronal pneumonia had been imported from overseas.",
    "summary": "From 0 to 24 o'clock on April 25, no new confirmed cases of newly imported coronal pneumonia were found in Zhejiang province.  One new case was discharged from hospital.  There were 3 new asymptomatic patients (all imported from Italy).  No confirmed cases were transferred on that day, and no isolation was lifted on that day.  As of 2400 hours on April 25, 56 asymptomatic patients (44 of whom were imported from abroad) were still under medical observation.",
    "infoSourse": "CCTV News app",
    "sourceUrl": "http://app.cctv.com/special/cportal/detail/arti/index.html?id=ArtiYxACvx08mjF6VdjapYXs200426&isfromapp=1"
  },
  {...}
]
```
### ● 1.2.9 IP_CurProvince
http://112.125.95.205:8090/get/CurrentLocation
#### ● Success
```
{
  "status": 0,
  "comments": "success",
  "curProvince": [
    {
      "nameCN": "内蒙古自治区",
      "shortName": "内蒙古",
      "nameEn": "Inner Mongolia",
      "confirmedCount": 198,
      "currentConfirmedCount": 76,
      "curedCount": 121,
      "deadCount": 1,
      "suspectedCount": 34,
      "locationId": 150000
    }
  ]
}
```
#### ● Fali-IP Address belongs to abroad countries, return the data of Beijing.
```
{
  "status": 1,
  "comments": "Sorry, the your location ip address unavailable. May be ip is not belongs to China. Return the data for Beijing.",
  "curProvince": [
    {
      "nameCN": "北京市",
      "shortName": "北京",
      "nameEn": "Beijing",
      "confirmedCount": 593,
      "currentConfirmedCount": 60,
      "curedCount": 525,
      "deadCount": 8,
      "suspectedCount": 164,
      "locationId": 110000
    }
  ]
}
```
### ● 1.2.10 World_currentData
#### ● All countries
http://112.125.95.205:8090/get/Country
```
[
  {
    "country_name": "不丹",
    "country_name_en": "Bhutan",
    "continent_name": "亚洲",
    "continent_name_en": "Asia",
    "current_confirmed_count": 7,
    "confirmed_count": 7,
    "suspected_count": 0,
    "cured_count": 0,
    "dead_count": 0,
    "location_id": 953002,
    "time": "2020-04-27T23:28:27.205"
  },
  ...
]
```
#### ● One country
http://112.125.95.205:8090/get/Country?country=英国
http://112.125.95.205:8090/get/Country?country=United Kingdom
http://112.125.95.205:8090/get/Country?country=uk
```
[
  {
    "country_name": "英国",
    "country_name_en": "United Kingdom",
    "continent_name": "欧洲",
    "continent_name_en": "Europe",
    "current_confirmed_count": 131764,
    "confirmed_count": 152840,
    "suspected_count": 0,
    "cured_count": 344,
    "dead_count": 20732,
    "location_id": 961007,
    "time": "2020-04-27T23:28:25.861"
  }
]
```




