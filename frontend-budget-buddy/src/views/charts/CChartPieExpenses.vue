<template>
  <CChartPie :data="defaultData" />
</template>

<script>
import { CChartPie } from '@coreui/vue-chartjs'
export default {
  name: 'CChartPieExpenses',
  components: { CChartPie },
  props: {
    percentExpense: {
      type: Number,
      required: true
    }
  },
  computed: {
    defaultData() {
      return {
        labels: ['Despesas', 'Ganhos'],
        datasets: [
          {
            backgroundColor: ['#DD1B16', '#41B883'],
            data: [this.percentExpense, 100 - this.percentExpense],
          },
        ],
      }
    },
  },
}
</script>
