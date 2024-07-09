<script setup>
import { blackListAdd, blackListsearch, blacklistRemove } from '@/js/Blacklist';
import { friendListSearch, friendAcceptanceRejection, friendDelete, } from '@/js/Friend';
import { roomHashTag, deleteHashTag, updateHashTag } from '@/js/HashTag';
import { memberDetail, memberSimple, memberUpdate } from '@/js/Member';
import { messageCheck, messageDetail, messageDelete, messageSend } from '@/js/Message';
import { roomSearchByTag, roomSearchByCategory } from '@/js/Search';
import { useAuthStore } from '@/stores/auth';
import { ref } from 'vue';


const authStore = useAuthStore()

const userId = ref('')

const testPk = 
    {    
        toUserTag: '3e9'
    }

const friendRequestAcception = 
    {
        testPk,
        acceptFreind: true
    }

const friendRequestReject = 
{
    testPk,
    acceptFreind: false
}




/// blackList
// get
const blacklistsear = () => {
    
    blackListsearch(
        (success) => {
            console.log(success)
        },
        (error) => {
            console.log(error)
        }
    )
}

// post
const blacklistaddd = () => {
    blackListAdd(
        testPk,
        (success) => {
            console.log(success)
        },
        (error) => {        
            console.log(error)
        }
    )
}

// delete
const blacklistdeletee = () => {
    
    blacklistRemove(
        testPk.toUserTag,
        (success) => {
            console.log(success)
        },
        (error) => {
            console.log(error)
        }
    )
}
///

/// Friend
// get
// friendListSearch(
//     (success) => {
//         console.log(success)
//     },
//     (error) => {
//         console.log(error)
//     }
// )
// // put
// friendAcceptanceRejection(
//     friendRequestAcception,
//     (success) => {
//         console.log(success)
//     },
//     (error) => {
//         console.log(error)
//     }
// )
// // delete
// friendDelete(
//     testPk,
//     (success) => {
//         console.log(success)
//     },
//     (error) => {
//         console.log(error)
//     }
// )

/// HashTag
// post
// roomHashTag(
//     roomId,
//     tags,
//     (success) => {
//         console.log(success)
//     },
//     (error) => {
//         console.log(error)
//     }
// )



///



const memberCheck = () => {
    memberDetail(
        authStore.userTag,
        (success) => {
            console.log(success)
        },
        (error) => {
            console.error(error)
        }
    )
}


</script>

<template>
    <div>
        <v-text-field 
            v-model="userId"
            label="blacklist">
            
        </v-text-field>
        <!-- blacklist -->
        <v-row>
            <v-col>
                
                <v-btn
                    @click="blacklistsear"
                >
                    blackListsearch
                </v-btn>
            </v-col>
            <v-col>

                <v-btn
                    @click="blacklistaddd"
                >
                    blackListAdd
                </v-btn>
            </v-col>
            <v-col>

                <v-btn
                    @click="blacklistdeletee"
                >
                    blacklistRemove
                </v-btn>
            </v-col>

        </v-row>




        <!-- freind -->
        <v-row>
            <v-col>
                
                <v-btn>
                    friendListSearch
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    friendAcceptanceRejection
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    friendDelete
                </v-btn>
            </v-col>
        </v-row>


        <!-- HashTag -->
        <v-row>
            <v-col>
                
                <v-btn>
                    roomHashTag
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    deleteHashTag
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    updateHashTag
                </v-btn>
            </v-col>
        </v-row>


        <!-- Member -->
        <v-row>
            <v-col>
                
                <v-btn @click="memberCheck">
                    memberDetail
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    memberSimple
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    memberUpdate
                </v-btn>
            </v-col>
        </v-row>


        <!-- Message -->
        <v-row>
            <v-col>
                
                <v-btn>
                    messageCheck
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    messageDetail
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    messageDelete
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    messageSend
                </v-btn>
            </v-col>
        </v-row>


        <!-- Search -->
        <v-row>
            <v-col>
                
                <v-btn>
                    roomSearchByTag
                </v-btn>
            </v-col>
            <v-col>

                <v-btn>
                    roomSearchByCategory
                </v-btn>
            </v-col>

        </v-row>
    </div>
    
</template>


<style scoped>

</style>