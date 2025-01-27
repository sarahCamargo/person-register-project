import { Box, Button, Grid2, IconButton } from "@mui/material";
import styled from "styled-components";

export const StyledBox = styled(Box)`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 20px;
  box-shadow: 24px;
  border-radius: 8px;

  display: flex;
  flex-direction: column;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;

  ${({ theme }) => theme.breakpoints.down('lg')} {
    width: 85%;
  }
`;

export const Header = styled(Box)`
  display: flex;
  justify-content: center;
  text-align: center;
  margin-bottom: 16px;
  width: 100%;
  position: relative;
`;

export const CloseButton = styled(IconButton)`
  position: absolute !important;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
`;


export const Content = styled(Box)`
  flex: 1;
  overflow-y: auto;
`;

export const Footer = styled(Box)`
  text-align: center;
  margin-top: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
`;


export const StyledButton = styled(Button)`
  width: 100%;
`;

export const StyledGrid = styled(Grid2)`
  ${({ theme }) => theme.breakpoints.down('sm')} {
    gap:0 !important;
  }
`;
