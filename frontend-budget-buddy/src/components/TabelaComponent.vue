<template>

    <CCol :xs="12">
        <CCard class="mb-4">
            <CCardHeader>
                <strong>Vue Table</strong> <small>Vertical alignment</small>
            </CCardHeader>
            <CCardBody>
                <CTable align="middle" responsive>
                    <CTableHead color="dark">
                        <CTableRow>
                            <CTableHeaderCell scope="col" v-for="(item, index) in lista[0]" :key="index"> {{ index }}
                            </CTableHeaderCell>
                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                            <CTableHeaderCell scope="col"></CTableHeaderCell>
                        </CTableRow>
                    </CTableHead>
                    <CTableBody>
                        <CTableRow v-for="(item, index) in lista" :key="index">
                            <CTableDataCell v-for="(value, key) in item" :key="key"> {{ formatByType(value) }}
                            </CTableDataCell>
                            <CTableDataCell>
                                <CButton color="danger" variant="outline">
                                    <CIcon icon="cilTrash" size="lg" />
                                </CButton>
                            </CTableDataCell>
                            <CTableDataCell>
                                <CButton color="success" variant="outline">
                                    <CIcon icon="cilCheckCircle" size="lg" />
                                </CButton>
                            </CTableDataCell>
                        </CTableRow>
                    </CTableBody>
                </CTable>
            </CCardBody>
        </CCard>
    </CCol>
</template>

<script>
import StringUtil from '../util/StringUtil';
export default {
    props: {
        lista: {
            type: Array,
            required: true
        }
    },
    methods: {
        formatByType(value) {

            if (typeof value == 'number')
                return StringUtil.formatarDinheiroBrasileiro(value)
            if (typeof value == 'string')
                return StringUtil.formatarDataBrasileiro(value);

            return typeof value;

        }
    }
}
</script>