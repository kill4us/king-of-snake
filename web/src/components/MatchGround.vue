<template>
    <div class="matchground">
        <div class="row">
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-6">
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

export default {
    components: {
        
    },
    setup() {
        let match_button_info = ref("开始匹配");
        const store = useStore();

        const click_match_button = () => {
            if (match_button_info.value === "开始匹配") {
                match_button_info.value = "取消";
                store.state.pk.socket.send(JSON.stringify({ 
                    event: "start-matching",
                }));


            } else {
                match_button_info.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        return {
            match_button_info,
            click_match_button,
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


</style>