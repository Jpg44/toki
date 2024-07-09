import { localaxios } from "./BaseAxios"
const local=localaxios();
async function commonaxios(success,fail){
await local.get("").then(success).catch(fail);
}
async function commonpostaxios(value,success,fail){
  await local.post("",JSON.stringify(value)).then(success).catch(fail);
  }

// GET 모든 뱃지 Read
async function badgesRead(success, fail){
    await local.get("/api/v1/badges")
      .then(success)
      .catch(fail)

    }

// POST 뱃지 create
async function badgeCreate(value, success, fail){
  await local.post("/api/v1/admin/badges", JSON.stringify(value))
    .then(success)
    .catch(fail)
}

// GET 배너 read
async function bannerRead(success, fail){
  await local.get("/api/v1/banner")
    .then(success)
    .catch(fail)
}

// POST 배너 create
async function bannerCreate(value,success, fail){
  await local.post("/api/v1/banner", JSON.stringify(value))
    .then(success)
    .catch(fail)
}

// PUT 배너 update
async function bannerPut(value, success, fail){
  await local.put(`/api/v1/banner/${value.idx}`, JSON.stringify(value))
    .then(success)
    .catch(fail)
}

// DELETE 배너 delete
async function bannerDelete(value, success, fail){
  await local.delete(`/api/v1/banner/${value.idx}`, JSON.stringify(value))
    .then(success)
    .catch(fail)
}
async function putaxios(value,success,fail){
    await local.put("/put",JSON.stringify(value)).then(success).catch(fail);
    console.log(JSON.stringify(value),"saf");
    }

async function postaxios(value,success,fail){
    await local.post(`/post`,JSON.stringify(value)).then(success).catch(fail);
    console.log(JSON.stringify(value),"saf");
    }
export{
    commonaxios,
    badgesRead,
    badgeCreate,
    bannerRead,
    bannerCreate,
    bannerPut,
    bannerDelete,
    postaxios,
    putaxios,
    commonpostaxios,
}