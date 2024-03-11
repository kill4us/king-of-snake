<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select v-model="selected_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自上阵</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">
                            {{ bot.title }}
                        </option>
                      </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user-photo-opponent">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username-opponent">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <div class="col-12" style="text-align: center; padding-top:15vh;">
                <button type="button" class="btn btn-warning btn-lg" @click="click_match_button">{{ match_button_info }}</button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex';
import $ from 'jquery';

export default {
    components: {
        
    },
    setup() {
        let match_button_info = ref("开始匹配");
        const store = useStore();
        let bots = ref([]);
        let selected_bot = ref("-1");

        const click_match_button = () => {
            if (match_button_info.value === "开始匹配") {
                match_button_info.value = "取消";
                console.log(selected_bot.value);
                store.state.pk.socket.send(JSON.stringify({ 
                    event: "start-matching",
                    bot_id: selected_bot.value,
                }));


            } else {
                match_button_info.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        const refresh_list = () => {
            $.ajax({
                url: "http://localhost:3000/user/bots/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                },
            });
        }

        refresh_list();  //  从云端获取bot列表

        return {
            match_button_info,
            click_match_button,
            refresh_list,
            bots,
            selected_bot,
        }
    }
}
</script>

<style scoped>
div.matchground {
    width: 60vw; 
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(50, 50, 50, 0.5);
}
div.user-photo {
    text-align: center;
}
div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
    margin-top: 100px;
}
div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.user-photo-opponent {
    text-align: center;
}
div.user-photo-opponent > img {
    border-radius: 50%;
    width: 20vh;
    margin-top: 100px;
}
div.user-username-opponent {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.user-select-bot {
    padding-top: 20vh;
}
div.user-select-bot > select {
    width: 60%;
    margin: 0 auto;
}


</style>