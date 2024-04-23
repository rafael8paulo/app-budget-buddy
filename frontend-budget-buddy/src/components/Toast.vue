<template>
    <CToaster class="p-3" placement="top-end">
        <CToast v-for="(toast, index) in toasts" visible :key="index" :autohide="true" :color="toast.color"
            class="text-white align-items-center">
            <div class="d-flex">
                <CToastBody>{{ toast.content }}</CToastBody>
                <CToastClose class="me-2 m-auto" white />
            </div>
        </CToast>
    </CToaster>
</template>
<script>
export default {
    name: 'Toast',
    expose: ['createToast'],
    data() {
        return {
            toasts: []
        }
    },
    methods: {
        createToast(content, color) {
            this.toasts.push({
                content: content,
                color: color
            })
        },
    }
}
</script>