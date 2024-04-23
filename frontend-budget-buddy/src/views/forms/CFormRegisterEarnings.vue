<template>
    <CButton v-if="!isUpdate()" color="success" @click="() => { isOpen = true; }" style="color: white;">
        <CIcon icon="cilPlus" size="lg" /> &nbsp; Adicionar Ganhos
    </CButton>
    <CButton color="success" variant="outline" v-if="idEarnings">
        <CIcon icon="cilPencil" size="lg" @click="() => { isOpen = true; getById() }" />
    </CButton>
    <div>

        <CModal size="lg" backdrop="static" :visible="isOpen" @close="() => { close() }"
            aria-labelledby="StaticBackdropExampleLabel">
            <CModalHeader>
                <CModalTitle id="StaticBackdropExampleLabel">Registrar Ganhos</CModalTitle>
            </CModalHeader>
            <CModalBody>
                <CSpinner color="primary" v-if="loading" />
                <CForm class="row g-3 needs-validation" v-if="!loading">
                    <CCol md="12">
                        <label for="description" class="form-label">* Descrição</label>
                        <input type="text" class="form-control" v-model="form.description" name="description"
                            id="description" max="60" required>
                    </CCol>
                    <CCol md="5">
                        <CFormLabel>* Valor</CFormLabel>
                        <CInputGroup>
                            <CInputGroupText>R$</CInputGroupText>
                            <input v-model="form.value" class="form-control" v-money="{ precision: 2, symbol: 'R$' }" />
                        </CInputGroup>
                    </CCol>
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
import GainService from '../../services/GainService';
import StringUtil from '../../util/StringUtil';
export default {
    name: 'CFormRegisterEarnings',
    props: {
        idEarnings: {
            type: Number,
            required: true,
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
                value: 0
            }
        };
    },
    methods: {
        close() {
            this.isOpen = false;
            this.$emit('close');
            this.clearsFields();
        },
        clearsFields() {
            this.form = {
                description: '',
                value: 0
            };
        },
        isUpdate() {
            return this.idEarnings != undefined
        },
        async save() {
            let response = undefined;
            try {

                if (!this.isUpdate())
                    response = await GainService.create(this.form);
                else
                    response = await GainService.update(this.idEarnings, this.form);

                this.$refs.toast.createToast(response, 'success');

                this.close();
            } catch (error) {
                this.$refs.toast.createToast(error.message, 'danger');
            }
        },
        async getById() {

            try {
                this.loading = true;

                const response = await GainService.getById(this.idEarnings);

                this.form = {
                    description: response.description,
                    value: StringUtil.formatarDinheiroBrasileiro(response.value)
                }

                this.loading = false;

            } catch (error) {
                this.$refs.toast.createToast(error.message, 'danger');
            }

        }
    },
    async mounted() { }
}
</script>