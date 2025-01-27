import React, { useState } from 'react';
import { IconButton, Tooltip } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { StyledBox, StyledTable, StyledTableCell } from './personTable.styles';
import { PersonTableProps } from './personTable.types';
import { Pessoa } from '../../types/pessoa';
import Pagination from '../pagination/Pagination';

const PersonTable: React.FC<PersonTableProps> = ({ pessoas, onEdit, onDelete }) => {
  const [displayedPessoas, setDisplayedPessoas] = useState<Pessoa[]>([]);
  return (
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
          {displayedPessoas?.map((person) => (
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
                <Tooltip title="Editar">
                  <IconButton
                    color="secondary"
                    sx={{ marginRight: '10px' }}
                    onClick={() => onEdit(person)}>
                    <EditIcon />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Remover">
                  <IconButton
                    color="error"
                    onClick={() => onDelete(person.id)}>
                    <DeleteIcon />
                  </IconButton>
                </Tooltip>
              </StyledTableCell>
            </tr>
          ))}
        </tbody>
      </StyledTable>
      <StyledTable>
        <tbody>
          <tr>
            <Pagination
              dados={pessoas}
              onPageChange={(updatedDisplayedPessoas: Pessoa[]) => setDisplayedPessoas(updatedDisplayedPessoas)}
            />
          </tr>
        </tbody>
      </StyledTable>
    </StyledBox>
  );
}

export default PersonTable;
