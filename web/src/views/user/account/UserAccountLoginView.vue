<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="输入你的用户名">
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="输入你的密码">
                    </div>
                    <div class = "error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-outline-primary">登录</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>


<script>
import ContentField from '../../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from '@/router';


export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();  //  取出全局变量
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {  //  触发函数
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,  //  ref取值是.value
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: "home"});
                            console.log(store.state.user);
                        }
                    })
                    
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }
        return {
            username,
            password,
            error_message,
            login,
        }

    }
}
</script>

<style scoped>
button {
    width: 100%;
}
div.error-message {
    color: red;
}

</style>