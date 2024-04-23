<template>
    <div>
        <CPagination v-if="pageable && pageable.totalPages > 1">
            <CPaginationItem aria-label="Previous" href="#" :disabled="disabledPrevius()" @click="previus()">
                <span aria-hidden="true">&laquo;</span>
            </CPaginationItem>
            <CPaginationItem href="#" :active="active(i)" v-for="i in pageable.totalPages" :key="i"
                @click="update(i - 1)"> {{ i }} </CPaginationItem>
            <CPaginationItem aria-label="Next" @click="next()" :disabled="disabledNext()"><span
                    aria-hidden="true">&raquo;</span>
            </CPaginationItem>
        </CPagination>        
    </div>
</template>
<script>
export default {
    name: 'PaginationComponnent',
    props: {
        pageable: {
            type: Object,
            required: true
        }
    },
    components: {},
    setup() {
        return {}
    },
    data() {
        return {

        };
    },
    methods: {
        active(id) {
            return this.pageable.number + 1 == id;
        },
        disabledPrevius() {
            return this.pageable.number == 0;
        },
        disabledNext() {
            return this.pageable.number + 1 == this.pageable.totalPages;
        },
        update(idPage) {
            this.$emit('update:idPage', idPage);
        },
        next() {
            if (this.pageable.number + 1 != this.pageable.totalPages)
                this.update(this.pageable.number + 1)
        },
        previus() {
            if (this.pageable.number != 0)
                this.update(this.pageable.number - 1)
        }
    },
    async mounted() { }
}
</script>