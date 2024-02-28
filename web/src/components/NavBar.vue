<template>
<nav class="navbar navbar-expand-lg bg-body-tertiary navbar bg-dark" data-bs-theme="dark">
  <div class="container">
    <router-link class="navbar-brand" :to="{name: 'home'}">King of Snake</router-link>
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <router-link class="nav-link" :to="{name: 'pk_index'}">对战</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{name: 'record_index'}">对局列表</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{name: 'ranklist_index'}">天梯排行榜</router-link>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            kill4us
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#">个人中心</a></li>
            <li><router-link class="dropdown-item" :to="{name: 'userbots_index'}">我的Bot</router-link></li>
            <li><a class="dropdown-item" href="#">退出登录</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>

<script>



</script>

<style scoped>

</style>