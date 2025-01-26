import { Button, Container, Table, TableCell, TablePagination, Grid2, Box } from '@mui/material';
import styled from 'styled-components';

export const StyledContainer = styled(Container)`
  margin-top: 20px;
  padding: 16px;
`;

export const StyledButton = styled(Button)`
  min-width: 100px;
  margin-bottom: 20px;
  font-size: 14px;
  padding: 8px 16px;

  @media (max-width: 600px) {
    font-size: 12px;
    padding: 6px 12px; 
    width: 100%;
    min-width: unset;
  }
`;

export const ButtonGrid = styled(Grid2)`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  width: 100%;

  @media (max-width: 600px) {
    justify-content: center;
    gap: 5px;
  }
`;

export const StyledTable = styled(Table)`
  width: 100%;
  max-width: 100%;
  overflow-x: auto;

  @media (max-width: 600px) {
    display: block;
  }
`;


export const StyledTableCell = styled(TableCell)`
  font-size: 14px;
  padding: 8px;

  @media (max-width: 600px) {
    font-size: 12px;
    padding: 6px;
  }
`;

export const StyledTablePagination = styled(TablePagination)`
  display: flex;
  justify-content: center;
  margin-top: 10px;
  width: 100%;
  padding: 0;

  @media (max-width: 600px) {
    .MuiTablePagination-selectLabel {
      font-size: 10px;
    }

    .MuiTablePagination-select {
      font-size: 10px;
    }

    .MuiTablePagination-displayedRows {
      font-size: 10px;
    }
  }
`;


export const StyledBox = styled(Box)`
  @media (min-width: 600px) {
    overflow-x: auto;
    max-width: 100%;
    margin-bottom: 20px;
  }
`;
