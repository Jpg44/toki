import { localaxios } from "./BaseAxios";

const local = localaxios();

async function roomHashTag(roomId, tags, success, fail) {
    const requestData = {
        roomId,
        tags,
    }

    await local.post(`/api/v1/rooms/${roomId}/tags`, JSON.stringify(requestData))
        .then(success)
        .catch(fail)
}

// Admin 권한
async function deleteHashTag(tagId, success, fail) {
    await local.delete(`/api/v1/admin/tags/${tagId}`)
        .then(success)
        .catch(fail)
}

async function updateHashTag(tagData, success, fail) {
    await local.put(`/api/v1/admin/tags`, JSON.stringify(tagData))
        .then(success)
        .catch(fail)
}

export {
    roomHashTag,
    deleteHashTag,
    updateHashTag,
}