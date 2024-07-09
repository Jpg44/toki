<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { roomSearchByTag } from '@/js/Search';


const route = useRoute()
const searchQuery = ref('')
const searchResults = ref([])

function fetchSearchResults() {
  roomSearchByTag(
    searchQuery.value,
    (res) => {
      searchResults.value = res.data
    },
    (err) => {
      console.error('검색 실패', err)
      searchResults.value = []
    }
  )
}

onMounted(() => {
  if (route.query.tag) {
    searchQuery.value = route.query.tag
    fetchSearchResults()
  }
})


watch(() => route.query.tag, (newValue) => {
  searchQuery.value = newValue
  fetchSearchResults()
})

</script>

<template>
    <div>
        '{{ searchQuery }}' 의 검색 결과 입니다.

        <!-- <ul>
          <li v-for="result in searchResults" :key="result.id">
            {{ result }}
          </li>
        </ul> -->
    </div>
</template>

<style scoped>

</style>