import { Box, Table, TableCell } from "@mui/material";
import styled from "styled-components";

export const StyledTable = styled(Table)`
  max-width: 100%;
  overflow-x: auto;

  ${({ theme }) => theme.breakpoints.down('sm')} {
    display: block !important;
  }
`;

export const StyledTableCell = styled(TableCell)`
  font-size: 14px;
  padding: 8px;

  ${({ theme }) => theme.breakpoints.down('sm')} {
    font-size: 12px;
    padding: 6px;
  }
`;

export const StyledBox = styled(Box)`
  ${({ theme }) => theme.breakpoints.up('sm')} {
    overflow-x: auto;
    max-width: 100%;
    margin-bottom: 20px;
  }
`;