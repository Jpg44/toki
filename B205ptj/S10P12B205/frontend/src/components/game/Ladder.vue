<template>

  <!-- 게임의 제목과 캔버스 요소를 표시하는 컴포넌트 -->
  <div class="ladder-game">
    <h1>사다리 타기 게임</h1>
    <!-- 캔버스 요소 -->
    <canvas ref="canvasRef" class="ladder-canvas" width="500" height="300"></canvas>
    <!-- 참가자 수 입력 필드와 게임 시작 버튼 -->
    <label for="participants">참가자 수:</label>
    <input type="number" id="participants" v-model.number="participants">
    <button @click="startGame" class="start-button">게임 시작</button>
    <!-- 우승자 표시 -->
    <div v-if="resultIf" v-for="index in participants" :key="index">
      <input type="text" v-model="setName[index-1]" :placeholder="'출발점 ' + index">
      <input type="text" v-model="setResult[index-1]" :placeholder="'도착점 ' + index">
    </div>
    <div v-if="!resultIf" v-for="index in participants" :key="index">
      <p> {{ endResult[index-1].startPoint }}->> {{endResult[index-1].endPoint }}</p>
    </div>
    
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 변수 선언
const participants = ref(6); // 참가자 수
const resultIf=ref(true);//그릴지 말지 랜덤 수
const canvasRef = ref(null); // 캔버스 요소의 참조
const draw=ref();//그릴지 말지 랜덤 수
const rowArray = ref(Array.from({ length: participants.value }, () => Array.from({ length: 10}, () => null)));//전에 그렸는지 확인
const result =ref([])
const setName=ref(Array.from({ length: participants.value },(_, index) => index + 1));
const setResult=ref(Array.from({ length: participants.value },(_, index) => index + 1));
const endResult =ref(Array.from({ length: participants.value },() =>({
  startPoint:'',
  endPoint:''
})))
// 게임 시작 함수
const startGame = () => {
  console.log('if값 안나오냐',resultIf.value)
  drawLadder(); // 사다리 그리기
  drawGhostLeg(); // 가상 가로선 그리기
  console.log('if값 안나오냐',resultIf.value)
  resultIf.value=!resultIf.value;
  
};

// 사다리 그리기 함수
const drawLadder = () => {
  let canvas = canvasRef.value; // 캔버스 요소 가져오기
  let ctx = canvas.getContext('2d'); // 2D 컨텍스트 가져오기
  ctx.resetTransform()
  // 캔버스 초기화
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.strokeStyle = 'brown'//그리는 선 색상
  //ctx.lineWidth='5' //선 두께
  //ctx.lineCap='round' //그리는선 끝 스타일
  // 참가자 수만큼의 세로선 그리기
  let verticalSpacing = canvas.width / (participants.value + 1); // 세로선 사이의 간격 계산
  for (let i = 1; i <= participants.value; i++) {
    let x = i * verticalSpacing; // x 좌표 계산
    ctx.lineWidth='6'; //선 두께
    //ctx.lineCap='round'; //그리는선 끝 스타일
    ctx.beginPath(); // 새로운 경로 시작
    ctx.moveTo(x, 0); // 시작점 설정
    ctx.lineTo(x, canvas.height); // 끝점 설정
    ctx.stroke(); // 선 그리기
  }
};

// 가상 가로선 그리기 함수
let currentRow = 0; 
const drawGhostLeg = () => {
  let canvas = canvasRef.value; // 캔버스 요소 가져오기
  let ctx = canvas.getContext('2d'); // 2D 컨텍스트 가져오기
  let verticalSpacing = canvas.width / (participants.value + 1); // 세로선 사이의 간격 계산
  let horizontalSpacing = canvas.height / 5; // 가로선 사이의 간격 계산

  currentRow = 0; // 현재 행 초기화
  let currentPosition = 0; // 현재 위치 초기화
  let start = null; // 출발점 초기화
  let end = null; // 도착점 초기화
  let direction = 1
  let y
  let ydel
  let jw
  // 캔버스 높이까지 반복
  //while (currentRow < canvas.height) {
    for(let j=0;j<participants.value-1;j++){
      currentPosition += direction;
      currentRow = 0;
    for(currentRow;currentRow<6;currentRow++){
   // currentPosition += direction; // 현재 위치 이동
    // if (currentPosition < 0 || currentPosition >= participants.value + 1) {
    //   // 범위를 벗어나면 반대 방향으로 설정하고 다음 행으로 이동
    //   currentPosition -= direction;
    //   //direction *= -1;
    //   currentRow++; 
    //   console.dir('값',currentRow);
    
    // }
    // 첫 번째 출발점과 마지막 도착점 저장

    draw.value=Math.random()*3;
    //console.dir(draw.value)
    // 세로선 사이에만 가로선 그리기
    //if (draw.value>=1&&currentRow < canvas.height - 1) {
     if(j<=0){
      jw=0;
     }else{
      jw=j-1;
     }
    if (draw.value>=1&&currentRow < canvas.height - 1&&rowArray.value[jw][currentRow]!=1) {
     // if (currentRow < canvas.height - 1) {
      let startX = currentPosition * verticalSpacing; // 가로선 시작점의 x 좌표 계산
      let endX = (currentPosition + direction) * verticalSpacing; // 가로선 끝점의 x 좌표 계산
      
       let startY = currentRow * horizontalSpacing + horizontalSpacing / 2; // 가로선 시작점의 y 좌표 계산

      let endY = currentRow * horizontalSpacing + horizontalSpacing / 2; // 가로선 끝점의 y 좌표 계산
      //ctx.setLineDash([5,5])//파선
      ctx.lineWidth='4' //선 두께
       ctx.beginPath(); // 새로운 경로 시작
      ctx.moveTo(startX, startY); // 시작점 설정
      ctx.lineTo(endX, endY); // 끝점 설정
      ctx.stroke(); // 선 그리기
      
      console.log('j:',j,'row:',currentRow,'이젠');
     rowArray.value[j][currentRow]=1;
      }
      else{
        rowArray.value[j][currentRow]=0;
      }
    }
    
  }
  let changeResult
  for(let i=0;i<participants.value;i++){
    changeResult=i
    console.log('처음값:',changeResult);
    for(let j=0;j<5;j++){
      if(changeResult<participants.value-1&&rowArray.value[changeResult][j]==1){
        changeResult++;
      }
      else if(changeResult>0&&rowArray.value[changeResult-1][j]==1){
        changeResult--
      }
    }
    console.log('바뀐값:',changeResult);
    result.value[i]=changeResult;
    console.log('결과:',result.value[i]);
  }
  for(let i=0;i<participants.value;i++){
    endResult.value[i].startPoint=setName.value[i]
    endResult.value[i].endPoint=setResult.value[result.value[i]]
  }
  // 우승자 출발점과 도착점 설정
  console.log('결과:',result.value);
};
</script>

<style scoped>
/* 스타일 */
.ladder-game {
  text-align: center;
}

.ladder-canvas {
  
}

.start-button {
  margin-top: 10px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #F9C95E;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
.start-button:hover {
  background-color: #F9B02C;
}

.winner {
  margin-top: 20px;
  font-weight: bold;
}
</style>