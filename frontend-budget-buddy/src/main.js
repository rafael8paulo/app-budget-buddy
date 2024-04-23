import { createApp } from 'vue'
import App from './App.vue'
import VueTheMask from 'vue-the-mask';
import VueMoney from 'v-money';
import router from './router'
import store from './store'
import CoreuiVue from '@coreui/vue'
import CIcon from '@coreui/icons-vue'

import {
    cilPlus,
    cilSearch,
    cilTrash,
    cilCheckCircle,
    cilSpeedometer,
    cilDrop,
    cilPencil,
    cilPuzzle,
    cilCursor,
    cilNotes,
    cilChartPie,
    cilStar,
    cilBell,
    cilCalculator,
    cilMenu,
    cilList,
    cilEnvelopeOpen,
    cilContrast,
    cilSun,
    cilMoon
} from '@coreui/icons'
const icons = {
    cilPlus,
    cilSearch,
    cilTrash,
    cilCheckCircle,
    cilSpeedometer,
    cilDrop,
    cilPencil,
    cilPuzzle,
    cilCursor,
    cilNotes,
    cilChartPie,
    cilStar,
    cilBell,
    cilCalculator,
    cilMenu,
    cilList,
    cilEnvelopeOpen,
    cilContrast,
    cilSun,
    cilMoon
}

import DocsExample from '@/components/DocsExample'

const app = createApp(App)
app.use(store)
app.use(router)
app.use(CoreuiVue)
app.use(VueTheMask)
app.use(VueMoney, {
    decimal: ',',
    thousands: '.',
    // prefix: 'R$ ',
    precision: 2,
    masked: false /* opcional, mas pode ser útil para exibir o símbolo da moeda */
});
app.provide('icons', icons)
app.component('CIcon', CIcon)
app.component('DocsExample', DocsExample)

app.mount('#app')
