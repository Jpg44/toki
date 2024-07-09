import { localaxios } from "./BaseAxios";

const local = localaxios();

async function messageSend(messageData, success, fail) {
    await local.post(`/api/v1/messages`, JSON.stringify(messageData))
        .then(success)
        .catch(fail)
}

async function messageCheck(typeData, success, fail) {
    await local.get(`/api/v1/messages?page=${typeData}`)
        .then(success)
        .catch(fail)
}

async function messageDetail(messageId, success, fail) {
    await local.get(`/api/v1/messages/${messageId}`)
        .then(success)
        .catch(fail)
}

async function messageDelete(messageId, success, fail) {
    await local.delete(`/api/v1/messages/${messageId}`)
        .then(success)
        .catch(fail)
}

export {
    messageSend,
    messageCheck,
    messageDetail,
    messageDelete,
}