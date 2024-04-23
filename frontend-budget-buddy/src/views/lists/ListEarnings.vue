<template>
    <div>
        <CRow class="mb-3">
            <CCol :sm="12">
                <CCard>
                    <CCardBody>
                        <CSearchText :searchText="searchText"
                            @search="(text) => { this.searchText = text; this.search(); }" />

                        <CCard>
                            <CCardHeader><strong>Ganhos</strong></CCardHeader>
                            <CCardBody>
                                <CTable striped hover responsive>
                                    <CTableHead color="dark">
                                        <CTableRow>
                                            <CTableHeaderCell scope="col">#</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Data de lançamento</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                                        </CTableRow>
                                    </CTableHead>
                                    <CTableBody>
                                        <CTableRow v-for="e in earnings" :key="e.id">
                                            <CTableHeaderCell scope="row">{{ e.id }}</CTableHeaderCell>
                                            <CTableDataCell>{{ e.description }}</CTableDataCell>
                                            <CTableDataCell>{{ strDate(e.createDate) }}</CTableDataCell>
                                            <CTableDataCell>{{ strDinheiro(e.value) }}</CTableDataCell>
                                            <CTableDataCell>
                                                <CConfirmDelete @delete="() => { this.delete(e.id) }" />
                                            </CTableDataCell>
                                            <CTableDataCell>
                                                <CFormRegisterEarnings :idEarnings="e.id" @close="() => { this.search() }" />
                                            </CTableDataCell>
                                        </CTableRow>
                                    </CTableBody>
                                </CTable>
                                <PaginationComponnent v-if="!loading" :pageable="pageable"
                                    @update:idPage="(newIdPage) => { this.pageable.idPage = newIdPage; this.search() }" />
                            </CCardBody>
                        </CCard>
                    </CCardBody>
                </CCard>
            </CCol>
        </CRow>
    </div>
    <Toast ref="toast" />
</template>
<script>
import CSearchText from '../../components/CSearchText.vue'
import StringUtil from '../../util/StringUtil.js'
import PaginationComponnent from '../../components/PaginationComponnent.vue'
import CConfirmDelete from '../../components/CConfirmDelete.vue'
import Toast from '../../components/Toast.vue'
import CFormRegisterEarnings from '../forms/CFormRegisterEarnings.vue'
import GainService from '../../services/GainService.js'
export default {
    name: 'ListEarnings',
    props: {
    },
    components: {
        Toast,
        CSearchText,
        PaginationComponnent,
        CConfirmDelete,
        CFormRegisterEarnings
    },
    setup() {
        return {}
    },
    data() {
        return {
            isOpen: false,
            loading: true,
            searchText: '',
            earnings: [],
            pageable: {
                totalPages: 0,
                number: 0,
                idPage: 0
            },
        };
    },
    methods: {
        strDate(value) {
            return StringUtil.formatarDataBrasileiro(value);
        },
        strDinheiro(value) {
            return StringUtil.formatarDinheiroBrasileiro(value);
        },
        async search() {

            const response = await GainService.findPaged(this.searchText, this.pageable.idPage);

            this.pageable = {
                totalPages: response.totalPages,
                number: response.number,
                idPage: response.number
            }

            this.earnings = response.content;

        },
        async delete(id) {
            try {
                const response = await GainService.delete(id);
                this.$refs.toast.createToast(response, 'success');
                await this.search();
            } catch (error) {
                this.$refs.toast.createToast(error.message, 'danger');
            }
        }
    },
    async mounted() {
        await this.search();
        this.loading = false;
    }
}
</script>