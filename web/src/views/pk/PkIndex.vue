<template>
    <PlayGround />
</template>

<script>
import PlayGround from '../../components/PlayGround.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex';

export default {
    components: {
        PlayGround
    },
    setup() {
        const store = useStore();
        const socketUrl = `ws://localhost:3000/websocket/${store.state.user.id}/`;
        let socket = null;
        onMounted(() => {
            socket = new WebSocket(socketUrl);
            socket.onopen = () => {
                console.log("connected!");
            }
        });

        onUnmounted(() => {
            socket.close();
        });
    }
}
</script>

<style scoped>

</style>