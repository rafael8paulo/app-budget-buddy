import apiService from './ApiService';

class InstallmentService {

    static async getInstallmentByDate(dtStart, dtStop) {
        let params = '?dtStart=' + dtStart + '&dtStop=' + dtStop
        try {
            const response = await apiService.get('/installments' + params);
            return response.data;
        } catch (error) {
            console.error('Erro ao buscar parcelas:', error);
            throw error.response.data;
        }
    }

    static async delete(id) {
        try {
            await apiService.delete(`/installments/${id}`);
            return 'Parcela excluída com sucesso';
        } catch (error) {
            if(error.response.data != undefined)
                throw error.response.data.message;
            else
                throw 'Ocorreu um erro ao tentar excluir a parcela';
        }
    }

    static async pay(id) {
        try {
            await apiService.put(`/installments/${id}/paid`);
            return "Parcela paga com sucesso";
        } catch (error) {
            console.error('Ocorreu um erro ao tentar registar o pagamento da parcela: ', error);
            throw "Ocorreu um erro ao tentar registar o pagamento da parcela";
        }
    }

}

export default InstallmentService;