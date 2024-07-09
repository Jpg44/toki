import { localaxios } from "./BaseAxios"
function click (change){
    console.log("fagag",change.value);
    change.value= !change.value;
    console.log("fff",change.value);
  }
  async function commonaxios1(success,fail){
    await local.get("/topic/greetings").then(success).catch(fail);
    }
  
  export{
    click,
    commonaxios1,
  }
