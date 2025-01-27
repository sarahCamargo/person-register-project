import { useState, useEffect } from 'react';
import { Pessoa } from '../types/pessoa';

const usePersonForm = (personToEdit?: Pessoa) => {
    const [nome, setNome] = useState('');
    const [telefone, setTelefone] = useState('');
    const [cpf, setCpf] = useState('');
    const [cep, setCep] = useState('');
    const [logradouro, setLogradouro] = useState('');
    const [municipio, setMunicipio] = useState('');
    const [estado, setEstado] = useState('');
    const [bairro, setBairro] = useState('');
    const [numero, setNumero] = useState('');
    const [complemento, setComplemento] = useState('');

    const [erros, setErros] = useState({ nome: false, cpf: false });

    useEffect(() => {
        if (personToEdit) {
            setNome(personToEdit.nome);
            setTelefone(personToEdit.telefone);
            setCpf(personToEdit.cpf);
            setCep(personToEdit.cep);
            setLogradouro(personToEdit.logradouro);
            setMunicipio(personToEdit.municipio);
            setEstado(personToEdit.estado);
            setBairro(personToEdit.bairro);
            setNumero(personToEdit.numero);
            setComplemento(personToEdit.complemento);
        }
    }, [personToEdit]);

    const resetForm = () => {
        setNome('');
        setTelefone('');
        setCpf('');
        setCep('');
        setLogradouro('');
        setMunicipio('');
        setEstado('');
        setBairro('');
        setNumero('');
        setComplemento('');
    };

    const isFieldsValid = (): boolean => {
        let valid = true;
        const erros = {
            nome: !nome,
            cpf: !cpf,
        };
        if (erros.nome || erros.cpf) {
            setErros(erros);
            valid = false;
        }
        return valid;
    };

    return {
        nome, setNome,
        telefone, setTelefone,
        cpf, setCpf,
        cep, setCep,
        logradouro, setLogradouro,
        municipio, setMunicipio,
        estado, setEstado,
        bairro, setBairro,
        numero, setNumero,
        complemento, setComplemento,
        erros,
        resetForm,
        isFieldsValid,
    };
};

export default usePersonForm;
