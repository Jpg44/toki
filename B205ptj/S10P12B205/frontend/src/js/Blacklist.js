import { localaxios } from "./BaseAxios"

const local = localaxios();
// async function userInfo(userPk, param, success, fail){
//     await local.get(`/api/v1/users/` + userPk.idx, { params: param }).then(success).catch(fail);
//     console.log(local.get(`/api/v1/users/` + userPk.idx, { params: param }))
// }

async function blackListsearch(success, fail) {
    await local.get('/api/v1/blacklist')
        .then(success)
        .catch(fail)
}

async function blackListAdd(value, success, fail) {
    await local.post('/api/v1/blacklist', JSON.stringify(value))
        .then(success)
        .catch(fail)
}

async function blacklistRemove(userId, success, fail) {
    await local.delete(`/api/v1/blacklist/${userId}`)
        .then(success)
        .catch(fail)
}

export{    
    blackListsearch,
    blackListAdd,
    blacklistRemove,
}