import { Typography } from '@mui/material';
import styled from 'styled-components';

export const StyledPageContainer = styled.div`
  margin-top: 20px;
  padding: 16px;
`;

export const StyledTypographyTitle = styled(Typography)`
  margin-bottom: 30px !important;
  justify-content: center;
  align-items: center;
  display: flex;
  color: #2196f3;

  ${({ theme }) => theme.breakpoints.up('xs')} {
    font-size: 30px !important;
  }
`;
