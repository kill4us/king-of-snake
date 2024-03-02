<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form>
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input type="text" class="form-control" id="username" placeholder="输入你的用户名">
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label">密码</label>
                        <input type="password" class="form-control" id="username" placeholder="输入你的密码">
                    </div>
                    <div class = "error-message"></div>
                    <button type="submit" class="btn btn-outline-primary">登录</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>


<script>
import ContentField from '../../../components/ContentField.vue'

export default {
    components: {
        ContentField
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