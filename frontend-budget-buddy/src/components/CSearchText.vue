<template>
    <CRow>
        <CCol :sm="11">
            <input type="text" class="form-control mb-3" placeholder="Buscar..." name="searchText" id="searchText"
                v-model="text">
        </CCol>
        <CCol :sm="1">
            <CButton as="a" color="primary" role="button" @click="search()">
                <CIcon icon="cilSearch" size="lg" />
            </CButton>
        </CCol>
    </CRow>
</template>
<script>

export default {
    name: 'CSearchText',
    props: {
        searchText: {
            type: String,
            required: true
        }
    },
    components: {},
    setup() {
        return {}
    },
    data() {
        return {
            text: ''
        };
    },
    methods: {
        search() {
            this.$emit('search', this.text);
        }
    },
    async mounted() { }
}
</script>