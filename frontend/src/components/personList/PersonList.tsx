import React, { useEffect, useState } from 'react';
import {
  adicionarPessoa,
  listarPessoas,
  editarPessoa,
  removerPessoa,
} from '../../services/personService';
import { Grid2 } from '@mui/material';
import { gerarCSV } from '../../services/csvService';
import { toast } from 'react-toastify';
import AddPersonModal from '../personModal/PersonModal';
import { Pessoa } from '../../types/pessoa';
import { ButtonGrid, StyledButton } from './personList.styles';
import PersonTable from '../personTable/PersonTable';

const PeopleList: React.FC = () => {
  const [pessoas, setPessoas] = useState<Pessoa[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [personToEdit, setPersonToEdit] = useState<Pessoa | undefined>();

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
        pessoas={pessoas}
        onEdit={handleOpenEditModal}
        onDelete={handleDeletePessoa}
      />
    </>
  );
};

export default PeopleList;
