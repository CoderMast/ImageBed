<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
var baseUrl = ref("http://localhost:8080/");
import { UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';

axios.defaults.baseURL = baseUrl.value;

// 用来暂时存储需要上传的图片
const formData = new FormData();
// 当图片被上传时触发执行
const onFileChange = (event) => {
    console.log("event", event)
    // 获取选择的文件  
    // const file = event.target.files[0];
    const file = event.raw;
    formData.append("file", file);
};


// 提交按钮点击后触发
function submitImage() {
    axios.post(
        'image/upload',
        formData,
        {
            headers: {
                "Content-Type": 'multipart/form-data'
            }
        }).then(response => {
            // 调用 ElMessage 方法
            ElMessage({ type: "success", message: '上传成功' });
            console.log('成功', response.data)
        }, error => {
            ElMessage({ type: "error", message: "上传失败" + error.message });
            console.log('错误', error.message)
        })
}
</script>

<template>
    <div>
        <el-upload class="upload-main" drag multiple :auto-upload="false" :on-change="onFileChange">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text main">
                拖拽图片到这里 或者 <em>点击这里上传</em>
            </div>
            <template #tip>
                <div class="el-upload__tip">
                    支持任意图片格式，但大小不得超过 20MB
                </div>
            </template>
        </el-upload>
        <el-button type="success" @click="submitImage" class="upload">上传图片</el-button>
    </div>
</template>

<style scoped>
.upload {
    width: 300px;
    height: 100px;
    background-color: pink;
    display: block;
    margin: auto;
}

.upload-main {
    height: 80vh;
}

.main {
    height: 40vh;
}
</style>