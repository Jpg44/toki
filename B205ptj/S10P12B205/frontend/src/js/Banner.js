import { localaxios } from "./BaseAxios"

const local = localaxios();

async function bannerCheck(success, fail) {
    await local.get(`/api/v1/banners`)
        .then(success)
        .catch(fail)
}

async function bannerAdd(value, success, fail) {
    await local.post(`/api/v1/banners`, JSON.stringify(value))
        .then(success)
        .catch(fail)
}

async function bannerUpdate(value, success, fail) {
    await local.put(`/api/v1/banners`, JSON.stringify(value))
        .then(success)
        .catch(fail)
}

async function bannerDelete(bannerId, success, fail) {
    await local.delete(`/api/v1/banners/${bannerId}`)
        .then(success)
        .catch(fail)
}

export {
    bannerCheck,
    bannerAdd,
    bannerUpdate,
    bannerDelete
}