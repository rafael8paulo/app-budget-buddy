<template>
    <div>
        <CRow class="mb-3">
            <CCol :sm="12">                
                <CCard>
                    <CCardBody>
                        <CSearchText :searchText="searchText"
                            @search="(text) => { this.searchText = text; this.search(); }" />

                        <CCard>
                            <CCardHeader><strong>Despesas</strong></CCardHeader>
                            <CCardBody>
                                <CTable striped hover responsive>
                                    <CTableHead color="dark">
                                        <CTableRow>
                                            <CTableHeaderCell scope="col">#</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Data de vencimento</CTableHeaderCell>
                                            <CTableHeaderCell scope="col">Valor Total</CTableHeaderCell>
                                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                                        </CTableRow>
                                    </CTableHead>
                                    <CTableBody>
                                        <CTableRow v-for="e in expenses" :key="e.id">
                                            <CTableHeaderCell scope="row">{{ e.id }}</CTableHeaderCell>
                                            <CTableDataCell>{{ e.description }}</CTableDataCell>
                                            <CTableDataCell>{{ strDate(e.createDate) }}</CTableDataCell>
                                            <CTableDataCell>{{ strDinheiro(e.value) }}</CTableDataCell>
                                            <CTableDataCell>
                                                <CConfirmDelete @delete="() => { this.delete(e.id) }" />
                                            </CTableDataCell>
                                            <CTableDataCell>
                                                <CFormRegisterExpenses :idExpense="e.id" @close="() => { this.isOpen = false; this.search() }" />
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
import ExpenseService from '../../services/ExpenseService.js'
import StringUtil from '../../util/StringUtil.js'
import PaginationComponnent from '../../components/PaginationComponnent.vue'
import CConfirmDelete from '../../components/CConfirmDelete.vue'
import Toast from '../../components/Toast.vue'
import CFormRegisterExpenses from '../forms/CFormRegisterExpenses.vue'
export default {
    name: 'ListExpenses',
    props: {
    },
    components: {
        Toast,
        CSearchText,
        PaginationComponnent,
        CConfirmDelete,
        CFormRegisterExpenses
    },
    setup() {
        return {}
    },
    data() {
        return {
            isOpen: false,
            loading: true,
            searchText: '',
            expenses: [],
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

            const response = await ExpenseService.findPaged(this.searchText, this.pageable.idPage);

            this.pageable = {
                totalPages: response.totalPages,
                number: response.number,
                idPage: response.number
            }

            this.expenses = response.content;

        },
        async delete(id) {
            try {
                const response = await ExpenseService.delete(id);
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