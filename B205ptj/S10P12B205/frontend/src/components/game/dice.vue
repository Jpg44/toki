<template>
  <div class="dice-game">
    <h1>주사위 게임</h1>
    <button @click="startGame" :disabled="rolling || result !== null" class="roll-button">{{ rolling ? '굴리는 중...' : '주사위 굴리기' }}</button>
    <div class="dice-container">
      <div v-if="rolling" class="rolling-number">
        <span>{{ currentNumber }}</span>
      </div>
      <div v-else-if="result !== null" class="result">
        <span>{{ result }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const currentNumber = ref(1);
const rolling = ref(false);
const result = ref(null);

const startGame = () => {
  if (rolling.value || result.value !== null) return;

  rolling.value = true;
  let number = 1;
  let count = 0;
  const rollInterval = setInterval(() => {
    count++;
    if (count >= 10) { // 숫자가 랜덤하게 변화할 시간을 조정할 수 있습니다.
      clearInterval(rollInterval);
      rolling.value = false;
      result.value = currentNumber.value;
      return;
    }
    number = Math.floor(Math.random() * 6) + 1;
    currentNumber.value = number;
  }, 100); // 0.1초마다 숫자가 변함 (숫자 변화 속도)
};
</script>

<style scoped>
.dice-game {
  text-align: center;
}

.roll-button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #F9C95E;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.roll-button:hover {
  background-color: #F9B02C;
}

.dice-container {
  display: inline-block;
}

.rolling-number {
  font-size: 36px;
  font-weight: bold;
  border: 2px solid #333;
  border-radius: 10px;
  padding: 20px;
  animation: rollAnimation 0.3s infinite;
}

@keyframes rollAnimation {
  0% { opacity: 0.1; }
  50% { opacity: 1; }
  100% { opacity: 0.1; }
}

.result {
  font-size: 36px;
  font-weight: bold;
  margin-top: 20px;
}
</style>