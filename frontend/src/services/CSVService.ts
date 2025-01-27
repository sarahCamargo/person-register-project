import { toast } from 'react-toastify';
import { api } from './api';
import { API_ENDPOINT_CSV } from '../utils';

export const gerarCSV = async () => {
    try {
        const response = await api.get(`${API_ENDPOINT_CSV}/download`)
        if (response.data) {
            toast.info(response.data);
        }
    } catch (error: any) {
        if (error.response && error.response.data) {
            toast.error(error.response.data);
        } else {
            toast.error('Erro ao gerar CSV: ', error);
        }
    }
}
