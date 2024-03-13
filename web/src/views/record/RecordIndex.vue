<template>
    <ContentField>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>A</th>
                    <th>B</th>
                    <th>对战结果</th>
                    <th>对战时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.record.id">
                    <td>
                        <img :src="record.a_photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.a_username }}</span>
                    </td>
                    <td>
                        <img :src="record.b_photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.b_username }}</span>
                    </td>
                    <td>
                        {{ record.result }}
                    </td>
                    <td>{{ record.record.createtime }}</td>
                    <td>
                        <button type="button" class="btn btn-danger">查看录像</button>
                    </td>
                </tr>
            </tbody>
          </table>
    </ContentField>
</template>


<script>
import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import $ from 'jquery'
import { ref } from 'vue';

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let records = ref("");
        let current_page = 1;
        let total_records = 0;

        console.log(total_records);

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "http://127.0.0.1:3000/record/getlist/",
                data: {
                    page,
                },
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    records.value = resp.records;
                    total_records = resp.records_count;
                },
                error(resp) {
                    console.log(resp);
                }
            });
        }
        pull_page(current_page);
        
        return {
            records,
        }
    }
}
</script>

<style scoped>
img.record-user-photo {
    width: 4vh;
    border-radius: 50%;
}

</style>