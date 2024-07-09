<script setup>
import { onMounted, ref } from 'vue';
import HeaderView from '@/views/HeaderView.vue';
import FooterView from '@/views/FooterView.vue';
import MyCard from '@/components/MyCard.vue';
import { roomDetail, roomSimple } from '@/js/Room';



// 데이터 상태 변수
// const items = ref(Array.from({ length: 48 }, (_, v) => v + 1));
const items = ref([])
const currentPage = ref(1)
const isLoading = ref(false)




// 비동기 API 호출 함수
// const api = async () => {
  //   return new Promise(resolve => {
    //     setTimeout(() => {
      //       // 마지막 항목 값 계산
//       const lastItem = items.value.length ? items.value[items.value.length - 1] : 0;
//       // 새로운 데이터 생성 및 반환
//       resolve(Array.from({ length: 12 }, (_, v) => v + lastItem + 1));
//     }, 1000);
//   });
// };

// // 데이터 로드 함수
// const load = async ({ done }) => {
//   // API 호출하여 데이터 가져오기
//   const res = await api();
//   // 가져온 데이터를 items 배열에 추가
//   items.value.push(...res);
//   // 로드 완료 신호 전달
//   done('ok');
// };

const fetchRoomData = async (pageNumber) => {
  isLoading.value = true
  roomSimple(pageNumber)
  .then(res => {
    items.value.push(...res.data)
    currentPage.value++
  })
  .catch(err => {
    console.error(err)
  })
  .finally(() => {
    isLoading.value = false
  })
}



const joinRoom = () => {
  roomJoin(
    roomId,
    sessionData,
    (success) => {
      console.log(success)
    },
    (error) => {
      console.log(error)
    }
  )
}

// 채팅방 목록에 관한 간단한 정보 조희
const simpleRoomSearch = (pageNumber) => {
  roomSimple(
    pageNumber,
    (success) => {
        console.log(success)
    },
    (error) => {        
        console.log(error)
    }
  )
}

// 방 클릭 시 볼 수 있는 상세 정보 조희
const roomDetailSearch = () => {
  roomDetail(
    roomId,
    (success) => {
      console.log(success)
    },
    (error) => {
      console.log(error)
    }
  )
}

onMounted(() => {
  fetchRoomData(currentPage.value)
})


</script>

<template>
  <v-app>
    

    <v-container 
      fluid 
      style="margin-top: 80px; padding-top: 60px; margin-bottom: 200px;"
    >
      <v-infinite-scroll
        :items="items"
        :onLoad="load"
      >
        <v-row>

          <v-col 
            v-for="(item, index) in items"
            :key="item" 
            cols="12" sm="6" md="4" lg="3"
          >

            <my-card 
              :item="item"
              :index="index"
              >
            </my-card>
            
          </v-col>
        </v-row>
        
      </v-infinite-scroll>
    </v-container>



    
  </v-app>
  <main>
  </main>
</template>
<style>
.content {
  margin-top: 80px;
  margin-bottom: auto;
}

</style>