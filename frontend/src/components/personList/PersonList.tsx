import React, { useEffect, useState } from 'react';
import {
  adicionarPessoa,
  listarPessoas,
  editarPessoa,
  removerPessoa,
} from '../../services/personService';
import { Grid2, MenuItem, TextField } from '@mui/material';
import { gerarCSV } from '../../services/csvService';
import { toast } from 'react-toastify';
import AddPersonModal from '../personModal/PersonModal';
import { Pessoa } from '../../types/pessoa';
import { ButtonGrid, StyledButton, StyledFilter } from './personList.styles';
import PersonTable from '../personTable/PersonTable';

const PeopleList: React.FC = () => {
  const [pessoas, setPessoas] = useState<Pessoa[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [personToEdit, setPersonToEdit] = useState<Pessoa | undefined>();
  const [filtro, setFiltro] = useState('nome');
  const [termoBusca, setTermoBusca] = useState('');

  const fetchPessoas = async () => {
    const data = await listarPessoas();
    setPessoas(data);
  };

  useEffect(() => {
    fetchPessoas();
  }, []);

  const handleAdicionarPessoa = async (pessoa: Pessoa) => {
    const novaPessoa = await adicionarPessoa(pessoa);
    setPessoas((prevPessoas) => [...prevPessoas, novaPessoa]);
    setIsModalOpen(false);
  };

  const handleEditPessoa = async (novaPessoa: Pessoa) => {
    const pessoaEditada = await editarPessoa(novaPessoa);
    setPessoas((old) =>
      old.map((pessoa) => (pessoa.id === pessoaEditada.id ? pessoaEditada : pessoa))
    );
    setIsModalOpen(false);
  };

  const handleDeletePessoa = async (id: string) => {
    await removerPessoa(id);
    setPessoas((old) => old.filter((pessoa) => pessoa.id !== id));
    toast.success('Pessoa removida com sucesso!');
  };

  const handleOpenEditModal = (pessoa: Pessoa) => {
    setPersonToEdit(pessoa);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setPersonToEdit(undefined);
  };

  const handleGenerateCSVFile = async () => {
    await gerarCSV();
  };

  const pessoasFiltradas = pessoas?.filter((pessoa) => {
    if (filtro === 'nome') {
      return pessoa.nome.toLowerCase().includes(termoBusca.toLowerCase());
    }
    if (filtro === 'cpf') {
      return pessoa.cpf.includes(termoBusca);
    }
    return true;
  });

  return (
    <>
      <ButtonGrid container spacing={2} justifyContent="flex-start" alignItems="center">
        <Grid2>
          <StyledButton variant="outlined" color="primary" onClick={() => setIsModalOpen(true)}>
            Adicionar Pessoa
          </StyledButton>
        </Grid2>
        <Grid2>
          <StyledButton variant="outlined" color="info" onClick={handleGenerateCSVFile}>
            Gerar Arquivo CSV
          </StyledButton>
        </Grid2>
        <StyledFilter>
          <Grid2>
            <TextField
              select
              label="Filtrar por"
              value={filtro}
              onChange={(e) => setFiltro(e.target.value)}
              fullWidth
            >
              <MenuItem value="nome">Nome</MenuItem>
              <MenuItem value="cpf">CPF</MenuItem>
            </TextField>
          </Grid2>
          <Grid2>
            <TextField
              label="Buscar"
              value={termoBusca}
              onChange={(e) => setTermoBusca(e.target.value)}
              fullWidth
            />
          </Grid2>
        </StyledFilter>
      </ButtonGrid>

      <AddPersonModal
        open={isModalOpen}
        onClose={handleCloseModal}
        onAdd={handleAdicionarPessoa}
        onEdit={handleEditPessoa}
        personToEdit={personToEdit}
        refreshList={fetchPessoas}
      />

      <PersonTable
        pessoas={pessoasFiltradas}
        onEdit={handleOpenEditModal}
        onDelete={handleDeletePessoa}
      />
    </>
  );
};

export default PeopleList;
