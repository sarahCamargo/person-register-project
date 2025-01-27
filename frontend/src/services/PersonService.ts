import { toast } from 'react-toastify';
import { api } from './api';
import { API_ENDPOINT_PESSOAS } from '../utils';

export const listarPessoas = async () => {
    try {
        const response = await api.get(`${API_ENDPOINT_PESSOAS}/`);
        return response.data;
    } catch (error: any) {
        toast.error('Erro ao listar pessoas', error);
    }
};

export const adicionarPessoa = async (pessoa: any) => {
    try {
        const response = await api.post(`${API_ENDPOINT_PESSOAS}/`, pessoa);
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
        const response = await api.put(`${API_ENDPOINT_PESSOAS}/${pessoa.id}`, pessoa);
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
        await api.delete(`${API_ENDPOINT_PESSOAS}/${id}`);
    } catch (error: any) {
        throw error('Erro ao remover pessoa:', error);
    }
};
