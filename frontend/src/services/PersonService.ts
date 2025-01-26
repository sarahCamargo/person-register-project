import axios from 'axios';
import { toast } from 'react-toastify';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/pessoa'
});

export const listarPessoas = async () => {
    try {
        const response = await api.get('/');
        return response.data;
    } catch (error: any) {
        toast.error('Erro ao listar pessoas', error);
    }
};

export const adicionarPessoa = async (pessoa: any) => {
    try {
        const response = await api.post('/', pessoa);
        return response.data;
    } catch (error: any) {
        if (error.response && error.response.data) {
            throw error;
        } else {
            toast.error('Erro ao adicionar pessoa:', error);
        }
    }
};

export const editarPessoa = async (pessoa: any) => {
    try {
        const response = await api.put(`/${pessoa.id}`, pessoa);
        return response.data;
    } catch (error: any) {
        if (error.response && error.response.data) {
            throw error;
        } else {
            toast.error('Erro ao editar pessoa:', error);
        }
    }
};

export const removerPessoa = async (id: string) => {
    try {
        await api.delete(`/${id}`);
    } catch (error: any) {
        throw error('Erro ao remover pessoa:', error);
    }
};
