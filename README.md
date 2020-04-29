# API Documentation 
This is CNSCC212 Advanced Programming Coursework API Server Part\
Yu Tian (China Part)\
Chongyang Zhao (World Part)\
This project is based on Spring Boot\
Port: 8090

### Dcoker:
docker pull registry.cn-beijing.aliyuncs.com/scc212_group3/api_server


# 0  API Documentation Specifications
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

Please see the detail in the wiki.
https://gitee.com/VisiblePandemic/api_server/wikis/pages

## 1.2  Data Format Examples (Ref. 0.1.1)
### ● 1.2.1  CN_CurrentData
http://112.125.95.205:8090/get/CurrentChina


### ● 1.2.2 CN_CurrentProvince
#### ● One Certain Province
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=Hubei
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=hubei
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=湖北省
##### http://112.125.95.205:8090/get/CurrentProInfo?proName=湖北

#### ● All provinces in China, ordered by total confirmed count DECS
http://112.125.95.205:8090/get/CurrentProInfo

### ● 1.2.3 CN_ProvinceCities
#### ● Cities for one province, ordered by total comfirmed count DECS
##### http://112/125/95.205:8090/get/CurrentCities?proName=Hubei
##### http://112/125/95.205:8090/get/CurrentCities?proName=hubei
##### http://112/125/95.205:8090/get/CurrentCities?proName=湖北
##### http://112/125/95.205:8090/get/CurrentCities?proName=湖北省

#### ● Cities for all provinces in China, including oversea input

### ● 1.2.4 CN_NationHistory
#### ● One certain day
http://112.125.95.205:8090/get/NationHistory?data=20200305

#### ● All days from 2020-01-19
http://112.125.95.205:8090/get/NationHistory

### ● 1.2.5 CN_ProvinceHistory 
#### ● All days for one provinces
http://112.125.95.205:8090/get/getProHistoryData?proName=湖北

#### ● One day for one provinces
http://112.125.95.205:8090/get/getProHistoryData?proName=湖北&date=20200305

### ● 1.2.6 CN_NationChart
####  ● Nation History Chart API
http://112.125.95.205:8090/NationChart?type=nationHistoryChart

####  ● Nation Oversea Input Top 10
http://112.125.95.205:8090/NationChart?type=overSeaInputTop10

####  ● History Information for Hubei Province
http://112.125.95.205:8090/NationChart?type=historyInfoForHubei

####  ● Comparation with Hubei and other provinces in China
http://112.125.95.205:8090/NationChart?type=HubeiCompareOthers

### ● 1.2.7 Medical_Comments
http://112.125.95.205:8090/MedicalComments

### ● 1.2.8 COVID19_CurrentNews
http://112.125.95.205:8090/CurrentNews

### ● 1.2.9 IP_CurProvince
http://112.125.95.205:8090/get/CurrentLocation
#### ● Success

#### ● Fali-IP Address belongs to abroad countries, return the data of Beijing.

### ● 1.2.10 World_currentData
#### ● All countries
http://112.125.95.205:8090/get/Country

#### ● One country
http://112.125.95.205:8090/get/Country?country=英国
http://112.125.95.205:8090/get/Country?country=United Kingdom
http://112.125.95.205:8090/get/Country?country=uk





