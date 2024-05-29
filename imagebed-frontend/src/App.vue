<script setup lang="ts">
import { NMessageProvider } from 'naive-ui';
</script>

<template>
    <div>
        <n-message-provider>
            <router-view></router-view>
        </n-message-provider>
    </div>
</template>

<style scoped></style>
