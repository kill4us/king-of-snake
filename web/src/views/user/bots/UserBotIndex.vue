<template>
    <ContentField>
        个人中心bot
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'
import $ from 'jquery'
import { useStore } from 'vuex'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        // $.ajax({
        //     url: "http://localhost:3000/user/bots/add/",
        //     type: "post",
        //     data: {
        //         title: "My Bot",
        //         description: "hello world",
        //         content: "this is content",
        //     },
        //     headers: {
        //         Authorization: "Bearer " + store.state.user.token,
        //     },
        //     success(resp) {
        //         console.log(resp);
        //     },
        //     error(resp) {
        //         console.log(resp);
        //     }
        // });

        // $.ajax({
        //     url: "http://localhost:3000/user/bots/remove/",
        //     type: "post",
        //     data: {
        //         bot_id: "6",
        //     },
        //     headers: {
        //         Authorization: "Bearer " + store.state.user.token,
        //     },
        //     success(resp) {
        //         console.log(resp);
        //     },
        //     error(resp) {
        //         console.log(resp);
        //     }
        // });

        // $.ajax({
        //     url: "http://localhost:3000/user/bots/update/",
        //     type: "post",
        //     data: {
        //         bot_id: 1,
        //         title: "changed title",
        //         description: "changed description",
        //         content: "changed content",
        //     },
        //     headers: {
        //         Authorization: "Bearer " + store.state.user.token,
        //     },
        //     success(resp) {
        //         console.log(resp);
        //     },
        //     error(resp) {
        //         console.log(resp);
        //     }
        // });

        $.ajax({
            url: "http://localhost:3000/user/bots/getlist/",
            type: "get",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                console.log(resp);
            },
            error(resp) {
                console.log(resp);
            }
        });
    }
}
</script>

<style scoped>
</style>