<script setup>
import { ref, defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter()


const props = defineProps({
    exitDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:exitDialog'])

function closeModal() {
  emit('update:exitDialog', false)
}

function leaveRoom() {
    router.push({ name: 'roomjoin', params: { roomPk: route.params.roomId }})
}


</script>

<template>
    <div>
        <v-dialog
            v-model="props.exitDialog"
            @click:outside="closeModal"
            width="1000px"
            height="600px"                        
        >
            <v-sheet 
                class="d-flex align-center justify-center flex-wrap text-center"
                width="auto"
                height="600px"
                color="black"
                style="padding-top: 25%; border-radius: 20px; border: 1px solid white;"
            >
                <div
                    id="font-check"
                    class="text-h4"
                >
                    소회의실을 나가시겠습니까?
                </div>

                <v-footer
                    color="black"
                    class="mt-auto"
                    style="bottom: 0; width: 600px; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;"
                >
                    <v-container
                        class="d-flex justify-end align-center"
                        style="width: 95%; height: 50px;" 
                    >
                        <v-btn
                            style="border-radius: 30px; border: 1px solid white;"
                            @click="leaveRoom"
                        >
                        회의실 나가기
                        </v-btn>

                        <v-btn
                            color="black"
                            style="border-radius: 30px; border: 1px solid white; margin-inline-start: 5%;"
                            @click="closeModal"
                        >
                        취소
                        </v-btn>
                    </v-container>
                </v-footer>
            </v-sheet>
        </v-dialog>
        <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/neodgm/neodgm-pro-webfont@latest/neodgm_pro/style.css">
    </div>
</template>

<style scoped>
.font-neodgm-pro { 
    font-family: 'NeoDunggeunmo Pro'; 
}

#font-check {
    font-family: NeoDunggeunmo Pro;
}

</style>