import React, { useState, useEffect, useCallback } from 'react';
import { Modal, TextField, Typography, Grid2 } from '@mui/material';
import { toast } from 'react-toastify';
import { adicionarPessoa, editarPessoa } from '../../services/personService';
import { consultaCep } from '../../services/cepService';
import { CloseButton, Content, Footer, Header, StyledBox, StyledButton, StyledGrid } from './personModal.styles';
import { PersonModalProps } from './personModalProps.types';
import usePersonForm from '../../hooks/usePersonForm';
import { isNumber } from '../../utils/validateNumber';
import CloseIcon from '@mui/icons-material/Close';

const PersonModal: React.FC<PersonModalProps> = ({
  open,
  onClose,
  refreshList,
  personToEdit,
}) => {
  const {
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
  } = usePersonForm(personToEdit);

  const [isLoading, setIsLoading] = useState(false);

  const obterEndereco = useCallback(async (cep: string) => {
    if (cep.length === 8) {
      setIsLoading(true);
      try {
        await consultaCep(cep).then(response => {
          if (response && response.data) {
            const endereco = response.data;
            setMunicipio(endereco.localidade);
            setEstado(endereco.uf);
            setLogradouro(endereco.logradouro);
            setBairro(endereco.bairro);
          }
        });
      } finally {
        setIsLoading(false);
      }
    }
  }, [setMunicipio, setEstado, setLogradouro, setBairro]);

  useEffect(() => {
    if (cep.length === 8) {
      obterEndereco(cep);
    }
  }, [cep, obterEndereco]);

  const handleClose = () => {
    resetForm();
    onClose();
  };


  const handleSave = async () => {

    if (!isFieldsValid()) return;

    const newPerson = { nome, telefone, cpf, cep, logradouro, municipio, estado, bairro, numero, complemento };

    setIsLoading(true);

    try {
      if (personToEdit) {
        await editarPessoa(newPerson);
        toast.success('Pessoa editada com sucesso!');
      } else {
        await adicionarPessoa(newPerson);
        toast.success('Pessoa adicionada com sucesso!');
      }
      refreshList();
      handleClose();
    } catch (error: any) {
      toast.error(error.response.data);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <StyledBox>
        <Header>
          <Typography variant="h6" gutterBottom>
            {personToEdit ? 'Editar Pessoa' : 'Adicionar Pessoa'}
          </Typography>
          <CloseButton onClick={handleClose}>
            <CloseIcon></CloseIcon>
          </CloseButton>
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
              if (isNumber(value)) {
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
              if (isNumber(value)) {
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
                  if (isNumber(value)) {
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
                  if (isNumber(value)) {
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

export default PersonModal;
