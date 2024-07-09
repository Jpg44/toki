import { localaxios } from "./BaseAxios"

const local = localaxios();

// GET 받은 친구 요청 조회 {isFriend = true (친구 목록 조회) / isFriend = false (받은 친구 요청 조회)}
async function friendListSearch(isFriend, success, fail) {
    await local.get(`/api/v1/friends?isFriend=${isFriend}`)
        .then(success)
        .catch(fail)
}

// POST 친구 요청
async function friendListAdd(value, success, fail) {
    await local.post(`/api/v1/friends`, JSON.stringify(value))
        .then(success)
        .catch(fail)
}

// PUT 친구 수락 또는 거절
async function friendAcceptanceRejection(userId, respondFriend, success, fail) {
    const requestData = {
        userId: userId,
        respondFriend: respondFriend,
    }
    await local.put('/api/v1/friends', JSON.stringify(requestData))
        .then(success)
        .catch(fail)
}

// DELETE 친구 삭제
async function friendDelete(friendId, success, fail) {
    await local.delete(`/api/v1/friends/${friendId}`)
        .then(success)
        .catch(fail)
}

export {
    friendListSearch,
    friendListAdd,
    friendAcceptanceRejection,
    friendDelete,
}