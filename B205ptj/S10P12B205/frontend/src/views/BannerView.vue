<template>
<v-container>
    <h2>배너 관리</h2>
    <v-spacer></v-spacer>
    <v-row>
        <!-- 배너 조회 -->
        <v-btn color="light-blue-lighten-5" @click="checkBanners">배너 조회</v-btn>
    </v-row>
    
    <v-row>
        <!-- 배너 추가 -->
        <p>배너 추가</p>
        <input type="file" @change="onFileChange">
        <input v-model="banner.name" placeholder="배너 이름 입력">
        <v-btn color="purple-accent-1" @click="addBanner">배너 추가</v-btn>
    </v-row>

    <v-row>
        <!-- 배너 수정 -->
        <input v-model="banner.id" placeholder="수정할 배너 ID">
        <input v-model="banner.name" placeholder="새 배너 이름">
        <v-btn color="teal-accent-2" @click="updateBanner">배너 수정</v-btn>
    </v-row>

    <v-row>
        <!-- 배너 삭제 -->
        <input v-model="deleteBannerId" placeholder="삭제할 배너 ID">
        <v-btn color="red-accent-3" @click="deleteBanner">배너 삭제</v-btn>
    </v-row>
</v-container>
</template>

<script setup>
import { ref } from 'vue';
import { bannerCheck, bannerAdd, bannerUpdate, bannerDelete } from '@/js/Banner';

const banner = ref({
  id: '',
  name: '',
  imageUrl: '',
});

const deleteBannerId = ref('');
const imageFile = ref(null);

const onFileChange = (e) => {
  imageFile.value = e.target.files[0];
};

const checkBanners = () => {
  bannerCheck().then(response => {
    console.log('배너 조회 결과:', response);
  }).catch(error => {
    console.error('배너 조회 실패:', error);
  });
};

const addBanner = () => {
  if (!imageFile.value || !banner.value.name) {
    alert('배너 이름과 이미지를 모두 입력해주세요.');
    return;
  }
  
  const bannerData = {
    name: banner.value.name,
    imageUrl: banner.value.imageUrl,
  };

  bannerAdd(bannerData).then(response => {
    console.log('배너 추가 성공:', response);
    alert('배너가 성공적으로 추가되었습니다.');
  }).catch(error => {
    console.error('배너 추가 실패:', error);
    alert('배너 추가에 실패했습니다.');
  });
};

const updateBanner = () => {
  if (!banner.value.id || !banner.value.name || !imageFile.value) {
    alert('수정할 배너 ID, 새 배너 이름, 이미지를 모두 입력해주세요.');
    return;
  }
  
  const bannerData = {
    bannerId: banner.value.id,
    bannerName: banner.value.name,
    imageUrl: banner.value.imageUrl,
  };

  bannerUpdate(bannerData).then(response => {
    console.log('배너 수정 성공:', response);
    alert('배너가 성공적으로 수정되었습니다.');
  }).catch(error => {
    console.error('배너 수정 실패:', error);
    alert('배너 수정에 실패했습니다.');
  });
};

const deleteBanner = () => {
  if (!deleteBannerId.value) {
    alert('삭제할 배너 ID를 입력해주세요.');
    return;
  }

  bannerDelete(deleteBannerId.value).then(response => {
    console.log('배너 삭제 성공:', response);
    alert('배너가 성공적으로 삭제되었습니다.');
  }).catch(error => {
    console.error('배너 삭제 실패:', error);
    alert('배너 삭제에 실패했습니다.');
  });
};
</script>

<style scoped>

</style>