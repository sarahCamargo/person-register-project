import { useState, useEffect } from 'react';
import { listarPessoas } from '../services/personService';
import { toast } from 'react-toastify';
import { Pessoa } from '../types/pessoa';

export const useFetchPessoas = () => {
  const [pessoas, setPessoas] = useState<Pessoa[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchPessoas = async () => {
    setLoading(true);
    try {
      const data = await listarPessoas();
      setPessoas(data);
    } catch (error: any) {
      toast.error('Erro ao buscar pessoas', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPessoas();
  }, []);

  return { pessoas, setPessoas, fetchPessoas, loading };
};
