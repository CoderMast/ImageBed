<script setup lang="ts">
import axios from 'axios';
import { ref, onMounted } from 'vue';

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

console.log(list);

const imageUpload = ref();

// 当图片被上传时触发执行
const onFileChange = (event) => {
  // 获取选择的文件  
  const file = event.target.files[0];
  // 将文件赋值给 ref，以便在其他地方使用  
  imageUpload.value = file;
  console.log(imageUpload.value)
};

// :action="baseUrl + 'image/upload'" method="post" enctype="multipart/form-data"

// 表单提交之前触发
function handleSubmitPrevent() {
  // axios.defaults.headers["Content-Type"] = "multipart/form-data";
  axios.post('image/upload', {
    "file": imageUpload.value,
  }, {
    headers: {
      "Content-Type": 'multipart/form-data'
    }
  }).then(response => {
    console.log('成功', response.data)
    location.reload();
  }, error => {
    console.log('错误', error.message)
  })
}

// 删除图片
function removeImage(id) {

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
}

</script>

<template>
  <!-- 图片上传 -->
  <div class="upload">
    <form @submit.prevent="handleSubmitPrevent">
      <label>上传图片：<input ref="imageUpload" type="file" accept="image/*" name="file" @change="onFileChange" /></label>
      <button type="submit">提交</button>
    </form>
  </div>
  <hr />

  <!-- 图片展示 -->
  <div class="display">
    <h2 align="center">图床图片</h2>
    <div class="imageCard" v-for="item in list">
      <a :href="baseUrl + item.url" target="_blank">
        <img :src="baseUrl + item.url" height="100%"> </img>
      </a>
      <p align="center">
        <button type="submit" @click="removeImage(item.id)">删除图片</button>
      </p>
    </div>
  </div>

</template>

<style scoped>
* {
  margin: 0px;
  padding: 0px;
}

.upload {
  width: 100vw;
  height: 30vh;
  background-color: blueviolet;
}
.display {
  width: 100vw;
  height: 70vh;
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
