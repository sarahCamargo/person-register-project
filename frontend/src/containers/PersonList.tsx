import React, { useEffect, useState } from 'react';
import { Pessoa } from '../types/Pessoa';
import AddPersonModal from '../components/AddPersonModal';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {
  adicionarPessoa,
  listarPessoas,
  editarPessoa,
  removerPessoa,
  gerarCSV,
} from '../services/PersonService';
import {
  StyledContainer,
  StyledButton,
  ButtonGrid,
  StyledTable,
  StyledTableCell,
  StyledTablePagination,
  StyledBox,
} from '../styles/PersonList.styles'
import { Grid2, IconButton } from '@mui/material';

const PeopleList: React.FC = () => {
  const [pessoas, setPessoas] = useState<Pessoa[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [personToEdit, setPersonToEdit] = useState<Pessoa | undefined>();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchPessoas = async () => {
    const data = await listarPessoas();
    setPessoas(data);
  };

  useEffect(() => {
    fetchPessoas();
  }, []);

  const handleAdicionarPessoa = async (pessoa: Pessoa) => {
    setPersonToEdit(undefined); 
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
    alert('Pessoa removida com sucesso!');
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

  const handleChangePage = (_event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const displayedPessoas = pessoas.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage
  );

  return (
    <StyledContainer>
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

      <StyledBox>
        <StyledTable stickyHeader>
          <thead>
            <tr>
              <StyledTableCell>Nome</StyledTableCell>
              <StyledTableCell>Telefone</StyledTableCell>
              <StyledTableCell>CPF</StyledTableCell>
              <StyledTableCell>CEP</StyledTableCell>
              <StyledTableCell>Logradouro</StyledTableCell>
              <StyledTableCell>Município</StyledTableCell>
              <StyledTableCell>Estado</StyledTableCell>
              <StyledTableCell>Bairro</StyledTableCell>
              <StyledTableCell>Número</StyledTableCell>
              <StyledTableCell>Complemento</StyledTableCell>
              <StyledTableCell>Ações</StyledTableCell>
            </tr>
          </thead>

          <tbody>
            {displayedPessoas.map((person) => (
              <tr key={person.id}>
                <StyledTableCell>{person.nome}</StyledTableCell>
                <StyledTableCell>{person.telefone}</StyledTableCell>
                <StyledTableCell>{person.cpf}</StyledTableCell>
                <StyledTableCell>{person.cep}</StyledTableCell>
                <StyledTableCell>{person.logradouro}</StyledTableCell>
                <StyledTableCell>{person.municipio}</StyledTableCell>
                <StyledTableCell>{person.estado}</StyledTableCell>
                <StyledTableCell>{person.bairro}</StyledTableCell>
                <StyledTableCell>{person.numero}</StyledTableCell>
                <StyledTableCell>{person.complemento}</StyledTableCell>
                <StyledTableCell>
                  <IconButton
                    color="secondary"
                    sx={{ marginRight: '10px' }}
                    onClick={() => handleOpenEditModal(person)}
                  >
                    <EditIcon />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleDeletePessoa(person.id)}
                  >
                    <DeleteIcon />
                  </IconButton>
                </StyledTableCell>
              </tr>
            ))}
          </tbody>
        </StyledTable>
      </StyledBox>

      <StyledTablePagination
        rowsPerPageOptions={[5, 10, 15]}
        count={pessoas.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
        labelRowsPerPage=""
      />
    </StyledContainer>
  );
};

export default PeopleList;
