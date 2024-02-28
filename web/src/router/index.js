import { createRouter, createWebHistory } from 'vue-router'
import PkIndex from '../views/pk/PkIndex'
import RanklistIndex from '../views/ranklist/RanklistIndex'
import RecordIndex from '../views/record/RecordIndex'
import UserBotIndex from '../views/user/bots/UserBotIndex'
import NotFound from '../views/error/NotFound'

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
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
