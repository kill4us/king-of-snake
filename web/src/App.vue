<template>
  <NavBar />
  <router-view  />
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"


export default {
  components: {
    NavBar
  },

  setup() {
    // $.ajax({
    //   url: "http://localhost:3000/user/account/token/",
    //   type: "post",
    //   data: {
    //     username: "kill4us",
    //     password: "password1",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });

    // $.ajax({
    //   url: "http://localhost:3000/user/account/info/",
    //   type: "get",
    //   headers: {
    //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MTkxNGExMWQyNWM0MWNhYjI4ODA3NzA2MjM5ZjczYSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcwOTM2ODI0MywiZXhwIjoxNzEwNTc3ODQzfQ.G_oyLvHOI3LBUhTZif_GKa1Djo0_4x-vLQbPwMU35O8"

    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // });

    // $.ajax({
    //   url: "http://localhost:3000/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "kill4us10",
    //     password: "123",
    //     confirmedPassword: "123",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   },
    // });
  }


}
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
