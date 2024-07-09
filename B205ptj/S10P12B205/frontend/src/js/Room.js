import { localaxios } from "./BaseAxios";

const local = localaxios();

async function roomCreate(roomData, success, fail) {
    await local.post('/api/v1/rooms', JSON.stringify(roomData))
        .then(success)
        .catch(fail);
}

async function roomJoin(roomId, sessionData, success, fail) {
    await local.post(`/api/v1/rooms/${roomId}`, JSON.stringify(sessionData))
        .then(success)
        .catch(fail);
}

async function roomDetail(roomId, success, fail) {
    await local.get(`/api/v1/admin/rooms/${roomId}`)
        .then(success)
        .catch(fail)
}

async function roomSimple(pageNumber, success, fail) {
    await local.get(`/api/v1/rooms?page=${pageNumber}`)
        .then(success)
        .catch(fail)
}

async function roomUpdate(roomId, roomData, success, fail) {
    await local.put(`/api/v1/rooms/${roomId}`, JSON.stringify(roomData))
        .then(success)
        .catch(fail)
}

async function roomLeave(roomId, success, fail) {
    await local.delete(`/api/v1/rooms/${roomId}`)
        .then(success)
        .catch(fail)
}

export{
    roomCreate,
    roomJoin,
    roomDetail,
    roomSimple,
    roomUpdate,
    roomLeave,
}
