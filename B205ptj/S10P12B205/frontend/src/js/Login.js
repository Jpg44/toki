import { localaxios } from "./BaseAxios";

const local = localaxios();

function loginNaver(success, fail) {
    local.get('/oauth2/authorization/naver')
        .then(success)
        .catch(fail)
}


function loginKakao(success, fail) {
    local.get('/oauth2/authorization/kakao')
        .then(success)
        .catch(fail)
}

function logout(success, fail) {
    local.get('/api/v1/users/logout')
        .then(success)
        .catch(fail)
}

function accountDelete(userId, success, fail) {
    local.delete(`/api/v1/users/${userId}`)
        .then(success)
        .catch(fail)
}



export{
    loginNaver,
    loginKakao,
    logout,
    accountDelete,
}