import axios from 'axios';
import { toast } from 'react-toastify';

export const consultaCep = async (cep: string) => {
  try {
      return await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
  } catch (error: any) {
      toast.error('Erro ao obter dados do CEP', error);
  }
}
