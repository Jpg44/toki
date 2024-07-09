import { localaxios } from "./BaseAxios";
import { defineStore } from "pinia";
const local = localaxios();
function refreshTokenGet(token,success,fail){
token=local.get(``).then(success).catch(fail).defineStore;
}
function AccessTokenGet(success,fail){
local.get(``).then(success).catch(fail);
}
function refreshTokenDelete(){

}
function AccessTokenDelete(){

}
export{
    refreshTokenGet,
    refreshTokenDelete,
    AccessTokenGet,
    AccessTokenDelete,
}