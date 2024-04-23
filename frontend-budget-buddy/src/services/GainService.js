import StringUtil from '../util/StringUtil';
import apiService from './ApiService';

class GainService {

    static async totalWinnings(dtStart, dtStop) {

        let params = `dtStart=${dtStart}&dtStop=${dtStop}`

        try {
            const response = await apiService.get(`/earnings/totalWinnings?${params}`);
            return response.data;
        } catch (error) {
            console.error('Ocorreu um erro ao tentar recuperar os ganhos do periodo:', error);
            throw 'Ocorreu um erro ao tentar recuperar os ganhos do periodo';
        }

    }

    static async create(form) {

        let body = {
            description: form.description,
            value: StringUtil.strToNubemDecimal(form.value),
        }

        try {
            await apiService.post(`/earnings`, body);
            return "Ganhos registrado com sucesso";
        } catch (error) {
            throw error.response.data;
        }
    }

    static async update(id, form) {        

        let body = {
            description: form.description,
            value: StringUtil.strToNubemDecimal(form.value),
        }

        try {
            await apiService.put(`/earnings/${id}`, body);
            return "Ganhos registrado com sucesso";
        } catch (error) {
            throw error.response.data;
        }
    }

    static async findPaged(description, idPage) {

        let params = description == '' ? description : `&description=${description}`;

        try {
            const response = await apiService.get(`/earnings/paged?page=${idPage}${params}&sort=id,asc`);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }

    }

    static async delete(id) {
        try {
            await apiService.delete(`/earnings/${id}`);
            return 'Ganho excluído com sucesso'
        } catch (error) {
            throw error.response.data;
        }
    }

    static async getById(id) {
        try {
            const response = await apiService.get(`/earnings/${id}`);
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    }

}

export default GainService;