<script setup>
import { roomSearchByCategory } from "@/js/Search";
import { computed, onMounted, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";


const router = useRouter()
const route = useRoute()


const items = (['게임', '공부', '스포츠', '잡담', '정보', '음악', '자유(기타)'])

const checkPage = ref(parseInt(route.query.page) || 1)
const checkCategory = computed(() => route.query.category)
const categoryNum = ref(1)

const searchCategory = (categoryNum, pageNumber) => {
  roomSearchByCategory(categoryNum, pageNumber, (res) => {
    console.log(res.data)
  }, (err) => {
    console.error(err)
  })
}

const moveCategory = (category, Num) => {
    categoryNum.value = Num
    
    router.push({ name: 'search', query: { category: category, page: 1 }})
      .then(() => {
        checkPage.value = 1
      })
}

onMounted(() => {
  if (checkCategory.value && checkPage) {
    searchCategory(categoryNum.value, checkPage.value)
  }
})

watch(checkPage, (newValue) => {
  if (route.query.category === checkCategory.value) {
    router.push({ name: 'search', query: { category: checkCategory.value, page: newValue }});
  }
})

// watch([categoryNum, checkPage], ([newCategory, newPage], [oldCategory, oldPage]) => {
//   if (newCategory !== oldCategory || newPage !== oldPage) {
//     searchCategory(newCategory, newPage)
//     console.log(newCategory, newPage)
//   }
// })

watch(categoryNum, (newCateGory, oldCategory) => {
  if (newCateGory !== oldCategory) {
    console.log(newCateGory, checkPage.value)
    searchCategory(newCateGory, checkPage.value)
  }
})

watch(checkPage, (newPage, oldPage) => {
  if (newPage !== oldPage) {
    console.log(`categoryNum : ${categoryNum.value}, newPage : ${newPage}`)
    searchCategory(categoryNum.value, newPage)
  }
})

watch(route.query, (query) => {
  if(query.page) {
    checkPage.value = query.page
    checkCategory.value = query.category
  }

})
</script>

<template>
  <v-container>
    <v-row 
        style="justify-content: center; margin-top: 10px;"

    >
        <template
            v-for="(item, i) in items"
            :key="i"
        >
            <v-col
                cols="1"
                style="white-space: nowrap; padding-inline: 7%;"
            >
                <v-hover v-slot="{isHovering, props}">
                    <div 
                        class="v-text cursor-pointer"
                        :elevation="isHovering ? 12 : 2"
                        :class="[{ 'on-hover': isHovering }, { 'active-category': item === checkCategory }]"
                        v-bind="props"
                        @click="moveCategory(item, i + 1)"
                    >
                        {{ item }}
                    </div>
                </v-hover>
            </v-col>
        </template>
    </v-row>
    <div class="text-center">
      <v-pagination
        v-model="checkPage"
        :length="30"
        :total-visible="8"
        rounded="circle"
        
      >
      </v-pagination>
    </div>
  </v-container>
</template>

<style scoped>
.v-text {
    transition: opacity .4s ease-in-out;
    width: 100%;
    text-align: center;
    font-family: 'Warhaven';
    font-style: normal;
    font-weight: 400;
    font-size: 22px;
    line-height: 52px;
  }

  .v-text:not(.on-hover) {
    opacity: 0.4;
  }

  .active-category {
    opacity: 1 !important;
  }

  .show-btns {
    color: rgba(255, 255, 255, 1) !important;
  }


</style>