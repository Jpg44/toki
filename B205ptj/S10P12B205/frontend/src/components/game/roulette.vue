 <script setup>
  import { ref, reactive } from 'vue';
  
  const numbers = ref([]);
  const rolling = ref(false);
  const result = ref(null);
  const inputValue = ref('');
  
  const addNumber = () => {
    if (!inputValue.value.trim()) return;
    numbers.value.push(inputValue.value);
    inputValue.value = '';
  };
  
  const startGame = () => {
    if (rolling.value || numbers.value.length === 0) return;
  
    rolling.value = true;
    let index = 0;
    const rollInterval = setInterval(() => {
      index = Math.floor(Math.random() * numbers.value.length);
      result.value = numbers.value[index];
    }, 100); // 0.1초마다 결과가 변함 (룰렛 돌리는 속도)
  
    setTimeout(() => {
      clearInterval(rollInterval);
      rolling.value = false;
    }, 3000); // 3초 후에 룰렛 돌리기가 멈추고 결과가 확정됨
  };
  </script>
  <template>
    <div class="roulette-game">
      <h1>룰렛 게임</h1>
      <input v-model="inputValue" @keyup.enter="addNumber" :disabled="rolling">
      <button @click="startGame" :disabled="rolling || numbers.length === 0" :class="{ 'disabled': rolling || numbers.length === 0 }">{{ rolling ? '돌리는 중...' : '룰렛 돌리기' }}</button>
      <div class="result" v-if="result !== null">결과: {{ result }}</div>
      <div class="numbers">
        <div v-for="(number, index) in numbers" :key="index" class="number">{{ number }}</div>
      </div>
    </div>
  </template>
  
  <style scoped>
  .roulette-game {
    text-align: center;
  }
  
  input {
    padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s ease;
  }
  
  button {
    margin-left: 10px;
    padding: 10px 20px;
    font-size: 16px;
    background-color: #F9C95E;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #F9B02C;
  }
  
  button.disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  
  .result {
    margin-top: 20px;
    font-size: 24px;
  }
  
  .numbers {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
  
  .number {
    padding: 10px 20px;
    margin: 0 5px;
    border: 1px solid #333;
    border-radius: 5px;
    font-size: 20px;
  }
  </style>