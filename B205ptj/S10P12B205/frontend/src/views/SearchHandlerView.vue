<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import SearchView from './SearchView.vue';
import CategoryView from './CategoryView.vue';

const route = useRoute();

const currentComponent = computed(() => {
  if (route.query.tag) {
    return SearchView;
  } else if (route.query.category) {
    return CategoryView;
  }

});
</script>

<template>
  <component :is="currentComponent"></component>
</template>
