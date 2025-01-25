import React, { useState, useEffect } from 'react';
import { Modal, TextField, Typography, Grid2 } from '@mui/material';
import { Pessoa } from '../types/Pessoa';
import { adicionarPessoa, editarPessoa } from '../services/PersonService';
import { consultaCep } from '../services/CepService';
import { Content, Footer, Header, StyledBox, StyledButton, StyledGrid } from '../styles/AddPersonModal.styles';

interface AddPersonModalProps {
  refreshList: () => void;
  open: boolean;
  onClose: () => void;
  onAdd: (person: Pessoa) => Promise<void>;
  onEdit: (person: Pessoa) => Promise<void>;
  personToEdit?: Pessoa;
}

const AddPersonModal: React.FC<AddPersonModalProps> = ({
  open,
  onClose,
  refreshList,
  personToEdit,
}) => {
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
  const [isLoading, setIsLoading] = useState(false);

  const [erros, setErros] = useState({
    nome: false,
    cpf: false,
  });

  useEffect(() => {
    if (cep.length === 8) {
      obterEndereco(cep);
    }
  }, [cep]);

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
    } else {
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
    }
  }, [personToEdit]);

  useEffect(() => {
    if (!personToEdit) {
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
    }
  }, [personToEdit]);
  
  

  const handleSave = async () => {
    let valid = true;
    const erros = {
      nome: !nome,
      cpf: !cpf,
    };

    if (erros.nome || erros.cpf) {
      setErros(erros);
      valid = false;
    }

    if (!valid) return;

    const newPerson: Pessoa = {
      id: personToEdit ? personToEdit.id : '',
      nome: nome,
      telefone: telefone,
      cpf: cpf,
      cep: cep,
      logradouro: logradouro,
      municipio: municipio,
      estado: estado,
      bairro: bairro,
      numero: numero,
      complemento: complemento
    };

    setIsLoading(true);

    try {
      if (personToEdit) {
        await editarPessoa(newPerson);
        alert('Pessoa editada com sucesso!');
      } else {
        await adicionarPessoa(newPerson);
        alert('Pessoa adicionada com sucesso!');
      }
      refreshList();
      onClose();
    } catch (error: any) {
      alert(error.response.data);
    } finally {
      setIsLoading(false);
    }
  };

  const obterEndereco = async (cep: string) => {
    if (cep.length === 8) {
      setIsLoading(true);
      try {
        await consultaCep(cep).then(response => {
          const endereco = response.data;
          setMunicipio(endereco.localidade);
          setEstado(endereco.uf);
          setLogradouro(endereco.logradouro);
          setBairro(endereco.bairro);
        });
      } finally {
        setIsLoading(false);
      }
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <StyledBox>
        <Header>
          <Typography variant="h6" gutterBottom>
            {personToEdit ? 'Editar Pessoa' : 'Adicionar Pessoa'}
          </Typography>
        </Header>
        <Content>
          <TextField
            fullWidth
            margin="normal"
            label="Nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            disabled={isLoading}
            error={erros.nome}
            helperText={erros.nome ? "Campo obrigatório" : ""}
          />
          <TextField
            fullWidth
            margin="normal"
            label="Telefone"
            value={telefone}
            onChange={(e) => {
              const value = e.target.value;
              if (/^\d*$/.test(value)) {
                setTelefone(e.target.value)
              }
            }}
            disabled={isLoading}
          />
          <TextField
            fullWidth
            margin="normal"
            label="CPF"
            value={cpf}
            onChange={(e) => {
              const value = e.target.value;
              if (/^\d*$/.test(value)) {
                setCpf(e.target.value)
              }
            }}
            disabled={isLoading}
            error={erros.cpf}
            helperText={erros.cpf ? "Campo obrigatório" : ""}
          />
          <StyledGrid container spacing={3}>
            <Grid2 size={{ xs: 12, md: 5 }}>
              <TextField
                fullWidth
                margin="normal"
                label="CEP"
                value={cep}
                onChange={(e) => {
                  const value = e.target.value;
                  if (/^\d*$/.test(value)) {
                    setCep(e.target.value)
                  }
                }}
                disabled={isLoading}
              />
            </Grid2>
            <Grid2 size={{ xs: 12, md: 5 }}>
              <TextField
                fullWidth
                margin="normal"
                label="Município"
                value={municipio}
                onChange={(e) => setMunicipio(e.target.value)}
                disabled={isLoading}
              />
            </Grid2>
            <Grid2 size={{ xs: 12, md: 2 }}>
              <TextField
                fullWidth
                margin="normal"
                label="Estado"
                value={estado}
                onChange={(e) => setEstado(e.target.value)}
                disabled={isLoading}
              />
            </Grid2>
          </StyledGrid>
          <StyledGrid container spacing={3}>
            <Grid2 size={{ xs: 12, md: 5 }}>
              <TextField
                fullWidth
                margin="normal"
                label="Logradouro"
                value={logradouro}
                onChange={(e) => setLogradouro(e.target.value)}
                disabled={isLoading}
              />
            </Grid2>
            <Grid2 size={{ xs: 12, md: 5 }}>
              <TextField
                fullWidth
                margin="normal"
                label="Bairro"
                value={bairro}
                onChange={(e) => setBairro(e.target.value)}
                disabled={isLoading}
              />
            </Grid2>
            <Grid2 size={{ xs: 12, md: 2 }}>
              <TextField
                fullWidth
                margin="normal"
                label="Número"
                value={numero}
                onChange={(e) => {
                  const value = e.target.value;
                  if (/^\d*$/.test(value)) {
                    setNumero(value);
                  }
                }}
                disabled={isLoading}
              />
            </Grid2>
          </StyledGrid>
          <TextField
            fullWidth
            margin="normal"
            label="Complemento"
            value={complemento}
            onChange={(e) => setComplemento(e.target.value)}
            disabled={isLoading}
          />
          <Footer>
            <StyledButton
              variant="contained"
              color="primary"
              onClick={handleSave}
              disabled={isLoading}
            >
              {isLoading
                ? 'Salvando...'
                : personToEdit
                  ? 'Salvar Alterações'
                  : 'Adicionar Pessoa'}
            </StyledButton>
          </Footer>
        </Content>
      </StyledBox>
    </Modal>
  );
};

export default AddPersonModal;
