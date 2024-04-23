import StringUtil from '../util/StringUtil';
import apiService from './ApiService';

class ExpenseService {

    static async getAllExpenses() {
        try {
            const response = await apiService.get('/expenses');
            return response.data;
        } catch (error) {
            console.error('Erro ao buscar despesas:', error);
            throw error;
        }
    }

    static async createExpense(dataForm) {
        let body = {
            description: dataForm.description,
            value: StringUtil.strToNubemDecimal(dataForm.value),
            qtyInstallment: dataForm.qtyInstallment,
            dueDay: dataForm.dueDay
        }

        try {
            await apiService.post('/expenses', body);
            return 'Despesa cadastrada com sucesso';
        } catch (error) {
            console.error('Ocorreu um erro ao tentar criar uma despesa: ', error.response.data);
            throw error.response.data;
        }
    }

    static async getTotalExpenseAmount(dtStart, dtStop) {
        let params = '?dtStart=' + dtStart + '&dtStop=' + dtStop
        try {
            const response = await apiService.get('/expenses/totalExpenseAmount' + params);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    }

    static async findPaged(description, idPage) {

        let params = description == '' ? description : `&description=${description}`;

        try {
            const response = await apiService.get(`/expenses/paged?page=${idPage}${params}&sort=id,asc`);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }

    }

    static async delete(id) {
        try {
            await apiService.delete(`/expenses/${id}`);
            return 'Despesa excluída com sucesso'
        } catch (error) {
            throw error.response.data;
        }
    }

    static async getById(id) {
        try {
            const response = await apiService.get(`/expenses/${id}`);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    }

    static async update(id, dataForm) {

        let body = {
            description: dataForm.description,            
        }

        try {
            await apiService.put(`/expenses/${id}`, body);
            return 'Despesa alterada com sucesso';
        } catch (error) {
            console.error('Ocorreu um erro ao tentar atualizar uma despesa: ', error.response.data);
            throw error.response.data;
        }
    }

}

export default ExpenseService;
