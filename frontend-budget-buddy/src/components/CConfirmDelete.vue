<template>
    <CButton color="danger" variant="outline" @click="() => { visible = true }">
        <CIcon icon="cilTrash" size="lg" />
    </CButton>
    <CModal :visible="visible" @close="() => { visible = false }" aria-labelledby="LiveDemoExampleLabel">
        <CModalHeader>
            <CModalTitle id="LiveDemoExampleLabel">Excluir?</CModalTitle>
        </CModalHeader>
        <CModalBody>Você realmente deseja excluir este item?</CModalBody>
        <CModalFooter>
            <CButton color="primary" @click="() => { visible = false }">
                Fechar
            </CButton>
            <CButton color="danger" @click="evDelete()">Excluir</CButton>
        </CModalFooter>
    </CModal>
</template>
<script>
export default {
    data() {
        return {
            visible: false,
        }
    },
    methods: {
        evDelete() {
            this.$emit('delete');
            this.visible = false;
        }
    }
}
</script>