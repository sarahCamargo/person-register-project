import React from 'react';
import PersonList from '../components/personList/PersonList';
import { StyledPageContainer, StyledTypographyTitle } from '../styles/GlobalStyles'

const PersonPage: React.FC = () => {
  return (
    <StyledPageContainer>
      <StyledTypographyTitle variant="h4">
        Registro de Pessoas
      </StyledTypographyTitle>
      <PersonList />
    </StyledPageContainer>
  );
};

export default PersonPage;
