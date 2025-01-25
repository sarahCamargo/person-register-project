import axios from 'axios';

export const consultaCep = async (cep: string) => {
  try {
      return await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
  } catch (error) {
      console.error('Erro ao obter dados do CEP:', error);
      throw error;
  }
}
