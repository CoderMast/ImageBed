<script setup lang="ts">
import axios from "axios";
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'

var baseUrl = ref("http://localhost:8080/");

axios.defaults.baseURL = baseUrl.value;

const list = ref(null); // 初始化为null或空数组，取决于你的需求  

onMounted(async () => {
    try {
        const response = await axios.get("/image/list");
        list.value = response.data.data; // 在异步操作完成后更新list.value  
    } catch (error) {
        console.error("Error fetching image list:", error);
    }
});

// 删除图片
function removeImage(id) {
    ElMessageBox.confirm(
        '您确认删除这个图片吗？',
        '警告',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        // 当确认时，则执行删除操作
        // 创建一个只包含id属性的对象  
        let image = {
            id: id
        };
        axios.delete('image/remove', {
            data: image
        }).then(response => {
            console.log('成功', response.data)
            location.reload();
        }, error => {
            console.log('错误', error.message)
        })
        ElMessage({
            type: 'success',
            message: '删除成功',
        })
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消删除',
        })
    })
}

// 清空图片
function emptyImage() {
    ElMessageBox.confirm(
        '您确认清空所有图片吗？',
        '警告',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        // 当确认时，则执行清空操作
        axios.delete('image/removeAll')
            .then(response => {
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                })
                console.log('成功', response.data)
                location.reload();
            }, error => {
                ElMessage({
                    type: 'error',
                    message: '删除失败',
                })
                console.log('错误', error.message)
            })

    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消删除',
        })
    })
}
</script>

<template>
    <!-- 图片展示 -->
    <div class="display">
        <h2 align="center">图床图片</h2>
        <div align="center"><el-button type="danger" @click="emptyImage()">清空图片</el-button></div>
        <div class="imageCard" v-for="item in list">
            <a :href="baseUrl + item.url" target="_blank">
                <img :src="baseUrl + item.url" height="100%"> </img>
            </a>
            <p align="center">
                <el-button type="warning" @click="removeImage(item.id)">删除图片</el-button>
            </p>
        </div>
    </div>
</template>

<style scoped>
.display {
    width: 100vw;
    background-color: pink;
}

.imageCard {
    display: inline-block;
    width: 300;
    height: 300px;
    margin: 10px;
    border: 2px;
}
</style>
