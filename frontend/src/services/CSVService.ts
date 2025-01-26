import axios from 'axios';
import { toast } from 'react-toastify';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/csv'
});

export const gerarCSV = async () => {
    try {
        const response = await api.get('/download')
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