import { createRouter, createWebHistory } from 'vue-router';  
import Upload from '../components/Upload.vue';  
import Display from '../components/Display.vue';  
import Home from '../components/Home.vue'
const routes = [  
    {
        path:'/',
        name: 'Home' ,
        component: Home
    },
    {  
        path: '/upload',  
        name: 'Upload',  
        component: Upload  
    },  
    {  
        path: '/display',  
        name: 'Display',  
        component: Display  
    }  
  // 可以添加其他路由...  
];  
  
const router = createRouter({  
  history: createWebHistory(),  
  routes  
});  
  
export default router;