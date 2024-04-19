<script setup lang="ts">

</script>

<template>
    <div align="center">
        <el-button><a href="upload">上传图片</a></el-button>
        <el-button><a href="display">查看图片</a></el-button>
    </div>
    <router-view></router-view>
</template>

<style scoped>
* {
    margin: 0px;
    padding: 0px;
}

a {
    /* 取消下划线 */
    text-decoration: none;
}
</style>
