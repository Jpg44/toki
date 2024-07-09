<script setup>
import { ref } from 'vue'
import ProfileBlacklistCreateModal from '@/components/modal/ProfileBlacklistCreateModal.vue';

const items = ref([
  {
    id: 1,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
    title: '아이디 or 닉네임',
    subtitle: `<span class="text-primary">자기 소개</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
  },
  {
    id: 2,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
    title: '아이디 or 닉네임',
    subtitle: `<span class="text-primary">자기 소개</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
  },
  {
    id: 3,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      '<span class="text-primary">자기 소개</span> &mdash; Do you have Paris recommendations? Have you ever been?',
  },
  {
    id: 4,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      '<span class="text-primary">자기 소개</span> &mdash; Have any ideas about what we should get Heidi for her birthday?',
  },
  {
    id: 5,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      '<span class="text-primary">자기 소개</span> &mdash; We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
  },
])
</script>

<template>
  <v-container class="mt-3" fluid>
    <v-row>
      <v-col offset="1" cols="10">
        <v-card class="mx-auto">
          <div class="mt-7 mb-4 mr-10">
            <!-- 블랙리스트 추가 버튼 -->
            <ProfileBlacklistCreateModal/>
          </div>
          <div class="v-list v-theme--light v-lt--density-default v-list--three-line" tabindex="0" role="listbox">
            <div class="v-list-subheader" type="subheader">
              <div class="v-list-subheader__text ml-2">블랙 리스트</div>
            </div>
            <template v-for="item in items" :key="item.id">
              <div v-ripple class="v-list-item v-theme--light v-list-item--density-default v-list-item--three-line v-list-item--variant-text">
                <span class="v-list-item__underlay"></span>
                <div class="v-list-item__prepend">
                  <div class="v-avatar v-theme--light v-avatar--density-default v-avatar--size-default v-avatar--variant-flat">
                    <div class="v-responsive v-img" aria-label>
                      <div class="v-responsive__sizer" style="padding-bottom: 100%;"></div>
                      <!-- 이미지 url -->
                      <img class="v-img__img v-img__img--cover" :src="item.prependAvatar" alt style>
                    </div>
                    <span class="v-avatar__underlay"></span>
                  </div>
                  <div class="v-list-item__spacer"></div>
                </div>
                <div class="v-list-item__content" data-no-activator>
                  <!-- 아이디 닉네임 -->
                  <div class="v-list-item-title">{{ item.title }}</div>
                  <div class="v-list-item-subtitle">
                    <div class="d-flex justify-space-between">
                      <div>
                        <!-- 자기 소개 -->
                        <span class="text-primary">자기 소개</span>
                        <!-- 자기 소개 내용 -->
                        {{ item.subtitle }}
                      </div>
                      <v-btn class="ma-1 ml-7" color="blue-grey-lighten-5">블랙리스트 삭제</v-btn>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  

  
  <style scoped>

  </style>