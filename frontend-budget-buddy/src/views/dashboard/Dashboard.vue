<template>
  <div>

    <CRow class="mb-3">
      <CCol :sm="6">
        <input type="date" class="form-control mb-3" v-model="dtStart">
      </CCol>
      <CCol :sm="5">
        <input type="date" class="form-control mb-3" v-model="dtStop">
      </CCol>
      <CCol :sm="1">
        <CButton as="a" color="primary" role="button" @click="getInstallmentsByDate()">
          <CIcon icon="cilSearch" size="lg" />
        </CButton>
      </CCol>
    </CRow>

    <CRow>
      <CCol :sm="6">
        <CWidgetStatsB class="mb-3" :progress="{ color: 'success', value: 100 - percentage }">
          <template #text>Ganhos do mês</template>
          <template #title>Entradas</template>
          <template #value>{{ totalWinningsValue }}</template>
        </CWidgetStatsB>
      </CCol>
      <CCol :sm="6">
        <CSpinner color="primary" v-if="loading" />
        <CWidgetStatsB class="mb-3" :progress="{ color: 'danger', value: percentage }" text="Despesas do mês"
          title="Saídas" :value="amountExpense" v-if="!loading" />
      </CCol>
    </CRow>

    <CRow class="mb-3">
      <CCol xs="6">
        <div class="d-grid gap-2">
          <CFormRegisterEarnings @close="() => { this.totalWinnings() }" />
        </div>
      </CCol>
      <CCol xs="6">
        <div class="d-grid gap-2">
          <CFormRegisterExpenses @close="() => { this.loading = true; this.getInstallmentsByDate(); }" />          
        </div>
      </CCol>
    </CRow>

    <CRow>
      <CCol xs="12">
        <CSpinner color="primary" v-if="loading" />
        <CCard class="mb-4" v-if="!loading">
          <CCardHeader>
            <strong>Despesas</strong>
          </CCardHeader>
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
                <CTableRow v-for="i in installments" :key="i.id">
                  <CTableHeaderCell scope="row">{{ i.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ i.expense.description }}</CTableDataCell>
                  <CTableDataCell>{{ strDate(i.dueDate) }}</CTableDataCell>
                  <CTableDataCell>{{ strDinheiro(i.value) }}</CTableDataCell>
                  <CTableDataCell>
                    <CButton color="danger" variant="outline" @click="deleteInstallment(i.id)">
                      <CIcon icon="cilTrash" size="lg" />
                    </CButton>
                  </CTableDataCell>
                  <CTableDataCell>
                    <CButton color="success" variant="outline" :disabled="i.paid" @click="pay(i.id)">
                      <CIcon icon="cilCheckCircle" size="lg" />
                    </CButton>
                  </CTableDataCell>
                </CTableRow>
              </CTableBody>
            </CTable>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <CCol :md="3" class="mb-4">
      <CCard>
        <CCardHeader><strong>Despesas x Ganhos</strong></CCardHeader>
        <CCardBody>
          <CChartPieExpenses :percentExpense="percentage" />
        </CCardBody>
      </CCard>
    </CCol>
    <Toast ref="toast" />
  </div>
</template>
<script>
import StringUtil from '../../util/StringUtil.js'
import ExpenseService from '../../services/ExpenseService.js'
import InstallmentService from '../../services/InstallmentService.js'
import Toast from '../../components/Toast.vue'
import CChartPieExpenses from '../charts/CChartPieExpenses.vue'
import CFormRegisterEarnings from '../forms/CFormRegisterEarnings.vue'
import CFormRegisterExpenses from '../forms/CFormRegisterExpenses.vue'
import GainService from '../../services/GainService.js'
export default {
  name: 'Dashboard',
  components: {
    Toast,
    CChartPieExpenses,
    CFormRegisterEarnings,
    CFormRegisterExpenses
  },
  setup() {
    return {}
  },
  data() {
    return {
      installments: [],
      loading: false,
      error: null,
      dtStart: '',
      dtStop: '',
      seeModal: false,
      form: {
        description: '',
        value: 0.00,
        qtyInstallment: 1,
        dueDay: 10
      },
      amountExpense: 0,
      amountExpenseFloat: 0,
      percentage: 0,
      totalWinningsValue: 0
    };
  },
  methods: {
    strDate(value) {
      return StringUtil.formatarDataBrasileiro(value);
    },
    strDinheiro(value) {
      return StringUtil.formatarDinheiroBrasileiro(value);
    },
    setDate() {
      let dateAux = new Date();
      const ano = dateAux.getFullYear();
      const mes = dateAux.getMonth();
      const ultimoDia = new Date(ano, mes + 1, 0).getDate();
      dateAux.setDate(1);
      this.dtStart = dateAux.toISOString().substring(0, 10);
      dateAux.setMonth(dateAux.getMonth() + 1);
      dateAux.setDate(ultimoDia);
      this.dtStop = dateAux.toISOString().substring(0, 10);
    },
    async listExpenses() {
      this.loading = true;
      try {
        this.expenses = await ExpenseService.getAllExpenses();
      } catch (error) {
        this.$refs.toast.createToast('Ocorreu um erro ao tentar carregar as depesas', 'danger');
      } finally {
        this.loading = false;
      }
    },
    async getInstallmentsByDate() {
      await this.getTotalExpenseAmount();
      await this.totalWinnings();
      this.loading = true;
      try {
        this.installments = await InstallmentService.getInstallmentByDate(this.dtStart, this.dtStop);
      } catch (error) {
        this.$refs.toast.createToast('Ocorreu um erro ao tentar carregar as depesas', 'danger');
      } finally {
        this.loading = false;
      }
    },
    async getTotalExpenseAmount() {
      this.loading = true;
      try {
        this.amountExpenseFloat = await ExpenseService.getTotalExpenseAmount(this.dtStart, this.dtStop);
        this.amountExpense = this.amountExpenseFloat.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
      } catch (error) {
        this.amountExpenseFloat = "0".toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        this.$refs.toast.createToast('Ocoreu um erro ao tentar carregar o valor total de despesa', 'danger');
      } finally {
        this.loading = false;
      }
      this.calculatePercentage();
    },
    async deleteInstallment(id) {
      try {
        let response = await InstallmentService.delete(id);
        this.$refs.toast.createToast(response, 'success');
        await this.getInstallmentsByDate();
      } catch (error) {
        this.$refs.toast.createToast(error, 'danger');
      }
    },
    async pay(id) {
      try {
        let response = await InstallmentService.pay(id);
        this.$refs.toast.createToast(response, 'success');
        await this.getInstallmentsByDate();
      } catch (error) {
        this.$refs.toast.createToast(error, 'danger');
      }
    },
    async totalWinnings() {
      try {
        let response = await GainService.totalWinnings(this.dtStart, this.dtStop);
        this.totalWinningsValue = this.strDinheiro(response);
      } catch (error) {
        this.totalWinningsValue = "0".toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        this.$refs.toast.createToast(error, 'danger');
      }
    },
    calculatePercentage() {
      let amount = parseFloat(this.amountExpenseFloat);
      let earnings = parseFloat(4800);
      let reason = amount / earnings;
      this.percentage = reason * 100;
      this.percentage = this.percentage.toFixed(2);
    },
  },
  async mounted() {
    this.setDate();
    this.getInstallmentsByDate();
    this.totalWinnings();
  }
}
</script>
