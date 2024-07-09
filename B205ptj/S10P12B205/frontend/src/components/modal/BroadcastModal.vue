<script setup>
import { commonaxios, postaxios } from '@/js/CommonAxios';
import { roomCreate } from '@/js/Room';
import { ref, watch, onMounted, inject, defineProps, computed } from 'vue';
import router from '@/router';

// 상태 주입
// const broadcastDialog = inject('broadcastState')

const props = defineProps({
  broadcastDialog: {
    type: Boolean,
    required: true,
  }
});

const emit = defineEmits(['update:broadcastDialog'])

function closeModal() {
  emit('update:broadcastDialog', false)
}

// 반응형 참조 변수들
const roomName = ref('')
const ageLimit = ref('')
const roomPassword = ref('')
const genderDisabled = ref(true)
const genderCatch = ref('')
const categoryCatch = ref('')
const categoryIndex = ref(null)

const show = ref(false)
const tags = ref([])
const newHashtag = ref('')
const isPrivate = ref(false)
const btn2 = ref(false)
const btn3 = ref(false)
const btn4 = ref(false)

const updateCategoryIndex = (newValue) => {
    const index = category.findIndex(c => c === newValue)
    categoryIndex.value = index >= 0 ? index : null
}

const roomData = computed(() => ({
    roomName: roomName.value,
    categoryPk: categoryIndex.value+1,
    tags: tags.value,
    isPrivate: isPrivate.value,
    roomPassword: roomPassword.value,
    parentRoomId: null,
    roomOption: {
        ageLimit: ageLimit.value,
        genderCatch: genderCatch.value
    }

}))

const cllick = () => {
        sessionStorage.setItem('roomData', JSON.stringify(roomData.value))
        roomCreate(
            roomData.value,
            (success) => {
                console.log(success)
            },
            (error) => {
                console.log(error)
            }
        )

        router.push({ name: 'roomjoin', params: { roomPk: 1 }})

}


// 상수
const category = ['게임', '공부', '스포츠', '음악', '잡담', '정보', '자유(기타)', ]
const gender = ['남자', '여자']

// 나이 제한 검증
const validateAge = () => {
    if (ageLimit.value > 100) {
        ageLimit.value = 100
    }
}

// 성별 토글
const genderToggle = () => {
    genderDisabled.value = !genderDisabled.value
    genderCatch.value = ''
}

// 해시태그 추가
const addHashtag = (newTag) => {
    if (newTag.trim() && !tags.value.includes(newTag) && tags.value.length < 10) {
        tags.value.push(newTag)
    }
    newHashtag.value = ''
}

// 해시태그 제거
const removeHashtag = (removeTag) => {
    tags.value = tags.value.filter(tag => tag !== removeTag)
}
//----------------------오류때문에주석처리함---------------------------------
// Dialog 상태 변경 감시
watch(() => props.broadcastDialog, (newValue) => {
    if (!newValue) {
        roomName.value = null
        ageLimit.value = null
        roomPassword.value = null
        tags.value = []
        newHashtag.value = ''
        genderCatch.value = ''
        categoryCatch.value = ''
        btn2.value = false
        btn3.value = false
        btn4.value = false
    }
})

watch(categoryCatch, updateCategoryIndex)
</script>

<template>
    <div>
        <v-dialog 
            v-model="props.broadcastDialog" 
            width="auto"
            @click:outside="closeModal"
        >
            <v-card 
                width="800px" 
                height="1000px" 
                justify="center"
            >
                <v-sheet>
                    <v-container 
                        class="d-flex align-center" 
                        style="margin-top: 50px"
                    >
                        <v-col 
                            cols="2"
                            offset="1"
                        >
                            <img 
                                src="@/assets/profile_assets/토키 로고.png" 
                                alt="로고 이미지" 
                                width="150px"
                            >
                        </v-col>
                        <v-col
                            offset="2" 
                        >
                            <p 
                                class="font-weight-bold text-h2 text-light-blue-lighten-2"
                            >                                
                                토키 만들기
                            </p>
                        </v-col>
                    </v-container>           
                </v-sheet>
                
                <v-container 
                    style="width: 80%"
                >
                    <v-text-field
                        v-model="roomName"
                        label="제목"
                        placeholder="Tom & Toms"
                        class="text-black"
                        variant="outlined"
                        rounded="lg"
                        base-color="blue"
                        color="light-blue-lighten-1"
                    >

                    </v-text-field>
                </v-container>
                <v-container
                    class="d-flex"
                    style="width: 90%"
                >
                    <v-col>
                        <v-combobox
                            clearable
                            label="카테고리"
                            :items="category"
                            class="text-black"
                            variant="outlined"
                            rounded="lg"
                            base-color="blue"
                            color="light-blue-lighten-1"
                            v-model.trim="categoryCatch"
                            @change="updateCategoryIndex(categoryCatch)"
                        >
        
                        </v-combobox>
                    </v-col>
                        
                    <v-col>
                        <v-text-field                            
                            placeholder="해시태그"
                            class="text-black"
                            variant="outlined"
                            rounded="lg"
                            base-color="blue"
                            color="light-blue-lighten-1"
                            v-model.trim="newHashtag"
                            @keyup.enter="addHashtag(newHashtag)"
                            maxlength="20"                            
                        >
                        
                        <template v-slot:append>
                            <v-btn
                                color="black"
                                height="55"                                
                                rounded="lg"
                                @click="addHashtag(newHashtag)"
                                
                            >
                                추가
                            </v-btn>
                        </template>

                        </v-text-field>
                    </v-col>                 
                </v-container>

                <div style="margin-left: 10%">
                    <v-chip
                        variant="outlined"
                        color="light-blue-lighten-1"
                        closable
                        label
                        v-for="tag in tags"
                        :key="tag"
                        class="ma-2"
                        
                        @click:close="removeHashtag(tag)"
                        @click="removeHashtag(tag)"
                    >
                        {{ tag }} 
                    </v-chip>                                        
                </div>

                <v-container 
                    class="d-flex justify-end" 
                    style="margin-left: -3%"
                    >

                        <v-btn 
                            class="mx-5 font-weight-bold"
                            stacked
                            :active="isPrivate"
                            @click="isPrivate = !isPrivate"                            
                        >
                          비밀 방
                        </v-btn>

                        <v-btn 
                            class="mx-5 font-weight-bold"
                            stacked                            
                            variant="text"
                            color="primary"
                            :active="btn2"
                        >
                          뱃지 착용
                        </v-btn>

                        <v-btn 
                            class="mx-5"
                            stacked
                            :active="btn3"
                        >
                            악성 유저
                        </v-btn>

                        <v-btn 
                            class="mx-5"
                            stacked
                            :active="btn4"
                            @click="btn4 = !btn4"                                                        
                        >
                            캠 on
                        </v-btn>

                </v-container>

                <v-container
                    class="d-flex"
                    style="width: 90%"
                >
                    <v-col
                        cols="5"
                    >
                        <v-text-field
                            placeholder="비밀번호 입력"
                            class="text-black"
                            variant="outlined"
                            rounded="lg"
                            base-color="blue"
                            color="light-blue-lighten-1"
                            maxlength="20"
                            :append-inner-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="show ? 'text' : 'password'"
                            v-model="roomPassword"
                            @click:append-inner="show = !show"
                        >
                        </v-text-field>
                    </v-col>
                        
                    <v-col
                        cols="3"
                    >
                        <v-text-field
                            placeholder="나이 제한 입력"
                            class="text-black"
                            variant="outlined"
                            rounded="lg"
                            base-color="blue"
                            color="light-blue-lighten-1"
                            type="number"
                            min="0"
                            max="100"                            
                            v-model.number="ageLimit"
                            @input="validateAge"
                        >

                        </v-text-field>            
                    </v-col>

                    <v-col
                        cols="3"
                    >
                        <v-combobox                        
                            label="성별 제한"
                            :items="gender"
                            class="text-black"
                            variant="outlined"
                            rounded="lg"
                            base-color="blue"
                            color="light-blue-lighten-1"
                            :disabled="genderDisabled"
                            v-model.trim="genderCatch"
                        >
                        </v-combobox>
                     </v-col>
                    <v-col
                        cols="1"
                    >
                        <v-checkbox-btn 
                            @click="genderToggle"
                            class="d-flex justify-center align-center"
                        >
                        </v-checkbox-btn>
                    </v-col>

                    
                </v-container>

                <v-container
                    class="d-flex justify-end" 
                    style="width: 95%;"
                >
                    <v-btn
                        class="mx-3 text-white"
                        color="light-blue-lighten-1"
                        :button-font-size="100"
                        font-coloe="light-blue-lighten-1"
                        @click="cllick()"
                        
                    >
                        토키 생성
                    </v-btn>

                    <v-btn
                        class="mx-5"
                        @click="closeModal"
                    >
                        취소
                    </v-btn>
                </v-container>
            </v-card>
        </v-dialog>
    </div>
</template>

<style scoped>
</style>
