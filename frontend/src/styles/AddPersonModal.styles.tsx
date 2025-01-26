import { Box, Button, Grid2 } from "@mui/material";
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
  overflow-y: auto;
  padding-right: 10px;

  @media (min-width: 600px) {
    width: 80%;
  }

  @media (min-width: 960px) {
    width: 60%;
  }

  @media (min-width: 1280px) {
    width: 50%;
  }
`;

export const Header = styled(Box)`
  display: flex;
  justify-content: center;
  text-align: center;
  margin-bottom: 16px;
  width: 100%;
`;


export const Content = styled(Box)`
  flex: 1;
  overflow-y: auto;
  padding-bottom: 16px;
`;

export const Footer = styled(Box)`
  text-align: center;
  margin-top: 16px;
  padding-top: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
`;


export const StyledButton = styled(Button)`
  width: 100%;
  display: flex;
`;

export const StyledGrid = styled(Grid2)`
  @media (max-width: 600px) {
    gap:0;
  }
`;
