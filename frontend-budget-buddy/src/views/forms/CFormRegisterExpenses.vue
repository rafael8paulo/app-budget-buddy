<template>
    <CButton color="success" variant="outline" v-if="idExpense">
        <CIcon icon="cilPencil" size="lg" @click="() => { isOpen = true; this.getById() }" />
    </CButton>
    <CButton color="danger" v-if="!isUpdate()" @click="() => { isOpen = true; }" style="color: white;">
        <CIcon icon="cilPlus" size="lg" /> &nbsp; Adicionar Despesa
    </CButton>
    <div>
        <CModal size="lg" backdrop="static" :visible="isOpen" @close="() => { close() }"
            aria-labelledby="StaticBackdropExampleLabel">
            <CModalHeader>
                <CModalTitle id="StaticBackdropExampleLabel" v-if="!isUpdate()">Cadastrar Despesa
                </CModalTitle>
                <CModalTitle id="StaticBackdropExampleLabel" v-if="isUpdate()">#{{ idExpense }} Alterar despesa
                </CModalTitle>
            </CModalHeader>
            <CModalBody>
                <CSpinner color="primary" v-if="loading" />
                <CForm class="row g-3 needs-validation" v-if="!loading">
                    <CCol md="12">
                        <label for="description" class="form-label">Descrição</label>
                        <input type="text" class="form-control" v-model="form.description" name="description"
                            id="description" max="60" required>
                    </CCol>
                    <CCol md="6" v-if="!isUpdate()">
                        <label for="qtyInstallment" class="form-label">Quantidade de parcelas</label>
                        <input type="number" id="qtyInstallment" v-model="form.qtyInstallment" class="form-control"
                            min="0" max="60" required>
                    </CCol>
                    <CCol md="6" v-if="!isUpdate()">
                        <label for="dueDay" class="form-label">Dia de venciamento</label>
                        <input type="number" id="dueDay" v-model="form.dueDay" class="form-control" min="1" max="29"
                            required>
                    </CCol>
                    <CCol md="12">
                        <CFormLabel>Valor total</CFormLabel>
                        <CInputGroup>
                            <CInputGroupText>R$</CInputGroupText>
                            <input v-model="form.value" class="form-control" v-money="{ precision: 2, symbol: 'R$' }"
                                :disabled="isUpdate()" />
                        </CInputGroup>
                    </CCol>
                    <CCard v-if="isUpdate() && installments.length > 0">
                        <CCardHeader><strong>Parcelas</strong></CCardHeader>
                        <CCardBody>
                            <CTable striped hover responsive>
                                <CTableHead color="dark">
                                    <CTableRow>
                                        <CTableHeaderCell scope="col">#</CTableHeaderCell>
                                        <CTableHeaderCell scope="col">Data de vencimento</CTableHeaderCell>
                                        <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                                        <CTableHeaderCell scope="col">Pago?</CTableHeaderCell>
                                    </CTableRow>
                                </CTableHead>
                                <CTableBody>
                                    <CTableRow v-for="i in installments" :key="i.installmentNumber">
                                        <CTableHeaderCell scope="row">{{ i.id }}</CTableHeaderCell>
                                        <CTableDataCell>{{ strDate(i.dueDate) }}</CTableDataCell>
                                        <CTableDataCell>{{ strDinheiro(i.value) }}</CTableDataCell>
                                        <CTableDataCell v-if="i.paid">Sim</CTableDataCell>
                                        <CTableDataCell v-if="!i.paid">Não</CTableDataCell>
                                    </CTableRow>
                                </CTableBody>
                            </CTable>
                            <PaginationComponnent v-if="!loading" :pageable="pageable"
                                @update:idPage="(newIdPage) => { this.pageable.idPage = newIdPage; this.search() }" />
                        </CCardBody>
                    </CCard>
                </CForm>
            </CModalBody>
            <CModalFooter>
                <CButton color="secondary" @click="() => { close() }">
                    Fechar
                </CButton>
                <CButton color="primary" @click="save()">Salvar</CButton>
            </CModalFooter>
        </CModal>
        <Toast ref="toast" />
    </div>
</template>
<script>
import Toast from '../../components/Toast.vue'
import ExpenseService from '../../services/ExpenseService.js'
import StringUtil from '../../util/StringUtil.js';
export default {
    name: 'CFormRegisterExpenses',
    props: {
        idExpense: {
            type: Number,
            required: false,
        }
    },
    components: {
        Toast,
    },
    setup() {
        return {}
    },
    data() {
        return {
            loading: false,
            isOpen: false,
            form: {
                description: '',
                value: 0.00,
                qtyInstallment: 1,
                dueDay: 10
            },
            installments: []
        };
    },
    methods: {
        strDate(value) {
            return StringUtil.formatarDataBrasileiro(value);
        },
        strDinheiro(value) {
            return StringUtil.formatarDinheiroBrasileiro(value);
        },
        close() {
            this.isOpen = false;
            this.$emit('close');
            this.limparCampos();
        },
        open() {
            this.$emit('open');
        },
        limparCampos() {
            this.form = {
                description: '',
                value: 0.00,
                qtyInstallment: 1,
                dueDay: 10
            };
        },
        isUpdate() {
            return this.idExpense != undefined;
        },
        async save() {
            try {

                let response = undefined;

                if (this.idExpense == undefined)
                    response = await ExpenseService.createExpense(this.form);
                else
                    response = await ExpenseService.update(this.idExpense, this.form);

                this.$refs.toast.createToast(response, 'success');
                this.close();
            } catch (error) {
                this.$refs.toast.createToast(error.message, 'danger');
            }
        },
        async getById() {
            this.loading = true;
            try {
                const response = await ExpenseService.getById(this.idExpense);

                this.form = {
                    description: response.description,
                    value: StringUtil.formatarDinheiroBrasileiro(response.value),
                    qtyInstallment: 1,
                    dueDay: 10
                }
                this.installments = response.installments;
                this.loading = false;

            } catch (error) {
                console.log(error);
            }
        }
    },
    async mounted() { }
}
</script>