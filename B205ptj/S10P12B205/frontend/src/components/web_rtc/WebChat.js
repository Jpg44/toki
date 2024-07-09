import {Client} from '@stomp/stompjs';
import UserDisplayVue from './UserDisplay.vue';
const stompClient = new Client;
//stompClient.brokerURL='ws://localhost:8080/gs-guide-websocket';
stompClient.brokerURL='wss://i10b205.p.ssafy.io/ws/chat';
let message;
stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
     //stompClient.subscribe('/topic/greetings', (greeting) => {
        stompClient.subscribe('/pubChat', (greeting) => {
        message=JSON.parse(greeting.body).content;
        console.dir("메세지뭔데");
    console.dir(message);
    });
    console.dir("이게되야한다");
    console.dir(data);
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

// function setConnected(connected) {
//     ("#connect").prop("disabled", connected);
//     ("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         ("#conversation").show();
//     }
//     else {
//         ("#conversation").hide();
//     }
//     ("#greetings").html("");
// }

function connect() {
    stompClient.activate();
    console.dir("연결");
}

function disconnect() {
    stompClient.deactivate();
   setConnected(false);
    console.log("Disconnected");
}

function sendName(value) {
    console.dir(value);
    stompClient.publish({
        //destination: "/app/hello",
        destination: "/subChat",
        body: JSON.stringify('name'+ value)
    });
    console.dir("보내기");
}

function showGreeting(msg) {
    console.dir("받는 메세지");
    msg=message;
    console.dir(message);
}


export{
    connect,
    disconnect,
    sendName,
    showGreeting,
}

