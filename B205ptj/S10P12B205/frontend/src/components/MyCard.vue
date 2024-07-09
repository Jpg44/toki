<script setup>
import { onMounted } from "vue"
import { useRoute, useRouter } from "vue-router";

const router = useRouter()

const props = defineProps({
  item: {
    type: [Number, Object],
    required: true,
  },
  index: Number
})


const goToRoom = () => {
  router.push({ name: 'roomjoin', params: { roomPk: props.item }})
}



</script>

<template>
      <v-card
    class="mx-auto"
    max-width="400"
    @click="goToRoom"
  >
    <v-img
      class="align-end text-white"
      height="300"
      :src="`https://picsum.photos/500/300?image=${props.item * 5 + 10}`"
      cover
    >
      
    </v-img>

    <v-card-subtitle class="pt-4">
      Number {{ props.item }}
    </v-card-subtitle>

    <v-card-text>
      <div>Whitehaven Beach</div>

      <div>Whitsunday Island, Whitsunday Islands</div>
    </v-card-text>

    <v-card-actions>
      <v-btn color="orange">
        Share
      </v-btn>

      <v-btn color="orange">
        Explore
      </v-btn>
    </v-card-actions>
  </v-card>

</template>


<style lang="scss" scoped>

</style>