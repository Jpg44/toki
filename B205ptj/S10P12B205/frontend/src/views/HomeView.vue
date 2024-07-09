<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import MyCard from "@/components/MyCard.vue";
import { bannerCheck } from "@/js/Banner";
import { useUserStore } from "@/stores/user";
import { useFriendsStore } from "@/stores/friends";

const router = useRouter()

const banners = ref([])
const items = ref(Array.from({ length: 4 }, (_, i) => ({ id: i + 1 })));
const cycle = ref(true)

const cycleCheck = (newValue) => {
  if (newValue.type === 'mouseenter') {
    cycle.value = false;
  }
  else if (newValue.type === 'mouseleave') {
    cycle.value = true;
  }
};

// user.js 에서 유저 상세 간단 조회 axios 함수 가져오기
const storeUser = useUserStore()

// friends.js 에서 친구 조회 axios 함수 가져오기
const storeFriend = useFriendsStore()

onMounted(async () => {
  bannerCheck(
    (res) => {
      banners.value = res.data
    },
    (err) => {
      console.error(err)
    }
  ),
  storeUser.memberDetailAxios()
  storeUser.memberSimpleAxios()
  storeUser.otherMemberDetailAxios('3e8')
  storeFriend.friendListSearchAxios(true)
})

</script>

<template>
  <v-carousel 
    :cycle="cycle"
    interval="4500"
    :showArrows="false"
    hideDelimiterBackground
    height="300"
    @mouseenter="cycleCheck"
    @mouseleave="cycleCheck"
  >
    <v-carousel-item
      v-for="banner in banners"
      :key="banner.id"
    >
      <v-img
        :src="banner.imageUrl"
        alt="배너 이미지"
        style="width: 100%; height: 100%; object-fit: cover;"
      >

      </v-img>
    </v-carousel-item>
  </v-carousel>

  <v-container
    style="margin: 50px; padding-bottom: 500px; max-width: 90%;"
  >

    <div v-text="'추천 토키'" class="text-h5"></div>

    <v-row style="padding-top: 2%;">
      <v-col 
        v-for="(item, index) in items"
        :key="item.id"
        cols="3"
      >
        <MyCard 
          style="height: 200px;"
          :item="item.id"
          :index="index"
        />
      </v-col>
    </v-row>
    
    <div v-text="'현재 진행 중인 토키'" style="padding-top: 50px;" class="text-h5"></div>
    <v-row style="padding-top: 2%;">
      <v-col 
        v-for="(item, index) in items"
        :key="item.id"
        cols="3"
      >
        <MyCard 
          style="height: 200px;"
          :item="item.id"
          :index="index"
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<style>


</style>