<script setup>
import { ref, defineProps, onMounted, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter()

const props = defineProps({
    roomMeetingDialog: {
        type: Boolean,
        required: true,
    }
})

const checkUser = ref([])

const emit = defineEmits(['update:roomMeetingDialog'])
const selectedGame = ref('')

function closeModal() {
  emit('update:roomMeetingDialog', false)
}

function selectGame(gameName) {
    selectedGame.value = gameName
}

const roomData = computed(() => ({
    checkUser: checkUser.value
    
}))

const cllick = () => {
        sessionStorage.setItem('roomData', JSON.stringify(roomData.value))
        //console.log('////////////////////////////////////////',route.params);
        router.push({ name: 'roomMeeting', params: { roomId: route.params.roomPk, roomMeetingId: 1 }})

}

const addUser = (user) => {
    if (checkUser.value.includes(user)) {
        checkUser.value = checkUser.value.filter(item => item !== user)
    } else {
        checkUser.value.push(user)
    }

}



</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomMeetingDialog"
            @click:outside="closeModal"
            width="800px"
            height="800px"                        
        >
            <v-sheet 
                width="800px" 
                height="800px" 
                color="white"                
                style="border-radius: 20px; "
            >
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        소회의실
                    </v-toolbar-title>
                </v-toolbar>
                <v-item-group
                    multiple
                    style="padding-right: auto; padding-bottom: 80px; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;"
                >
                    <v-row>
                        <v-col
                            v-for="n in 20"
                            :key="n"
                            cols="6"
                        >
                        <v-item v-slot="{ isSelected, toggle }">
                            <v-card
                                style="margin-inline: -4%; margin-block: -3.5%; border-radius: 0px;"
                                :color="isSelected ? 'light-blue-lighten-3' : ''"
                                class="d-flex align-center"
                                dark
                                height="100"
                                @click="() => {toggle(); addUser(n);}"
                                variant="flat"
                            >
                            <v-scroll-y-transition>
                                <div
                                class="text-h4 flex-grow-1 text-center"
                                >
                                {{ n }}
                                </div>
                            </v-scroll-y-transition>
                            </v-card>
                        </v-item>
                        </v-col>
                    </v-row>
                </v-item-group>
            
                <v-footer
                    color="white"
                    style="position: fixed; bottom: 0; width: 800px; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;"
                >
                    <v-container
                        class="d-flex justify-end align-center"
                        style="width: 95%; height: 50px;" 
                    >
                        <v-btn
                            color="black"
                            style="border-radius: 30px; border: 1px solid white;"
                            @click="cllick"
                        >
                        생성
                        </v-btn>

                        <v-btn
                            style="border-radius: 30px; border: 1px solid white; margin-inline-start: 5%;"
                            @click="closeModal"
                        >
                        취소
                        </v-btn>
                    </v-container>
                </v-footer>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>