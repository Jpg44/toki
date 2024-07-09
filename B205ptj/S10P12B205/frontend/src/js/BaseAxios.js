import axios from "axios";
const { VITE_VUE_API_URL, VITE_ELECTRIC_CHARGING_STATION_URL } = import.meta.env;
const token = localStorage.getItem('accessToken')
function localaxios(){
const instance = axios.create({
    baseURL: "https://i10b205.p.ssafy.io",

    //응답을 아래 형식으로 받음
    headers:{
        "Content-Type": "application/json;charset=utf-8",
    },
    "Access-Control-Allow-Origin":"https://i10b205.p.ssafy.io",
    "Access-Control-Allow-Credentials":true,
    withCredentials:true
})
//request시  아래
instance.defaults.headers.common["Authorization"]=`Bearer ${token}`;
instance.defaults.headers.post["Content-Type"]="application/json";
instance.defaults.headers.put["Content-Type"]="appication/json"
return instance;
}
function statusaxios(){
    const instance = axios.create({
        baseURL:VITE_ELECTRIC_CHARGING_STATION_URL,
        headers:{
            "Content-Type": "application/json;charset=utf-8",
        }
    })
    return instance;
    }
export{localaxios,statusaxios};