import { createRouter, createWebHistory } from 'vue-router'
import PkIndex from '../views/pk/PkIndex'
import RanklistIndex from '../views/ranklist/RanklistIndex'
import RecordIndex from '../views/record/RecordIndex'
import UserBotIndex from '../views/user/bots/UserBotIndex'
import NotFound from '../views/error/NotFound'
import UserAccountLoginView from '@/views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegisterView'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndex,
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndex,
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndex,
  },
  {
    path: "/error/",
    name: "404NotFound_index",
    component: NotFound,
  },
  {
    path: "/user/bots/",
    name: "userbots_index",
    component: UserBotIndex,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/error/",
  },
  {
    path: "/user/account/register/",
    name: "account_register",
    component: UserAccountRegisterView,
  },
  {
    path: "/user/account/login/",
    name: "account_login",
    component: UserAccountLoginView,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
