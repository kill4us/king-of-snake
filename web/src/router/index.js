import { createRouter, createWebHistory } from 'vue-router'
import PkIndex from '../views/pk/PkIndex'
import RanklistIndex from '../views/ranklist/RanklistIndex'
import RecordIndex from '../views/record/RecordIndex'
import UserBotIndex from '../views/user/bots/UserBotIndex'
import NotFound from '../views/error/NotFound'
import UserAccountLoginView from '@/views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegisterView'
import store from '@/store/index'

const routes = [
  {
    path: "/user/account/login/",
    name: "account_login",
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/error/",
    name: "404NotFound_index",
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/bots/",
    name: "userbots_index",
    component: UserBotIndex,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/error/",
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/register/",
    name: "account_register",
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({name: "account_login"});
  } else {
    next();
  }
})

export default router

