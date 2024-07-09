import { localaxios } from "./BaseAxios"

const local = localaxios();

async function memberDetail(userId, success, fail) {
    await local.get(`/api/v1/users/${userId}?info_type=detail`)
        .then(success)
        .catch(fail)
}

async function memberSimple(userId, success, fail) {
    await local.get(`/api/v1/users/${userId}?info_type=simple`)
        .then(success)
        .catch(fail)
}

async function memberUpdate(userId, userData, success, fail) {
    await local.put(`/api/v1/users/${userId}`, JSON.stringify(userData))
        .then(success)
        .catch(fail)
}

async function otherMemberDetail(userId, success, fail) {
    await local.get(`/api/v1/users/others/${userId}`)
        .then(success)
        .catch(fail)
}

export {
    memberDetail,
    memberSimple,
    memberUpdate,
    otherMemberDetail,
}