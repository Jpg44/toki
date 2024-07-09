import ProfileView from '@/views/ProfileView.vue'

const routerProfile = [
  {
    path: '/profile/:userId',
    name: 'profile',
    component: ProfileView
  },
]
export default routerProfile