import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/pessoa'
});

export const listarPessoas = async () => {
    try {
        const response = await api.get('/');
        return response.data;
    } catch (error) {
        console.error('Erro ao listar pessoas:', error);
        throw error;
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
            console.error('Erro ao adicionar pessoa:', error);
            throw error;
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
            console.error('Erro ao editar pessoa:', error);
            throw error;
        }
    }
};

export const removerPessoa = async (id: string) => {
    try {
        await api.delete(`/${id}`);
    } catch (error) {
        console.error('Erro ao remover pessoa:', error);
        throw error;
    }
};
