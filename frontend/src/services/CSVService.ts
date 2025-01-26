import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/csv'
});

export const gerarCSV = async () => {
    try {
        const response = await api.get('/download')
        if (response.data) {
            alert(response.data);
        }
    } catch (error: any) {
        if (error.response && error.response.data) {
            alert(error.response.data);
            throw error;
        } else {
            console.error('Erro ao gerar CSV: ', error);
            throw error;
        }
    }
}