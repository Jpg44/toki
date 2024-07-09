import { localaxios } from "./BaseAxios";

const local = localaxios();

async function roomSearchByTag(tagName, success, fail) {
    await local.get(`/api/v1/rooms/search/tag?tagName=${tagName}`)
        .then(success)
        .catch(fail)
}

async function roomSearchByCategory(categoryNum, pageNumber, success, fail) {
    await local.get(`/api/v1/rooms/search/category?categoryPk=${categoryNum}&paage=${pageNumber}`)
        .then(success)
        .catch(fail)
}

export {
    roomSearchByTag,
    roomSearchByCategory,
}