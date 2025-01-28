import { Button, Container, Grid2 } from '@mui/material';
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
  
  ${({ theme }) => theme.breakpoints.down('sm')} {
    font-size: 12px !important;
    padding: 6px 12px !important; 
    width: 100% !important;
    min-width: unset !important;
  }
`;

export const ButtonGrid = styled(Grid2)`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  width: 100%;
  margin-bottom: 10px;

  ${({ theme }) => theme.breakpoints.down('sm')} {
    justify-content: center !important;
    gap: 5px !important;
  }
`;

export const StyledFilter = styled(Grid2)`
  display:flex;
  flex-wrap: wrap;
  gap: 5px !important;

  ${({ theme }) => theme.breakpoints.down('sm')} {
    flex-wrap: wrap;
    margin-bottom: 10px;
    margin-top: 10px;
    width:100%;
  }
`;
